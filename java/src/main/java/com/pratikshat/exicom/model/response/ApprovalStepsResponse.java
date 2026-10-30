package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.ApprovalSteps;

public class ApprovalStepsResponse {

	long id;
	
	long approvalId;
	
	String stepName;
	
	String approvedBy;
	int approvedStage;
	
	String objectFiledName;
	String onAprovedValue;
	String onRejectedValue;
	
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	String createdByEmailId;
	Date createdDate;
	
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	String modifiedByEmailId;
	Date modifiedDate;
	
	
	public ApprovalStepsResponse() {}
	
	public ApprovalStepsResponse(ApprovalSteps steps) {
		this.id = steps.getId();
		this.stepName = steps.getStepName();
		this.approvedBy = steps.getApprovedBy();
		this.approvedStage = steps.getApprovedStage();
		this.objectFiledName = steps.getObjectFiledName();
		this.onAprovedValue = steps.getOnAprovedValue();
		this.onRejectedValue = steps.getOnRejectedValue();
		
		if(steps.getApproval()!=null) {
			this.approvalId = steps.getApproval().getId();
		}
		
		if(steps.getCreatedBy()!=null) {
			this.createdByFirstName = steps.getCreatedBy().getFirstName();
			this.createdByMiddleName = steps.getCreatedBy().getMiddleName();
			this.createdByLastName = steps.getCreatedBy().getLastName();
			this.createdByEmailId = steps.getCreatedBy().getEmailId();
			this.createdDate = steps.getCreatedDate();
		}
		
		if(steps.getModifiedBy()!=null) {
			this.modifiedByFirstName = steps.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = steps.getModifiedBy().getMiddleName();
			this.modifiedByLastName = steps.getModifiedBy().getLastName();
			this.modifiedByEmailId = steps.getModifiedBy().getEmailId();
			this.modifiedDate = steps.getModifiedDate();
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(long approvalId) {
		this.approvalId = approvalId;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
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

	public String getObjectFiledName() {
		return objectFiledName;
	}

	public void setObjectFiledName(String objectFiledName) {
		this.objectFiledName = objectFiledName;
	}

	public String getOnAprovedValue() {
		return onAprovedValue;
	}

	public void setOnAprovedValue(String onAprovedValue) {
		this.onAprovedValue = onAprovedValue;
	}

	public String getOnRejectedValue() {
		return onRejectedValue;
	}

	public void setOnRejectedValue(String onRejectedValue) {
		this.onRejectedValue = onRejectedValue;
	}

	public String getCreatedByFirstName() {
		return createdByFirstName;
	}

	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}

	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}

	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}

	public String getCreatedByLastName() {
		return createdByLastName;
	}

	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}

	public String getCreatedByEmailId() {
		return createdByEmailId;
	}

	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}

	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}

	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}

	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}

	public String getModifiedByLastName() {
		return modifiedByLastName;
	}

	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}

	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}

	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
}
