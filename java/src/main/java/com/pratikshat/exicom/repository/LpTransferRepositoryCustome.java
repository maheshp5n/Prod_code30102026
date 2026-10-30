package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.response.LocationProductTransferItemResponse;
import com.pratikshat.exicom.model.response.LocationProductTransferLineItemResponse;
public interface LpTransferRepositoryCustome {
	
	public List<LocationProductTransferItemResponse> advanceSearch(Map<String,Object> searchKeys,LoginUser user,List<Long> childLoc)throws ParseException;
	
	public List<LocationProductTransferLineItemResponse> getLineItemsByTransferItemIdAdvanceSearch(long transferId, Map<String,Object> request)throws ParseException;

	public ByteArrayInputStream generatePackingListPdf(LoginUser user, long id, String type) throws Exception;
	
	public List<Map<String, Object>> productTransferAdvanceSearch(Map<String, Object> request, LoginUser user, List<Long> locationIds)throws ParseException;
  
}
