package com.pratikshat.exicom.services.impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.UserProfile;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.BtPacking;
import com.pratikshat.exicom.repository.BtProductRequestItemRepository;
import com.pratikshat.exicom.repository.BtProductRequestRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtProductRequestService;
import com.pratikshat.exicom.utils.DateConverter;

@Service
public class BtProductRequestImpl implements BtProductRequestService {
	
	@Autowired
	BtProductRequestRepository btprRepo;
	
	@Autowired
	UserProfileRepository dbProfile;
	
	@Autowired
	ServiceRecourseRepository dbServiceResource;
	
	@Autowired
	BtProductRequestItemRepository dbProductRequestItem;
	
	
	

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductRequest.class, UserProfileRepository.VIEW_ALL)){
			return btprRepo.getById((Long)id);
		}
		else if(dbProfile.hasObjectAccess(user.getUsername(), BtProductRequest.class, UserProfileRepository.VIEW)){
			return btprRepo.getById((Long)id,dbProfile.getLocationAndChildLocation(user.getUsername()));
		}
		else
			throw new UnauthoriseException("You are not authorised");
	}


	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductRequest.class, UserProfileRepository.VIEW_ALL)){
			return btprRepo.getProductRequest(page);
		}
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductRequest.class, UserProfileRepository.VIEW)) {
			return btprRepo.getProductRequest(dbProfile.getLocationAndChildLocation(user.getUsername()), page);
		}
		throw new UnauthoriseException("You are not authorised") ;
	}


	@Override
	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductRequest.class, UserProfileRepository.CREATE)) {
			
			ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());
			
			Long destinationLocationId=Long.parseLong(request.get("destinationLocationId").toString());
			
			if(!destinationLocationId.equals(sr.getLocation().getLocationId()))
				throw new BadRequestException("Please provide a valid destination location");
			
			BtProductRequest pr=new BtProductRequest();
			pr.setBtProductRequestStatus("Draft");
			pr.setDescription(request.get("description")==null?"":request.get("description").toString());
			pr.setDestinationLocation(sr.getLocation());
			pr.setDummyStatus("Draft");
			
			System.out.println("Date Given by user" +request.get("needByTime"));
			
			pr.setNeedByDateTime(DateConverter.convertToDate(request.get("needByTime").toString()));
			pr.setOwner(new EmployeeDetails(user.getUsername()));
			pr.setOwnerDetails(new EmployeeDetails(user.getUsername()));
			pr.setSourceLocation(new Location(Long.parseLong(request.get("sourceLocationId").toString())));
			pr.setSynced(false);

			if(destinationLocationId.equals(pr.getSourceLocation().getLocationId()))
				throw new BadRequestException("Source and destination can not be same");
			
			btprRepo.saveDetails(pr, user);
			
			List<Map<String,Object>> items=request.get("items")!=null?(List<Map<String,Object>>)request.get("items"):null;
			if(items!=null&&items.size()>0) {
				for(Map<String,Object> item:items) {



					BtProductRequestItem prItem=new BtProductRequestItem();
					
					prItem.setBtProduct(new BtProducts(Long.parseLong(item.get("btProductId").toString())));
					prItem.setBtProductRequest(pr);
					prItem.setDescription(item.get("itemRequiredDescription")!=null? item.get("itemRequiredDescription").toString():"");
					prItem.setDisabledItem(false);
					prItem.setItemStatus(pr.getBtProductRequestStatus());
					prItem.setOwner(new EmployeeDetails(user.getUsername()));
					prItem.setQuantity(Float.parseFloat(item.get("requiredQty").toString()));
					prItem.setQuantityIssued(0);
					prItem.setQuantityReceived(0);
					prItem.setSynced(false);
					//dbProductRequestItem.saveDetails(prItem, user);
					
					dbProductRequestItem.addItem(prItem, user);
					btprRepo.addTotalRequiredQty(pr.getId(), prItem.getQuantity(), user);
				}
			}
			request.put("productRequestId", pr.getId());
			request.put("manualId", pr.getManualId());
			return request;
		}
		else
			throw new UnauthoriseException("You are not authorised");
		
	}


	@Override
	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		Optional<BtProductRequest> requestPrOptional=btprRepo.findById(Long.parseLong(request.get("productRequestId").toString()));
		if(!requestPrOptional.isPresent())
			throw new BadRequestException("Product request not found,Invalid request id");
		BtProductRequest requestPr=requestPrOptional.get();
		if(!requestPr.getBtProductRequestStatus().equalsIgnoreCase("Draft"))
			throw new BadRequestException("Can not edit request, Request is not in Draft mode");
		requestPr.setSourceLocation(new Location(Long.parseLong(request.get("sourceLocationId").toString())));
		if(request.get("needByTime")!=null)
			requestPr.setNeedByDateTime(DateConverter.convertToDate(request.get("needByTime").toString()));
		requestPr.setDescription(request.get("description").toString());
		btprRepo.updateDetails(requestPr, user);
		
		//return btprRepo.getById(requestPr.getId());
		return request;
	}


	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductRequest.class, UserProfileRepository.VIEW)||dbProfile.hasObjectAccess(user.getUsername(), BtProductRequest.class, UserProfileRepository.VIEW_ALL)){
			return btprRepo.searchProductRequest("%"+search+"%",dbProfile.getLocationAndChildLocation(user.getUsername()));
		}
		else
			throw new UnauthoriseException("You are not authorised");
	}


	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductRequest.class, UserProfileRepository.VIEW)||dbProfile.hasObjectAccess(user.getUsername(), BtProductRequest.class, UserProfileRepository.VIEW_ALL)) {
			return btprRepo.advanceSearch(searchRequest, dbProfile.getLocationAndChildLocation(user.getUsername()),user);
		}
		else
			throw new UnauthoriseException("You are not authorised");
		
	}


	@Override
	public Page<Map<String, Object>> getByFilterParam(String filterparam, Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		if(filterparam==null|| filterparam.equalsIgnoreCase(""))
			filterparam="All";
		System.out.println("Filter param "+filterparam);
		
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductRequest.class,UserProfileRepository.VIEW_ALL)){
			
			if(filterparam.equalsIgnoreCase("All")) 
				return btprRepo.getProductRequest(page);
			
			else if(filterparam.equalsIgnoreCase("Draft")) 
				return getPageByStatus(filterparam, page);
			
			else if(filterparam.equalsIgnoreCase("Open"))
				return getPageByStatus(filterparam, page);
			
			else if (filterparam.equalsIgnoreCase("Short_Closed")||filterparam.equalsIgnoreCase("Short Closed"))
				return getPageByStatus("Short Closed", page);
			
			else if (filterparam.equalsIgnoreCase("Partial_Received")||filterparam.equalsIgnoreCase("Partial Received"))
				return getPageByStatus("Partial Received", page);
			
			else if (filterparam.equalsIgnoreCase("Receipt_Pending")||filterparam.equalsIgnoreCase("Receipt Pending"))
				return getPageByStatus("Receipt Pending", page);
			
			else if (filterparam.equalsIgnoreCase("Received"))
				return getPageByStatus("Received", page);
			
			else if(filterparam.equalsIgnoreCase("Submitted"))
				return getPageByStatus(filterparam, page);
			
			else if(filterparam.equalsIgnoreCase("Send_To_Me")||filterparam.equalsIgnoreCase("Send To Me"))
				return btprRepo.getProductRequestSendToMe(dbServiceResource.getByEmail(user.getUsername()).getLocation().getLocationId(), page);
			
			else if(filterparam.equalsIgnoreCase("Send_By_Me")||filterparam.equalsIgnoreCase("Send By Me"))
				return btprRepo.getProductRequestSendByMe(dbServiceResource.getByEmail(user.getUsername()).getLocation().getLocationId(), page);
			
			else
				return btprRepo.getProductRequest(page);
		}
		else if(dbProfile.hasObjectAccess(user.getUsername(), BtProductRequest.class,UserProfileRepository.VIEW)) {
			
			List<Long> locationList=dbProfile.getLocationAndChildLocation(user.getUsername());
			
			if(filterparam.equalsIgnoreCase("All")) 
				return getPage(page, user);
			
			else if(filterparam.equalsIgnoreCase("Draft")) 
				return getPageByStatus(locationList, filterparam, page,user.getUsername());
			
			else if(filterparam.equalsIgnoreCase("Open"))
				return getPageByStatus(locationList,filterparam,page,user.getUsername());
			
			else if (filterparam.equalsIgnoreCase("Short_Closed")||filterparam.equalsIgnoreCase("Short Closed"))
				return getPageByStatus(locationList,"Short Closed",page,user.getUsername());
			
			else if (filterparam.equalsIgnoreCase("Partial_Received")||filterparam.equalsIgnoreCase("Partial Received"))
				return getPageByStatus(locationList,"Partial Received",page,user.getUsername());
			
			else if (filterparam.equalsIgnoreCase("Receipt_Pending")||filterparam.equalsIgnoreCase("Receipt Pending"))
				return getPageByStatus(locationList,"Receipt Pending",page,user.getUsername());
			
			else if (filterparam.equalsIgnoreCase("Received"))
				return getPageByStatus(locationList,"Received",page,user.getUsername());
			
			else if(filterparam.equalsIgnoreCase("Submitted"))
				return getPageByStatus(locationList,"Submitted",page,user.getUsername());
			
			else if(filterparam.equalsIgnoreCase("Send_To_Me")||filterparam.equalsIgnoreCase("Send To Me"))
				return btprRepo.getProductRequestSendToMe(dbServiceResource.getByEmail(user.getUsername()).getLocation().getLocationId(), page);
			
			else if(filterparam.equalsIgnoreCase("Send_By_Me")||filterparam.equalsIgnoreCase("Send By Me"))
				return btprRepo.getProductRequestSendByMe(dbServiceResource.getByEmail(user.getUsername()).getLocation().getLocationId(), page);
			else
				return getPage(page,user);
		}
		 
		else
			throw new UnauthoriseException("You are not authorised");
		
		
		
	}
	
	private Page<Map<String,Object>> getPageByStatus(List<Long> locationList,String status,Pageable page,String userName){
		List<String> statusList=new ArrayList<String>();
		statusList.add(status);
		return btprRepo.getProductRequest(locationList,statusList, page);
	}
	private Page<Map<String,Object>> getPageByStatus(String status,Pageable page){
		List<String> statusList=new ArrayList<String>();
		statusList.add(status);
		return btprRepo.getProductRequestByStatus(statusList, page);
	}


	// @Override
	// public void changeStatus(Long productRequestId, String status,LoginUser user) throws BadRequestException,UnauthoriseException {
	// 	btprRepo.changeRequestStatus(productRequestId, status, user);
	// }


	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	@Override
	public void changeStatus(Long productRequestId, LoginUser user, String status)
			throws BadRequestException, UnauthoriseException {
		
		if(status.equalsIgnoreCase("Submitted")||status.equalsIgnoreCase("Short Closed")||status.equalsIgnoreCase("Short_Closed"))
			{}
		else
				throw new BadRequestException("You can not change status other than Submit");
		
		Optional<BtProductRequest> optionProductRequst=btprRepo.findById(productRequestId);

		if(!optionProductRequst.isPresent())
				throw new BadRequestException("Product request not found");

		BtProductRequest request=optionProductRequst.get();
		
		if(request.getBtProductRequestStatus().equalsIgnoreCase("Short Closed")||request.getBtProductRequestStatus().equalsIgnoreCase("Short_Closed"))
				throw new BadRequestException("Can not change status of product request, Request is sort closed");
		ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());
		
		if(sr.getLocation().getLocationId()!=request.getDestinationLocation().getLocationId())
				throw new UnauthoriseException("You are not authorised");
		if(status.equalsIgnoreCase("Submitted")) {
			request.setSubmittedDate(new Date());
		}
		request.setDummyStatus(status);
		request.setBtProductRequestStatus(status);
		btprRepo.updateDetails(request, user);
		
		
		
		List<BtProductRequestItem> items=dbProductRequestItem.findByBtProductRequest(request);

		if(items!=null&&!items.isEmpty()){
			for(BtProductRequestItem item:items){
				item.setItemStatus(status);
				dbProductRequestItem.updateDetails(item, user);
			}
		}
	}


	@Override
	public Page<Map<String, Object>> getProductRequestBySourceLocation(Long locationId,Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductRequest.class, UserProfileRepository.VIEW_ALL))
				return btprRepo.getProductRequestSendToMe(locationId, page);
		else if (dbProfile.hasObjectAccess(user.getUsername(), BtProductRequest.class, UserProfileRepository.VIEW))
				return btprRepo.getProductRequestSendToMe(locationId,dbProfile.getLocationAndChildLocation(user.getUsername()), page);
		else
				throw new UnauthoriseException("You are not authorised");
	}


	@Override
	public Page<Map<String, Object>> getProductRequestByDestinationLocation(Long locationId,Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductRequest.class, UserProfileRepository.VIEW_ALL))
				return btprRepo.getProductRequestSendByMe(locationId, page);
		if(dbProfile.hasObjectAccess(user.getUsername(),BtProductRequest.class, UserProfileRepository.VIEW))
				return btprRepo.getProductRequestSendByMe(locationId, dbProfile.getLocationAndChildLocation(user.getUsername()), page);
		throw new UnauthoriseException("You are not authorised");
	}


	@Override
	public List<Map<String, Object>> advanceSearchByLocationId(Long locationId, Map<String, Object> request,
			Boolean toMe, LoginUser user) throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), BtProductRequest.class, UserProfileRepository.VIEW_ALL))
			return btprRepo.advanceSearchByLocationId(locationId, request, null, user, toMe);
		else if (dbProfile.hasObjectAccess(user.getUsername(), BtProductRequest.class, UserProfileRepository.VIEW))
			return btprRepo.advanceSearchByLocationId(locationId, request, dbProfile.getLocationAndChildLocation(user.getUsername()), user, toMe);
		else
			throw new UnauthoriseException("You are not authorised");
	}


	@Override
	public BtPacking getPackingList(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Map<String, Object>> getProductRequestSubmittedDate(Long productRequestId, LoginUser user) {
		
		return btprRepo.getProductRequestSubmittedDate(productRequestId);
	}



}
