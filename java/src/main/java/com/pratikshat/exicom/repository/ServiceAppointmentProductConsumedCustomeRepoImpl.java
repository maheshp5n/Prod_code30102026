package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.Assets;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LegalEntity;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LocationProductItem;
import com.pratikshat.exicom.entity.LocationProductLineItems;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.PartCodechangeApproval;
import com.pratikshat.exicom.entity.PartcodeChangeRequest;
import com.pratikshat.exicom.entity.PartcodeChangeRequestApproval;
import com.pratikshat.exicom.entity.PartcodeChangeSa;
import com.pratikshat.exicom.entity.ProductConsumptionType;
import com.pratikshat.exicom.entity.ProductLineItem;
import com.pratikshat.exicom.entity.ProductLineItemProductState;
import com.pratikshat.exicom.entity.ProductLineItemStatus;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.entity.ServiceAppoinmentProductConsumed;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.entity.ServiceAppointmentProductConsumedLineItems;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ServiceAppointmentProductConsumedLineitemRequest;
import com.pratikshat.exicom.model.request.ServiceAppointmentProductConsumedRequest;

@org.springframework.stereotype.Repository
public class ServiceAppointmentProductConsumedCustomeRepoImpl implements ServiceAppointmentProductConsumedCustomeRepo {
	@javax.persistence.PersistenceContext
	javax.persistence.EntityManager entityManager;
	
	@Autowired
	ProductLineItemRepository dbLineItemRepo;
	
	@Autowired
	PartCodechangeApprovalRepository dbChangeApproval;

	@Autowired
	UserProfileRepository dbProfile;
	
	private static final Logger logger=LoggerFactory.getLogger(ServiceAppointmentProductConsumedCustomeRepoImpl.class);

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public void consumeProducts(ServiceAppointmentProductConsumedRequest consumedProduct, LoginUser user)
			throws Exception {
		
		ServiceAppointment serviceAppointment = null;
		try {
			javax.persistence.Query serviceAppointmentQuery=entityManager.createQuery("select srp from ServiceAppointment srp left join srp.ownerDetails onr left join srp.status st left join srp.claimStatus clst where srp.serviceAppointmentId=?1",com.pratikshat.exicom.entity.ServiceAppointment.class);
			serviceAppointmentQuery.setParameter(1, consumedProduct.getServiceAppointmentNumber());
			serviceAppointment=(ServiceAppointment)serviceAppointmentQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}

		
		if(serviceAppointment==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Appointment does not exist");
		}
		
		List<Long> claimStatusList = new ArrayList<Long>();
		claimStatusList.add(1L);	//Submitted
		claimStatusList.add(3L);	//Approved By SCH
		claimStatusList.add(5L);	//Approved By Finance
		claimStatusList.add(7L);	//Approved By RSH
		claimStatusList.add(9L);	//Posted in SAP
		
		if(claimStatusList.contains(serviceAppointment.getClaimStatus().getClaimStatusId())) {
			throw new BadRequestException("You can not edit this record.");
		}
		
		
		if(!serviceAppointment.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new com.pratikshat.exicom.exception.BadRequestException("You are not authorised");
		}
		
		String productType = null;
		try {
			javax.persistence.Query productTypeQuery=entityManager.createQuery("select pr.productType from Products pr where pr.productPartCode=?1",String.class);
			productTypeQuery.setParameter(1, consumedProduct.getPartCode());
			productType=(String)productTypeQuery.getSingleResult();	
		}catch(NoResultException ex) {
			throw new BadRequestException("Product Type not found");
		}
		
		if(productType==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Product Part Code does not exist");
		}
		if(!productType.equalsIgnoreCase("Component")) {
			consumedProduct.setConsumedQty(1);
		}
		if(!productType.equalsIgnoreCase("Component")&&consumedProduct.getProductLineItem().size()!=1) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Please provide Ok Item Serial Number/Or No Serial Number provided");
		}
		if(productType.equalsIgnoreCase("Component")&&consumedProduct.getProductLineItem().size()>0) throw new com.pratikshat.exicom.exception.BadRequestException("Component can not have serial number");
		ServiceRecource _serviceResource = null;
		try {
			javax.persistence.Query serviceResourceQuery=entityManager.createQuery("select sr from ServiceAppointment srp join srp.serviceResource sr join sr.location lco where srp.serviceAppointmentId=?1",com.pratikshat.exicom.entity.ServiceRecource.class);
			serviceResourceQuery.setParameter(1, consumedProduct.getServiceAppointmentNumber());
			_serviceResource=(ServiceRecource)serviceResourceQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		
		if(_serviceResource==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource not assigned in this service appointment");
		}
		
		com.pratikshat.exicom.entity.LocationProductItem items = null;
		try {
			javax.persistence.Query locationProductItemQuery=entityManager.createQuery("select litem from LocationProductItem litem join litem.product pr join litem.location loc where loc=?1 and pr.productPartCode=?2",com.pratikshat.exicom.entity.LocationProductItem.class);
			locationProductItemQuery.setParameter(1, _serviceResource.getLocation());
			locationProductItemQuery.setParameter(2,consumedProduct.getPartCode());
			items=(com.pratikshat.exicom.entity.LocationProductItem)locationProductItemQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		
		if(items == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource does not have product "+consumedProduct.getPartCode());
		}
		
		if(items.getOkQuantity()<consumedProduct.getConsumedQty()) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Quantity not avilable");
		}
		float okQuantity=items.getOkQuantity();
		float faultyQuantity=items.getFaultyQuantity();
		float refurbished=items.getRefurbished();
		
		okQuantity=okQuantity-consumedProduct.getConsumedQty();
		
		if(productType.equalsIgnoreCase("Component")&&consumedProduct.getConsumeType().equalsIgnoreCase("Replaced")) {
			
			throw new com.pratikshat.exicom.exception.BadRequestException("Component can not be replaced");
		}
		if(!productType.equalsIgnoreCase("Component")) {
			
			com.pratikshat.exicom.entity.ProductLineItemStatus _pstatus=entityManager.createQuery("select ps from ProductLineItemStatus ps where ps.statusName=?1",com.pratikshat.exicom.entity.ProductLineItemStatus.class)
					.setParameter(1,"Received")
					.getSingleResult();
			com.pratikshat.exicom.entity.ProductLineItemProductState _pstate=entityManager.createQuery("select ps from ProductLineItemProductState ps where ps.productStateName=?1",com.pratikshat.exicom.entity.ProductLineItemProductState.class)
					.setParameter(1, "Faulty")
					.getSingleResult();
			for(com.pratikshat.exicom.model.request.ServiceAppointmentProductConsumedLineitemRequest lItem:consumedProduct.getProductLineItem()) {
				
				LocationProductLineItems checkItems=null;
				try {
					checkItems=entityManager.createQuery("select litem from LocationProductLineItems litem where litem.locationProductItem=?1 and litem.itemSerialNumber=?2",LocationProductLineItems.class)
							.setParameter(1,items)
							.setParameter(2,lItem.getConsumedItemSerialNumber().trim())
							.getSingleResult();
				}catch(NoResultException ex) {
					throw new BadRequestException(String.format("Invalid Serial Number %s or Serial number not exist in your stock", lItem.getConsumedItemSerialNumber()));
				}
				catch(NonUniqueResultException ex) {
					throw new BadRequestException(String.format("Invalid Serial Number %s, Multiple stock found", lItem.getConsumedItemSerialNumber()));
				}
				if(checkItems.isCustomerInventory())
					throw new BadRequestException(String.format("Can not consume or replace serial number %s, As is customer inventory", lItem.getConsumedItemSerialNumber()));
				
				if(checkItems.getLocationProeuctLineItemState().getProductStateId()!=1)
					throw new BadRequestException(String.format("Can not consume or replace serial number %s, As is not ok inventory", lItem.getConsumedItemSerialNumber()));
				
				if(checkItems.isRefurbished()) {
					refurbished--;
					lItem.setRefurbished(true);
				}
				//UPDATE PRODUCT LINE ITEM STATUS AND STATE OF CONSUMED PRODUCT SERIAL NUMBER;
				ProductLineItem productLineItem=dbLineItemRepo.findByProductAndSerialNumber(items.getProduct(), lItem.getConsumedItemSerialNumber());
				Assets _asset=null;
				try {
					_asset=entityManager.createQuery("select ast from ServiceAppointment sa join sa.caseDetails cs join cs.assets ast where sa=?1",Assets.class).setParameter(1, serviceAppointment).getSingleResult();
					
				}catch(Exception ex) {
					
				}
				if(productLineItem!=null) {
					if(!productLineItem.getPositionState().equalsIgnoreCase(ProductLineItemRepository.AT_LOCATION))
						throw new BadRequestException("Invalid serial number "+lItem.getConsumedItemSerialNumber());
					productLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
					productLineItem.setModifiedDate(new Date());
					productLineItem.setStatus(new ProductLineItemStatus(3));
					productLineItem.setPositionState(ProductLineItemRepository.ATTACHED_TO_ASSETS);
					
					productLineItem.setPosition(_asset.getSerialNumber());
					dbLineItemRepo.save(productLineItem);
				}
				else {
					productLineItem=new ProductLineItem();
					productLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
					productLineItem.setCreatedDate(new Date());
					productLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
					productLineItem.setModifiedDate(new Date());
					productLineItem.setPosition(_asset.getSerialNumber());
					productLineItem.setPositionState(ProductLineItemRepository.ATTACHED_TO_ASSETS);
					productLineItem.setProduct(items.getProduct());
					productLineItem.setSerialNumber(lItem.getConsumedItemSerialNumber());
					productLineItem.setState(new ProductLineItemProductState(1));
					productLineItem.setStatus(new ProductLineItemStatus(3));
					dbLineItemRepo.save(productLineItem);
				}
				
				
				Query removeOkSerialNumber=entityManager.createQuery("delete from LocationProductLineItems litem where litem.locationProductItem=?1 and litem.itemSerialNumber=?2");
				removeOkSerialNumber.setParameter(1, items);
				removeOkSerialNumber.setParameter(2, lItem.getConsumedItemSerialNumber());
				removeOkSerialNumber.executeUpdate();
				
				if(consumedProduct.getConsumeType().equalsIgnoreCase("Replaced")) {
					faultyQuantity=faultyQuantity+1;
					com.pratikshat.exicom.entity.LocationProductLineItems fLineItem=null;
					try {
						fLineItem=entityManager.createQuery("select item from LocationProductLineItems item join item.locationProductItem pd  where item.itemSerialNumber=?1 and pd.product=?2",com.pratikshat.exicom.entity.LocationProductLineItems.class)
								.setParameter(1, lItem.getReplacedItemSerialNumber())
								.setParameter(2,items.getProduct())
								.getSingleResult();
						throw new BadRequestException("Please check faulty Serial Number");
					}catch(NonUniqueResultException ex) {
						throw new BadRequestException("Please check faulty Serial Number ");
					}
					catch(NoResultException ex) {
						
					}
					if(fLineItem!=null) throw new BadRequestException("Please check faulty Serial Number");
					
					fLineItem=new com.pratikshat.exicom.entity.LocationProductLineItems();
					fLineItem.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
					fLineItem.setCreatedDate(new java.util.Date());
					fLineItem.setInStock(true);
					fLineItem.setItemSerialNumber(lItem.getReplacedItemSerialNumber());
					fLineItem.setLocationProductItem(items);
					fLineItem.setModifiedDate(new java.util.Date());
					fLineItem.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
					fLineItem.setLocationProductLineItemStatus(new com.pratikshat.exicom.entity.ProductLineItemStatus(_pstatus.getStatusId()));
					fLineItem.setLocationProeuctLineItemState(new com.pratikshat.exicom.entity.ProductLineItemProductState(_pstate.getProductStateId()));
					entityManager.persist(fLineItem);
					
					ProductLineItem fProductLineItems=dbLineItemRepo.findByProductAndSerialNumber(items.getProduct(), lItem.getReplacedItemSerialNumber());
					if(fProductLineItems!=null) {
						if(!fProductLineItems.getPositionState().equalsIgnoreCase(ProductLineItemRepository.ATTACHED_TO_ASSETS))
							throw new BadRequestException("Invalid faulty serial number "+fProductLineItems.getSerialNumber());
						fProductLineItems.setModifiedBy(new EmployeeDetails(user.getUsername()));
						fProductLineItems.setModifiedDate(new Date());
						fProductLineItems.setPosition(String.valueOf(items.getLocation().getLocationId()));
						fProductLineItems.setPositionState(ProductLineItemRepository.AT_LOCATION);
						fProductLineItems.setState(new com.pratikshat.exicom.entity.ProductLineItemProductState(_pstate.getProductStateId()));
						fProductLineItems.setStatus(new com.pratikshat.exicom.entity.ProductLineItemStatus(_pstatus.getStatusId()));
						dbLineItemRepo.save(fProductLineItems);
					}
					else {
						fProductLineItems=new ProductLineItem();
						fProductLineItems.setCreatedBy(new EmployeeDetails(user.getUsername()));
						fProductLineItems.setCreatedDate(new Date());
						fProductLineItems.setModifiedBy(new EmployeeDetails(user.getUsername()));
						fProductLineItems.setModifiedDate(new Date());
						fProductLineItems.setPosition(String.valueOf(items.getLocation().getLocationId()));
						fProductLineItems.setPositionState(ProductLineItemRepository.AT_LOCATION);
						fProductLineItems.setProduct(items.getProduct());
						fProductLineItems.setSerialNumber(lItem.getReplacedItemSerialNumber());
						fProductLineItems.setState(new com.pratikshat.exicom.entity.ProductLineItemProductState(_pstate.getProductStateId()));
						fProductLineItems.setStatus(new com.pratikshat.exicom.entity.ProductLineItemStatus(_pstatus.getStatusId()));
						dbLineItemRepo.save(fProductLineItems);
					}
				}
				
			}
		}
		
		
		items.setOkQuantity(okQuantity);
		items.setFaultyQuantity(faultyQuantity);
		items.setQuantityInOnHand(okQuantity+faultyQuantity);
		items.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		items.setRefurbished(refurbished);
		items.setModifiedDate(new java.util.Date());
		
		entityManager.persist(items);
		
		com.pratikshat.exicom.entity.ServiceAppoinmentProductConsumed _sProductConsumed=new com.pratikshat.exicom.entity.ServiceAppoinmentProductConsumed();
		_sProductConsumed.setConsumptionType(new com.pratikshat.exicom.entity.ProductConsumptionType(consumedProduct.getConsumeType()));
		_sProductConsumed.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_sProductConsumed.setCreatedDate(new java.util.Date());
		_sProductConsumed.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_sProductConsumed.setModifiedDate(new java.util.Date());
		_sProductConsumed.setProduct((items.getProduct()));
		_sProductConsumed.setQuantity(consumedProduct.getConsumedQty());
		_sProductConsumed.setServiceAppointment(new com.pratikshat.exicom.entity.ServiceAppointment(consumedProduct.getServiceAppointmentNumber()));
		_sProductConsumed.setSynked(false);
		_sProductConsumed.setRemark(consumedProduct.getRemark());
		entityManager.persist(_sProductConsumed);
		
		for(com.pratikshat.exicom.model.request.ServiceAppointmentProductConsumedLineitemRequest lItem:consumedProduct.getProductLineItem()) {
			com.pratikshat.exicom.entity.ServiceAppointmentProductConsumedLineItems _sLitem=new com.pratikshat.exicom.entity.ServiceAppointmentProductConsumedLineItems();
			_sLitem.setProductConsumed(new com.pratikshat.exicom.entity.ServiceAppoinmentProductConsumed( _sProductConsumed.getId()));
			_sLitem.setQty(lItem.getQty()==0?1:lItem.getQty());
			_sLitem.setConsumedLineItemSerialNumber(lItem.getConsumedItemSerialNumber());
			_sLitem.setReplacedLineItemSerialNumber(lItem.getReplacedItemSerialNumber());
			_sLitem.setSynked(false);
			_sLitem.setRefurbished(lItem.isRefurbished());
			entityManager.persist(_sLitem);
		}
	}
	/*
	 * 
	 * 
	 * <p>Before creating delete process please make sure to return all stock back to Location which was consumed during service appointment product consumed part</p>
	 * */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public void deleteProducts(long consumeid,com.pratikshat.exicom.entity.LoginUser user) throws BadRequestException,UnauthoriseException {
		// STAPES
		// 1. FIND SERVICE RESOURCE
		// 2. FIND STOCK LOCATION 
		// 3. CHECK PRODUCT TYPE
		// 4. IF PRODUCT IS COMPONENT INCREASE STOCK BALANCE AND DELETE CONSUME ENTRY
		// 5. IF PRODUCT IS NOT COMPONENT 
		// 6. FIND OK SERIAL NUMBER AND ADD TO STOCK AND REMOVE FAULTY SERIAL NUMBER
		
		ServiceRecource _serviceResource=null;
		Location loc=null;
		Products prod=null;
		ServiceAppoinmentProductConsumed consumedProduct=null;
		ServiceAppointment sa=null;
		
		try {
			consumedProduct=entityManager.createQuery("select citem from ServiceAppoinmentProductConsumed citem join citem.serviceAppointment as sapt join sapt.caseDetails cs left join cs.assets ast join citem.product prd join sapt.serviceResource srv join sapt.caseDetails cs  where citem=?1",ServiceAppoinmentProductConsumed.class)
					.setParameter(1, new ServiceAppoinmentProductConsumed(consumeid))
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Invalid Id");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Invalid Id");
		}
		
		sa=consumedProduct.getServiceAppointment();
		_serviceResource=sa.getServiceResource();
		loc=_serviceResource.getLocation();
		prod=consumedProduct.getProduct();
		
		List<Long> claimStatusList = new ArrayList<Long>();
		claimStatusList.add(1L);	//Submitted
		claimStatusList.add(3L);	//Approved By SCH
		claimStatusList.add(5L);	//Approved By Finance
		claimStatusList.add(7L);	//Approved By RSH
		claimStatusList.add(9L);	//Posted in SAP
		
		if(claimStatusList.contains(sa.getClaimStatus().getClaimStatusId())) {
			throw new BadRequestException("Can not remove item");
		}
			
		
		if(!user.getUsername().equalsIgnoreCase(_serviceResource.getUser().getEmailId()))
			throw new UnauthoriseException("You are not authorised");
		
		LocationProductItem locItem=null;
		
		try {
			locItem=entityManager.createQuery("select item from LocationProductItem item where item.location=?1 and item.product=?2",LocationProductItem.class)
					.setParameter(1, loc)
					.setParameter(2, prod)
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Product not found");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple result set found");
		}
		
		if(prod.getProductType().equalsIgnoreCase("Component")) {
			locItem.setOkQuantity(locItem.getOkQuantity()+consumedProduct.getQuantity());
			locItem.setQuantityInOnHand(locItem.getQuantityInOnHand()+consumedProduct.getQuantity());
			locItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			locItem.setModifiedDate(new Date());
			entityManager.persist(locItem);
		}else {
			List<ServiceAppointmentProductConsumedLineItems> clitems=null;
			try {
				clitems=entityManager.createQuery("select item from ServiceAppointmentProductConsumedLineItems item join item.productConsumed pcm where item.productConsumed=?1",ServiceAppointmentProductConsumedLineItems.class)
						.setParameter(1, consumedProduct)
						.getResultList();
			}catch(Exception ex) {
				throw new BadRequestException("Unable to find serial numbers");
			}
			for(ServiceAppointmentProductConsumedLineItems clitem:clitems) {
				if(clitem.isRefurbished())
					locItem.setRefurbished(locItem.getRefurbished()+1);
				if(consumedProduct.getConsumptionType().getConsumptionTypeName().equalsIgnoreCase("Sale")) {
					locItem.setOkQuantity(locItem.getOkQuantity()+1);
					locItem.setQuantityInOnHand(locItem.getQuantityInOnHand()+1);
					
					LocationProductLineItems llitems=new LocationProductLineItems();
					llitems.setCreatedBy(new EmployeeDetails(user.getUsername()));
					llitems.setModifiedBy(new EmployeeDetails(user.getUsername()));
					llitems.setCreatedDate(new Date());
					llitems.setCustomerInventory(false);
					llitems.setInStock(true);
					llitems.setItemSerialNumber(clitem.getConsumedLineItemSerialNumber());
					llitems.setLocationProductItem(locItem);
					//llitems.setLocationProductLineItemId(locationProductLineItemId);
					llitems.setLocationProductLineItemStatus(new ProductLineItemStatus(1));
					llitems.setLocationProeuctLineItemState(new ProductLineItemProductState(1));
					llitems.setModifiedDate(new Date());
					llitems.setRefurbished(clitem.isRefurbished());
					llitems.setReplaced(false);
					llitems.setReturned(false);
					entityManager.persist(llitems);
					
					//product line item update
					
					ProductLineItem _pLineItem=dbLineItemRepo.findByProductAndSerialNumber(locItem.getProduct(),clitem.getConsumedLineItemSerialNumber());
					if(_pLineItem!=null) {
						_pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
						_pLineItem.setModifiedDate(new Date());
						_pLineItem.setPosition(String.valueOf(locItem.getLocation().getLocationId()));
						_pLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
						_pLineItem.setStatus(new ProductLineItemStatus(1));
						_pLineItem.setState(new ProductLineItemProductState(1));
						dbLineItemRepo.save(_pLineItem);
					}
					else {
						_pLineItem=new ProductLineItem();
						_pLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
						_pLineItem.setCreatedDate(new Date());
						_pLineItem.setProduct(locItem.getProduct());
						_pLineItem.setSerialNumber(clitem.getConsumedLineItemSerialNumber());
						_pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
						_pLineItem.setModifiedDate(new Date());
						_pLineItem.setPosition(String.valueOf(locItem.getLocation().getLocationId()));
						_pLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
						_pLineItem.setStatus(new ProductLineItemStatus(1));
						_pLineItem.setState(new ProductLineItemProductState(1));
						dbLineItemRepo.save(_pLineItem);
					}
					
				}
				
				else {
					
					//REPLACE ITEM OLD
					
					LocationProductItem consumedItem;
					LocationProductItem faultyItem;
					try {
						consumedItem=entityManager.createQuery("select item from LocationProductItem item where item.product=?1 and item.location=?2",LocationProductItem.class)
								.setParameter(1, clitem.getProductConsumed().getProduct())
								.setParameter(2, _serviceResource.getLocation())
								.getSingleResult();
						consumedItem.setQuantityInOnHand(consumedItem.getQuantityInOnHand()+1);
						consumedItem.setOkQuantity(consumedItem.getOkQuantity()+1);
						consumedItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
						consumedItem.setModifiedDate(new Date());
						if(clitem.isRefurbished()) {
							consumedItem.setRefurbished(consumedItem.getRefurbished()+1);
						}
						entityManager.persist(consumedItem);
						
						LocationProductLineItems _existsConsumedLineItems=null;
						try {
							_existsConsumedLineItems=entityManager.createQuery("select litem from LocationProductLineItems litem join litem.locationProductItem item where litem.itemSerialNumber=?1 and item.product=?2",LocationProductLineItems.class)
									.setParameter(1, clitem.getConsumedLineItemSerialNumber())
									.setParameter(2, clitem.getProductConsumed().getProduct())
									.getSingleResult();
							
							throw new BadRequestException("Serial number "+clitem.getConsumedLineItemSerialNumber()+" can not return back to stock, Another serial number already exists");
							
						}catch(NoResultException ex) {
							_existsConsumedLineItems=new LocationProductLineItems();
							
							_existsConsumedLineItems.setCreatedBy(new EmployeeDetails(user.getUsername()));
							_existsConsumedLineItems.setCreatedDate(new Date());
							_existsConsumedLineItems.setCustomerInventory(false);
							_existsConsumedLineItems.setInStock(true);
							_existsConsumedLineItems.setItemSerialNumber(clitem.getConsumedLineItemSerialNumber().trim());
							_existsConsumedLineItems.setLocationProductItem(consumedItem);
							//_existsConsumedLineItems.setLocationProductLineItemId(locationProductLineItemId);
							_existsConsumedLineItems.setLocationProductLineItemStatus(new ProductLineItemStatus(1));
							_existsConsumedLineItems.setLocationProeuctLineItemState(new ProductLineItemProductState(1));
							_existsConsumedLineItems.setModifiedBy(new EmployeeDetails(user.getUsername()));
							_existsConsumedLineItems.setModifiedDate(new Date());
							_existsConsumedLineItems.setRefurbished(clitem.isRefurbished());
							_existsConsumedLineItems.setRemarks("Return from SA Consumption ");
							_existsConsumedLineItems.setReplaced(false);
							_existsConsumedLineItems.setReturned(true);
							entityManager.persist(_existsConsumedLineItems);
							
							ProductLineItem cLineItem=dbLineItemRepo.findByProductAndSerialNumber(clitem.getProductConsumed().getProduct(),clitem.getConsumedLineItemSerialNumber());
							if(cLineItem!=null) {
								if(!cLineItem.getPositionState().equalsIgnoreCase(ProductLineItemRepository.ATTACHED_TO_ASSETS))
									throw new BadRequestException("Can not return back stock, Serial number is not attached with Assets");
								cLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
								cLineItem.setPosition(""+_serviceResource.getLocation().getLocationId());
								cLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
								cLineItem.setStatus(new ProductLineItemStatus(1));
								cLineItem.setState(new ProductLineItemProductState(1));
								cLineItem.setModifiedDate(new Date());
								dbLineItemRepo.save(cLineItem);
							}
							else {
								cLineItem=new ProductLineItem();
								cLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
								cLineItem.setCreatedDate(new Date());
								cLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
								cLineItem.setModifiedDate(new Date());
								cLineItem.setPosition(""+_serviceResource.getLocation().getLocationId());
								cLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
								cLineItem.setProduct(clitem.getProductConsumed().getProduct());
								cLineItem.setRefurbished(cLineItem.getRefurbished());
								cLineItem.setSerialNumber(clitem.getConsumedLineItemSerialNumber());
								cLineItem.setState(new ProductLineItemProductState(1));
								cLineItem.setStatus(new ProductLineItemStatus(1));
								dbLineItemRepo.save(cLineItem);
							}
							
						}catch(NonUniqueResultException ex) {
							throw new BadRequestException("Multiple serial number found with same seril number and product");
						}
						
					}
					catch(NoResultException ex) {
						throw new BadRequestException("Unable to find consumed product item");
					}
					catch(NonUniqueResultException ex) {
						throw new BadRequestException("Multipal Location product item found");
					}
					
					try {
						faultyItem=entityManager.createQuery("select item from LocationProductItem item where item.product=?1 and item.location=?2",LocationProductItem.class)
								.setParameter(1,clitem.getFaultyPartcode()==null?clitem.getProductConsumed().getProduct():new Products(clitem.getFaultyPartcode()))
								.setParameter(2,  _serviceResource.getLocation())
								.getSingleResult();
						
						faultyItem.setQuantityInOnHand(faultyItem.getQuantityInOnHand()-1);
						faultyItem.setFaultyQuantity(faultyItem.getFaultyQuantity()-1);
						faultyItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
						faultyItem.setModifiedDate(new Date());
						entityManager.persist(faultyItem);
						
						LocationProductLineItems faultyLineItem=null;
						
						try {
							faultyLineItem=entityManager.createQuery("select litem from LocationProductLineItems litem where litem.itemSerialNumber=?1 and litem.locationProductItem=?2",LocationProductLineItems.class)
									.setParameter(1, clitem.getReplacedLineItemSerialNumber().trim())
									.setParameter(2, faultyItem)
									.getSingleResult();
							ProductLineItem fLineItem=dbLineItemRepo.findByProductAndSerialNumber(clitem.getFaultyPartcode()==null?clitem.getProductConsumed().getProduct():new Products(clitem.getFaultyPartcode()),clitem.getReplacedLineItemSerialNumber().trim());
							if(fLineItem!=null) {
								if(!fLineItem.getPositionState().equalsIgnoreCase(ProductLineItemRepository.AT_LOCATION)) {
									throw new BadRequestException("Faulty serial number not found in stock, Many be in transfer so unable to return back item");
								}
								fLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
								fLineItem.setModifiedDate(new Date());
								if(sa.getCaseDetails().getAssets()!=null)
									fLineItem.setPosition(sa.getCaseDetails().getAssets().getSerialNumber());
								else
									fLineItem.setPosition(sa.getAppointmentNumber());
								fLineItem.setPositionState(ProductLineItemRepository.ATTACHED_TO_ASSETS);
								fLineItem.setState(new ProductLineItemProductState(1));
								fLineItem.setStatus(new ProductLineItemStatus(3));
								dbLineItemRepo.save(fLineItem);
							}else {
								fLineItem=new ProductLineItem();
								fLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
								fLineItem.setCreatedDate(new Date());
								fLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
								fLineItem.setModifiedDate(new Date());
								if(sa.getCaseDetails().getAssets()!=null)
									fLineItem.setPosition(sa.getCaseDetails().getAssets().getSerialNumber());
								else
									fLineItem.setPosition(sa.getAppointmentNumber());
								fLineItem.setPositionState(ProductLineItemRepository.ATTACHED_TO_ASSETS);
								fLineItem.setState(new ProductLineItemProductState(1));
								fLineItem.setStatus(new ProductLineItemStatus(3));
								fLineItem.setProduct(clitem.getFaultyPartcode()==null?clitem.getProductConsumed().getProduct():new Products(clitem.getFaultyPartcode()));
								fLineItem.setSerialNumber(clitem.getReplacedLineItemSerialNumber().trim());
								dbLineItemRepo.save(fLineItem);
							}
							
							entityManager.createQuery("delete from LocationProductLineItems litem where litem=?1").setParameter(1, faultyLineItem).executeUpdate();
							
						}catch(NoResultException ex) {
							throw new BadRequestException("Faulty serial number not found in stock, Many be in transfer so unable to return back item");
						}catch(NonUniqueResultException ex) {
							throw new BadRequestException("Multiple serial number found");
						}
						
					}catch(NoResultException ex) {
						throw new BadRequestException("Faulty product not found");
					}catch(NonUniqueResultException ex) {
						throw new BadRequestException("Multiple Faulty product part code found");
					}
					
//					//REPLACED ITEM old
//					
//					LocationProductLineItems llitems=null;
//					try {
//						llitems=entityManager.createQuery("select litem from LocationProductLineItems litem where litem.locationProductItem=?1 and litem.itemSerialNumber=?2",LocationProductLineItems.class)
//								.setParameter(1, locItem)
//								.setParameter(2, clitem.getReplacedLineItemSerialNumber())
//								.getSingleResult();
//					}catch(NoResultException ex) {
//						throw new BadRequestException(String.format("Serial number %s not found", clitem.getReplacedLineItemSerialNumber()));
//					}catch(NonUniqueResultException ex) {
//						throw new BadRequestException(String.format("Multiple Serial number %s found", clitem.getReplacedLineItemSerialNumber()));
//					}
//					llitems.setItemSerialNumber(clitem.getConsumedLineItemSerialNumber());
//					llitems.setLocationProeuctLineItemState(new ProductLineItemProductState(1));
//					if(clitem.isRefurbished())
//						llitems.setRefurbished(true);
//					llitems.setModifiedBy(new EmployeeDetails(user.getUsername()));
//					llitems.setModifiedDate(new Date());
//					
//					entityManager.persist(llitems);
//					
//					ProductLineItem _pLineItem=dbLineItemRepo.findByProductAndSerialNumber(locItem.getProduct(),clitem.getConsumedLineItemSerialNumber());
//					if(_pLineItem!=null) {
//						_pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
//						_pLineItem.setModifiedDate(new Date());
//						_pLineItem.setPosition(String.valueOf(locItem.getLocation().getLocationId()));
//						_pLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
//						_pLineItem.setStatus(new ProductLineItemStatus(1));
//						_pLineItem.setState(new ProductLineItemProductState(1));
//						dbLineItemRepo.save(_pLineItem);
//					}
//					else {
//						_pLineItem=new ProductLineItem();
//						_pLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
//						_pLineItem.setCreatedDate(new Date());
//						_pLineItem.setProduct(locItem.getProduct());
//						_pLineItem.setSerialNumber(clitem.getConsumedLineItemSerialNumber());
//						_pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
//						_pLineItem.setModifiedDate(new Date());
//						_pLineItem.setPosition(String.valueOf(locItem.getLocation().getLocationId()));
//						_pLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
//						_pLineItem.setStatus(new ProductLineItemStatus(1));
//						_pLineItem.setState(new ProductLineItemProductState(1));
//						dbLineItemRepo.save(_pLineItem);
//					}
//					
//					ProductLineItem _fLineItem=dbLineItemRepo.findByProductAndSerialNumber(locItem.getProduct(),clitem.getReplacedLineItemSerialNumber());
//					if(_fLineItem!=null) {
//						_fLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
//						_fLineItem.setModifiedDate(new Date());
//						if(sa.getCaseDetails()!=null&sa.getCaseDetails().getAssets()!=null)
//							_fLineItem.setPosition(sa.getCaseDetails().getAssets().getSerialNumber());
//						else
//							_fLineItem.setPosition(null);
//						
//						_fLineItem.setPositionState(ProductLineItemRepository.ATTACHED_TO_ASSETS);
//						_fLineItem.setStatus(new ProductLineItemStatus(3));
//						_fLineItem.setState(new ProductLineItemProductState(1));
//						dbLineItemRepo.save(_pLineItem);
//					}
//					else {
//						_fLineItem=new ProductLineItem();
//						_fLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
//						_fLineItem.setCreatedDate(new Date());
//						_fLineItem.setProduct(locItem.getProduct());
//						_fLineItem.setSerialNumber(clitem.getConsumedLineItemSerialNumber());
//						_fLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
//						_fLineItem.setModifiedDate(new Date());
//						if(sa.getCaseDetails()!=null&sa.getCaseDetails().getAssets()!=null)
//							_fLineItem.setPosition(sa.getCaseDetails().getAssets().getSerialNumber());
//						else
//							_fLineItem.setPosition(null);
//						_fLineItem.setPositionState(ProductLineItemRepository.ATTACHED_TO_ASSETS);
//						_fLineItem.setStatus(new ProductLineItemStatus(3));
//						_fLineItem.setState(new ProductLineItemProductState(1));
//						dbLineItemRepo.save(_pLineItem);
//					}
					
					PartcodeChangeRequest chReq=null;
					try {
						chReq=entityManager.createQuery("select req from PartcodeChangeRequest req where req.saId=?1 and req.consumedProduct=?2 and req.consumedProductSerialNumber=?3",PartcodeChangeRequest.class)
								.setParameter(1,""+ sa.getServiceAppointmentId())
								.setParameter(2,clitem.getProductConsumed().getProduct())
								.setParameter(3, clitem.getConsumedLineItemSerialNumber())
								.getSingleResult();
					}catch(NoResultException ex) {
						
					}
					if(chReq!=null&&chReq.getApprovedStatus())
						throw new BadRequestException("Unable to change delete as part code change request has been approved");
					
					entityManager.createQuery("delete from PartcodeChangeRequest req where req.saId=?1 and req.consumedProduct=?2 and req.consumedProductSerialNumber=?3")
						.setParameter(1,""+ sa.getServiceAppointmentId())
						.setParameter(2,clitem.getProductConsumed().getProduct())
						.setParameter(3, clitem.getConsumedLineItemSerialNumber())
						.executeUpdate();
					
					entityManager.createQuery("delete from PartcodeChangeSa req where req.saId=?1 and req.consumedProduct=?2 and req.consumedProductSerialNumber=?3")
					.setParameter(1,""+ sa.getServiceAppointmentId())
					.setParameter(2,clitem.getProductConsumed().getProduct())
					.setParameter(3, clitem.getConsumedLineItemSerialNumber())
					.executeUpdate();
					
				}
				entityManager.persist(locItem);
				
				entityManager.createQuery("delete from ServiceAppointmentProductConsumedLineItems litem where litem=?1").setParameter(1, clitem).executeUpdate();
				
			}
		}
		
		entityManager.createQuery("delete from ServiceAppoinmentProductConsumed item where item=?1").setParameter(1, consumedProduct).executeUpdate();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public void consumeProductsWithFaultyProduct(ServiceAppointmentProductConsumedRequest consumedProduct, LoginUser user)
			throws Exception {
		
		Products okProduct=null;
		Products faultyProducts=null;
		String okType=null;
		String faultyType=null;
		float qty=consumedProduct.getConsumedQty();
		ServiceAppointment sa=null;
		ServiceRecource sr=null;
		Location loc=null;
		LocationProductItem okProductItem=null;
		LocationProductItem faultyProductItem=null;
		String role="";
		LegalEntity lg=null;
		
		Iterator<LoginUserRoles> ite=(Iterator<LoginUserRoles>)user.getAuthorities().iterator();
		while(ite.hasNext()) {
			LoginUserRoles rl=ite.next();
			role=rl.getAuthority();
			break;
		}
		
		ProductConsumptionType consumeType=new ProductConsumptionType(consumedProduct.getConsumeType());
		
		
		
		try {
			sa=entityManager.createQuery("select sa from ServiceAppointment sa join sa.serviceResource sr join sr.location loc left join loc.legalEntity lg where sa=?1",ServiceAppointment.class)
					.setParameter(1,new ServiceAppointment(consumedProduct.getServiceAppointmentNumber()))
					.getSingleResult();
			
			List<Long> claimStatusList = new ArrayList<Long>();
			claimStatusList.add(1L);	//Submitted
			claimStatusList.add(3L);	//Approved By SCH
			claimStatusList.add(5L);	//Approved By Finance
			claimStatusList.add(7L);	//Approved By RSH
			claimStatusList.add(9L);	//Posted in SAP
			
			if(claimStatusList.contains(sa.getClaimStatus().getClaimStatusId())) {
				throw new BadRequestException("Can not consume product to this SA");
			}
		
			sr=sa.getServiceResource();
			lg=sr.getLocation().getLegalEntity();
			
			if(!sr.getUser().getEmailId().equalsIgnoreCase(user.getUsername()))
				throw new BadRequestException("You are not authorised");
			
			if(!sa.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername()))
				throw new BadRequestException("Owner of SA and assigned eng are not same");
			
			loc=sr.getLocation();
			
		}catch(NoResultException ex) {
			throw new BadRequestException("Invalid service appointment id");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Invalid/Multipal service appointment id");
		}
		
		Assets _asset=null;
		try {
			_asset=entityManager.createQuery("select ast from ServiceAppointment sa join sa.caseDetails cs join cs.assets ast where sa=?1",Assets.class).setParameter(1, sa).getSingleResult();
			
		}catch(Exception ex) {
			
		}
		
		try {
			okProduct=entityManager.createQuery("select prd from Products prd where prd=?1",Products.class).setParameter(1, new Products(consumedProduct.getPartCode())).getSingleResult();
			okType=okProduct.getProductType();
		}catch(NoResultException ex) {
			throw new Exception("Not valid product part code "+consumedProduct.getPartCode());
		}catch(NonUniqueResultException ex) {
			throw new Exception("Not valid product part code "+consumedProduct.getPartCode()+" multiple result found");
		}
		
		try {
			okProductItem=entityManager.createQuery("select item from LocationProductItem item where item.product=?1 and item.location=?2",LocationProductItem.class)
					.setParameter(1, okProduct)
					.setParameter(2, loc)
					.getSingleResult();
			if(okProductItem.getOkQuantity()<qty)
				throw new Exception("You do not have enough quantity to consume for product part code "+okProduct.getProductPartCode());
			
		}catch(NoResultException ex) {
			throw new Exception("You do not have enough quantity to consume for product part code "+okProduct.getProductPartCode());
		}catch(NonUniqueResultException ex) {
			throw new Exception("Multiple records found for part code "+okProduct.getProductPartCode()+" at location "+loc.getLocationName());
		}
		
		if(okType.equalsIgnoreCase("Component")) {
			
			if(!consumeType.getConsumptionTypeName().equalsIgnoreCase("Consumed"))
				throw new Exception("Invalid consumption type");
			
			if(consumedProduct.getProductLineItem()!=null&&consumedProduct.getProductLineItem().size()>0) {
				throw new Exception("Component can not have serial number");
			}
			
			qty=consumedProduct.getConsumedQty();
			okProductItem.setOkQuantity(okProductItem.getOkQuantity()-qty);
			okProductItem.setQuantityInOnHand(okProductItem.getQuantityInOnHand()-qty);
			okProductItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			okProductItem.setModifiedDate(new Date());
			entityManager.persist(okProductItem);
			
			ServiceAppoinmentProductConsumed _consumed=new ServiceAppoinmentProductConsumed();
			_consumed.setConsumptionType(consumeType);
			_consumed.setCreatedBy(new EmployeeDetails(user.getUsername()));
			_consumed.setCreatedDate(new Date());
			//_consumed.setManualId(manualId);
			_consumed.setModifiedBy(new EmployeeDetails(user.getUsername()));
			_consumed.setModifiedDate(new Date());
			_consumed.setProduct(okProduct);
			_consumed.setQuantity(qty);
			_consumed.setRemark(consumedProduct.getRemark());
			_consumed.setServiceAppointment(sa);
			_consumed.setSynked(false);
			entityManager.persist(_consumed);
			
		}
		else {
			
			
			
			LocationProductLineItems okLocationProductLineItems=null;
			LocationProductLineItems faultyLocationProductLineItems=null;
			ProductLineItem okProductLineItem=null;
			ProductLineItem faultyProductLineItem=null;
			
			qty=1;
			if(consumeType.getConsumptionTypeName().equalsIgnoreCase("Consumed"))
				throw new BadRequestException("Invalid consumption type");
			if(consumedProduct.getProductLineItem()==null||consumedProduct.getProductLineItem().size()!=1)
				throw new BadRequestException("Invalid request");
			ServiceAppointmentProductConsumedLineitemRequest itemRequest=consumedProduct.getProductLineItem().get(0);
			
			if(consumedProduct.getPartCode().equalsIgnoreCase(itemRequest.getFaultyPartcode())&&itemRequest.getConsumedItemSerialNumber().trim().equalsIgnoreCase(itemRequest.getReplacedItemSerialNumber().trim()))
				throw new BadRequestException("Invalid faulty product serial number "+itemRequest.getReplacedItemSerialNumber());
			
			if(!isValidSerialNumber(itemRequest.getReplacedItemSerialNumber().trim()))
				throw new BadRequestException("Invalid faulty product serial number "+itemRequest.getReplacedItemSerialNumber().trim() +", Serial number should be between 1 to 16 digits alphanumeric");
			
			okProductLineItem=dbLineItemRepo.findByProductAndSerialNumber(okProduct, itemRequest.getConsumedItemSerialNumber());
			faultyProductLineItem=dbLineItemRepo.findByProductAndSerialNumber(faultyProducts==null||itemRequest.getFaultyPartcode().equalsIgnoreCase(okProduct.getProductPartCode())?okProduct:faultyProducts, itemRequest.getReplacedItemSerialNumber());
//		yha pr error aai thi
			if(okProductLineItem!=null) {
				if(!okProductLineItem.getPositionState().equalsIgnoreCase(ProductLineItemRepository.AT_LOCATION))
					throw new BadRequestException("Invalid product serial number "+itemRequest.getConsumedItemSerialNumber());
				okProductLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				okProductLineItem.setModifiedDate(new Date());
				if(_asset!=null)
					okProductLineItem.setPosition(_asset.getSerialNumber());
				okProductLineItem.setPositionState(ProductLineItemRepository.ATTACHED_TO_ASSETS);
				okProductLineItem.setState(new ProductLineItemProductState(1));
				okProductLineItem.setStatus(new ProductLineItemStatus(3));
				dbLineItemRepo.save(okProductLineItem);
			}
			if(faultyProductLineItem!=null) {
				if(!faultyProductLineItem.getPositionState().equalsIgnoreCase(ProductLineItemRepository.ATTACHED_TO_ASSETS))
					throw new BadRequestException("Duplicate/Invalid faulty product serial number "+itemRequest.getReplacedItemSerialNumber());
				faultyProductLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				faultyProductLineItem.setModifiedDate(new Date());
				faultyProductLineItem.setPosition(String.valueOf(loc.getLocationId()));
				faultyProductLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
				faultyProductLineItem.setState(new ProductLineItemProductState(2));
				faultyProductLineItem.setStatus(new ProductLineItemStatus(1));
				dbLineItemRepo.save(faultyProductLineItem);
			}
			if(itemRequest.getFaultyPartcode()==null||itemRequest.getFaultyPartcode().equalsIgnoreCase(okProduct.getProductPartCode())) {
				faultyProducts=okProduct;
				faultyProductItem=okProductItem;
				faultyType=okType;
				if(itemRequest.getFaultyPartcode()==null)
					itemRequest.setFaultyPartcode(okProduct.getProductPartCode());
				
				if(itemRequest.getConsumedItemSerialNumber().trim().equalsIgnoreCase(itemRequest.getReplacedItemSerialNumber().trim())) {
					throw new BadRequestException("Consumed serial number "+itemRequest.getConsumedItemSerialNumber()+" and replaced serial number +"+itemRequest.getReplacedItemSerialNumber()+" cant be same");
				}
			}
			
			
			else {
				try {
					faultyProducts=entityManager.createQuery("select prd from Products prd where prd=?1",Products.class)
							.setParameter(1,new Products(itemRequest.getFaultyPartcode()))
							.getSingleResult();
					faultyType=faultyProducts.getProductType();
					
					if(okProduct.getSubProductFamily().getProductFamilySubProductId()!=faultyProducts.getSubProductFamily().getProductFamilySubProductId())
						throw new Exception("Product sub family mismatch");
					
				}catch(NoResultException ex) {
					throw new Exception("Invalid faulty part code"+itemRequest.getFaultyPartcode());
				}catch(NonUniqueResultException ex) {
					throw new Exception("Multiple record for faulty part code"+itemRequest.getFaultyPartcode()+" found");
				}
				
				try {
					faultyProductItem=entityManager.createQuery("select item from LocationProductItem item where item.location=?1 and item.product=?2",LocationProductItem.class)
							.setParameter(1, loc)
							.setParameter(2, faultyProducts)
							.getSingleResult();
				}catch(NoResultException ex) {
					
					faultyProductItem=new LocationProductItem();
					faultyProductItem.setBerQty(0);
					faultyProductItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
					faultyProductItem.setCreatedDate(new Date());
					faultyProductItem.setCustomerInventory(0);
					faultyProductItem.setDoaQuantity(0);
					faultyProductItem.setFaultyQuantity(0);
					faultyProductItem.setHslQuantity(0);
					faultyProductItem.setHslStatus("L");
					faultyProductItem.setIssuedQunatity(0);
					faultyProductItem.setLocation(loc);
					//faultyProductItem.setLocationProductItemId(locationProductItemId);
					faultyProductItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
					faultyProductItem.setModifiedDate(new Date());
					faultyProductItem.setMslQuantity(0);
					faultyProductItem.setMslQuantityStatus("L");
					faultyProductItem.setOkQuantity(0);
					faultyProductItem.setProduct(faultyProducts);
					faultyProductItem.setQuantityInOnHand(0);
					faultyProductItem.setQuantityUnitOfMasur("NO");
					faultyProductItem.setRefurbished(0);
					faultyProductItem.setRejectedQty(0);
					faultyProductItem.setScrapQty(0);
					entityManager.persist(faultyProductItem);
					faultyProductItem.setLocationProductItemId(String.format("PI-%s",MiscellaneousUtils.fillZero(String.valueOf(faultyProductItem.getId()), 5)));
					entityManager.persist(faultyProductItem);
					
				}catch(NonUniqueResultException ex) {
					throw new Exception("Multiple record for faulty part code"+itemRequest.getFaultyPartcode()+" found at location "+loc.getLocationName());
				}
			}
			
			logger.info("Ok product type {} and faultyprodct type {}",okType,faultyType);
			
			if(!okType.equalsIgnoreCase(faultyType))
				throw new BadRequestException("Invalid product type for faulty product part code "+faultyProducts.getProductPartCode());
			
			try {
				okLocationProductLineItems=entityManager.createQuery("select litem from LocationProductLineItems litem join litem.locationProductItem item where item.product=?1 and litem.itemSerialNumber=?2",LocationProductLineItems.class)
						.setParameter(1, okProduct)
						.setParameter(2,itemRequest.getConsumedItemSerialNumber())
						.getSingleResult();
				if(okProductItem.getId()!=okLocationProductLineItems.getLocationProductItem().getId())
					throw new BadRequestException("Serial number "+itemRequest.getConsumedItemSerialNumber()+" of product part code "+okProduct.getProductPartCode()+" is not in your stock");
				
			}catch(NoResultException ex) {
				throw new BadRequestException("Not a valid OK serial number "+itemRequest.getConsumedItemSerialNumber());
			}catch(NonUniqueResultException ex) {
				throw new BadRequestException("Duplicate OK serial number"+itemRequest.getConsumedItemSerialNumber()+" found for part code"+okProduct.getProductPartCode());
			}
			
			try {
				faultyLocationProductLineItems=entityManager.createQuery("select litem from LocationProductLineItems litem join litem.locationProductItem item where item.product=?1 and litem.itemSerialNumber=?2",LocationProductLineItems.class)
						.setParameter(1, faultyProducts)
						.setParameter(2,itemRequest.getReplacedItemSerialNumber())
						.getSingleResult();
				throw new BadRequestException("Invalid/Duplicate faulty serial number"+itemRequest.getReplacedItemSerialNumber()+" found for part code"+faultyProducts.getProductPartCode());
			}catch(NoResultException ex) {
				
				faultyLocationProductLineItems=new LocationProductLineItems();
				faultyLocationProductLineItems.setCreatedBy(new EmployeeDetails(user.getUsername()));
				faultyLocationProductLineItems.setCreatedDate(new Date());
				faultyLocationProductLineItems.setCustomerInventory(false);
				faultyLocationProductLineItems.setInStock(true);
				faultyLocationProductLineItems.setItemSerialNumber(itemRequest.getReplacedItemSerialNumber());
				faultyLocationProductLineItems.setLocationProductItem(faultyProductItem);
				//faultyLocationProductLineItems.setLocationProductLineItemId(locationProductLineItemId);
				faultyLocationProductLineItems.setLocationProductLineItemStatus(new ProductLineItemStatus(1));
				faultyLocationProductLineItems.setLocationProeuctLineItemState(new ProductLineItemProductState(2));
				faultyLocationProductLineItems.setModifiedBy(new EmployeeDetails(user.getUsername()));
				faultyLocationProductLineItems.setModifiedDate(new Date());
				faultyLocationProductLineItems.setRefurbished(false);
				faultyLocationProductLineItems.setReplaced(false);
				faultyLocationProductLineItems.setReturned(false);
				entityManager.persist(faultyLocationProductLineItems);
				faultyLocationProductLineItems.setLocationProductLineItemId(String.format("PIL-%s", MiscellaneousUtils.fillZero(String.valueOf(faultyLocationProductLineItems.getId()),5)));
				entityManager.persist(faultyLocationProductLineItems);
				
			}catch(NonUniqueResultException ex) {
				throw new BadRequestException("Invalid/Duplicate faulty serial number"+itemRequest.getReplacedItemSerialNumber()+" found for part code"+faultyProducts.getProductPartCode());
			}
			
			if(okProductLineItem==null) {
				
				okProductLineItem=new ProductLineItem();
				okProductLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
				okProductLineItem.setCreatedDate(new Date());
				okProductLineItem.setCustomerInventory(false);
				okProductLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				okProductLineItem.setModifiedDate(new Date());
				if(_asset!=null)
					okProductLineItem.setPosition(_asset.getSerialNumber());
				okProductLineItem.setPositionState(ProductLineItemRepository.ATTACHED_TO_ASSETS);
				okProductLineItem.setProduct(okProduct);
				okProductLineItem.setRefurbished(false);
				okProductLineItem.setSerialNumber(itemRequest.getConsumedItemSerialNumber());
				okProductLineItem.setState(okLocationProductLineItems.getLocationProeuctLineItemState());
				okProductLineItem.setStatus(new ProductLineItemStatus(3));
				dbLineItemRepo.save(okProductLineItem);
			}
			if(faultyProductLineItem==null) {
				
				faultyProductLineItem=new ProductLineItem();
				faultyProductLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
				faultyProductLineItem.setCreatedDate(new Date());
				faultyProductLineItem.setCustomerInventory(false);
				faultyProductLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				faultyProductLineItem.setModifiedDate(new Date());
				faultyProductLineItem.setPosition(String.valueOf(loc.getLocationId()));
				faultyProductLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
				faultyProductLineItem.setProduct(faultyProducts);
				faultyProductLineItem.setRefurbished(false);
				faultyProductLineItem.setSerialNumber(itemRequest.getReplacedItemSerialNumber());
				faultyProductLineItem.setState(new ProductLineItemProductState(2));
				faultyProductLineItem.setStatus(new ProductLineItemStatus(1));
				dbLineItemRepo.save(faultyProductLineItem);
			}
			
			if(faultyProductItem.getId()==okProductItem.getId()) {
				
				okProductItem.setOkQuantity(okProductItem.getOkQuantity()-1);
				okProductItem.setFaultyQuantity(okProductItem.getFaultyQuantity()+1);
				if(okLocationProductLineItems.isRefurbished())
					okProductItem.setRefurbished(okProductItem.getRefurbished()-1);
				entityManager.persist(okProductItem);
				
			}
			else {
				
				okProductItem.setOkQuantity(okProductItem.getOkQuantity()-1);
				okProductItem.setQuantityInOnHand(okProductItem.getQuantityInOnHand()-1);
				if(okLocationProductLineItems.isRefurbished())
					okProductItem.setRefurbished(okProductItem.getRefurbished()-1);
				entityManager.persist(okProductItem);
				
				faultyProductItem.setFaultyQuantity(faultyProductItem.getFaultyQuantity()+1);
				faultyProductItem.setQuantityInOnHand(faultyProductItem.getQuantityInOnHand()+1);
				entityManager.persist(faultyProductItem);
			}
			
			entityManager.createQuery("delete from LocationProductLineItems litem where litem=?1").setParameter(1, okLocationProductLineItems).executeUpdate();
			
			ServiceAppoinmentProductConsumed _consumed=new ServiceAppoinmentProductConsumed();
			
			_consumed.setConsumptionType(consumeType);
			_consumed.setCreatedBy(new EmployeeDetails(user.getUsername()));
			_consumed.setCreatedDate(new Date());
			//_consumed.setManualId(manualId);
			_consumed.setModifiedBy(new EmployeeDetails(user.getUsername()));
			_consumed.setModifiedDate(new Date());
			_consumed.setProduct(okProduct);
			_consumed.setQuantity(qty);
			_consumed.setRemark(consumedProduct.getRemark());
			_consumed.setServiceAppointment(sa);
			_consumed.setSynked(false);
			
			entityManager.persist(_consumed);
			
			ServiceAppointmentProductConsumedLineItems _cItems=new ServiceAppointmentProductConsumedLineItems();
			_cItems.setConsumedLineItemSerialNumber(itemRequest.getConsumedItemSerialNumber());
			_cItems.setFaultyPartcode(faultyProducts.getProductPartCode());
			_cItems.setProductConsumed(_consumed);
			_cItems.setQty(1);
			_cItems.setRefurbished(okLocationProductLineItems.isRefurbished());
			_cItems.setReplacedLineItemSerialNumber(itemRequest.getReplacedItemSerialNumber());
			_cItems.setSynked(false);
			entityManager.persist(_cItems);
			
			if(!consumedProduct.getPartCode().trim().equalsIgnoreCase(itemRequest.getFaultyPartcode().trim())) {
				
				PartcodeChangeRequest _changeRequest=new PartcodeChangeRequest();
				_changeRequest.setApprovedStatus(false);
				
				List<PartCodechangeApproval> approvar=dbChangeApproval.findByActiveAndInitRoleLikeOrInitRoleLike(true, "%ROLE:"+role+"%", "%USER:"+user.getUsername()+"%");
				if(approvar.size()>0) {
					_changeRequest.setAssignedTo(approvar.get(0).getAssignedToRoles());	
				}
				
				_changeRequest.setCircle(sa.getCircle().getCircleName());
				_changeRequest.setConsumedProduct(okProduct);
				_changeRequest.setConsumedProductSerialNumber(itemRequest.getConsumedItemSerialNumber());
				_changeRequest.setCreateDate(new Date());
				_changeRequest.setCreatedBy(new EmployeeDetails(user.getUsername()));
				_changeRequest.setEngineerName(sr.getName());
				_changeRequest.setFaultyProduct(faultyProducts);
				_changeRequest.setFaultyProductSerialNumber(itemRequest.getReplacedItemSerialNumber());
				_changeRequest.setInitiatedBy(new EmployeeDetails(user.getUsername()));
				_changeRequest.setModifiedBy(new EmployeeDetails(user.getUsername()));
				_changeRequest.setModifiedDate(new Date());
				//_changeRequest.setPriceVariance(priceVariance);
				_changeRequest.setRequestDate(new Date());
				_changeRequest.setSaId(""+sa.getServiceAppointmentId());
				_changeRequest.setSaManualId(sa.getAppointmentNumber());
				_changeRequest.setSourceLocation(loc);
				_changeRequest.setFlowType(PartcodeChangeRequestRepository.FLOW_TYPE_SA);
				
				//ADITION AFTER MAIL FROM MANPREET ON 08-APR-2021
				
				_changeRequest.setLocationName(loc.getLocationName());
				_changeRequest.setLegalEntity(lg.getLegalEntityName());
				entityManager.persist(_changeRequest);
				String manualId="PCR-"+MiscellaneousUtils.fillZero(String.valueOf(_changeRequest.getId()), 5);
				_changeRequest.setManualId(manualId);
				entityManager.persist(_changeRequest);

				//ADITTIONAL AUTO APPROVAL FOR SCH IN CASE SCH GOES TO SITE AS SERVICE ENGINEER

				Map<String,String> roleMap=dbProfile.getAccountAndRoleGroup(user);
				if(roleMap!=null&&!roleMap.isEmpty()&&roleMap.get("roleGroup").equalsIgnoreCase("SCH")){
					PartcodeChangeRequestApproval autoApproval=new PartcodeChangeRequestApproval();
					autoApproval.setApprovalDate(new Date());
					autoApproval.setChangeRequest(_changeRequest);
					autoApproval.setCreatedBy(new EmployeeDetails(user.getUsername()));
					autoApproval.setCreatedDate(new Date());
					autoApproval.setId(_changeRequest.getId());
					autoApproval.setLocation(sr.getLocation());
					autoApproval.setModifiedBy(new EmployeeDetails(user.getUsername()));
					autoApproval.setModifiedDate(new Date());
					autoApproval.setNewPartCode(faultyProducts.getProductPartCode());
					autoApproval.setNewSerialnumber(itemRequest.getReplacedItemSerialNumber());
					autoApproval.setOldPartcode(itemRequest.getFaultyPartcode());
					autoApproval.setOldSerialNumber(itemRequest.getReplacedItemSerialNumber());
					autoApproval.setRemarks("Auto Approval");
					entityManager.persist(autoApproval);
					_changeRequest.setApprovedStatus(true);
					entityManager.merge(_changeRequest);
					
				}
				
			}
			else {
				
				PartcodeChangeSa _changeRequest=new PartcodeChangeSa();
				_changeRequest.setApprovedStatus(false);
				
				List<PartCodechangeApproval> approvar=dbChangeApproval.findByActiveAndInitRoleLikeOrInitRoleLike(true, "%ROLE:"+role+"%", "%USER:"+user.getUsername()+"%");
				if(approvar.size()>0) {
					_changeRequest.setAssignedTo(approvar.get(0).getAssignedToRoles());	
				}				
				_changeRequest.setCircle(sa.getCircle().getCircleName());
				_changeRequest.setConsumedProduct(okProduct);
				_changeRequest.setConsumedProductSerialNumber(itemRequest.getConsumedItemSerialNumber());
				_changeRequest.setCreateDate(new Date());
				_changeRequest.setCreatedBy(new EmployeeDetails(user.getUsername()));
				_changeRequest.setEngineerName(sr.getName());
				_changeRequest.setFaultyProduct(faultyProducts);
				_changeRequest.setFaultyProductSerialNumber(itemRequest.getReplacedItemSerialNumber());
				_changeRequest.setInitiatedBy(new EmployeeDetails(user.getUsername()));
				_changeRequest.setModifiedBy(new EmployeeDetails(user.getUsername()));
				_changeRequest.setModifiedDate(new Date());
				//_changeRequest.setPriceVariance(priceVariance);
				_changeRequest.setRequestDate(new Date());
				_changeRequest.setSaId(""+sa.getServiceAppointmentId());
				_changeRequest.setSaManualId(sa.getAppointmentNumber());
				_changeRequest.setSourceLocation(loc);
				_changeRequest.setFlowType(PartcodeChangeRequestRepository.FLOW_TYPE_NON_SA);
				_changeRequest.setAssigned(false);
				
				entityManager.persist(_changeRequest);
			}
		}
		
	}
	private boolean isValidSerialNumber(String s){
	    String pattern= "^[a-zA-Z0-9]*$";
	    if(!s.matches(pattern))
	    	return false;
	    if(s.length()<1||s.length()>16)
	    	return false;
	    
	    return true;
	}
	
}
