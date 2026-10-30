package com.pratikshat.exicom.services;

import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BtCommissioningFSRRequest;
import com.pratikshat.exicom.model.request.BtCommissioningFSRUpdateRequest;
import com.pratikshat.exicom.model.response.BtCommissioningFSRResponse;


public interface BtCommissioningFSRService extends GenricService{

	BtCommissioningFSRResponse saveObject(BtCommissioningFSRRequest req, LoginUser user) throws BadRequestException, UnauthoriseException;
	
	Map<String, Object> updateObject(BtCommissioningFSRUpdateRequest req, LoginUser user) throws BadRequestException, UnauthoriseException;

}
