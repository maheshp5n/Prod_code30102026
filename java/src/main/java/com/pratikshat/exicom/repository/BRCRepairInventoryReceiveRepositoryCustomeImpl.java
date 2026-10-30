package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import com.pratikshat.exicom.entity.Assets;
import com.pratikshat.exicom.entity.BRCRepairInventoryDispatch;
import com.pratikshat.exicom.entity.BRCRepairInventoryReceive;
import com.pratikshat.exicom.entity.BRCRepairInventoryReceiveRepairItem;
import com.pratikshat.exicom.entity.BRCRepairType;
import com.pratikshat.exicom.entity.BRCRepiarStatus;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BRCRepairInventoryDispatchRequest;
import com.pratikshat.exicom.model.request.BRCRepairInventoryReceiveItemRequest;
import com.pratikshat.exicom.model.request.BRCRepairInventoryReceiveRequest;
import com.pratikshat.exicom.model.request.BRCRepariInventoryProductConsumedRequest;

@org.springframework.transaction.annotation.Transactional
public class BRCRepairInventoryReceiveRepositoryCustomeImpl implements BRCRepairInventoryReceiveRepositoryCustome {

	@javax.persistence.PersistenceContext
	javax.persistence.EntityManager entityManager;
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	@Override
	@org.springframework.transaction.annotation.Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	public BRCRepairInventoryReceiveRequest saveRequest(BRCRepairInventoryReceiveRequest request, LoginUser user)
			throws Exception {
		
		CaseDetails _caseDetails = null;
		try {
			Query selectCaseDetailsQuery = entityManager.createQuery("select ca from CaseDetails ca where ca.caseId=?1",CaseDetails.class);
			selectCaseDetailsQuery.setParameter(1, request.getCaseDetailsId());
			_caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		}
		
		if(_caseDetails==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");	
		}
		
		BRCRepairInventoryReceive receive = null;
		try {
			Query selectCaseDetailsQuery = entityManager.createQuery("select re from BRCRepairInventoryReceive re join re.caseDetails cd where cd.caseId=?1",BRCRepairInventoryReceive.class);
			selectCaseDetailsQuery.setParameter(1, request.getCaseDetailsId());
			receive = (BRCRepairInventoryReceive) selectCaseDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			
		}
		
		if(receive!=null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Battery already received on this case");
		}
		
		
		
		com.pratikshat.exicom.entity.BRCRepairInventoryReceive _receive = new com.pratikshat.exicom.entity.BRCRepairInventoryReceive();
		
		_receive.setCaseDetails(_caseDetails);
		
		if (request.getAssetsSerialNumber() != null && !request.getAssetsSerialNumber().equalsIgnoreCase("")) {
			_receive.setAssets(new com.pratikshat.exicom.entity.Assets(request.getAssetsSerialNumber()));
		}
		_receive.setBatteryVoltageBefoureRepair(request.getBatteryVoltageBefoureRepair());
		_receive.setAlterSerialNumber(request.getAlterSerialNumber());
		_receive.setChallanNumber(request.getChallanNumber());

		if (request.getContactId() != 0) {
			_receive.setContact(new com.pratikshat.exicom.entity.ContactDetails(request.getContactId()));
		}
		_receive.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_receive.setCreatedDate(new java.util.Date());
		
		if(request.getFaultObservedId()!=0) {
			_receive.setFaultObserved(new com.pratikshat.exicom.entity.FaultObserved(request.getFaultObservedId()));	
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Please select fault observed");	
		}
		
		_receive.setInvoiceDate(request.getInvoiceDate());
		_receive.setInvoiceNumber(request.getInvoiceNumber());
		
		if(request.getLegalEntityId()!=0) {
			_receive.setLegalEntity(new com.pratikshat.exicom.entity.LegalEntity(request.getLegalEntityId()));
		}else{
			throw new com.pratikshat.exicom.exception.BadRequestException("Please select legal entity");	
		}
		
		_receive.setLocationStateOrCircle(request.getLocationStateOrCircle());
		_receive.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_receive.setModifiedDate(new java.util.Date());
		_receive.setOwnerDetails(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		
		if(request.getPackingConditionId()!=0) {
			_receive.setPackingCondition(new com.pratikshat.exicom.entity.PackingCondition(request.getPackingConditionId()));	
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Please select packing condition");
		}
		
		_receive.setPhysicalDamageIfAny(request.getPhysicalDamageIfAny());
		
		if(request.getProductPartCode()!=null && request.getProductPartCode().length()>0) {
			_receive.setProduct(new com.pratikshat.exicom.entity.Products(request.getProductPartCode()));
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Please select part code");
		}
		
		_receive.setReceivedDate(request.getReceivedDate());
		_receive.setRemarks(request.getRemarks());
		_receive.setRjilChallanDate(new java.util.Date());
		
		if(request.getSiteId()!=0) {
			_receive.setSite(new com.pratikshat.exicom.entity.SiteDetails(request.getSiteId()));	
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Please select site");
		}
		
		if (request.getBrcRepariStatus() != 0) {
			_receive.setBrcRepariStatus(new com.pratikshat.exicom.entity.BRCRepiarStatus(request.getBrcRepariStatus()));
		} else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Please select status");
		}
		_receive.setSynked(false);
		workflow.executeWorkflow(entityManager, _receive, "INSERT", "PRE", user);
		entityManager.persist(_receive);
		workflow.executeWorkflow(entityManager, _receive, "INSERT", "POST", user);

		javax.persistence.Query updateId=entityManager.createQuery("update BRCRepairInventoryReceive rc set rc.receivingName=?1 where rc.receivingId=?2");
		java.text.SimpleDateFormat dateFormatter=new java.text.SimpleDateFormat("yyyyMMdd");
		String name=String.format("MI-%s-%s",dateFormatter.format(new java.util.Date()),com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_receive.getReceivingId()),5));
		updateId.setParameter(1,name);
		updateId.setParameter(2, _receive.getReceivingId());
		updateId.executeUpdate();
		
		request.setReceivingId(_receive.getReceivingId());
		
		//search index entry
				try {
					com.pratikshat.exicom.entity.GlobalSearchIndex _search=new com.pratikshat.exicom.entity.GlobalSearchIndex();
					_search.setKeyName("receivingName");
					_search.setObjectDetailsUrl("manageInventory/"+_receive.getReceivingId());
					_search.setObjectIdName("receivingId");
					_search.setObjectIdValue(""+_receive.getReceivingId());
					_search.setObjectName("BRCRepairInventoryReceive");
					_search.setSearchString(name);
					entityManager.persist(_search);
					
				}catch(Exception e) {}
				
				entityManager.flush();
				entityManager.clear();
				
		return request;
	}

	// created by shyamu
	@org.springframework.transaction.annotation.Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	@Override
	public BRCRepairInventoryReceiveRequest updateRequest(BRCRepairInventoryReceiveRequest request, LoginUser user)
			throws Exception {

		if (request != null) {
			BRCRepairInventoryReceive receive = null;
			try {
				Query receiveQuery = entityManager.createQuery("select re from BRCRepairInventoryReceive re where re.receivingId=?1",BRCRepairInventoryReceive.class);
				receiveQuery.setParameter(1, request.getReceivingId());
				receive = (BRCRepairInventoryReceive) receiveQuery.getSingleResult();
			}catch(NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("MI does not exist");
			}
			
			CaseDetails _caseDetails = null;
			try {
				Query selectCaseDetailsQuery = entityManager.createQuery("select ca from CaseDetails ca where ca.caseId=?1",CaseDetails.class);
				selectCaseDetailsQuery.setParameter(1, request.getCaseDetailsId());
				_caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			}
			
			receive.setCaseDetails(_caseDetails);
			receive.setAssets(new Assets(request.getAssetsSerialNumber()));
			
			if(request.getProductPartCode()!=null && request.getProductPartCode().length()>0) {
				receive.setProduct(new com.pratikshat.exicom.entity.Products(request.getProductPartCode()));
			}else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Please select part code");
			}
			
			if(request.getLegalEntityId()!=0) {
				receive.setLegalEntity(new com.pratikshat.exicom.entity.LegalEntity(request.getLegalEntityId()));
			}else{
				throw new com.pratikshat.exicom.exception.BadRequestException("Please select legal entity");	
			}
			
			if(request.getSiteId()!=0) {
				receive.setSite(new com.pratikshat.exicom.entity.SiteDetails(request.getSiteId()));	
			}else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Please select site");
			}
			
			if (request.getContactId() != 0) {
				receive.setContact(new com.pratikshat.exicom.entity.ContactDetails(request.getContactId()));
			}
			
			receive.setInvoiceNumber(request.getInvoiceNumber());
			receive.setInvoiceDate(request.getInvoiceDate());
			receive.setAlterSerialNumber(request.getAlterSerialNumber());
			receive.setChallanNumber(request.getChallanNumber());
			receive.setRjilChallanDate(request.getRjilChallanDate());
			receive.setLocationStateOrCircle(request.getLocationStateOrCircle());
			if(request.getPackingConditionId()!=0) {
				receive.setPackingCondition(new com.pratikshat.exicom.entity.PackingCondition(request.getPackingConditionId()));	
			}else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Please select packing condition");
			}
			receive.setReceivedDate(request.getReceivedDate());
			
			receive.setPhysicalDamageIfAny(request.getPhysicalDamageIfAny());
			if(request.getFaultObservedId()!=0) {
				receive.setFaultObserved(new com.pratikshat.exicom.entity.FaultObserved(request.getFaultObservedId()));	
			}else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Please select fault observed");	
			}
			
			if (request.getBrcRepariStatus() != 0) {
				receive.setBrcRepariStatus(new com.pratikshat.exicom.entity.BRCRepiarStatus(request.getBrcRepariStatus()));
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Please select status");
			}
			receive.setBatteryVoltageBefoureRepair(request.getBatteryVoltageBefoureRepair());
			receive.setRemarks(request.getRemarks());
			receive.setSynked(false);

			receive.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			receive.setModifiedDate(new java.util.Date());
			workflow.executeWorkflow(entityManager, receive, "INSERT", "PRE", user);
			entityManager.persist(receive);
			workflow.executeWorkflow(entityManager, receive, "INSERT", "POST", user);

			entityManager.flush();
			entityManager.clear();

		} else {
			throw new Exception("No data found to save details");
		}

		return request;
	}
	// close by shyamu
	
//	@org.springframework.transaction.annotation.Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
//			Exception.class, RuntimeException.class, SQLException.class })
//	@Override
//	public BRCRepairInventoryReceiveItemRequest saveItemRequest(BRCRepairInventoryReceiveItemRequest itemRequest,
//			LoginUser user) throws Exception {
//		
//		BRCRepairInventoryReceiveRepairItem item = null;
//		try {
//			Query receiveQuery = entityManager.createQuery(
//					"select item from BRCRepairInventoryReceiveRepairItem item join BRCRepairInventoryReceive re on item.repairId=re.repariItem.repairId where re.receivingId=?1",
//					BRCRepairInventoryReceiveRepairItem.class);
//			receiveQuery.setParameter(1, itemRequest.getReceiveDetailsId());
//			item = (BRCRepairInventoryReceiveRepairItem) receiveQuery.getSingleResult();
//		}catch(NoResultException ex) {
//			
//		}
//		
//		if(item!=null) {
//			throw new BadRequestException("Already exist");
//		}
//		
//		com.pratikshat.exicom.entity.BRCRepairInventoryReceiveRepairItem _item = new com.pratikshat.exicom.entity.BRCRepairInventoryReceiveRepairItem();
//		_item.setBattryAHAfterRepair(itemRequest.getBattryAHAfterRepair());
//		_item.setBattryBalanceAt(
//				new com.pratikshat.exicom.entity.BRCRepiarBalancedAt(itemRequest.getBattryBalanceAtId()));
//		_item.setBattryEngCorrectiveAction(itemRequest.getBattryEngCorrectiveAction());
//		_item.setBattryEngRemarks(itemRequest.getBattryEngRemarks());
//		_item.setBattryVoltageAfterRepair(itemRequest.getBattryVoltageAfterRepair());
//		_item.setBrcBERRemarks(new com.pratikshat.exicom.entity.BRCRepairBERRemark(itemRequest.getBrcBERRemarksId()));
//		// _item.setBrcEngResolutionMethod(brcEngResolutionMethod);
//		_item.setCellBulged(itemRequest.getCellBulged());
//		_item.setEngineer(new com.pratikshat.exicom.entity.ServiceRecource(itemRequest.getServiceResourceId()));
//		_item.setEngineerIssuedDate(itemRequest.getEngineerIssuedDate());
//		_item.setMaterialSortage(itemRequest.getMaterialSortage());
//		_item.setReceiveDetails(
//				new com.pratikshat.exicom.entity.BRCRepairInventoryReceive(itemRequest.getReceiveDetailsId()));
//		_item.setRepariDate(itemRequest.getRepariDate());
//		_item.setType(new com.pratikshat.exicom.entity.BRCRepairType(itemRequest.getBrcRepariType()));
//		entityManager.persist(_item);
//		for (com.pratikshat.exicom.model.request.BRCRepiarResolutionMethodChoosenRequest choosen : itemRequest
//				.getMethodChoosen()) {
//			com.pratikshat.exicom.entity.BRCRepiarResolutionMethodChoosen _ch = new com.pratikshat.exicom.entity.BRCRepiarResolutionMethodChoosen();
//			_ch.setRepariItem(_item);
//			_ch.setResolutionMethods(
//					new com.pratikshat.exicom.entity.BRCRepiarResolutionMethod(choosen.getResolutionMethodsId()));
//			entityManager.persist(_ch);
//		}
//		itemRequest.setRepairId(_item.getRepairId());
//		
//		entityManager.flush();
//		entityManager.clear();
//		return itemRequest;
//	}

	@org.springframework.transaction.annotation.Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	@Override
	public BRCRepairInventoryReceive saveDispatch(BRCRepairInventoryDispatchRequest dispatchRequest,
			LoginUser user) throws Exception {
		
		BRCRepairInventoryDispatch receive = null;
		try {
			Query receiveQuery = entityManager.createQuery("select dis from BRCRepairInventoryDispatch dis join BRCRepairInventoryReceiveRepairItem item on item.repairId=dis.repariItem.repairId where item.repairId=?1",BRCRepairInventoryDispatch.class);
			receiveQuery.setParameter(1, dispatchRequest.getRepariItemId());
			receive = (BRCRepairInventoryDispatch) receiveQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		
		if(receive!=null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Already dispatched");
		}
		
		com.pratikshat.exicom.entity.BRCRepairInventoryDispatch _dispatch = new com.pratikshat.exicom.entity.BRCRepairInventoryDispatch();
		_dispatch.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_dispatch.setCreatedDate(new java.util.Date());
		_dispatch.setDispatchDate(dispatchRequest.getDispatchDate());
		_dispatch.setDispatcherName(dispatchRequest.getDispatcherName());
		_dispatch.setDispatchMode(dispatchRequest.getDispatchMode());
		_dispatch.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_dispatch.setModifiedDate(new java.util.Date());
		_dispatch.setOwnerDetails(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_dispatch.setPodNumber(dispatchRequest.getPodNumber());
		_dispatch.setDispatchShippmentNo(dispatchRequest.getDispatchShipmentNo());
		_dispatch.setDispatchRemark(dispatchRequest.getDispatchRemark());
		if(dispatchRequest.getRepariItemId()!=0) {
			_dispatch.setRepariItem(new com.pratikshat.exicom.entity.BRCRepairInventoryReceiveRepairItem(dispatchRequest.getRepariItemId()));	
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Battery not assign to engineer");	
		}
		
		_dispatch.setSapDeleveryChallanNumber(dispatchRequest.getSapDeleveryChallanNumber());
		workflow.executeWorkflow(entityManager, dispatchRequest, "INSERT", "PRE", user);

		entityManager.persist(_dispatch);
		workflow.executeWorkflow(entityManager, dispatchRequest, "INSERT", "POST", user);

		dispatchRequest.setDispatchId(_dispatch.getDispatchId());
		
		BRCRepiarStatus status = null;
		try {
			Query queryStatus = entityManager.createQuery("select rb from BRCRepiarStatus rb where rb.statusName=?1",BRCRepiarStatus.class);
			queryStatus.setParameter(1, "Dispatched");
			status = (BRCRepiarStatus) queryStatus.getSingleResult();
		}catch(NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Status does not exist");
		}
		
		BRCRepairInventoryReceive _receive = null;
		try {
			Query receiveQuery = entityManager.createQuery(
					"select re from BRCRepairInventoryReceive re join BRCRepairInventoryReceiveRepairItem item on item.receiveDetails.receivingId=re.receivingId where item.repairId=?1",
					BRCRepairInventoryReceive.class);
			receiveQuery.setParameter(1, dispatchRequest.getRepariItemId());
			_receive = (BRCRepairInventoryReceive) receiveQuery.getSingleResult();
			
			_receive.setBrcRepariStatus(status);
			workflow.executeWorkflow(entityManager, _receive, "UPDATE", "PRE", user);

				entityManager.persist(_receive);
			workflow.executeWorkflow(entityManager, _receive, "UPDATE", "POST", user);
		
		}catch(NoResultException ex) {
			
		}
		
		entityManager.flush();
		entityManager.clear();
		return _receive;
	}

	@org.springframework.transaction.annotation.Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	@Override
	public BRCRepairInventoryReceiveRepairItem assignToEng(long receivingId, long serviceResourceId, LoginUser user) throws Exception {
		BRCRepairInventoryReceive _repariItem = null;
		try {
			Query BRCRepairInventoryQuery = entityManager.createQuery("select rc from BRCRepairInventoryReceive rc where rc.receivingId=?1",BRCRepairInventoryReceive.class);
			BRCRepairInventoryQuery.setParameter(1, receivingId);
			_repariItem = (BRCRepairInventoryReceive) BRCRepairInventoryQuery.getSingleResult();	
		}catch(NoResultException ex) {
			
		}
		
		if(_repariItem==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("MI does not exist");
		}
		
		ServiceRecource serviceRecource = null;
		try {
			Query serviceAppointmentOwnerQuery = entityManager.createQuery(
					"select sr from ServiceRecource sr left join sr.user ur where sr.serviceResourceId=?1",
					ServiceRecource.class);
			serviceAppointmentOwnerQuery.setParameter(1, serviceResourceId);
			serviceRecource = (ServiceRecource) serviceAppointmentOwnerQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource does not exist");
		}
		
		BRCRepairInventoryReceiveRepairItem item = null;
		try {
			Query receiveQuery = entityManager.createQuery(
					"select item from BRCRepairInventoryReceiveRepairItem item left join item.receiveDetails rec where rec.receivingId=?1",
					BRCRepairInventoryReceiveRepairItem.class);
			receiveQuery.setParameter(1, receivingId);
			item = (BRCRepairInventoryReceiveRepairItem) receiveQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		
		if(item!=null) {
			item.setEngineer(serviceRecource);
			item.setEngineerIssuedDate(new Date());
			item.setReceiveDetails(_repariItem);
			item.setType(new BRCRepairType(1));	
		}else {
			item = new BRCRepairInventoryReceiveRepairItem();
			item.setEngineer(serviceRecource);
			item.setEngineerIssuedDate(new Date());
			item.setReceiveDetails(_repariItem);
			item.setType(new BRCRepairType(1));	
		}
		entityManager.persist(item);
		
		try {
			Query queryStatus = entityManager.createQuery("select rb from BRCRepiarStatus rb where rb.statusName=?1",BRCRepiarStatus.class);
			queryStatus.setParameter(1, "Assign To Engineer");
			BRCRepiarStatus status = (BRCRepiarStatus) queryStatus.getSingleResult();

			_repariItem.setBrcRepariStatus(status);
			workflow.executeWorkflow(entityManager, _repariItem, "UPDATE", "PRE", user);
			entityManager.persist(_repariItem);
			workflow.executeWorkflow(entityManager, _repariItem, "UPDATE", "POST", user);
			
		}catch(NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Status does not exist");
		}
		
		entityManager.flush();
		entityManager.clear();
		return item;

	}
	
	@org.springframework.transaction.annotation.Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	@Override
	public BRCRepairInventoryReceiveItemRequest receiveFromEng(BRCRepairInventoryReceiveItemRequest itemRequest,
			LoginUser user) throws Exception {
		
		BRCRepairInventoryReceiveRepairItem _repariItem = null;
		try {
			Query repariAssignedDetailsQuery = entityManager.createQuery("from BRCRepairInventoryReceiveRepairItem repari where repari.repairId=?1",BRCRepairInventoryReceiveRepairItem.class);
			repariAssignedDetailsQuery.setParameter(1, itemRequest.getRepairId());
			_repariItem = (BRCRepairInventoryReceiveRepairItem) repariAssignedDetailsQuery.getSingleResult();
		}catch(NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Battery does not assign to engineer");
		}
		
		if (_repariItem != null) {
			_repariItem.setBattryAHAfterRepair(itemRequest.getBattryAHAfterRepair());
			if(itemRequest.getBattryBalanceAtId()!=0) {
				_repariItem.setBattryBalanceAt(new com.pratikshat.exicom.entity.BRCRepiarBalancedAt(itemRequest.getBattryBalanceAtId()));
			}
			_repariItem.setBattryEngCorrectiveAction(itemRequest.getBattryEngCorrectiveAction());
			_repariItem.setBattryEngRemarks(itemRequest.getBattryEngRemarks());
			_repariItem.setBattryVoltageAfterRepair(itemRequest.getBattryVoltageAfterRepair());
			if(itemRequest.getBrcBERRemarksId()!=0) {
				_repariItem.setBrcBERRemarks(new com.pratikshat.exicom.entity.BRCRepairBERRemark(itemRequest.getBrcBERRemarksId()));
			}
			_repariItem.setCellBulged(itemRequest.getCellBulged());
			_repariItem.setMaterialSortage(itemRequest.getMaterialSortage());
			_repariItem.setRepariDate(itemRequest.getRepariDate());
			if(itemRequest.getBrcRepariType()!=0) {
				_repariItem.setType(new com.pratikshat.exicom.entity.BRCRepairType(itemRequest.getBrcRepariType()));	
			}else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Repair type deos not exist");
			}
			workflow.executeWorkflow(entityManager, _repariItem, "UPDATE", "PRE", user);
			entityManager.persist(_repariItem);
			workflow.executeWorkflow(entityManager, _repariItem, "UPDATE", "POST", user);
			
			for (com.pratikshat.exicom.model.request.BRCRepiarResolutionMethodChoosenRequest choosen : itemRequest.getMethodChoosen()) {
				com.pratikshat.exicom.entity.BRCRepiarResolutionMethodChoosen _ch = new com.pratikshat.exicom.entity.BRCRepiarResolutionMethodChoosen();
				_ch.setRepariItem(_repariItem);
				_ch.setResolutionMethods(new com.pratikshat.exicom.entity.BRCRepiarResolutionMethod(choosen.getResolutionMethodsId()));
				entityManager.persist(_ch);
			}
			
			entityManager.flush();
			entityManager.clear();
			return itemRequest;
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Battery does not assign to engineer");
		}
	}
	
	@org.springframework.transaction.annotation.Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	@Override
	public BRCRepariInventoryProductConsumedRequest consumeProduct(
			BRCRepariInventoryProductConsumedRequest consumeRequest, LoginUser uesr) throws Exception {
		
		com.pratikshat.exicom.entity.BRCRepariInventoryProductConsumed _consumed = new com.pratikshat.exicom.entity.BRCRepariInventoryProductConsumed();
		_consumed.setConsumeddate(new java.util.Date());
		_consumed.setFaultySubmissionDate(consumeRequest.getFaultySubmissionDate());
		_consumed.setMaterialIssueDate(consumeRequest.getMaterialIssueDate());
		if(consumeRequest.getProductPartCode()!=null && consumeRequest.getProductPartCode().length()>0) {
			_consumed.setProduct(new com.pratikshat.exicom.entity.Products(consumeRequest.getProductPartCode()));
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Please select part code");
		}
		
		_consumed.setQuantity(consumeRequest.getQuantity());
		if(consumeRequest.getMiCode()!=0) {
			_consumed.setReceiveDetails(new com.pratikshat.exicom.entity.BRCRepairInventoryReceive(consumeRequest.getMiCode()));
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Please select MI code");
		}
		workflow.executeWorkflow(entityManager, _consumed, "INSERT", "PRE", uesr);
		entityManager.persist(_consumed);
		workflow.executeWorkflow(entityManager, _consumed, "INSERT", "POST", uesr);

		consumeRequest.setConsumeid(_consumed.getConsumedId());
		
		entityManager.flush();
		entityManager.clear();
		return consumeRequest;
	}

}
