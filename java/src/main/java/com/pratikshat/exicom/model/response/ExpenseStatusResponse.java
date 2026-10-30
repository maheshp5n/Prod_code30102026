package com.pratikshat.exicom.model.response;

public class ExpenseStatusResponse {
	int statusId;
	String statusName;
	public ExpenseStatusResponse(int statusId, String statusName) {
		this.statusId = statusId;
		this.statusName = statusName;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
}
