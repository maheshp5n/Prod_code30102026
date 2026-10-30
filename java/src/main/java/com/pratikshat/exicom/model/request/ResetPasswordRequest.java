package com.pratikshat.exicom.model.request;

public class ResetPasswordRequest {
	String userName;
	String newPassword;
	String confirmPassword;
	
	public ResetPasswordRequest() {}
	
	public ResetPasswordRequest(String userName,String newPassword,String confirmPassword) {
		this.userName=userName;
		this.newPassword=newPassword;
		this.confirmPassword=confirmPassword;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
}
