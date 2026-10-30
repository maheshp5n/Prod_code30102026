package com.pratikshat.exicom.model.response;

public class CaseForList {
	long caseId;
	String caseManualId;
	
	
	
	public CaseForList(long caseId, String caseManualId) {
		this.caseId = caseId;
		this.caseManualId = caseManualId;
	}
	public CaseForList() {
		
	}
	public long getCaseId() {
		return caseId;
	}
	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}
	public String getCaseManualId() {
		return caseManualId;
	}
	public void setCaseManualId(String caseManualId) {
		this.caseManualId = caseManualId;
	}
	
	
	
}
