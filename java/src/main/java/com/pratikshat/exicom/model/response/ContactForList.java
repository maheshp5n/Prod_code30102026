package com.pratikshat.exicom.model.response;

public class ContactForList {
	long contactId;
	String firstName;
	String middleName;
	String lastName;
	String emailId;
	String phoneNumber;
	String accountSapNumber;
	String primaryAccNumber;
	public ContactForList() {}
	public ContactForList(long contactId,String firstName,String middleName,String lastName,String emailId,String phoneNumber) {
		this.contactId=contactId;
		this.firstName=firstName;
		this.middleName=middleName;
		this.lastName=lastName;
		this.emailId=emailId;
		this.phoneNumber=phoneNumber;
	}
	
	public ContactForList(long contactId,String firstName,String middleName,String lastName,String emailId,String phoneNumber,String accountSapNumber,String primaryAccNumber) {
		this.contactId=contactId;
		this.firstName=firstName;
		this.middleName=middleName;
		this.lastName=lastName;
		this.emailId=emailId;
		this.phoneNumber=phoneNumber;
		this.accountSapNumber=accountSapNumber;
		this.primaryAccNumber=primaryAccNumber;
	}
	
	
	public ContactForList(long contactId,String firstName,String middleName,String lastName) {
		this.contactId=contactId;
		this.firstName=firstName;
		this.middleName=middleName;
		this.lastName=lastName;
	}
	
	/**
	 * @return the contactId
	 */
	public long getContactId() {
		return contactId;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the accountSapNumber
	 */
	public String getAccountSapNumber() {
		return accountSapNumber;
	}
	/**
	 * @param accountSapNumber the accountSapNumber to set
	 */
	public void setAccountSapNumber(String accountSapNumber) {
		this.accountSapNumber = accountSapNumber;
	}
	/**
	 * @return the primaryAccNumber
	 */
	public String getPrimaryAccNumber() {
		return primaryAccNumber;
	}
	/**
	 * @param primaryAccNumber the primaryAccNumber to set
	 */
	public void setPrimaryAccNumber(String primaryAccNumber) {
		this.primaryAccNumber = primaryAccNumber;
	}
	
	
}
