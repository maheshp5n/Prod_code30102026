package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ChangeCaseTypeLog implements java.io.Serializable,OldPresist<ChangeCaseTypeLog>{
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	CaseDetails caseDetails;
	
	@ManyToOne(fetch=FetchType.LAZY)
	CaseTypes previousCaseType;
	@ManyToOne(fetch=FetchType.LAZY)
	CaseReasonDetails previousCaseReason;
	
	@ManyToOne(fetch=FetchType.LAZY)
	CaseTypes currentCaseType;
	@ManyToOne(fetch=FetchType.LAZY)
	CaseReasonDetails currentCaseReason;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	String poNo;
	Date poDate;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public CaseDetails getCaseDetails() {
		return caseDetails;
	}
	public void setCaseDetails(CaseDetails caseDetails) {
		this.caseDetails = caseDetails;
	}
	public CaseTypes getPreviousCaseType() {
		return previousCaseType;
	}
	public void setPreviousCaseType(CaseTypes previousCaseType) {
		this.previousCaseType = previousCaseType;
	}
	public CaseReasonDetails getPreviousCaseReason() {
		return previousCaseReason;
	}
	public void setPreviousCaseReason(CaseReasonDetails previousCaseReason) {
		this.previousCaseReason = previousCaseReason;
	}
	public CaseTypes getCurrentCaseType() {
		return currentCaseType;
	}
	public void setCurrentCaseType(CaseTypes currentCaseType) {
		this.currentCaseType = currentCaseType;
	}
	public CaseReasonDetails getCurrentCaseReason() {
		return currentCaseReason;
	}
	public void setCurrentCaseReason(CaseReasonDetails currentCaseReason) {
		this.currentCaseReason = currentCaseReason;
	}
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	

	public String getPoNo() {
		return poNo;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	public Date getPoDate() {
		return poDate;
	}
	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}



	@javax.persistence.Transient transient ChangeCaseTypeLog old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ChangeCaseTypeLog)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ChangeCaseTypeLog gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
