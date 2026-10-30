package com.pratikshat.exicom.model.request;

public class ChangeOwnerRequest {
	java.util.List<Long> ids;
	java.util.List<String> stringIds;
	String userEmailId;
	/**
	 * @return the ids
	 */
	public java.util.List<Long> getIds() {
		return ids;
	}
	/**
	 * @return the stringIds
	 */
	public java.util.List<String> getStringIds() {
		return stringIds;
	}
	/**
	 * @return the userEmailId
	 */
	public String getUserEmailId() {
		return userEmailId;
	}
	/**
	 * @param ids the ids to set
	 */
	public void setIds(java.util.List<Long> ids) {
		this.ids = ids;
	}
	/**
	 * @param stringIds the stringIds to set
	 */
	public void setStringIds(java.util.List<String> stringIds) {
		this.stringIds = stringIds;
	}
	/**
	 * @param userEmailId the userEmailId to set
	 */
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	
	
}
