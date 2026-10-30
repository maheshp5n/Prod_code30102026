package com.pratikshat.exicom.model.response;

public class ExpenseTypeResponse {

	int expenseTypeId;
	String expenseTypeName;
	public ExpenseTypeResponse(int expenseTypeId, String expenseTypeName) {
		this.expenseTypeId = expenseTypeId;
		this.expenseTypeName = expenseTypeName;
	}
	public int getExpenseTypeId() {
		return expenseTypeId;
	}
	public void setExpenseTypeId(int expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}
	public String getExpenseTypeName() {
		return expenseTypeName;
	}
	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}
	
	
}
