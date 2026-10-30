package com.pratikshat.exicom.repository;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.ApplicationNotificationDetails;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LProductReturnOrderItem;
import com.pratikshat.exicom.entity.LegalEntity;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LocationProductItem;
import com.pratikshat.exicom.entity.LocationProductLineItems;
import com.pratikshat.exicom.entity.LocationType;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.ObjectLog;
import com.pratikshat.exicom.entity.PartCodechangeApproval;
import com.pratikshat.exicom.entity.PartcodeChangeRequest;
import com.pratikshat.exicom.entity.PartcodeChangeRequestApproval;
import com.pratikshat.exicom.entity.ProductLineItem;
import com.pratikshat.exicom.entity.ProductLineItemProductState;
import com.pratikshat.exicom.entity.ProductLineItemStatus;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.LPRequest;
import com.pratikshat.exicom.model.request.LPReturnOrderRequest;
import com.pratikshat.exicom.model.request.LocationRequest;
import com.pratikshat.exicom.model.response.LocationProductItemResponse;
import com.pratikshat.exicom.model.response.LocationProductRequestResponse;
import com.pratikshat.exicom.model.response.LocationResponse;

@Repository
public class LocationRepositoryCustomeImpl implements LocationRepositoryCustome {

	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	SimpMessagingTemplate msgTemplate;
	
	@Autowired
	ProductLineItemRepository dbProductLineItem;
	
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	@Autowired JdbcTemplate jdbc;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public LocationRequest saveLocation(LocationRequest location, LoginUser user, int businessDomainId) throws Exception {
		
		if(location.getLocationTypeId()!=4&&location.getParentLocationId()==0) {
			throw new Exception("Please provide parent Warehouse");
		}
		if(location.getLocationTypeId()==4&&location.getParentLocationId()!=0) {
			throw new Exception("Plant can not have Parent Warehouse/Plant");
		}
		if((location.getLocationTypeId()==4||location.getLocationTypeId()==1)&&(location.getPlant()==null||location.getPlant().equalsIgnoreCase(""))) {
			throw new Exception("Please provide Plant(Fectory)/Warehouse code");
		}
		
		if(location.getLocationTypeId()==0) {
			throw new Exception("Please provide warehouse type");
		}
		if(location.getLocationTypeId()==4||location.getLocationTypeId()==1) {
			Location checkloc=null;
			try {
				checkloc=entityManager.createQuery("select loc from Location loc where loc.plant=?1",Location.class)
						.setParameter(1, location.getPlant().trim())
						.getSingleResult();
				throw new Exception(String.format("Plant/Warehouse code %s already in use",location.getPlant()));
			}catch(NoResultException ex) {
				
			}
		}
		
		Location parentLocation=null;
		try {
			parentLocation=entityManager.find(Location.class, location.getParentLocationId());
			
		}catch(NoResultException ex) {
			if(location.getLocationTypeId()!=4) {
				throw new Exception("Parent Location not found");
			}
			
		}
		if(parentLocation!=null) {
			if(parentLocation.getLocationType()!=null&&(parentLocation.getLocationType().getLocationTypeId()==3||parentLocation.getLocationType().getLocationTypeId()==5)) {
				
				throw new Exception("You can not create any type of warehouse under Site Engineer or Repair Engineer");
			}
		}
		
		//START - CODE NOT APPLYIED OTHER THEN EXICOM, THIS IS BASED ON EXICOM-SAP TEAM IT IS KIND OF "JUGAR"
		if(location.getLocationTypeId()!=3&&location.getLocationTypeId()!=5) {
			
			throw new Exception("You can not create warehouse other then Site Engineer and Repair Engineer");
		}
		//END
		
		Location locationCheck=null;
		try {
			locationCheck=entityManager.createQuery("select loc from Location loc where loc.locationName=?1",Location.class)
					.setParameter(1, location.getLocationName().trim())
					.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		
		if(locationCheck!=null) {
			throw new Exception("Warehouse already exist with this name");
		}
		
		
		com.pratikshat.exicom.entity.Location _location=new com.pratikshat.exicom.entity.Location();
		_location.setInventoryLocation(location.isInventoryLocation());
		_location.setLegalEntity(new LegalEntity(location.getLegalEntityId()));
		_location.setLocationName(location.getLocationName());
		_location.setLocationType(new LocationType(location.getLocationTypeId()));
		_location.setMobileLocation(location.isMobileLocation());
		//_location.setOwner(new EmployeeDetails(user.getUsername()));
		_location.setParentLocation(parentLocation);
		_location.setCreatedBy(new EmployeeDetails(user.getUsername()));
		_location.setCreatedDate(new Date());
		_location.setModifiedBy(new EmployeeDetails(user.getUsername()));
		_location.setModifiedDate(new Date());
		_location.setPlant(location.getPlant()!=null?location.getPlant().trim():null);
		_location.setBusinessDomain(new BusinessDomain(businessDomainId));
		entityManager.persist(_location);
		location.setLocationId(_location.getLocationId());
		
		//Global Search Entry Point
		try {
			GlobalSearchIndex _search=new GlobalSearchIndex();
			_search.setSearchString(_location.getLocationName());
			_search.setKeyName("locationName");
			_search.setObjectName("Location");
			_search.setObjectDetailsUrl("location/"+_location.getLocationId());
			_search.setObjectIdName("locationId");
			_search.setObjectIdValue(String.valueOf(_location.getLocationId()));
			
			entityManager.persist(_search);
			
		}catch(Exception e) {}
		
		return location;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public LPRequest saveProductRequest(LPRequest lprRequest, LoginUser user)throws com.pratikshat.exicom.exception.BadRequestException,com.pratikshat.exicom.exception.UnauthoriseException {
		ServiceRecource sr=entityManager.createQuery("select sr from ServiceRecource sr where sr.user=?1",ServiceRecource.class).setParameter(1,new EmployeeDetails(user.getUsername())).getSingleResult();
		com.pratikshat.exicom.entity.Location _lc=null;
		
		if(lprRequest.getSourceLocationId()==lprRequest.getDestinationLocationId())
			throw new BadRequestException("Source location can not be destination location");
		
		try {
			_lc=entityManager.createQuery("select loc from Location loc join loc.parentLocation pl join loc.owner own where loc.locationId=?1",com.pratikshat.exicom.entity.Location.class)
					.setParameter(1,lprRequest.getDestinationLocationId())
					.getSingleResult();
			if(sr.getLocation().getLocationId()!=_lc.getLocationId())
				throw new com.pratikshat.exicom.exception.UnauthoriseException("You are not authorised to create product request");
//			if(_lc.getOwner()!=null) {
//				if(!_lc.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
//					throw new com.pratikshat.exicom.exception.UnauthoriseException("You are not authorised to create product request");
//				}
//			}
		}catch(javax.persistence.NoResultException ex) {
			
			throw new com.pratikshat.exicom.exception.BadRequestException("Destination Warehouse not found");
		}
		com.pratikshat.exicom.entity.Location sourceLocation=null;
		try {
			sourceLocation=entityManager.createQuery("select loc from Location loc left join loc.parentLocation pl join loc.owner own where loc.locationId=?1",com.pratikshat.exicom.entity.Location.class)
					.setParameter(1, lprRequest.getSourceLocationId())
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Source Warehouse not found");
		}
		
		
		if(_lc.getParentLocation().getLocationId()!=sourceLocation.getLocationId()) {
			if(sourceLocation.getParentLocation().getLocationId()!=_lc.getLocationId())
				if(_lc.getParentLocation().getLocationId()!=sourceLocation.getParentLocation().getLocationId())
					throw new com.pratikshat.exicom.exception.UnauthoriseException("You are not authorised to send product request to selected source warehouse");
		}
		
		
		com.pratikshat.exicom.entity.LProductRequest _request=new com.pratikshat.exicom.entity.LProductRequest();
		
		_request.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_request.setCreatedDate(new java.util.Date());
		_request.setDescription(lprRequest.getDescirption());
		_request.setLocationProductRequestStatus("Draft");
		_request.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_request.setModifiedDate(new java.util.Date());
		_request.setNeedByDateTime(lprRequest.getNeedByDateTime());
		_request.setOwnerDetails(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_request.setSourceLocation(new com.pratikshat.exicom.entity.Location(lprRequest.getSourceLocationId()));
		_request.setDestinationLocation(new com.pratikshat.exicom.entity.Location(lprRequest.getDestinationLocationId()));		
		_request.setTotalQuantityIssued(0);
		_request.setTotalQuantityReceived(0);
		_request.setTotalQuantityRequired(0);
		_request.setSynked(false);
		entityManager.persist(_request);
		float totalQuantity=0;
		if(lprRequest.getLileItems()!=null&&lprRequest.getLileItems().size()>0) {
			for(com.pratikshat.exicom.model.request.LPItemRequest item:lprRequest.getLileItems()) {
				if(item.getRequestQuantity()<=0)
					throw new BadRequestException("Please provide valid quantity");
				if(item.getRequestQuantity()>99999)
					throw new BadRequestException("Please put quantity between 1 to 99999");//24-09-2020 by Manpreet Madam
				com.pratikshat.exicom.entity.LProductRequestLineItem _item=new com.pratikshat.exicom.entity.LProductRequestLineItem();
				_item.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
				_item.setCreatedDate(new java.util.Date());
				_item.setLocationProductRequest(new com.pratikshat.exicom.entity.LProductRequest(_request.getLocationProductRequestId()));
				_item.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
				_item.setModifiedDate(new java.util.Date());
				_item.setProduct(new com.pratikshat.exicom.entity.Products(item.getProductPartCode()));
				_item.setQuantity(item.getRequestQuantity());
				_item.setQuantityReceived(0);
				_item.setQuantityIssued(0);
				totalQuantity+=item.getRequestQuantity();
				entityManager.persist(_item);
				// javax.persistence.Query updateItemManualIdQuery=entityManager.createQuery("update LProductRequestLineItem it set it.locationProductRequestLineItemManualId=?1 where it.locationProductRequestLineItemId=?2");
				// updateItemManualIdQuery.setParameter(1,);
				// updateItemManualIdQuery.setParameter(2, _item.getLocationProductRequestLineItemId());
				// updateItemManualIdQuery.executeUpdate();

				_item.setLocationProductRequestLineItemManualId( String.format("PRLI-%s",com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_item.getLocationProductRequestLineItemId()),5)));
				entityManager.persist(_item);

				item.setId(_item.getLocationProductRequestLineItemId());
				try {
					com.pratikshat.exicom.entity.GlobalSearchIndex _global=new com.pratikshat.exicom.entity.GlobalSearchIndex();
					_global.setKeyName("locationProductRequestLineItemManualId");
					_global.setObjectDetailsUrl(String.format("productrequestlineitem/%s", _item.getLocationProductRequestLineItemId()));
					_global.setSearchString(String.format("PRLI-%s",com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_item.getLocationProductRequestLineItemId()),5)));
					_global.setObjectIdName("locationProductRequestLineItemId");
					_global.setObjectIdValue(""+_item.getLocationProductRequestLineItemId());
					_global.setObjectName("LProductRequestLineItem");
					entityManager.persist(_global);
					
				}catch(Exception ex) {}
				
			}
		}
		// javax.persistence.Query updateManualIdLpr=entityManager.createQuery("update LProductRequest r set r.locationProductRequestManualId=?1,r.totalQuantityRequired=?2 where r.locationProductRequestId=?3");
		// updateManualIdLpr.setParameter(1,);
		// updateManualIdLpr.setParameter(2, totalQuantity);
		// updateManualIdLpr.setParameter(3, _request.getLocationProductRequestId());
		// updateManualIdLpr.executeUpdate();

		_request.setLocationProductRequestManualId(String.format("PR-%s",com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_request.getLocationProductRequestId()),5)));
		_request.setTotalQuantityRequired(totalQuantity);
		entityManager.persist(_request);

		lprRequest.setId(_request.getLocationProductRequestId());
		lprRequest.setManualId(String.format("PR-%s",com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_request.getLocationProductRequestId()),5)));
		try {
			com.pratikshat.exicom.entity.GlobalSearchIndex _global=new com.pratikshat.exicom.entity.GlobalSearchIndex();
			_global.setKeyName("locationProductRequestManualId");
			_global.setObjectDetailsUrl(String.format("productrequest/%s", _request.getLocationProductRequestId()));
			_global.setSearchString(String.format("PR-%s",com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_request.getLocationProductRequestId()),5)));
			_global.setObjectIdName("locationProductRequestId");
			_global.setObjectIdValue(""+_request.getLocationProductRequestId());
			_global.setObjectName("LProductRequest");
			entityManager.persist(_global);
			
		}catch(Exception ex) {}
		
		return lprRequest;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.SERIALIZABLE,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public LPReturnOrderRequest saveReturnOrder(LPReturnOrderRequest returnOrder, LoginUser user)throws BadRequestException,UnauthoriseException {
		ServiceRecource sr=entityManager.createQuery("select sr from ServiceRecource sr where sr.user=?1",ServiceRecource.class).setParameter(1,new EmployeeDetails(user.getUsername())).getSingleResult();
		if(sr.getLocation().getLocationId()!=returnOrder.getSourceLocationId())
			throw new UnauthoriseException("You are not authorised to make return order from this location");
		String roleName="";
		
		if(returnOrder.getDestinationLocationId()==returnOrder.getSourceLocationId())
			throw new BadRequestException("Source location can not be destination location");
		
		Iterator<LoginUserRoles> ite=(Iterator<LoginUserRoles>) user.getAuthorities().iterator();
		while(ite.hasNext()) {
			LoginUserRoles rl=ite.next();
			roleName= rl.getAuthority();
		}
		
		String rlGroupName=entityManager.createQuery("select rlg.groupName from UserRoles rl join rl.roleGroup rlg where rl.authorities=?1",String.class).setParameter(1, roleName).getSingleResult();
		
		com.pratikshat.exicom.entity.LProductReturnOrder _returnOrder=new com.pratikshat.exicom.entity.LProductReturnOrder();
		_returnOrder.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_returnOrder.setCreatedDate(new java.util.Date());
		_returnOrder.setDestinationLocation(new com.pratikshat.exicom.entity.Location(returnOrder.getDestinationLocationId()));
		_returnOrder.setExpectedArrivalDate(returnOrder.getExpectedArrivalDate());
		_returnOrder.setLocationReturnOrderStatus("Draft");
		_returnOrder.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_returnOrder.setModifiedDate(new java.util.Date());
		_returnOrder.setOwner(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_returnOrder.setShippingMode(returnOrder.getShippingMode());
		_returnOrder.setShipToName(returnOrder.getShipToName());
		_returnOrder.setSourceLocation(new com.pratikshat.exicom.entity.Location(returnOrder.getSourceLocationId()));
		_returnOrder.setTotalQuantityReturned(0);
		_returnOrder.setTotalQuantityReceived(0);
		_returnOrder.setTrackingNumber(returnOrder.getTrackingNumber());
		entityManager.persist(_returnOrder);
		float totalReturned=0;
		
		if(returnOrder.getItems()!=null&&returnOrder.getItems().size()>0) {
			for(com.pratikshat.exicom.model.request.LPReturnOrderItemRequest item:returnOrder.getItems()) {
				float itemTotalReturned=0;
				float itemTotalFaulty=0;
				float itemTotalOk=0;
				float itemTotalScrap=0;
				float itemTotalRejected=0;
				float itemTotalBER=0;
				float itemTotalDoa=0;
				float itemCustomerInventory=0;
				float _up=0;
				com.pratikshat.exicom.entity.LProductReturnOrderItem _item=null;//
				try {
					_item=entityManager.createQuery("select item from LProductReturnOrderItem item where item.locationProductReturnOrder=?1 and item.product=?2",LProductReturnOrderItem.class)
							.setParameter(1, _returnOrder)
							.setParameter(2, new com.pratikshat.exicom.entity.Products(item.getProductPartCode()))
							.getSingleResult();
				}catch(NoResultException ex) {
					_item=new com.pratikshat.exicom.entity.LProductReturnOrderItem();
					_item.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
					_item.setCreatedDate(new java.util.Date());
					_item.setLocationProductReturnOrder(new com.pratikshat.exicom.entity.LProductReturnOrder(_returnOrder.getLprOrderid()));
					_item.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
					_item.setModifiedDate(new java.util.Date());
					_item.setOwner(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
					_item.setProduct(new com.pratikshat.exicom.entity.Products(item.getProductPartCode()));
					_item.setProductItem(new com.pratikshat.exicom.entity.LocationProductItem(item.getProductItemId()));
					_item.setReturnDate(item.getReturnDate());
					_item.setTotalReceiveQuantity(0);
					_item.setTotalReturnQuantity(0);
					
					entityManager.persist(_item);
				}
				//itemTotalReturned=_item.getTotalReturnQuantity();
				_up=_item.getTotalReturnQuantity();
				
				if(item.getLineItems()!=null&&item.getLineItems().size()>0) {
					for(com.pratikshat.exicom.model.request.LPReturnOrderLineItemRequest lineItem:item.getLineItems()) {
						
						//CHECK FOR CHANGE PART CODE PENDING APPROVAL FOR NON WORKFLOW
						PartcodeChangeRequest chRequest=null;
						try {
							
							chRequest=entityManager.createQuery("select item from PartcodeChangeRequest item where item.faultyProduct=?1 and item.faultyProductSerialNumber=?2 and item.flowType=?3 and item.approvedStatus=?4",PartcodeChangeRequest.class)
									.setParameter(1, new com.pratikshat.exicom.entity.Products(item.getProductPartCode()))
									.setParameter(2,lineItem.getProductLineItemSerialNumber())
									.setParameter(3, PartcodeChangeRequestRepository.FLOW_TYPE_NON_SA)
									.setParameter(4, false)
									.getSingleResult();
							throw new BadRequestException("Can not move inventory of part code "+item.getProductPartCode()+" with serial number "+lineItem.getProductLineItemSerialNumber()+" due to pending approval");
						}catch(NoResultException ex) {
							
							
						}catch(NonUniqueResultException ex) {
							throw new BadRequestException("Can not move inventory of part code "+item.getProductPartCode()+" with serial number "+lineItem.getProductLineItemSerialNumber()+" due to multiple pending approval");
						}
						
						
						//CHECK FOR PRODUCT SERIAL NUMBER EXISTS OR NOT
						LocationProductLineItems eLineItem=null;
						try {
							eLineItem=entityManager.createQuery("select lineItem from LocationProductLineItems lineItem join lineItem.locationProductItem item where item.product=?1 and lineItem.itemSerialNumber=?2",LocationProductLineItems.class)
									.setParameter(1, new Products(item.getProductPartCode()))
									.setParameter(2,lineItem.getProductLineItemSerialNumber().trim())
									.getSingleResult();
						}catch(NoResultException ex) {
							throw new BadRequestException(String.format("Invalid serial number %s for partcode %s",lineItem.getProductLineItemSerialNumber().trim(),item.getProductPartCode()));
						}catch(NonUniqueResultException ex) {
							throw new BadRequestException(String.format("Multiple serial number %s for partocde %s found",lineItem.getProductLineItemSerialNumber().trim(),item.getProductPartCode()));
						}
						if(eLineItem!=null) {
							
							if(eLineItem.getLocationProeuctLineItemState().getProductStateId()==6&&!rlGroupName.equalsIgnoreCase("FIELD_SERVICE_ENGINEER"))
								throw new BadRequestException("Can not create return order with DOA status");
							
							//CHECK PRODUCT LINE ITEM EXIST IN SOURCE LOCATION
							if(eLineItem.getLocationProductItem().getLocation().getLocationId()!=returnOrder.getSourceLocationId()) 
								throw new BadRequestException(String.format("Serial number %s for partcode %s does not exist in your stock",lineItem.getProductLineItemSerialNumber().trim(),item.getProductPartCode()));
							
							//CHECK PRODUCT LIEN ITEM STATUS FOR RETURN ORDER
							if(eLineItem.getLocationProeuctLineItemState().getProductStateId()==1)
								throw new BadRequestException(String.format("Can not create return order for serial number %s of part code %s as item is not faulty", lineItem.getProductLineItemSerialNumber().trim(),item.getProductPartCode()));
						}else
							throw new BadRequestException(String.format("Invalid serial number %s for partcode %s",lineItem.getProductLineItemSerialNumber().trim(),item.getProductPartCode()));
						
						com.pratikshat.exicom.entity.LProductReturnOrderLineItem _lineItem=new com.pratikshat.exicom.entity.LProductReturnOrderLineItem();
						_lineItem.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
						_lineItem.setCreatedDate(new java.util.Date());
						_lineItem.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
						_lineItem.setModifiedDate(new java.util.Date());
						_lineItem.setProductLineItemSerialNumber(lineItem.getProductLineItemSerialNumber());
						_lineItem.setQuantityReceived(0);
						_lineItem.setQuantityReturned(lineItem.getQuantityReturned()==0?1:lineItem.getQuantityReturned());
						_lineItem.setReasonForReturn("");
						_lineItem.setReturnOrderItem(new com.pratikshat.exicom.entity.LProductReturnOrderItem(_item.getLproItemid()));
						
						_lineItem.setCustomerInventory(eLineItem.isCustomerInventory()); /*IN CASE OF CUSTOMER INVENTORY MOVEMENT FLAG SHULD BE UP*/
						_lineItem.setCustomerName(eLineItem.getCustomerName());
						_lineItem.setCustomerSapNumber(eLineItem.getCustomerSapNumber());
						long stateId=eLineItem.getLocationProeuctLineItemState().getProductStateId();
						_lineItem.setLineItemState(stateId==0?2:eLineItem.getLocationProeuctLineItemState().getProductStateId());
						_lineItem.setRemarks(eLineItem.getRemarks());
						_lineItem.setReturnStatus("Submitted");
					
						entityManager.persist(_lineItem);
						
						ProductLineItem pLineItem=dbProductLineItem.findByProductAndSerialNumber(_item.getProduct(), lineItem.getProductLineItemSerialNumber());
						if(pLineItem!=null) {
							
							pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
							pLineItem.setModifiedDate(new Date());
							pLineItem.setPosition("RO,"+_returnOrder.getLprOrderid());
							pLineItem.setPositionState(ProductLineItemRepository.IN_TRANSIT);
							pLineItem.setState(eLineItem.getLocationProeuctLineItemState());
							pLineItem.setStatus(new ProductLineItemStatus(2));
							dbProductLineItem.save(pLineItem);
						}
						else {
							pLineItem=new ProductLineItem();
							pLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
							pLineItem.setCreatedDate(new Date());
							pLineItem.setProduct(_item.getProduct());
							pLineItem.setSerialNumber(lineItem.getProductLineItemSerialNumber());
							pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
							pLineItem.setModifiedDate(new Date());
							pLineItem.setPosition("RO,"+_returnOrder.getLprOrderid());
							pLineItem.setPositionState(ProductLineItemRepository.IN_TRANSIT);
							pLineItem.setState(eLineItem.getLocationProeuctLineItemState());
							pLineItem.setStatus(new ProductLineItemStatus(2));
							dbProductLineItem.save(pLineItem);
						}
						
						
						//removing serial number from source location;
						entityManager.createQuery("delete from LocationProductLineItems litem where litem.itemSerialNumber=?1 and litem.locationProductItem.id=?2")
							.setParameter(1, _lineItem.getProductLineItemSerialNumber())
							.setParameter(2, item.getProductItemId())
							.executeUpdate();
						
						itemTotalReturned+=1;
						if(stateId==1)
							itemTotalOk=itemTotalOk+1;
						if(stateId==2)
							itemTotalFaulty=itemTotalFaulty+1;
						if(stateId==3)
							itemTotalScrap=itemTotalScrap+1;
						if(stateId==4)
							itemTotalRejected=itemTotalRejected+1;
						if(stateId==5)
							itemTotalBER=itemTotalBER+1;
						if(stateId==6)
							itemTotalDoa=itemTotalDoa+1;
						_up++;
						
						if(eLineItem.isCustomerInventory())
							itemCustomerInventory=itemCustomerInventory+1;

							_item.setTotalReturnQuantity((_item.getTotalReturnQuantity()+1));
							_item.setLproItemManualId(String.format("ROI-%s", com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_item.getLproItemid()),5)));
							entityManager.persist(_item);
						
						//lineItem.getQuantityReturned()==0?1:lineItem.getQuantityReturned();
						// javax.persistence.Query _lineItemUpdateQuery=entityManager.createQuery("update LProductReturnOrderLineItem litem set litem.lproLineitemManualId=?1 where litem.lproLineitemId=?2");
						// _lineItemUpdateQuery.setParameter(1,);
						// _lineItemUpdateQuery.setParameter(2, _lineItem.getLproLineitemId());
						// _lineItemUpdateQuery.executeUpdate();

						_lineItem.setLproLineitemManualId(String.format("ROLI-%s",com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_lineItem.getLproLineitemId()),5)));
						entityManager.persist(_lineItem);

						lineItem.setLproLineitemId(_lineItem.getLproLineitemId());
					}
				}
				javax.persistence.Query productTypeQuery=entityManager.createQuery("select pr.productType from Products pr where pr.productPartCode=?1",String.class);
				productTypeQuery.setParameter(1, item.getProductPartCode());
				String productType=(String)productTypeQuery.getSingleResult();
				if(productType.equalsIgnoreCase("Component")) {
					itemTotalReturned=item.getTotalReturnQuantity();
					_up=item.getTotalReturnQuantity();
				}
				totalReturned+=itemTotalReturned;
				// javax.persistence.Query updateItemQuery=entityManager.createQuery("update LProductReturnOrderItem item set item.lproItemManualId=?1,item.totalReturnQuantity=?2 where item.lproItemid=?3");
				// updateItemQuery.setParameter(1,);
				// updateItemQuery.setParameter(2, _up);
				// updateItemQuery.setParameter(3,_item.getLproItemid());
				// updateItemQuery.executeUpdate();
				


				item.setReturnOrderItemId(_item.getLproItemid());
				//update LocationItem Quantity
				com.pratikshat.exicom.entity.LocationProductItem locationProductItem=null;
				try {
					locationProductItem=entityManager.createQuery("select litem from LocationProductItem litem join litem.location loc where litem.id=?1 and loc.locationId=?2",com.pratikshat.exicom.entity.LocationProductItem.class)
								.setParameter(1, item.getProductItemId())
								.setParameter(2, returnOrder.getSourceLocationId())
								.getSingleResult();
				}catch(javax.persistence.NoResultException ex) {
					throw new BadRequestException("Item not found at location");
				}
				
				locationProductItem.setOkQuantity(locationProductItem.getOkQuantity()-itemTotalOk);
				locationProductItem.setFaultyQuantity(locationProductItem.getFaultyQuantity()-itemTotalFaulty);
				locationProductItem.setScrapQty(locationProductItem.getScrapQty()-itemTotalScrap);
				locationProductItem.setRejectedQty(locationProductItem.getRejectedQty()-itemTotalRejected);
				locationProductItem.setBerQty(locationProductItem.getBerQty()- itemTotalBER);
				locationProductItem.setQuantityInOnHand(locationProductItem.getQuantityInOnHand()- itemTotalReturned);
				locationProductItem.setDoaQuantity(locationProductItem.getDoaQuantity()-itemTotalDoa);
				locationProductItem.setCustomerInventory(locationProductItem.getCustomerInventory()- itemCustomerInventory);
				locationProductItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				locationProductItem.setModifiedDate(new Date());
				entityManager.persist(locationProductItem);
				
			}
		}
		// javax.persistence.Query returnUpdateQuery=entityManager.createQuery("update LProductReturnOrder rt set rt.lprOrderManualId=?1,rt.totalQuantityReturned=?2 where rt.lprOrderid=?3");
		
		// returnUpdateQuery.setParameter(1,mnId);
		// returnUpdateQuery.setParameter(2, totalReturned);
		// returnUpdateQuery.setParameter(3, _returnOrder.getLprOrderid());
		// returnUpdateQuery.executeUpdate();
		String mnId=String.format("RO-%s",com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_returnOrder.getLprOrderid()), 5));
		_returnOrder.setLprOrderManualId(mnId);
		_returnOrder.setTotalQuantityReturned(totalReturned);
		entityManager.persist(_returnOrder);

		returnOrder.setLprOrderid(_returnOrder.getLprOrderid());
		returnOrder.setLprOrderidManual(mnId);
		try {
			com.pratikshat.exicom.entity.GlobalSearchIndex _global=new com.pratikshat.exicom.entity.GlobalSearchIndex();
			_global.setKeyName("lprOrderManualId");
			_global.setObjectDetailsUrl(String.format("productreturnorder/%s", _returnOrder.getLprOrderid()));
			_global.setSearchString(String.format("RO-%s",com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf( _returnOrder.getLprOrderid()),5)));
			_global.setObjectIdName("lprOrderid");
			_global.setObjectIdValue(""+ _returnOrder.getLprOrderid());
			_global.setObjectName("LProductReturnOrder");
			entityManager.persist(_global);
			
		}catch(Exception ex) {
			
		}
		try {
			String notificationEmail=entityManager.createQuery("select ownr.emailId from Location loc join loc.owner ownr where loc.locationId=?1",String.class).setParameter(1, returnOrder.getDestinationLocationId()).getSingleResult();
			com.pratikshat.exicom.entity.ApplicationNotificationDetails _notification=new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
			_notification.setNofificationDate(new java.util.Date());
			_notification.setNotificationActionUrl(String.format("productreturnorder/%s", _returnOrder.getLprOrderid()));
			_notification.setNotificationMessage(String.format("Product Return Order with Id %s has been created for you", String.format("RO-%s",com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf( _returnOrder.getLprOrderid()),5))));
			_notification.setNotificationRead(false);
			_notification.setUserEmailId(notificationEmail);
			entityManager.persist(_notification);
			try {
				msgTemplate.convertAndSend("/topic/"+notificationEmail,_notification);
			}catch(Exception ex1) {}
		}catch(Exception ex) {
			
		}
		return returnOrder;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public void changeOwner(List<Long> locationIds, String ownerEmailId, LoginUser user, int businessDomainId) {
		for(long ids:locationIds) {
			Location loc=entityManager.createQuery("select loc from Location loc where loc=?1",Location.class).setParameter(1, new Location(ids)).getSingleResult();
			ServiceRecource sr=entityManager.createQuery("select sr from ServiceRecource sr where sr.user=?1",ServiceRecource.class).setParameter(1, new EmployeeDetails(ownerEmailId)).getSingleResult();
			
			loc.setOwner(new EmployeeDetails(ownerEmailId));
			loc.setModifiedBy(new EmployeeDetails(user.getUsername()));
			loc.setModifiedDate(new Date());
			
			sr.setLocation(loc);
			sr.setModifiedBy(new EmployeeDetails(user.getUsername()));
			sr.setModifiedDate(new Date());
			
			entityManager.persist(loc);
			entityManager.persist(sr);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public List<Map<String, Object>> customeProductRequest(List<Map<String, Object>> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(request!=null&&request.size()>0) {
			for(Map<String,Object> req:request) {
				
				float qtycheck=Float.parseFloat(req.get("reorderQuantity").toString());
				if(qtycheck<=0)
					throw new BadRequestException("Quantity can not be less or equal to 0");
				if(qtycheck>9999)
					throw new BadRequestException("Quantity can not be greater then 9999");
				com.pratikshat.exicom.entity.Location location=entityManager.createQuery("select loc from Location loc left join loc.parentLocation ploc left join ploc.owner pownr join loc.owner onr where loc.locationId=?1",com.pratikshat.exicom.entity.Location.class).setParameter(1,Long.parseLong(req.get("locationId").toString())).getSingleResult();
				if(location.getLocationType().getLocationTypeId()==4) {
					throw new BadRequestException("Plant can not request for product");
				}
				
				
				List<com.pratikshat.exicom.entity.LProductRequest> existingRequest= entityManager.createQuery("select req from LProductRequest req where req.sourceLocation=?1 and req.destinationLocation=?2 and req.totalQuantityRequired>req.totalQuantityIssued and req.synked=?3 order by req.modifiedDate desc",com.pratikshat.exicom.entity.LProductRequest.class)
						.setParameter(1,location.getParentLocation())
						.setParameter(2,new com.pratikshat.exicom.entity.Location(Long.parseLong(req.get("locationId").toString())))
						.setParameter(3,false)
						.getResultList();
				if(existingRequest!=null&&existingRequest.size()>0) {
					float existingQty=0;
					com.pratikshat.exicom.entity.LProductRequestLineItem existingitems=null;
					try {
						existingitems=entityManager.createQuery("select item from LProductRequestLineItem item where item.locationProductRequest=?1 and item.product=?2",com.pratikshat.exicom.entity.LProductRequestLineItem.class)
						.setParameter(1, existingRequest.get(0))
						.setParameter(2, new com.pratikshat.exicom.entity.Products(req.get("productPartCode").toString()))
						.getSingleResult();
						existingQty=existingitems.getQuantity();
					}catch(NoResultException ex) {
						existingitems=new com.pratikshat.exicom.entity.LProductRequestLineItem();
						existingitems.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
						existingitems.setCreatedDate(new java.util.Date());
						existingitems.setDescription("Auto Re-Order by central monitoring system");
						existingitems.setLocationProductRequest(existingRequest.get(0));
						existingitems.setProduct(new com.pratikshat.exicom.entity.Products(req.get("productPartCode").toString()));
						existingitems.setSynked(false);
					}
					existingitems.setQuantity((existingQty+Float.parseFloat(req.get("reorderQuantity").toString())));
					existingitems.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
					existingitems.setSynked(false);
					existingitems.setModifiedDate(new java.util.Date());
					if(existingitems.getLocationProductRequestLineItemManualId()==null)	workflow.executeWorkflow(entityManager, existingitems, "INSERT","PRE", user);
					else workflow.executeWorkflow(entityManager, existingitems, "UPDATE","PRE", user);
					entityManager.persist(existingitems);
					if(existingitems.getLocationProductRequestLineItemManualId()==null) {
						existingitems.setLocationProductRequestLineItemManualId(String.format("PRLI-%s",com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(existingitems.getLocationProductRequestLineItemId()), 5)));
						entityManager.persist(existingitems);
						workflow.executeWorkflow(entityManager, existingitems, "INSERT","POST", user);
					}
					else {
						workflow.executeWorkflow(entityManager, existingitems, "UPDATE","POST", user);
					}
					com.pratikshat.exicom.entity.LProductRequest re=existingRequest.get(0);
					re.setTotalQuantityRequired(Float.parseFloat(req.get("reorderQuantity").toString())+re.getTotalQuantityRequired());
					re.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
					re.setModifiedDate(new java.util.Date());
					
					entityManager.persist(re);
				}
				else {
					com.pratikshat.exicom.entity.LProductRequest _request=new com.pratikshat.exicom.entity.LProductRequest();
					_request.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
					_request.setCreatedDate(new Date());
					_request.setDescription("Auto Re-order by central monitoring system");
					_request.setDestinationLocation(location);
					_request.setLocationProductRequestStatus("Submitted");
					_request.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
					_request.setModifiedDate(new Date());
					_request.setNeedByDateTime(new Date());
					_request.setOwnerDetails(location.getOwner());
					_request.setSourceLocation(location.getParentLocation());
					_request.setSynked(false);
					_request.setTotalQuantityIssued(0);
					_request.setTotalQuantityReceived(0);
					_request.setTotalQuantityRequired(Float.parseFloat(req.get("reorderQuantity").toString()));
					workflow.executeWorkflow(entityManager, _request, "INSERT","PRE", user);
					entityManager.persist(_request);
					_request.setLocationProductRequestManualId(String.format("PR-%s", com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_request.getLocationProductRequestId()), 5)));
					entityManager.persist(_request);
					workflow.executeWorkflow(entityManager, _request, "INSERT","POST", user);
					
					com.pratikshat.exicom.entity.LProductRequestLineItem _requestItem=new com.pratikshat.exicom.entity.LProductRequestLineItem();
					_requestItem.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
					_requestItem.setCreatedDate(new Date());
					_requestItem.setDescription("Auto Re-order by central monitoring system");
					_requestItem.setLocationProductRequest(_request);
					_requestItem.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
					_requestItem.setModifiedDate(new Date());
					_requestItem.setProduct(new com.pratikshat.exicom.entity.Products(req.get("productPartCode").toString()));
					_requestItem.setQuantity(Float.parseFloat(req.get("reorderQuantity").toString()));
					_requestItem.setQuantityIssued(0);
					_requestItem.setQuantityReceived(0);
					_requestItem.setSynked(false);
					workflow.executeWorkflow(entityManager, _requestItem, "INSERT", "PRE", user);
					entityManager.persist(_requestItem);
					_requestItem.setLocationProductRequestLineItemManualId(String.format("PRLI-%s",com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_requestItem.getLocationProductRequestLineItemId()), 5)));
					entityManager.persist(_requestItem);
					workflow.executeWorkflow(entityManager, _requestItem, "INSERT", "POST", user);
					
					Object[] initiatedBy= (Object[])entityManager.createNativeQuery("select ifnull(first_name,''),ifnull(middle_name,''),ifnull(last_name,'') from employee_details where email_id=?1").setParameter(1, user.getUsername()).getSingleResult();
					
					//application notification
					com.pratikshat.exicom.entity.ApplicationNotificationDetails _notification=new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
					_notification.setNofificationDate(new Date());
					_notification.setNotificationActionUrl("/productrequest/"+_request.getLocationProductRequestId());
					_notification.setNotificationMessage(String.format("Product Request %s for stock transfer initiated by %s %s %s", _request.getLocationProductRequestManualId(),initiatedBy[0].toString(),initiatedBy[1].toString(),initiatedBy[0].toString()));
					_notification.setNotificationRead(false);
					_notification.setUserEmailId(location.getParentLocation().getOwner().getEmailId());
					entityManager.persist(_notification);
					try {
						msgTemplate.convertAndSend("/topic/"+location.getParentLocation().getOwner().getEmailId(), _notification);
					}catch(Exception ex) {}
				}
			}
		}
		else throw new BadRequestException("Bad request");
		return request;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public List<Map<String, Object>> resetMSL(List<Map<String, Object>> objects, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(objects!=null&&objects.size()>0) {
			for(Map<String,Object> obj:objects) {
				com.pratikshat.exicom.entity.LocationProductItem items=null;
				try {
					items=entityManager.createQuery("select items from LocationProductItem items where items.location=?1 and items.product=?2",com.pratikshat.exicom.entity.LocationProductItem.class)
							.setParameter(1,new com.pratikshat.exicom.entity.Location(Long.parseLong(obj.get("locationId").toString())))
							.setParameter(2,new com.pratikshat.exicom.entity.Products(obj.get("partCode").toString()))
							.getSingleResult();
				}catch(NoResultException ex) {
					items=new com.pratikshat.exicom.entity.LocationProductItem();
					items.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
					items.setCreatedDate(new Date());
					items.setCustomerInventory(0);
					items.setFaultyQuantity(0);
					items.setHslStatus("L");
					items.setIssuedQunatity(0);
					items.setLocation(new com.pratikshat.exicom.entity.Location(Long.parseLong(obj.get("locationId").toString())));
					items.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
					items.setModifiedDate(new Date());
					items.setMslQuantityStatus("L");
					items.setProduct(new com.pratikshat.exicom.entity.Products(obj.get("partCode").toString()));
					items.setQuantityInOnHand(0);
					items.setQuantityUnitOfMasur("NO");
				}
				items.setHslQuantity(Float.parseFloat(obj.get("hsl").toString()));
				items.setMslQuantity(Float.parseFloat(obj.get("msl").toString()));
				entityManager.persist(items);
				if(items.getLocationProductItemId()==null) {
					items.setLocationProductItemId(String.format("PI-%s", com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(items.getId()),0)));
					entityManager.persist(items);
				}
			}
		}
		return objects;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public LocationRequest editLocation(LocationRequest location, LoginUser user, int businessDomainId) throws Exception {
		com.pratikshat.exicom.entity.Location _location=null;
		try {
			_location=entityManager.createQuery("select loc from Location loc where loc=?1",Location.class)
					.setParameter(1, new Location(location.getLocationId()))
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new Exception("Warehouse not found");
		}
		
		if(location.getLocationTypeId()!=4&&location.getParentLocationId()==0) {
			throw new Exception("Please provide parent Warehouse");
		}
		if(location.getLocationTypeId()==4&&location.getParentLocationId()!=0) {
			throw new Exception("Plant can not have Parent Warehouse/Plant");
		}
		if((location.getLocationTypeId()==4||location.getLocationTypeId()==1)&&(location.getPlant()==null||location.getPlant().equalsIgnoreCase(""))) {
			throw new Exception("Please provide Plant(Fectory)/Warehouse code");
		}
		
		if(location.getLocationTypeId()==0) {
			throw new Exception("Please provide warehouse type");
		}
		if((location.getLocationTypeId()==4||location.getLocationTypeId()==1)&&_location.getPlant()!=null&&!_location.getPlant().equalsIgnoreCase(location.getPlant())) {
			Location checkloc=null;
			try {
				checkloc=entityManager.createQuery("select loc from Location loc where loc.plant=?1",Location.class)
						.setParameter(1, location.getPlant().trim())
						.getSingleResult();
				throw new Exception(String.format("Plant/Warehouse code %s already in use",location.getPlant()));
			}catch(NoResultException ex) {
				
			}
		}
		
		Location parentLocation=null;
		try {
			parentLocation=entityManager.find(Location.class, location.getParentLocationId());
			
		}catch(NoResultException ex) {
			if(location.getLocationTypeId()!=4) {
				throw new Exception("Parent Location not found");
			}
			
		}
		if(parentLocation!=null) {
			if(parentLocation.getLocationType()!=null&&(parentLocation.getLocationType().getLocationTypeId()==3||parentLocation.getLocationType().getLocationTypeId()==5)) {
				
				throw new Exception("You can not create any type of warehouse under Site Engineer or Repair Engineer");
			}
		}
		
		_location.setInventoryLocation(location.isInventoryLocation());
		_location.setLegalEntity(new LegalEntity(location.getLegalEntityId()));
		_location.setLocationName(location.getLocationName());
		_location.setLocationType(new LocationType(location.getLocationTypeId()));
		_location.setMobileLocation(location.isMobileLocation());
		//_location.setOwner(new EmployeeDetails(user.getUsername()));
		_location.setParentLocation(parentLocation);
		_location.setModifiedBy(new EmployeeDetails(user.getUsername()));
		_location.setModifiedDate(new Date());
		_location.setPlant(location.getPlant()!=null?location.getPlant().trim():null);
		_location.setBusinessDomain(new BusinessDomain(businessDomainId));
		entityManager.persist(_location);
		location.setLocationId(_location.getLocationId());
		return location;
	}

	@Override
	public List<Map<String, Object>> searchMyInventory(Map<String, String> param, LoginUser user, int businessDomainId) {
		List<Object> parameter=new ArrayList<Object>();
		String query="SELECT	loc.`location_id` AS locationId,\r\n" + 
				"	prd.`pdoduct_description` AS productDescription,\r\n" + 
				"	prd.`product_part_code` AS partCode,\r\n" + 
				"	prd.`product_type` AS productType,\r\n" + 
				"	prd.`sub_product_family_product_family_sub_product_id` AS subProductFamilay,\r\n" + 
				"	item.`id` AS itemId,\r\n" + 
				"	item.`ber_qty` AS berQty,\r\n" + 
				"	item.`customer_inventory` AS customerInventoryQty,\r\n" + 
				"	item.`faulty_quantity` AS faultyQty,\r\n" + 
				"	item.`ok_quantity` AS okQty,\r\n" + 
				"	item.`refurbished` AS refurbishedQty,\r\n" + 
				"	item.`rejected_qty` AS rejectedQty,\r\n" + 
				"	item.`scrap_qty` AS scrapQty,\r\n" + 
				"	item.`quantity_in_on_hand` AS quantityOnHand,\r\n"+
				"	litem.`id` AS lineItemId,\r\n" + 
				"	litem.`customer_inventory` AS customerInventory,\r\n" + 
				"	litem.`item_serial_number` AS serialNumber,\r\n" + 
				"	litem.remarks,\r\n"+
				"	st.`product_state_id` AS serialNumberStateId,\r\n" + 
				"	st.`product_state_name` AS srialNumberStae\r\n" + 
				"	FROM `location` loc\r\n" + 
				"	JOIN `location_product_item` item ON item.`location_location_id`=loc.`location_id`\r\n" + 
				"	JOIN `products` prd ON prd.`product_part_code` =item.`product_product_part_code`\r\n" + 
				"	JOIN `service_recource` sr on sr.location_location_id=loc.location_id\r\n"+
				"	LEFT JOIN `location_product_line_items` litem ON litem.`location_product_item_id` =item.`id`\r\n" + 
				"	LEFT JOIN `product_line_item_product_state` st ON st.`product_state_id`=litem.`location_proeuct_line_item_state_product_state_id`\r\n" + 
				"	WHERE sr.`user_email_id`='"+user.getUsername()+"'";
		
		query+=" and loc.business_domain_business_domain_id='"+businessDomainId+"'";
		
		if(param.get("partCode")!=null&&!param.get("partCode").toString().equalsIgnoreCase("")) {
			query+=" and prd.product_part_code like '%"+param.get("partCode")+"%'";
			
		}
		if(param.get("serialNumber")!=null&&!param.get("serialNumber").toString().equalsIgnoreCase("")) {
			query+=" and litem.item_serial_number like '%"+param.get("serialNumber")+"%'";
			//parameter.add("%"+param.get("serialNumber")+"%");
		}
		
		if(param.get("description")!=null&&!param.get("description").toString().equalsIgnoreCase("")) {
			query+=" and prd.pdoduct_description like '%"+param.get("description")+"%'";
			
		}
		if(param.get("state")!=null&&!param.get("state").toString().equalsIgnoreCase("0")) {
			query+=" and st.product_state_id="+param.get("state");
			
		}
		query+=" order by litem.`customer_inventory` ASC";
		System.out.println(query);

		return jdbc.queryForList(query);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.SERIALIZABLE,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public void changeProductSerialNumberStatus(LoginUser user, long lineitemId, long stageid,String remarks)
			throws BadRequestException, UnauthoriseException {
		LocationProductLineItems lineItems=null;
		LocationProductItem item=null;
		Location location=null;
		//A JUGAR FOR @MANPPREET MADAM DEMAND OF DOA ACTION 
		
		//FINDING ROLE GROUP OF USER
		String roleName="";
		
		Iterator<LoginUserRoles> ite=(Iterator<LoginUserRoles>) user.getAuthorities().iterator();
		while(ite.hasNext()) {
			LoginUserRoles rl=ite.next();
			roleName= rl.getAuthority();
		}
		
		String rlGroupName=entityManager.createQuery("select rlg.groupName from UserRoles rl join rl.roleGroup rlg where rl.authorities=?1",String.class).setParameter(1, roleName).getSingleResult();
		
		if(rlGroupName.equalsIgnoreCase("FIELD_SERVICE_ENGINEER")||rlGroupName.equalsIgnoreCase("SCH")) {
			if(rlGroupName.equalsIgnoreCase("SCH")&&stageid!=2)
				throw new BadRequestException("Can not update status other then faulty");
			
			if(rlGroupName.equalsIgnoreCase("FIELD_SERVICE_ENGINEER")&&stageid!=6)
				throw new BadRequestException("Can not update status other then DOA");
		}
		else
			throw new UnauthoriseException("You are not authorise to change status");
		
		try {
			lineItems= entityManager.createQuery("select lineItem from LocationProductLineItems lineItem join lineItem.locationProductItem item join item.location loc where lineItem=?1",LocationProductLineItems.class)
					.setParameter(1,new LocationProductLineItems(lineitemId))
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException(ex.getMessage());
		}
		item=lineItems.getLocationProductItem();
		location=item.getLocation();
		
		if(!location.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) 
			throw new UnauthoriseException("You are not authorise");
		long prestate=lineItems.getLocationProeuctLineItemState().getProductStateId();
		
		//UPDATE VALIDATION GUGAR FOR MANPREET MADAM
		
		if(rlGroupName.equalsIgnoreCase("FIELD_SERVICE_ENGINEER"))
			if(prestate!=1)
				throw new BadRequestException("Unable to udpate status");
		if(rlGroupName.equalsIgnoreCase("SCH"))
			if(prestate!=6)
				throw new BadRequestException("Unable to update status");
		
		if(stageid!=lineitemId) {
			if(prestate==1)
				item.setOkQuantity(item.getOkQuantity()-1);
			if(prestate==2)
				item.setFaultyQuantity(item.getFaultyQuantity()-1);
			if(prestate==3)
				item.setScrapQty(item.getScrapQty()-1);
			if(prestate==4)
				item.setRejectedQty(item.getRejectedQty()-1);
			if(prestate==5)
				item.setBerQty(item.getBerQty()-1);
			if(prestate==6)
				item.setDoaQuantity(item.getDoaQuantity()-1);
			if(prestate==1&&lineItems.isRefurbished())
				item.setRefurbished(item.getRefurbished()-1);
			
			
			if(stageid==1)
				item.setOkQuantity(item.getOkQuantity()+1);
			if(stageid==2)
				item.setFaultyQuantity(item.getFaultyQuantity()+1);
			if(stageid==3)
				item.setScrapQty(item.getScrapQty()+1);
			if(stageid==4)
				item.setRejectedQty(item.getRejectedQty()+1);
			if(stageid==5)
				item.setBerQty(item.getBerQty()+1);
			if(stageid==6)
				item.setDoaQuantity(item.getDoaQuantity()+1);
			
			if(stageid==1&&lineItems.isRefurbished())
				item.setRefurbished(item.getRefurbished()+1);
			item.setModifiedBy(new EmployeeDetails(user.getUsername()));
			item.setModifiedDate(new Date());
			entityManager.persist(item);
			lineItems.setModifiedBy(new EmployeeDetails(user.getUsername()));
			lineItems.setModifiedDate(new Date());
			lineItems.setLocationProeuctLineItemState(new ProductLineItemProductState(stageid));
			lineItems.setRemarks(remarks);
			entityManager.persist(lineItems);
			
			ProductLineItem pLineItem=dbProductLineItem.findByProductAndSerialNumber(item.getProduct(),lineItems.getItemSerialNumber());
			if(pLineItem!=null) {
				pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				pLineItem.setModifiedDate(new Date());
				pLineItem.setState(new ProductLineItemProductState(stageid));
				pLineItem.setRemarks(remarks);
				dbProductLineItem.save(pLineItem);
			}else {
				pLineItem=new ProductLineItem();
				pLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
				pLineItem.setCreatedDate(new Date());
				pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				pLineItem.setModifiedDate(new Date());
				pLineItem.setPosition(String.valueOf(item.getLocation().getLocationId()));
				pLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
				pLineItem.setProduct(item.getProduct());
				pLineItem.setSerialNumber(lineItems.getItemSerialNumber());
				pLineItem.setState(new ProductLineItemProductState(stageid));
				pLineItem.setStatus(new ProductLineItemStatus(1));
				pLineItem.setRemarks(remarks);
				dbProductLineItem.save(pLineItem);
			}
			
			ObjectLog log=new ObjectLog();
			log.setCreatedDate(new Date());
			log.setKeyName("LocationProeuctLineItemState");
			log.setLoggedBy(new EmployeeDetails(user.getUsername()));
			log.setObjectId(lineItems.getItemSerialNumber()+"-"+item.getProduct().getProductPartCode());
			log.setObjectName("LocationProductLineItems");
			log.setOldValue(String.valueOf(prestate));
			log.setValue(String.valueOf(stageid));
			entityManager.persist(log);
		}
		
	}
	
	
	@Override
	public List<LocationResponse> advanceSearch(Map<String, Object> filterData, LoginUser user, List<Long> childLoc, int businessDomainId)throws ParseException {
			StringBuilder queryStringBuilder=new StringBuilder();
			queryStringBuilder.append("select new com.pratikshat.exicom.model.response.LocationResponse(loc) from Location loc left join loc.owner ow left join loc.parentLocation prtLocation left join loc.legalEntity legal left join loc.createdBy left join loc.modifiedBy left join loc.locationType lty where 1=1");
			
			queryStringBuilder.append(" and (loc.businessDomain.businessDomainId=:businessDomainId or loc.businessDomain.businessDomainId=3)");
			
			if(filterData.get("locationName")!=null&&!filterData.get("locationName").equals(""))
				queryStringBuilder.append(" and loc.locationName like :locationName");
			if(filterData.get("locationTypeName")!=null&&!filterData.get("locationTypeName").equals(""))
				queryStringBuilder.append(" and lty.locationTypeName like :locationTypeName");
			if(filterData.get("legalEntityName")!=null&&!filterData.get("legalEntityName").equals(""))
				queryStringBuilder.append(" and legal.legalEntityName like :legalEntityName");
			if(filterData.get("owner")!=null&&!filterData.get("owner").equals(""))
				queryStringBuilder.append(" and (CONCAT(ow.firstName,' ',coalesce(ow.middleName,''),ow.lastName) LIKE :owner OR ow.emailId LIKE :owner)");
			if(filterData.get("parentLocationName")!=null&&!filterData.get("parentLocationName").equals(""))
				queryStringBuilder.append(" and prtLocation.locationName like :parentLocationName");
			
			if(childLoc!=null)
				queryStringBuilder.append(" and (loc.locationId in :childsLoc or prtLocation.locationId in :childsLoc)");
			
			Query qr=entityManager.createQuery(queryStringBuilder.toString(),LocationResponse.class);
			
			qr.setParameter("businessDomainId",businessDomainId);
			
			if(filterData.get("locationName")!=null&&!filterData.get("locationName").equals(""))
				qr.setParameter("locationName","%"+ filterData.get("locationName")+"%");
			if(filterData.get("locationTypeName")!=null&&!filterData.get("locationTypeName").equals(""))
				qr.setParameter("locationTypeName", "%"+ filterData.get("locationTypeName")+"%");
			if(filterData.get("legalEntityName")!=null&&!filterData.get("legalEntityName").equals(""))
				qr.setParameter("legalEntityName", "%"+ filterData.get("legalEntityName")+"%");
			if(filterData.get("owner")!=null&&!filterData.get("owner").equals(""))
				qr.setParameter("owner","%"+ filterData.get("owner")+"%");
			if(filterData.get("parentLocationName")!=null&&!filterData.get("parentLocationName").equals(""))
				qr.setParameter("parentLocationName","%"+ filterData.get("parentLocationName")+"%");
			
			if(childLoc!=null) {
				List<Long> emps=new ArrayList<Long>();
				for(Long loc:childLoc) {
					emps.add(loc);
				}
				qr.setParameter("childsLoc", emps);
			}
			return qr.getResultList();
		}

	@Override
	public List<LocationResponse> getChildLocationAdvanceSearch(long locationid, Map<String, Object> request,
			LoginUser user, int businessDomainId) throws ParseException {
		
		StringBuilder queryStringBuilder=new StringBuilder();
		queryStringBuilder.append("select new com.pratikshat.exicom.model.response.LocationResponse(loc) from Location loc left join loc.owner ow left join loc.parentLocation prtLocation left join loc.legalEntity legal left join loc.createdBy left join loc.modifiedBy left join loc.locationType lty where prtLocation.locationId=:locationId ");
		
		queryStringBuilder.append(" and (loc.businessDomain.businessDomainId=:businessDomainId or loc.businessDomain.businessDomainId=3)");
		
		if(request.get("locationName")!=null&&!request.get("locationName").equals(""))
			queryStringBuilder.append(" and loc.locationName like :locationName");
		if(request.get("locationTypeName")!=null&&!request.get("locationTypeName").equals(""))
			queryStringBuilder.append(" and lty.locationTypeName like :locationTypeName");
		if(request.get("legalEntityName")!=null&&!request.get("legalEntityName").equals(""))
			queryStringBuilder.append(" and legal.legalEntityName like :legalEntityName");
		if(request.get("owner")!=null&&!request.get("owner").equals(""))
			queryStringBuilder.append(" and (CONCAT(CONCAT(ow.firstName, ' '),coalesce(CONCAT(ow.middleName,' '),''),ow.lastName) LIKE :owner OR ow.emailId LIKE :owner)");
		if(request.get("plantcode")!=null&&!request.get("plantcode").equals(""))
			queryStringBuilder.append(" and loc.plant like :plantcode");
		
		if(request.get("mobileWarehoue")!=null&&!request.get("mobileWarehoue").equals("") && 
				request.get("inventoryWarehouse")!=null&&!request.get("inventoryWarehouse").equals("")) {
			
			if(request.get("mobileWarehoue").toString().equalsIgnoreCase("true") && request.get("inventoryWarehouse").toString().equalsIgnoreCase("false")) {
				queryStringBuilder.append(" and loc.mobileLocation=:mobileWarehoue");
			}
			
			if(request.get("mobileWarehoue").toString().equalsIgnoreCase("false") && request.get("inventoryWarehouse").toString().equalsIgnoreCase("true")) {
				queryStringBuilder.append(" and loc.inventoryLocation=:inventoryWarehouse");	
			}
		}else if(request.get("mobileWarehoue")!=null&&!request.get("mobileWarehoue").equals("") && 
				(request.get("inventoryWarehouse")==null || request.get("inventoryWarehouse").equals(""))) {
			
			if(request.get("mobileWarehoue").toString().equalsIgnoreCase("true")) {
				queryStringBuilder.append(" and loc.mobileLocation=:mobileWarehoue");	
			}		
		}else if((request.get("mobileWarehoue")==null || request.get("mobileWarehoue").equals("")) && 
				request.get("inventoryWarehouse")!=null&&!request.get("inventoryWarehouse").equals("")) {
			
			if(request.get("inventoryWarehouse").toString().equalsIgnoreCase("true")) {
				queryStringBuilder.append(" and loc.inventoryLocation=:inventoryWarehouse");	
			}
		}
			
		Query qr=entityManager.createQuery(queryStringBuilder.toString(),LocationResponse.class);
		
		qr.setParameter("locationId",locationid);
		
		qr.setParameter("businessDomainId",businessDomainId);
		
		if(request.get("locationName")!=null&&!request.get("locationName").equals(""))
			qr.setParameter("locationName","%"+ request.get("locationName")+"%");
		if(request.get("locationTypeName")!=null&&!request.get("locationTypeName").equals(""))
			qr.setParameter("locationTypeName", "%"+ request.get("locationTypeName")+"%");
		if(request.get("legalEntityName")!=null&&!request.get("legalEntityName").equals(""))
			qr.setParameter("legalEntityName", "%"+ request.get("legalEntityName")+"%");
		if(request.get("owner")!=null&&!request.get("owner").equals(""))
			qr.setParameter("owner","%"+ request.get("owner")+"%");
		if(request.get("plantcode")!=null&&!request.get("plantcode").equals(""))
			qr.setParameter("plantcode","%"+request.get("plantcode")+"%");
		
		if(request.get("mobileWarehoue")!=null&&!request.get("mobileWarehoue").equals("") && 
				request.get("inventoryWarehouse")!=null&&!request.get("inventoryWarehouse").equals("")) {
			
			if(request.get("mobileWarehoue").toString().equalsIgnoreCase("true") && request.get("inventoryWarehouse").toString().equalsIgnoreCase("false")) {
				qr.setParameter("mobileWarehoue",true);
			}
			
			if(request.get("mobileWarehoue").toString().equalsIgnoreCase("false") && request.get("inventoryWarehouse").toString().equalsIgnoreCase("true")) {
				qr.setParameter("inventoryWarehouse",true);
			}
		}else if(request.get("mobileWarehoue")!=null&&!request.get("mobileWarehoue").equals("") && 
				(request.get("inventoryWarehouse")==null || request.get("inventoryWarehouse").equals(""))) {
			
			if(request.get("mobileWarehoue").toString().equalsIgnoreCase("true")) {
				qr.setParameter("mobileWarehoue",true);
			}		
		}else if((request.get("mobileWarehoue")==null || request.get("mobileWarehoue").equals("")) && 
				request.get("inventoryWarehouse")!=null&&!request.get("inventoryWarehouse").equals("")) {
			
			if(request.get("inventoryWarehouse").toString().equalsIgnoreCase("true")) {
				qr.setParameter("inventoryWarehouse",true);
			}
		}
		
		return qr.getResultList();
	}

	@Override
	public List<LocationProductItemResponse> getLocationProductItemsAdvanceSearch(long locationId,
			Map<String, Object> request, LoginUser user, int businessDomainId) throws ParseException {
			
		StringBuilder queryStringBuilder=new StringBuilder();
		queryStringBuilder.append("select new com.pratikshat.exicom.model.response.LocationProductItemResponse(litem) from LocationProductItem litem join litem.location loc join litem.product pr where loc.locationId=:locationId");
		
		queryStringBuilder.append(" and (loc.businessDomain.businessDomainId=:businessDomainId or loc.businessDomain.businessDomainId=3)");
		
		if(request.get("productItemNumber")!=null&&!request.get("productItemNumber").equals(""))
			queryStringBuilder.append(" and litem.locationProductItemId like :productItemNumber");
		if(request.get("partcode")!=null&&!request.get("partcode").equals(""))
			queryStringBuilder.append(" and pr.productPartCode like :partcode");
		if(request.get("productDescription")!=null&&!request.get("productDescription").equals(""))
			queryStringBuilder.append(" and pr.pdoductDescription like :productDescription");
		if(request.get("productType")!=null&&!request.get("productType").equals(""))
			queryStringBuilder.append(" and pr.productType like :productType");

			
		Query qr=entityManager.createQuery(queryStringBuilder.toString(),LocationProductItemResponse.class);
		
		qr.setParameter("locationId",locationId);
		
		qr.setParameter("businessDomainId",businessDomainId);
		
		if(request.get("productItemNumber")!=null&&!request.get("productItemNumber").equals(""))
			qr.setParameter("productItemNumber","%"+ request.get("productItemNumber")+"%");
		if(request.get("partcode")!=null&&!request.get("partcode").equals(""))
			qr.setParameter("partcode", "%"+ request.get("partcode")+"%");
		if(request.get("productDescription")!=null&&!request.get("productDescription").equals(""))
			qr.setParameter("productDescription", "%"+ request.get("productDescription")+"%");
		if(request.get("productType")!=null&&!request.get("productType").equals(""))
			qr.setParameter("productType","%"+ request.get("productType")+"%");
		
		return qr.getResultList();
	}

	@Override
	public List<LocationProductRequestResponse> getSendProductRequestAdvanceSearch(long locationId,
			Map<String, Object> request, LoginUser user, int businessDomainId) throws ParseException {
		
		StringBuilder queryStringBuilder=new StringBuilder();
		queryStringBuilder.append("select new com.pratikshat.exicom.model.response.LocationProductRequestResponse(lpr) from LProductRequest lpr left join lpr.sourceLocation sl left join lpr.destinationLocation dl where dl.locationId=:locationId and lpr.locationProductRequestStatus not in ('Draft','Archived')");
		
		if(request.get("productRequestNumber")!=null&&!request.get("productRequestNumber").equals(""))
			queryStringBuilder.append(" and lpr.locationProductRequestManualId like :productRequestNumber");
		if(request.get("sourceWarehouse")!=null&&!request.get("sourceWarehouse").equals(""))
			queryStringBuilder.append(" and sl.locationName like :sourceWarehouse");
		if(request.get("destinationWarehouse")!=null&&!request.get("destinationWarehouse").equals(""))
			queryStringBuilder.append(" and dl.locationName like :destinationWarehouse");
		if(request.get("expectedDate")!=null&&!request.get("expectedDate").equals(""))
			queryStringBuilder.append(" and function('date_format', lpr.needByDateTime, '%Y, %m, %d')=function('date_format', :expectedDate, '%Y, %m, %d') ");

			
		Query qr=entityManager.createQuery(queryStringBuilder.toString(),LocationProductRequestResponse.class);
		
		qr.setParameter("locationId",locationId);
		
		if(request.get("productRequestNumber")!=null&&!request.get("productRequestNumber").equals(""))
			qr.setParameter("productRequestNumber","%"+ request.get("productRequestNumber")+"%");
		if(request.get("sourceWarehouse")!=null&&!request.get("sourceWarehouse").equals(""))
			qr.setParameter("sourceWarehouse", "%"+ request.get("sourceWarehouse")+"%");
		if(request.get("destinationWarehouse")!=null&&!request.get("destinationWarehouse").equals(""))
			qr.setParameter("destinationWarehouse", "%"+ request.get("destinationWarehouse")+"%");
		if(request.get("expectedDate")!=null&&!request.get("expectedDate").equals(""))
			qr.setParameter("expectedDate",request.get("expectedDate"));
		
		return qr.getResultList();
	}

	@Override
	public List<LocationProductRequestResponse> getReceivedProductRequestAdvanceSearch(long locationId,
			Map<String, Object> request, LoginUser user, int businessDomainId) throws ParseException {
		
		StringBuilder queryStringBuilder=new StringBuilder();
		queryStringBuilder.append("select new com.pratikshat.exicom.model.response.LocationProductRequestResponse(lpr) from LProductRequest lpr left join lpr.sourceLocation sl left join lpr.destinationLocation dl where sl.locationId=:locationId and lpr.locationProductRequestStatus not in ('Draft','Archived')");
		
		if(request.get("productRequestNumber")!=null&&!request.get("productRequestNumber").equals(""))
			queryStringBuilder.append(" and lpr.locationProductRequestManualId like :productRequestNumber");
		if(request.get("sourceWarehouse")!=null&&!request.get("sourceWarehouse").equals(""))
			queryStringBuilder.append(" and sl.locationName like :sourceWarehouse");
		if(request.get("destinationWarehouse")!=null&&!request.get("destinationWarehouse").equals(""))
			queryStringBuilder.append(" and dl.locationName like :destinationWarehouse");
		if(request.get("expectedDate")!=null&&!request.get("expectedDate").equals(""))
			queryStringBuilder.append(" and function('date_format', lpr.needByDateTime, '%Y, %m, %d')=function('date_format', :expectedDate, '%Y, %m, %d') ");

			
		Query qr=entityManager.createQuery(queryStringBuilder.toString(),LocationProductRequestResponse.class);
		
		qr.setParameter("locationId",locationId);
		if(request.get("productRequestNumber")!=null&&!request.get("productRequestNumber").equals(""))
			qr.setParameter("productRequestNumber","%"+ request.get("productRequestNumber")+"%");
		if(request.get("sourceWarehouse")!=null&&!request.get("sourceWarehouse").equals(""))
			qr.setParameter("sourceWarehouse", "%"+ request.get("sourceWarehouse")+"%");
		if(request.get("destinationWarehouse")!=null&&!request.get("destinationWarehouse").equals(""))
			qr.setParameter("destinationWarehouse", "%"+ request.get("destinationWarehouse")+"%");
		if(request.get("expectedDate")!=null&&!request.get("expectedDate").equals(""))
			qr.setParameter("expectedDate",request.get("expectedDate"));
		
		return qr.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.SERIALIZABLE,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public void changeProductPartcodeAndState(long litemId, Map<String, Object> requestParam, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		if(requestParam.get("remark")==null||requestParam.get("remark").toString().equalsIgnoreCase(""))
			throw new BadRequestException("Remark can not be blank");
		
		ServiceRecource sr =null;
		EmployeeDetails currentEmp=new EmployeeDetails(user.getUsername());
						
		Date today=new Date();
		String roleName="";
		Iterator<LoginUserRoles> rIt=(Iterator<LoginUserRoles>)user.getAuthorities().iterator();
		while(rIt.hasNext()) {
			roleName=rIt.next().getAuthority();
		}
		
		try {
			sr=entityManager.createQuery("select sr from ServiceRecource sr join sr.location loc join loc.legalEntity lg where sr.user=?1",ServiceRecource.class)
					.setParameter(1, currentEmp)
					.getSingleResult();
			
		}catch(NoResultException ex) {
			throw new BadRequestException("Your home location not found");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple Service resource found");
		}
		
		LocationProductLineItems litems=null;
		
		try {
			litems=entityManager.createQuery("select litems from LocationProductLineItems litems join litems.locationProductItem item join item.location loc where litems=?1",LocationProductLineItems.class)
					.setParameter(1, new LocationProductLineItems(litemId))
					.getSingleResult();
			
		}catch(NoResultException ex) {
			throw new BadRequestException("Line item not found");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple Line item found");
		}
		
		if(sr.getLocation().getLocationId()!=litems.getLocationProductItem().getLocation().getLocationId())
			throw new UnauthoriseException("You are not authorised");
		
		checkPendingPartcodeChangeRequest(entityManager,litems.getLocationProductItem().getProduct(),litems.getItemSerialNumber());
		
		
		String nPartcode=requestParam.get("part_code").toString().trim();
		String nSerialnumber=requestParam.get("serial_number").toString().trim();
		long nState=Long.parseLong(requestParam.get("state_id").toString());
		
		
		checkPendingPartcodeChangeRequest(entityManager,new Products(nPartcode),litems.getItemSerialNumber());
		
		//CHECK FOR EXISTING NEW PARTCODE AND SERIAL NUMBER
		LocationProductLineItems existingLineItems=null;
		try {
			existingLineItems=entityManager.createQuery("select litem from LocationProductLineItems litem join litem.locationProductItem item join item.product prd where item.product=?1 and litem.itemSerialNumber=?2",LocationProductLineItems.class)
					.setParameter(1,new Products(nPartcode))
					.setParameter(2,nSerialnumber)
					.getSingleResult();
			if(existingLineItems.getId()!=litemId) {
				throw new BadRequestException("Duplicate serial number found for parcode "+nPartcode+" and serial number "+nSerialnumber);
			}
					
		}catch(NoResultException ex) {
			
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Duplicate serial number found for Partcode "+nPartcode+" and serial number "+nSerialnumber);
		}
		
		//END FOR CHECK
		
		//IF PARTCODE HAS NOT CHANGED
		if(litems.getLocationProductItem().getProduct().getProductPartCode().equalsIgnoreCase(nPartcode)) {
			
			LocationProductItem item=litems.getLocationProductItem();
			long oStateId=litems.getLocationProeuctLineItemState().getProductStateId();
			if(nState!=oStateId) {
				
				String rlGroupName=entityManager.createQuery("select rlg.groupName from UserRoles rl join rl.roleGroup rlg where rl.authorities=?1",String.class).setParameter(1, roleName).getSingleResult();
				
				if(rlGroupName.equalsIgnoreCase("SCH")) {
				
						throw new BadRequestException("Can not update status from this module");
				}
				if(oStateId==1) {
					item.setOkQuantity(item.getOkQuantity()-1);
				}
				if(oStateId==2) {
					item.setFaultyQuantity(item.getFaultyQuantity()-1);
				}
				if(oStateId==3) {
					item.setScrapQty(item.getScrapQty()-1);
				}
				if(oStateId==4) {
					item.setRejectedQty(item.getRejectedQty()-1);
				}
				if(oStateId==5) {
					item.setBerQty(item.getBerQty()-1);
				}
				if(oStateId==6) {
					item.setDoaQuantity(item.getDoaQuantity()-1);
				}
				
				if(nState==1) {
					item.setOkQuantity(item.getOkQuantity()+1);
				}
				if(nState==2) {
					item.setFaultyQuantity(item.getFaultyQuantity()+1);
				}
				if(nState==3) {
					item.setScrapQty(item.getScrapQty()+1);
				}
				if(nState==4) {
					item.setRejectedQty(item.getRejectedQty()+1);
				}
				if(nState==5) {
					item.setBerQty(item.getBerQty()+1);
				}
				if(nState==6) {
					item.setDoaQuantity(item.getDoaQuantity()+1);
				}
				
				item.setModifiedBy(currentEmp);
				item.setModifiedDate(today);
				entityManager.persist(item);
			}
			
			litems.setItemSerialNumber(nSerialnumber);
			litems.setLocationProeuctLineItemState(new ProductLineItemProductState(nState));
			litems.setModifiedBy(currentEmp);
			litems.setModifiedDate(today);
			
			entityManager.persist(litems);
			
			
		}
		else {
			
			//IF PARTCODE CHANGE
			
			//validation for product type and product  sub fammily
			
			Products prev=litems.getLocationProductItem().getProduct();
			Products nv=null;
			try {
				nv=entityManager.createQuery("select nv from Products nv where nv.productPartCode=?1",Products.class)
						.setParameter(1, nPartcode)
						.getSingleResult();
			}catch(NoResultException ex) {
				throw new BadRequestException("Wrong partcode "+nPartcode+", Product not found");
			}catch(NonUniqueResultException ex) {
				throw new BadRequestException("Multiple partcode found  "+nPartcode);
			}
			
			if(!nv.getProductType().equalsIgnoreCase(prev.getProductType()))
				throw new BadRequestException("Different partcode type found for "+nPartcode+" and "+prev.getProductPartCode());
			
			if(nv.getSubProductFamily().getProductFamilySubProductId()!=prev.getSubProductFamily().getProductFamilySubProductId())
				throw new BadRequestException("Sub family for partcode "+nPartcode+" and "+prev.getProductPartCode());
			
			ProductLineItem pLineItem=null;
			
			try {
				pLineItem=entityManager.createQuery("select pitem from ProductLineItem pitem where pitem.product=?1 and pitem.serialNumber=?2",ProductLineItem.class)
						.setParameter(1, new Products(nPartcode))
						.setParameter(2, nSerialnumber)
						.getSingleResult();
				
				throw new BadRequestException("Duplicate serial number "+nSerialnumber+" partcode "+nPartcode);
				
			} catch(NonUniqueResultException ex) { 
				
				throw new BadRequestException("Duplicate partcode "+nPartcode+" and serial number "+nSerialnumber+" found"); 
				
			} catch(NoResultException ex) {
				
				
			}
			
			LocationProductItem oldItem=litems.getLocationProductItem();
			
			long oldStateId=litems.getLocationProeuctLineItemState().getProductStateId();
			if(oldStateId!=nState) {
				String rlGroupName=entityManager.createQuery("select rlg.groupName from UserRoles rl join rl.roleGroup rlg where rl.authorities=?1",String.class).setParameter(1, roleName).getSingleResult();
				
				if(rlGroupName.equalsIgnoreCase("SCH")) {
				
						throw new BadRequestException("Can not update status from this module");
				}
			}
			
			
			if(oldStateId==1) {
				oldItem.setOkQuantity(oldItem.getOkQuantity()-1);
			}
			if(oldStateId==2) {
				oldItem.setFaultyQuantity(oldItem.getFaultyQuantity()-1);
			}
			if(oldStateId==3) {
				oldItem.setScrapQty(oldItem.getScrapQty()-1);
			}
			if(oldStateId==4) {
				oldItem.setRejectedQty(oldItem.getRejectedQty()-1);
			}
			if(oldStateId==5) {
				oldItem.setBerQty(oldItem.getBerQty()-1);
			}
			if(oldStateId==6) {
				oldItem.setDoaQuantity(oldItem.getDoaQuantity()-1);
			}
			
			if(litems.isCustomerInventory())
				oldItem.setCustomerInventory(oldItem.getCustomerInventory()-1);
			
			if(litems.isRefurbished())
				oldItem.setRefurbished(oldItem.getRefurbished()-1);
			
			oldItem.setQuantityInOnHand(oldItem.getQuantityInOnHand()-1);
			
			oldItem.setModifiedBy(currentEmp);
			
			oldItem.setModifiedDate(today);
			
			entityManager.persist(oldItem);
			
			LocationProductItem newItem=null;
			try {
				newItem=entityManager.createQuery("select item from LocationProductItem item where item.location=?1 and item.product=?2",LocationProductItem.class)
						.setParameter(1, sr.getLocation())
						.setParameter(2,new Products(nPartcode))
						.getSingleResult();
			}catch(NoResultException ex) {
				
				newItem=new LocationProductItem();
				newItem.setBerQty(0);
				newItem.setCreatedBy(currentEmp);
				newItem.setCreatedDate(today);
				newItem.setCustomerInventory(0);
				newItem.setDoaQuantity(0);
				newItem.setFaultyQuantity(0);
				newItem.setHslQuantity(0);
				newItem.setHslStatus("L");
				newItem.setIssuedQunatity(0);
				newItem.setLocation(sr.getLocation());
			//	newItem.setLocationProductItemId(locationProductItemId);
				newItem.setModifiedBy(currentEmp);
				newItem.setModifiedDate(today);
				newItem.setMslQuantity(0);
				newItem.setMslQuantityStatus("L");
				newItem.setOkQuantity(0);
				newItem.setProduct(new Products(nPartcode));
				newItem.setQuantityInOnHand(0);
				newItem.setQuantityUnitOfMasur("NO");
				newItem.setRefurbished(0);
				newItem.setRejectedQty(0);
				newItem.setScrapQty(0);
				entityManager.persist(newItem);
				
				String manualId="PI-"+MiscellaneousUtils.fillZero(String.valueOf(newItem.getId()),5);
				newItem.setLocationProductItemId(manualId);
				
				entityManager.persist(newItem);
				
			}catch(NonUniqueResultException ex) {
				throw new BadRequestException("Multipal product found at same location");
			}
			
			if(nState==1) {
				newItem.setOkQuantity(newItem.getOkQuantity()+1);
			}
			if(nState==2) {
				newItem.setFaultyQuantity(newItem.getFaultyQuantity()+1);
			}
			if(nState==3) {
				newItem.setScrapQty(newItem.getScrapQty()+1);
			}
			if(nState==4) {
				newItem.setRejectedQty(newItem.getRejectedQty()+1);
			}
			if(nState==5) {
				newItem.setBerQty(newItem.getBerQty()+1);
			}
			if(nState==6) {
				newItem.setDoaQuantity(newItem.getDoaQuantity()+1);
			}
			
			newItem.setQuantityInOnHand(newItem.getQuantityInOnHand()+1);
			
			if(litems.isCustomerInventory())
				newItem.setCustomerInventory(newItem.getCustomerInventory()+1);
			
			if(litems.isRefurbished())
				newItem.setRefurbished(newItem.getRefurbished()+1);
			
			newItem.setModifiedBy(currentEmp);
			newItem.setModifiedDate(today);
			
			entityManager.persist(newItem);
			String oldSerial=litems.getItemSerialNumber();
			litems.setItemSerialNumber(nSerialnumber);
			litems.setLocationProeuctLineItemState(new ProductLineItemProductState(nState));
			litems.setLocationProductItem(newItem);
			litems.setModifiedBy(currentEmp);
			litems.setModifiedDate(today);
			
			entityManager.persist(litems);
			
			
			PartcodeChangeRequest pcr=new PartcodeChangeRequest();
			pcr.setApprovedStatus(true);
			pcr.setAssignedTo("USER:"+user.getUsername());
			pcr.setConsumedProduct(oldItem.getProduct());
			pcr.setConsumedProductSerialNumber(oldSerial);
			pcr.setCreateDate(today);
			pcr.setCreatedBy(currentEmp);
			pcr.setFaultyProduct(new Products(nPartcode));
			pcr.setFaultyProductSerialNumber(nSerialnumber);
			pcr.setFlowType(PartcodeChangeRequestRepository.FLOW_TYPE_NON_SA);
			pcr.setInitiatedBy(currentEmp);
			pcr.setLocationName(sr.getLocation().getLocationName());
			pcr.setLegalEntity(sr.getLocation().getLegalEntity().getLegalEntityName());
			//pcr.setManualId(manualId);
			pcr.setModifiedBy(currentEmp);
			pcr.setModifiedDate(today);
			pcr.setRequestDate(today);
			pcr.setSourceLocation(sr.getLocation());
			
			
			entityManager.persist(pcr);
			
			String pcrManualId="PCR-"+MiscellaneousUtils.fillZero(String.valueOf(pcr.getId()), 5);
			
			pcr.setManualId(pcrManualId);
			
			entityManager.persist(pcr);
			
			PartcodeChangeRequestApproval pcrA=new PartcodeChangeRequestApproval();
			pcrA.setApprovalDate(today);
			pcrA.setChangeRequest(pcr);
			pcrA.setCreatedBy(currentEmp);
			pcrA.setCreatedDate(today);
			pcrA.setLocation(sr.getLocation());
			pcrA.setModifiedBy(currentEmp);
			pcrA.setModifiedDate(today);
			pcrA.setNewPartCode(nPartcode);
			pcrA.setNewSerialnumber(nSerialnumber);
			pcrA.setOldPartcode(oldItem.getProduct().getProductPartCode());
			pcrA.setOldSerialNumber(oldSerial);
			pcrA.setRemarks(requestParam.get("remark").toString());
			pcrA.setSynced(false);
			
			entityManager.persist(pcrA);
			
			pLineItem=new ProductLineItem();
			pLineItem.setAccountName(litems.getCustomerName());
			pLineItem.setAccountSapNumber(litems.getCustomerSapNumber());
			pLineItem.setCreatedBy(currentEmp);
			pLineItem.setCreatedDate(today);
			pLineItem.setCustomerInventory(litems.isCustomerInventory());
			pLineItem.setModifiedBy(currentEmp);
			pLineItem.setModifiedDate(today);
			pLineItem.setPosition(""+sr.getLocation().getLocationId());
			pLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
			pLineItem.setProduct(new Products(nPartcode));
			pLineItem.setRefurbished(litems.isRefurbished());
			pLineItem.setSerialNumber(nSerialnumber);
			pLineItem.setState(litems.getLocationProeuctLineItemState());
			pLineItem.setStatus(litems.getLocationProductLineItemStatus());
			
			entityManager.persist(pLineItem);
			
		}
								
	}
	
	private void checkPendingPartcodeChangeRequest(EntityManager em,Products partcode,String serialnum) throws BadRequestException{
		PartcodeChangeRequest pcr=null;
		try {
			pcr=em.createQuery("select pcr from PartcodeChangeRequest pcr where pcr.faultyProduct=?1 and pcr.faultyProductSerialNumber=?2 and pcr.approvedStatus=?3 and pcr.flowType=?4",PartcodeChangeRequest.class)
					.setParameter(1, partcode)
					.setParameter(2, serialnum)
					.setParameter(3,false)
					.setParameter(4, PartcodeChangeRequestRepository.FLOW_TYPE_SA)
					.getSingleResult();
			throw new BadRequestException(" Part code change request is pending for approval of part code "+partcode.getProductPartCode()+" and serial number "+serialnum);
		}catch(NoResultException ex) {
			
		}catch(NonUniqueResultException ex) {
			
			throw new BadRequestException("Multiple part code change request is pending for approval of part code "+partcode.getProductPartCode()+" and serial number "+serialnum);
		}
		pcr=null;
		try {
			pcr=em.createQuery("select pcr from PartcodeChangeRequest pcr where pcr.faultyProduct=?1 and pcr.faultyProductSerialNumber=?2 and pcr.approvedStatus=?3 and pcr.flowType=?4",PartcodeChangeRequest.class)
					.setParameter(1, partcode)
					.setParameter(2, serialnum)
					.setParameter(3,false)
					.setParameter(4, PartcodeChangeRequestRepository.FLOW_TYPE_NON_SA)
					.getSingleResult();
			throw new BadRequestException(" Part code change request is pending for approval of part code "+partcode.getProductPartCode()+" and serial number "+serialnum);
		}catch(NoResultException ex) {
			
		}catch(NonUniqueResultException ex) {
			
			throw new BadRequestException("Multiple part code change request is pending for approval of part code "+partcode.getProductPartCode()+" and serial number "+serialnum);
		}
		
		pcr=null;
		try {
			pcr=em.createQuery("select pcr from PartcodeChangeRequest pcr where pcr.consumedProduct=?1 and pcr.consumedProductSerialNumber=?2 and pcr.approvedStatus=?3 and pcr.flowType=?4",PartcodeChangeRequest.class)
					.setParameter(1, partcode)
					.setParameter(2, serialnum)
					.setParameter(3,false)
					.setParameter(4, PartcodeChangeRequestRepository.FLOW_TYPE_NON_SA)
					.getSingleResult();
			throw new BadRequestException(" Part code change request is pending for approval of part code "+partcode.getProductPartCode()+" and serial number "+serialnum);
		}catch(NoResultException ex) {
			
		}catch(NonUniqueResultException ex) {
			
			throw new BadRequestException("Multiple part code change request is pending for approval of part code "+partcode.getProductPartCode()+" and serial number "+serialnum);
		}
	}

}
