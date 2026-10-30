package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class CaseStageDetails implements java.io.Serializable,OldPresist<CaseStageDetails>{
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	int id;
	
	String caseStageName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	CaseStatusDetails caseStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	BusinessDomain division;
	
	boolean synced;
	
	public CaseStageDetails() {}
	
	
	
	public CaseStageDetails(int id) {
		this.id = id;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the caseStageName
	 */
	public String getCaseStageName() {
		return caseStageName;
	}
	/**
	 * @param caseStageName the caseStageName to set
	 */
	public void setCaseStageName(String caseStageName) {
		this.caseStageName = caseStageName;
	}
	public CaseStatusDetails getCaseStatus() {
		return caseStatus;
	}
	public void setCaseStatus(CaseStatusDetails caseStatus) {
		this.caseStatus = caseStatus;
	}
	


	public BusinessDomain getDivision() {
		return division;
	}



	public void setDivision(BusinessDomain division) {
		this.division = division;
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

	@javax.persistence.Transient transient CaseStageDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CaseStageDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CaseStageDetails gOld() {
		return old;
	}}
