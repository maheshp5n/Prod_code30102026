package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ToBeApproved implements java.io.Serializable,OldPresist<ToBeApproved>{

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	long approvalRuleId;
	String ObjectName;
	long objectId;
	String objectManualId;
	String objectUrl;
	String previousRemarks;
	@ManyToOne
	EmployeeDetails assignedTo;
	
	boolean status;
	int stage;
	
	String action;
	
	@Column(length = 2000)
	String remark;
	
	Date approvedDate;
	Date assignedDate;
	
	@Transient
	EmployeeDetails initiator;
	
	@ManyToOne
	EmployeeDetails actionBy;
	
	public ToBeApproved() {
	}
	
	public ToBeApproved(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getObjectName() {
		return ObjectName;
	}
	public void setObjectName(String objectName) {
		ObjectName = objectName;
	}
	public long getObjectId() {
		return objectId;
	}
	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}
	public String getObjectManualId() {
		return objectManualId;
	}
	public void setObjectManualId(String objectManualId) {
		this.objectManualId = objectManualId;
	}
	public String getObjectUrl() {
		return objectUrl;
	}
	public void setObjectUrl(String objectUrl) {
		this.objectUrl = objectUrl;
	}
	public EmployeeDetails getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(EmployeeDetails assignedTo) {
		this.assignedTo = assignedTo;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public Date getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	/**
	 * @return the approvalRuleId
	 */
	public long getApprovalRuleId() {
		return approvalRuleId;
	}

	/**
	 * @param approvalRuleId the approvalRuleId to set
	 */
	public void setApprovalRuleId(long approvalRuleId) {
		this.approvalRuleId = approvalRuleId;
	}

	public String getPreviousRemarks() {
		return previousRemarks;
	}

	public void setPreviousRemarks(String previousRemarks) {
		this.previousRemarks = previousRemarks;
	}
	

	public EmployeeDetails getInitiator() {
		return initiator;
	}

	public void setInitiator(EmployeeDetails initiator) {
		this.initiator = initiator;
	}

	

	public EmployeeDetails getActionBy() {
		return actionBy;
	}

	public void setActionBy(EmployeeDetails actionBy) {
		this.actionBy = actionBy;
	}



	@javax.persistence.Transient transient ToBeApproved old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ToBeApproved)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ToBeApproved gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}

}
