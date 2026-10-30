package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class CaseReasonDetails implements java.io.Serializable,OldPresist<CaseReasonDetails>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long reasonId;
	String caseReasonDetailsName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	CaseTypes caseType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	BusinessDomain vertical;
	boolean synced;
	
	int wccTypeId;
	
	String costCenter;
	
	@Column(columnDefinition = "bit default 1")
	boolean fsrMandatory=true;
	
	@Column(columnDefinition = "bit default 1")
	boolean uploadfsrMandatory=true;
	
	public boolean isUploadfsrMandatory() {
		return uploadfsrMandatory;
	}
	public void setUploadfsrMandatory(boolean uploadfsrMandatory) {
		this.uploadfsrMandatory = uploadfsrMandatory;
	}
	public boolean isFsrMandatory() {
		return fsrMandatory;
	}
	public void setFsrMandatory(boolean fsrMandatory) {
		this.fsrMandatory = fsrMandatory;
	}
	
	public CaseReasonDetails() {}
	public CaseReasonDetails(String caseReasonDetailsName) {
		this.caseReasonDetailsName=caseReasonDetailsName;
	}
	public CaseReasonDetails(long reasonId) {
		this.reasonId=reasonId;
	}
	/**
	 * @return the caseReasonDetailsName
	 */
	public  String getCaseReasonDetailsName() {
		return caseReasonDetailsName;
	}
	/**
	 * @param caseReasonDetailsName the caseReasonDetailsName to set
	 */
	public  void setCaseReasonDetailsName(String caseReasonDetailsName) {
		this.caseReasonDetailsName = caseReasonDetailsName;
	}
	/**
	 * @return the reasonId
	 */
	public  long getReasonId() {
		return reasonId;
	}
	
	/**
	 * @param reasonId the reasonId to set
	 */
	public void setReasonId(long reasonId) {
		this.reasonId = reasonId;
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
	public CaseTypes getCaseType() {
		return caseType;
	}
	public void setCaseType(CaseTypes caseType) {
		this.caseType = caseType;
	}
	public int getWccTypeId() {
		return wccTypeId;
	}
	public void setWccTypeId(int wccTypeId) {
		this.wccTypeId = wccTypeId;
	}
	
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String toString() {
		return String.valueOf(reasonId);
	}

	@javax.persistence.Transient transient CaseReasonDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CaseReasonDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CaseReasonDetails gOld() {
		return old;
	}}
