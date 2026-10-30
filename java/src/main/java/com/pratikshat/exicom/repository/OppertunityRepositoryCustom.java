package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.OppertunityLineItemRequest;
import com.pratikshat.exicom.model.request.OppertunityRequest;

public interface OppertunityRepositoryCustom {
	OppertunityRequest saveOppertunity(OppertunityRequest oppertunityRequest, LoginUser user) throws Exception;
	OppertunityRequest updateOppertunity(OppertunityRequest oppertunityRequest, LoginUser user) throws Exception;
	List<OppertunityLineItemRequest> addLineItem(List<OppertunityLineItemRequest> lineItemList, long priceBookId, long oppertunityId, int businessDomainId, LoginUser user) throws Exception;
	OppertunityLineItemRequest updateLineItem(OppertunityLineItemRequest lineItem, long priceBookId, long oppertunityId, int businessDomainId, LoginUser user) throws Exception;;

	List<OppertunityLineItemRequest> updateLineItemList(List<OppertunityLineItemRequest> lineItemList, long priceBookId, long oppertunityId, int businessDomainId, LoginUser user) throws Exception;
	
	void deleteOppertunity(long oppertunityId, LoginUser user) throws Exception;
	
	void deleteOppertunityLineItem(long oppertunityLineItemId, LoginUser user) throws Exception;
	
	List<com.pratikshat.exicom.model.response.OppertunityResponse> advanceSearch(Map<String,Object> request,LoginUser user) throws ParseException; 
}
