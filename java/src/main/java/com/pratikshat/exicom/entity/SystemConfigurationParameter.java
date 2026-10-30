package com.pratikshat.exicom.entity;
@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class SystemConfigurationParameter implements java.io.Serializable,OldPresist<SystemConfigurationParameter>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	Long id;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails owner;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	String systemConfigParamName;
	String caseSmsBody;
	String smsurl;
	String user;
	String authKey;
	String sendor;
	String paramType;
	String objectName;
	String invokingStatus;
	boolean active;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public EmployeeDetails getOwner() {
		return owner;
	}
	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}
	public java.util.Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getSystemConfigParamName() {
		return systemConfigParamName;
	}
	public void setSystemConfigParamName(String systemConfigParamName) {
		this.systemConfigParamName = systemConfigParamName;
	}
	public String getCaseSmsBody() {
		return caseSmsBody;
	}
	public void setCaseSmsBody(String caseSmsBody) {
		this.caseSmsBody = caseSmsBody;
	}
	public String getSmsurl() {
		return smsurl;
	}
	public void setSmsurl(String smsurl) {
		this.smsurl = smsurl;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	public String getSendor() {
		return sendor;
	}
	public void setSendor(String sendor) {
		this.sendor = sendor;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getInvokingStatus() {
		return invokingStatus;
	}
	public void setInvokingStatus(String invokingStatus) {
		this.invokingStatus = invokingStatus;
	}
	
	

	@javax.persistence.Transient transient SystemConfigurationParameter old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(SystemConfigurationParameter)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public SystemConfigurationParameter gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
