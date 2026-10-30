package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ForgotPass {
	@Id
	String userEmailId;
	String otp;
	Date lastModifiedDate;
	boolean otpSend;
	boolean otpVerified;
	boolean mailSend;
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public boolean isOtpSend() {
		return otpSend;
	}
	public void setOtpSend(boolean otpSend) {
		this.otpSend = otpSend;
	}
	public boolean isOtpVerified() {
		return otpVerified;
	}
	public void setOtpVerified(boolean otpVerified) {
		this.otpVerified = otpVerified;
	}
	public boolean isMailSend() {
		return mailSend;
	}
	public void setMailSend(boolean mailSend) {
		this.mailSend = mailSend;
	}
	
	
	
}
