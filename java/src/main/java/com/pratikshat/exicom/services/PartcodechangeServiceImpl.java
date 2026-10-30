package com.pratikshat.exicom.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LProductReturnOrder;
import com.pratikshat.exicom.entity.LProductReturnOrderItem;
import com.pratikshat.exicom.entity.LProductReturnOrderLineItem;
import com.pratikshat.exicom.entity.LocationProductItem;
import com.pratikshat.exicom.entity.LocationProductLineItems;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.PartCodechangeApproval;
import com.pratikshat.exicom.entity.PartcodeChangeRequest;
import com.pratikshat.exicom.entity.PartcodeChangeRequestApproval;
import com.pratikshat.exicom.entity.PartcodeChangeSa;
import com.pratikshat.exicom.entity.ProductLineItem;
import com.pratikshat.exicom.entity.ProductLineItemProductState;
import com.pratikshat.exicom.entity.ProductLineItemStatus;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.LProductReturnOrderItemRepository;
import com.pratikshat.exicom.repository.LProductReturnOrderLineItemRepository;
import com.pratikshat.exicom.repository.LocationProductItemRepository;
import com.pratikshat.exicom.repository.LocationProductLineItemRepository;
import com.pratikshat.exicom.repository.LpReturnRepository;
import com.pratikshat.exicom.repository.PartCodechangeApprovalRepository;
import com.pratikshat.exicom.repository.PartcodeChangeRequestApprovalRepository;
import com.pratikshat.exicom.repository.PartcodeChangeRequestRepository;
import com.pratikshat.exicom.repository.PartcodeChangeSaRepository;
import com.pratikshat.exicom.repository.ProductLineItemRepository;
import com.pratikshat.exicom.repository.ProductRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;

@Service
public class PartcodechangeServiceImpl implements PartcodechangeService {

	@Autowired
	PartcodeChangeRequestRepository dhChangeRequest;
	@Autowired
	PartcodeChangeRequestApprovalRepository dbRequestApproval;
	@Autowired
	LocationProductLineItemRepository dbLocationProductLineItem;
	@Autowired
	LocationProductItemRepository dbLocationProductItem;
	
	@Autowired
	ProductLineItemRepository dbProductLineItem;
	
	@Autowired
	ProductRepository dbProduct;
	
	@Autowired
	LpReturnRepository dbRturnRepo;
	
	@Autowired
	LProductReturnOrderItemRepository dbLpReturnOrderItemRepo;
	
	@Autowired
	LProductReturnOrderLineItemRepository dbLpReturnOrderLineItemRepo;
	
	@Autowired
	ServiceRecourseRepository dbSr;
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	PartcodeChangeSaRepository dbSaChangeRequest;
	
	@Autowired
	PartCodechangeApprovalRepository dbChangeApproval;
	
	@SuppressWarnings("unlikely-arg-type")
	@Override
	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	public void approveDirect(List<Long> ids, LoginUser user) throws BadRequestException, UnauthoriseException {
		List<PartcodeChangeRequest> req=dhChangeRequest.findByIdIn(ids);
		
		ServiceRecource sr=dbSr.findByUser(new EmployeeDetails(user.getUsername()));
		
		String userRole=getUserRole(user);
		if(req!=null&&req.size()>0) {
			for(PartcodeChangeRequest rq:req) {
				
				if(rq.getFlowType().equalsIgnoreCase(PartcodeChangeRequestRepository.FLOW_TYPE_SA)) {
					if(rq.getApprovedStatus())
						throw new BadRequestException("Already approved");
					if(!(rq.getAssignedTo().contains(userRole)||req.contains("USER:"+user.getUsername())))
						throw new UnauthoriseException("You are not authorised");
					
//					LocationProductLineItems litem=dbLocationProductLineItem.findByItemSerialNumberAndLocationProductItem_product(rq.getFaultyProductSerialNumber(), rq.getFaultyProduct());
//					if(litem==null)
//						throw new BadRequestException("Product part code "+rq.getFaultyProduct().getProductPartCode()+" is with serial number "+rq.getFaultyProductSerialNumber()+" is not in stock");
//					if(litem.getLocationProductItem().getLocation().getLocationId()!=sr.getLocation().getLocationId())
//						throw new BadRequestException("Product part code "+rq.getFaultyProduct().getProductPartCode()+" is with serial number "+rq.getFaultyProductSerialNumber()+" is not in your stock");
//					if(litem.getLocationProeuctLineItemState().getProductStateId()!=2)
//						throw new BadRequestException("Product part code "+rq.getFaultyProduct().getProductPartCode()+" is with serial number "+rq.getFaultyProductSerialNumber()+" is not faulty stock");
					
					LProductReturnOrderLineItem rto=getReturnOrderLineItem(rq.getFaultyProduct().getProductPartCode(), rq.getFaultyProductSerialNumber());
					if(rto==null)
						throw new BadRequestException("Return order not found for part code "+rq.getFaultyProduct().getProductPartCode()+" and serial number "+rq.getFaultyProductSerialNumber());
					if(rto.getReturnOrderItem().getLocationProductReturnOrder().getLocationReturnOrderStatus().equalsIgnoreCase("Draft"))
						throw new BadRequestException("Return order not found for part code "+rq.getFaultyProduct().getProductPartCode()+" and serial number "+rq.getFaultyProductSerialNumber());
					if(rto.getReturnOrderItem().getLocationProductReturnOrder().getDestinationLocation().getLocationId()!=sr.getLocation().getLocationId())
						throw new BadRequestException("Return order not found for part code "+rq.getFaultyProduct().getProductPartCode()+" and serial number "+rq.getFaultyProductSerialNumber());
					
					rq.setApprovedStatus(true);
					rq.setModifiedBy(new EmployeeDetails(user.getUsername()));
					rq.setModifiedDate(new Date());
					rq.setSourceLocation(sr.getLocation());
					dhChangeRequest.save(rq);
					
					PartcodeChangeRequestApproval _approval=new PartcodeChangeRequestApproval();
					_approval.setApprovalDate(new Date());
					_approval.setChangeRequest(rq);
					_approval.setCreatedBy(new EmployeeDetails(user.getUsername()));
					_approval.setCreatedDate(new Date());
					_approval.setModifiedBy(new EmployeeDetails(user.getUsername()));
					_approval.setModifiedDate(new Date());
					_approval.setNewPartCode(rq.getFaultyProduct().getProductPartCode());
					_approval.setNewSerialnumber(rq.getFaultyProductSerialNumber());
					_approval.setOldPartcode(_approval.getNewPartCode());
					_approval.setOldSerialNumber(_approval.getNewSerialnumber());
					_approval.setRemarks("Approved by SCH");
					_approval.setLocation(rq.getSourceLocation());
					_approval.setSynced(false);
					
					dbRequestApproval.save(_approval);
				}
				else {
					//throw new BadRequestException("unable to process request");
					validateAndProcessNonWorkFlowData(rq,dhChangeRequest.findPendingRequestById(rq.getId()),user);
				}
			}
		}
		
	}

	@Override
	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	public void updatePartcode(Long id, Map<String, Object> updateInfo, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		PartcodeChangeRequest req=dhChangeRequest.findById(id).get();
		
		
		
		ServiceRecource sr=dbSr.findByUser(new EmployeeDetails(user.getUsername()));
		
		if(req.getApprovedStatus())
			throw new BadRequestException("Already approved");
		
		if(!(req.getAssignedTo().contains(getUserRole(user))||req.getAssignedTo().contains("USER:"+user.getUsername()))) {
			throw new BadRequestException("You are not authorise");
		}
		
		if(!isValidSerialNumber(updateInfo.get("faulty_product_serial_number").toString()))
			throw new BadRequestException("Invalid serial number "+updateInfo.get("faulty_product_serial_number").toString());
		
		Products oldFaulty=dbProduct.findByProductPartCode(req.getFaultyProduct().getProductPartCode());
		Products nFaulty=dbProduct.findByProductPartCode(updateInfo.get("faulty_product_product_part_code").toString());
		
		 
		//if(oldFaulty.getProductPartCode().equalsIgnoreCase(nFaulty.getProductPartCode()))
		//	throw new BadRequestException("Can not create request with same part code ");
		
		if(!oldFaulty.getProductType().equalsIgnoreCase(nFaulty.getProductType()))
			throw new BadRequestException("Product type mismatch");
		
		if(oldFaulty.getSubProductFamily().getProductFamilySubProductId()!=nFaulty.getSubProductFamily().getProductFamilySubProductId())
			throw new BadRequestException("Product sub family mismatch");
		
		
		if(req.getFlowType().equalsIgnoreCase(PartcodeChangeRequestRepository.FLOW_TYPE_SA)) {
			if(updateInfo.get("faulty_product_product_part_code").toString().equalsIgnoreCase(req.getFaultyProduct().getProductPartCode())) {
				
				if(!updateInfo.get("faulty_product_serial_number").toString().equalsIgnoreCase(req.getFaultyProductSerialNumber())) {
					
					ProductLineItem productLineItem=dbProductLineItem.findByProductAndSerialNumber(new Products(updateInfo.get("faulty_product_product_part_code").toString()), updateInfo.get("faulty_product_serial_number").toString());
					if(productLineItem!=null) {
						if(!productLineItem.getPositionState().equalsIgnoreCase(ProductLineItemRepository.ATTACHED_TO_ASSETS)) {
							throw new BadRequestException("Invalid new serial number");
						}
					}
					
					productLineItem=dbProductLineItem.findByProductAndSerialNumber(new Products(updateInfo.get("faulty_product_product_part_code").toString()), req.getFaultyProductSerialNumber());
					
					
					
					LProductReturnOrderLineItem roLineItem=getReturnOrderLineItem(req.getFaultyProduct().getProductPartCode(), req.getFaultyProductSerialNumber());
					if(roLineItem==null) {
						throw new BadRequestException("Return Order not found");
					}
					if(roLineItem.getReturnOrderItem().getLocationProductReturnOrder().getLocationReturnOrderStatus().equalsIgnoreCase("Draft"))
						
						throw new BadRequestException("Return Order not found");
					
					if(roLineItem.getReturnOrderItem().getLocationProductReturnOrder().getDestinationLocation().getLocationId()!=sr.getLocation().getLocationId())
						throw new BadRequestException("Return order not found");
					
					roLineItem.setProductLineItemSerialNumber(updateInfo.get("faulty_product_serial_number").toString());
					roLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
					roLineItem.setModifiedDate(new Date());
					dbLpReturnOrderLineItemRepo.save(roLineItem);
					
//					LocationProductLineItems oldLocationProductLineItem=dbLocationProductLineItem.findByItemSerialNumberAndLocationProductItem_product(req.getFaultyProductSerialNumber(), new Products(updateInfo.get("faulty_product_product_part_code").toString()));
//					if(oldLocationProductLineItem.getLocationProductItem().getLocation().getLocationId()!=sr.getLocation().getLocationId())
//						throw new BadRequestException("Stock does not exist in your stock");
//					if(oldLocationProductLineItem.getLocationProeuctLineItemState().getProductStateId()!=2)
//						throw new BadRequestException("Partcode is not faulty");
					
					if(productLineItem!=null) {
						
						if(!productLineItem.getPositionState().equalsIgnoreCase(ProductLineItemRepository.IN_TRANSIT))
							throw new BadRequestException("Old faulty part code and serial number not in transit mode");
						
						productLineItem.setSerialNumber(updateInfo.get("faulty_product_serial_number").toString());
						productLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
						productLineItem.setModifiedDate(new Date());
						dbProductLineItem.save(productLineItem);
					}
//					else {
//						productLineItem=new ProductLineItem();
//						productLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
//						productLineItem.setCreatedDate(new Date());
//						//productLineItem.setCustomerInventory(oldLocationProductLineItem.isCustomerInventory());
//						//productLineItem.setAccountName(oldLocationProductLineItem.getCustomerName());
//						//productLineItem.setAccountSapNumber(oldLocationProductLineItem.getCustomerSapNumber());
//						productLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
//						productLineItem.setModifiedDate(new Date());
//						productLineItem.setPosition(""+sr.getLocation().getLocationId());
//						productLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
//						productLineItem.setProduct(oldLocationProductLineItem.getLocationProductItem().getProduct());
//						productLineItem.setRefurbished(oldLocationProductLineItem.isRefurbished());
//						productLineItem.setRemarks("Part code change");
//						productLineItem.setSerialNumber(updateInfo.get("faulty_product_serial_number").toString());
//						productLineItem.setState(oldLocationProductLineItem.getLocationProeuctLineItemState());
//						productLineItem.setStatus(new ProductLineItemStatus(1));
//						dbProductLineItem.save(productLineItem);
//						
//					}
//					oldLocationProductLineItem.setItemSerialNumber(updateInfo.get("faulty_product_serial_number").toString());
//					oldLocationProductLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
//					oldLocationProductLineItem.setModifiedDate(new Date());
//					dbLocationProductLineItem.save(oldLocationProductLineItem);
					
					
				}
				else {
					LProductReturnOrderLineItem roLineItem=getReturnOrderLineItem(req.getFaultyProduct().getProductPartCode(), req.getFaultyProductSerialNumber());
					if(roLineItem==null) {
						throw new BadRequestException("Return Order not found");
					}
					if(roLineItem.getReturnOrderItem().getLocationProductReturnOrder().getLocationReturnOrderStatus().equalsIgnoreCase("Draft"))
						
						throw new BadRequestException("Return Order not found");
					
					if(roLineItem.getReturnOrderItem().getLocationProductReturnOrder().getDestinationLocation().getLocationId()!=sr.getLocation().getLocationId())
						throw new BadRequestException("Return order not found");
				}
				
			}
			else {
				validateAndProcessRequest(req,updateInfo,user);	
			}
			
			req.setApprovedStatus(true);
			req.setModifiedBy(new EmployeeDetails(user.getUsername()));
			req.setModifiedDate(new Date());
			req.setSourceLocation(sr.getLocation());
			
			dhChangeRequest.save(req);
			
			PartcodeChangeRequestApproval _approval=new PartcodeChangeRequestApproval();
			_approval.setApprovalDate(new Date());
			_approval.setChangeRequest(req);
			_approval.setCreatedBy(new EmployeeDetails(user.getUsername()));
			_approval.setCreatedDate(new Date());
			_approval.setLocation(req.getSourceLocation());
			_approval.setModifiedBy(new EmployeeDetails(user.getUsername()));
			_approval.setModifiedDate(new Date());
			_approval.setNewPartCode(updateInfo.get("faulty_product_product_part_code").toString());
			_approval.setNewSerialnumber(updateInfo.get("faulty_product_serial_number").toString());
			_approval.setOldPartcode(req.getFaultyProduct().getProductPartCode());
			_approval.setOldSerialNumber(req.getFaultyProductSerialNumber());
			_approval.setRemarks(updateInfo.get("remarks").toString());
			
			dbRequestApproval.save(_approval);
		}
		else {
			
			//throw new BadRequestException("Unable to serve request right now");
			validateAndProcessNonWorkFlowData(req,updateInfo,user);
		}
		
	}

	@Override
	public List<Map<String, Object>> getPendingToApprovedList(LoginUser user) {
		
		return dhChangeRequest.findPendingRequest("%"+getUserRole(user)+"%","%USER:"+user.getUsername()+"%",user.getUsername());
	}

	@Override
	public List<Map<String, Object>> getApprovedList(LoginUser user) {
		
		return dhChangeRequest.findApprovedAequestBy("%"+getUserRole(user)+"%","%USER:"+user.getUsername()+"%",user.getUsername());
	}
	
	@SuppressWarnings("unchecked")
	private String getUserRole(LoginUser user) {
		
		Iterator<LoginUserRoles> ite=(Iterator<LoginUserRoles>)user.getAuthorities().iterator();
		while(ite.hasNext()) {
			LoginUserRoles rl=ite.next();
			return "ROLE:"+rl.getAuthority();
		}
		return "NONROLE:NOROLLATALL";
	}
	
	private void validateAndProcessRequest(PartcodeChangeRequest req,Map<String,Object> info,LoginUser user) throws BadRequestException {
		
		ServiceRecource sr=dbSr.findByUser(new EmployeeDetails(user.getUsername()));
		
		ProductLineItem productLineItem=dbProductLineItem.findByProductAndSerialNumber(new Products(info.get("faulty_product_product_part_code").toString()),info.get("faulty_product_serial_number").toString());
		
		if(productLineItem!=null) {
			if(!(productLineItem.getPositionState().equalsIgnoreCase(ProductLineItemRepository.ASSETS_OUT)||productLineItem.getPositionState().equalsIgnoreCase(ProductLineItemRepository.ATTACHED_TO_ASSETS))) {
				throw new BadRequestException("Invalid Serial number");
			}
		}
		
		LocationProductLineItems litems=dbLocationProductLineItem.findByItemSerialNumberAndLocationProductItem_product(info.get("faulty_product_serial_number").toString(), new Products(info.get("faulty_product_product_part_code").toString()));
		if(litems!=null)
			throw new BadRequestException("Serial number "+litems.getItemSerialNumber()+" of part code "+litems.getLocationProductItem().getProduct().getProductPartCode() +" already exist at Location "+litems.getLocationProductItem().getLocation().getLocationName());
		
//		//NEW APPROCH FOR PARTCODE CHANGE
//		
//		LocationProductLineItems oldLineItem=dbLocationProductLineItem.findByItemSerialNumberAndLocationProductItem_product(req.getFaultyProductSerialNumber(), req.getFaultyProduct());
//		if(oldLineItem==null)
//			throw new BadRequestException("Old part code and serial number not found in stock");
//		if(oldLineItem.getLocationProeuctLineItemState().getProductStateId()!=2)
//			throw new BadRequestException("Part code is not in faulty state");
//		
//		if(sr.getLocation().getLocationId()!=oldLineItem.getLocationProductItem().getLocation().getLocationId())
//			throw new BadRequestException("Old stock does not exist in your stock");
//		
//		ProductLineItem oldProductItem=dbProductLineItem.findByProductAndSerialNumber(oldLineItem.getLocationProductItem().getProduct(), oldLineItem.getItemSerialNumber());
//		if(oldProductItem!=null) {
//			if(!oldProductItem.getPositionState().equalsIgnoreCase(ProductLineItemRepository.AT_LOCATION))
//				throw new BadRequestException("Stock is not present at location");
//			dbProductLineItem.delete(oldProductItem);
//		}
//		
//		LocationProductItem oldItem=oldLineItem.getLocationProductItem();
//		
//		oldItem.setQuantityInOnHand(oldItem.getQuantityInOnHand()-1);
//		oldItem.setFaultyQuantity(oldItem.getFaultyQuantity()-1);
//		oldItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
//		oldItem.setModifiedDate(new Date());
//		dbLocationProductItem.save(oldItem);
//		
//		
//		LocationProductItem newItem=dbLocationProductItem.findByProductAndLocation(new Products(info.get("faulty_product_product_part_code").toString()), sr.getLocation());
//		
//		if(newItem==null) {
//			newItem=new LocationProductItem();
//			
//			newItem.setBerQty(0);
//			newItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
//			newItem.setCreatedDate(new Date());
//			newItem.setCustomerInventory(0);
//			newItem.setDoaQuantity(0);
//			newItem.setFaultyQuantity(0);
//			newItem.setHslQuantity(0);
//			newItem.setHslStatus("L");
//			newItem.setIssuedQunatity(0);
//			newItem.setLocation(sr.getLocation());
////			newItem.setLocationProductItemId(locationProductItemId);
//			newItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
//			newItem.setModifiedDate(new Date());
//			newItem.setMslQuantity(0);
//			newItem.setMslQuantityStatus("L");
//			newItem.setOkQuantity(0);
////			newItem.setParentLocationProductItem(parentLocationProductItem);
//			newItem.setProduct(new Products(info.get("faulty_product_product_part_code").toString()));
//			newItem.setQuantityInOnHand(0);
//			newItem.setQuantityUnitOfMasur("NO");
//			newItem.setRefurbished(0);
//			newItem.setRejectedQty(0);
//			newItem.setScrapQty(0);
//			dbLocationProductItem.save(newItem);
//			newItem.setLocationProductItemId("PI-"+MiscellaneousUtils.fillZero(""+newItem.getId(), 5));
//			dbLocationProductItem.save(newItem);
//		}
//		
//		newItem.setQuantityInOnHand(newItem.getQuantityInOnHand()+1);
//		newItem.setFaultyQuantity(newItem.getFaultyQuantity()+1);
//		newItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
//		newItem.setModifiedDate(new Date());
//		
//		dbLocationProductItem.save(newItem);
//		
//		
//		LocationProductLineItems newProductLineItem=new LocationProductLineItems();
//		newProductLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
//		newProductLineItem.setCreatedDate(new Date());
//		newProductLineItem.setCustomerInventory(oldLineItem.isCustomerInventory());
//		newProductLineItem.setCustomerName(oldLineItem.getCustomerName());
//		newProductLineItem.setCustomerSapNumber(oldLineItem.getCustomerSapNumber());
//		newProductLineItem.setInStock(true);
//		newProductLineItem.setItemSerialNumber(info.get("faulty_product_serial_number").toString());
//		newProductLineItem.setLocationProductItem(newItem);
//		//newProductLineItem.setLocationProductLineItemId(locationProductLineItemId);
//		newProductLineItem.setLocationProductLineItemStatus(oldLineItem.getLocationProductLineItemStatus());
//		newProductLineItem.setLocationProeuctLineItemState(oldLineItem.getLocationProeuctLineItemState());
//		newProductLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
//		newProductLineItem.setModifiedDate(new Date());
//		newProductLineItem.setRefurbished(oldLineItem.isRefurbished());
//		newProductLineItem.setRemarks("Partcode change request approval");
//		newProductLineItem.setReplaced(false);
//		newProductLineItem.setReturned(false);
//		
//		dbLocationProductLineItem.save(newProductLineItem);
//		
//		newProductLineItem.setLocationProductLineItemId("PIL-"+MiscellaneousUtils.fillZero(""+newProductLineItem.getId(), 5));
//
//		dbLocationProductLineItem.save(newProductLineItem);
//		
//		req.setSourceLocation(sr.getLocation());
//		req.setModifiedBy(new EmployeeDetails(user.getUsername()));
//		req.setModifiedDate(new Date());
//		dhChangeRequest.save(req);
//		
//		if(productLineItem!=null) {
//			productLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
//			productLineItem.setModifiedDate(new Date());
//			productLineItem.setPosition(""+sr.getLocation().getLocationId());
//			productLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
//			productLineItem.setState(newProductLineItem.getLocationProeuctLineItemState());
//			productLineItem.setStatus(new ProductLineItemStatus(1));
//			dbProductLineItem.save(productLineItem);
//		}
//		else {
//			productLineItem=new ProductLineItem();
//			productLineItem.setAccountName(newProductLineItem.getCustomerName());
//			productLineItem.setAccountSapNumber(newProductLineItem.getCustomerSapNumber());
//			productLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
//			productLineItem.setCreatedDate(new Date());
//			productLineItem.setCustomerInventory(newProductLineItem.isCustomerInventory());
//			productLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
//			productLineItem.setModifiedDate(new Date());
//			productLineItem.setPosition(""+sr.getLocation().getLocationId());
//			productLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
//			productLineItem.setProduct(newProductLineItem.getLocationProductItem().getProduct());
//			productLineItem.setRefurbished(newProductLineItem.isRefurbished());
//			productLineItem.setRemarks("Partcode change");
//			productLineItem.setSerialNumber(newProductLineItem.getItemSerialNumber());
//			productLineItem.setState(newProductLineItem.getLocationProeuctLineItemState());
//			productLineItem.setStatus(new ProductLineItemStatus(1));
//			dbProductLineItem.save(productLineItem);
//		}
//		
//		dbLocationProductLineItem.delete(oldLineItem);
//		
//		//END OF NEW APPROCH FOR PARTCODE CHANGE
		
		
		//GETTING RETURN ORDER FOR OLD PART CODE AND OLD SERIAL NUMBER
		
		LProductReturnOrderLineItem oldReturnOrderLineItem=getReturnOrderLineItem(req.getFaultyProduct().getProductPartCode(),req.getFaultyProductSerialNumber());
		
		if(oldReturnOrderLineItem==null)
			throw new BadRequestException("Return order not found for old faulty part code and serial number");
		if(oldReturnOrderLineItem.getReturnOrderItem().getLocationProductReturnOrder().getLocationReturnOrderStatus().equalsIgnoreCase("Draft"))
			throw new BadRequestException("Return order not found for old faulty part code and serial number");
		if(oldReturnOrderLineItem.getReturnOrderItem().getLocationProductReturnOrder().getDestinationLocation().getLocationId()!=sr.getLocation().getLocationId())
			throw new BadRequestException("Return order not found for old faulty part code and serial number");
		
		LProductReturnOrderItem oldReturnOrderItem= oldReturnOrderLineItem.getReturnOrderItem();
		oldReturnOrderItem.setTotalReturnQuantity(oldReturnOrderItem.getTotalReturnQuantity()-1);
		oldReturnOrderItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
		oldReturnOrderItem.setModifiedDate(new Date());
		
		dbLpReturnOrderItemRepo.save(oldReturnOrderItem);
		dbLpReturnOrderLineItemRepo.delete(oldReturnOrderLineItem);
		
		LProductReturnOrder ro=oldReturnOrderItem.getLocationProductReturnOrder();
		
		//GETING RETURN ORDER ITEM INFO IF AVILABLE FOR NEW PART CODE
		
		LProductReturnOrderItem newReturnOrderItem=getReturnOrderItem(ro.getLprOrderid(),info.get("faulty_product_product_part_code").toString());
		if(newReturnOrderItem!=null) {
			newReturnOrderItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			newReturnOrderItem.setModifiedDate(new Date());
			newReturnOrderItem.setTotalReturnQuantity(newReturnOrderItem.getTotalReturnQuantity()+1);
			dbLpReturnOrderItemRepo.save(newReturnOrderItem);
		}
		else {
			LocationProductItem lpItem=null;
			try {
				lpItem=dbLocationProductItem.findByProductAndLocation(new Products(info.get("faulty_product_product_part_code").toString()), req.getSourceLocation());
			}catch(NoResultException ex) {
				
			}
			if(lpItem==null) {
				lpItem=new LocationProductItem();
				lpItem.setBerQty(0);
				lpItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
				lpItem.setCreatedDate(new Date());
				lpItem.setCustomerInventory(0);
				lpItem.setDoaQuantity(0);
				lpItem.setFaultyQuantity(0);
				lpItem.setHslQuantity(0);
				lpItem.setHslStatus("L");
				lpItem.setIssuedQunatity(0);
				lpItem.setLocation(req.getSourceLocation());
				//lpItem.setLocationProductItemId(locationProductItemId);
				lpItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				lpItem.setModifiedDate(new Date());
				lpItem.setMslQuantity(0);
				lpItem.setMslQuantityStatus("L");
				lpItem.setOkQuantity(0);
				lpItem.setProduct(new Products(info.get("faulty_product_product_part_code").toString()));
				lpItem.setQuantityInOnHand(0);
				lpItem.setQuantityUnitOfMasur("NO");
				lpItem.setRefurbished(0);
				lpItem.setRejectedQty(0);
				lpItem.setScrapQty(0);
				dbLocationProductItem.save(lpItem);
			}
			newReturnOrderItem=new LProductReturnOrderItem();
			newReturnOrderItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
			newReturnOrderItem.setCreatedDate(new Date());
			newReturnOrderItem.setLocationProductReturnOrder(ro);
			//newReturnOrderItem.setLproItemManualId(lproItemManualId);
			newReturnOrderItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			newReturnOrderItem.setModifiedDate(new Date());
			newReturnOrderItem.setOwner(ro.getOwner());
			newReturnOrderItem.setProduct(new Products(info.get("faulty_product_product_part_code").toString()));
			newReturnOrderItem.setProductItem(lpItem);
			newReturnOrderItem.setReturnDate(new Date());
			newReturnOrderItem.setSynked(false);
			newReturnOrderItem.setTotalReceiveQuantity(0);
			newReturnOrderItem.setTotalReturnQuantity(1);
			dbLpReturnOrderItemRepo.save(newReturnOrderItem);
			newReturnOrderItem.setLproItemManualId("ROI-"+MiscellaneousUtils.fillZero(""+newReturnOrderItem.getLproItemid(), 5));
			dbLpReturnOrderItemRepo.save(newReturnOrderItem);
			
		}
		
		LProductReturnOrderLineItem newReturnOrderLineItem=new LProductReturnOrderLineItem();
		newReturnOrderLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
		newReturnOrderLineItem.setCreatedDate(new Date());
		newReturnOrderLineItem.setCustomerInventory(false);
		//newReturnOrderLineItem.setCustomerName(customerName);
		newReturnOrderLineItem.setLineItemState(2);
		//newReturnOrderLineItem.setLproLineitemManualId(lproLineitemManualId);
		newReturnOrderLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
		newReturnOrderLineItem.setModifiedDate(new Date());
		newReturnOrderLineItem.setProductLineItemSerialNumber(info.get("faulty_product_serial_number").toString());
		newReturnOrderLineItem.setQuantityReceived(0);
		newReturnOrderLineItem.setQuantityReturned(1);
		newReturnOrderLineItem.setReasonForReturn("Faulty serial number from site");
		newReturnOrderLineItem.setReturnOrderItem(newReturnOrderItem);
		newReturnOrderLineItem.setReturnStatus("Submit");
		dbLpReturnOrderLineItemRepo.save(newReturnOrderLineItem);
		newReturnOrderLineItem.setLproLineitemManualId("ROLI-"+MiscellaneousUtils.fillZero(""+newReturnOrderLineItem.getLproLineitemId(), 5));
		dbLpReturnOrderLineItemRepo.save(newReturnOrderLineItem);
		
		if(productLineItem!=null) {
			productLineItem.setPositionState(ProductLineItemRepository.IN_TRANSIT);
			productLineItem.setPosition("RO,"+ro.getLprOrderid());
			productLineItem.setModifiedDate(new Date());
			productLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			productLineItem.setState(new ProductLineItemProductState(2));
			productLineItem.setStatus(new ProductLineItemStatus(2));
			dbProductLineItem.save(productLineItem);
		}
		
//		//END OF OLD FLOW
		
	}
	
	private LProductReturnOrderLineItem getReturnOrderLineItem(String partcode,String serialNumber) {
		return dbRturnRepo.findByProductLineItemSerialNumberAndLocationProductReturnOrder_productAndQuantityReceived(new Products(partcode), serialNumber,0);
	}
	
	private List<LProductReturnOrderLineItem> getReturnOrderLineItemReceived(String partcode,String serialNumber) {
		return dbRturnRepo.findByProductLineItemSerialNumberAndLocationProductReturnOrder_productAndQuantityReceivedReceived(new Products(partcode), serialNumber,1);
	}
	
	private LProductReturnOrderItem getReturnOrderItem(long returnOrderId,String partcode) {
		return dbRturnRepo.findByLocationProductReturnOrderAndProduct(new LProductReturnOrder(returnOrderId),new Products(partcode));
	}

	@Override
	public List<Map<String, Object>> searchPendingToApproveList(Map<String, Object> searchkey, LoginUser user) {
		String query="SELECT \r\n" + 
				"	req.`id`,\r\n" + 
				"	req.manual_id,\r\n"+
				"	req.`approved_status`,\r\n" + 
				"	req.`assigned_to`,\r\n" + 
				"	req.`circle`,\r\n" + 
				"	req.`consumed_product_serial_number`,\r\n" + 
				"	req.`create_date`,\r\n" + 
				"	req.`engineer_name`,\r\n" + 
				"	req.`faulty_product_serial_number`,\r\n" + 
				"	req.`modified_date`,\r\n" + 
				"	req.`price_variance`,\r\n" + 
				"	req.`request_date`,\r\n" + 
				"	req.`sa_id`,\r\n" + 
				"	req.`sa_manual_id`,\r\n" + 
				"	req.`consumed_product_product_part_code`,\r\n" + 
				"	cprd.`pdoduct_description` AS consumed_product_description,\r\n" + 
				"	cprd.`product_price` AS consumed_product_price,\r\n" + 
				"	req.`created_by_email_id`,\r\n" + 
				"	req.`faulty_product_product_part_code`,\r\n" + 
				"	fprd.`pdoduct_description` AS faulty_product_description,\r\n" + 
				"	fprd.`product_price` AS faulty_product_price,\r\n" + 
				"	req.`flow_type`,\r\n"+
				"	req.`initiated_by_email_id`,\r\n" + 
				"	req.`modified_by_email_id`\r\n" + 
				"	\r\n" + 
				" FROM `partcode_change_request` req\r\n" + 
				"	left JOIN `products` cprd ON cprd.`product_part_code`=req.`consumed_product_product_part_code`\r\n" + 
				"	JOIN `products` fprd ON fprd.`product_part_code`=req.`faulty_product_product_part_code` WHERE req.`approved_status`=FALSE AND (req.`assigned_to` LIKE ? or req.`assigned_to` like ?)";
		List<String> lstparam=new ArrayList<String>();
		
		lstparam.add("%"+getUserRole(user)+"%");
		lstparam.add("%USER:"+user.getUsername()+"%");
		
		
		if(searchkey.get("refid")!=null&&!searchkey.get("refid").toString().equalsIgnoreCase("")) {
			query+=" and req.manual_id like ?";
			lstparam.add("%"+searchkey.get("refid").toString()+"%");
		}
		
		if(searchkey.get("partcode")!=null&&!searchkey.get("partcode").toString().equalsIgnoreCase("")) {
			query+=" and (req.consumed_product_product_part_code like ? or req.faulty_product_product_part_code like ?)";
			lstparam.add("%"+searchkey.get("partcode").toString()+"%");
			lstparam.add("%"+searchkey.get("partcode").toString()+"%");
		}
		if(searchkey.get("serialnumber")!=null&&!searchkey.get("serialnumber").toString().equalsIgnoreCase("")) {
			query+=" and (req.consumed_product_serial_number like ? or req.faulty_product_serial_number like ?)";
			lstparam.add("%"+searchkey.get("serialnumber").toString()+"%");
			lstparam.add("%"+searchkey.get("serialnumber").toString()+"%");
		}
		
		return jdbc.queryForList(query,lstparam.toArray());
	}

	@Override
	public List<Map<String, Object>> searchApprovedList(Map<String, Object> searchkey, LoginUser user) {
		String query="SELECT   \r\n" + 
				"				req.`id`,  \r\n" + 
				"				req.manual_id,\r\n"+
				"				req.`approved_status`,  \r\n" + 
				"				req.`assigned_to`,  \r\n" + 
				"				req.`circle`,  \r\n" + 
				"				req.`consumed_product_serial_number`,  \r\n" + 
				"				req.`create_date`,  \r\n" + 
				"				req.`engineer_name`,  \r\n" + 
				"				req.`faulty_product_serial_number`,  \r\n" + 
				"				req.`modified_date`,  \r\n" + 
				"				req.`price_variance`,  \r\n" + 
				"				req.`request_date`,  \r\n" + 
				"				req.`sa_id`,  \r\n" + 
				"				req.`sa_manual_id`,  \r\n" + 
				"				req.`location_name`,\r\n"+
				" 				req.`legal_entity`,\r\n"+
				"				req.`consumed_product_product_part_code`,  \r\n" + 
				"				cprd.`pdoduct_description` AS consumed_product_description,  \r\n" + 
				"				cprd.`product_price` AS consumed_product_price,  \r\n" + 
				"				req.`created_by_email_id`,  \r\n" + 
				"				req.`faulty_product_product_part_code`,  \r\n" + 
				"				fprd.`pdoduct_description` AS faulty_product_description,  \r\n" + 
				"				fprd.`product_price` AS faulty_product_price,  \r\n" + 
				"				req.`initiated_by_email_id`,  \r\n" + 
				"				req.`modified_by_email_id`,  \r\n" + 
				"				apr.`approval_date`,  \r\n" + 
				"				apr.`new_part_code`,  \r\n" + 
				"				apr.`new_serialnumber`,  \r\n" + 
				"				apr.`old_partcode`,  \r\n" + 
				"				apr.`old_serial_number`,  \r\n" + 
				"				req.`flow_type`,\r\n" + 
				"				apr.`created_by_email_id` as `approved_by_email_id`,  \r\n" + 
				"				(select `pdoduct_description` from `products` where product_part_code=apr.`old_partcode`) AS `old_product_description`,  \r\n" + 
				"				(select `pdoduct_description` from `products` where product_part_code=apr.`new_part_code`) AS `new_product_description`,  \r\n" + 
				"				apr.mblnr,\r\n" + 
				"				apr.mblnr_c,\r\n" + 
				"				apr.mblnr_u,\r\n" + 
				"				apr.mjahr,\r\n" + 
				"				apr.mjahr_c,\r\n" + 
				"				apr.mjahr_u,\r\n" + 
				"				apr.remark_c,\r\n" + 
				"				apr.remark_u\r\n" + 
				"			 FROM `partcode_change_request` req  \r\n" + 
				"				left JOIN `products` cprd ON cprd.`product_part_code`=req.`consumed_product_product_part_code`  \r\n" + 
				"				JOIN `products` fprd ON fprd.`product_part_code`=req.`faulty_product_product_part_code`   \r\n" + 
				"				JOIN `partcode_change_request_approval` apr ON apr.id=req.id  \r\n" + 
				"				WHERE (req.`assigned_to` LIKE ? or req.`assigned_to` like ? or req.initiated_by_email_id like ?) ";
		List<String> lstparam=new ArrayList<String>();
		
		lstparam.add("%"+getUserRole(user)+"%");
		lstparam.add("%USER:"+user.getUsername()+"%");
		lstparam.add("%"+user.getUsername()+"%");
		
		if(searchkey.get("refid")!=null&&!searchkey.get("refid").toString().equalsIgnoreCase("")) {
			query+=" and req.manual_id like ?";
			lstparam.add("%"+searchkey.get("refid").toString()+"%");
		}
		
		if(searchkey.get("partcode")!=null&&!searchkey.get("partcode").toString().equalsIgnoreCase("")) {
			query+=" and (req.consumed_product_product_part_code like ? or req.faulty_product_product_part_code like ? or apr.`new_part_code` like ? or apr.`old_partcode` like ?)";
			lstparam.add("%"+searchkey.get("partcode").toString()+"%");
			lstparam.add("%"+searchkey.get("partcode").toString()+"%");
			lstparam.add("%"+searchkey.get("partcode").toString()+"%");
			lstparam.add("%"+searchkey.get("partcode").toString()+"%");
		}
		if(searchkey.get("serialnumber")!=null&&!searchkey.get("serialnumber").toString().equalsIgnoreCase("")) {
			query+=" and (req.consumed_product_serial_number like ? or req.faulty_product_serial_number like ? or apr.`new_serialnumber` like ? or apr.`old_serial_number` like ?)";
			lstparam.add("%"+searchkey.get("serialnumber").toString()+"%");
			lstparam.add("%"+searchkey.get("serialnumber").toString()+"%");
			lstparam.add("%"+searchkey.get("serialnumber").toString()+"%");
			lstparam.add("%"+searchkey.get("serialnumber").toString()+"%");
		}
		
		query+=" order by req.`modified_date`";
		
		return jdbc.queryForList(query,lstparam.toArray());
	}

	@Override
	public List<Map<String, Object>> elegibleForChange(LoginUser user) {
		
		return dbSaChangeRequest.getElegibalPartcode("%"+getUserRole(user)+"%", "%USER:"+user.getUsername()+"%");
		//return dbSaChangeRequest.getElegibalPartCodeFromLocation(user.getUsername());
	}

	@Override
	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	public void createNonworkflowPartcodeChange(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		PartcodeChangeSa saChange=dbSaChangeRequest.findByChangeId(Long.parseLong(request.get("conmuption_id").toString()));
		
		if(!isValidSerialNumber(request.get("faulty_serial_number").toString()))
			throw new BadRequestException("Serial number "+request.get("faulty_serial_number").toString()+" is not valid");
		
		if(saChange==null)
			throw new BadRequestException("Invalid Id");
		
		if(saChange.getAssigned())
			throw new BadRequestException("Request already assigned");
		
		Products oldproduct=dbProduct.findByProductPartCode(saChange.getFaultyProduct().getProductPartCode());
		Products rqPartCode=dbProduct.findByProductPartCode(request.get("faulty_part_code").toString());
		//VALIDATION forcibly APPLIED- MAIL DATED -24-MARCH-2021 BY MANPREET MADAM
		if(oldproduct.getProductPartCode().equalsIgnoreCase(rqPartCode.getProductPartCode()))
			throw new BadRequestException("Can not create request with same part code");
		
		if(!oldproduct.getProductType().equalsIgnoreCase(rqPartCode.getProductType())) {
			throw new BadRequestException("Invalid product type for faulty product part code "+rqPartCode.getProductPartCode());
		}
		
		if(oldproduct.getSubProductFamily().getProductFamilySubProductId()!=rqPartCode.getSubProductFamily().getProductFamilySubProductId())
			throw new BadRequestException("Product sub family mismatch");
		
		
		
//		if(saChange.getAssignedTo().contains(getUserRole(user))||saChange.getAssignedTo().contains("USER:"+user.getUsername())) {
//			
//		}
//		else {
//			throw new UnauthoriseException("You are not authorised");
//		}
		
		ServiceRecource sr=null;
		try {
			
			sr=dbSr.findByUser(new EmployeeDetails(user.getUsername()));
			
		}catch(NonUniqueResultException ex) {
			
			throw new BadRequestException("Multiple location found");
		}			
		
		if(sr==null) {
			throw new BadRequestException("Location not found");
		}
		if(sr.getLocation()==null) {
			throw new BadRequestException("Location not found");
		}
//		
		LocationProductLineItems stockLineItem= dbLocationProductLineItem.findByItemSerialNumberAndLocationProductItem_product(saChange.getFaultyProductSerialNumber(), saChange.getFaultyProduct());
		if(stockLineItem==null) {
			//throw new BadRequestException("Stock for part code "+saChange.getFaultyProduct()+" for serial number "+saChange.getFaultyProductSerialNumber()+" not found");
			LProductReturnOrderLineItem roItem=getReturnOrderLineItem(saChange.getFaultyProduct().getProductPartCode(),saChange.getFaultyProductSerialNumber());
			if(roItem==null) {
				List<LProductReturnOrderLineItem> ls=getReturnOrderLineItemReceived(saChange.getFaultyProduct().getProductPartCode(), saChange.getFaultyProductSerialNumber());
				if(ls.size()==0)
					throw new BadRequestException("Product partcode "+saChange.getFaultyProduct().getProductPartCode()+" with serial number "+saChange.getFaultyProductSerialNumber()+" has not received/send through your location yet");
			}
		}
		else {
			if(stockLineItem.getLocationProductItem().getLocation().getLocationId()!=sr.getLocation().getLocationId()) {
				LProductReturnOrderLineItem roItem=getReturnOrderLineItem(saChange.getFaultyProduct().getProductPartCode(),saChange.getFaultyProductSerialNumber());
				if(roItem==null) {
					List<LProductReturnOrderLineItem> ls=getReturnOrderLineItemReceived(saChange.getFaultyProduct().getProductPartCode(), saChange.getFaultyProductSerialNumber());
					if(ls.size()==0)
						throw new BadRequestException("Product partcode "+saChange.getFaultyProduct().getProductPartCode()+" with serial number "+saChange.getFaultyProductSerialNumber()+" has not received/send through your location yet");
				}
			}
		}
//		if(stockLineItem.getLocationProductItem().getLocation().getLocationId()!=sr.getLocation().getLocationId()) {
//			throw new BadRequestException("Stock for part code "+saChange.getFaultyProduct()+" for serial number "+saChange.getFaultyProductSerialNumber()+" not found at your stock");
//		}
		
		List<PartCodechangeApproval> approvar=dbChangeApproval.findByActiveAndInitRoleLikeOrInitRoleLike(true, "%"+getUserRole(user)+"%", "%USER:"+user.getUsername()+"%");
		
		
		PartcodeChangeRequest partchangeRequest=new PartcodeChangeRequest();
		partchangeRequest.setApprovedStatus(false);
		
		if(approvar.size()>0) {
			partchangeRequest.setAssignedTo(approvar.get(0).getAssignedToRoles());	
		}
		//partchangeRequest.setAssignedTo(assignedTo);
		partchangeRequest.setCircle(saChange.getCircle());
		partchangeRequest.setConsumedProduct(saChange.getFaultyProduct());
		partchangeRequest.setConsumedProductSerialNumber(saChange.getFaultyProductSerialNumber());
		partchangeRequest.setCreateDate(new Date());
		partchangeRequest.setCreatedBy(new EmployeeDetails(user.getUsername()));
		partchangeRequest.setEngineerName(saChange.getEngineerName());
		partchangeRequest.setFaultyProduct(new Products(request.get("faulty_part_code").toString()));
		partchangeRequest.setFaultyProductSerialNumber(request.get("faulty_serial_number").toString());
		partchangeRequest.setFlowType(PartcodeChangeRequestRepository.FLOW_TYPE_NON_SA);
		partchangeRequest.setInitiatedBy(new EmployeeDetails(user.getUsername()));
		partchangeRequest.setModifiedBy(new EmployeeDetails(user.getUsername()));
		partchangeRequest.setModifiedDate(new Date());
		partchangeRequest.setRequestDate(new Date());
		partchangeRequest.setSaId(saChange.getSaId());
		partchangeRequest.setSaManualId(saChange.getSaManualId());
		partchangeRequest.setSourceLocation(sr.getLocation());
		partchangeRequest.setPartcodeChangeSa(saChange);
		
		//TWO ATTRIBUTE ADDED ON 08-04-2021 AFTER MANPREET MAIL
		
		partchangeRequest.setLocationName(sr.getLocation().getLocationName());
		partchangeRequest.setLegalEntity(sr.getLocation().getLegalEntity().getLegalEntityName());
		dhChangeRequest.save(partchangeRequest);
		String manualId="PCR-"+MiscellaneousUtils.fillZero(String.valueOf(partchangeRequest.getId()), 5);
		partchangeRequest.setManualId(manualId);
		dhChangeRequest.save(partchangeRequest);
		
		saChange.setAssigned(true);
		
		dbSaChangeRequest.save(saChange);
		
	}
	
	private void validateAndProcessNonWorkFlowData(PartcodeChangeRequest req,Map<String,Object> info,LoginUser user) throws BadRequestException,UnauthoriseException {
		if(req.getPartcodeChangeSa()==null)
			throw new BadRequestException("Not a valid request SA not found");
		if(!isValidSerialNumber(info.get("faulty_product_serial_number").toString()))
			throw new BadRequestException("New srial number "+info.get("faulty_product_serial_number").toString()+" is not valid");
		
		ServiceRecource sr=null;
		try {
			
			sr=dbSr.findByUser(new EmployeeDetails(user.getUsername()));
			
		}catch(NonUniqueResultException ex) {
			
			throw new BadRequestException("Multiple location found");
		}			
		
		if(sr==null) {
			throw new BadRequestException("Location not found");
		}
		if(sr.getLocation()==null) {
			throw new BadRequestException("Location not found");
		}
		
		//CHECKING FOR REPLACED SERIAL NUMBER AND STOCK
		LocationProductLineItems existingLocationProductLineItems=dbLocationProductLineItem.findByItemSerialNumberAndLocationProductItem_product(req.getPartcodeChangeSa().getFaultyProductSerialNumber(),req.getPartcodeChangeSa().getFaultyProduct());
		if(existingLocationProductLineItems==null) {
			throw new BadRequestException("Replaced Product "+req.getPartcodeChangeSa().getFaultyProduct().getProductPartCode()+" with serial number "+req.getPartcodeChangeSa().getFaultyProductSerialNumber()+" not found at stock");
		}
		if(existingLocationProductLineItems.getLocationProductItem().getLocation().getLocationId()!=sr.getLocation().getLocationId()) {
			throw new BadRequestException("Stock does not exists at your location");
		}
		
		//AS DISCUSSED TO MANOJ SOFALI AND HEMNAT NEET TO COMMENT
		
//		if(existingLocationProductLineItems.getLocationProeuctLineItemState().getProductStateId()!=2)
//			throw new BadRequestException("Stock is not in faulty state");
//		
		
		LocationProductItem existingLocationProductItem=existingLocationProductLineItems.getLocationProductItem();
		
		if(existingLocationProductItem.getLocation().getLocationId()!=sr.getLocation().getLocationId())
			throw new BadRequestException("Product is not in your stock");
		
		
		existingLocationProductItem.setQuantityInOnHand((existingLocationProductItem.getQuantityInOnHand()-1));
		if(existingLocationProductLineItems.isCustomerInventory())
			existingLocationProductItem.setCustomerInventory(existingLocationProductItem.getCustomerInventory()-1);
		long stateId=existingLocationProductLineItems.getLocationProeuctLineItemState().getProductStateId();
		if(stateId==1) {
			existingLocationProductItem.setOkQuantity(existingLocationProductItem.getOkQuantity()-1);
		}
		if(stateId==2) {
			existingLocationProductItem.setFaultyQuantity(existingLocationProductItem.getFaultyQuantity()-1);
		}
		if(stateId==3) {
			existingLocationProductItem.setScrapQty(existingLocationProductItem.getScrapQty()-1);
		}
		if(stateId==4) {
			existingLocationProductItem.setRejectedQty(existingLocationProductItem.getRejectedQty()-1);
		}
		if(stateId==5) {
			existingLocationProductItem.setBerQty(existingLocationProductItem.getBerQty()-1);
		}
		if(stateId==6) {
			existingLocationProductItem.setDoaQuantity(existingLocationProductItem.getDoaQuantity()-1);
		}
		
		dbLocationProductItem.save(existingLocationProductItem);
		
		ProductLineItem existingProductLineItem=dbProductLineItem.findByProductAndSerialNumber(existingLocationProductItem.getProduct(), existingLocationProductLineItems.getItemSerialNumber());
		
		
		LocationProductLineItems newLocationProductLineItem=dbLocationProductLineItem.findByItemSerialNumberAndLocationProductItem_product(info.get("faulty_product_serial_number").toString(),new Products(info.get("faulty_product_product_part_code").toString()));
		if(newLocationProductLineItem!=null) {
			throw new BadRequestException("New Serial number for new product part code already exists in stock");
		}
		else {
			ProductLineItem nProductLineItem=dbProductLineItem.findByProductAndSerialNumber(new Products(info.get("faulty_product_product_part_code").toString()), info.get("faulty_product_serial_number").toString());
			
			if(nProductLineItem!=null) {
				if(!nProductLineItem.getPositionState().equalsIgnoreCase(ProductLineItemRepository.ATTACHED_TO_ASSETS))
					throw new BadRequestException("New Serial number "+info.get("faulty_product_serial_number").toString()+" for product "+info.get("faulty_product_product_part_code").toString());
			}
			
			LocationProductItem locationProductItem=dbLocationProductItem.findByProductAndLocation(new Products(info.get("faulty_product_product_part_code").toString()), existingLocationProductItem.getLocation());
			if(locationProductItem==null) {
				locationProductItem=new LocationProductItem();
				locationProductItem.setBerQty(0);
				locationProductItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
				locationProductItem.setCreatedDate(new Date());
				locationProductItem.setCustomerInventory(0);
				locationProductItem.setDoaQuantity(0);
				locationProductItem.setFaultyQuantity(0);
				locationProductItem.setHslQuantity(0);
				locationProductItem.setHslStatus("L");
				locationProductItem.setIssuedQunatity(0);
				locationProductItem.setLocation(existingLocationProductItem.getLocation());
				//locationProductItem.setLocationProductItemId(locationProductItemId);
				locationProductItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				locationProductItem.setModifiedDate(new Date());
				locationProductItem.setMslQuantity(0);
				locationProductItem.setMslQuantityStatus("L");
				locationProductItem.setOkQuantity(0);
				locationProductItem.setProduct(new Products(info.get("faulty_product_product_part_code").toString()));
				locationProductItem.setQuantityInOnHand(0);
				locationProductItem.setQuantityUnitOfMasur("NO");
				locationProductItem.setRefurbished(0);
				locationProductItem.setRejectedQty(0);
				locationProductItem.setScrapQty(0);
				dbLocationProductItem.save(locationProductItem);
				locationProductItem.setLocationProductItemId("PI-"+MiscellaneousUtils.fillZero(""+locationProductItem.getId(), 5));
				dbLocationProductItem.save(locationProductItem);
			}
		
			locationProductItem.setQuantityInOnHand(locationProductItem.getQuantityInOnHand()+1);
			locationProductItem.setFaultyQuantity(locationProductItem.getFaultyQuantity()+1);
			locationProductItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			locationProductItem.setModifiedDate(new Date());
			dbLocationProductItem.save(locationProductItem);
			
			LocationProductLineItems locationProductLineItems=new LocationProductLineItems();
			locationProductLineItems.setCreatedBy(new EmployeeDetails(user.getUsername()));
			locationProductLineItems.setCreatedDate(new Date());
			locationProductLineItems.setCustomerInventory(false);
			locationProductLineItems.setInStock(true);
			locationProductLineItems.setItemSerialNumber(info.get("faulty_product_serial_number").toString());
			locationProductLineItems.setLocationProductItem(locationProductItem);
			//locationProductLineItems.setLocationProductLineItemId(locationProductLineItemId);
			locationProductLineItems.setLocationProductLineItemStatus(new ProductLineItemStatus(1));
			locationProductLineItems.setLocationProeuctLineItemState(new ProductLineItemProductState(stateId));
			locationProductLineItems.setModifiedBy(new EmployeeDetails(user.getUsername()));
			locationProductLineItems.setModifiedDate(new Date());
			locationProductLineItems.setRefurbished(false);
			locationProductLineItems.setRemarks("Part code change request approved by "+user.getUsername());
			locationProductLineItems.setReplaced(false);
			locationProductLineItems.setReturned(false);
			dbLocationProductLineItem.save(locationProductLineItems);
			locationProductLineItems.setLocationProductLineItemId("PIL-"+MiscellaneousUtils.fillZero(""+locationProductLineItems.getId(), 5));
			dbLocationProductLineItem.save(locationProductLineItems);
			
			PartcodeChangeRequestApproval reqApproval=new PartcodeChangeRequestApproval();
			
			reqApproval.setApprovalDate(new Date());
			reqApproval.setChangeRequest(req);
			reqApproval.setCreatedBy(new EmployeeDetails(user.getUsername()));
			reqApproval.setCreatedDate(new Date());
			reqApproval.setLocation(locationProductItem.getLocation());
			reqApproval.setModifiedBy(new EmployeeDetails(user.getUsername()));
			reqApproval.setModifiedDate(new Date());
			reqApproval.setNewPartCode(info.get("faulty_product_product_part_code").toString());
			reqApproval.setNewSerialnumber(info.get("faulty_product_serial_number").toString());
			reqApproval.setOldPartcode(req.getFaultyProduct().getProductPartCode());
			reqApproval.setOldSerialNumber(req.getFaultyProductSerialNumber());
			if(info.get("remarks")!=null)
				reqApproval.setRemarks(info.get("remarks").toString());
			else {
				reqApproval.setRemarks("");
			}
			reqApproval.setSynced(false);
			dbRequestApproval.save(reqApproval);
			
			req.setApprovedStatus(true);
			req.setModifiedBy(new EmployeeDetails(user.getUsername()));
			req.setModifiedDate(new Date());
			req.setSourceLocation(sr.getLocation());
			
			dhChangeRequest.save(req);
			
			PartcodeChangeSa _saChange=req.getPartcodeChangeSa();
			_saChange.setApprovedStatus(true);
			_saChange.setModifiedBy(new EmployeeDetails(user.getUsername()));
			_saChange.setModifiedDate(new Date());
			
			dbSaChangeRequest.save(_saChange);

//			UNCOMMENT WHEN NEED TO ENABLE CHANGE OVER CHANGE FOR NEW PART CODE			
//			PartcodeChangeSa _newChange=new PartcodeChangeSa();
//			_newChange.setApprovedStatus(false);
//			_newChange.setAssigned(false);
//			_newChange.setAssignedTo(_saChange.getAssignedTo());
//			_newChange.setCircle(_saChange.getCircle());
//			_newChange.setConsumedProduct(_saChange.getConsumedProduct());
//			_newChange.setConsumedProductSerialNumber(_saChange.getConsumedProductSerialNumber());
//			_newChange.setCreateDate(new Date());
//			_newChange.setCreatedBy(new EmployeeDetails(user.getUsername()));
//			_newChange.setEngineerName(_saChange.getEngineerName());
//			_newChange.setFaultyProduct(locationProductItem.getProduct());
//			_newChange.setFaultyProductSerialNumber(locationProductLineItems.getItemSerialNumber());
//			_newChange.setFlowType(PartcodeChangeRequestRepository.FLOW_TYPE_NON_SA);
//			_newChange.setInitiatedBy(new EmployeeDetails(user.getUsername()));
//			_newChange.setModifiedBy(new EmployeeDetails(user.getUsername()));
//			_newChange.setModifiedDate(new Date());
//			_newChange.setRequestDate(new Date());
//			_newChange.setSaId(_saChange.getSaId());
//			_newChange.setSaManualId(_saChange.getSaManualId());
//			_newChange.setSourceLocation(sr.getLocation());
//			
//			dbSaChangeRequest.save(_newChange);
			
			if(nProductLineItem!=null) {
				nProductLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				nProductLineItem.setModifiedDate(new Date());
				nProductLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
				nProductLineItem.setPosition(""+locationProductItem.getLocation().getLocationId());
				nProductLineItem.setState(new ProductLineItemProductState(stateId));
				nProductLineItem.setStatus(new ProductLineItemStatus(1));
				
				dbProductLineItem.save(nProductLineItem);
				
			}else {
				nProductLineItem=new ProductLineItem();
				nProductLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
				nProductLineItem.setCreatedDate(new Date());
				nProductLineItem.setCustomerInventory(false);
				nProductLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				nProductLineItem.setModifiedDate(new Date());
				nProductLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
				nProductLineItem.setPosition(""+locationProductItem.getLocation().getLocationId());
				nProductLineItem.setState(new ProductLineItemProductState(stateId));
				nProductLineItem.setStatus(new ProductLineItemStatus(1));
				nProductLineItem.setProduct(locationProductItem.getProduct());
				nProductLineItem.setSerialNumber(locationProductLineItems.getItemSerialNumber());
				dbProductLineItem.save(nProductLineItem);
			}
			if(existingProductLineItem!=null)
				dbProductLineItem.delete(existingProductLineItem);
			if(existingLocationProductLineItems!=null)
				dbLocationProductLineItem.delete(existingLocationProductLineItems);
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

	@Override
	public Page<Map<String, Object>> getApprovedList(LoginUser user, Pageable page) {
		// TODO Auto-generated method stub
		return dhChangeRequest.findApprovedAequestBy("%"+getUserRole(user)+"%","%USER:"+user.getUsername()+"%",user.getUsername(),page);
	}
	
}
