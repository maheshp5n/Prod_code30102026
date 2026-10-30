package com.pratikshat.exicom.model.request;

import java.util.Date;

public class CustomerInventoryRequest {
	String accountSapNumber;
	String productPartCode;
	String productSerialNumber;
	String circle;
	Date receiveDate;
	long previousState;
	String customerChallanNumber;
	Date customerChallanDate;
	String customerDocketNumber;
	String redmrks;
	String warrantyStatus;
	String lotNo;
	
	Long id;
	public String getAccountSapNumber() {
		return accountSapNumber;
	}
	public void setAccountSapNumber(String accountSapNumber) {
		this.accountSapNumber = accountSapNumber;
	}
	public String getProductPartCode() {
		return productPartCode;
	}
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	public String getProductSerialNumber() {
		return productSerialNumber;
	}
	public void setProductSerialNumber(String productSerialNumber) {
		this.productSerialNumber = productSerialNumber;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	public long getPreviousState() {
		return previousState;
	}
	public void setPreviousState(long previousState) {
		this.previousState = previousState;
	}
	public String getCustomerChallanNumber() {
		return customerChallanNumber;
	}
	public void setCustomerChallanNumber(String customerChallanNumber) {
		this.customerChallanNumber = customerChallanNumber;
	}
	public Date getCustomerChallanDate() {
		return customerChallanDate;
	}
	public void setCustomerChallanDate(Date customerChallanDate) {
		this.customerChallanDate = customerChallanDate;
	}
	public String getCustomerDocketNumber() {
		return customerDocketNumber;
	}
	public void setCustomerDocketNumber(String customerDocketNumber) {
		this.customerDocketNumber = customerDocketNumber;
	}
	public String getRedmrks() {
		return redmrks;
	}
	public void setRedmrks(String redmrks) {
		this.redmrks = redmrks;
	}
	public String getWarrantyStatus() {
		return warrantyStatus;
	}
	public void setWarrantyStatus(String warrantyStatus) {
		this.warrantyStatus = warrantyStatus;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	
	
}
