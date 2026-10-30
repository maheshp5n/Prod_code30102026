package com.pratikshat.exicom.model.response;

public class CustomerForList {
	long customerId;
	String customerName;
	public CustomerForList() {}
	public CustomerForList(long customerId,String customerName) {
		this.customerId=customerId;
		this.customerName=customerName;
	}
	/**
	 * @return the customerId
	 */
	public long getCustomerId() {
		return customerId;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
