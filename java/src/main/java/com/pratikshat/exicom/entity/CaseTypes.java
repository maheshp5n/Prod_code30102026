package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class CaseTypes implements java.io.Serializable,OldPresist<CaseTypes>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long caseTypeId;
	@ManyToOne(fetch = FetchType.LAZY)
	BusinessDomain vertical;
	String caseTypesName;
	boolean synced;
	int wccTypeId;
	public CaseTypes() {}
	public CaseTypes(String caseTypesName) {
		this.caseTypesName=caseTypesName;
	}
	public CaseTypes(long caseTypeId) {
		this.caseTypeId=caseTypeId;
	}
	
	/**
	 * @return the caseTypesName
	 */
	public String getCaseTypesName() {
		return caseTypesName;
	}
	/**
	 * @param caseTypesName the caseTypesName to set
	 */
	public void setCaseTypesName(String caseTypesName) {
		this.caseTypesName = caseTypesName;
	}
	/**
	 * @return the caseTypeId
	 */
	public long getCaseTypeId() {
		return caseTypeId;
	}
	
	/**
	 * @param caseTypeId the caseTypeId to set
	 */
	public void setCaseTypeId(long caseTypeId) {
		this.caseTypeId = caseTypeId;
	}
	
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	public BusinessDomain getVertical() {
		return vertical;
	}
	public void setVertical(BusinessDomain vertical) {
		this.vertical = vertical;
	}
	public int getWccTypeId() {
		return wccTypeId;
	}
	public void setWccTypeId(int wccTypeId) {
		this.wccTypeId = wccTypeId;
	}
	public String toString() {
		return String.valueOf(caseTypeId);
	}

	@javax.persistence.Transient transient CaseTypes old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CaseTypes)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CaseTypes gOld() {
		return old;
	}}
