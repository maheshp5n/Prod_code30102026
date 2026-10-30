package com.pratikshat.exicom.model.request;

public class RejectCaseRequest {

	String caseManualId;
	String remark;
	
	String file;

	public String getCaseManualId() {
		return caseManualId;
	}

	public void setCaseManualId(String caseManualId) {
		this.caseManualId = caseManualId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
	
}
