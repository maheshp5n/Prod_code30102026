package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LPReturnOrderReceiveLineItem;
import com.pratikshat.exicom.entity.LProductReturnOrder;
import com.pratikshat.exicom.entity.LProductReturnOrderItem;
import com.pratikshat.exicom.entity.LProductReturnOrderLineItem;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LocationProductItem;
import com.pratikshat.exicom.entity.LocationProductLineItems;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PartcodeChangeRequest;
import com.pratikshat.exicom.entity.ProductLineItem;
import com.pratikshat.exicom.entity.ProductLineItemProductState;
import com.pratikshat.exicom.entity.ProductLineItemStatus;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.TemplateDetails;
import com.pratikshat.exicom.entity.TemplateType;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.LpReturnOrderReceiveRequest;
import com.pratikshat.exicom.model.response.LocationProductRequestResponse;
import com.pratikshat.exicom.model.response.LpReturnOrderResponse;
import com.pratikshat.exicom.utils.MaterialTransferPackingList;
import com.pratikshat.exicom.utils.SmsNotifier;

public class LpReturnRepositoryCustomeImpl implements LpReturnRepositoryCustome {
	
	@javax.persistence.PersistenceContext
	javax.persistence.EntityManager em;
	
	@Autowired
	ProductLineItemRepository dbProductLineItem;
	
	@Autowired 
	JdbcTemplate jdbc;
	
	@Autowired
	FileStorageProperties fileStorageProperties;
	
	@Value("${feedbackurl}")
	String feedbackurl;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE,rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class,RuntimeException.class,SQLException.class})
	public LpReturnOrderReceiveRequest saveReturnOrderReceive(LpReturnOrderReceiveRequest receiveItem,
			EmployeeDetails user) throws BadRequestException, UnauthoriseException {
		com.pratikshat.exicom.entity.LProductReturnOrder _returnOrder=null;
		com.pratikshat.exicom.entity.Location _sourceLocation=null;
		com.pratikshat.exicom.entity.Location _destinationLocation=null;
		com.pratikshat.exicom.entity.EmployeeDetails _destOwner=null;
		ServiceRecource sr=em.createQuery("select sr from ServiceRecource sr where sr.user=?1",ServiceRecource.class).setParameter(1,user).getSingleResult();
		try {
			_returnOrder=em.createQuery("select rord from LProductReturnOrder rord join rord.sourceLocation sl join rord.destinationLocation dl join dl.owner onr where rord.lprOrderid=?1",com.pratikshat.exicom.entity.LProductReturnOrder.class)
				.setParameter(1, receiveItem.getLpReturnOrderId())
				.getSingleResult();
		}catch(javax.persistence.NoResultException ex) {
			throw new BadRequestException("No data found");
		}
		
		if(_returnOrder.getLocationReturnOrderStatus().equalsIgnoreCase("Draft"))
			throw new BadRequestException("Can not receive, Return order is in Draft mode");
		
		if(_returnOrder.getLocationReturnOrderStatus().equalsIgnoreCase("Archived"))
			throw new BadRequestException("Can not receive, Return order is in Archived mode");
		
		
		_sourceLocation=_returnOrder.getSourceLocation();
		_destinationLocation=_returnOrder.getDestinationLocation();
		_destOwner=_returnOrder.getDestinationLocation().getOwner();
		
		if(_destinationLocation.getLocationId()!=sr.getLocation().getLocationId())
			throw new UnauthoriseException("You are not authorised to receive item");
		
//		if(!_destOwner.getEmailId().equalsIgnoreCase(user.getEmailId())) {
//			
//		}
		com.pratikshat.exicom.entity.LPReturnOrderReceive _receive=new com.pratikshat.exicom.entity.LPReturnOrderReceive();
		_receive.setCreatedBy(user);
		_receive.setCreatedDate(new java.util.Date());
		_receive.setLocation(_destinationLocation);
		_receive.setModifiedBy(user);
		_receive.setModifirdBy(new java.util.Date());
		_receive.setOwner(user);
		_receive.setReceiveDate(new java.util.Date());
		_receive.setReturnOrder(_returnOrder);
		_receive.setTotalQuantityReceived(0);//will update with manual id;
		float totalItemReceived=0;
		em.persist(_receive);
		
		for(com.pratikshat.exicom.model.request.LpReturnOrderReceiveItem item:receiveItem.getItems()) {
			if(item.getSerialNumber().size()==0) {
				throw new BadRequestException("No item serial number found");
			}
			com.pratikshat.exicom.entity.LProductReturnOrderItem _lrItem=null;
			com.pratikshat.exicom.entity.LocationProductItem slocationItem=null;
			com.pratikshat.exicom.entity.Products products=null;
			try {
				_lrItem=em.createQuery("select item from LProductReturnOrderItem item join item.locationProductReturnOrder rto join item.productItem litem join item.product prd where item.lproItemid=?1 and rto.lprOrderid=?2",com.pratikshat.exicom.entity.LProductReturnOrderItem.class)
						.setParameter(1, item.getLpReturnOrderItemId())
						.setParameter(2, receiveItem.getLpReturnOrderId())
						.getSingleResult();
			}catch(javax.persistence.NoResultException ex) {
				throw new BadRequestException("No item found");
			}
			slocationItem=_lrItem.getProductItem();
			products=_lrItem.getProduct();
			com.pratikshat.exicom.entity.LocationProductItem dlocationItem=null;
			try {
				dlocationItem=em.createQuery("select item from LocationProductItem item where item.location=?1 and item.product=?2",com.pratikshat.exicom.entity.LocationProductItem.class)
						.setParameter(1, _destinationLocation)
						.setParameter(2, products)
						.getSingleResult();
				dlocationItem.setModifiedBy(user);
				dlocationItem.setModifiedDate(new java.util.Date());
		//		dlocationItem.setFaultyQuantity(dlocationItem.getFaultyQuantity()+item.getSerialNumber().size());
				dlocationItem.setQuantityInOnHand(dlocationItem.getQuantityInOnHand()+item.getSerialNumber().size());
				em.persist(dlocationItem);
				
			}catch(javax.persistence.NoResultException noex) {
				dlocationItem=new com.pratikshat.exicom.entity.LocationProductItem();
				dlocationItem.setCreatedBy(user);
				dlocationItem.setCreatedDate(new java.util.Date());
				dlocationItem.setCustomerInventory(0);
				dlocationItem.setFaultyQuantity(item.getSerialNumber().size());
				dlocationItem.setHslQuantity(0);
				dlocationItem.setHslStatus("L");
				dlocationItem.setIssuedQunatity(0);
				dlocationItem.setLocation(_destinationLocation);
				dlocationItem.setModifiedBy(user);
				dlocationItem.setModifiedDate(new java.util.Date());
				dlocationItem.setMslQuantity(0);
				dlocationItem.setMslQuantityStatus("L");
				dlocationItem.setOkQuantity(0);
				dlocationItem.setProduct(products);
				dlocationItem.setQuantityInOnHand(item.getSerialNumber().size());
				dlocationItem.setQuantityUnitOfMasur("NO");
				dlocationItem.setDoaQuantity(0);
				em.persist(dlocationItem);
				String manualId=String.format("PIL-%s",com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(dlocationItem.getId()), 5));
				dlocationItem.setLocationProductItemId(manualId);
				em.persist(dlocationItem);
			}
			com.pratikshat.exicom.entity.LPReturnOrderReceiveItem _receiveItems=new com.pratikshat.exicom.entity.LPReturnOrderReceiveItem();
			_receiveItems.setCreatedBy(user);
			_receiveItems.setCreatedDate(new java.util.Date());
			_receiveItems.setLocationProductItem(slocationItem);
			_receiveItems.setLocationProductReturnOrderItem(_lrItem);
			_receiveItems.setLocationProductReturnOrderReceive(_receive);
			_receiveItems.setModifiedBy(user);
			_receiveItems.setModifiedDate(new java.util.Date());
			_receiveItems.setOwner(user);
			_receiveItems.setProducts(products);
			_receiveItems.setReceiveDate(new java.util.Date());
			_receiveItems.setTotalReceiveQuantity(item.getSerialNumber().size());
			em.persist(_receiveItems);
			
			_lrItem.setModifiedBy(user);
			_lrItem.setModifiedDate(new java.util.Date());
			_lrItem.setTotalReceiveQuantity(_lrItem.getTotalReceiveQuantity()+item.getSerialNumber().size());
			em.persist(_lrItem);
			totalItemReceived+=item.getSerialNumber().size();
			float customerInventory=0;
			
			for(String serialNumber:item.getSerialNumber()) {
				
				//21212
				PartcodeChangeRequest request=null;
				try {
					request=em.createQuery("select item from PartcodeChangeRequest item where item.faultyProductSerialNumber=?1 and item.faultyProduct=?2 and item.flowType=?3 and item.approvedStatus=?4",PartcodeChangeRequest.class)
							.setParameter(1, serialNumber.trim())
							.setParameter(2, products)
							.setParameter(3,PartcodeChangeRequestRepository.FLOW_TYPE_SA)
							.setParameter(4, false)
							.getSingleResult();
					throw new BadRequestException("Partcode change request is pending for approval for part code "+products.getProductPartCode()+" and serial number "+serialNumber);
				}catch(NoResultException ex) {
					
				}catch(NonUniqueResultException ex) {
					throw new BadRequestException("Multipal Partcode change request is pending for approval for part code "+products.getProductPartCode()+" and serial number "+serialNumber);
				}
				
				com.pratikshat.exicom.entity.LProductReturnOrderLineItem returnLineItems=null;
				try {
					returnLineItems=em.createQuery("select items from LProductReturnOrderLineItem items where items.productLineItemSerialNumber=?1 and items.returnOrderItem=?2 and items.quantityReceived=0",com.pratikshat.exicom.entity.LProductReturnOrderLineItem.class)
							.setParameter(1, serialNumber)
							.setParameter(2,_lrItem)
							.getSingleResult();
				}catch(javax.persistence.NoResultException ex) {
					throw new BadRequestException("Item serial number not found");
				}
				returnLineItems.setModifiedBy(user);
				returnLineItems.setModifiedDate(new java.util.Date());
				returnLineItems.setQuantityReceived(1);
				em.persist(returnLineItems);
				
				LocationProductLineItems checckExistslitems=null;
				try {
					checckExistslitems=em.createQuery("select litem from LocationProductLineItems litem join litem.locationProductItem item join item.location loc where litem.itemSerialNumber=?1 and item.product=?2",LocationProductLineItems.class)
							.setParameter(1, serialNumber)
							.setParameter(2,new Products(item.getProductPartCode()))
							.getSingleResult();
					throw new BadRequestException("Product serial number "+serialNumber+" of part code "+item.getProductPartCode()+" already exists at "+checckExistslitems.getLocationProductItem().getLocation().getLocationName());
				}catch(NoResultException ex) {
					
				}catch(NonUniqueResultException ex) {
					throw new BadRequestException("Multiple product serial number "+serialNumber+" of part code "+item.getProductPartCode()+" found ");
				}
				
				com.pratikshat.exicom.entity.LocationProductLineItems _ldLocationLineItem=new com.pratikshat.exicom.entity.LocationProductLineItems();
				_ldLocationLineItem.setCreatedBy(user);
				_ldLocationLineItem.setCreatedDate(new java.util.Date());
				_ldLocationLineItem.setCustomerInventory(false);
				_ldLocationLineItem.setInStock(true);
				_ldLocationLineItem.setItemSerialNumber(serialNumber);
				_ldLocationLineItem.setLocationProductItem(dlocationItem);
				_ldLocationLineItem.setLocationProductLineItemStatus(new com.pratikshat.exicom.entity.ProductLineItemStatus(1));
				_ldLocationLineItem.setLocationProeuctLineItemState(new com.pratikshat.exicom.entity.ProductLineItemProductState(returnLineItems.getLineItemState()==0? 2:returnLineItems.getLineItemState()));
				_ldLocationLineItem.setModifiedBy(user);
				_ldLocationLineItem.setModifiedDate(new java.util.Date());
				_ldLocationLineItem.setReplaced(false);
				_ldLocationLineItem.setReturned(false);
				_ldLocationLineItem.setCustomerInventory(returnLineItems.isCustomerInventory());
				_ldLocationLineItem.setCustomerName(returnLineItems.getCustomerName());
				_ldLocationLineItem.setCustomerSapNumber(returnLineItems.getCustomerSapNumber());
				_ldLocationLineItem.setRemarks(returnLineItems.getRemarks());
				if(returnLineItems.isCustomerInventory())
					customerInventory++;
				
				em.persist(_ldLocationLineItem);
				
				long stateid=returnLineItems.getLineItemState();
				//MANTAINING STOCK BALANCE
				if(stateid==1)
					dlocationItem.setOkQuantity(dlocationItem.getOkQuantity()+1);
				if(stateid==2)
					dlocationItem.setFaultyQuantity(dlocationItem.getFaultyQuantity()+1);
				if(stateid==3)
					dlocationItem.setScrapQty(dlocationItem.getScrapQty()+1);
				if(stateid==4)
					dlocationItem.setRejectedQty(dlocationItem.getRejectedQty()+1);
				if(stateid==5)
					dlocationItem.setBerQty(dlocationItem.getBerQty()+1);
				if(stateid==6)
					dlocationItem.setDoaQuantity(dlocationItem.getDoaQuantity()+1);
				em.persist(dlocationItem);
				
				//PUSHING DATA TO LOCATION RETURN RECEIVE LINE ITEMS
				LPReturnOrderReceiveLineItem returnOrderReceiveLineItem =new LPReturnOrderReceiveLineItem();
				returnOrderReceiveLineItem.setCreatedBy(new EmployeeDetails(user.getEmailId()));
				returnOrderReceiveLineItem.setCreatedDate(new Date());
				returnOrderReceiveLineItem.setLprOrderLineitem(returnLineItems);
				returnOrderReceiveLineItem.setLprOrderReceiveItem(_receiveItems);
				returnOrderReceiveLineItem.setModifiedBy(new EmployeeDetails(user.getEmailId()));
				returnOrderReceiveLineItem.setModifiedDate(new Date());
				returnOrderReceiveLineItem.setOwner(new EmployeeDetails(user.getEmailId()));
				returnOrderReceiveLineItem.setProductSerialNumber(serialNumber.trim());
				returnOrderReceiveLineItem.setReceiveDate(new Date());
				returnOrderReceiveLineItem.setTotalReceiveQuantity(1);
				
				em.persist(returnOrderReceiveLineItem);
				
				ProductLineItem pLineItem=dbProductLineItem.findByProductAndSerialNumber(products, serialNumber.trim());
				if(pLineItem!=null) {
					pLineItem.setModifiedBy(user);
					pLineItem.setModifiedDate(new Date());
					pLineItem.setPosition(String.valueOf(dlocationItem.getLocation().getLocationId()));
					pLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
					pLineItem.setState(new ProductLineItemProductState(stateid));
					pLineItem.setStatus(new ProductLineItemStatus(1));
					dbProductLineItem.save(pLineItem);
				}
				else {
					pLineItem=new ProductLineItem();
					pLineItem.setCreatedBy(user);
					pLineItem.setCreatedDate(new Date());
					pLineItem.setProduct(products);
					pLineItem.setSerialNumber(serialNumber);
					pLineItem.setModifiedBy(user);
					pLineItem.setModifiedDate(new Date());
					pLineItem.setPosition(String.valueOf(dlocationItem.getLocation().getLocationId()));
					pLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
					pLineItem.setState(new ProductLineItemProductState(stateid));
					pLineItem.setStatus(new ProductLineItemStatus(1));
					dbProductLineItem.save(pLineItem);
				}
			}
			dlocationItem.setCustomerInventory(dlocationItem.getCustomerInventory()+customerInventory);
			em.persist(dlocationItem);
		}
		
		_receive.setTotalQuantityReceived(totalItemReceived);
		em.persist(_receive);
		_returnOrder.setTotalQuantityReceived(_returnOrder.getTotalQuantityReceived()+totalItemReceived);
		if(_returnOrder.getTotalQuantityReceived()==_returnOrder.getTotalQuantityReturned()) {
			_returnOrder.setLocationReturnOrderStatus("Close");
		}
		else {
			_returnOrder.setLocationReturnOrderStatus("Partial Receive");
		}
		em.persist(_returnOrder);
		receiveItem.setLpReturnOderReceiveId(_receive.getReturnOrderReceiveId());
		return receiveItem;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE,rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class,RuntimeException.class,SQLException.class})
	public void addIitem(long returnOrderId, long productLineItemId, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		Date today=new Date();
		ServiceRecource sr=null;
		Location sLoc=null;
		LocationProductLineItems lItem=null;
		LProductReturnOrder returnOrder=null;
		
		
		try {
			sr=em.createQuery("select sr from ServiceRecource sr join sr.location loc where sr.user=?1",ServiceRecource.class).setParameter(1,emp).getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Please contact to admin, Service resource not found");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Please contact to admin, Multiple service resource found");
		}
		if(sr.getLocation()==null)
			throw new BadRequestException("Warehouse not found");
		sLoc=sr.getLocation();
		
		
		try {
			lItem=em.createQuery("select lItem from LocationProductLineItems lItem join lItem.locationProductItem item join item.product prd join item.location loc where lItem=?1",LocationProductLineItems.class)
					.setParameter(1, new LocationProductLineItems(productLineItemId))
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Item not found in your stock");
		}
		if(sLoc.getLocationId()!=lItem.getLocationProductItem().getLocation().getLocationId())
			throw new BadRequestException("Item not found in your stock");
		
		
		PartcodeChangeRequest chRequest=null;
		try {
			
			chRequest=em.createQuery("select item from PartcodeChangeRequest item where item.faultyProduct=?1 and item.faultyProductSerialNumber=?2 and item.flowType=?3 and item.approvedStatus=?4",PartcodeChangeRequest.class)
					.setParameter(1,lItem.getLocationProductItem().getProduct())
					.setParameter(2,lItem.getItemSerialNumber())
					.setParameter(3, PartcodeChangeRequestRepository.FLOW_TYPE_NON_SA)
					.setParameter(4, false)
					.getSingleResult();
			throw new BadRequestException("Can not move inventory of part code "+lItem.getLocationProductItem().getProduct()+" with serial number "+lItem.getItemSerialNumber()+" due to pending approval");
		}catch(NoResultException ex) {
			
			
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Can not move inventory of part code "+lItem.getLocationProductItem().getProduct()+" with serial number "+lItem.getItemSerialNumber()+" due to multiple pending approval");
		}
		
		
		try {
			returnOrder=em.createQuery("select ro from LProductReturnOrder ro join ro.sourceLocation loc where ro=?1",LProductReturnOrder.class)
					.setParameter(1,new LProductReturnOrder(returnOrderId))
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Wrong return order Id");
		}
		if(returnOrder.getSourceLocation().getLocationId()!=sLoc.getLocationId())
			throw new UnauthoriseException("You are not authorise");
		
		if(!returnOrder.getLocationReturnOrderStatus().equalsIgnoreCase("Draft"))
			throw new BadRequestException("Can not add item");
		
		if(returnOrder.getLocationReturnOrderStatus().equalsIgnoreCase("Archived"))
			throw new BadRequestException("Can not add item, Transaction has been archived");
		//CHECKING IF PRODUCT ALREADY IN RETURN ORDER LIST
		
		LProductReturnOrderItem rItem=null;
		try {
			rItem=em.createQuery("select rItem from LProductReturnOrderItem rItem where rItem.product=?1 and rItem.locationProductReturnOrder=?2",LProductReturnOrderItem.class)
					.setParameter(1, lItem.getLocationProductItem().getProduct())
					.setParameter(2, returnOrder)
					.getSingleResult();
		}catch(NoResultException ex) {
			
			rItem=new LProductReturnOrderItem();
			rItem.setCreatedBy(emp);
			rItem.setCreatedDate(today);
			rItem.setLocationProductReturnOrder(returnOrder);
			rItem.setModifiedBy(emp);
			//rItem.setLproItemManualId(lproItemManualId);
			rItem.setModifiedDate(today);
			rItem.setOwner(emp);
			rItem.setProduct(lItem.getLocationProductItem().getProduct());
			rItem.setProductItem(lItem.getLocationProductItem());
			rItem.setReturnDate(today);
			rItem.setSynked(false);
			rItem.setTotalReceiveQuantity(0);
			rItem.setTotalReturnQuantity(0);
			em.persist(rItem);
			String mId=String.format("ROI-%s",MiscellaneousUtils.fillZero(String.valueOf(rItem.getLproItemid()),5));
			rItem.setLproItemManualId(mId);
			em.persist(rItem);
			
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple record found for same product part code unable to add quantity");
		}
		rItem.setReturnDate(today);
		rItem.setTotalReturnQuantity(rItem.getTotalReturnQuantity()+1);
		rItem.setModifiedBy(emp);
		rItem.setModifiedDate(today);
		em.persist(rItem);
		LProductReturnOrderLineItem rLItem=new LProductReturnOrderLineItem();
		rLItem.setCreatedBy(emp);
		rLItem.setCreatedDate(today);
		rLItem.setCustomerInventory(lItem.isCustomerInventory());
		rLItem.setCustomerName(lItem.getCustomerName());
		rLItem.setCustomerSapNumber(lItem.getCustomerSapNumber());
		rLItem.setLineItemState(lItem.getLocationProeuctLineItemState().getProductStateId());
		rLItem.setModifiedBy(emp);
		rLItem.setModifiedDate(today);
		rLItem.setProductLineItemSerialNumber(lItem.getItemSerialNumber());
		rLItem.setQuantityReceived(0);
		rLItem.setQuantityReturned(1);
		rLItem.setReturnOrderItem(rItem);
		rLItem.setReturnStatus("");
		rLItem.setRemarks(lItem.getRemarks());
		
		em.persist(rLItem);
		///rLItem.set
		rLItem.setLproLineitemManualId("ROLI-"+MiscellaneousUtils.fillZero(""+rLItem.getLproLineitemId(), 5));
		em.persist(rLItem);
		
		LocationProductItem lpitem=lItem.getLocationProductItem();
		lpitem.setQuantityInOnHand(lpitem.getQuantityInOnHand()-1);
		long stateid=lItem.getLocationProeuctLineItemState().getProductStateId();
		if(stateid==1)
			lpitem.setOkQuantity(lpitem.getOkQuantity()-1);
			if(stateid==2)
				lpitem.setFaultyQuantity(lpitem.getFaultyQuantity()-1);
				if(stateid==3)
					lpitem.setScrapQty(lpitem.getScrapQty()-1);
					if(stateid==4)
						lpitem.setRejectedQty(lpitem.getRejectedQty()-1);
						if(stateid==5)
							lpitem.setBerQty(lpitem.getBerQty()-1);
						if(stateid==6)
							lpitem.setDoaQuantity(lpitem.getDoaQuantity()-1);
		if(stateid==1&&lItem.isRefurbished())
			lpitem.setRefurbished(lpitem.getRefurbished()-1);
		if(lItem.isCustomerInventory())
			lpitem.setCustomerInventory(lpitem.getCustomerInventory()-1);
		
		lpitem.setModifiedBy(emp);
		lpitem.setModifiedDate(today);
		em.persist(lpitem);
		
		ProductLineItem pLineItem=dbProductLineItem.findByProductAndSerialNumber(lItem.getLocationProductItem().getProduct(), lItem.getItemSerialNumber().trim());
		if(pLineItem!=null) {
			pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			pLineItem.setModifiedDate(new Date());
			pLineItem.setPosition("RO,"+returnOrder.getLprOrderid());
			pLineItem.setPositionState(ProductLineItemRepository.IN_TRANSIT);
			pLineItem.setState(new ProductLineItemProductState(stateid));
			pLineItem.setStatus(new ProductLineItemStatus(1));
			dbProductLineItem.save(pLineItem);
		}
		else {
			pLineItem=new ProductLineItem();
			pLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
			pLineItem.setCreatedDate(new Date());
			pLineItem.setProduct(lItem.getLocationProductItem().getProduct());
			pLineItem.setSerialNumber(lItem.getItemSerialNumber().trim());
			pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			pLineItem.setModifiedDate(new Date());
			pLineItem.setPosition("RO,"+returnOrder.getLprOrderid());
			pLineItem.setPositionState(ProductLineItemRepository.IN_TRANSIT);
			pLineItem.setState(new ProductLineItemProductState(stateid));
			pLineItem.setStatus(new ProductLineItemStatus(1));
			dbProductLineItem.save(pLineItem);
		}
		
		returnOrder.setTotalQuantityReturned(returnOrder.getTotalQuantityReturned()+1);
		returnOrder.setModifiedBy(emp);
		returnOrder.setModifiedDate(today);
		em.persist(returnOrder);
		
		em.createQuery("delete from LocationProductLineItems lItem where lItem=?1").setParameter(1, lItem).executeUpdate();
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE,rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class,RuntimeException.class,SQLException.class})
	public void removeItem(long returnOrderLineItemId, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		Date today=new Date();
		ServiceRecource sr=null;
		
		Location sLoc=null;
		LocationProductItem item=null;
		
		LProductReturnOrderLineItem rLineItem=null;
		LProductReturnOrderItem rItem=null;
		LProductReturnOrder rOrder=null;
		
		try {
			sr=em.createQuery("select sr from ServiceRecource sr join sr.location loc where sr.user=?1",ServiceRecource.class)
					.setParameter(1,emp)
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Service resource not found");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple service resource found");
		}
		sLoc=sr.getLocation();
		try {
			rLineItem=em.createQuery("select rLineItem from LProductReturnOrderLineItem rLineItem join rLineItem.returnOrderItem rItem join rItem.locationProductReturnOrder rOrder join rItem.product prd where rLineItem=?1",LProductReturnOrderLineItem.class)
					.setParameter(1, new LProductReturnOrderLineItem(returnOrderLineItemId))
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Return Order Line Item not found");
		}
		
		rItem=rLineItem.getReturnOrderItem();
		
		rOrder=rItem.getLocationProductReturnOrder();
		
		if(!rOrder.getLocationReturnOrderStatus().equalsIgnoreCase("Draft"))
			throw new BadRequestException("Can not edit record");
		
		if(rOrder.getLocationReturnOrderStatus().equalsIgnoreCase("Archived"))
			throw new BadRequestException("Can not edit record, Transaction has been archived");
		
		if(sLoc.getLocationId()!=rOrder.getSourceLocation().getLocationId())
			throw new UnauthoriseException("You are not authorise");
		
		try {
			item=em.createQuery("select item from LocationProductItem item where item.product=?1 and item.location=?2",LocationProductItem.class)
					.setParameter(1, rItem.getProduct())
					.setParameter(2, sLoc)
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Product item not found");
		}
		
		LocationProductLineItems lItems=new LocationProductLineItems();
		lItems.setCreatedBy(emp);
		lItems.setCreatedDate(today);
		lItems.setCustomerInventory(rLineItem.isCustomerInventory());
		lItems.setCustomerName(rLineItem.getCustomerName());
		lItems.setCustomerSapNumber(rLineItem.getCustomerSapNumber());
		lItems.setInStock(true);
		lItems.setItemSerialNumber(rLineItem.getProductLineItemSerialNumber());
		lItems.setLocationProductItem(item);
		//lItems.setLocationProductLineItemId(locationProductLineItemId);
		lItems.setLocationProductLineItemStatus(new ProductLineItemStatus(1));
		lItems.setLocationProeuctLineItemState(new ProductLineItemProductState(rLineItem.getLineItemState()));
		lItems.setModifiedBy(emp);
		lItems.setRefurbished(false);
		lItems.setReplaced(false);
		lItems.setReturned(true);
		lItems.setRemarks(rLineItem.getRemarks());
		em.persist(lItems);
		String mId=String.format("PIL-",MiscellaneousUtils.fillZero(String.valueOf(lItems.getId()), 5));
		lItems.setLocationProductLineItemId(mId);
		em.persist(lItems);
		
		long stateid=rLineItem.getLineItemState();
		
		item.setQuantityInOnHand(item.getQuantityInOnHand()+1);
		if(rLineItem.isCustomerInventory())
			item.setCustomerInventory(item.getCustomerInventory()+1);
		if(stateid==1)
			item.setOkQuantity(item.getOkQuantity()+1);
			if(stateid==2)
				item.setFaultyQuantity(item.getFaultyQuantity()+1);
				if(stateid==3)
					item.setScrapQty(item.getScrapQty()+1);
					if(stateid==4)
						item.setRejectedQty(item.getRejectedQty()+1);
						if(stateid==5)
							item.setBerQty(item.getBerQty()+1);
						if(stateid==6)
							item.setDoaQuantity(item.getDoaQuantity()+1);
						
		item.setModifiedBy(emp);
		item.setModifiedDate(today);
		em.persist(item);
		rItem.setTotalReturnQuantity(rItem.getTotalReturnQuantity()-1);
		rItem.setModifiedBy(emp);
		rItem.setModifiedDate(today);
		em.persist(rItem);
		rOrder.setTotalQuantityReturned(rOrder.getTotalQuantityReturned()-1);
		rItem.setModifiedBy(emp);
		rItem.setModifiedDate(today);
		em.persist(rItem);
		em.persist(rOrder);
		ProductLineItem pLineItem=dbProductLineItem.findByProductAndSerialNumber(item.getProduct(), rLineItem.getProductLineItemSerialNumber().trim());
		if(pLineItem!=null) {
			pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			pLineItem.setModifiedDate(new Date());
			pLineItem.setPosition(String.valueOf(item.getLocation().getLocationId()));
			pLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
			pLineItem.setState(new ProductLineItemProductState(stateid));
			pLineItem.setStatus(new ProductLineItemStatus(1));
			dbProductLineItem.save(pLineItem);
		}
		else {
			pLineItem=new ProductLineItem();
			pLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
			pLineItem.setCreatedDate(new Date());
			pLineItem.setProduct(item.getProduct());
			pLineItem.setSerialNumber(rLineItem.getProductLineItemSerialNumber().trim());
			pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			pLineItem.setModifiedDate(new Date());
			pLineItem.setPosition(String.valueOf(item.getLocation().getLocationId()));
			pLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
			pLineItem.setState(new ProductLineItemProductState(stateid));
			pLineItem.setStatus(new ProductLineItemStatus(1));
			dbProductLineItem.save(pLineItem);
		}
		em.remove(rLineItem);
		//em.createQuery("delete from LProductReturnOrderLineItem item where item=?1").setParameter(1,rLineItem).executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class,RuntimeException.class,SQLException.class})
	public void submit(long returnOrderId, LoginUser user) throws BadRequestException, UnauthoriseException {
		LProductReturnOrder rd=null;
		try {
			rd=em.createQuery("select rd from LProductReturnOrder rd where rd=?1",LProductReturnOrder.class).setParameter(1, new LProductReturnOrder(returnOrderId)).getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Wrong id");
		}
		ServiceRecource sr=em.createQuery("select sr from ServiceRecource sr where sr.user=?1",ServiceRecource.class).setParameter(1,new EmployeeDetails(user.getUsername())).getSingleResult();
		if(sr.getLocation().getLocationId()!=rd.getSourceLocation().getLocationId())
			throw new BadRequestException("You are not authorise");
//		if(!rd.getOwner().getEmailId().equalsIgnoreCase(user.getUsername()))
//			throw new BadRequestException("You are not authorise");
		
		if(!rd.getLocationReturnOrderStatus().equalsIgnoreCase("Draft"))
			throw new BadRequestException("Already submitted");
		
		rd.setLocationReturnOrderStatus("Submitted");
		rd.setModifiedBy(new EmployeeDetails(user.getUsername()));
		rd.setModifiedDate(new Date());
		em.persist(rd);
		
		try {
		
			Location warehouse=em.createQuery("select loc from Location loc join loc.legalEntity lgl left join loc.owner onr left join onr.contact cnt where loc.locationId=?1",Location.class)
					.setParameter(1, rd.getDestinationLocation())
					.getSingleResult();
			
		TemplateDetails	templateDetails = null;
		
		try {
			Query templateQuery=em.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
			templateQuery.setParameter(1,true);
			templateQuery.setParameter(2,TemplateType.SMS);
			templateQuery.setParameter(3,new BusinessDomain(2));
			templateQuery.setParameter(4,"ro_initiate");

			templateDetails = (TemplateDetails) templateQuery.getSingleResult();
		}catch(NoResultException nex) {
			nex.printStackTrace();
		}catch (NonUniqueResultException e) {
			e.printStackTrace();
		}
		
		String smsMessage = null;
		if(templateDetails!=null) {
			smsMessage = templateDetails.getBody();
			smsMessage = smsMessage.replaceFirst("<var>", rd.getLprOrderManualId());
			smsMessage = smsMessage.replaceFirst("<var>", sr.getName());
			smsMessage = smsMessage.replaceFirst("<var>", feedbackurl+"/#/productreturnorder/"+rd.getLprOrderid()+"/details");
		}
		
		if(smsMessage!=null && !smsMessage.equalsIgnoreCase("")) {
			SmsNotifier sNotifier = new SmsNotifier(em, warehouse.getOwner().getContact().getMobile(),smsMessage, user.getUsername(),templateDetails.getTemplaeCode());
			sNotifier.sendNotification();
		}
	}catch(Exception ex) {
		ex.printStackTrace();
	}
		
	}

	
	@Override
	public List<LpReturnOrderResponse> advanceSearch(Map<String, Object> filterData, LoginUser user, List<Long> childLoc)throws ParseException {
			StringBuilder queryStringBuilder=new StringBuilder();
			queryStringBuilder.append("select new com.pratikshat.exicom.model.response.LpReturnOrderResponse(rt) from LProductReturnOrder rt join rt.sourceLocation slo join rt.destinationLocation dslo left join rt.createdBy cby left join rt.modifiedBy mby left join rt.owner onr where rt.locationReturnOrderStatus !='Archived' and 1=1 ");
			if(filterData.get("manualId")!=null&&!filterData.get("manualId").equals(""))
				queryStringBuilder.append(" and rt.lprOrderManualId like :manualId");
			if(filterData.get("sourceLocation")!=null&&!filterData.get("sourceLocation").equals(""))
				queryStringBuilder.append(" and slo.locationName like :sourceLocation");
			if(filterData.get("destinationLocation")!=null&&!filterData.get("destinationLocation").equals(""))
				queryStringBuilder.append(" and dslo.locationName like :destinationLocation");
			if(filterData.get("status")!=null&&!filterData.get("status").equals(""))
				queryStringBuilder.append(" and rt.locationReturnOrderStatus like :status");
			if(childLoc!=null)
				queryStringBuilder.append(" and (slo.locationId in :childsLoc or dslo.locationId in :childsLoc)");
			
			Query qr=em.createQuery(queryStringBuilder.toString(),LpReturnOrderResponse.class);
			
			
			if(filterData.get("manualId")!=null&&!filterData.get("manualId").equals(""))
				qr.setParameter("manualId","%"+ filterData.get("manualId")+"%");
			if(filterData.get("sourceLocation")!=null&&!filterData.get("sourceLocation").equals(""))
				qr.setParameter("sourceLocation", "%"+ filterData.get("sourceLocation")+"%");
			if(filterData.get("destinationLocation")!=null&&!filterData.get("destinationLocation").equals(""))
				qr.setParameter("destinationLocation", "%"+ filterData.get("destinationLocation")+"%");
			if(filterData.get("status")!=null&&!filterData.get("status").equals(""))
				qr.setParameter("status","%"+ filterData.get("status")+"%");
			
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
	public List<Map<String, Object>> getItemNativeAdvanceSearch(long returOrderId, Map<String, Object> request) {

		List<Object> value = new ArrayList<>();
		String _query="SELECT litem.lpro_lineitem_id AS returnOrderLineItemId,\r\n" + 
				"litem.lpro_lineitem_manual_id AS returnOrderLineItemManualId, \r\n" + 
				"prd.product_part_code AS returnOrderProductPartcode,\r\n" + 
				"prd.pdoduct_description AS returnOrderProductDescription,\r\n" + 
				"litem.product_line_item_serial_number AS returnOrderProductSerialNumber,\r\n" + 
				"CASE "
				+ "WHEN litem.quantity_received>=litem.quantity_returned THEN TRUE "
				+ "ELSE FALSE "
				+"END AS received\r\n" + 
				" FROM `lproduct_return_order_line_item` litem JOIN `lproduct_return_order_item` item ON litem.`return_order_item_lpro_itemid`=item.`lpro_itemid` \r\n"
				+" JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code` \r\n";
		
		StringBuilder queryBuilder=new StringBuilder();
		queryBuilder.append(_query);
		queryBuilder.append(" WHERE item.`location_product_return_order_lpr_orderid`=?");
		value.add(returOrderId);
		
		if(request.get("returnOrderLineItemManualId")!=null&&!request.get("returnOrderLineItemManualId").equals("")) {
			queryBuilder.append(" and litem.lpro_lineitem_manual_id like ?");
			value.add("%"+request.get("returnOrderLineItemManualId")+"%");
		}
		
		if(request.get("productPartcode")!=null&&!request.get("productPartcode").equals("")) {
			queryBuilder.append(" and prd.product_part_code like ?");
			value.add("%"+request.get("productPartcode")+"%");
		}
		
		if(request.get("productDescription")!=null&&!request.get("productDescription").equals("")) {
			queryBuilder.append(" and prd.pdoduct_description like ?");
			value.add("%"+request.get("productDescription")+"%");
		}
		
		if(request.get("serialNumber")!=null&&!request.get("serialNumber").equals("")) {
			queryBuilder.append(" and litem.product_line_item_serial_number like ?");
			value.add("%"+request.get("serialNumber")+"%");
		}
		
		return jdbc.queryForList(queryBuilder.toString(),value.toArray());
	}
	
	
	@Transactional(isolation=Isolation.READ_UNCOMMITTED,rollbackFor = {Exception.class,BadRequestException.class,UnauthoriseException.class,RuntimeException.class},propagation = Propagation.REQUIRES_NEW)
	@Override
	public ByteArrayInputStream generatePackingListPdf(LoginUser user, long id, String type) throws Exception {
		
		MaterialTransferPackingList packingListPdf = new MaterialTransferPackingList(em, fileStorageProperties);
		return packingListPdf.createPdf(user, id, type);
	}
}
