package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtToBeApproved extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtToBeApproved> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	BtApprovalStapes stapes;
	
	String ObjectName;
	long objectId;
	String objectManualId;
	String objectUrl;
	String previousRemarks;
	
	String assignedTo;
	boolean status;
	int stage;
	
	String action;
	
	@Column(length = 2000)
	String remark;
	
	Date approvedDate;
	Date assignedDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails actionBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails initiator;
	
	public BtToBeApproved() {}
	public BtToBeApproved(Long id) {}
	
	
	
	public BtApprovalStapes getStapes() {
		return stapes;
	}
	public void setStapes(BtApprovalStapes stapes) {
		this.stapes = stapes;
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
	public String getPreviousRemarks() {
		return previousRemarks;
	}
	public void setPreviousRemarks(String previousRemarks) {
		this.previousRemarks = previousRemarks;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
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
	public EmployeeDetails getActionBy() {
		return actionBy;
	}
	public void setActionBy(EmployeeDetails actionBy) {
		this.actionBy = actionBy;
	}
	public EmployeeDetails getInitiator() {
		return initiator;
	}
	public void setInitiator(EmployeeDetails initiator) {
		this.initiator = initiator;
	}


	@Transient
	transient BtToBeApproved old;
	
	@PostLoad
	@Override
	public void old() {
		old=(BtToBeApproved)SerializationUtils.clone(this);
	}

	@Override
	public BtToBeApproved gOld() {
		
		return old;
	}

	@Override
	public Long getId() {
		
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}

	@Override
	public EmployeeDetails getOwner() {
		
		return null;
	}

	@Override
	public void setOwner(EmployeeDetails u) {
		
	}

	@Override
	public String getManualId() {
		
		return null;
	}

	@Override
	public void setManualId(String manualId) {
		
	}

	public String toString() {
		return String.valueOf(id);
	}
}
