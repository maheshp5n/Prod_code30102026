package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;

import com.pratikshat.exicom.entity.BtFsr;
import com.pratikshat.exicom.entity.BtServiceFSR;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;

public interface BtServiceFSRRepositoryCustom extends GenericOperation<BtFsr>{
	
	BtFsr getFsrWithId(long id)throws BadRequestException;
	
	public ByteArrayInputStream generatePdf(LoginUser user, long id) throws Exception;

}
