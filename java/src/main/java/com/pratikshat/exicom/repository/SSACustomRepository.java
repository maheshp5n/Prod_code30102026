package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.SSARequest;
import com.pratikshat.exicom.model.response.SSAResponse;

public interface SSACustomRepository {

	SSARequest saveSSA(SSARequest request, LoginUser user) throws Exception;
	SSARequest updateSSA(SSARequest request, LoginUser user) throws Exception;
	
	public List<SSAResponse> advanceSearch(Map<String,Object> searchKeys,LoginUser user)throws ParseException;
}
