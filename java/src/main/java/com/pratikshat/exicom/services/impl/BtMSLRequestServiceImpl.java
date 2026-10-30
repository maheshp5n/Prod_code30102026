package com.pratikshat.exicom.services.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.MSLItemRequestRequest;
import com.pratikshat.exicom.model.request.MSLRequestRequest;
import com.pratikshat.exicom.repository.BtMSLRequestItemRepository;
import com.pratikshat.exicom.repository.BtMSLRequestRepository;
import com.pratikshat.exicom.repository.BtMSLRequestRepositoryCustom;
import com.pratikshat.exicom.services.BtMSLRequestService;

@Service
public class BtMSLRequestServiceImpl implements BtMSLRequestService{

	@Autowired
	BtMSLRequestRepositoryCustom BtMSLRequestCustomDb;
	
	@Autowired
	BtMSLRequestRepository btMSLRequestDB;
	
	@Autowired
	BtMSLRequestItemRepository itemDB;
	
	@Autowired
    JdbcTemplate jdbc;
	
	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
	
		MSLRequestRequest btMslRequest=new MSLRequestRequest();
		MSLItemRequestRequest itemReq=new MSLItemRequestRequest();
		List<MSLItemRequestRequest> listItem=new ArrayList();
		List<Map<String, Object>> list=null;
		if(request.get("locationId")!=null &&  !request.get("locationId").toString().equalsIgnoreCase("") && !request.get("locationId").toString().equalsIgnoreCase("null") && Long.parseLong(request.get("locationId").toString())!=0 )
		{
			btMslRequest.setLocationId(Long.parseLong(request.get("locationId").toString()));
		}
		if(request.get("btMslRequestId")!=null &&  !request.get("btMslRequestId").toString().equalsIgnoreCase("") && !request.get("btMslRequestId").toString().equalsIgnoreCase("null") && Long.parseLong(request.get("btMslRequestId").toString())!=0 )
		{
			btMslRequest.setMslRequestId(Long.parseLong(request.get("StatusId").toString()));
		}
		if(request.get("StatusId")!=null &&  !request.get("StatusId").toString().equalsIgnoreCase("") && !request.get("StatusId").toString().equalsIgnoreCase("null") && Long.parseLong(request.get("StatusId").toString())!=0 )
		{
			btMslRequest.setStatusId(Long.parseLong(request.get("StatusId").toString()));
		}
		if(request.get("btItemRequest")!=null &&  !request.get("btItemRequest").toString().equalsIgnoreCase("") && !request.get("btItemRequest").toString().equalsIgnoreCase("null")  )
		{
			 list=(List<Map<String, Object>>) request.get("btItemRequest");
			 
			 for(Map<String,Object> map : list)
			 {
				 
				 if(map.get("btMslItmeRequestId")!=null &&  !map.get("btMslItmeRequestId").toString().equalsIgnoreCase("") && !map.get("btMslItmeRequestId").toString().equalsIgnoreCase("null") && Long.parseLong(map.get("btMslItmeRequestId").toString())!=0 )
					{
					 itemReq.setMslItmeRequestId(Long.parseLong(map.get("btMslItmeRequestId").toString()));
					} 
				 if(map.get("btMslRequestId")!=null &&  !map.get("btMslRequestId").toString().equalsIgnoreCase("") && !map.get("btMslRequestId").toString().equalsIgnoreCase("null") && Long.parseLong(map.get("btMslRequestId").toString())!=0 )
					{
					 itemReq.setMslRequestId(Long.parseLong(map.get("btMslRequestId").toString()));
					} 
				 if(map.get("btProductId")!=null &&  !map.get("btProductId").toString().equalsIgnoreCase("") && !map.get("btProductId").toString().equalsIgnoreCase("null") && Long.parseLong(map.get("btProductId").toString())!=0 )
					{
					 itemReq.setProductPartCode(map.get("btProductId").toString());
					} 
				 if(map.get("mslQty")!=null &&  !map.get("mslQty").toString().equalsIgnoreCase("") && !map.get("mslQty").toString().equalsIgnoreCase("null") && Float.parseFloat(map.get("mslQty").toString())!=0 )
					{
					 itemReq.setMslQty(Float.parseFloat(map.get("mslQty").toString()));
					} 
				 if(map.get("hslQty")!=null &&  !map.get("hslQty").toString().equalsIgnoreCase("") && !map.get("hslQty").toString().equalsIgnoreCase("null") && Float.parseFloat(map.get("hslQty").toString())!=0 )
					{
					 itemReq.setHslQty(Float.parseFloat(map.get("hslQty").toString()));
					} 
				 if(map.get("remarks")!=null &&  !map.get("remarks").toString().equalsIgnoreCase("") && !map.get("remarks").toString().equalsIgnoreCase("null")  )
					{
					 itemReq.setRemarks(map.get("remarks").toString());
					} 
				 
				 listItem.add(itemReq);
			 }
			 
			 
		}
	
		btMslRequest.setItemRequest(listItem); 
		
		MSLRequestRequest req=BtMSLRequestCustomDb.saveDetails(btMslRequest, user);
		
		return btMSLRequestDB.getById(req.getMslRequestId());
	}

	@Override
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public Page<Map<String, Object>> addItems(LoginUser user, Map<String, Object> map,Pageable page) throws BadRequestException,UnauthoriseException {

		MSLItemRequestRequest itemReq=new MSLItemRequestRequest();
		
		
		 if(map.get("btMslItmeRequestId")!=null &&  !map.get("btMslItmeRequestId").toString().equalsIgnoreCase("") && !map.get("btMslItmeRequestId").toString().equalsIgnoreCase("null") && Long.parseLong(map.get("btMslItmeRequestId").toString())!=0 )
			{
			 itemReq.setMslItmeRequestId(Long.parseLong(map.get("btMslItmeRequestId").toString()));
			} 
		 if(map.get("btMslRequestId")!=null &&  !map.get("btMslRequestId").toString().equalsIgnoreCase("") && !map.get("btMslRequestId").toString().equalsIgnoreCase("null") && Long.parseLong(map.get("btMslRequestId").toString())!=0 )
			{
			 itemReq.setMslRequestId(Long.parseLong(map.get("btMslRequestId").toString()));
			} 
		 if(map.get("btProductId")!=null &&  !map.get("btProductId").toString().equalsIgnoreCase("") && !map.get("btProductId").toString().equalsIgnoreCase("null") && Long.parseLong(map.get("btProductId").toString())!=0 )
			{
			 itemReq.setProductPartCode(map.get("btProductId").toString());
			} 
		 if(map.get("mslQty")!=null &&  !map.get("mslQty").toString().equalsIgnoreCase("") && !map.get("mslQty").toString().equalsIgnoreCase("null") && Float.parseFloat(map.get("mslQty").toString())!=0 )
			{
			 itemReq.setMslQty(Float.parseFloat(map.get("mslQty").toString()));
			} 
		 if(map.get("hslQty")!=null &&  !map.get("hslQty").toString().equalsIgnoreCase("") && !map.get("hslQty").toString().equalsIgnoreCase("null") && Float.parseFloat(map.get("hslQty").toString())!=0 )
			{
			 itemReq.setHslQty(Float.parseFloat(map.get("hslQty").toString()));
			} 
		 if(map.get("remarks")!=null &&  !map.get("remarks").toString().equalsIgnoreCase("") && !map.get("remarks").toString().equalsIgnoreCase("null")  )
			{
			 itemReq.setRemarks(map.get("remarks").toString());
			} 
		 BtMSLRequestCustomDb.addItems(user, itemReq);
		return btMSLRequestDB.getReleatedItemByRequestId(itemReq.getMslRequestId(), page);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public void submitForApproval(LoginUser user, long btMslrequestid, String remarks)
			throws BadRequestException, UnauthoriseException {
		
		BtMSLRequestCustomDb.submitForApproval(user, btMslrequestid, remarks);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchKeys, LoginUser user, List<Long> childLoc)
			throws BadRequestException,UnauthoriseException {
		// TODO Auto-generated method stub
		return BtMSLRequestCustomDb.advanceSearch(searchKeys, user, childLoc);
	}

}
