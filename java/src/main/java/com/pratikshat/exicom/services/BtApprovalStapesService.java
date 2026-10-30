package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface BtApprovalStapesService extends GenricService {
	public List<Map<String,Object>> getApprovalStapesByApprovalId(Long approvalId,LoginUser user) throws BadRequestException,UnauthoriseException;
	public Map<String,Object> getApprovalStateByApprovalIdAndStateId(Long approvalId,Long approvalStapeId,LoginUser user)  throws BadRequestException,UnauthoriseException;
}
