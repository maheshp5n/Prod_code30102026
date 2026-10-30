package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.pratikshat.exicom.entity.LegalEntityType;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LocationProductItem;
import com.pratikshat.exicom.entity.LocationProductLineItems;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ProductLineItemProductState;
import com.pratikshat.exicom.entity.ProductLineItemStatus;
import com.pratikshat.exicom.entity.ServiceOrderItem;
import com.pratikshat.exicom.entity.ServiceOrderItemReceive;
import com.pratikshat.exicom.entity.ServiceOrderItemReturn;
import com.pratikshat.exicom.entity.ServiceOrderReturn;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.TemplateDetails;
import com.pratikshat.exicom.entity.TemplateType;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ServiceOrderReturnRequest;
import com.pratikshat.exicom.model.request.ServiceOrderReturnRequestItem;
import com.pratikshat.exicom.utils.MaterialTransferPackingList;
import com.pratikshat.exicom.utils.SmsNotifier;

@Repository
public class ServiceOrderItemReturnRepositoryCustomeImpl implements ServiceOrderItemReturnRepositoryCustome {
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	SimpMessagingTemplate msTemplate;
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	FileStorageProperties fileStorageProperties;
	
	@Value("${feedbackurl}")
	String feedbackurl;

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	@Override
	public void createServiceOrderReturn(ServiceOrderReturnRequest request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		//LOGIN EMP & TODAY
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		Date today=new Date();
		
		ServiceRecource sr=null;
		
		try {
			sr=em.createQuery("select sr from ServiceRecource sr join sr.location loc where sr.user=?1",ServiceRecource.class).setParameter(1, emp).getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Service resource not found");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multipal resource found");
		}
		
		//SAVING FIRST SERVICE ORDER  RETURN 
		
		ServiceOrderReturn _returnServiceOrder=new ServiceOrderReturn();
		_returnServiceOrder.setCreatedBy(emp);
		_returnServiceOrder.setCreatedDate(today);
		_returnServiceOrder.setModifiedBy(emp);
		_returnServiceOrder.setModifiedDate(today);
		_returnServiceOrder.setOwnner(emp);
		_returnServiceOrder.setShippingMode(request.getShippingMode());
		_returnServiceOrder.setShipToName(request.getShipToName());
		_returnServiceOrder.setTrackingNumber(request.getTrackingNumber());
		_returnServiceOrder.setTotalItem(request.getItems().size());
		_returnServiceOrder.setTotalReceive(0);
		
		em.persist(_returnServiceOrder);
		
		Set<Long> ids=new HashSet<Long>();
		Location mainWarehouse=null;
		//FOR EACH ITEMS
		for(ServiceOrderReturnRequestItem returnItem:request.getItems()) {
		
			//CHECK FOR DUPLICACY OF RECEIVE ITEM ID
			if(ids.contains(returnItem.getItemReceiveId()))
				throw new BadRequestException("Duplicate id");
			
			ids.add(returnItem.getItemReceiveId());
			
			Location repariCenter=null;
			
			
			ServiceOrderItemReceive _itemReceive;
			
			try {
				_itemReceive = em.createQuery("select ritem from ServiceOrderItemReceive ritem join ritem.item item join item.serviceOrder sord join item.product prd join sord.repariCenter rnrc join sord.wareHouse wrhm where ritem=?1",ServiceOrderItemReceive.class)
						.setParameter(1, new ServiceOrderItemReceive(new ServiceOrderItem(returnItem.getItemReceiveId())))
						.getSingleResult();
			}catch(NoResultException  ex) {
				throw new BadRequestException("Invalid request");
			}catch(NonUniqueResultException ex) {
				throw new BadRequestException("Invalid request multiple");
			}
			
			if(_itemReceive.getItem().getServiceOrder().getSoStatus().equalsIgnoreCase("Archived"))
				throw new BadRequestException("Service order has been archived, can not change record");
			
			repariCenter=_itemReceive.getItem().getServiceOrder().getRepariCenter();
			if(mainWarehouse==null)
				mainWarehouse=_itemReceive.getItem().getServiceOrder().getWareHouse();
			
			else
				if(mainWarehouse.getLocationId()!=_itemReceive.getItem().getServiceOrder().getWareHouse().getLocationId())
					throw new BadRequestException("Can not return to multiple warehouse");
			
			if(_itemReceive.isReturned())
				throw new BadRequestException(String.format("Part code %s with serial number %s already returnd",_itemReceive.getItem().getProduct().getProductPartCode(),_itemReceive.getItem().getSerialNumber()));
			
			if(repariCenter.getLocationId()!=sr.getLocation().getLocationId())
				throw new UnauthoriseException("You are not authorise");
			
//			if(!repariCenter.getOwner().getEmailId().equalsIgnoreCase(user.getUsername()))
//				throw new UnauthoriseException("You are not authorise");
			
			LocationProductLineItems locationProductLineItems=null;
			
			try {
				locationProductLineItems =em.createQuery("select litem from LocationProductLineItems litem join litem.locationProductItem item where item.product=?1 and litem.itemSerialNumber=?2",LocationProductLineItems.class)
						.setParameter(1, _itemReceive.getItem().getProduct())
						.setParameter(2, _itemReceive.getItem().getSerialNumber())
						.getSingleResult();
				
			}catch(NoResultException ex) {
				throw new BadRequestException(String.format("Stock for part code %s with serial number %s not found", _itemReceive.getItem().getProduct().getProductPartCode(),_itemReceive.getItem().getSerialNumber()));
			}catch(NonUniqueResultException ex) {
				throw new BadRequestException("Multiple stock found");
			}
			
			if(!locationProductLineItems.getLocationProductItem().getLocation().getOwner().getEmailId().equalsIgnoreCase(user.getUsername()))
				throw new UnauthoriseException(String.format("You are not authorise, Stock for part code %s with serial number %s is not in your warehouse", _itemReceive.getItem().getProduct().getProductPartCode(),_itemReceive.getItem().getSerialNumber()));
			
			LocationProductItem locationProductItem=locationProductLineItems.getLocationProductItem();
			
			long pstatus=locationProductLineItems.getLocationProeuctLineItemState().getProductStateId();
			
			if(pstatus==1)
				locationProductItem.setOkQuantity(locationProductItem.getOkQuantity()-1);
			
			if(pstatus==2)
				locationProductItem.setFaultyQuantity(locationProductItem.getFaultyQuantity()-1);
			
			if(pstatus==3)
				locationProductItem.setScrapQty(locationProductItem.getScrapQty()-1);
			
			if(pstatus==4)
				locationProductItem.setRejectedQty(locationProductItem.getRejectedQty()-1);
			
			if(pstatus==5)
				locationProductItem.setBerQty(locationProductItem.getBerQty()-1);
			
			if(locationProductLineItems.isCustomerInventory())
				locationProductItem.setCustomerInventory(locationProductItem.getCustomerInventory()-1);
			
			if(locationProductLineItems.isRefurbished()&&pstatus==1)
				locationProductItem.setRefurbished(locationProductItem.getRefurbished()-1);
			
			locationProductItem.setQuantityInOnHand(locationProductItem.getQuantityInOnHand()-1);
			
			locationProductItem.setModifiedBy(emp);
			locationProductItem.setModifiedDate(today);
			em.persist(locationProductItem);
			
			ServiceOrderItemReturn soItemReturn=new ServiceOrderItemReturn();
			soItemReturn.setCreatedBy(emp);
			soItemReturn.setCreatedDate(today);
			soItemReturn.setCustomerInventory(locationProductLineItems.isCustomerInventory());
			soItemReturn.setCustomerName(locationProductLineItems.getCustomerName());
			soItemReturn.setCustomerSapNumber(locationProductLineItems.getCustomerSapNumber());
			soItemReturn.setId(_itemReceive.getItem().getId());
			soItemReturn.setModifiedBy(emp);
			soItemReturn.setModifiedDate(today);
			soItemReturn.setOwner(emp);
			soItemReturn.setReceived(false);
			soItemReturn.setServiceOrderReturn(_returnServiceOrder);
			if(locationProductLineItems.isRefurbished()&&returnItem.getFinalStatusId()==1)
				soItemReturn.setRefurbished(true);
			else
				soItemReturn.setRefurbished(false);
			
			soItemReturn.setReturnDate(today);
			soItemReturn.setReturnWarehouse(mainWarehouse);
			_returnServiceOrder.setSourceLoccation(repariCenter);
			_returnServiceOrder.setDestinationLocation(mainWarehouse);
			
			soItemReturn.setState(new ProductLineItemProductState(returnItem.getFinalStatusId()));
			soItemReturn.setRemarks(locationProductLineItems.getRemarks());
			soItemReturn.setLotNo(locationProductLineItems.getLotNo());
			em.persist(soItemReturn);
			
			_itemReceive.setReturned(true);
			
			em.persist(_itemReceive);
			
			em.createQuery("delete from LocationProductLineItems litem where litem=?1").setParameter(1, locationProductLineItems).executeUpdate();
		}
		String soReturnManualid=String.format("SOR-%s", MiscellaneousUtils.fillZero(String.valueOf(_returnServiceOrder.getId()),5));
		_returnServiceOrder.setManualId(soReturnManualid);
		em.persist(_returnServiceOrder);
		
		ApplicationNotificationDetails _notification=new ApplicationNotificationDetails();
		_notification.setNofificationDate(today);
		_notification.setNotificationActionUrl("/serviceorder");
		_notification.setNotificationMessage(String.format("%d returned from repair center", ids.size()));
		_notification.setNotificationRead(false);
		_notification.setUserEmailId(mainWarehouse.getOwner().getEmailId());
		em.persist(_notification);
		
		try {
			msTemplate.convertAndSend("/topic/"+_notification.getUserEmailId(),_notification);
		}catch(Exception e) {}
		
		
		
		try {
			Location warehouse=em.createQuery("select loc from Location loc join loc.legalEntity lgl left join loc.owner onr left join onr.contact cnt where loc.locationId=?1",Location.class)
					.setParameter(1, mainWarehouse.getLocationId())
					.getSingleResult();
			
			TemplateDetails	templateDetails = null;
			
			try {
				Query templateQuery=em.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
				templateQuery.setParameter(1,true);
				templateQuery.setParameter(2,TemplateType.SMS);
				templateQuery.setParameter(3,new BusinessDomain(2));
				templateQuery.setParameter(4,"sor_initiate");

				templateDetails = (TemplateDetails) templateQuery.getSingleResult();
			}catch(NoResultException nex) {
				nex.printStackTrace();
			}catch (NonUniqueResultException e) {
				e.printStackTrace();
			}
			
			String smsMessage = null;
			if(templateDetails!=null) {
				smsMessage = templateDetails.getBody();
				smsMessage = smsMessage.replaceFirst("<var>", soReturnManualid);
				smsMessage = smsMessage.replaceFirst("<var>", sr.getName());
				smsMessage = smsMessage.replaceFirst("<var>", feedbackurl+"/#/serviceorder");
			}
			
			if(smsMessage!=null && !smsMessage.equalsIgnoreCase("")) {
				SmsNotifier sNotifier = new SmsNotifier(em, warehouse.getOwner().getContact().getMobile(),smsMessage, user.getUsername(),templateDetails.getTemplaeCode());
				sNotifier.sendNotification();
			}
		}catch(Exception ex) {
			ex.printStackTrace();	
		}
		
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	@Override
	public void receiveServiceOrderReturn(Set<Long> ids, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		
		ServiceRecource sr=null;
		
		try {
			sr=em.createQuery("select sr from ServiceRecource sr join sr.location loc where sr.user=?1",ServiceRecource.class).setParameter(1, emp).getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Service resource not found");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multipal resource found");
		}
		
		Date today=new Date();
	
		for(Long id:ids) {
			
			ServiceOrderItemReturn returnItem=null;
			
			Location warehouse=null;
			try {
				System.out.println("Id ==>"+id);
				returnItem=em.createQuery("select item from ServiceOrderItemReturn item join item.serviceOrderReturn sord join item.returnWarehouse loc where item=?1",ServiceOrderItemReturn.class)
						.setParameter(1, new ServiceOrderItemReturn(id))
						.getSingleResult();
			}
			catch(NoResultException ex) {
				ex.printStackTrace();
				throw new BadRequestException("Invalid Id");
			}
			catch(NonUniqueResultException ex) {
				System.out.println("Multiple");
				ex.printStackTrace();
				throw new BadRequestException("Invalid Id");
			}
			
			List<ServiceOrderItemReceive> _r=null;
			try {
				_r=em.createQuery("select r from ServiceOrderItemReceive r join r.item itm join itm.serviceOrder so join ServiceOrderItemReturn rt on r.item.id=rt.id where rt=?1",ServiceOrderItemReceive.class)
						.setParameter(1, returnItem)
						.getResultList();
			}catch(NoResultException ex) {
				
			}
			if(_r!=null&&_r.size()>0) {
				if(_r.get(0).getItem().getServiceOrder().getSoStatus().equalsIgnoreCase("Archived")) {
					throw new BadRequestException("Service order is Archived, Unable to change record");
				}
			}
			
			warehouse= returnItem.getReturnWarehouse();
			if(warehouse.getLocationId()!=sr.getLocation().getLocationId())
				throw new UnauthoriseException("You aer not authorise");
//			if(!warehouse.getOwner().getEmailId().equalsIgnoreCase(user.getUsername()))
//				throw new UnauthoriseException("You aer not authorise");
			
			ServiceOrderItemReceive _preReceive=null;
			try {
				
				_preReceive=em.createQuery("select litem from ServiceOrderItemReceive litem join litem.item item join item.serviceOrder sord  where litem=?1",ServiceOrderItemReceive.class)
						.setParameter(1, new ServiceOrderItemReceive(new ServiceOrderItem(id)))
						.getSingleResult();
				
			}catch(NoResultException ex) {
				throw new BadRequestException("Invalid Id");
			}catch(NonUniqueResultException ex) {
				throw new BadRequestException("Invalid Id");
			}
			
			//CHECK FOR STOCK ALREADY EXISTS OR NOT
			LocationProductLineItems _locationProductLineItems=null;
			
			try {
				_locationProductLineItems=em.createQuery("select litem from LocationProductLineItems litem join litem.locationProductItem item where litem.itemSerialNumber=?1 and item.product=?2",LocationProductLineItems.class)
						.setParameter(1,_preReceive.getItem().getSerialNumber())
						.setParameter(2,_preReceive.getItem().getProduct())
						.getSingleResult();
				throw new BadRequestException(String.format("Part code %s with serial number %s alrady in stock", _preReceive.getItem().getProduct().getProductPartCode(),_preReceive.getItem().getSerialNumber()));
			}catch(NoResultException ex) {
				
			}catch(NonUniqueResultException ex) {
				throw new BadRequestException(String.format("Multiple part code %s with serial number %s alrady in stock", _preReceive.getItem().getProduct().getProductPartCode(),_preReceive.getItem().getSerialNumber()));
			}
			
			// STOCK MANUPULATION
			
			LocationProductItem locationProductItem=null;
			
			try {
				
				locationProductItem=em.createQuery("select item from LocationProductItem item where item.product=?1 and item.location=?2",LocationProductItem.class)
						.setParameter(1, _preReceive.getItem().getProduct())
						.setParameter(2, warehouse)
						.getSingleResult();
			}
			catch(NoResultException ex) {
				locationProductItem=new LocationProductItem();
				locationProductItem.setBerQty(0);
				locationProductItem.setCreatedBy(emp);
				locationProductItem.setCreatedDate(today);
				locationProductItem.setCustomerInventory(0);
				locationProductItem.setFaultyQuantity(0);
				locationProductItem.setHslQuantity(0);
				locationProductItem.setHslStatus("L");
				locationProductItem.setIssuedQunatity(0);
				locationProductItem.setLocation(warehouse);
				locationProductItem.setModifiedBy(emp);
				locationProductItem.setModifiedDate(today);
				locationProductItem.setMslQuantity(0);
				locationProductItem.setMslQuantityStatus("L");
				locationProductItem.setOkQuantity(0);
				locationProductItem.setProduct(_preReceive.getItem().getProduct());
				locationProductItem.setQuantityInOnHand(0);
				locationProductItem.setQuantityUnitOfMasur("NO");
				locationProductItem.setRefurbished(0);
				locationProductItem.setRejectedQty(0);
				locationProductItem.setScrapQty(0);
				
				em.persist(locationProductItem);
				
				String lm=String.format("PI-%s", MiscellaneousUtils.fillZero(String.valueOf(locationProductItem.getId()), 5));
				
				locationProductItem.setLocationProductItemId(lm);
				
				em.persist(locationProductItem);
				
			}
			catch(NonUniqueResultException ex) {
				throw new BadRequestException("Multipal result");
			}
			
			long pstatus=returnItem.getState().getProductStateId();
			
			if(pstatus==1)
				locationProductItem.setOkQuantity(locationProductItem.getOkQuantity()+1);
			if(pstatus==2)
				locationProductItem.setFaultyQuantity(locationProductItem.getFaultyQuantity()+1);
			if(pstatus==3)
				locationProductItem.setScrapQty(locationProductItem.getScrapQty()+1);
			if(pstatus==4)
				locationProductItem.setRejectedQty(locationProductItem.getRejectedQty()+1);
			if(pstatus==5)
				locationProductItem.setBerQty(locationProductItem.getBerQty()+1);
			
			if(pstatus==1&&returnItem.isRefurbished())
				locationProductItem.setRefurbished(locationProductItem.getRefurbished()+1);
			if(returnItem.isCustomerInventory())
				locationProductItem.setCustomerInventory(locationProductItem.getCustomerInventory()+1);
			locationProductItem.setQuantityInOnHand(locationProductItem.getQuantityInOnHand()+1);
			
			em.persist(locationProductItem);
			
			_locationProductLineItems=new LocationProductLineItems();
			
			_locationProductLineItems.setCreatedBy(emp);
			_locationProductLineItems.setCreatedDate(today);
			
			if(returnItem.isCustomerInventory())
				_locationProductLineItems.setCustomerInventory(true);
			else
				_locationProductLineItems.setCustomerInventory(false);
			
			_locationProductLineItems.setCustomerName(returnItem.getCustomerName());
			_locationProductLineItems.setCustomerSapNumber(returnItem.getCustomerSapNumber());
			_locationProductLineItems.setInStock(true);
			_locationProductLineItems.setItemSerialNumber(_preReceive.getItem().getSerialNumber());
			_locationProductLineItems.setLocationProductItem(locationProductItem);
			
			//_locationProductLineItems.setLocationProductLineItemId(locationProductLineItemId);
			
			_locationProductLineItems.setLocationProductLineItemStatus(new ProductLineItemStatus(1));
			_locationProductLineItems.setLocationProeuctLineItemState(new ProductLineItemProductState(pstatus));
			_locationProductLineItems.setModifiedBy(emp);
			_locationProductLineItems.setModifiedDate(today);
			
			if(pstatus==1&&returnItem.isRefurbished())
				_locationProductLineItems.setRefurbished(true);
			else
				_locationProductLineItems.setRefurbished(false);
			_locationProductLineItems.setReplaced(false);
			_locationProductLineItems.setReturned(false);
			_locationProductLineItems.setRemarks(returnItem.getRemarks());
			_locationProductLineItems.setLotNo(returnItem.getLotNo());
			
			
			returnItem.setReceived(true);
			returnItem.setModifiedBy(emp);
			returnItem.setModifiedDate(today);
			
			em.persist(_locationProductLineItems);
			String _lManualId=String.format("PIL-%s",MiscellaneousUtils.fillZero(String.valueOf(_locationProductLineItems.getId()),5));
			_locationProductLineItems.setLocationProductLineItemId(_lManualId);
			em.persist(_locationProductLineItems);
			
		}
	}

	@Override
	public List<Map<String, Object>> advanceSearchSOR(Map<String, Object> request, LoginUser user,
			List<Long> childLocations) throws ParseException {
		
		StringBuilder queryStringBuilder=new StringBuilder();
		List<Object> value = new ArrayList<>();
		queryStringBuilder.append("SELECT \r\n" + 
				"sor.`id` AS `id`,\r\n" + 
				"sor.`manual_id` AS `manualId`,\r\n" + 
				"sor.`ship_to_name` AS `shipToName`,\r\n" + 
				"sor.`shipping_mode` AS `shippingMode`,\r\n" + 
				"sor.`tracking_number` AS `trackingNumber`,\r\n" + 
				"sor.`total_item` AS `returnedQty`,\r\n" + 
				"IFNULL(qty.`receivedQty`,0) AS `receivedQty`,\r\n" + 
				"(CASE\r\n" + 
				"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`=IFNULL(qty.`receivedQty`,0) THEN 'Received'\r\n" + 
				"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`>IFNULL(qty.`receivedQty`,0) THEN 'Partially Received'\r\n" + 
				"	WHEN IFNULL(qty.`receivedQty`,0)=0 THEN 'Pending to Receive'\r\n" + 
				"END) AS `status`,\r\n" + 
				"sor.`created_date` AS `createdDate`,\r\n" + 
				"sor.`modified_date` AS `modifiedDate`,\r\n" + 
				"sor.`created_by_email_id` AS `createdByEmailId`,\r\n" + 
				"CONCAT(CONCAT(cby.`first_name`,' '),IFNULL(CONCAT(cby.`middle_name`,' '),''),cby.`last_name`) AS `createdByName`,\r\n" + 
				"sor.`modified_by_email_id` AS `modifiedByEmailId`,\r\n" + 
				"CONCAT(CONCAT(mby.`first_name`,' '),IFNULL(CONCAT(mby.`middle_name`,' '),''),mby.`last_name`) AS `modifiedByName`,\r\n" + 
				"sor.`ownner_email_id` AS `ownerEmailId`,\r\n" + 
				"CONCAT(CONCAT(onr.`first_name`,' '),IFNULL(CONCAT(onr.`middle_name`,' '),''),onr.`last_name`) AS `ownerName`,\r\n" + 
				"sor.`destination_location_location_id` AS `sourceLocationId`,\r\n" + 
				"dl.`location_name` AS `destinationLocationName`,\r\n" + 
				"sor.`source_loccation_location_id` AS `destinationLocationId`,\r\n" + 
				"sl.`location_name` AS `sourceLocationName`\r\n" + 
				"FROM `service_order_return` sor\r\n" + 
				"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
				"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
				"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
				"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
				"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
				"LEFT JOIN \r\n" + 
				"(SELECT\r\n" + 
				"	COUNT(1) AS `receivedQty`,\r\n" + 
				"	item.`service_order_return_id`\r\n" + 
				"	FROM `service_order_item_return` item\r\n" + 
				"	WHERE item.`received`=TRUE\r\n" + 
				"	GROUP BY  item.`service_order_return_id`\r\n" + 
				") AS `qty`\r\n" + 
				"ON qty.`service_order_return_id`=sor.`id`\r\n"+
				"WHERE 1=1 ");
		
		
		if(request.get("manualId")!=null&&!request.get("manualId").equals("")) {
			queryStringBuilder.append(" and sor.`manual_id` like ?");
			value.add("%"+request.get("manualId")+"%");
		}
		if(request.get("sourceLocation")!=null&&!request.get("sourceLocation").equals("")) {
			queryStringBuilder.append(" and sl.`location_name` like ?");
			value.add("%"+request.get("sourceLocation")+"%");
		}
			
		if(request.get("destinationLocation")!=null&&!request.get("destinationLocation").equals("")) {
			queryStringBuilder.append(" and dl.`location_name` like ?");
			value.add("%"+request.get("destinationLocation")+"%");
		}
			
		if(request.get("status")!=null&&!request.get("status").equals("")) {
			if(request.get("status").toString().equalsIgnoreCase("Received")) {
				queryStringBuilder.append(" and sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`=IFNULL(qty.`receivedQty`,0)");	
			}
			
			if(request.get("status").toString().equalsIgnoreCase("Partially Received")) {
				queryStringBuilder.append(" and sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`>IFNULL(qty.`receivedQty`,0)");
			}
			
			if(request.get("status").toString().equalsIgnoreCase("Pending to Receive")) {
				queryStringBuilder.append(" and IFNULL(qty.`receivedQty`,0)=0");
			}
		}
			
		if(childLocations!=null) {
			queryStringBuilder.append(" and (sl.`location_id` in ");
			
			StringBuilder locations=new StringBuilder();
			int i=0;
			for(Long loc:childLocations) {
				if(i!=0)
					locations.append(",");
				locations.append("'"+loc+"'");
				i++;
			}
			queryStringBuilder.append("("+locations.toString()+")");
			
			queryStringBuilder.append(" or dl.`location_id` in ");
			
			queryStringBuilder.append("("+locations.toString()+"))");
		}
					
		return jdbc.queryForList(queryStringBuilder.toString(),value.toArray());
	}

	@Override
	public List<Map<String, Object>> advanceSearchSORItems(long serviceOrderReturnId, Map<String, Object> request)
			throws ParseException {
		
		StringBuilder queryStringBuilder=new StringBuilder();
		List<Object> value = new ArrayList<>();
		queryStringBuilder.append("SELECT \r\n" + 
				"	itemreturn.`id` AS reurnItemId,\r\n" + 
				"	itemreturn.`service_order_return_id` AS returnIdServiceOrder,\r\n" + 
				"	itemreturn.`state_product_state_id` AS productFinalStateId,\r\n" + 
				"	item.`circle` AS circleName,\r\n" + 
				"	item.`customer_inventory` AS isCustomerInventory,\r\n" + 
				"	item.`customer_name` AS customerName,\r\n" + 
				"	item.`customer_sap` AS customerSAP,\r\n" + 
				"	item.`product_product_part_code` AS productPartCode,\r\n" +
				"	prd.`pdoduct_description` AS productDescription,\r\n"+
				"	item.`serial_number` AS itemSerialNumber,\r\n" + 
				"	item.`service_order_id` AS serviceOrderId,\r\n" + 
				"	item.`state_product_state_id` AS previousStatteId,\r\n" +
				"	item.`received` AS received,\r\n"+
				"	stfinal.`product_state_name` AS finalProductStateName,\r\n" + 
				"	prestate.`product_state_name` AS previousProductFinalStateName,\r\n" + 
				"	sord.manual_id as serviceOrderManualId,\r\n"+
				"	item.`remark` AS `remarks`,\r\n"+
				"	item.`lot_no` AS `lotNo`\r\n"+
				"	\r\n" + 
				"	FROM `service_order_item_return` itemreturn\r\n" + 
				"	JOIN `service_order_item_receive` itemreceive ON itemreturn.`id`=itemreceive.`item_id`\r\n" + 
				"	JOIN `service_order_item` item ON item.`id`=itemreceive.`item_id`\r\n" + 
				"	JOIN `service_order` sord ON item.`service_order_id`=sord.`id`\r\n" + 
				"	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n"+
				"	JOIN `product_line_item_product_state` stfinal ON stfinal.`product_state_id`=itemreturn.`state_product_state_id`\r\n" + 
				"	JOIN `product_line_item_product_state` prestate ON prestate.`product_state_id`=item.`state_product_state_id`\r\n" + 
				"	WHERE itemreturn.`service_order_return_id`=?");
		
		value.add(serviceOrderReturnId);
		
		if(request.get("serialNumber")!=null&&!request.get("serialNumber").equals("")) {
			queryStringBuilder.append(" and item.`serial_number` like ?");
			value.add("%"+request.get("serialNumber")+"%");
		}
		
		if(request.get("productPartcode")!=null&&!request.get("productPartcode").equals("")) {
			queryStringBuilder.append(" and item.`product_product_part_code` like ?");
			value.add("%"+request.get("productPartcode")+"%");
		}
		
		if(request.get("productDescription")!=null&&!request.get("productDescription").equals("")) {
			queryStringBuilder.append(" and prd.`pdoduct_description` like ?");
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
