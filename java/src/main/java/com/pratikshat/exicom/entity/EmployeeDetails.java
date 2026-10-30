package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pratikshat.exicom.interceptor.ObjectAuditListener;
import com.pratikshat.exicom.model.response.ObjectAccessResponse;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class EmployeeDetails implements java.io.Serializable,OldPresist<EmployeeDetails>{
	@Id
	String emailId;
	String firstName;
	String middleName;
	String lastName;
	@ManyToOne(fetch=FetchType.EAGER)
	BusinessDomain emptype;
	String fcmToken;
	String deviceId;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	ContactDetails contact;
	
	@Transient
	java.util.Set<com.pratikshat.exicom.model.response.NavResponse> nav;
	@Transient
	String defaultPage;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails manager;
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails approvalManager;
	@JsonIgnore
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	UserProfile userProfile;
	
	Date createdDate;
	Date modifiedDate;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails modifiedBy;
	
	@Transient
	java.util.List<ObjectAccessResponse> objectAccess;
	
	@Transient
	Map<String,String> roleGroupAndAccount;
	@Transient
	Long locationId;
	
	@ManyToOne
	BusinessDomain businessDomain;
	
	public EmployeeDetails() {}
	
	
	
	public EmployeeDetails(String emailid) {
		this.emailId=emailid;
	}
	
	/**
	 * @return the contact
	 */
	public ContactDetails getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(ContactDetails contact) {
		this.contact = contact;
	}
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the nav
	 */
	public java.util.Set<com.pratikshat.exicom.model.response.NavResponse> getNav() {
		return nav;
	}
	/**
	 * @param nav the nav to set
	 */
	public void setNav(java.util.Set<com.pratikshat.exicom.model.response.NavResponse> nav) {
		this.nav = nav;
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
	/**
	 * @return the defaultPage
	 */
	public String getDefaultPage() {
		return defaultPage;
	}
	/**
	 * @param defaultPage the defaultPage to set
	 */
	public void setDefaultPage(String defaultPage) {
		this.defaultPage = defaultPage;
	}
	public BusinessDomain getEmptype() {
		return emptype;
	}
	public void setEmptype(BusinessDomain emptype) {
		this.emptype = emptype;
	}
	public EmployeeDetails getManager() {
		return manager;
	}
	public void setManager(EmployeeDetails manager) {
		this.manager = manager;
	}



	/**
	 * @return the approvalManager
	 */
	public EmployeeDetails getApprovalManager() {
		return approvalManager;
	}



	/**
	 * @param approvalManager the approvalManager to set
	 */
	public void setApprovalManager(EmployeeDetails approvalManager) {
		this.approvalManager = approvalManager;
	}


	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	
	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}



	public java.util.List<ObjectAccessResponse> getObjectAccess() {
		return objectAccess;
	}



	public void setObjectAccess(java.util.List<ObjectAccessResponse> objectAccess) {
		this.objectAccess = objectAccess;
	}



	public Date getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}



	public Date getModifiedDate() {
		return modifiedDate;
	}



	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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
	
	public String toString() {
		return this.emailId;
	}

	@Transient transient EmployeeDetails old;

	@Override
	@PostLoad
	public void old() {
		old=(EmployeeDetails)SerializationUtils.clone(this);
	}



	@Override
	public EmployeeDetails gOld() {
		// TODO Auto-generated method stub
		return old;
	}



	public Map<String, String> getRoleGroupAndAccount() {
		return roleGroupAndAccount;
	}



	public void setRoleGroupAndAccount(Map<String, String> roleGroupAndAccount) {
		this.roleGroupAndAccount = roleGroupAndAccount;
	}



	public Long getLocationId() {
		return locationId;
	}



	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}



	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}



	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}

	
	
	
}
