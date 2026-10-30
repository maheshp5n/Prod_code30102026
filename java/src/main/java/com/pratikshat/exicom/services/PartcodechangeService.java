package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface PartcodechangeService {
	public void approveDirect(List<Long> ids,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void updatePartcode(Long id,Map<String,Object> updateInfo,LoginUser user) throws BadRequestException,UnauthoriseException;
	public List<Map<String,Object>> getPendingToApprovedList(LoginUser user);
	public List<Map<String,Object>> getApprovedList(LoginUser user);
	public List<Map<String,Object>> searchPendingToApproveList(Map<String,Object> searchkey,LoginUser user);
	public List<Map<String,Object>> searchApprovedList(Map<String,Object>searchkey,LoginUser user);
	
	//NOT WORK FLOW 
	
	public List<Map<String,Object>> elegibleForChange(LoginUser user);
	public void createNonworkflowPartcodeChange(Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException;
	
	
	public Page<Map<String,Object>> getApprovedList(LoginUser user,Pageable page);
}
