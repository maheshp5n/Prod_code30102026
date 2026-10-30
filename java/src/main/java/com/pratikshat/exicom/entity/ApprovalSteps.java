package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ApprovalSteps implements java.io.Serializable,OldPresist<ApprovalSteps>{

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Approval approval;
	
	String stepName;
	
	String approvedBy;
	int approvedStage;
	
	String objectFiledName;
	String onAprovedValue;
	String onRejectedValue;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	public ApprovalSteps() {
		
	}
	
	public ApprovalSteps(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Approval getApproval() {
		return approval;
	}
	public void setApproval(Approval approval) {
		this.approval = approval;
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
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the objectFiledName
	 */
	public String getObjectFiledName() {
		return objectFiledName;
	}

	/**
	 * @return the onAprovedValue
	 */
	public String getOnAprovedValue() {
		return onAprovedValue;
	}

	/**
	 * @return the onRejectedValue
	 */
	public String getOnRejectedValue() {
		return onRejectedValue;
	}

	/**
	 * @param objectFiledName the objectFiledName to set
	 */
	public void setObjectFiledName(String objectFiledName) {
		this.objectFiledName = objectFiledName;
	}

	/**
	 * @param onAprovedValue the onAprovedValue to set
	 */
	public void setOnAprovedValue(String onAprovedValue) {
		this.onAprovedValue = onAprovedValue;
	}

	/**
	 * @param onRejectedValue the onRejectedValue to set
	 */
	public void setOnRejectedValue(String onRejectedValue) {
		this.onRejectedValue = onRejectedValue;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	
	
	

	@javax.persistence.Transient transient ApprovalSteps old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ApprovalSteps)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ApprovalSteps gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
