package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;


public interface BtTobeApprovedService extends GenricService {
	Page<Map<String,Object>> getPendingPage(Pageable page,LoginUser user);
	public List<Map<String,Object>> getApprovalHistory(String objectName,Long objectId) throws BadRequestException,UnauthoriseException;
	Map<String, Object> getToBeApprovedBasedOnIdAndObjectName(long objectId, String objectName, LoginUser user);
	Map<String, Object> updateObject(Map<String, Object> request, LoginUser user) throws BadRequestException, UnauthoriseException;
	//Map<String, Object> updateMultipleObjects(List<Long> ids, String actionType, String remark, LoginUser user);
	

}
