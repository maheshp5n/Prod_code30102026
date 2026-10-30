package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class ContactDetails implements java.io.Serializable,OldPresist<ContactDetails>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long contactId;
	String title;
	String salutation;
	String firstName;
	String middleName;
	String department;
	@NotNull
	@NotEmpty
	@NotBlank
	String lastName;
	String suffix;
	@NotNull
	@ManyToOne
	@JoinColumn(name="account_id")
	Accounts account;
	String emailId;
	String fax;
	String phone;
	@Column(length = 13)
	String mobile;
	@ManyToOne
	@JoinColumn(name="address_id")
	AddressInformation addressInfo;
	@ManyToOne
	@JoinColumn(name="contact_owner_id")
	EmployeeDetails contactOwner;
	Date createdOn;
	Date lastModifiedOn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="created_by")
	EmployeeDetails createdBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="modified_by")
	EmployeeDetails modifiedBy;
	
	String employeeCode;
	@ManyToOne(fetch = FetchType.LAZY)
	ContactType contactType;
	@ManyToOne(fetch = FetchType.LAZY)
	ContactDesignation contactDesignation;
	@ManyToOne
	@JoinColumn(name="grade_name")
	BoardingLoadingDetails grade;
	@ManyToOne
	@JoinColumn(name="legal_entity_id")
	LegalEntity legalEntity;
	
	@ManyToOne
	ExpenseManagementBusinessPlace businessPlace;
	
	@ManyToOne
	BusinessDomain businessDomain;
	
	boolean synced;
	public ContactDetails() {}
	public ContactDetails(long contactId) {
		this.contactId=contactId;
	}
	/**
	 * @return the synced
	 */
	public boolean isSynced() {
		return synced;
	}
	/**
	 * @param synced the synced to set
	 */
	public void setSynced(boolean synced) {
		this.synced = synced;
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
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the salutation
	 */
	public String getSalutation() {
		return salutation;
	}
	/**
	 * @param salutation the salutation to set
	 */
	public void setSalutation(String salutation) {
		this.salutation = salutation;
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
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
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
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}
	/**
	 * @param suffix the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	/**
	 * @return the account
	 */
	public Accounts getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(Accounts account) {
		this.account = account;
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
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the addressInfo
	 */
	public AddressInformation getAddressInfo() {
		return addressInfo;
	}
	/**
	 * @param addressInfo the addressInfo to set
	 */
	public void setAddressInfo(AddressInformation addressInfo) {
		this.addressInfo = addressInfo;
	}
	/**
	 * @return the contactOwner
	 */
	public EmployeeDetails getContactOwner() {
		return contactOwner;
	}
	/**
	 * @param contactOwner the contactOwner to set
	 */
	public void setContactOwner(EmployeeDetails contactOwner) {
		this.contactOwner = contactOwner;
	}
	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}
	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	/**
	 * @return the lastModifiedOn
	 */
	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}
	/**
	 * @param lastModifiedOn the lastModifiedOn to set
	 */
	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	/**
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the modifiedBy
	 */
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the employeeCode
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	
	/**
	 * @return the grade
	 */
	public BoardingLoadingDetails getGrade() {
		return grade;
	}
	/**
	 * @return the legalEntity
	 */
	public LegalEntity getLegalEntity() {
		return legalEntity;
	}
	/**
	 * @param employeeCode the employeeCode to set
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(BoardingLoadingDetails grade) {
		this.grade = grade;
	}
	/**
	 * @param legalEntity the legalEntity to set
	 */
	public void setLegalEntity(LegalEntity legalEntity) {
		this.legalEntity = legalEntity;
	}
	public ContactType getContactType() {
		return contactType;
	}
	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}
	public ContactDesignation getContactDesignation() {
		return contactDesignation;
	}
	public void setContactDesignation(ContactDesignation contactDesignation) {
		this.contactDesignation = contactDesignation;
	}
	public ExpenseManagementBusinessPlace getBusinessPlace() {
		return businessPlace;
	}
	public void setBusinessPlace(ExpenseManagementBusinessPlace businessPlace) {
		this.businessPlace = businessPlace;
	}
	@Transient transient ContactDetails old;
	@Override
	@PostLoad
	public void old() {
		old=(ContactDetails)SerializationUtils.clone(this);
	}
	@Override
	public ContactDetails gOld() {
		// TODO Auto-generated method stub
		return old;
	}
	public String toString() {
		return String.valueOf(contactId);
	}
	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}
	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}
	

}
