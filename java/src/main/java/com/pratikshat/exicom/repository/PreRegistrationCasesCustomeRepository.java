package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.CaseRequest;
import com.pratikshat.exicom.model.request.CaseSearchRequest;
import com.pratikshat.exicom.model.request.PrcRequest;
import com.pratikshat.exicom.model.response.CaseResponse;
import com.pratikshat.exicom.model.response.PrcResponse;

public interface PreRegistrationCasesCustomeRepository {

	public long createPreRegistrationCases(PrcRequest request, LoginUser user)throws UnauthoriseException, BadRequestException;
	public long updatePreRegistrationCases(PrcRequest request, LoginUser user)throws UnauthoriseException, BadRequestException;
	public long actionOnPreRegistrationCase(long caseId, String action, String comment, LoginUser user)throws UnauthoriseException, BadRequestException;
	
	public List<PrcResponse> advanceSearch(Map<String,Object> searchKeys,List<String>userList)throws ParseException;
}
