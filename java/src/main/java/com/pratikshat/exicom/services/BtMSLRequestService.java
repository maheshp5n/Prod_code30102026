package com.pratikshat.exicom.services;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.MSLRequestResponse;

public interface BtMSLRequestService extends GenricService {

	Page<Map<String, Object>> addItems(LoginUser user,Map<String,Object> map,Pageable page) throws BadRequestException,UnauthoriseException;
	void submitForApproval(LoginUser user,long btMslrequestid,String remarks) throws BadRequestException,UnauthoriseException;

	public List<Map<String,Object>> advanceSearch(Map<String,Object> searchKeys,LoginUser user,List<Long> childLoc) throws BadRequestException,UnauthoriseException;
}
