package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.MSLRequestRequest;
import com.pratikshat.exicom.model.response.LocationProductRequestResponse;
import com.pratikshat.exicom.model.response.MSLRequestResponse;

public interface MSLRequestCustomRepository {
	MSLRequestRequest createRequest(LoginUser user,MSLRequestRequest mslRequest) throws Exception;
	MSLRequestRequest updateRequest(LoginUser user,MSLRequestRequest mslRequest) throws Exception;
	com.pratikshat.exicom.model.request.MSLItemRequestRequest addItems(LoginUser user,com.pratikshat.exicom.model.request.MSLItemRequestRequest items)throws Exception;
	void submitForApproval(LoginUser user,long mslrequestid,String remarks) throws com.pratikshat.exicom.exception.BadRequestException,com.pratikshat.exicom.exception.UnauthoriseException;

	public List<MSLRequestResponse> advanceSearch(Map<String,Object> searchKeys,LoginUser user,List<Long> childLoc)throws ParseException;
}
