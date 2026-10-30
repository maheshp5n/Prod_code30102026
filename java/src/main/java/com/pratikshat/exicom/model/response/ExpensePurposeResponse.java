package com.pratikshat.exicom.model.response;

public class ExpensePurposeResponse {

	int expensePurposeId;
	String expensePurposeName;
	
	
	
	public ExpensePurposeResponse(int expensePurposeId, String expensePurposeName) {
		this.expensePurposeId = expensePurposeId;
		this.expensePurposeName = expensePurposeName;
	}
	public int getExpensePurposeId() {
		return expensePurposeId;
	}
	public void setExpensePurposeId(int expensePurposeId) {
		this.expensePurposeId = expensePurposeId;
	}
	public String getExpensePurposeName() {
		return expensePurposeName;
	}
	public void setExpensePurposeName(String expensePurposeName) {
		this.expensePurposeName = expensePurposeName;
	}
	
	
}
