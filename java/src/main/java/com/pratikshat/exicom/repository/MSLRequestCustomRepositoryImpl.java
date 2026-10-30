package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.MSLRequest;
import com.pratikshat.exicom.entity.MSLRequestStatus;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.MSLItemRequestRequest;
import com.pratikshat.exicom.model.request.MSLRequestRequest;
import com.pratikshat.exicom.model.response.LocationProductRequestResponse;
import com.pratikshat.exicom.model.response.MSLRequestResponse;


public class MSLRequestCustomRepositoryImpl implements MSLRequestCustomRepository {

	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	SimpMessagingTemplate template;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public MSLRequestRequest createRequest(LoginUser user, MSLRequestRequest mslRequest) throws Exception {
		
		if (mslRequest != null) {
			com.pratikshat.exicom.entity.Location _location=null;
			try {
				 _location=entityManager.createQuery("select loc from Location loc join loc.owner onr where loc.locationId=?1",com.pratikshat.exicom.entity.Location.class).setParameter(1, mslRequest.getLocationId()).getSingleResult();	
			}catch(javax.persistence.NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException(String.format("Not a valid warehouse : %s", ex.getMessage()));
			}
			if(!_location.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
				throw new com.pratikshat.exicom.exception.UnauthoriseException("You are not authorised to create MSL Request for this warehouse");
			}
			
			MSLRequest _mslRequest = new MSLRequest();
			_mslRequest.setCreatedBy(new EmployeeDetails(user.getUsername()));
			_mslRequest.setCreatedDate(new Date());

			_mslRequest.setModifiedBy(new EmployeeDetails(user.getUsername()));
			_mslRequest.setModifiedDate(new Date());

			_mslRequest.setMslRequestStatus(new MSLRequestStatus(mslRequest.getStatusId()));
			_mslRequest.setOwner(new EmployeeDetails(user.getUsername()));
			_mslRequest.setWarehouse(new Location(mslRequest.getLocationId()));
			_mslRequest.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_mslRequest.setCreatedDate(new java.util.Date());
			_mslRequest.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_mslRequest.setModifiedDate(new java.util.Date());
			_mslRequest.setOwner(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			
			com.pratikshat.exicom.entity.MSLRequestStatus _status=entityManager.createQuery("select msls from MSLRequestStatus msls where msls.statusName=?1",com.pratikshat.exicom.entity.MSLRequestStatus.class).setParameter(1, "Draft").getSingleResult();
			_mslRequest.setMslRequestStatus(_status);
			
			entityManager.persist(_mslRequest);
			
			Query updateManualId = entityManager.createQuery("update MSLRequest mr set mr.manualId=?1 where mr.id=?2");
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
			String manualId = "MSL-" + dateformatter.format(new java.util.Date()) + "-"
					+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_mslRequest.getId()), 5);
			updateManualId.setParameter(1, manualId);
			updateManualId.setParameter(2, _mslRequest.getId());
			updateManualId.executeUpdate();
			mslRequest.setMslRequestId(_mslRequest.getId());
			
			for(com.pratikshat.exicom.model.request.MSLItemRequestRequest item:mslRequest.getItemRequest()) {
				
				com.pratikshat.exicom.entity.LocationProductItem _locationProductItem=null;
				try {
					javax.persistence.Query locationProductItemQuery=entityManager.createQuery("select lpt from LocationProductItem lpt join lpt.product prd join lpt.location loc where prd.productPartCode=?1 and loc.locationId=?2",com.pratikshat.exicom.entity.LocationProductItem.class);
					locationProductItemQuery.setParameter(1, item.getProductPartCode());
					locationProductItemQuery.setParameter(2, mslRequest.getLocationId());
					_locationProductItem=(com.pratikshat.exicom.entity.LocationProductItem)locationProductItemQuery.getSingleResult();
				}catch(javax.persistence.NoResultException ex) {
					
				}
				com.pratikshat.exicom.entity.MSLRequestItem _item=new com.pratikshat.exicom.entity.MSLRequestItem();
				_item.setProducts(new com.pratikshat.exicom.entity.Products(item.getProductPartCode()));
				_item.setMslRequest(_mslRequest);
				_item.setOldHSL(_locationProductItem!=null? _locationProductItem.getHslQuantity():0);
				_item.setOlfMSL(_locationProductItem!=null? _locationProductItem.getMslQuantity():0);
				_item.setNewHSL(item.getHslQty());
				_item.setNewMSL(item.getMslQty());
				_item.setRemarks(item.getRemarks());
				entityManager.persist(_item);
			}

			// search index entry
			try {
				com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
				_search.setKeyName("manualId");
				_search.setObjectDetailsUrl("Mslrequest/" + _mslRequest.getId());
				_search.setObjectIdName("id");
				_search.setObjectIdValue("" + _mslRequest.getId());
				_search.setObjectName("MSLRequest");
				_search.setSearchString(manualId);
				entityManager.persist(_search);
				
			} catch (Exception e) {
			}
			entityManager.flush();
			entityManager.clear();
			
		} else {
			
			throw new Exception("No data found to save details");
		}
		return mslRequest;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public MSLRequestRequest updateRequest(LoginUser user, MSLRequestRequest mslRequest) throws Exception {
		
		throw new com.pratikshat.exicom.exception.BadRequestException("Editing msl Request not allowed"); 
		//return mslRequest;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public MSLItemRequestRequest addItems(LoginUser user, MSLItemRequestRequest items) throws Exception {
		if(items==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("No data found");
		}
		com.pratikshat.exicom.entity.MSLRequest _mslRequest=null;
		try {
			_mslRequest=entityManager.createQuery("select msl from MSLRequest msl join msl.warehouse loc join msl.mslRequestStatus sts join msl.owner own where msl.id=?1",com.pratikshat.exicom.entity.MSLRequest.class)
					.setParameter(1, items.getMslRequestId())
					.getSingleResult();
		}catch(javax.persistence.NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException(String.format("Not a valid request %s",ex.getMessage()));
		}
		if(!_mslRequest.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new com.pratikshat.exicom.exception.UnauthoriseException("You are not autorised to add items");
		}
		if(!_mslRequest.getMslRequestStatus().getStatusName().equalsIgnoreCase("Draft")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("MSL request is not in Draft mode, can not add items");
		}
		com.pratikshat.exicom.entity.MSLRequestItem exItem=null;
		try {
			exItem=entityManager.createQuery("select item from MSLRequestItem item join item.products prd join item.mslRequest rc where prd.productPartCode=?1 and rc.id=?2",com.pratikshat.exicom.entity.MSLRequestItem.class)
					.setParameter(1, items.getProductPartCode())
					.setParameter(2, items.getMslRequestId())
					.getSingleResult();
		}catch(javax.persistence.NoResultException ex) {}
		if(exItem!=null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Item already exists");
		}
		com.pratikshat.exicom.entity.LocationProductItem _locationItem =null;
		try {
			_locationItem=entityManager.createQuery("select item from LocationProductItem item join item.product prd join item.location loc where prd.productPartCode=?1 and loc.locationId=?2",com.pratikshat.exicom.entity.LocationProductItem.class)
					.setParameter(1,items.getProductPartCode())
					.setParameter(2, _mslRequest.getWarehouse().getLocationId())
					.getSingleResult();
		}catch(javax.persistence.NoResultException ex) {
			
		}
		com.pratikshat.exicom.entity.MSLRequestItem _item=new com.pratikshat.exicom.entity.MSLRequestItem();
		_item.setMslRequest(new com.pratikshat.exicom.entity.MSLRequest(_mslRequest.getId()));
		_item.setNewHSL(items.getHslQty());
		_item.setNewMSL(items.getMslQty());
		_item.setOldHSL(_locationItem!=null?_locationItem.getHslQuantity():0);
		_item.setOlfMSL(_locationItem!=null?_locationItem.getMslQuantity():0);
		_item.setProducts(new com.pratikshat.exicom.entity.Products(items.getProductPartCode()));
		entityManager.persist(_item);
		items.setMslItmeRequestId(_item.getId());
		return items;
	}

	@Override
	@Transactional(isolation=Isolation.READ_UNCOMMITTED,propagation = Propagation.REQUIRES_NEW,rollbackFor= {Exception.class,RuntimeException.class,UnauthoriseException.class,BadRequestException.class})
	public void submitForApproval(LoginUser user, long mslrequestid,String remarks) throws BadRequestException, UnauthoriseException {
		com.pratikshat.exicom.entity.BtMSLRequest _request=null;
		try {
			_request=entityManager.createQuery("select msl from BtMSLRequest msl join msl.owner own join msl.warehouse loc join loc.parentLocation ploc join ploc.owner powner join msl.mslRequestStatus sts where msl.id=?1",com.pratikshat.exicom.entity.BtMSLRequest.class)
					.setParameter(1, mslrequestid)
					.getSingleResult();
			
		}catch(javax.persistence.NoResultException ex) {
			throw  new BadRequestException("No result found");
		}
		if(!_request.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new UnauthoriseException("You are not authorised");
		}
		if(!_request.getMslRequestStatus().getStatusName().equalsIgnoreCase("Draft")) {
			throw new BadRequestException("Request is not in Draft mode, Already submitted for approval");
		}
		
		entityManager.createQuery("update BtMSLRequest msl set msl.modifiedDate=?1,msl.modifiedBy=?2,msl.mslRequestStatus=?3 where msl.id=?4")
		.setParameter(1, new java.util.Date())
		.setParameter(2,new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()))
		.setParameter(3,new com.pratikshat.exicom.entity.MSLRequestStatus(2))
		.setParameter(4, mslrequestid)
		.executeUpdate();
		
		try {
			
			java.util.Set<String> _roles=new java.util.HashSet<String>();
			java.util.Iterator ite=user.getAuthorities().iterator();
			while(ite.hasNext()) {
				com.pratikshat.exicom.entity.LoginUserRoles _rr=(com.pratikshat.exicom.entity.LoginUserRoles)ite.next();
				_roles.add(_rr.getAuthority());
			}
			
			boolean roleFound=false;
			boolean userFound=false;
			com.pratikshat.exicom.entity.Approval _appliedRule=new com.pratikshat.exicom.entity.Approval();
			
			java.util.List<com.pratikshat.exicom.entity.Approval> _rulesLst=new java.util.ArrayList<com.pratikshat.exicom.entity.Approval>();
			_rulesLst=entityManager.createQuery("select apr from Approval apr where apr.objectClassName=?1 and apr.active=?2",com.pratikshat.exicom.entity.Approval.class)
					.setParameter(1, "BtMSLRequest")
					.setParameter(2, true)
					.getResultList();
			for(com.pratikshat.exicom.entity.Approval _appr:_rulesLst) {
				String submitters[]=_appr.getInitialSubmitters().split(",");
				for(String submi:submitters) {
					String _subs[]=submi.split(":");
					if(_subs[0].equalsIgnoreCase("ROLE")) {
						if(_roles.contains(_subs[1])) {
							roleFound=true;
							break;
						}
					}
					if(_subs[0].equalsIgnoreCase("USER")) {
						if(user.getUsername().equalsIgnoreCase(_subs[1])) {
							userFound=true;
							break;
						}
					}
				}
				if(roleFound||userFound) {
					_appliedRule=_appr;
					break;
				}
			}
			com.pratikshat.exicom.entity.ApprovalSteps _stapes=entityManager.createQuery("select stp from ApprovalSteps stp where stp.approval=?1 and stp.approvedStage=?2",com.pratikshat.exicom.entity.ApprovalSteps.class)
						.setParameter(1, _appliedRule)
						.setParameter(2, 1)
						.getSingleResult();
			
			String assigned[]=_stapes.getApprovedBy().split(":");
			String assigneUserEmailId="";
			if(assigned[0].equalsIgnoreCase("USER")) {
				assigneUserEmailId=assigned[1];
			}
			if(assigned[0].equalsIgnoreCase("APPROVAL-MANAGER")) {
				assigneUserEmailId=entityManager.createQuery("select apm.emailId from EmployeeDetails emp join emp.approvalManager apm where emp.emailId=?1",String.class).setParameter(1, user.getUsername()).getSingleResult();
			}
			if(assigned[0].equalsIgnoreCase("MANAGER")) {
				assigneUserEmailId=entityManager.createQuery("select apm.emailId from EmployeeDetails emp join emp.manager apm where emp.emailId=?1",String.class).setParameter(1, user.getUsername()).getSingleResult();
			}
			if(assigned[0].equalsIgnoreCase("ROLE")) {
				assigneUserEmailId=getApproverEmail(assigned[1],user.getUsername(),entityManager);
			}
			
			com.pratikshat.exicom.entity.ToBeApproved _toBeApproved=new com.pratikshat.exicom.entity.ToBeApproved();
			_toBeApproved.setAssignedTo(new com.pratikshat.exicom.entity.EmployeeDetails(assigneUserEmailId));
			_toBeApproved.setAssignedDate(new java.util.Date());
			_toBeApproved.setObjectId(mslrequestid);
			_toBeApproved.setObjectName("BtMSLReauest");
			_toBeApproved.setObjectId(mslrequestid);
			_toBeApproved.setObjectManualId(_request.getManualId());
			_toBeApproved.setObjectUrl("/BtMslrequest/"+mslrequestid);
			_toBeApproved.setRemark("");
			_toBeApproved.setApprovalRuleId(_appliedRule.getId());
			_toBeApproved.setStage(1);
			_toBeApproved.setStatus(false);
			_toBeApproved.setPreviousRemarks(remarks);
			entityManager.persist(_toBeApproved);
			com.pratikshat.exicom.entity.ApplicationNotificationDetails notificationDetails=new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
			notificationDetails.setNofificationDate(new java.util.Date());
			notificationDetails.setNotificationActionUrl("/itemtobeapproved");
			notificationDetails.setNotificationMessage(String.format("BtMSL Request %s has been send to you for approval", _request.getManualId()));
			notificationDetails.setNotificationRead(false);
			notificationDetails.setUserEmailId(assigneUserEmailId);
			entityManager.persist(notificationDetails);
			
			try {
				template.convertAndSend("/topic/"+assigneUserEmailId,notificationDetails);
			}catch(Exception ex) {}
		}catch(Exception ex) {
			ex.printStackTrace();
			throw new BadRequestException("Unable to submit request for approval");
		}
		
	}
	private String getApproverEmail(String role,String userMailId,javax.persistence.EntityManager _em) {

		com.pratikshat.exicom.entity.EmployeeDetails emp=null;
		emp=_em.createQuery("select mng from EmployeeDetails emp join emp.manager mng where emp.emailId=?1",com.pratikshat.exicom.entity.EmployeeDetails.class)
				.setParameter(1, userMailId)
				.getSingleResult();
		java.util.List<String> roles=_em.createQuery("select rol.authority from LoginUserRoles rol join rol.loginUser usr where usr.username=?1",String.class)
				.setParameter(1, emp.getEmailId()).getResultList();
		for(String _rl:roles) {
			if(_rl.equalsIgnoreCase(role)) {
				return emp.getEmailId();
			}
		}
		return getApproverEmail(role,emp.getEmailId(),_em);
	}
	
	@Override
	public List<MSLRequestResponse> advanceSearch(Map<String, Object> filterData, LoginUser user, List<Long> childLoc)throws ParseException {
			StringBuilder queryStringBuilder=new StringBuilder();
			queryStringBuilder.append("select new com.pratikshat.exicom.model.response.MSLRequestResponse(ra) from MSLRequest ra left join ra.owner onr left join ra.createdBy cby left join ra.modifiedBy cby left join ra.warehouse loc left join ra.mslRequestStatus sts where 1=1 ");
			if(filterData.get("manualId")!=null&&!filterData.get("manualId").equals(""))
				queryStringBuilder.append(" and ra.manualId like :manualId");
			if(filterData.get("warehouse")!=null&&!filterData.get("warehouse").equals(""))
				queryStringBuilder.append(" and loc.locationName like :warehouse");
			if(filterData.get("status")!=null&&!filterData.get("status").equals(""))
				queryStringBuilder.append(" and sts.statusName like :status");
			
			if(childLoc!=null)
				queryStringBuilder.append(" and loc.locationId in :childsLoc");
			
			Query qr=entityManager.createQuery(queryStringBuilder.toString(),MSLRequestResponse.class);
			
			
			if(filterData.get("manualId")!=null&&!filterData.get("manualId").equals(""))
				qr.setParameter("manualId","%"+ filterData.get("manualId")+"%");
			if(filterData.get("warehouse")!=null&&!filterData.get("warehouse").equals(""))
				qr.setParameter("warehouse", "%"+ filterData.get("warehouse")+"%");
			if(filterData.get("status")!=null&&!filterData.get("status").equals(""))
				qr.setParameter("status", "%"+ filterData.get("status")+"%");
			
			if(childLoc!=null) {
				List<Long> emps=new ArrayList<Long>();
				for(Long loc:childLoc) {
					emps.add(loc);
				}
				qr.setParameter("childsLoc", emps);
			}
			return qr.getResultList();
		}
}
