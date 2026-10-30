package com.pratikshat.exicom.model.response;

public class UserForList {
	String userEmailId;
	String userFirstName;
	String userMiddleName;
	String userLastName;
	public UserForList(String userEmailId,String userFirstName,String userMiddleName,String userLastName) {
		this.userEmailId=userEmailId;
		this.userFirstName=userFirstName;
		this.userMiddleName=userMiddleName;
		this.userLastName=userLastName;
	}
	public UserForList() {}
	/**
	 * @return the userEmailId
	 */
	public String getUserEmailId() {
		return userEmailId;
	}
	/**
	 * @return the userFirstName
	 */
	public String getUserFirstName() {
		return userFirstName;
	}
	/**
	 * @return the userLastName
	 */
	public String getUserLastName() {
		return userLastName;
	}
	/**
	 * @param userEmailId the userEmailId to set
	 */
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	/**
	 * @param userFirstName the userFirstName to set
	 */
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	/**
	 * @param userLastName the userLastName to set
	 */
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public String getUserMiddleName() {
		return userMiddleName;
	}
	public void setUserMiddleName(String userMiddleName) {
		this.userMiddleName = userMiddleName;
	}
	
}
