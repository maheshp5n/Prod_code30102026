package com.pratikshat.exicom.model.response;

public class CaseStageResponse {

	int id;
	String caseStageName;
	
	public CaseStageResponse(int id,String caseStageName) {
		this.id = id;
		this.caseStageName = caseStageName;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCaseStageName() {
		return caseStageName;
	}
	public void setCaseStageName(String caseStageName) {
		this.caseStageName = caseStageName;
	}
	
	
}
