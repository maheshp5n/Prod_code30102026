package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class PendingResponsibility implements java.io.Serializable,OldPresist<PendingResponsibility>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long PendingResponsibilityId;
	
	String PendingResponsibilityName;
	boolean synced;

	public PendingResponsibility() {
		
	}

	public PendingResponsibility(long pendingResponsibilityId) {
		PendingResponsibilityId = pendingResponsibilityId;
	}

	public long getPendingResponsibilityId() {
		return PendingResponsibilityId;
	}

	public void setPendingResponsibilityId(long pendingResponsibilityId) {
		PendingResponsibilityId = pendingResponsibilityId;
	}

	public String getPendingResponsibilityName() {
		return PendingResponsibilityName;
	}

	public void setPendingResponsibilityName(String pendingResponsibilityName) {
		PendingResponsibilityName = pendingResponsibilityName;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	public String toString() {
		return String.valueOf(PendingResponsibilityId);
	}
	

	
	
	

	@javax.persistence.Transient transient PendingResponsibility old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(PendingResponsibility)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public PendingResponsibility gOld() {
		return old;
	}}
