package com.pratikshat.exicom.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Login {
	@NotNull(message="User email can not be null or blank or empty")
	@NotBlank(message="User email can not be null or blank or empty")
	@NotEmpty(message="User email can not be null or blank or empty")
	String userEmailId;
	@NotNull(message = "Password can not be null or blank or empty")
	@NotEmpty(message = "Password can not be null or blank or empty")
	@NotBlank(message = "Password can not be null or blank or empty")
	String userPassword;
	String fcmToken;
	String deviceId;
	
	/**
	 * @return the userEmailId
	 */
	public String getUserEmailId() {
		return userEmailId;
	}
	/**
	 * @param userEmailId the userEmailId to set
	 */
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}
	/**
	 * @param userPassword the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	/**
	 * @return the fcmToken
	 */
	public String getFcmToken() {
		return fcmToken;
	}
	/**
	 * @param fcmToken the fcmToken to set
	 */
	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}
	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}
	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
}
