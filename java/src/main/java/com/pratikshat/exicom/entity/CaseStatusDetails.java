package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class CaseStatusDetails implements java.io.Serializable,OldPresist<CaseStatusDetails>{
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	int id;
	
	String caseStatusName;
	
	@OneToMany(mappedBy="caseStatus",fetch = FetchType.EAGER)
	Set<CaseStageDetails> caseStage;
	
	boolean synced;
	
	public CaseStatusDetails() {}
	
	
	public CaseStatusDetails(int id) {
		this.id = id;
	}

	/**
	 * @return the caseStatusName
	 */
	public String getCaseStatusName() {
		return caseStatusName;
	}
	/**
	 * @param caseStatusName the caseStatusName to set
	 */
	public void setCaseStatusName(String caseStatusName) {
		this.caseStatusName = caseStatusName;
	}
	public Set<CaseStageDetails> getCaseStage() {
		return caseStage;
	}
	public void setCaseStage(Set<CaseStageDetails> caseStage) {
		this.caseStage = caseStage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public boolean isSynced() {
		return synced;
	}


	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	public String toString() {
		return String.valueOf(id);
	}
	

	@javax.persistence.Transient transient CaseStatusDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CaseStatusDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CaseStatusDetails gOld() {
		return old;
	}}
