package com.pratikshat.exicom.model.response;

public class CaseStatusResponse {

	int id;
	String caseStatusName;
	
	public CaseStatusResponse(int id, String caseStatusName) {
		this.id = id;
		this.caseStatusName = caseStatusName;
	}
	
	
	public CaseStatusResponse() {
		
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCaseStatusName() {
		return caseStatusName;
	}
	public void setCaseStatusName(String caseStatusName) {
		this.caseStatusName = caseStatusName;
	}
	
	
}
