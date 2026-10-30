package com.pratikshat.exicom.repository.custom;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pratikshat.exicom.entity.BtProductRequest;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;
import com.pratikshat.exicom.repository.GenericOperation;
import com.pratikshat.exicom.model.request.BtProductRequestItemRequest;
import com.pratikshat.exicom.model.request.BtProductTransferRequest;
import com.pratikshat.exicom.model.response.BtProductRequestResponse;

public interface BtProductRequestRepositoryCustome extends GenericOperation<BtProductRequest> {
 	public List<Map<String,Object>> advanceSearch(Map<String,Object> request,List<Long> locationIds,LoginUser users);
 	public void changeRequestStatus(Long productRequestId,String status,LoginUser user) throws BadRequestException,UnauthoriseException;
 	public void addTotalRequiredQty(Long productRequestId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
 	public void addTotalIssuedQty(Long productRequestId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
 	public void addTotalReceiedQty(Long productRequestId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
 	
 	public void subtractTotalRequiredQty(Long productRequestId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
 	public void subtractTotalIssuedQty(Long productRequestId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
 	public void subtractTotalReceiedQty(Long productRequestId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;

	public List<Map<String,Object>> advanceSearchByLocationId(Long locationId,Map<String,Object> request,List<Long> locationIds,LoginUser user, Boolean toMe) throws BadRequestException,UnauthoriseException;
  
}
