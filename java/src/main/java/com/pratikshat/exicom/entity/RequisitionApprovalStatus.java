package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class RequisitionApprovalStatus implements java.io.Serializable,OldPresist<RequisitionApprovalStatus>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long approvalStatusId;
	String approvalStatusName;
	boolean synced;
	
	public RequisitionApprovalStatus() {
		
	}

	public RequisitionApprovalStatus(long approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}

	public long getApprovalStatusId() {
		return approvalStatusId;
	}

	public void setApprovalStatusId(long approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}

	public String getApprovalStatusName() {
		return approvalStatusName;
	}

	public void setApprovalStatusName(String approvalStatusName) {
		this.approvalStatusName = approvalStatusName;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	

	@javax.persistence.Transient transient RequisitionApprovalStatus old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(RequisitionApprovalStatus)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public RequisitionApprovalStatus gOld() {
		return old;
	}
	public String toString() {
			return ""+approvalStatusId;
	}
}
