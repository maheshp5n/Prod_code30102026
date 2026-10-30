package com.pratikshat.exicom.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.UnhandledException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BtProductRequest;
import com.pratikshat.exicom.entity.BtProductRequestItem;
import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtProductRequestItemRepository;
import com.pratikshat.exicom.repository.BtProductRequestRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtProductRequestItemService;

@Service
public class BtProductRequestItemServiceImpl implements BtProductRequestItemService {

	@Autowired BtProductRequestItemRepository dbProductRequestItem;
	@Autowired UserProfileRepository dbUserProfile;
	@Autowired BtProductRequestRepository dbProductRequest;
	
	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		if(dbUserProfile.hasObjectAccess(user.getUsername(), BtProductRequestItem.class, UserProfileRepository.VIEW_ALL)) {
			return dbProductRequestItem.getById((Long)id);	
		}
		else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtProductRequestItem.class, UserProfileRepository.VIEW))
			return dbProductRequestItem.getById((Long)id,dbUserProfile.getLocationAndChildLocation(user.getUsername()));
		else
			throw new UnauthoriseException("You are not autorised");
	}

	@Override
	@Deprecated
	/*
	 * Generic operations method does not return valid value, do not use.
	 */
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(!dbUserProfile.hasObjectAccess(user.getUsername(), BtProductRequestItem.class, UserProfileRepository.CREATE))
			throw new UnauthoriseException("You are not authorised");
		
		BtProductRequest mainRequest=dbProductRequest.findById(Long.parseLong(request.get("productRequestId").toString())).get();
		if(mainRequest!=null) {
			BtProductRequestItem item=new BtProductRequestItem();
			item.setBtProduct(new BtProducts(Long.parseLong(request.get("btProductId").toString())));
			item.setBtProductRequest(mainRequest);
			item.setDescription(request.get("itemRequiredDescription")!=null?request.get("itemRequiredDescription").toString():"");
			item.setDisabledItem(false);
			item.setItemStatus(mainRequest.getBtProductRequestStatus());
			item.setOwner(new EmployeeDetails(user.getUsername()));
			item.setQuantity(Float.parseFloat(request.get("requiredQty").toString()));
			item.setQuantityIssued(0);
			item.setQuantityReceived(0);
			item.setSynced(false);
			
			dbProductRequestItem.addItem(item, user);
			dbProductRequest.addTotalRequiredQty(mainRequest.getId(), item.getQuantity(), user);
			
			request.put("productRequestItemId", item.getId());
			request.put("productRequestItemManualId",item.getManualId());
		}
		else
			throw new BadRequestException("Product request not found, Invalid id");
		
		return request;
	}

	@Deprecated
	@Override
	/*
	 * Not to use
	 * */
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	/*
	 * Not to use
	 * */
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	/*
	 * Not to use
	 * */
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Map<String, Object>> getPage(Long productRequestId, Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(dbUserProfile.hasObjectAccess(user.getUsername(), BtProductRequestItem.class, UserProfileRepository.VIEW_ALL)) 
			return dbProductRequestItem.getPageBy(productRequestId, page);
		
		else if (dbUserProfile.hasObjectAccess(user.getUsername(),BtProductRequestItem.class, UserProfileRepository.VIEW)) 
			return dbProductRequestItem.getPageBy(productRequestId, dbUserProfile.getLocationAndChildLocation(user.getUsername()), page);
		
		else
			throw new UnauthoriseException("You are not authorised");
		
	}

	@Override
	public List<Map<String, Object>> getList(Long productRequestId, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(dbUserProfile.hasObjectAccess(user.getUsername(), BtProductRequestItem.class, UserProfileRepository.VIEW_ALL)) 
			return dbProductRequestItem.getListBy(productRequestId);
		
		else if (dbUserProfile.hasObjectAccess(user.getUsername(),BtProductRequestItem.class, UserProfileRepository.VIEW)) 
			return dbProductRequestItem.getListBy(productRequestId, dbUserProfile.getLocationAndChildLocation(user.getUsername()));
		
		else
			throw new UnauthoriseException("You are not authorised");
		
	}

	@Override
	public List<Map<String, Object>> searchForList(Long productRequestId, String searchKey, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return dbProductRequestItem.getSearchList(productRequestId, searchKey);
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Long productRequestId, Map<String, Object> search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return dbProductRequestItem.advanceSearch(productRequestId, search, user);
	}

	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	@Override
	public void updateStatus(Long productRequestItemId, String status, LoginUser user) throws BadRequestException,UnauthoriseException {
		if(status.equalsIgnoreCase("Short Closed")||status.equalsIgnoreCase("Short_Closed")){}
		else
			throw new BadRequestException("You can not change status other then Sort close");
		Optional<BtProductRequestItem> optionalRequestItem=dbProductRequestItem.findById(productRequestItemId);
		if(!optionalRequestItem.isPresent())
			throw new BadRequestException("Product request item not found, Invalid product request item id");
		BtProductRequestItem item=optionalRequestItem.get();

		BtProductRequest request=dbProductRequest.findById(item.getBtProductRequest().getId()).get();

		if(request.getBtProductRequestStatus().equalsIgnoreCase("Short Closed")||request.getBtProductRequestStatus().equalsIgnoreCase("Short_Closed"))
			throw new BadRequestException("Associated product request is sort closed, Can not change status of item");

		if(request.getTotalQuantityRequired()<=request.getTotalQuantityIssued())
			throw new BadRequestException("Associated product request is closed, Can not change status of item");

		if(item.getItemStatus().equalsIgnoreCase("Short Closed")||item.getItemStatus().equalsIgnoreCase("Short_Closed"))
			throw new BadRequestException("Item is already sort closed");

		item.setItemStatus(status);

		dbProductRequestItem.updateDetails(item, user);

		dbProductRequest.subtractTotalRequiredQty(request.getId(), (item.getQuantity()-item.getQuantityIssued()), user);

	}

	@Override
	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	public void deleteProductItem(Long productRequestItemId, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		Optional<BtProductRequestItem> optionalItem=dbProductRequestItem.findById(productRequestItemId);
		if(!optionalItem.isPresent())
				throw new BadRequestException("Item not found");

		BtProductRequestItem item=optionalItem.get();
		
		BtProductRequest request=dbProductRequest.findById(item.getBtProductRequest().getId()).get();

		if(!request.getBtProductRequestStatus().equalsIgnoreCase("Draft"))
				throw new BadRequestException("Can not delete item, Product request is not in draft mode");
		dbProductRequest.subtractTotalRequiredQty(request.getId(), item.getQuantity(), user);

		dbProductRequestItem.delete(item);
		
	}

}
