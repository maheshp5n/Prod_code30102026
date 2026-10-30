package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface BtAssetsService extends GenricService {
	
	public Map<String,Object> searchForPartcode(long search,LoginUser user);
	
	public List<Map<String, Object>> searchForLists(String search,Pageable pageable)
			throws BadRequestException, UnauthoriseException ;

	public List<Map<String, Object>> searchForPartcodes(String productManualId, String assetManualId, LoginUser user);
	
	public Map<String, Object> getByAssetsSerialNumber(String id, LoginUser user)
			throws BadRequestException, UnauthoriseException;

}