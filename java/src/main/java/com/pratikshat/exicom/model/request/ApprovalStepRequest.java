package com.pratikshat.exicom.model.request;

public class ApprovalStepRequest {

	long id;
	String approvedBy;
	int approvedStage;
	long approvalId;
	String objectFieldName;
	String onApprovedValue;
	String onRejectedValue;
	String stepName;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public int getApprovedStage() {
		return approvedStage;
	}
	public void setApprovedStage(int approvedStage) {
		this.approvedStage = approvedStage;
	}
	public long getApprovalId() {
		return approvalId;
	}
	public void setApprovalId(long approvalId) {
		this.approvalId = approvalId;
	}
	public String getObjectFieldName() {
		return objectFieldName;
	}
	public void setObjectFieldName(String objectFieldName) {
		this.objectFieldName = objectFieldName;
	}
	public String getOnApprovedValue() {
		return onApprovedValue;
	}
	public void setOnApprovedValue(String onApprovedValue) {
		this.onApprovedValue = onApprovedValue;
	}
	public String getOnRejectedValue() {
		return onRejectedValue;
	}
	public void setOnRejectedValue(String onRejectedValue) {
		this.onRejectedValue = onRejectedValue;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	
	
}
