package com.pratikshat.exicom.model.request;

import java.util.Date;

public class PODUpdateRequest {

	String caseNumber;
	
	String podNumber;
	Date podDate;
	
	String podShippingMode;
	String podShippingToName;
	String podShippingTrackingNumber;
	Date podShippingExpectedDate;
	public String getCaseNumber() {
		return caseNumber;
	}
	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}
	public String getPodNumber() {
		return podNumber;
	}
	public void setPodNumber(String podNumber) {
		this.podNumber = podNumber;
	}
	public Date getPodDate() {
		return podDate;
	}
	public void setPodDate(Date podDate) {
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
	public Date getPodShippingExpectedDate() {
		return podShippingExpectedDate;
	}
	public void setPodShippingExpectedDate(Date podShippingExpectedDate) {
		this.podShippingExpectedDate = podShippingExpectedDate;
	}
}
