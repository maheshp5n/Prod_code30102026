package com.pratikshat.exicom.services;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface WebviewLoginService {
	String generateToken(LoginUser user,String usertoken,String callbackurl);
	EmployeeDetails getRoute(String usertoken,String hash,String callbackurl) throws BadRequestException,UnauthoriseException;
}
