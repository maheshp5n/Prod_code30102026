package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.ApplicationNotificationDetails;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LegalEntityType;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LocationProductItem;
import com.pratikshat.exicom.entity.LocationProductLineItems;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PartcodeChangeRequest;
import com.pratikshat.exicom.entity.ProductLineItem;
import com.pratikshat.exicom.entity.ProductLineItemProductState;
import com.pratikshat.exicom.entity.ProductLineItemStatus;
import com.pratikshat.exicom.entity.ServiceOrder;
import com.pratikshat.exicom.entity.ServiceOrderItem;
import com.pratikshat.exicom.entity.ServiceOrderItemReceive;
import com.pratikshat.exicom.entity.ServiceOrderStatus;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.TemplateDetails;
import com.pratikshat.exicom.entity.TemplateType;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.LocationProductRequestResponse;
import com.pratikshat.exicom.utils.MaterialTransferPackingList;
import com.pratikshat.exicom.utils.SmsNotifier;

@Repository
public class ServiceOrderRepositoryCustomeImpl implements ServiceOrderRepositoryCustome {
	@PersistenceContext
	EntityManager em;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	@Autowired
	ProductLineItemRepository dbProductLineItem;

	@Autowired 
	JdbcTemplate jdbc;
	
	@Autowired
	FileStorageProperties fileStorageProperties;
	
	@Value("${feedbackurl}")
	String feedbackurl;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.SERIALIZABLE,rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class})
	public void createServiceOrder(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		Date today=new Date();
		
		Location warehouse=null;
		Location repariCenter=null;
		
		ServiceRecource sr=null;
		
		try {
			sr=em.createQuery("select sr from ServiceRecource sr join sr.location loc where sr.user=?1",ServiceRecource.class).setParameter(1, emp).getSingleResult();
			warehouse=sr.getLocation();
			//warehouse=em.createQuery("select loc from Location loc where loc.owner=?1",Location.class).setParameter(1, emp).getSingleResult();
			
		}catch(NoResultException ex) {
			
			throw new UnauthoriseException("You are not authorise");
			
		}catch(NonUniqueResultException ex) {
			
			throw new BadRequestException("Multiple Location/Warehouse found with your Login");
		}
		
		try {
			repariCenter=em.createQuery("select loc from Location loc join loc.legalEntity lgl where lgl.legalEntityType!=?1 and loc=?2",Location.class)
					.setParameter(1, new LegalEntityType(3))
					.setParameter(2,new Location(Long.valueOf(request.get("repairCenter").toString())))
					.getSingleResult();
		}catch(NoResultException ex) {
			
			throw new BadRequestException("Invalid repari center");
			
		}catch(NonUniqueResultException ex) {
			
			throw new BadRequestException("Invalid request");
		}
		
		ServiceOrder _order=new ServiceOrder();
		_order.setCreatedBy(emp);
		_order.setCreatedDate(today);
		_order.setLastModifiedDate(today);
		_order.setModifiedBy(emp);
		_order.setOrderStatus(new ServiceOrderStatus(2));
		_order.setRepariCenter(repariCenter);
		_order.setWareHouse(warehouse);
		_order.setTotalReceived(0);
		_order.setShippingMode(request.get("shippingMode")==null?null:request.get("shippingMode").toString());
		_order.setShipToName(request.get("shipToName")==null?null:request.get("shipToName").toString());
		_order.setTrackingNumber(request.get("trackingNumber")==null?null:request.get("trackingNumber").toString());
		_order.setSoStatus("Submitted");
		
		workflow.executeWorkflow(em, _order, "INSERT","PRE", user);
		em.persist(_order);
		
		float totalItem=0;
		
		for(String it:(List<String>)request.get("items")) {
			long item=Long.valueOf(it);
			LocationProductLineItems _item=null;
			try {
				_item=em.createQuery("select lineItem from LocationProductLineItems lineItem join lineItem.locationProductItem item where lineItem=?1",LocationProductLineItems.class)
						.setParameter(1,new LocationProductLineItems(item))
						.getSingleResult();
			}catch(NoResultException ex) {
				throw new BadRequestException("invalid request");
			}
			if(_item.getLocationProductItem().getLocation().getLocationId()!=warehouse.getLocationId()) {
				
				throw new UnauthoriseException("You are not authorised");
			}
			LocationProductItem pItem=_item.getLocationProductItem();
			
			//CHECK FOR CHANGE PART CODE PENDING APPROVAL FOR NON WORKFLOW
			PartcodeChangeRequest chRequest=null;
			try {
				
				chRequest=em.createQuery("select item from PartcodeChangeRequest item where item.faultyProduct=?1 and item.faultyProductSerialNumber=?2 and item.flowType=?3 and item.approvedStatus=?4",PartcodeChangeRequest.class)
						.setParameter(1,pItem.getProduct())
						.setParameter(2,_item.getItemSerialNumber())
						.setParameter(3, PartcodeChangeRequestRepository.FLOW_TYPE_NON_SA)
						.setParameter(4, false)
						.getSingleResult();
				throw new BadRequestException("Can not move inventory of part code "+pItem.getProduct().getProductPartCode()+" with serial number "+_item.getItemSerialNumber()+" due to pending approval");
			}catch(NoResultException ex) {
				
				
			}catch(NonUniqueResultException ex) {
				throw new BadRequestException("Can not move inventory of part code "+pItem.getProduct().getProductPartCode()+" with serial number "+_item.getItemSerialNumber()+" due to multiple pending approval");
			}
			
			try {
				
				chRequest=em.createQuery("select item from PartcodeChangeRequest item where item.consumedProduct=?1 and item.consumedProductSerialNumber=?2 and item.flowType=?3 and item.approvedStatus=?4",PartcodeChangeRequest.class)
						.setParameter(1,pItem.getProduct())
						.setParameter(2,_item.getItemSerialNumber())
						.setParameter(3, PartcodeChangeRequestRepository.FLOW_TYPE_NON_SA)
						.setParameter(4, false)
						.getSingleResult();
				throw new BadRequestException("Can not move inventory of part code "+pItem.getProduct().getProductPartCode()+" with serial number "+_item.getItemSerialNumber()+" due to pending approval");
				
			}catch(NoResultException ex) {
				
			}catch(NonUniqueResultException ex) {
				throw new BadRequestException("Can not move inventory of part code "+pItem.getProduct().getProductPartCode()+" with serial number "+_item.getItemSerialNumber()+" due to multiple pending approval");
			}
			
			if(_item.isCustomerInventory())
				pItem.setCustomerInventory(pItem.getCustomerInventory()-1);
			
			long stateid=_item.getLocationProeuctLineItemState().getProductStateId();
			
			if(stateid==1)
				pItem.setOkQuantity(pItem.getOkQuantity()-1);
			if(stateid==2)
				pItem.setFaultyQuantity(pItem.getFaultyQuantity()-1);
			if(stateid==3)
				pItem.setScrapQty(pItem.getScrapQty()-1);
			if(stateid==4)
				pItem.setRejectedQty(pItem.getRejectedQty()-1);
			if(stateid==5)
				pItem.setBerQty(pItem.getBerQty()-1);
			if(stateid==6)
				pItem.setDoaQuantity(pItem.getDoaQuantity()-1);
			
			pItem.setQuantityInOnHand(pItem.getQuantityInOnHand()-1);
			
			if(pItem.getQuantityInOnHand()<0)
				throw new BadRequestException("Quantity can not be less then zero "+pItem.getProduct().getProductPartCode());
			
			em.persist(pItem);
			
			ServiceOrderItem sItem=new ServiceOrderItem();
			sItem.setCustomerInventory(_item.isCustomerInventory());
			sItem.setCustomerName(_item.getCustomerName());
			sItem.setCustomerSap(_item.getCustomerSapNumber());
			sItem.setProduct(pItem.getProduct());
			sItem.setReceived(false);
			sItem.setSerialNumber(_item.getItemSerialNumber());
			sItem.setServiceOrder(_order);
			sItem.setState(_item.getLocationProeuctLineItemState());
			sItem.setSynked(false);
			sItem.setRemark(_item.getRemarks());
			sItem.setLotNo(_item.getLotNo());
			em.persist(sItem);
			totalItem++;
			
			ProductLineItem pLineItem=dbProductLineItem.findByProductAndSerialNumber(pItem.getProduct(), _item.getItemSerialNumber().trim());
			if(pLineItem!=null) {
				pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				pLineItem.setModifiedDate(new Date());
				pLineItem.setPosition("SO,"+_order.getId());
				pLineItem.setPositionState(ProductLineItemRepository.IN_TRANSIT);
				pLineItem.setState(new ProductLineItemProductState(stateid));
				pLineItem.setStatus(new ProductLineItemStatus(2));
				dbProductLineItem.save(pLineItem);
			}
			else {
				pLineItem=new ProductLineItem();
				pLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
				pLineItem.setCreatedDate(new Date());
				pLineItem.setProduct(pItem.getProduct());
				pLineItem.setSerialNumber(_item.getItemSerialNumber().trim());
				pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				pLineItem.setModifiedDate(new Date());
				pLineItem.setPosition("SO,"+_order.getId());
				pLineItem.setPositionState(ProductLineItemRepository.IN_TRANSIT);
				pLineItem.setState(new ProductLineItemProductState(stateid));
				pLineItem.setStatus(new ProductLineItemStatus(2));
				pLineItem.setRemarks(_item.getRemarks());
				pLineItem.setLotNo(_item.getLotNo());
				dbProductLineItem.save(pLineItem);
			}
			
			em.createQuery("delete LocationProductLineItems lineItem where lineItem=?1").setParameter(1,_item).executeUpdate();
			
		}
		
		_order.setTotalItem(totalItem);
		String manualId=String.format("SO-%s", MiscellaneousUtils.fillZero(String.valueOf(_order.getId()),5));
		_order.setManualId(manualId);
		
		em.persist(_order);
		workflow.executeWorkflow(em, _order, "INSERT", "POST", user);
		
		
		request.put("id", _order.getId());
		request.put("manualId", manualId);
		
		//GLOBAL SEARCH ENTRY
		GlobalSearchIndex _index=new GlobalSearchIndex();
		
		_index.setSearchString(manualId);
		_index.setKeyName("manualId");
		_index.setObjectDetailsUrl("serviceorder/"+_order.getId());
		_index.setObjectIdName("id");
		_index.setObjectIdValue(""+_order.getId());
		_index.setObjectName("ServiceOrder");
		em.persist(_index);
		
		//GLOABL SEARCH ENTRY END
		
		//NOTIFICATION ENTRY
		
		ApplicationNotificationDetails notification=new ApplicationNotificationDetails();
		notification.setNofificationDate(new Date());
		notification.setNotificationActionUrl("serviceorder/"+_order.getId());
		notification.setNotificationMessage(String.format("A new service order has issued to you with id %s", manualId));
		notification.setNotificationRead(false);
		notification.setUserEmailId(repariCenter.getOwner().getEmailId());
		
		em.persist(notification);
		
		try {
			messagingTemplate.convertAndSend("/topic/"+repariCenter.getOwner().getEmailId(), notification);
		}catch(Exception ex) {}
		
		try {
			Location repCenter=em.createQuery("select loc from Location loc join loc.legalEntity lgl left join loc.owner onr left join onr.contact cnt where lgl.legalEntityType!=?1 and loc=?2",Location.class)
					.setParameter(1, new LegalEntityType(3))
					.setParameter(2,new Location(Long.valueOf(request.get("repairCenter").toString())))
					.getSingleResult();
			
			TemplateDetails	templateDetails = null;
			
			try {
				Query templateQuery=em.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
				templateQuery.setParameter(1,true);
				templateQuery.setParameter(2,TemplateType.SMS);
				templateQuery.setParameter(3,new BusinessDomain(2));
				templateQuery.setParameter(4,"so_initiate");

				templateDetails = (TemplateDetails) templateQuery.getSingleResult();
			}catch(NoResultException nex) {
				nex.printStackTrace();
			}catch (NonUniqueResultException e) {
				e.printStackTrace();
			}
			
			String smsMessage = null;
			if(templateDetails!=null) {
				smsMessage = templateDetails.getBody();
				smsMessage = smsMessage.replaceFirst("<var>", manualId);
				smsMessage = smsMessage.replaceFirst("<var>", sr.getName());
				smsMessage = smsMessage.replaceFirst("<var>", feedbackurl+"/#/serviceorder/"+_order.getId());
			}
			
			if(smsMessage!=null && !smsMessage.equalsIgnoreCase("")) {
				SmsNotifier sNotifier = new SmsNotifier(em, repCenter.getOwner().getContact().getMobile(),smsMessage, user.getUsername(),templateDetails.getTemplaeCode());
				sNotifier.sendNotification();
			}
		}catch(Exception ex) {
			ex.printStackTrace();	
		}
		
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.SERIALIZABLE,rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class})
	public void receiveServiceOrder(List<Long> ids, LoginUser user) throws BadRequestException, UnauthoriseException {
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		Date toDay=new Date();
		
		for(Long id:ids) {
			ServiceOrderItem soItem=null;
			ServiceOrder order=null;
			try {
				soItem=em.createQuery("select sitem from ServiceOrderItem sitem join sitem.serviceOrder so join so.repariCenter rpcc join ServiceRecource sr on sr.location=rpcc where sitem=?1 and sr.user=?2 and sitem.received=?3",ServiceOrderItem.class)
						.setParameter(1, new ServiceOrderItem(id))
						.setParameter(2, emp)
						.setParameter(3, false)
						.getSingleResult();
			}catch(NoResultException ex) {
				throw new UnauthoriseException("You are not authorise");
			}
			
			LocationProductLineItems _lineItems=null;
			try {
				_lineItems=em.createQuery("select lineitem from LocationProductLineItems lineitem join lineitem.locationProductItem item join item.location loc where lineitem.itemSerialNumber=?1 and item.product=?2",LocationProductLineItems.class).setParameter(1,soItem.getSerialNumber()).setParameter(2, soItem.getProduct()).getSingleResult();
				
				throw new BadRequestException("Unable to receive serial number "+soItem.getSerialNumber()+" of part code "+soItem.getProduct().getProductPartCode()+",Stock avilable with location id  "+_lineItems.getLocationProductItem().getLocation().getLocationName());
			
			}catch(NoResultException ex) {
				
			}catch(NonUniqueResultException ex) {
				throw new BadRequestException("Multiple serial "+soItem.getSerialNumber()+" number of part code "+soItem.getProduct().getProductPartCode()+" found");
			}
			
			order=soItem.getServiceOrder();
			
			if(order.getSoStatus().equalsIgnoreCase("Archived"))
				throw new BadRequestException("Servie order is Archived, can not change");
			
			Location repariCenter=soItem.getServiceOrder().getRepariCenter();
			LocationProductItem locationProductItem=null;
			try {
				locationProductItem=em.createQuery("select item from LocationProductItem item where item.product=?1 and item.location=?2",LocationProductItem.class)
						.setParameter(1, soItem.getProduct())
						.setParameter(2, repariCenter)
						.getSingleResult();
			}catch(NoResultException ex) {
				locationProductItem=new LocationProductItem();
				locationProductItem.setCreatedBy(emp);
				locationProductItem.setCreatedDate(toDay);
				locationProductItem.setHslQuantity(0);
				locationProductItem.setHslStatus("L");
				locationProductItem.setIssuedQunatity(0);
				locationProductItem.setLocation(repariCenter);
				locationProductItem.setMslQuantity(0);
				locationProductItem.setMslQuantityStatus("L");
				locationProductItem.setProduct(soItem.getProduct());
				locationProductItem.setQuantityUnitOfMasur("NO");
				
			}catch(NonUniqueResultException ex) {
				throw new BadRequestException("Invalid product");
			}
			
			long stateId=soItem.getState().getProductStateId();
			
			if(stateId==1)
				locationProductItem.setOkQuantity(locationProductItem.getOkQuantity()+1);
			if(stateId==2)
				locationProductItem.setFaultyQuantity(locationProductItem.getFaultyQuantity()+1);
			if(stateId==3)
				locationProductItem.setScrapQty(locationProductItem.getScrapQty()+1);
			if(stateId==4)
				locationProductItem.setRejectedQty(locationProductItem.getRejectedQty()+1);
			if(stateId==5)
				locationProductItem.setBerQty(locationProductItem.getBerQty()+1);
			if(stateId==6)
				locationProductItem.setDoaQuantity(locationProductItem.getDoaQuantity()+1);
			
			if(soItem.isCustomerInventory())
				locationProductItem.setCustomerInventory(locationProductItem.getCustomerInventory()+1);
			
			locationProductItem.setQuantityInOnHand(locationProductItem.getQuantityInOnHand()+1);
			
			locationProductItem.setModifiedBy(emp);
			locationProductItem.setModifiedDate(toDay);
			
			em.persist(locationProductItem);
			
			if(locationProductItem.getLocationProductItemId()==null) {
				String manualId=String.format("PI-%s",MiscellaneousUtils.fillZero(String.valueOf(locationProductItem.getId()), 5));
				locationProductItem.setLocationProductItemId(manualId);
				em.persist(locationProductItem);
			}
			
			LocationProductLineItems lineItem= new LocationProductLineItems();
			lineItem.setCreatedBy(emp);
			lineItem.setCreatedDate(toDay);
			lineItem.setCustomerInventory(soItem.isCustomerInventory());
			lineItem.setCustomerName(soItem.getCustomerName());
			lineItem.setCustomerSapNumber(soItem.getCustomerSap());
			lineItem.setInStock(true);
			lineItem.setItemSerialNumber(soItem.getSerialNumber());
			lineItem.setLocationProductItem(locationProductItem);
			//lineItem.setLocationProductLineItemStatus();
			lineItem.setLocationProeuctLineItemState(soItem.getState());
			lineItem.setModifiedBy(emp);
			lineItem.setModifiedDate(toDay);
			lineItem.setReplaced(false);
			lineItem.setReturned(false);
			lineItem.setRemarks(soItem.getRemark());
			lineItem.setLotNo(soItem.getLotNo());
			em.persist(lineItem);
			
			String m=String.format("PIL-%s", MiscellaneousUtils.fillZero(String.valueOf(lineItem.getId()), 5));
			lineItem.setLocationProductLineItemId(m);
			em.persist(lineItem);
			
			soItem.setReceived(true);
			
			ProductLineItem pLineItem=dbProductLineItem.findByProductAndSerialNumber(locationProductItem.getProduct(), lineItem.getItemSerialNumber().trim());
			if(pLineItem!=null) {
				pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				pLineItem.setModifiedDate(new Date());
				pLineItem.setPosition(String.valueOf(locationProductItem.getLocation().getLocationId()));
				pLineItem.setPositionState(ProductLineItemRepository.AT_REPAIR);
				pLineItem.setState(soItem.getState());
				pLineItem.setStatus(new ProductLineItemStatus(1));
				dbProductLineItem.save(pLineItem);
			}
			else {
				pLineItem=new ProductLineItem();
				pLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
				pLineItem.setCreatedDate(new Date());
				pLineItem.setProduct(locationProductItem.getProduct());
				pLineItem.setSerialNumber(lineItem.getItemSerialNumber().trim());
				pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				pLineItem.setModifiedDate(new Date());
				pLineItem.setPosition(String.valueOf(locationProductItem.getLocation().getLocationId()));
				pLineItem.setPositionState(ProductLineItemRepository.AT_REPAIR);
				pLineItem.setState(soItem.getState());
				pLineItem.setStatus(new ProductLineItemStatus(1));
				pLineItem.setRemarks(lineItem.getRemarks());
				pLineItem.setLotNo(lineItem.getLotNo());
				dbProductLineItem.save(pLineItem);
			}
			
			ServiceOrderItemReceive received=new ServiceOrderItemReceive();
			received.setCreatedBy(emp);
			received.setItem(soItem);
			received.setLastModifiedBy(emp);
			received.setLastModifiedDate(toDay);
			received.setOwner(emp);
			received.setReceiveDate(toDay);
			
			em.persist(received);
			em.persist(soItem);
			
			order.setTotalReceived(order.getTotalReceived()+1);
			
			if(order.getTotalItem()==order.getTotalReceived())
				order.setOrderStatus(new ServiceOrderStatus(4));
			if(order.getTotalItem()>order.getTotalReceived())
				order.setOrderStatus(new ServiceOrderStatus(3));
			
			order.setLastModifiedDate(toDay);
			order.setModifiedBy(emp);
			
			em.persist(order);
			
		}
	}
	
	@Override
	public List<Map<String,Object>> advanceSearch(Map<String, Object> filterData, LoginUser user, List<Long> childLoc)throws ParseException {
			StringBuilder queryStringBuilder=new StringBuilder();
			List<Object> value = new ArrayList<>();
			queryStringBuilder.append("SELECT \r\n" + 
					"	sord.`id` AS id,\r\n" + 
					"	sord.`manual_id` AS manualId,\r\n" + 
					"	sord.`created_date` AS createdDate,\r\n" + 
					"	sord.`last_modified_date` AS lastModifiedDate,\r\n" + 
					"	sord.`order_status_id` AS orderStatusId,\r\n" + 
					"	sos.`name` AS orderStatusName,\r\n" + 
					"	sord.`repari_center_location_id` AS repairCenterLocationId,\r\n" + 
					"	dloc.`location_name` AS repariCenterLocationName,\r\n" + 
					"	sloc.`location_name` AS warehouseName,\r\n" + 
					"	sord.`ware_house_location_id` AS wareHouseId,\r\n" + 
					"	sord.`total_item` AS totalNumberOfItem,\r\n" + 
					"	cemp.`email_id` AS createdByEmailId,\r\n" + 
					"	cemp.`first_name` AS createdByFirstName,\r\n" + 
					"	cemp.`middle_name` AS createdByMiddleName,\r\n" + 
					"	cemp.`last_name` AS createdByLastName,\r\n" + 
					"	memp.`email_id` AS lastModifiedByEmailD,\r\n" + 
					"	memp.`first_name` AS lastModifiedByFirstName,\r\n" + 
					"	memp.`middle_name` AS lastModifiedByMiddleName,\r\n" + 
					"	memp.`last_name` AS lastModifiedByLastName\r\n" + 
					"	\r\n" + 
					"	\r\n" + 
					" FROM service_order sord \r\n" + 
					"	JOIN `location` sloc ON sloc.`location_id`=sord.`ware_house_location_id`\r\n" + 
					"	JOIN `location` dloc ON dloc.`location_id`=sord.`repari_center_location_id`\r\n" + 
					"	JOIN `service_order_status` sos ON sos.`id`=sord.`order_status_id`\r\n" + 
					"	JOIN `employee_details` cemp ON cemp.`email_id`=sord.`created_by_email_id`\r\n" + 
					"	JOIN `employee_details` memp ON memp.`email_id`=sord.`modified_by_email_id`\r\n" + 
					"	WHERE sord.so_status <>'Archived' and 1=1 ");
			
			
			if(filterData.get("manualId")!=null&&!filterData.get("manualId").equals("")) {
				queryStringBuilder.append(" and sord.`manual_id` like ?");
				value.add("%"+filterData.get("manualId")+"%");
			}
			if(filterData.get("warehouse")!=null&&!filterData.get("warehouse").equals("")) {
				queryStringBuilder.append(" and sloc.`location_name` like ?");
				value.add("%"+filterData.get("warehouse")+"%");
			}
				
			if(filterData.get("repairLocation")!=null&&!filterData.get("repairLocation").equals("")) {
				queryStringBuilder.append(" and dloc.`location_name` like ?");
				value.add("%"+filterData.get("repairLocation")+"%");
			}
				
			if(filterData.get("status")!=null&&!filterData.get("status").equals("")) {
				queryStringBuilder.append(" and sos.`name` like ?");
				value.add("%"+filterData.get("status")+"%");
			}
				
			if(childLoc!=null) {
				queryStringBuilder.append(" and (sord.`ware_house_location_id` in ");
				
				StringBuilder locations=new StringBuilder();
				int i=0;
				for(Long loc:childLoc) {
					if(i!=0)
						locations.append(",");
					locations.append("'"+loc+"'");
					i++;
				}
				queryStringBuilder.append("("+locations.toString()+")");
				
				queryStringBuilder.append(" or sord.`repari_center_location_id` in ");
				
				queryStringBuilder.append("("+locations.toString()+"))");
			}
						
			return jdbc.queryForList(queryStringBuilder.toString(),value.toArray());
		}


	@Override
	public List<Map<String, Object>> getNotOkeItemsAdvanceSearch(Map<String, Object> searchKey,
			LoginUser user) throws ParseException {
		
		StringBuilder queryStringBuilder=new StringBuilder();
		List<Object> value = new ArrayList<>();
		
		queryStringBuilder.append("SELECT \r\n" + 
				"lineitem.`id` AS lineItemId,\r\n" + 
				"lineitem.`customer_inventory` AS isCustomerInventory,\r\n" + 
				"lineitem.`customer_name` AS customerName,\r\n" + 
				"lineitem.`customer_sap_number` AS customerSapNumber,\r\n" + 
				"lineitem.`item_serial_number` AS itemSerialNumber,\r\n" + 
				"item.`product_product_part_code` AS partCode,\r\n" + 
				"lstate.product_state_name AS productStatus,\r\n" + 
				"prod.`pdoduct_description` AS partCodeDescription,\r\n" +
				"lineitem.`remarks` AS `remarks`,\r\n"+
				"lineitem.`lot_no` AS `lotNo`,\r\n"+
				"lineitem.`modified_date` AS `modifiedDate`"+
				"FROM `location_product_line_items` lineitem\r\n" + 
				"JOIN location_product_item item ON item.`id`=lineitem.`location_product_item_id`\r\n" + 
				"JOIN product_line_item_product_state lstate ON lstate.product_state_id=lineitem.location_proeuct_line_item_state_product_state_id\r\n" + 
				"JOIN location loc ON item.`location_location_id`=loc.`location_id`\r\n" + 
				"JOIN products prod ON prod.`product_part_code`=item.`product_product_part_code`\r\n" + 
				"JOIN service_recource sr ON sr.location_location_id=loc.location_id\r\n" + 
				"WHERE lineitem.`location_proeuct_line_item_state_product_state_id`=2");
		
		if(searchKey.get("inhouseInventory")!=null&&!searchKey.get("inhouseInventory").equals("") && 
				searchKey.get("customerInventory")!=null&&!searchKey.get("customerInventory").equals("")) {
			
			if(searchKey.get("inhouseInventory").toString().equalsIgnoreCase("true") && searchKey.get("customerInventory").toString().equalsIgnoreCase("false")) {
				queryStringBuilder.append(" and lineitem.`customer_inventory`=?");
				value.add(false);	
			}
			
			if(searchKey.get("inhouseInventory").toString().equalsIgnoreCase("false") && searchKey.get("customerInventory").toString().equalsIgnoreCase("true")) {
				queryStringBuilder.append(" and lineitem.`customer_inventory`=?");
				value.add(true);	
			}
			
		}else if(searchKey.get("inhouseInventory")!=null&&!searchKey.get("inhouseInventory").equals("") && 
				(searchKey.get("customerInventory")==null || searchKey.get("customerInventory").equals(""))) {
			
			if(searchKey.get("inhouseInventory").toString().equalsIgnoreCase("true")) {
				queryStringBuilder.append(" and lineitem.`customer_inventory`=?");
				value.add(false);	
			}		
		}else if((searchKey.get("inhouseInventory")==null || searchKey.get("inhouseInventory").equals("")) && 
				searchKey.get("customerInventory")!=null&&!searchKey.get("customerInventory").equals("")) {
			
			if(searchKey.get("customerInventory").toString().equalsIgnoreCase("true")) {
				queryStringBuilder.append(" and lineitem.`customer_inventory`=?");
				value.add(true);	
			}
			
		}
			
		if(searchKey.get("lotNo")!=null&&!searchKey.get("lotNo").equals("")) {
			queryStringBuilder.append(" and lineitem.`lot_no` like ?");
			value.add("%"+searchKey.get("lotNo")+"%");
		}
		
		if(searchKey.get("partCode")!=null&&!searchKey.get("partCode").equals("")) {
			queryStringBuilder.append(" and prod.`product_part_code` like ?");
			value.add("%"+searchKey.get("partCode")+"%");
		}
		
		if(searchKey.get("remarks")!=null&&!searchKey.get("remarks").equals("")) {
			queryStringBuilder.append(" and lineitem.`remarks` like ?");
			value.add("%"+searchKey.get("remarks")+"%");
		}
		
		if(user!=null) {
			queryStringBuilder.append(" and sr.`user_email_id`=?");
			value.add(user.getUsername());	
		}
		
		return jdbc.queryForList(queryStringBuilder.toString(),value.toArray());
	}


	@Override
	public List<Map<String, Object>> getServiceOrderItemsAdvanceSearch(long id, Map<String, Object> request)
			throws ParseException {
		
		
		StringBuilder queryStringBuilder=new StringBuilder();
		List<Object> value = new ArrayList<>();
		
		queryStringBuilder.append("SELECT item.`circle` AS circleName,\r\n" + 
				"	item.`customer_inventory` AS isCustomerInventory,\r\n" + 
				"	item.`customer_name` AS customerName,\r\n" + 
				"	item.`customer_sap` AS customerSAP,\r\n" + 
				"	item.`id` AS serviceOrderItemId,\r\n" + 
				"	item.`product_product_part_code` AS partCode,\r\n" + 
				"	item.`received` AS isReceived,\r\n" + 
				"	item.`serial_number` AS itemSerialNumber,\r\n" + 
				"	item.`service_order_id` AS serviceOrderId,\r\n" + 
				"	item.`state_product_state_id` AS stateId,\r\n" + 
				"	st.`product_state_name` AS state,\r\n" + 
				"	prod.`pdoduct_description` AS productDescription,\r\n" + 
				"	srr.service_resource_id as assignedResourceId,\r\n"+
				"	srr.name as assignedToEng,\r\n"+
				"	item.`remark` AS `remarks`,\r\n"+
				"	item.`lot_no` AS `lotNo`\r\n"+
				"\r\n" + 
				"	FROM service_order_item item \r\n" + 
				"	JOIN products prod ON prod.`product_part_code`=item.`product_product_part_code`\r\n" + 
				"	JOIN `product_line_item_product_state` st ON st.`product_state_id`=item.`state_product_state_id` \r\n" + 
				"	LEFT JOIN service_order_item_receive sritem on sritem.item_id=item.id\r\n"+
				"	LEFT JOIN service_recource srr on srr.service_resource_id=sritem.assigned_to_service_resource_id ");
		
		queryStringBuilder.append(" WHERE item.`service_order_id`=?");
		value.add(id);
		
		if(request.get("serialNumber")!=null&&!request.get("serialNumber").equals("")) {
			queryStringBuilder.append(" and item.`serial_number` like ?");
			value.add("%"+request.get("serialNumber")+"%");
		}
		
		if(request.get("productPartcode")!=null&&!request.get("productPartcode").equals("")) {
			queryStringBuilder.append(" and item.`product_product_part_code` like ?");
			value.add("%"+request.get("productPartcode")+"%");
		}
		
		if(request.get("productDescription")!=null&&!request.get("productDescription").equals("")) {
			queryStringBuilder.append(" and prod.`pdoduct_description` like ?");
			value.add("%"+request.get("productDescription")+"%");
		}
		
		
		if(request.get("inhouseInventory")!=null&&!request.get("inhouseInventory").equals("") && 
				request.get("customerInventory")!=null&&!request.get("customerInventory").equals("")) {
			
			if(request.get("inhouseInventory").toString().equalsIgnoreCase("true") && request.get("customerInventory").toString().equalsIgnoreCase("false")) {
				queryStringBuilder.append(" and item.`customer_inventory`=?");
				value.add(false);	
			}
			
			if(request.get("inhouseInventory").toString().equalsIgnoreCase("false") && request.get("customerInventory").toString().equalsIgnoreCase("true")) {
				queryStringBuilder.append(" and item.`customer_inventory`=?");
				value.add(true);	
			}
			
		}else if(request.get("inhouseInventory")!=null&&!request.get("inhouseInventory").equals("") && 
				(request.get("customerInventory")==null || request.get("customerInventory").equals(""))) {
			
			if(request.get("inhouseInventory").toString().equalsIgnoreCase("true")) {
				queryStringBuilder.append(" and item.`customer_inventory`=?");
				value.add(false);	
			}		
		}else if((request.get("inhouseInventory")==null || request.get("inhouseInventory").equals("")) && 
				request.get("customerInventory")!=null&&!request.get("customerInventory").equals("")) {
			
			if(request.get("customerInventory").toString().equalsIgnoreCase("true")) {
				queryStringBuilder.append(" and item.`customer_inventory`=?");
				value.add(true);	
			}
			
		}
		
		
		if(request.get("received")!=null&&!request.get("received").equals("") && 
				request.get("notReceived")!=null&&!request.get("notReceived").equals("")) {
			
			if(request.get("received").toString().equalsIgnoreCase("true") && request.get("notReceived").toString().equalsIgnoreCase("false")) {
				queryStringBuilder.append(" and item.`received`=?");
				value.add(true);	
			}
			
			if(request.get("received").toString().equalsIgnoreCase("false") && request.get("notReceived").toString().equalsIgnoreCase("true")) {
				queryStringBuilder.append(" and item.`received`=?");
				value.add(false);	
			}
			
		}else if(request.get("received")!=null&&!request.get("received").equals("") && 
				(request.get("notReceived")==null || request.get("notReceived").equals(""))) {
			
			if(request.get("received").toString().equalsIgnoreCase("true")) {
				queryStringBuilder.append(" and item.`received`=?");
				value.add(true);	
			}		
		}else if((request.get("received")==null || request.get("received").equals("")) && 
				request.get("notReceived")!=null&&!request.get("notReceived").equals("")) {
			
			if(request.get("notReceived").toString().equalsIgnoreCase("true")) {
				queryStringBuilder.append(" and item.`received`=?");
				value.add(false);	
			}
			
		}
		
		
		if(request.get("assignedTo")!=null&&!request.get("assignedTo").equals("")) {
			queryStringBuilder.append(" and srr.name like ?");
			value.add("%"+request.get("assignedTo")+"%");
		}
		
		if(request.get("lotNo")!=null&&!request.get("lotNo").equals("")) {
			queryStringBuilder.append(" and item.`lot_no` like ?");
			value.add("%"+request.get("lotNo")+"%");
		}
		
		if(request.get("remarks")!=null&&!request.get("remarks").equals("")) {
			queryStringBuilder.append(" and item.`remark` like ?");
			value.add("%"+request.get("remarks")+"%");
		}
		
		return jdbc.queryForList(queryStringBuilder.toString(),value.toArray());
	}
	
	@Transactional(isolation=Isolation.READ_UNCOMMITTED,rollbackFor = {Exception.class,BadRequestException.class,UnauthoriseException.class,RuntimeException.class},propagation = Propagation.REQUIRES_NEW)
	@Override
	public ByteArrayInputStream generatePackingListPdf(LoginUser user, long id, String type) throws Exception {
		
		MaterialTransferPackingList packingListPdf = new MaterialTransferPackingList(em, fileStorageProperties);
		return packingListPdf.createPdf(user, id, type);
	}
}
