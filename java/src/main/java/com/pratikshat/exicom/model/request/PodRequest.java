package com.pratikshat.exicom.model.request;

import java.util.Date;

public class PodRequest {
	
	long[] caseId;
	String podNumber;
	String podDate;
	String podShippingMode;
	String podShippingToName;
	String podShippingTrackingNumber;
	String podShippingExpectedDate;
	String jmsNo;
	String fileDetails;
	String customer;
	String customerPoNumber;
	String customerPoDate;
	String grnNumber;
	String grnDate;
	String servicePeriodFrom;
	String servicePeriodTo;
	// long wccId;
	

	

	// public long getWccId() {
	// 	return wccId;
	// }
	// public void setWccId(long wccId) {
	// 	this.wccId = wccId;
	// }
	public String getCustomer() {
		return customer;
	}
	public String getCustomerPoNumber() {
		return customerPoNumber;
	}
	public void setCustomerPoNumber(String customerPoNumber) {
		this.customerPoNumber = customerPoNumber;
	}
	public String getCustomerPoDate() {
		return customerPoDate;
	}
	public void setCustomerPoDate(String customerPoDate) {
		this.customerPoDate = customerPoDate;
	}
	public String getGrnNumber() {
		return grnNumber;
	}
	public void setGrnNumber(String grnNumber) {
		this.grnNumber = grnNumber;
	}
	public String getGrnDate() {
		return grnDate;
	}
	public void setGrnDate(String grnDate) {
		this.grnDate = grnDate;
	}
	public String getServicePeriodFrom() {
		return servicePeriodFrom;
	}
	public void setServicePeriodFrom(String servicePeriodFrom) {
		this.servicePeriodFrom = servicePeriodFrom;
	}
	public String getServicePeriodTo() {
		return servicePeriodTo;
	}
	public void setServicePeriodTo(String servicePeriodTo) {
		this.servicePeriodTo = servicePeriodTo;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public long[] getCaseId() {
		return caseId;
	}
	public void setCaseId(long[] caseId) {
		this.caseId = caseId;
	}
	public String getPodNumber() {
		return podNumber;
	}
	public void setPodNumber(String podNumber) {
		this.podNumber = podNumber;
	}
	public String getPodDate() {
		return podDate;
	}
	public void setPodDate(String podDate) {
		this.podDate = podDate;
	}
	public String getPodShippingMode() {
		return podShippingMode;
	}
	public void setPodShippingMode(String podShippingMode) {
		this.podShippingMode = podShippingMode;
	}
	public String getPodShippingToName() {
		return podShippingToName;
	}
	public void setPodShippingToName(String podShippingToName) {
		this.podShippingToName = podShippingToName;
	}
	public String getPodShippingTrackingNumber() {
		return podShippingTrackingNumber;
	}
	public void setPodShippingTrackingNumber(String podShippingTrackingNumber) {
		this.podShippingTrackingNumber = podShippingTrackingNumber;
	}
	public String getPodShippingExpectedDate() {
		return podShippingExpectedDate;
	}
	public void setPodShippingExpectedDate(String podShippingExpectedDate) {
		this.podShippingExpectedDate = podShippingExpectedDate;
	}
	public String getJmsNo() {
		return jmsNo;
	}
	public void setJmsNo(String jmsNo) {
		this.jmsNo = jmsNo;
	}
	public String getFileDetails() {
		return fileDetails;
	}
	public void setFileDetails(String fileDetails) {
		this.fileDetails = fileDetails;
	}
	
	

}
