package com.pratikshat.exicom.model.request;

import java.util.Date;

public class ChangeCaseTypeRequest {

	String caseManualId;
	
	String caseType;
	String caseReason;
	
	String poNo;
	Date poDate;
	
	public String getCaseManualId() {
		return caseManualId;
	}
	public void setCaseManualId(String caseManualId) {
		this.caseManualId = caseManualId;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getCaseReason() {
		return caseReason;
	}
	public void setCaseReason(String caseReason) {
		this.caseReason = caseReason;
	}
	public String getPoNo() {
		return poNo;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	public Date getPoDate() {
		return poDate;
	}
	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}
	
	
}
