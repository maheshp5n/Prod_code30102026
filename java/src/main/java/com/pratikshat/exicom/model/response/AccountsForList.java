package com.pratikshat.exicom.model.response;

public class AccountsForList {
	private String sapAccountNumber;
	private String accountName;
	private String primaryAccountSampNumber;
	
	public AccountsForList() {}
	public AccountsForList(String sapAccountNumber,String accountName,String primaryAccountSapNumber) {
		this.sapAccountNumber=sapAccountNumber;
		this.accountName=accountName;
		this.primaryAccountSampNumber=primaryAccountSapNumber;
	}
	
	/**
	 * @return the sapAccountNumber
	 */
	public String getSapAccountNumber() {
		return sapAccountNumber;
	}
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @param sapAccountNumber the sapAccountNumber to set
	 */
	public void setSapAccountNumber(String sapAccountNumber) {
		this.sapAccountNumber = sapAccountNumber;
	}
	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * @return the primaryAccountSampNumber
	 */
	public String getPrimaryAccountSampNumber() {
		return primaryAccountSampNumber;
	}
	/**
	 * @param primaryAccountSampNumber the primaryAccountSampNumber to set
	 */
	public void setPrimaryAccountSampNumber(String primaryAccountSampNumber) {
		this.primaryAccountSampNumber = primaryAccountSampNumber;
	}
	
	
}
