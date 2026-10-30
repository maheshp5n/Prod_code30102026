package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.WccDetailsRequest;
import com.pratikshat.exicom.model.response.PrcResponse;

public interface WccDetailsRepositoryCustome {
	public void saveWcc(WccDetailsRequest request,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void updateWcc(WccDetailsRequest request,LoginUser uesr) throws BadRequestException,UnauthoriseException;
	public void submitForapproval(Long wccId,LoginUser user) throws BadRequestException,UnauthoriseException;
	
	public List<Map<String,Object>> advanceSearch(Map<String,Object> searchKeys,List<String>userList)throws ParseException;
}
