package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BRCRepairBERRemark implements java.io.Serializable,OldPresist<BRCRepairBERRemark>{

	
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	int berRemarkId;
	String berRemarkName;
	boolean synced;
	
	
	
	public BRCRepairBERRemark(int berRemarkId) {
		this.berRemarkId = berRemarkId;
	}
	public BRCRepairBERRemark() {
		
	}
	public int getBerRemarkId() {
		return berRemarkId;
	}
	public void setBerRemarkId(int berRemarkId) {
		this.berRemarkId = berRemarkId;
	}
	public String getBerRemarkName() {
		return berRemarkName;
	}
	public void setBerRemarkName(String berRemarkName) {
		this.berRemarkName = berRemarkName;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	
	

	@javax.persistence.Transient transient BRCRepairBERRemark old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BRCRepairBERRemark)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BRCRepairBERRemark gOld() {
		return old;
	}
	public String toString() {
			return ""+berRemarkId;
	}
}
