package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;



import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

import com.pratikshat.exicom.model.response.LocationResponse;

public interface LocationRepositoryCustome {
	com.pratikshat.exicom.model.request.LocationRequest saveLocation(com.pratikshat.exicom.model.request.LocationRequest location,LoginUser user, int businessDomainId)throws Exception;
	com.pratikshat.exicom.model.request.LocationRequest editLocation(com.pratikshat.exicom.model.request.LocationRequest location,LoginUser user, int businessDomainId)throws Exception;
	com.pratikshat.exicom.model.request.LPRequest saveProductRequest(com.pratikshat.exicom.model.request.LPRequest lprRequest,LoginUser user)throws com.pratikshat.exicom.exception.BadRequestException,com.pratikshat.exicom.exception.UnauthoriseException;
	com.pratikshat.exicom.model.request.LPReturnOrderRequest saveReturnOrder(com.pratikshat.exicom.model.request.LPReturnOrderRequest returnOrder,LoginUser user)throws com.pratikshat.exicom.exception.BadRequestException,com.pratikshat.exicom.exception.UnauthoriseException;
	void changeOwner(java.util.List<Long> locationIds,String ownerEmailId,com.pratikshat.exicom.entity.LoginUser user, int businessDomainId);
	List<Map<String,Object>> customeProductRequest(List<Map<String,Object>> request,LoginUser user)throws BadRequestException,UnauthoriseException;
	List<Map<String,Object>> resetMSL(List<Map<String,Object>> objects,LoginUser user)throws BadRequestException,UnauthoriseException;
	
	List<Map<String,Object>> searchMyInventory(Map<String,String> param, LoginUser user, int businessDomainId);
	
	void changeProductSerialNumberStatus(LoginUser user,long lineitemId,long stageid,String remarks) throws BadRequestException,UnauthoriseException;

	public List<LocationResponse> advanceSearch(Map<String,Object> searchKeys,LoginUser user,List<Long> childLoc, int businessDomainId)throws ParseException;
	
	public List<com.pratikshat.exicom.model.response.LocationResponse> getChildLocationAdvanceSearch(long locationid, Map<String,Object> request,LoginUser user, int businessDomainId) throws ParseException;
	
	public List<com.pratikshat.exicom.model.response.LocationProductItemResponse> getLocationProductItemsAdvanceSearch(long locationId, Map<String,Object> request, LoginUser user, int businessDomainId) throws ParseException;
	
	public List<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getSendProductRequestAdvanceSearch(long locationId, Map<String,Object> request, LoginUser user, int businessDomainId) throws ParseException;
	
	public List<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getReceivedProductRequestAdvanceSearch(long locationId, Map<String,Object> request, LoginUser user, int businessDomainId) throws ParseException;
	
	public void changeProductPartcodeAndState(long litemId,Map<String,Object> requestParam,LoginUser user) throws BadRequestException,UnauthoriseException;

}
