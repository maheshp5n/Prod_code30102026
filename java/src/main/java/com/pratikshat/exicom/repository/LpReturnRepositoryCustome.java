package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.LocationProductRequestResponse;
import com.pratikshat.exicom.model.response.LpReturnOrderResponse;

public interface LpReturnRepositoryCustome {
	com.pratikshat.exicom.model.request.LpReturnOrderReceiveRequest saveReturnOrderReceive(com.pratikshat.exicom.model.request.LpReturnOrderReceiveRequest receiveItem,com.pratikshat.exicom.entity.EmployeeDetails user)throws com.pratikshat.exicom.exception.BadRequestException,com.pratikshat.exicom.exception.UnauthoriseException;
	void addIitem(long returnOrderId,long productLineItemId,LoginUser user)throws BadRequestException,UnauthoriseException;
	void removeItem(long returnOrderLineItemId,LoginUser user) throws BadRequestException,UnauthoriseException;
	void submit(long returnOrderId,LoginUser user) throws BadRequestException,UnauthoriseException;
	
	public List<LpReturnOrderResponse> advanceSearch(Map<String,Object> searchKeys,LoginUser user,List<Long> childLoc)throws ParseException;
	
	public List<Map<String,Object>> getItemNativeAdvanceSearch(long returOrderId, Map<String,Object> request);
	
	public ByteArrayInputStream generatePackingListPdf(LoginUser user, long id, String type) throws Exception;
}
