package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.WccDetailsRequest;

public interface BtWccDetailsService extends GenricService{
	
	public Map<String, Object> saveObject(WccDetailsRequest _request, LoginUser user) throws BadRequestException, UnauthoriseException;
	public List<Map<String, Object>> advanceSearchs(Map<String, Object> request, List<String> userLists)
			throws BadRequestException, UnauthoriseException;
	List<Map<String,Object>> getPendingToInvoiceWccByUser(String userEmailId);
	public Map<String, Object> submitForapproval(long wccId, LoginUser user) throws BadRequestException, UnauthoriseException;
}