package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class CasePriorityDetails implements java.io.Serializable,OldPresist<CasePriorityDetails>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int CasePriorityId;
	String casePriorityName;
	boolean synced;
	
	public CasePriorityDetails() {}
	
	public CasePriorityDetails(int casePriorityId) {
		CasePriorityId = casePriorityId;
	}

	public int getCasePriorityId() {
		return CasePriorityId;
	}

	public void setCasePriorityId(int casePriorityId) {
		CasePriorityId = casePriorityId;
	}

	public String getCasePriorityName() {
		return casePriorityName;
	}

	public void setCasePriorityName(String casePriorityName) {
		this.casePriorityName = casePriorityName;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	public String toString(){
		return String.valueOf(CasePriorityId);
	 }
	

	@javax.persistence.Transient transient CasePriorityDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CasePriorityDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CasePriorityDetails gOld() {
		return old;
	}}
