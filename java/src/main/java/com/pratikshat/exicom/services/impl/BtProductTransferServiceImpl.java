package com.pratikshat.exicom.services.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtProductItem;
import com.pratikshat.exicom.entity.BtProductRequest;
import com.pratikshat.exicom.entity.BtProductRequestItem;
import com.pratikshat.exicom.entity.BtProductTransfer;
import com.pratikshat.exicom.entity.BtProductTransferItem;
import com.pratikshat.exicom.entity.BtProductTransferReceive;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceRecource;

import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.BtPacking;
import com.pratikshat.exicom.model.response.BtPackingItem;
import com.pratikshat.exicom.repository.BtProductItemRepository;
import com.pratikshat.exicom.repository.BtProductRequestItemRepository;
import com.pratikshat.exicom.repository.BtProductRequestRepository;
import com.pratikshat.exicom.repository.BtProductTransferItemRepository;
import com.pratikshat.exicom.repository.BtProductTransferReceiveRepository;
import com.pratikshat.exicom.repository.BtProductTransferRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.repository.custom.impl.BtProductTransferRepositoryCustomImpl;
import com.pratikshat.exicom.services.BtProductTransferService;

@Service
public class BtProductTransferServiceImpl implements BtProductTransferService {



	
	@Autowired
	BtProductTransferRepository dbRepo;
	
	@Autowired
	BtProductTransferItemRepository dbProductTransferItem;

	@Autowired
	BtProductRequestRepository dbProductRequest;
	
	@Autowired
	BtProductRequestItemRepository dbProductRequestItem;

	@Autowired
	ServiceRecourseRepository dbServiceResource;

	@Autowired
	BtProductItemRepository dbStock;
	
	@Autowired
	UserProfileRepository dbProfile;

	@Autowired
	BtProductTransferReceiveRepository dbProductTransferReceive;
	
	@Autowired
	BtProductTransferRepositoryCustomImpl customImpl; 



	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransfer.class, UserProfileRepository.VIEW_ALL))
			return dbRepo.getById((Long)id);
		else if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransfer.class, UserProfileRepository.VIEW))
			return dbRepo.getById((Long)id, dbProfile.getLocationAndChildLocation(user.getUsername()));
		else{
			throw new UnauthoriseException("You are not authorised");
		}
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransfer.class, UserProfileRepository.VIEW_ALL))
				return dbRepo.getPage(page);
		else if (dbProfile.hasObjectAccess(user.getUsername(), BtProductTransfer.class, UserProfileRepository.VIEW))
				return dbRepo.getPage(dbProfile.getLocationAndChildLocation(user.getUsername()), page);
		else
				throw new UnauthoriseException("You are not authorised");
	}

	@Override
	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
			
			if(request.get("destinationLocationId")==null)
				throw new BadRequestException("Please provide destination location");

			if(request.get("shippingToName")==null)
				throw new BadRequestException("Please provide shipping to ");

			if(request.get("trackingNumber")==null)
				throw new BadRequestException("Please provide tracking number");
			
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransfer.class, UserProfileRepository.CREATE)){

			ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());
			if(sr.getLocation().getLocationId()==Long.parseLong(request.get("destinationLocationId").toString()))
				throw new BadRequestException("Source location and destination location can not be same");

			BtProductTransfer tran=new BtProductTransfer();
			if(request.get("actualDeliveryDate")!=null)
				tran.setActualDeliveryDate(com.pratikshat.exicom.utils.DateConverter.convertToDate(request.get("actualDeliveryDate").toString()));
			tran.setDestinationLocation(new Location(Long.parseLong(request.get("destinationLocationId").toString())));
			tran.setOwner(new EmployeeDetails(user.getUsername()));
			tran.setShippingMode(request.get("shippingMode").toString());
			tran.setShippingToName(request.get("shippingToName").toString());
			tran.setSourceLocation(sr.getLocation());
			tran.setTrackingNumber(request.get("trackingNumber").toString());
			tran.setTransferStatus("Pending To Receive");
			dbRepo.saveDetails(tran, user);
			List<Map<String,Object>> items=(List<Map<String,Object>>) request.get("items");

			if(items!=null && !items.isEmpty()){
				for(Map<String,Object>item:items){

					BtProductRequestItem prouctRequestItem=dbProductRequestItem.findById(Long.parseLong(item.get("productRequestItemId").toString())).get();
					BtProductRequest productRequest=dbProductRequest.findById(prouctRequestItem.getBtProductRequest().getId()).get();
					BtProductTransferItem tranItem=new BtProductTransferItem();
					tranItem.setBtProductTransfer(tran);
					tranItem.setBtpRequestItem(prouctRequestItem);
					tranItem.setOwner(new EmployeeDetails(user.getUsername()));
					tranItem.setTransferQuantity(Long.parseLong(item.get("issueQuantity").toString()));
					tranItem.setSynced(false);
					
					dbProductTransferItem.saveDetails(tranItem, user);
					
					//ADDING TRANSFER QUANTITY TO PRODUCT REQUEST ITEM AND CROSSPONDING PRODUCT REQUEST AND TRANSFER

					dbProductRequestItem.addIssueQty(prouctRequestItem.getId(), tranItem.getTransferQuantity(), user);
					dbProductRequest.addTotalIssuedQty(productRequest.getId(), tranItem.getTransferQuantity(), user);
					dbRepo.addTransfredQty(tran.getId(), tranItem.getTransferQuantity(), user);

					dbStock.subQuantity(prouctRequestItem.getBtProduct(), sr.getLocation(), tranItem.getTransferQuantity(), false, 1, user);

					item.put("productTransferItemId", tranItem.getId());
					item.put("productTransferItemManualId", tranItem.getManualId());
				}
			}
			request.put("id", tran.getId());
			request.put("manualId", tran.getManualId());

			return request;
		}
		else
			throw new UnauthoriseException("You are not authorised");
		
	}

	@Override
	@Deprecated
	/**
	 * DO NOT USE THIS METHOD IT WILL NOT WORK AS YOU THINK, IT DO NOTHING
	 * 
	 */
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		return null;
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransfer.class, UserProfileRepository.VIEW_ALL))
				return dbRepo.searchProductTransferForList(search);
		else if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransfer.class, UserProfileRepository.VIEW))
				return dbRepo.searchProductTransferForList(dbProfile.getLocationAndChildLocation(user.getUsername()), search);
				
		return new ArrayList<>();
	}

//	@Override
//	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
//			throws BadRequestException, UnauthoriseException {
//		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransfer.class,UserProfileRepository.VIEW_ALL)){
//			return customImpl.advanceSearch(dbProfile.getLocationAndChildLocation(user.getUsername()), searchRequest, user);
//		}
//		else if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransfer.class, UserProfileRepository.VIEW))
//			return customImpl.advanceSearch(null, searchRequest, user);
//		else
//			throw new UnauthoriseException("You are not authorised");
//		
//	}
	
	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
	        throws BadRequestException, UnauthoriseException {

	    if (dbProfile.hasObjectAccess(user.getUsername(), BtProductTransfer.class, UserProfileRepository.VIEW_ALL)) {
	      
	        return dbRepo.advanceSearch(null, searchRequest, user);
	    }
	    else if (dbProfile.hasObjectAccess(user.getUsername(), BtProductTransfer.class, UserProfileRepository.VIEW)) {
	      
	        return dbRepo.advanceSearch(dbProfile.getLocationAndChildLocation(user.getUsername()), searchRequest, user);
	    }
	    else {
	        throw new UnauthoriseException("You are not authorised");
	    }
	}

	@Override
	public List<Map<String, Object>> getPendingLocationForProductTransfer(LoginUser user)
			throws BadRequestException, UnauthoriseException {
		ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());
		// System.out.println("Location id "+sr.getLocation().getLocationId());
		return dbRepo.getPndingLocationListForTransfer(sr.getLocation().getLocationId());
	}

	@Override
	public List<Map<String, Object>> getPendingItemToTransfer(Long destinationLocationId, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		ServiceRecource sr =dbServiceResource.getByEmail(user.getUsername());
		return dbRepo.getPendingProductRequestForTransfer(sr.getLocation().getLocationId(), destinationLocationId);
	}

	@Override
	public List<Map<String, Object>> getProductTransferByPrductRequestId(Long productRequestId, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransferItem.class, UserProfileRepository.VIEW_ALL))
			return dbRepo.getProductTransferByProductRequestId(productRequestId);
		else if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransferItem.class, UserProfileRepository.VIEW))
			return dbRepo.getProductTransferByProductRequestId(productRequestId, dbProfile.getLocationAndChildLocation(user.getUsername()));
		else
			throw new UnauthoriseException("You are not authorised");
	}

	@Override
	public List<Map<String, Object>> getProductTransferByProductRequestIdAndProductTransferItemId(Long productRequestId,
			Long productRequestItemId, LoginUser user) throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransferItem.class, UserProfileRepository.VIEW_ALL))
			return dbProductTransferItem.getProductTransferByProductRequestIdAndRequestItemId(productRequestId, productRequestItemId);
		else if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransferItem.class, UserProfileRepository.VIEW))
			return dbProductTransferItem.getProductTransferByProductRequestIdAndRequestItemId(productRequestId, dbProfile.getLocationAndChildLocation(user.getUsername()), productRequestItemId);
		else
			throw new UnauthoriseException("You are not authorised");
	}

	@Override
	public List<Map<String, Object>> getPendingTransferItemToReceive(LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransferItem.class, UserProfileRepository.VIEW_ALL)){

		}else if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransferItem.class, UserProfileRepository.VIEW)){

		}else{
			throw new UnauthoriseException("You are not authorised");
		}
		System.out.print("Checking fo executing");
		ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());

		return dbProductTransferItem.getPendingTransferItemToReceive(sr.getLocation().getLocationId());
	}

	@Override
	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	public void receiveTransferdItem(List<Map<String, Object>> lstItemReceived, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(!dbProfile.hasObjectAccess(user.getUsername(), BtProductTransferReceive.class, UserProfileRepository.CREATE))
			throw new UnauthoriseException("You are not authorised");
		
		ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());

		if(lstItemReceived!=null&&!lstItemReceived.isEmpty()){
			for(Map<String,Object> d:lstItemReceived){
				Long transferItemId=Long.parseLong(d.get("transferItemId").toString());
				
				Float qty=Float.parseFloat(d.get("quantity").toString());
				
				Optional<BtProductTransferItem> optionalTransferItem=dbProductTransferItem.findById(transferItemId);
				
				if(!optionalTransferItem.isPresent())
					throw new BadRequestException("Invalid product transfer id");
				
				
				BtProductTransferItem productTransferItem=optionalTransferItem.get();

				BtProductTransfer btProductTransfer=dbRepo.findById(productTransferItem.getBtProductTransfer().getId()).get();

				BtProductRequestItem productRequestItem=dbProductRequestItem.findById(productTransferItem.getBtRequestItem().getId()).get();

				BtProductRequest btProductRequest=dbProductRequest.findById(productRequestItem.getBtProductRequest().getId()).get();

				if(btProductRequest.getDestinationLocation().getLocationId()!=sr.getLocation().getLocationId())
					throw new UnauthoriseException("You are not authorised");
				
				BtProductItem stock=null;
				try {
					stock=dbStock.findByProductAndWarehouse(productRequestItem.getBtProduct(), sr.getLocation());
				} catch (NoResultException e) {
					
				} catch(NonUniqueResultException ex){
					throw new BadRequestException("Multiple stock record found for same product at same location");
				}
				if(stock==null){
					stock=new BtProductItem();
					
					stock.setCustomerTotalBerQuantity(0F);
					stock.setCustomerTotalFaultyQuantity(0F);
					stock.setCustomerTotalOkQuantity(0F);
					stock.setCustomerTotalQuantity(0F);
					stock.setCustomerTotalRejectedQuantity(0F);
					stock.setCustomerTotalScrapQuantity(0F);
					stock.setExicomTotalBerQuantity(0F);
					stock.setExicomTotalFaultyQuantity(0F);
					stock.setExicomTotalOkQuantity(0F);
					stock.setExicomTotalQuantity(0F);
					stock.setExicomTotalRejectedQuantity(0F);
					stock.setExicomTotalScrapQuantity(0F);
					stock.setOwner(new EmployeeDetails(user.getUsername()));
					stock.setProduct(productRequestItem.getBtProduct());
					stock.setWarehouse(sr.getLocation());
					
					dbStock.saveDetails(stock, user);
				}
				dbStock.addQuantity(stock, qty, false, 1, user);
				dbProductTransferItem.addTotalReceivedQty(productTransferItem.getId(), qty, user);
				dbRepo.addReceivedQty(btProductTransfer.getId(), qty, user);
				dbProductRequestItem.addReceivedQty(productRequestItem.getId(), qty, user);
				dbProductRequest.addTotalReceiedQty(btProductRequest.getId(), qty, user);

				Optional<BtProductTransferReceive> otpionalReceive=dbProductTransferReceive.findById(productTransferItem.getId());

				BtProductTransferReceive receive=null;

				if(otpionalReceive.isPresent()){
					receive=otpionalReceive.get();
					receive.setReceivedQuantity((receive.getReceivedQuantity()+qty));
					dbProductTransferReceive.updateDetails(receive, user);
				}
				else{
					
					receive=new BtProductTransferReceive();
					receive.setId(productRequestItem.getId());
					receive.setItem(productTransferItem);
					receive.setOwner(new EmployeeDetails(user.getUsername()));
					receive.setReceiveDate(new Date());
					receive.setReceivedQuantity(qty);
					dbProductTransferReceive.saveDetails(receive, user);
				}
			}
		}
	}

	@Override
	public List<Map<String, Object>> getAdvanceSearchByProductRequestId(Long productRequestId,
			Map<String, Object> request, LoginUser user) throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransferItem.class, UserProfileRepository.VIEW_ALL))
			return dbProductTransferItem.getAdvanceSearchByProductRequestId(productRequestId,null,request, user);
		else if (dbProfile.hasObjectAccess(user.getUsername(), BtProductTransferItem.class, UserProfileRepository.VIEW))
			return dbProductTransferItem.getAdvanceSearchByProductRequestId(productRequestId, dbProfile.getLocationAndChildLocation(user.getUsername()), request, user);
		else
			throw new UnauthoriseException("You are not authorised");
	}

	@Override
	public Page<Map<String, Object>> getProductTransferByPrductRequestId(Long productRequestId, LoginUser user,	Pageable page) throws BadRequestException, UnauthoriseException {
				
					if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransferItem.class, UserProfileRepository.VIEW_ALL))
						return dbRepo.getProductTransferByProductRequestId(productRequestId,page);
					else if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransferItem.class, UserProfileRepository.VIEW))
						return dbRepo.getProductTransferByProductRequestId(productRequestId, dbProfile.getLocationAndChildLocation(user.getUsername()),page);
					else
						throw new UnauthoriseException("You are not authorised");
	}

	@Override
	public BtPacking getPacking(Long id) {
		BtProductTransfer transfer=dbRepo.getTransferById(id);
		List<Map<String,Object>> items=dbProductTransferItem.getItems(id);

		BtPacking packing=new BtPacking();
		packing.setDestinationLegalEntity(transfer.getDestinationLocation().getLegalEntity().getLegalEntityName());
		packing.setDestinationWarehouseName(transfer.getDestinationLocation().getLocationName());
		packing.setRef(transfer.getManualId());
		packing.setRefType("PT Number");
		packing.setSourceLegalEntity(transfer.getSourceLocation().getLegalEntity().getLegalEntityName());
		packing.setSrouceWarehouseName(transfer.getSourceLocation().getLocationName());
		packing.setCreationDate(transfer.getCreatedDate());
		List<BtPackingItem> lstItems=new ArrayList<BtPackingItem>();
		if(items!=null&&!items.isEmpty()){
			for(Map<String,Object> obj:items){
				BtPackingItem _item=new BtPackingItem();
				_item.setDescription(MiscellaneousUtils.objToString(obj.get("productDescription")));
				_item.setPartCode(MiscellaneousUtils.objToString(obj.get("productPartcode")));
				_item.setProductStatus("OK");
				_item.setProductType(MiscellaneousUtils.objToString(obj.get("productType")));
				_item.setQuantity(Float.parseFloat(MiscellaneousUtils.objToString(obj.get("transferQuantity"))));
				lstItems.add(_item);
			}
		}
		packing.setItems(lstItems);
		
		return packing;
	}

	
	
}
