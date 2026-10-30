package com.pratikshat.exicom.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.ApplicationNotificationDetails;
import com.pratikshat.exicom.entity.BillOfMaterialLocation;
import com.pratikshat.exicom.entity.DcrRepariCitemsSerial;
import com.pratikshat.exicom.entity.DctRepair;
import com.pratikshat.exicom.entity.DctRepairItemConsumed;
import com.pratikshat.exicom.entity.DctRepairProblemMaster;
import com.pratikshat.exicom.entity.DctRepariQc;
import com.pratikshat.exicom.entity.DctRepariResulation;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LocationProductItem;
import com.pratikshat.exicom.entity.LocationProductLineItems;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ProductLineItem;
import com.pratikshat.exicom.entity.ProductLineItemProductState;
import com.pratikshat.exicom.entity.ProductLineItemStatus;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.entity.ServiceOrderJobCard;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.DctRepairConsumedItemsRequest;
import com.pratikshat.exicom.model.request.DctRepairRequest;

@Repository
public class DctRepairRepositoryCustomeImpl implements DctRepairRepositoryCustome {
	
	private static final Logger LOGGER=LogManager.getLogger(DctRepairRepositoryCustomeImpl.class);
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	ProductLineItemRepository dbProductLineItem;
	
	@Autowired
	SimpMessagingTemplate templatee;

	@Override
	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_UNCOMMITTED)
	public void repairItems(DctRepairRequest request, LoginUser user) throws BadRequestException, UnauthoriseException {
		Location location=null;
		ServiceRecource sr=null;
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		Date today=new Date();
		
		ServiceOrderJobCard jobCard=null;
		try {
			jobCard=em.createQuery("select jc from ServiceOrderJobCard jc join jc.itemId item join item.item itemd where jc=?1",ServiceOrderJobCard.class)
					.setParameter(1, new ServiceOrderJobCard(request.getJobCardId()))
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Job Card not found");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Invalid request");
		}
		
		if(jobCard.getJobCardStatus().equalsIgnoreCase("Archived"))
			throw new BadRequestException("This job card is Archived, can not update");
		
		if(jobCard.isReturned())
			throw new BadRequestException("Already returned to the Job card sendor");
		if(jobCard.isReparied())
			throw new BadRequestException("Already repaired");
		try {
			sr=em.createQuery("select sr from ServiceRecource sr join sr.location lc where sr.user=?1",ServiceRecource.class)
					.setParameter(1, new EmployeeDetails(user.getUsername())).getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Service Resource not found or Location not found with your user id");
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple Service resource attached with this user, Please contact with system admin");
		}
		
		if(jobCard.getRepairEng().getServiceResourceId()!=sr.getServiceResourceId())
			throw new UnauthoriseException("You are not authorise");
		
		location=sr.getLocation();
		String ownerLocation=location.getOwner().getEmailId();
		if(!ownerLocation.equalsIgnoreCase(user.getUsername())) {
			throw new UnauthoriseException("You are not authorised, Your taged Location does not have your ownership");
		}
		
		if(request.getPartCode()==null||request.getPartCode().equalsIgnoreCase("")) throw new BadRequestException("Finished product part code not found");
		
		if(request.getSerialNumber()==null||request.getSerialNumber().equalsIgnoreCase("")) throw new BadRequestException("Serial  number not found");
		
		com.pratikshat.exicom.entity.LocationProductLineItems fProductLineItems=null;
		try {
			fProductLineItems=em.createQuery("select litem from LocationProductLineItems litem join litem.locationProductItem item join item.location loc where loc=?1 and item.product=?2 and litem.itemSerialNumber=?3",com.pratikshat.exicom.entity.LocationProductLineItems.class)
						.setParameter(1, location)
						.setParameter(2,new Products(request.getPartCode()))
						.setParameter(3,request.getSerialNumber())
						.getSingleResult();
		}
		catch(NoResultException ex) {
			throw new BadRequestException(String.format("Product part code %s having serial number %s ",request.getPartCode(),request.getSerialNumber()));
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException("Some thing went wrong "+ex.getMessage());
		}
		LocationProductItem productItem=fProductLineItems.getLocationProductItem();
		
		long prevStatusCode=fProductLineItems.getLocationProeuctLineItemState().getProductStateId();
		
		jobCard.setProductState(new ProductLineItemProductState(request.getProductStatus()));
		
		//DEDECTING STOCK FROM ENG FOR REPARID INVENTORY
		
		if(prevStatusCode==1)
			productItem.setOkQuantity(productItem.getOkQuantity()-1);
		if(prevStatusCode==2)
			productItem.setFaultyQuantity(productItem.getFaultyQuantity()-1);
		if(prevStatusCode==3)
			productItem.setScrapQty(productItem.getScrapQty()-1);
		if(prevStatusCode==4)
			productItem.setRejectedQty(productItem.getRejectedQty()-1);
		if(prevStatusCode==5)
			productItem.setBerQty(productItem.getBerQty()-1);
		
		if(fProductLineItems.isCustomerInventory())
			productItem.setCustomerInventory(productItem.getCustomerInventory()-1);
		
		productItem.setQuantityInOnHand(productItem.getQuantityInOnHand()-1);
		
		if(prevStatusCode==1&&fProductLineItems.isRefurbished())
			productItem.setRefurbished(productItem.getRefurbished()-1);
		
		em.persist(productItem);
		
		//ADDING STOCK TO SENDER OF JOB CARD 
		
		Location sendorLocation=null;
		LocationProductItem sendorProductItem=null;
		try {
			sendorLocation=em.createQuery("select loc from Location loc where loc.owner=?1",Location.class)
					.setParameter(1,jobCard.getCreatedBy())
					.getSingleResult();
			
		}catch(NoResultException ex) {
			throw new BadRequestException("Warehouse not found for sendor");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple warehouse found for sendor of job card");
		}
		try {
			sendorProductItem=em.createQuery("select item from LocationProductItem item where item.location=?1 and item.product=?2",LocationProductItem.class)
					.setParameter(1, sendorLocation)
					.setParameter(2,new Products(request.getPartCode()))
					.getSingleResult();
		}catch(NoResultException ex) {
			sendorProductItem =new LocationProductItem();
			sendorProductItem.setBerQty(0);
			sendorProductItem.setCreatedBy(emp);
			sendorProductItem.setCreatedDate(today);
			sendorProductItem.setCustomerInventory(0);
			sendorProductItem.setFaultyQuantity(0);
			sendorProductItem.setHslQuantity(0);
			sendorProductItem.setHslStatus("L");
			sendorProductItem.setIssuedQunatity(0);
			sendorProductItem.setLocation(sendorLocation);
			//sendorProductItem.setLocationProductItemId(locationProductItemId);
			sendorProductItem.setModifiedBy(emp);
			sendorProductItem.setModifiedDate(today);
			sendorProductItem.setMslQuantity(0);
			sendorProductItem.setMslQuantityStatus("L");
			sendorProductItem.setOkQuantity(0);
			sendorProductItem.setProduct(new Products(request.getPartCode()));
			sendorProductItem.setQuantityInOnHand(0);
			sendorProductItem.setQuantityUnitOfMasur("NO");
			sendorProductItem.setRejectedQty(0);
			sendorProductItem.setScrapQty(0);
			sendorProductItem.setRefurbished(0);
			em.persist(sendorProductItem);
			String mid=String.format("PI-%s", MiscellaneousUtils.fillZero(String.valueOf(sendorProductItem.getId()),5));
			sendorProductItem.setLocationProductItemId(mid);
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Invalid request");
		}
		
		long newstatus=request.getProductStatus();
		if(newstatus==1) {
			sendorProductItem.setOkQuantity(sendorProductItem.getOkQuantity()+1);
			sendorProductItem.setRefurbished(sendorProductItem.getRefurbished()+1);
			fProductLineItems.setRefurbished(true);
		}
			
			
		if(newstatus==2)
			sendorProductItem.setFaultyQuantity(sendorProductItem.getFaultyQuantity()+1);
		if(newstatus==3)
			sendorProductItem.setScrapQty(sendorProductItem.getScrapQty()+1);
		if(newstatus==4)
			sendorProductItem.setRejectedQty(sendorProductItem.getRejectedQty()+1);
		if(newstatus==5)
			sendorProductItem.setBerQty(sendorProductItem.getBerQty()+1);
		if(fProductLineItems.isCustomerInventory())
			sendorProductItem.setCustomerInventory(sendorProductItem.getCustomerInventory()+1);
		
		sendorProductItem.setQuantityInOnHand(sendorProductItem.getQuantityInOnHand()+1);
		
		sendorProductItem.setModifiedBy(emp);
		
		sendorProductItem.setModifiedDate(today);
		
		em.persist(sendorProductItem);
		
		fProductLineItems.setLocationProeuctLineItemState(new ProductLineItemProductState(newstatus));
		fProductLineItems.setLocationProductItem(sendorProductItem);
		
		
		em.persist(fProductLineItems);
		
		if(newstatus==1)
			jobCard.setReparied(true);
		jobCard.setReturned(true);
		jobCard.setProductState(new ProductLineItemProductState(newstatus));
		
		/*
		
		if(prevStatusCode!=request.getProductStatus()) {
			if(request.getProductStatus()==1) {
				productItem.setOkQuantity(okQuantity+1);
				jobCard.setReparied(true);
			}
			if(request.getProductStatus()==2) {
				productItem.setFaultyQuantity(faultyQuantity+1);
			}
			if(request.getProductStatus()==3) {
				productItem.setScrapQty(scrapQuantity+1);
			}
			if(request.getProductStatus()==4) {
				productItem.setRejectedQty(rejectedQuantity+1);
			}
			if(request.getProductStatus()==5) {
				productItem.setBerQty(berQuantity+1);
			}
			
			
			
			//previous status code 
			if(prevStatusCode==1) {
				productItem.setOkQuantity(okQuantity-1);
			}
			if(prevStatusCode==2) {
				productItem.setFaultyQuantity(faultyQuantity-1);
			}
			if(prevStatusCode==3) {
				productItem.setScrapQty(scrapQuantity-1);
			}
			if(prevStatusCode==4) {
				productItem.setRejectedQty(rejectedQuantity-1);
			}
			if(prevStatusCode==5) {
				productItem.setBerQty(berQuantity-1);
			}
			
			em.persist(productItem);
			
			fProductLineItems.setLocationProeuctLineItemState(new ProductLineItemProductState(request.getProductStatus()));
			em.persist(fProductLineItems);
			
		}*/
		
		//dct repari start
		
		DctRepair _repari=new DctRepair();
		_repari.setCreatedBy(emp);
		_repari.setCreatedDate(today);
		_repari.setCurrentStatus(new ProductLineItemProductState(request.getProductStatus()));
		_repari.setfProduct(new Products(request.getPartCode()));
		_repari.setLastModifiedDate(today);
		_repari.setModifiedBy(emp);
		_repari.setOwner(emp);
		_repari.setPreviousStatus(fProductLineItems.getLocationProeuctLineItemState());
		//_repari.setRepairBy(emp);
		_repari.setRepariDate(today);
		_repari.setSerialNumber(request.getSerialNumber());
		_repari.setJobCard(jobCard);
		float totalRepariAmount=0;
		_repari.setRemarks(request.getRemarks());
		if(request.getRepariEng()==null||request.getRepariEng().equalsIgnoreCase(""))
			_repari.setRepairBy(emp);
		else
			_repari.setRepairBy(new EmployeeDetails(request.getRepariEng().trim()));
		em.persist(_repari);
		
		//end dct repari
		
		
		ProductLineItem fpLineItem=dbProductLineItem.findByProductAndSerialNumber(_repari.getfProduct(), request.getSerialNumber());
		if(fpLineItem!=null) {
			fpLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			fpLineItem.setModifiedDate(new Date());
			fpLineItem.setPositionState(ProductLineItemRepository.AT_REPAIR);
			fpLineItem.setPosition(String.valueOf(sendorLocation.getLocationId()));
			fpLineItem.setState(_repari.getCurrentStatus());
			fpLineItem.setRefurbished(true);
			dbProductLineItem.save(fpLineItem);
		}
		else {
			fpLineItem=new ProductLineItem();
			fpLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
			fpLineItem.setCreatedDate(new Date());
			fpLineItem.setProduct(_repari.getfProduct());
			fpLineItem.setSerialNumber(request.getSerialNumber());
			fpLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			fpLineItem.setModifiedDate(new Date());
			fpLineItem.setPositionState(ProductLineItemRepository.AT_REPAIR);
			fpLineItem.setPosition(String.valueOf(sendorLocation.getLocationId()));
			fpLineItem.setState(_repari.getCurrentStatus());
			fpLineItem.setRefurbished(true);
			fpLineItem.setStatus(new ProductLineItemStatus(3));
			dbProductLineItem.save(fpLineItem);
		}
		
		
		//problem master 
		
		for(Long id:request.getProblemmaster()) {
			DctRepariResulation res=new DctRepariResulation();
			res.setDctRepair(_repari);
			res.setResulation(new DctRepairProblemMaster(id));
			em.persist(res);
		}
		
		//QC ENTRY
		
		DctRepariQc qc=new DctRepariQc();
		qc.setFaultObservedInHeatRun(request.getFaultObservedInHeatRun());
		qc.setFinalStatus(request.getFinalStatus());
		qc.setHeatRunStatus(request.getHeatRunStatus());
		qc.setRepari(_repari);
		qc.setReworkAfterFail(request.getReworkAfterFail());
		
		em.persist(qc);
		
		
		if(request.getItems()!=null&&request.getItems().size()>0) {
			for(DctRepairConsumedItemsRequest ireq:request.getItems()) {
				if(ireq.getQty()<=0)
					throw new BadRequestException("Consumption Quantity Not found");
				BillOfMaterialLocation bomLocation=null;
				try {
					bomLocation=em.createQuery("select bl from BillOfMaterialLocation bl join bl.bom bm join bm.rProduct pr where bl=?1",BillOfMaterialLocation.class)
							.setParameter(1, new BillOfMaterialLocation(Long.valueOf(ireq.getLocationId())))
							.getSingleResult();
				}catch(NoResultException ex) {
					throw new BadRequestException("BOM Location not found");
				}
				LocationProductItem conItem=null;
				try {
					conItem=em.createQuery("select litem from LocationProductItem litem join litem.product pr where litem.location=?1 and pr=?2",LocationProductItem.class)
							.setParameter(1,location)
							.setParameter(2,bomLocation.getBom().getrProduct())
							.getSingleResult();
				}catch(NoResultException ex) {
					throw new BadRequestException(String.format("Part code %s not found in stock", bomLocation.getBom().getrProduct().getProductPartCode()));
				}
				if(conItem.getOkQuantity()<=0) throw new BadRequestException("You have not stock to consume ");
				
				if(conItem.getProduct().getProductType().equalsIgnoreCase("Component")) {
					
					conItem.setOkQuantity(conItem.getOkQuantity()-ireq.getQty());
					conItem.setQuantityInOnHand(conItem.getQuantityInOnHand()-ireq.getQty());
					em.persist(conItem);
				}
				
				if(!conItem.getProduct().getProductType().equalsIgnoreCase("Component")) {
					
					int qty=(int)ireq.getQty();
					
					if(ireq.getSerialNumber()==null) throw new BadRequestException("Please provide OK Serial Number");
					
					if(qty!=ireq.getSerialNumber().size()) throw new BadRequestException("Consumed Qty is not found with Serial Numbers");
					
					for(String serials:ireq.getSerialNumber()) {
						LocationProductLineItems serialsNumber=null;
						try {
							serialsNumber=em.createQuery("select sl from LocationProductLineItems sl where sl.itemSerialNumber=?1 and sl.locationProductItem=?2",LocationProductLineItems.class)
									.setParameter(1,serials)
									.setParameter(2,conItem)
									.getSingleResult();
						}catch(NoResultException ex) {
							throw new BadRequestException(String.format("Serial number %s not found in your stock",serials));
						}
						
						if(serialsNumber.isCustomerInventory())
							throw new BadRequestException("You can not consume customer inventory");
						
						long status=serialsNumber.getLocationProeuctLineItemState().getProductStateId();
						if(status!=1)
							throw new BadRequestException(String.format("Serial number %s is not in ok state",serials));
						conItem.setOkQuantity(conItem.getOkQuantity()-1);
						conItem.setQuantityInOnHand(conItem.getQuantityInOnHand()-1);
					
						if(serialsNumber.isRefurbished())
							conItem.setRefurbished(conItem.getRefurbished()-1);
						
						em.persist(conItem);
						
						ProductLineItem pLineItem=dbProductLineItem.findByProductAndSerialNumber(conItem.getProduct(), serialsNumber.getItemSerialNumber());
						if(pLineItem!=null) {
							pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
							pLineItem.setModifiedDate(new Date());
							pLineItem.setPositionState(ProductLineItemRepository.ATTACHED_TO_ASSETS);
							pLineItem.setPosition(request.getSerialNumber().trim()+","+request.getPartCode());
							pLineItem.setState(serialsNumber.getLocationProeuctLineItemState());
							pLineItem.setStatus(new ProductLineItemStatus(3));
							dbProductLineItem.save(pLineItem);
						}
						else {
							pLineItem=new ProductLineItem();
							pLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
							pLineItem.setCreatedDate(new Date());
							pLineItem.setProduct(conItem.getProduct());
							pLineItem.setSerialNumber(serialsNumber.getItemSerialNumber().trim());
							pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
							pLineItem.setModifiedDate(new Date());
							pLineItem.setPositionState(ProductLineItemRepository.ATTACHED_TO_ASSETS);
							pLineItem.setPosition(request.getSerialNumber().trim()+","+request.getPartCode());
							pLineItem.setState(serialsNumber.getLocationProeuctLineItemState());
							pLineItem.setStatus(new ProductLineItemStatus(3));
							dbProductLineItem.save(pLineItem);
						}
						
						em.createQuery("delete from LocationProductLineItems dl where dl=?1").setParameter(1,serialsNumber).executeUpdate();
						
					}
				}
				else {
					if(ireq.getSerialNumber()!=null&&ireq.getSerialNumber().size()>0) throw new BadRequestException("Component can not have serial number");
				}
				
				//dct repari consumed items
				DctRepairItemConsumed _consumed=new DctRepairItemConsumed();
				_consumed.setLocation(bomLocation.getLocationName());
				_consumed.setProduct(conItem.getProduct());
				_consumed.setQuantity(ireq.getQty());
				float amount=ireq.getQty()*conItem.getProduct().getProductPrice();
				_consumed.setRepairAmount(amount);
				_consumed.setRepair(_repari);
				_consumed.setSubAssemblyPartcode(bomLocation.getBom().getfProduct().getProductPartCode());
				em.persist(_consumed);
				if(ireq.getSerialNumber()!=null&&ireq.getSerialNumber().size()>0) {
					for(String se:ireq.getSerialNumber()) {
						DcrRepariCitemsSerial _S=new DcrRepariCitemsSerial();
						_S.setItems(_consumed);
						_S.setSerialNumber(se);
						em.persist(_S);
					}
				}
				totalRepariAmount=totalRepariAmount+amount;
			}
			
		}
		_repari.setTotalRepairAmount(totalRepariAmount);
		em.persist(_repari);
		em.persist(jobCard);
		
		//NOTIFICATION FOR JOB CARD RETURN
		
		ApplicationNotificationDetails notification=new ApplicationNotificationDetails();
		notification.setNofificationDate(today);
		notification.setNotificationActionUrl("/serviceorder");
		notification.setNotificationMessage(String.format("Product %s with serial number %s returnd to you for your action.", request.getPartCode(),request.getSerialNumber()));
		notification.setNotificationRead(false);
		notification.setUserEmailId(jobCard.getOwner().getEmailId());
		em.persist(notification);
		
		try {
			templatee.convertAndSend("/topic/"+notification.getUserEmailId(), notification);
		}catch(Exception ex) {}
		
	}

	@Override
	@Transactional
	public List<String> getOkSerialNumbers(String partcode, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		Location location=null;
		ServiceRecource sr=null;
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		Date today=new Date();
		try {
			sr=em.createQuery("select sr from ServiceRecource sr join sr.location lc where sr.user=?1",ServiceRecource.class)
					.setParameter(1, new EmployeeDetails(user.getUsername())).getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Service Resource not found or Location not found with your user id");
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple Service resource attached with this user, Please contact with system admin");
		}
		location=sr.getLocation();
		String ownerLocation=location.getOwner().getEmailId();
		if(!ownerLocation.equalsIgnoreCase(user.getUsername())) {
			throw new UnauthoriseException("You are not authorised, Your taged Location does not have your ownership");
		}
		
		List<String> serla=null;
		try {
			serla=em.createQuery("select litem.itemSerialNumber from LocationProductLineItems litem join litem.locationProductItem item where item.product=?1 and item.location=?2 and litem.locationProeuctLineItemState=?3",String.class)
					.setParameter(1, new Products(partcode))
					.setParameter(2, location)
					.setParameter(3,new ProductLineItemProductState(1))
					.getResultList();
			return serla;
		}catch(Exception ex) {
			
		}
		
		return new ArrayList<String>();
	}
	
}
