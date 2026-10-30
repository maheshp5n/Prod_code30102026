package com.pratikshat.exicom.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.CaseReasonDetails;
import com.pratikshat.exicom.entity.CaseTypes;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.VendorPoDetails;
import com.pratikshat.exicom.entity.WccDetails;
import com.pratikshat.exicom.entity.WccServiceAppointment;
import com.pratikshat.exicom.entity.WccStatus;
import com.pratikshat.exicom.entity.WccTypes;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.WccDetailsRequest;
import com.pratikshat.exicom.model.request.WccServiceAppointmentRequest;

@Repository
public class WccDetailsRepositoryCustomeImpl implements WccDetailsRepositoryCustome {
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	@Autowired 
	JdbcTemplate jdbc;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class})
	public void saveWcc(WccDetailsRequest _request, LoginUser user) throws BadRequestException, UnauthoriseException {
		
		List<WccDetailsRequest> lstReq=new ArrayList<WccDetailsRequest>();
		
		for(WccServiceAppointmentRequest r:_request.getSaIds()) {
			VendorPoDetails tempPo=null;
			try {
				tempPo=em.createQuery("select po from VendorPoDetails po where po=?1",VendorPoDetails.class).setParameter(1, new VendorPoDetails(r.getPoId())).getSingleResult();
				boolean found=false;
				for(int i=0;i<lstReq.size();i++) {
					if(lstReq.get(i).getPonumber().equalsIgnoreCase(tempPo.getPoNumber())) {
						found=true;
						
						if(lstReq.get(i).getSaIds()==null) {
							List<WccServiceAppointmentRequest>t=new ArrayList<WccServiceAppointmentRequest>();
							lstReq.get(i).setSaIds(t);
						}
						lstReq.get(i).getSaIds().add(r);
					}
				}
				if(!found) {
					WccDetailsRequest tr=new WccDetailsRequest();
					tr.setWccTypeId(_request.getWccTypeId());
					tr.setStartDate(_request.getStartDate());
					tr.setToDate(_request.getToDate());
					tr.setPonumber(tempPo.getPoNumber());
					List<WccServiceAppointmentRequest>t=new ArrayList<WccServiceAppointmentRequest>();
					t.add(r);
					tr.setSaIds(t);
					lstReq.add(tr);
				}
				
			}catch(Exception ex) {
				throw new BadRequestException("Invalid PO Id");
			}
		}
		for(WccDetailsRequest request:lstReq) {
			WccDetails _wcc=new WccDetails();
			EmployeeDetails emp=new EmployeeDetails(user.getUsername());
			Date todya=new Date();
			
			ServiceRecource res=null;
			try {
				res=em.createQuery("select res from ServiceRecource res join res.contact ct join ct.legalEntity lg join ct.account act where res.user=?1",ServiceRecource.class)
						.setParameter(1, emp)
						.getSingleResult();
			}catch(NoResultException ex) {
				throw new BadRequestException("Service resource not found");
			}catch(NonUniqueResultException ex) {
				throw new BadRequestException("Multiple service resource found");
			}
			_wcc.setCreatedBy(emp);
			_wcc.setCreatedDate(todya);
			_wcc.setFromDate(request.getStartDate());
			_wcc.setLastModifiedBy(emp);
			_wcc.setLastModifiedDate(todya);
			_wcc.setOwner(emp);
			_wcc.setServiceCenter(res.getContact().getLegalEntity());
			_wcc.setSynked(false);
			_wcc.setWccStatus(new WccStatus(4));
			_wcc.setWccType(new WccTypes(request.getWccTypeId()));
			_wcc.setToDate(request.getToDate());
			_wcc.setWccAmount(0);
			_wcc.setWccTaxAmount(0);
			_wcc.setWccTotalAmount(0);
			
			workflow.executeWorkflow(em, _wcc,"INSERT", "PRE", user);
			em.persist(_wcc);
			String manualId=String.format("WCC-%s", MiscellaneousUtils.fillZero(String.valueOf(_wcc.getId()),5));
			_wcc.setManualId(manualId);
			for(WccServiceAppointmentRequest r:request.getSaIds()) {
				ServiceAppointment apt=null;
				VendorPoDetails poDetails=null;
				
				try {
					apt=em.createQuery("select s from ServiceAppointment s join s.serviceResource sr join sr.user ur where s.serviceAppointmentId=?1",ServiceAppointment.class)
							.setParameter(1, r.getSaId()).getSingleResult();
				}catch(NoResultException ex) {
					throw new BadRequestException("Service appointment not found");
				}
				catch(NonUniqueResultException ex) {
					throw new BadRequestException("Service appointment not found");
				}
				if(!apt.getServiceResource().getUser().getEmailId().equalsIgnoreCase(user.getUsername())) {
					throw new UnauthoriseException("You are not authorised");
				}
				if(apt.isWcc()) throw new BadRequestException(String.format("Service Appointment %s already added in Wcc", apt.getAppointmentNumber()));
				
				try {
					poDetails=em.createQuery("select p from VendorPoDetails p where p=?1",VendorPoDetails.class)
							.setParameter(1,new VendorPoDetails(r.getPoId())).getSingleResult();
				}catch(NoResultException ex) {
					throw new BadRequestException("Po Details not found");
				}
				catch(NonUniqueResultException ex) {
					throw new BadRequestException("Invalid id");
				}
				
				WccServiceAppointment s=new WccServiceAppointment();
				s.setServiceAppointment(apt);
				s.setPoDetails(poDetails);
				poDetails.setRemainingQuantity(poDetails.getRemainingQuantity()-1);
				s.setAmount(poDetails.getActivityPrice());
				s.setTaxRate(poDetails.getTaxRate());
				s.setTaxAmount(s.getAmount()*s.getTaxRate()/100);
				s.setTotalAmount(s.getAmount()+s.getTaxAmount());
				if(poDetails.getRemainingQuantity()<0)
					throw new BadRequestException("po limit exceeded");
				s.setWcc(_wcc);
				em.persist(s);
				apt.setWcc(true);
				apt.setWccId(""+_wcc.getId());
				apt.setWccManualId(_wcc.getManualId());
				em.persist(apt);
				em.persist(poDetails);
				
				_wcc.setWccAmount(_wcc.getWccAmount()+s.getAmount());
				_wcc.setWccTaxAmount(_wcc.getWccTaxAmount()+s.getTaxAmount());
				_wcc.setWccTotalAmount(_wcc.getWccTotalAmount()+s.getTotalAmount());
			}
			
			//String manualId=String.format("WCC-%s", MiscellaneousUtils.fillZero(String.valueOf(_wcc.getId()),5));
			//_wcc.setManualId(manualId);
			em.persist(_wcc);
			try {
				GlobalSearchIndex search=new GlobalSearchIndex();
				search.setKeyName("manualId");
				search.setObjectDetailsUrl("wcc/"+_wcc.getId());
				search.setObjectIdName("id");
				search.setObjectIdValue(""+_wcc.getId());
				search.setObjectName("WccDetails");
				search.setSearchString(manualId);
				em.persist(search);
				
			}catch(Exception ex) {
				
			}
			
			workflow.executeWorkflow(em, _wcc, "INSERT", "POST", user);
			
			request.setId(_wcc.getId());	
		}
	}
	
	private String getApproverEmail(String role, String userMailId, javax.persistence.EntityManager _em) {
		com.pratikshat.exicom.entity.EmployeeDetails emp = null;
		emp = _em
				.createQuery("select mng from EmployeeDetails emp join emp.manager mng where emp.emailId=?1",
						com.pratikshat.exicom.entity.EmployeeDetails.class)
				.setParameter(1, userMailId).getSingleResult();

		java.util.List<String> roles = _em.createQuery(
				"select rol.authority from LoginUserRoles rol join rol.loginUser usr where usr.username=?1",
				String.class).setParameter(1, emp.getEmailId()).getResultList();

		for (String _rl : roles) {
			if (_rl.equalsIgnoreCase(role)) {
				return emp.getEmailId();
			}
		}
		return getApproverEmail(role, emp.getEmailId(), _em);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class})
	public void updateWcc(WccDetailsRequest request, LoginUser user) throws BadRequestException, UnauthoriseException {
		WccDetails _wcc=null;
		try {
			_wcc=em.createQuery("select wc from WccDetails wc where wc=?1",WccDetails.class).setParameter(1,new WccDetails(request.getId())).getSingleResult();
			
		}catch(Exception ex) {
			throw new BadRequestException("Invalid wcc id");
		}
		if(_wcc==null) throw new BadRequestException("Invalid wcc id");
		
		if(!_wcc.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) throw new UnauthoriseException("You are not authorise");
		
		if(_wcc.getWccStatus().getId()==1||_wcc.getWccStatus().getId()==2) throw new BadRequestException("Can not edit WCC "+_wcc.getManualId());
		
		List<WccServiceAppointment> _pSa=em.createQuery("select sa from WccServiceAppointment sa join sa.serviceAppointment ps join sa.poDetails po where sa.wcc=?1",WccServiceAppointment.class).setParameter(1, _wcc).getResultList();
		
		if(_pSa!=null&&_pSa.size()>0) {
			for(WccServiceAppointment s:_pSa) {
				ServiceAppointment apt=s.getServiceAppointment();
				apt.setWcc(false);
				apt.setWccId("");
				apt.setWccManualId("");
				em.persist(apt);
				VendorPoDetails po=s.getPoDetails();
				po.setRemainingQuantity(po.getRemainingQuantity()+1);
				em.persist(po);
			}
			em.createQuery("delete from WccServiceAppointment ws where ws.wcc=?1").setParameter(1,_wcc).executeUpdate();
		}
		
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		Date todya=new Date();
		
		ServiceRecource res=null;
		try {
			res=em.createQuery("select res from ServiceRecource res join res.contact ct join ct.legalEntity lg join ct.account act where res.user=?1",ServiceRecource.class)
					.setParameter(1, emp)
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Service resource not found");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple service resource found");
		}
		CaseTypes ctypes=null;
		CaseReasonDetails cres=null;
		try {
			List<CaseTypes> lst=em.createQuery("select c from CaseTypes c where c.wccTypeId=?1",CaseTypes.class).setParameter(1,request.getWccTypeId()).getResultList();
			if(lst==null||lst.size()==0)throw new BadRequestException("Case types not found with Wcc type");
			ctypes=lst.get(0);
		}catch(Exception ex) {
			throw new BadRequestException("Case types not found with Wcc type");
		}
		try {
			List<CaseReasonDetails> lst=em.createQuery("select c from CaseReasonDetails c where c.caseType=?1",CaseReasonDetails.class)
					.setParameter(1, ctypes).getResultList();
			if(lst==null||lst.size()==0)throw new BadRequestException("Case Reason not found with Wcc type");
			cres=lst.get(0);
		}catch(Exception ex) {
			throw new BadRequestException("Case Reason not found with Wcc type");
		}
		
		_wcc.setCaseReason(cres);
		_wcc.setCaseType(ctypes);
		//_wcc.setCircle(new CircleDetails(request.getCircleName()));
		_wcc.setFromDate(request.getStartDate());
		_wcc.setLastModifiedBy(emp);
		_wcc.setLastModifiedDate(todya);
		_wcc.setServiceCenter(res.getContact().getLegalEntity());
		_wcc.setSynked(false);
		_wcc.setWccStatus(new WccStatus(4));
		_wcc.setWccType(new WccTypes(request.getWccTypeId()));
		_wcc.setToDate(request.getToDate());
		_wcc.setWccAmount(0);
		_wcc.setWccTaxAmount(0);
		_wcc.setWccTotalAmount(0);
		
		workflow.executeWorkflow(em, _wcc,"UPDATE", "PRE", user);
		em.persist(_wcc);
		
		for(WccServiceAppointmentRequest r:request.getSaIds()) {
			ServiceAppointment apt=null;
			VendorPoDetails poDetails=null;
			
			try {
				apt=em.createQuery("select s from ServiceAppointment s join s.serviceResource sr join sr.user ur where s.serviceAppointmentId=?1",ServiceAppointment.class)
						.setParameter(1, r.getSaId()).getSingleResult();
			}catch(NoResultException ex) {
				throw new BadRequestException("Service appointment not found");
			}
			catch(NonUniqueResultException ex) {
				throw new BadRequestException("Service appointment not found");
			}
			if(!apt.getServiceResource().getUser().getEmailId().equalsIgnoreCase(user.getUsername())) {
				throw new UnauthoriseException("You are not authorised");
			}
			if(apt.isWcc()) throw new BadRequestException(String.format("Service Appointment %s already added in Wcc", apt.getAppointmentNumber()));
			
			try {
				poDetails=em.createQuery("select p from VendorPoDetails p where p=?1",VendorPoDetails.class)
						.setParameter(1,new VendorPoDetails(r.getPoId())).getSingleResult();
			}catch(NoResultException ex) {
				throw new BadRequestException("Po Details not found");
			}
			catch(NonUniqueResultException ex) {
				throw new BadRequestException("Invalid id");
			}
			
			WccServiceAppointment s=new WccServiceAppointment();
			s.setServiceAppointment(apt);
			s.setPoDetails(poDetails);
			poDetails.setRemainingQuantity(poDetails.getRemainingQuantity()-1);
			s.setWcc(_wcc);
			
			s.setAmount(poDetails.getActivityPrice());
			s.setTaxRate(poDetails.getTaxRate());
			s.setTaxAmount(s.getAmount()*s.getTaxRate()/100);
			s.setTotalAmount(s.getAmount()+s.getTaxAmount());
			
			if(poDetails.getRemainingQuantity()<0)
				throw new BadRequestException("po limit exceeded");
			
			em.persist(s);
			apt.setWcc(true);
			apt.setWccId(""+_wcc.getId());
			apt.setWccManualId(_wcc.getManualId());
			em.persist(apt);
			em.persist(poDetails);
			
			_wcc.setWccAmount(_wcc.getWccAmount()+s.getAmount());
			_wcc.setWccTaxAmount(_wcc.getWccTaxAmount()+s.getTaxAmount());
			_wcc.setWccTotalAmount(_wcc.getWccTotalAmount()+s.getTotalAmount());
		}
		em.persist(_wcc);
		workflow.executeWorkflow(em, _wcc, "UPDATE", "POST", user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class})
	public void submitForapproval(Long wccId, LoginUser user) throws BadRequestException, UnauthoriseException {
		WccDetails _wcc=null;
		try {
			_wcc=em.createQuery("select w from WccDetails w where w=?1",WccDetails.class).setParameter(1, new WccDetails(wccId)).getSingleResult();
		}catch(Exception ex) {
			throw new BadRequestException("Invalid Id");
		}
		if(!_wcc.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) throw new UnauthoriseException("You are not authorised");
		if(_wcc.getWccStatus().getId()==1||_wcc.getWccStatus().getId()==2) throw new BadRequestException("Already submitted or approved");
		_wcc.setWccStatus(new WccStatus(1));
		em.persist(_wcc);
		
		//approval process
		
		try {

			java.util.Set<String> _roles = new java.util.HashSet<String>();
			java.util.Iterator ite = user.getAuthorities().iterator();
			while (ite.hasNext()) {
				com.pratikshat.exicom.entity.LoginUserRoles _rr = (com.pratikshat.exicom.entity.LoginUserRoles) ite
						.next();
				_roles.add(_rr.getAuthority());
			}

			boolean roleFound = false;
			boolean userFound = false;
			com.pratikshat.exicom.entity.Approval _appliedRule = new com.pratikshat.exicom.entity.Approval();

			java.util.List<com.pratikshat.exicom.entity.Approval> _rulesLst = new java.util.ArrayList<com.pratikshat.exicom.entity.Approval>();

			_rulesLst = em.createQuery(
					"select apr from Approval apr where apr.objectClassName=?1 and apr.active=?2",
					com.pratikshat.exicom.entity.Approval.class).setParameter(1, "WccDetails")
					.setParameter(2, true)
					.getResultList();

			for (com.pratikshat.exicom.entity.Approval _appr : _rulesLst) {
				String submitters[] = _appr.getInitialSubmitters().split(",");
				for (String submi : submitters) {
					String _subs[] = submi.split(":");
					if (_subs[0].equalsIgnoreCase("ROLE")) {
						if (_roles.contains(_subs[1])) {
							roleFound = true;
							break;
						}
					}
					if (_subs[0].equalsIgnoreCase("USER")) {
						if (user.getUsername().equalsIgnoreCase(_subs[1])) {
							userFound = true;
							break;
						}
					}
				}
				if (roleFound || userFound) {
					_appliedRule = _appr;
					break;
				}
			}

			com.pratikshat.exicom.entity.ApprovalSteps _stapes = em
					.createQuery("select stp from ApprovalSteps stp where stp.approval=?1 and stp.approvedStage=?2",
							com.pratikshat.exicom.entity.ApprovalSteps.class)
					.setParameter(1, _appliedRule).setParameter(2, 1).getSingleResult();

			String assigned[] = _stapes.getApprovedBy().split(":");

			String assigneUserEmailId = "";

			if (assigned[0].equalsIgnoreCase("USER")) {
				assigneUserEmailId = assigned[1];
			}
			if (assigned[0].equalsIgnoreCase("APPROVAL-MANAGER")) {
				assigneUserEmailId = em.createQuery(
						"select apm.emailId from EmployeeDetails emp join emp.approvalManager apm where emp.emailId=?1",
						String.class).setParameter(1, user.getUsername()).getSingleResult();
			}

			if (assigned[0].equalsIgnoreCase("MANAGER")) {
				assigneUserEmailId = em.createQuery(
						"select apm.emailId from EmployeeDetails emp join emp.manager apm where emp.emailId=?1",
						String.class).setParameter(1, user.getUsername()).getSingleResult();
			}

			if (assigned[0].equalsIgnoreCase("ROLE")) {
				assigneUserEmailId = getApproverEmail(assigned[1], user.getUsername(), em);
			}

			com.pratikshat.exicom.entity.ToBeApproved _toBeApproved = new com.pratikshat.exicom.entity.ToBeApproved();
			_toBeApproved.setAssignedTo(new com.pratikshat.exicom.entity.EmployeeDetails(assigneUserEmailId));
			_toBeApproved.setAssignedDate(new java.util.Date());
			_toBeApproved.setObjectId(_wcc.getId());
			_toBeApproved.setObjectName("WccDetails");
			_toBeApproved.setObjectId(_wcc.getId());
			_toBeApproved.setObjectManualId(_wcc.getManualId());
			_toBeApproved
					.setObjectUrl("/wcc/" + _wcc.getId());
			_toBeApproved.setRemark("");
			_toBeApproved.setApprovalRuleId(_appliedRule.getId());
			_toBeApproved.setStage(1);
			_toBeApproved.setStatus(false);
			_toBeApproved.setPreviousRemarks(null);
			em.persist(_toBeApproved);

			com.pratikshat.exicom.entity.ApplicationNotificationDetails notificationDetails = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
			notificationDetails.setNofificationDate(new java.util.Date());
			notificationDetails.setNotificationActionUrl("/itemtobeapproved");
			notificationDetails.setNotificationMessage(
					String.format(" %s received for your appoval",
							_wcc.getManualId()));
			notificationDetails.setNotificationRead(false);
			notificationDetails.setUserEmailId(assigneUserEmailId);

			em.persist(notificationDetails);

			try {
				messagingTemplate.convertAndSend("/topic/" + assigneUserEmailId, notificationDetails);
			} catch (Exception ex) {
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BadRequestException("Unable to submit request for approval");

		}
	}
	
	@Override
	@Transactional
	public List<Map<String,Object>> advanceSearch( Map<String,Object> request,List<String> userLists){
		StringBuilder queryStringBuilder=new StringBuilder();
		List<Object> value = new ArrayList<>();
		queryStringBuilder.append("SELECT 	w.`manual_id` AS manulId,\r\n" + 
				"	w.invoiced as invoiced,\r\n"+
				"	w.`id` AS id,\r\n" + 
				" 	w.wcc_amount as wccAmount,\r\n"+
				"   w.wcc_tax_amount as wccTaxAmount,\r\n"+
				"	w.wcc_total_amount as wccTotalAmount,\r\n"+
				"	act.`name` AS vendorName,\r\n" + 
				"	act.`sap_customer_number` AS vendorSapNumber,\r\n" + 
				"	cod.`first_name` AS vendorUserFirstName,\r\n" + 
				"	cod.`last_name` AS vendorUserLastName,\r\n" + 
				"	cod.`middle_name` AS vendorUserMiddleName,\r\n" + 
				"	cod.`employee_code` AS vendorUserCode,\r\n" + 
				"	cod.`contact_id` as vendorContactId,\r\n"+
				"	w.`circle_circle_name` AS circleName,\r\n" + 
				"	lg.`legal_entity_name` AS serviceCenter,\r\n" + 
				"	wt.`wcc_type_name` AS wccType,\r\n" + 
				"	ct.`case_types_name` AS caseType,\r\n" + 
				"	cr.`case_reason_details_name` AS caseReasonDetails,\r\n" + 
				"	ws.`name` AS wccStatus,\r\n" + 
				"	w.`created_date` AS createdDate,\r\n" + 
				"	w.`last_modified_by_email_id` AS lastModifiedByEmailId,\r\n" + 
				"	w.`last_modified_date` AS lastModifiedDate,\r\n" + 
				"	crd.`first_name` AS createdByFirstName,\r\n" + 
				"	crd.`last_name` AS createdByLastname,\r\n" + 
				"	crd.`middle_name` AS createdbyMiddleName,\r\n" + 
				"	mrd.`first_name` AS modifiedByFirstName,\r\n" + 
				"	mrd.`last_name` AS modifiedByLastName,\r\n" + 
				"	mrd.`middle_name` AS modifiedByMiddleName,\r\n" + 
				"	crd.`email_id` AS createdByEmailId,\r\n" + 
				"	mrd.`email_id` AS modifiedByEmailId,\r\n"+
				"	w.to_date as toDAte,\r\n"+
				"	w.from_date as fromDate,\r\n"+
				" 	w.wcc_type_id as wccTypeId,\r\n"+
				"	tobe.remark AS approvalRemark,\r\n" + 
				"	tobe.approved_date AS approvalDate,\r\n"+
				"	(SELECT approved_date FROM to_be_approved WHERE `object_name`='WccDetails' AND `object_id`=w.`id` ORDER BY id DESC LIMIT 1) AS lastActionDate\r\n"+
				"	FROM `wcc_details` w\r\n" + 
				"		JOIN `wcc_status` ws ON ws.`id`=w.`wcc_status_id`\r\n" + 
				"		JOIN `wcc_types` wt ON wt.`id`=w.`wcc_type_id`\r\n" + 
				"		JOIN `legal_entity` lg ON w.`service_center_legal_entity_id`=lg.`legal_entity_id`\r\n" + 
				"		LEFT JOIN `case_types` ct ON ct.`case_type_id`=w.`case_type_case_type_id`\r\n" + 
				"		LEFT JOIN `case_reason_details` cr ON cr.`reason_id`=w.`case_reason_reason_id`\r\n" + 
				"		JOIN `service_recource` sr ON sr.`user_email_id`=w.`owner_email_id`\r\n" + 
				"		JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n" + 
				"		JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n" + 
				"		JOIN `contact_details` crd ON w.`created_by_email_id`=crd.`email_id`\r\n" + 
				"		JOIN `contact_details` mrd ON w.`last_modified_by_email_id`=mrd.`email_id`\r\n"+
				"		LEFT JOIN `to_be_approved` tobe ON tobe.`object_name`='WccDetails' AND tobe.`object_id`=w.`id` AND tobe.`action`='Approved' AND tobe.`status`=1\r\n"+
				"		WHERE 1=1");
		
		
		if(request.get("manualId")!=null&&!request.get("manualId").equals("")) {
			queryStringBuilder.append(" and w.`manual_id` like ?");
			value.add("%"+request.get("manualId")+"%");
		}
		if(request.get("vendorName")!=null&&!request.get("vendorName").equals("")) {
			queryStringBuilder.append(" and act.`name` like ?");
			value.add("%"+request.get("vendorName")+"%");
		}
			
		if(request.get("vendorCode")!=null&&!request.get("vendorCode").equals("")) {
			queryStringBuilder.append(" and act.`sap_customer_number` like ?");
			value.add("%"+request.get("vendorCode")+"%");
		}
			
		if(request.get("serviceCenter")!=null&&!request.get("serviceCenter").equals("")) {
			queryStringBuilder.append(" and lg.`legal_entity_name` like ?");
			value.add("%"+request.get("serviceCenter")+"%");
		}
		
		if(request.get("wccType")!=null&&!request.get("wccType").equals("")) {
			queryStringBuilder.append(" and wt.`wcc_type_name` like ?");
			value.add("%"+request.get("wccType")+"%");
		}
		
		if(request.get("isInvoiced")!=null&&!request.get("isInvoiced").equals("")) {
			if(request.get("isInvoiced").toString().equalsIgnoreCase("true")) {
				queryStringBuilder.append(" and w.invoiced=true");	
			}else {
				queryStringBuilder.append(" and w.invoiced=false");
			}
		}
			
		
		if(userLists!=null) {
			queryStringBuilder.append(" and w.`owner_email_id` in ");
			StringBuilder em=new StringBuilder();
			int i=0;
			for(String usr:userLists) {
				if(i!=0)
					em.append(",");
				em.append("'"+usr+"'");
				i++;
			}
			queryStringBuilder.append("("+em.toString()+")");
		}
					
		return jdbc.queryForList(queryStringBuilder.toString(),value.toArray());
		
	}
}
