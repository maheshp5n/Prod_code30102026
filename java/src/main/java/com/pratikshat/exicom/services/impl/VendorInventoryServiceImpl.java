package com.pratikshat.exicom.services.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.ApplicationNotificationDetails;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.TemplateDetails;
import com.pratikshat.exicom.entity.TemplateType;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.ServiceAppointmentResponse;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.VendorInventoryService;

@Service
public class VendorInventoryServiceImpl implements VendorInventoryService{
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Value("${feedbackurl}")
	String feedbackurl;
	
	@Autowired
	ServiceRecourseRepository dbRecourseRepository;
	
	@Autowired
	UserProfileRepository dbProfile;

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public Map<String, Object> reAssignServiceAppointment(long id, LoginUser user) throws Exception {
		ServiceAppointment serviceAppointment = null;
		try {
			serviceAppointment = entityManager.createQuery(
					"select sa from ServiceAppointment sa left join sa.caseDetails cd join sa.status st left join sa.claimStatus cs join sa.circleManager cm where sa.serviceAppointmentId=?1",
					ServiceAppointment.class).setParameter(1, id).getSingleResult();

		} catch (javax.persistence.NoResultException ex) {
			throw new Exception("Service Appointment does not exist");
		}
		

		List<Long> list = new ArrayList<Long>();
		list.add(1L); // Unassigned
		list.add(2L); // Scheduled
		list.add(3L); // Dispatched 0
		if (!list.contains(serviceAppointment.getStatus().getStatusId())) {
			throw new Exception("You can not re-assign this service appointment");
		}

		try {
			Query serviceResourceQuery = entityManager.createQuery(
					"select sr from ServiceRecource sr left join sr.user ur where ur.emailId=?1",
					ServiceRecource.class);
			serviceResourceQuery.setParameter(1, user.getUsername());
			ServiceRecource serviceResource = (ServiceRecource) serviceResourceQuery.getSingleResult();

			CaseDetails caseDetails = null;
			try {
				caseDetails = entityManager
						.createQuery("select cd from CaseDetails cd left join cd.engineer where cd.caseId=?1",
								CaseDetails.class)
						.setParameter(1, serviceAppointment.getCaseDetails().getCaseId()).getSingleResult();

			} catch (javax.persistence.NoResultException ex) {
				throw new Exception("Case does not exist");
			}

			caseDetails.setEngineer(serviceResource);
			entityManager.persist(caseDetails);
			serviceAppointment.setEngAssigned(true);
			serviceAppointment.setOwnerDetails(serviceResource.getUser());
			serviceAppointment.setServiceResource(serviceResource);
			serviceAppointment.setReassign(true);

			try {
				ApplicationNotificationDetails notification = new ApplicationNotificationDetails();
				notification.setNofificationDate(new java.util.Date());
				notification.setNotificationActionUrl(
						"/serviceappointment/" + serviceAppointment.getServiceAppointmentId());
				notification.setNotificationMessage(
						String.format("Service Appointment %s has been send to you for approval",
								serviceAppointment.getAppointmentNumber()));
				notification.setNotificationRead(false);
				notification.setUserEmailId(serviceResource.getUser().getEmailId());
				entityManager.persist(notification);
				messagingTemplate.convertAndSend(notification);
			} catch (Exception ex) {

			}

			try {

				com.pratikshat.exicom.entity.ContactDetails contact = entityManager
						.createQuery("select c from ContactDetails c where c.contactId=?1",
								com.pratikshat.exicom.entity.ContactDetails.class)
						.setParameter(1, serviceResource.getContact().getContactId()).getSingleResult();

				com.pratikshat.exicom.entity.TemplateDetails templateDetails = null;

				try {
					Query templateQuery = entityManager.createQuery(
							"select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",
							TemplateDetails.class);
					templateQuery.setParameter(1, true);
					templateQuery.setParameter(2, TemplateType.SMS);
					templateQuery.setParameter(3, new BusinessDomain(2));
					templateQuery.setParameter(4, "assign_sa");

					templateDetails = (TemplateDetails) templateQuery.getSingleResult();
				} catch (javax.persistence.NoResultException nex) {
					nex.printStackTrace();
				} catch (javax.persistence.NonUniqueResultException e) {
					e.printStackTrace();
				}
				String smsMessage = null;
				if (templateDetails != null) {
					smsMessage = templateDetails.getBody();
					smsMessage = smsMessage.replaceFirst("<var>", serviceResource.getName());
					smsMessage = smsMessage.replaceFirst("<var>", serviceAppointment.getAppointmentNumber());
					smsMessage = smsMessage.replaceFirst("<var>",
							feedbackurl + "/#/serviceappointment/" + serviceAppointment.getServiceAppointmentId());
				}

				if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
					com.pratikshat.exicom.utils.SmsNotifier sNotifier = new com.pratikshat.exicom.utils.SmsNotifier(
							entityManager, contact.getMobile(), smsMessage, user.getUsername(),
							templateDetails.getTemplaeCode());
					sNotifier.sendNotification();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource deos not exist");
		}

		entityManager.persist(serviceAppointment);
		entityManager.flush();
		entityManager.clear();

		return null;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public Map<String, Object> assignToServiceAppointment(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		ServiceAppointment serviceAppointment = null;
		try {
			serviceAppointment = entityManager.createQuery(
					"select sa from ServiceAppointment sa left join sa.caseDetails cd join sa.status st left join sa.claimStatus cs join sa.circleManager cm where sa.serviceAppointmentId=?1",
					ServiceAppointment.class).setParameter(1, Long.parseLong(request.get("id").toString())).getSingleResult();

		} catch (javax.persistence.NoResultException ex) {
			throw new BadRequestException("Service Appointment does not exist");
		}
		
		Map<String,String> result=	dbProfile.getAccountAndRoleGroup(user);
		String s = result.get("accountSapNumber");

		if(serviceAppointment.getOwnerDetails().getEmailId()!= user.getUsername()) {
			throw new BadRequestException("You are Not Authorised..");
		}


		List<Long> list = new ArrayList<Long>();
		list.add(1L); // Unassigned
		list.add(2L); // Scheduled
		list.add(3L); // Dispatched 0
		if (!list.contains(serviceAppointment.getStatus().getStatusId())) {
			throw new BadRequestException("You can not re-assign this service appointment");
		}

		try {
			ServiceRecource serviceResource = dbRecourseRepository
					.getServiceResourceWithId(Long.valueOf(request.get("serviceResourceId").toString()));

			CaseDetails caseDetails = null;
			try {
				caseDetails = entityManager
						.createQuery("select cd from CaseDetails cd left join cd.engineer where cd.caseId=?1",
								CaseDetails.class)
						.setParameter(1, serviceAppointment.getCaseDetails().getCaseId()).getSingleResult();

			} catch (javax.persistence.NoResultException ex) {
				throw new BadRequestException("Case does not exist");
			}

			caseDetails.setEngineer(serviceResource);
			entityManager.persist(caseDetails);
			serviceAppointment.setOwnerDetails(serviceResource.getUser());
			serviceAppointment.setServiceResource(serviceResource);
			serviceAppointment.setReassign(true);

			try {
				ApplicationNotificationDetails notification = new ApplicationNotificationDetails();
				notification.setNofificationDate(new java.util.Date());
				notification.setNotificationActionUrl(
						"/serviceappointment/" + serviceAppointment.getServiceAppointmentId());
				notification.setNotificationMessage(
						String.format("Service Appointment %s has been send to you for approval",
								serviceAppointment.getAppointmentNumber()));
				notification.setNotificationRead(false);
				notification.setUserEmailId(serviceResource.getUser().getEmailId());
				entityManager.persist(notification);
				messagingTemplate.convertAndSend(notification);
			} catch (Exception ex) {

			}

			try {

				com.pratikshat.exicom.entity.ContactDetails contact = entityManager
						.createQuery("select c from ContactDetails c where c.contactId=?1",
								com.pratikshat.exicom.entity.ContactDetails.class)
						.setParameter(1, serviceResource.getContact().getContactId()).getSingleResult();

				com.pratikshat.exicom.entity.TemplateDetails templateDetails = null;

				try {
					Query templateQuery = entityManager.createQuery(
							"select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",
							TemplateDetails.class);
					templateQuery.setParameter(1, true);
					templateQuery.setParameter(2, TemplateType.SMS);
					templateQuery.setParameter(3, new BusinessDomain(2));
					templateQuery.setParameter(4, "assign_sa");

					templateDetails = (TemplateDetails) templateQuery.getSingleResult();
				} catch (javax.persistence.NoResultException nex) {
					nex.printStackTrace();
				} catch (javax.persistence.NonUniqueResultException e) {
					e.printStackTrace();
				}
				String smsMessage = null;
				if (templateDetails != null) {
					smsMessage = templateDetails.getBody();
					smsMessage = smsMessage.replaceFirst("<var>", serviceResource.getName());
					smsMessage = smsMessage.replaceFirst("<var>", serviceAppointment.getAppointmentNumber());
					smsMessage = smsMessage.replaceFirst("<var>",
							feedbackurl + "/#/serviceappointment/" + serviceAppointment.getServiceAppointmentId());
				}

				if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
					com.pratikshat.exicom.utils.SmsNotifier sNotifier = new com.pratikshat.exicom.utils.SmsNotifier(
							entityManager, contact.getMobile(), smsMessage, user.getUsername(),
							templateDetails.getTemplaeCode());
					sNotifier.sendNotification();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource deos not exist");
		}

		entityManager.persist(serviceAppointment);
		entityManager.flush();
		entityManager.clear();

		return null;
	}
	
	
	@Override
	public List<ServiceAppointmentResponse> getAdvanceSearchResult(LoginUser user, Map<String, Object> filterData,
			List<String> users) throws ParseException {
//		StringBuilder queryStringBuilder = new StringBuilder();
//		queryStringBuilder.append(
//				"select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sa) from ServiceAppointment sa left join sa.caseDetails cs left join cs.assets ast left join cs.site st left join st.circle cl left join st.ssa zn left join sa.claimStatus clm left join sa.status ss where 1=1");
//		if (filterData.get("sanumber") != null && !filterData.get("sanumber").equals(""))
//			queryStringBuilder.append(" and sa.appointmentNumber like :sanumber");
//		if (filterData.get("casenumber") != null && !filterData.get("casenumber").equals(""))
//			queryStringBuilder.append(" and cs.manualId like :casenumber");
//		if (filterData.get("site") != null && !filterData.get("site").equals(""))
//			queryStringBuilder
//					.append(" and (st.siteName like :site or st.infraId like :site or st.clusterDistrict like :site)");
//		if (filterData.get("ctype") != null && !filterData.get("ctype").equals(""))
//			queryStringBuilder.append(" and cs.caseType.caseTypesName like :ctype");
//		if (filterData.get("creason") != null && !filterData.get("creason").equals(""))
//			queryStringBuilder.append(" and cs.caseReason.caseReasonDetailsName like :creason");
//		if (filterData.get("circle") != null && !filterData.get("creason").equals(""))
//			queryStringBuilder.append(" and cl.circleName like :circle");
//		if (filterData.get("assets") != null && !filterData.get("assets").equals(""))
//			queryStringBuilder.append(" and ast.serialNumber like :assets");
//		if (filterData.get("status") != null && !filterData.get("status").equals(""))
//			queryStringBuilder.append(" and ss.statusName like :status");
//		if (filterData.get("cstatus") != null && !filterData.get("cstatus").equals(""))
//			queryStringBuilder.append(" and clm.claimStatusName like :cstatus");
//		if (filterData.get("srs") != null && !filterData.get("srs").equals(""))
//			queryStringBuilder.append(" and sa.serviceResource.name like :srs");
//		if (filterData.get("crdf") != null && !filterData.get("crdf").equals("") && filterData.get("crdt") != null
//				&& !filterData.get("crdt").equals(""))
//		
//			queryStringBuilder.append(
//					" and (function('date_format', sa.createDate, '%Y, %m, %d') between function('date_format', :crdf, '%Y, %m, %d') and function('date_format', :crdt, '%Y, %m, %d'))");
//		if (users != null)
//			queryStringBuilder.append(" and sa.ownerDetails in :users");
//		Query qr = entityManager.createQuery(queryStringBuilder.toString(), ServiceAppointmentResponse.class);
//
//
//
//		if (filterData.get("sanumber") != null && !filterData.get("sanumber").equals(""))
//			// queryStringBuilder.append(" and sa.appointmentNumber like '%:sanumber%'");
//			qr.setParameter("sanumber", "%" + filterData.get("sanumber") + "%");
//
//		if (filterData.get("casenumber") != null && !filterData.get("casenumber").equals(""))
//			// queryStringBuilder.append(" and cs.manualId like '%:casenumber%'");
//			qr.setParameter("casenumber", "%" + filterData.get("casenumber") + "%");
//
//		if (filterData.get("site") != null && !filterData.get("site").equals(""))
//			// queryStringBuilder.append(" and (st.siteName like '%:site%' or st.infraId
//			// like '%:site%' or st.clusterDistrict like '%:site%')");
//			qr.setParameter("site", "%" + filterData.get("site") + "%");
//
//		if (filterData.get("ctype") != null && !filterData.get("ctype").equals(""))
//			// queryStringBuilder.append(" and cs.caseType.caseTypesName like '%:ctype%'");
//			qr.setParameter("ctype", "%" + filterData.get("ctype") + "%");
//
//		if (filterData.get("creason") != null && !filterData.get("creason").equals(""))
//			// queryStringBuilder.append(" and cs.caseReason.caseReasonDetailsName like
//			// '%:creason%'");
//			qr.setParameter("creason", "%" + filterData.get("creason") + "%");
//		if (filterData.get("circle") != null && !filterData.get("creason").equals(""))
//			// queryStringBuilder.append(" and cl.circleName like '%:circle%'");
//			qr.setParameter("circle", "%" + filterData.get("circle") + "%");
//		if (filterData.get("assets") != null && !filterData.get("assets").equals(""))
//			// queryStringBuilder.append(" and ast.serialNumber like '%:assets%'");
//			qr.setParameter("assets", "%" + filterData.get("assets") + "%");
//		if (filterData.get("status") != null && !filterData.get("status").equals(""))
//			// queryStringBuilder.append(" and ss.statusName like '%:status%'");
//			qr.setParameter("status", "%" + filterData.get("status") + "%");
//		if (filterData.get("cstatus") != null && !filterData.get("cstatus").equals(""))
//			// queryStringBuilder.append(" and clm.claimStatusName like '%:cstatus%'");
//			qr.setParameter("cstatus", "%" + filterData.get("cstatus") + "%");
//		if (filterData.get("srs") != null && !filterData.get("srs").equals(""))
//			// queryStringBuilder.append(" and sa.serviceResource.name like '%:srs%'");
//			qr.setParameter("srs", "%" + filterData.get("srs") + "%");
//		if (filterData.get("crdf") != null && !filterData.get("crdf").equals("") && filterData.get("crdt") != null
//				&& !filterData.get("crdt").equals("")) {
//			// queryStringBuilder.append(" and (sa.createDate between date(:crdf) and
//			// date(:crdt))");
//			SimpleDateFormat dformatter = new SimpleDateFormat("dd-MM-yy");
//			qr.setParameter("crdf", filterData.get("crdf").toString());
//			qr.setParameter("crdt", filterData.get("crdt").toString());
//		}
//		if (users != null) {
//			List<EmployeeDetails> emps = new ArrayList<EmployeeDetails>();
//			for (String usr : users) {
//				emps.add(new EmployeeDetails(usr));
//			}
//			qr.setParameter("users", emps);
//		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public Map<String, Object> unAssignServiceAppointment(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		ServiceAppointment serviceAppointment = null;
		try {
			serviceAppointment = entityManager.createQuery(
					"select sa from ServiceAppointment sa left join sa.caseDetails cd join sa.status st left join sa.claimStatus cs join sa.circleManager cm where sa.serviceAppointmentId=?1",
					ServiceAppointment.class).setParameter(1, Long.parseLong(request.get("id").toString())).getSingleResult();

		} catch (javax.persistence.NoResultException ex) {
			throw new BadRequestException("Service Appointment does not exist");
		}

		if(!serviceAppointment.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new BadRequestException("You are Not Authorised..");
		}


		List<Long> list = new ArrayList<Long>();
		list.add(1L); // Unassigned
		list.add(2L); // Scheduled
		list.add(3L); // Dispatched 0
		if (!list.contains(serviceAppointment.getStatus().getStatusId())) {
			throw new BadRequestException("You can not Unassign this service appointment");
		}

			serviceAppointment.setEngAssigned(false);
			

		entityManager.persist(serviceAppointment);
		entityManager.flush();
		entityManager.clear();

		return null;

	}

}
