package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class RequisitionStatus implements java.io.Serializable,OldPresist<RequisitionStatus>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long statusId;
	String statusName;
	boolean synced;
	
	public RequisitionStatus(long statusId) {
		this.statusId = statusId;
	}

	public RequisitionStatus() {
		
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	

	@javax.persistence.Transient transient RequisitionStatus old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(RequisitionStatus)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public RequisitionStatus gOld() {
		return old;
	}
	public String toString() {
			return ""+statusId;
	}
}
