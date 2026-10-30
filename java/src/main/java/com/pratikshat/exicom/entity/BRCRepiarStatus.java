package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BRCRepiarStatus implements java.io.Serializable,OldPresist<BRCRepiarStatus>{

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	int statusId;
	String statusName;

	boolean synced;

	public BRCRepiarStatus() {

	}

	public BRCRepiarStatus(int statusId) {

		this.statusId = statusId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
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


	@javax.persistence.Transient transient BRCRepiarStatus old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BRCRepiarStatus)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BRCRepiarStatus gOld() {
		return old;
	}
	public String toString() {
			return ""+statusId;
	}
}
