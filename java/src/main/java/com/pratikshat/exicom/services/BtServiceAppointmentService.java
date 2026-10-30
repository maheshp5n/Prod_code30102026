package com.pratikshat.exicom.services;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.BtSchOnlyResponseCopy;
import com.pratikshat.exicom.model.response.BtServiceAppointmentResponse;


public interface BtServiceAppointmentService extends GenricService {
	public Map<String,Object> reAssignBtServiceAppointment(Map<String,Object> request, LoginUser user) throws BadRequestException,UnauthoriseException;
	
	public void requestForAnotherServiceAppointment(long caseId, String remark, LoginUser user) throws BadRequestException,UnauthoriseException ;
	public void serviceAppointmentClaimApproval(ServiceAppointment serviceAppointment, BusinessDomain businessDomain, LoginUser user) throws BadRequestException;
	Page<Map<String, Object>> getPage(LoginUser user,Pageable page,String filtername) throws BadRequestException, UnauthoriseException;
	public Page<Map<String, Object>> getServiceAppointmentListByClaimStatus(String claimstatus,Pageable page,LoginUser user);
	public Page<Map<String, Object>> getServiceAppointmentListByStatus(String status,Pageable page,LoginUser user);

	public BtServiceAppointmentResponse getByServiceAppId(long id, LoginUser user);
	
	public Page<BtSchOnlyResponseCopy> getSchDetailsAppId(long id, LoginUser user,Pageable pageable);
	
	public boolean verifyCode(long id,String vfCode);
}