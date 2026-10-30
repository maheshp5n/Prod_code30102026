package com.pratikshat.exicom.services;


import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.MrnDetails;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.MrnRequest;


public interface MrnService{
	
	public void saveMrn(MrnRequest mrn,LoginUser user) throws BadRequestException,UnauthoriseException;
}
