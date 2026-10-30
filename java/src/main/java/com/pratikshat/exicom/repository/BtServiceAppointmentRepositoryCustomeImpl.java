package com.pratikshat.exicom.repository;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.ApplicationNotificationDetails;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

public class BtServiceAppointmentRepositoryCustomeImpl implements BtServiceAppointmentRepositoryCustome{

	  @Autowired
	  WorkflowExecuter workflow;

	  @PersistenceContext
	  EntityManager em;
	  
	  @Autowired
	  private SimpMessagingTemplate messagingTemplate;
	
	@Override
	public BtServiceAppointment saveDetails(BtServiceAppointment btServiceAppointment, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btServiceAppointment, "INSERT", "PRE", user);
		em.persist(btServiceAppointment);
		SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
		String manualId = null;
		manualId = "SA-BT-" + MiscellaneousUtils.fillZero(String.valueOf(btServiceAppointment.getServiceAppointmentId()),5);
		btServiceAppointment.setManualId(manualId);
		em.persist(btServiceAppointment);
		workflow.executeWorkflow(em, btServiceAppointment, "INSERT", "POST", user);
		try {
			ApplicationNotificationDetails notification = new ApplicationNotificationDetails();
			notification.setNofificationDate(new java.util.Date());
			notification.setNotificationActionUrl(
					"/bat_serviceappointment/" + btServiceAppointment.getServiceAppointmentId());
			notification.setNotificationMessage(
					String.format("BtService Appointment %s assigned for your action",
							manualId));
			notification.setNotificationRead(false);
			notification
					.setUserEmailId(btServiceAppointment.getServiceResource().getUser().getEmailId());
			em.persist(notification);
			messagingTemplate.convertAndSend("/topic/"+btServiceAppointment.getServiceResource().getUser().getEmailId(),notification);
		} catch (Exception ex) {
		}
		try {
			com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
			_search.setKeyName("appointmentNumber");
			_search.setObjectDetailsUrl(
					"bat_serviceappointment/" + btServiceAppointment.getServiceAppointmentId());
			_search.setObjectIdName("serviceAppointmentId");
			_search.setObjectIdValue("" + btServiceAppointment.getServiceAppointmentId());
			_search.setObjectName("BtServiceAppointment");
			_search.setSearchString(manualId);
			em.persist(_search);
		} catch (Exception e) {
		}
		return btServiceAppointment;
	}

	@Override
	public BtServiceAppointment updateDetails(BtServiceAppointment btServiceAppointment, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		BtServiceAppointment _exits= null;
		try {
			System.out.println(btServiceAppointment.getId()+ "service appointment");
			_exits=em.createQuery("select ser from BtServiceAppointment ser where ser.serviceAppointmentId=?1",
					BtServiceAppointment.class)
					                   .setParameter(1,btServiceAppointment.getServiceAppointmentId()).getSingleResult();
			
		}catch (NoResultException ex){
            throw new BadRequestException("Assets details not found");
        }catch (NonUniqueResultException ex){
            throw new BadRequestException("Multiple assets details found");
        }
		_exits.setAcknowledgedByName(btServiceAppointment.getAcknowledgedByName());
		_exits.setAcknowledgedByPhone(btServiceAppointment.getAcknowledgedByPhone());
		_exits.setCorrectiveActionss(btServiceAppointment.getCorrectiveActionss());
		_exits.setFaultObserved(btServiceAppointment.getFaultObserved());
		_exits.setPendingResponsibility(btServiceAppointment.getPendingResponsibility());
		_exits.setPunchPointCustomer(btServiceAppointment.getPunchPointCustomer());
		_exits.setPunchPointExiom(btServiceAppointment.getPunchPointExiom());
		_exits.setStatus(btServiceAppointment.getStatus());
		_exits.setRemark(btServiceAppointment.getRemark());
		_exits.setSyncFlag(btServiceAppointment.isSyncFlag());
		_exits.setAssets(btServiceAppointment.getAssets());
		_exits.setProduct(btServiceAppointment.getProduct());
		_exits.setRma(btServiceAppointment.isRma());
		_exits.setBtServicePayout(btServiceAppointment.getBtServicePayout());
		_exits.setServiceAppointmentExpense(btServiceAppointment.isServiceAppointmentExpense());
		_exits.setVisitDate(btServiceAppointment.getVisitDate());
		_exits.setClosedFromPlatform(btServiceAppointment.getClosedFromPlatform());
		_exits.setFsrCreated(btServiceAppointment.isFsrCreated());
		_exits.setSchFill(btServiceAppointment.isSchFill());
		 workflow.executeWorkflow(em,_exits,"UPDATE","PRE",user);
	     em.persist(_exits);
	     workflow.executeWorkflow(em,_exits,"UPDATE","POST",user);
		return _exits;
	}

	@Override
	public BtServiceAppointment deleteDetails(BtServiceAppointment t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
	
		return null;
	}

	@Override
	public BtServiceAppointment getServiceAppointment(long id) throws BadRequestException {
		BtServiceAppointment appointment=null;
		try {
			appointment = em.createQuery(
					"select sa from BtServiceAppointment sa join sa.status st left join sa.claimStatus cs left join sa.circleManager cm left join sa.ownerDetails onr join sa.serviceResource sr join sr.user ur where sa.serviceAppointmentId=?1",
					BtServiceAppointment.class).setParameter(1, id).getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("Service Appointment does not exist");
		}
		return appointment;
	}
	@Override
	public List<BtServiceAppointment> getListWithCaseIDandStatus(long caseId,List<Long> saStatusList) throws BadRequestException {
		List<BtServiceAppointment> serviceAppointmentList = null;
		try {
			serviceAppointmentList = em.createQuery(
					"select sa from BtServiceAppointment sa join sa.status st join sa.caseDetails cd where cd.id=:caseId and st.statusId in :statusList",
					com.pratikshat.exicom.entity.BtServiceAppointment.class)
					.setParameter("caseId",caseId )
					.setParameter("statusList", saStatusList)
					.getResultList();
		} catch (NoResultException ex) {

		}
		return serviceAppointmentList;
	}

	@Override
	public List<BtServiceAppointment> getListWithCaseId(long caseId) throws BadRequestException {
		List<BtServiceAppointment> SAList=null;
		
		try {
			SAList = em.createQuery(
					"select sa from BtServiceAppointment sa left join sa.caseDetails cs join sa.status st left join sa.claimStatus css left join sa.circleManager cm where cs.id=?1 order by sa.createDate desc",
					BtServiceAppointment.class).setParameter(1, caseId).getResultList();

		} catch (javax.persistence.NoResultException ex) {
			throw new BadRequestException("Service Appointment does not exist");
		}
		return SAList;
	}

	@Override
	public EmployeeDetails caseCreatedByEmployee(String email) throws BadRequestException, UnauthoriseException {
		EmployeeDetails caseCreatedBy=null;
		try {
			Query getUserDetailsQuery = em.createQuery(
					"select emp from EmployeeDetails emp left join ContactDetails ct on emp.emailId=ct.emailId where emp.emailId=?1");
			getUserDetailsQuery.setParameter(1, email);
			caseCreatedBy = (com.pratikshat.exicom.entity.EmployeeDetails) getUserDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Created by does not exist");
		}
		
		return caseCreatedBy;
	}

}