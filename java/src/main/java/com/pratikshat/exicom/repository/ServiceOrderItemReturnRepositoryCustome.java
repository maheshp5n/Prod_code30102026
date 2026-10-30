package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ServiceOrderReturnRequest;

public interface ServiceOrderItemReturnRepositoryCustome {
	public void createServiceOrderReturn(ServiceOrderReturnRequest request,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void receiveServiceOrderReturn(Set<Long> ids,LoginUser user) throws BadRequestException,UnauthoriseException;
	
	public List<Map<String, Object>> advanceSearchSOR(Map<String,Object> request, LoginUser user, List<Long> childLocations) throws ParseException;
	
	public List<Map<String, Object>> advanceSearchSORItems(long serviceOrderReturnId, Map<String,Object> request) throws ParseException;
	
	public ByteArrayInputStream generatePackingListPdf(LoginUser user, long id, String type) throws Exception;
}
