package com.pratikshat.exicom.services;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.BtCaseResponse;
import com.pratikshat.exicom.model.response.BtPrcResponse;
import com.pratikshat.exicom.model.response.PrcResponse;

public interface BtPreRegistrationCasesService extends GenricService{
	
	public Map<String, Object> actionOnBtPreRegistrationCase(Map<String, Object> req , LoginUser user)throws UnauthoriseException, BadRequestException;
	
	Page<Map<String, Object>> getPrcBySiteId(long siteId, Pageable page) ;
	
	Map<String, Object> getCaseBtPrcId(long id);
	
	Page<BtPrcResponse> getAllCaseList(String action, Pageable page, LoginUser user) throws UnauthoriseException;
	public BtPrcResponse saveObjects(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException;
	public BtPrcResponse getByCaseId(LoginUser user, long caseid) throws BadRequestException, UnauthoriseException;

}

