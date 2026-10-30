package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
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
public class Accounts implements java.io.Serializable,OldPresist<Accounts>{
	@Id
	@Column(length = 20)
	String sapCustomerNumber;
	@NotNull
	@NotEmpty
	@NotBlank
	String name;
	@ManyToOne
	@JoinColumn(name="account_type")
	AccountsType accountType;
	@Column(length=500)
	String description;
	@ManyToOne
	@JoinColumn(name="catagory_id")
	BusinessDomain businessDoamin;
	String emailId;
	String phoneNumber;
	@ManyToOne
	@JoinColumn(name="shipping_info_id")
	AddressInformation shippingInfo;
	@ManyToOne
	@JoinColumn(name="billing_info_id")
	AddressInformation billingInfo;
	@ManyToOne
	@JoinColumn(name="account_owner_details_id")
	EmployeeDetails accountOwnerDetails;
	@ManyToOne
	@JoinColumn(name="created_by")
	EmployeeDetails createdBy;
	Date createdDate;
	@ManyToOne
	@JoinColumn(name="modified_by")
	EmployeeDetails modifiedBy;
	Date lastModified;
	boolean synced;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Accounts parentAccount;
	@ManyToOne(fetch=FetchType.LAZY)
	CustomerDeatils parentCustomer;
	
	
	/**
	 * @return the parentAccount
	 */
	public Accounts getParentAccount() {
		return parentAccount;
	}
	/**
	 * @return the parentCustomer
	 */
	public CustomerDeatils getParentCustomer() {
		return parentCustomer;
	}
	/**
	 * @param parentAccount the parentAccount to set
	 */
	public void setParentAccount(Accounts parentAccount) {
		this.parentAccount = parentAccount;
	}
	/**
	 * @param parentCustomer the parentCustomer to set
	 */
	public void setParentCustomer(CustomerDeatils parentCustomer) {
		this.parentCustomer = parentCustomer;
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
	public Accounts() {}
	public Accounts(String sapNumber) {
		this.sapCustomerNumber=sapNumber;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the sapCustomerNumber
	 */
	public String getSapCustomerNumber() {
		return sapCustomerNumber;
	}
	/**
	 * @param sapCustomerNumber the sapCustomerNumber to set
	 */
	public void setSapCustomerNumber(String sapCustomerNumber) {
		this.sapCustomerNumber = sapCustomerNumber;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the accountType
	 */
	public AccountsType getAccountType() {
		return accountType;
	}
	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(AccountsType accountType) {
		this.accountType = accountType;
	}
	
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	
	
	
	public BusinessDomain getBusinessDoamin() {
		return businessDoamin;
	}
	public void setBusinessDoamin(BusinessDomain businessDoamin) {
		this.businessDoamin = businessDoamin;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the shipingInfo
	 */
	public AddressInformation getShippingInfo() {
		return shippingInfo;
	}
	/**
	 * @param shipingInfo the shipingInfo to set
	 */
	public void setShipingInfo(AddressInformation shippingInfo) {
		this.shippingInfo = shippingInfo;
	}
	/**
	 * @return the billingInfo
	 */
	public AddressInformation getBillingInfo() {
		return billingInfo;
	}
	/**
	 * @param billingInfo the billingInfo to set
	 */
	public void setBillingInfo(AddressInformation billingInfo) {
		this.billingInfo = billingInfo;
	}
	/**
	 * @return the accountOwnerDetails
	 */
	public EmployeeDetails getAccountOwnerDetails() {
		return accountOwnerDetails;
	}
	/**
	 * @param accountOwnerDetails the accountOwnerDetails to set
	 */
	public void setAccountOwnerDetails(EmployeeDetails accountOwnerDetails) {
		this.accountOwnerDetails = accountOwnerDetails;
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
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	 * @return the lastModified
	 */
	public Date getLastModified() {
		return lastModified;
	}
	/**
	 * @param lastModified the lastModified to set
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	/**
	 * @param shippingInfo the shippingInfo to set
	 */
	public void setShippingInfo(AddressInformation shippingInfo) {
		this.shippingInfo = shippingInfo;
	}
	public String toString() {
		return sapCustomerNumber;
	}
	@Transient transient Accounts old;
	@Override
	@PostLoad
	public void old() {
		old=(Accounts)SerializationUtils.clone(this);
	}
	@Override
	public Accounts gOld() {
		// TODO Auto-generated method stub
		return old;
	}
	

}
