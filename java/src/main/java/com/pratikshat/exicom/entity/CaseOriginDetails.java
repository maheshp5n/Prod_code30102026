package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class CaseOriginDetails implements java.io.Serializable,OldPresist<CaseOriginDetails>{
	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	int caseOriginId;
	String caseOrignName;
	boolean synced;
	
	public CaseOriginDetails () {}
	
	
	public CaseOriginDetails(int caseOriginId) {
		this.caseOriginId = caseOriginId;
	}


	/**
	 * @return the caseOrignName
	 */
	public String getCaseOrignName() {
		return caseOrignName;
	}
	/**
	 * @param caseOrignName the caseOrignName to set
	 */
	public void setCaseOrignName(String caseOrignName) {
		this.caseOrignName = caseOrignName;
	}


	public int getCaseOriginId() {
		return caseOriginId;
	}


	public void setCaseOriginId(int caseOriginId) {
		this.caseOriginId = caseOriginId;
	}


	public boolean isSynced() {
		return synced;
	}


	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	public String toString() {
		return String.valueOf(caseOriginId);
	}
	

	@javax.persistence.Transient transient CaseOriginDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CaseOriginDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CaseOriginDetails gOld() {
		return old;
	}}
