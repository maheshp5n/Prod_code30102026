package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;
import com.pratikshat.exicom.model.response.LocationProductRequestResponse;

public interface LocationProductRequestRepositoryCustome {
	public void submitProductRequest(long prdId,com.pratikshat.exicom.entity.LoginUser user) throws Exception;
	public com.pratikshat.exicom.model.request.LPItemRequest addProductItem(com.pratikshat.exicom.model.request.LPItemRequest itemRequest,com.pratikshat.exicom.entity.LoginUser user) throws Exception;
	public com.pratikshat.exicom.model.response.OkItemResponse getOkItems(long prid,long itemid);
	public com.pratikshat.exicom.model.request.LPTransferRequest transferProducts(com.pratikshat.exicom.model.request.LPTransferRequest tRequest,com.pratikshat.exicom.entity.LoginUser user) throws com.pratikshat.exicom.exception.BadRequestException,com.pratikshat.exicom.exception.UnauthoriseException;
	public void receiveTransfredItem(long productRequestId,java.util.List<com.pratikshat.exicom.model.request.LocationProductTransferReceiveRequest> items,com.pratikshat.exicom.entity.EmployeeDetails user) throws com.pratikshat.exicom.exception.BadRequestException,com.pratikshat.exicom.exception.UnauthoriseException;
	public void removeProductRequestItem(long id,LoginUser user)throws BadRequestException,UnauthoriseException;
	
	public void updateSourceLocation(long productRequestId,long locationId,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void updateProductPartCode(long productRequestItemId,String nPartCode,LoginUser user) throws BadRequestException,UnauthoriseException;
	
	public void submitProductRequestToSAP(long prdId,com.pratikshat.exicom.entity.LoginUser user) throws Exception;
	public void disableProductLineItems(long productRequestLineItemId,boolean flag,LoginUser user) throws BadRequestException,UnauthoriseException;

	public List<LocationProductRequestResponse> advanceSearch(Map<String,Object> searchKeys,LoginUser user,List<Long> childLoc)throws ParseException;
	
	public List<com.pratikshat.exicom.model.response.LocationProductRequestItemResponse> getProductRequestItemsAdvanceSearch(long productrequestid, Map<String,Object> request)throws ParseException;
	
	public List<com.pratikshat.exicom.model.response.LocationProductTransferItemResponse> getLocationProductTransferByProductAdvanceSearch(long productrequestid, Map<String,Object> request)throws ParseException;
	void shortCloseProductRequest(long prdId, LoginUser user) throws Exception;
	void shortCloseProductRequestItems(long prdId, LoginUser user, long productRequestLineItemId) throws BadRequestException,UnauthoriseException;
}
