package com.pratikshat.exicom.services;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface BtServiceAppointmentProductConsumedService extends GenricService{
	
	void deleteProduct(long id,LoginUser user)throws BadRequestException,UnauthoriseException;


}
