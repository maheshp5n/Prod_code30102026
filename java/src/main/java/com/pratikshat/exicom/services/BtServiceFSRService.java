package com.pratikshat.exicom.services;

import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BtServiceFSRRequest;
import com.pratikshat.exicom.model.request.BtServiceFSRUpdateRequest;

public interface BtServiceFSRService extends GenricService{

	String saveObject(BtServiceFSRRequest req, LoginUser user) throws BadRequestException, UnauthoriseException;
	
	String updateObject(BtServiceFSRUpdateRequest req, LoginUser user) throws BadRequestException, UnauthoriseException;
}
