package com.pratikshat.exicom.model.response;

public class LoginUserAccountsDetails {
	private long contactId;
	private String userEmailId;
	private String accountSapNumber;
	private String accountName;
	private String accountType;
	private String accountCatagory;
	private String userFirstName;
	private String userLastName;
	private String userMiddleName;
	
	
	public LoginUserAccountsDetails() {}
	public LoginUserAccountsDetails(long contactId,String userEmailId,String accountSapNumber,String accountName,String accountType,String accountCatagory,String firstName,String lastName,String middleName) {
		this.contactId=contactId;
		this.userEmailId=userEmailId;
		this.accountSapNumber=accountSapNumber;
		this.accountName=accountName;
		this.accountType=accountType;
		this.accountCatagory=accountCatagory;
		this.userFirstName=firstName;
		this.userLastName=lastName;
		this.userMiddleName = middleName;
	}
	/**
	 * @return the contactId
	 */
	public long getContactId() {
		return contactId;
	}
	/**
	 * @return the userEmailId
	 */
	public String getUserEmailId() {
		return userEmailId;
	}
	/**
	 * @return the accountSapNumber
	 */
	public String getAccountSapNumber() {
		return accountSapNumber;
	}
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}
	/**
	 * @return the accountCatagory
	 */
	public String getAccountCatagory() {
		return accountCatagory;
	}
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	/**
	 * @param userEmailId the userEmailId to set
	 */
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	/**
	 * @param accountSapNumber the accountSapNumber to set
	 */
	public void setAccountSapNumber(String accountSapNumber) {
		this.accountSapNumber = accountSapNumber;
	}
	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	/**
	 * @param accountCatagory the accountCatagory to set
	 */
	public void setAccountCatagory(String accountCatagory) {
		this.accountCatagory = accountCatagory;
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
