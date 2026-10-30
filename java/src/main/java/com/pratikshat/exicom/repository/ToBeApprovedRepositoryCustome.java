package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;

public interface ToBeApprovedRepositoryCustome {
	com.pratikshat.exicom.model.request.ApprovalRequest approveReject(com.pratikshat.exicom.model.request.ApprovalRequest appRequest,com.pratikshat.exicom.entity.EmployeeDetails emp,LoginUser user)throws com.pratikshat.exicom.exception.BadRequestException,com.pratikshat.exicom.exception.UnauthoriseException;
	EmployeeDetails getInitiator(String objectClassName,long objectid);
	List<Map<String,Object>> getAdvanceSearchResult(Map<String,Object> search,LoginUser user) throws BadRequestException;
}
