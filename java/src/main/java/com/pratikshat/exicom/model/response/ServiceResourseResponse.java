package com.pratikshat.exicom.model.response;

public class ServiceResourseResponse {
	private long serviceResourceId;
	private String name;
	private String userEmailId;
	private String userFirstName;
	private String userMiddleName;
	private String userLastName;
	private String resourceTypeName;
	private int resourceTypeId;
	private long locationId;
	private String locationName;
	private String createdByEmailId;
	private String createdByFirstName;
	private String createdByMiddleName;
	private String createdByLastName;
	private java.util.Date createdDate;
	private String modifiedByEmailId;
	private String modifiedByFirstName;
	private String modifiedByMiddleName;
	private String modifiedByLastName;
	private java.util.Date modifiedDate;
	private String contactFirstName;
	private String contactMiddleName;
	private String contactLastName;
	private String contactEmailId;
	private long contactId;
	private boolean isActive;
	private String employeeCode;
	
	public ServiceResourseResponse() {}
	public ServiceResourseResponse(com.pratikshat.exicom.entity.ServiceRecource resourse) {
		this.serviceResourceId=resourse.getServiceResourceId();
		this.name=resourse.getName();
		if(resourse.getUser()!=null) {
			this.userEmailId=resourse.getUser().getEmailId();
			this.userFirstName=resourse.getUser().getFirstName();
			this.userMiddleName=resourse.getUser().getMiddleName();
			this.userLastName=resourse.getUser().getLastName();
		}
		
		if(resourse.getLocation()!=null) {
			this.locationId=resourse.getLocation().getLocationId();
			this.locationName=resourse.getLocation().getLocationName();
		}
		if(resourse.getCreatedBy()!=null) {
			this.createdByEmailId=resourse.getCreatedBy().getEmailId();
			this.createdByFirstName=resourse.getCreatedBy().getFirstName();
			this.createdByMiddleName=resourse.getCreatedBy().getMiddleName();
			this.createdByLastName=resourse.getCreatedBy().getLastName();
			this.createdDate = resourse.getCreatedDate();
			
		}
		
		if(resourse.getModifiedBy()!=null) {
			this.modifiedByEmailId=resourse.getModifiedBy().getEmailId();
			this.modifiedByFirstName=resourse.getModifiedBy().getFirstName();
			this.modifiedByMiddleName=resourse.getModifiedBy().getMiddleName();
			this.modifiedByLastName=resourse.getModifiedBy().getLastName();
			this.modifiedDate=resourse.getModifiedDate();
		}
		
		if(resourse.getContact()!=null) {
			this.contactFirstName = resourse.getContact().getFirstName();
			this.contactMiddleName = resourse.getContact().getMiddleName();
			this.contactLastName = resourse.getContact().getLastName();
			this.contactEmailId = resourse.getContact().getEmailId();
			this.contactId = resourse.getContact().getContactId();
			this.employeeCode = resourse.getContact().getEmployeeCode();
		}
		
		if(resourse.getRecourceType()!=null) {
			this.resourceTypeId = resourse.getRecourceType().getResourceTypeId();
			this.resourceTypeName = resourse.getRecourceType().getResourceTypeName();
		}
		this.isActive = resourse.isActive();
	}
	
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
	 * @return the locationId
	 */
	public long getLocationId() {
		return locationId;
	}
	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}
	/**
	 * @return the createdByEmailId
	 */
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	/**
	 * @return the createdByFirstName
	 */
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	/**
	 * @return the createdByLastName
	 */
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	/**
	 * @return the createdDate
	 */
	public java.util.Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the modifiedByEmailId
	 */
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}
	/**
	 * @return the modifiedByFirstName
	 */
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	/**
	 * @return the modifiedByLastName
	 */
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	/**
	 * @return the modifiedDate
	 */
	public java.util.Date getModifiedDate() {
		return modifiedDate;
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
	
	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	/**
	 * @param createdByEmailId the createdByEmailId to set
	 */
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	/**
	 * @param createdByFirstName the createdByFirstName to set
	 */
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	/**
	 * @param createdByLastName the createdByLastName to set
	 */
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param modifiedByEmailId the modifiedByEmailId to set
	 */
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	/**
	 * @param modifiedByFirstName the modifiedByFirstName to set
	 */
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	/**
	 * @param modifiedByLastName the modifiedByLastName to set
	 */
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getResourceTypeName() {
		return resourceTypeName;
	}
	public void setResourceTypeName(String resourceTypeName) {
		this.resourceTypeName = resourceTypeName;
	}
	public int getResourceTypeId() {
		return resourceTypeId;
	}
	public void setResourceTypeId(int resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}
	public String getContactFirstName() {
		return contactFirstName;
	}
	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}
	public String getContactLastName() {
		return contactLastName;
	}
	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}
	public String getContactEmailId() {
		return contactEmailId;
	}
	public void setContactEmailId(String contactEmailId) {
		this.contactEmailId = contactEmailId;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public long getContactId() {
		return contactId;
	}
	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getUserMiddleName() {
		return userMiddleName;
	}
	public void setUserMiddleName(String userMiddleName) {
		this.userMiddleName = userMiddleName;
	}
	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}
	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}
	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}
	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}
	public String getContactMiddleName() {
		return contactMiddleName;
	}
	public void setContactMiddleName(String contactMiddleName) {
		this.contactMiddleName = contactMiddleName;
	}
	
	
	
}
