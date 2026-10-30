package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ClaimStatus implements java.io.Serializable,OldPresist<ClaimStatus>{

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long claimStatusId;
	
	String claimStatusName;
	
	boolean synced;

	public ClaimStatus() {
	}

	public ClaimStatus(long claimStatusId) {
		this.claimStatusId = claimStatusId;
	}

	public long getClaimStatusId() {
		return claimStatusId;
	}

	public void setClaimStatusId(long claimStatusId) {
		this.claimStatusId = claimStatusId;
	}

	public String getClaimStatusName() {
		return claimStatusName;
	}

	public void setClaimStatusName(String claimStatusName) {
		this.claimStatusName = claimStatusName;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	
	@javax.persistence.Transient transient ClaimStatus old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ClaimStatus)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ClaimStatus gOld() {
		return old;
	}
	public String toString() {
			return ""+claimStatusId;
	}
}
