package com.pratikshat.exicom.model.request;

public class ServiceResourseRequest {
	private long serviceResourceId;
	private String name;
	private String userEmailId;
	private long locationId;
	private int recourceTypeId;
	private long contactId;
	private boolean isActive;
	
	
	
	/**
	 * @return the serviceResourceId
	 */
	public long getServiceResourceId() {
		return serviceResourceId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the userEmailId
	 */
	public String getUserEmailId() {
		return userEmailId;
	}
	/**
	 * @return the locationId
	 */
	public long getLocationId() {
		return locationId;
	}
	/**
	 * @param serviceResourceId the serviceResourceId to set
	 */
	public void setServiceResourceId(long serviceResourceId) {
		this.serviceResourceId = serviceResourceId;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param userEmailId the userEmailId to set
	 */
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	public int getRecourceTypeId() {
		return recourceTypeId;
	}
	public void setRecourceTypeId(int recourceTypeId) {
		this.recourceTypeId = recourceTypeId;
	}
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	/**
	 * @return the contactId
	 */
	public long getContactId() {
		return contactId;
	}
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	
}
