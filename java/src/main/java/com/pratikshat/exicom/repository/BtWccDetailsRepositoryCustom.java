package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtWccDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface BtWccDetailsRepositoryCustom extends GenericOperation<BtWccDetails>{
	
	BtWccDetails getBtWccDetailsWithID(long id) throws BadRequestException;

	public String submitforApproval(long wccId, LoginUser user) throws BadRequestException, UnauthoriseException;
	

}
