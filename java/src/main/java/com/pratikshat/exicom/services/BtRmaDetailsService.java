package com.pratikshat.exicom.services;

import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.entity.BtRmaRejectLog;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ChangeStatusRmaRequest;
import com.pratikshat.exicom.model.request.RejectRmaRequest;
import com.pratikshat.exicom.model.response.BtRmaResponse;

public interface BtRmaDetailsService extends GenricService {
	public Page<Map<String, Object>> getAll(LoginUser user,Pageable page,String filtername) throws BadRequestException, UnauthoriseException;

	public  BtRmaRejectLog rejectRMAById(Map<String, Object> rejectRmaRequest, MultipartFile file, LoginUser user) throws BadRequestException, UnauthoriseException ;

	public RejectRmaRequest rejectRma(RejectRmaRequest rejectRmaRequest, LoginUser user) throws BadRequestException, UnauthoriseException;

	public BtRmaResponse updateStatus(Map<String, Object> changeStatusRequest, MultipartFile file, LoginUser user) throws BadRequestException, UnauthoriseException;

	public ChangeStatusRmaRequest changeStatusRma(ChangeStatusRmaRequest changeStatusRmaRequest, LoginUser user) throws BadRequestException, UnauthoriseException;


}
