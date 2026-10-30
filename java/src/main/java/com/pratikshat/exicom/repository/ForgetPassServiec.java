package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.exception.BadRequestException;

public interface ForgetPassServiec {
	void sendOtp(String userEmailId) throws BadRequestException;
	void setMailPass(String emailId,String otp) throws BadRequestException;
}
