package com.pratikshat.exicom.model.request;

public class ChangeStatusRmaRequest {
	
	String rmaManualId;
	String remarks;
	public String getRmaManualId() {
		return rmaManualId;
	}
	public void setRmaManualId(String rmaManualId) {
		this.rmaManualId = rmaManualId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
