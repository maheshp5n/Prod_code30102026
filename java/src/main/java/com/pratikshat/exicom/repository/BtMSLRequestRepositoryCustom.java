package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.MSLRequestRequest;
import com.pratikshat.exicom.model.response.MSLRequestResponse;
import com.pratikshat.exicom.services.GenricService;

public interface BtMSLRequestRepositoryCustom extends GenericOperation<MSLRequestRequest> {

	MSLRequestRequest createRequest(LoginUser user,MSLRequestRequest mslRequest) throws BadRequestException,UnauthoriseException;
	MSLRequestRequest updateRequest(LoginUser user,MSLRequestRequest mslRequest) throws BadRequestException,UnauthoriseException;
	com.pratikshat.exicom.model.request.MSLItemRequestRequest addItems(LoginUser user,com.pratikshat.exicom.model.request.MSLItemRequestRequest items)throws BadRequestException,UnauthoriseException;
	void submitForApproval(LoginUser user,long mslrequestid,String remarks) throws BadRequestException,UnauthoriseException;

	public List<Map<String, Object>> advanceSearch(Map<String,Object> searchKeys,LoginUser user,List<Long> childLoc) throws BadRequestException,UnauthoriseException;
}
