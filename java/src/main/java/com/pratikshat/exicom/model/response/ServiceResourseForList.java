package com.pratikshat.exicom.model.response;

public class ServiceResourseForList {
	private long serviceResourceId;
	private String name;
	private String accountName;
	private String emailId;
	public ServiceResourseForList(long serviceResourceId,String name,String accountName) {
		this.serviceResourceId=serviceResourceId;
		this.name=name;
	}
	
	public ServiceResourseForList(long serviceResourceId,String name,String accountName,String emailId) {
		this.serviceResourceId=serviceResourceId;
		this.name=name;
		this.emailId = emailId;
		this.accountName = accountName;
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}	
}
