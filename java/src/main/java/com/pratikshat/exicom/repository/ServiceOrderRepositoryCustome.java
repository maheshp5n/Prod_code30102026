package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.LocationProductRequestResponse;

public interface ServiceOrderRepositoryCustome {
	public void createServiceOrder(Map<String,Object> request,LoginUser user)throws BadRequestException,UnauthoriseException;
	public void receiveServiceOrder(List<Long> ids,LoginUser user) throws BadRequestException,UnauthoriseException;
	
	public List<Map<String,Object>> advanceSearch(Map<String,Object> searchKeys,LoginUser user,List<Long> childLoc)throws ParseException;
	
	public List<Map<String,Object>> getNotOkeItemsAdvanceSearch(Map<String,Object> searchKey,LoginUser user)throws ParseException;
	
	public List<Map<String,Object>> getServiceOrderItemsAdvanceSearch(long id, @RequestParam Map<String,Object> request)throws ParseException;
	
	public ByteArrayInputStream generatePackingListPdf(LoginUser user, long id, String type) throws Exception;
}
