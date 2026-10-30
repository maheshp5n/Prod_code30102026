package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class WccDetails implements java.io.Serializable,OldPresist<WccDetails>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String manualId;
	@ManyToOne(fetch=FetchType.LAZY)
	CircleDetails circle;
	@ManyToOne(fetch=FetchType.LAZY)
	LegalEntity serviceCenter;
	@ManyToOne(fetch=FetchType.LAZY)
	CaseTypes caseType;
	@ManyToOne(fetch=FetchType.LAZY)
	CaseReasonDetails caseReason;
	@ManyToOne(fetch=FetchType.LAZY)
	WccTypes wccType;
	@ManyToOne(fetch=FetchType.LAZY)
	WccStatus wccStatus;
	Date fromDate;
	Date toDate;
	boolean synked;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails lastModifiedBy;
	Date createdDate;
	Date lastModifiedDate;
	
	boolean invoiced=false;
	
	float wccAmount;
	float wccTaxAmount;
	float wccTotalAmount;
	
	public WccDetails() {}
	public WccDetails(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getManualId() {
		return manualId;
	}
	public void setManualId(String manualId) {
		this.manualId = manualId;
	}
	public CircleDetails getCircle() {
		return circle;
	}
	public void setCircle(CircleDetails circle) {
		this.circle = circle;
	}
	public LegalEntity getServiceCenter() {
		return serviceCenter;
	}
	public void setServiceCenter(LegalEntity serviceCenter) {
		this.serviceCenter = serviceCenter;
	}
	public CaseTypes getCaseType() {
		return caseType;
	}
	public void setCaseType(CaseTypes caseType) {
		this.caseType = caseType;
	}
	public CaseReasonDetails getCaseReason() {
		return caseReason;
	}
	public void setCaseReason(CaseReasonDetails caseReason) {
		this.caseReason = caseReason;
	}
	public WccTypes getWccType() {
		return wccType;
	}
	public void setWccType(WccTypes wccType) {
		this.wccType = wccType;
	}
	public WccStatus getWccStatus() {
		return wccStatus;
	}
	public void setWccStatus(WccStatus wccStatus) {
		this.wccStatus = wccStatus;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public boolean isSynked() {
		return synked;
	}
	public void setSynked(boolean synked) {
		this.synked = synked;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public EmployeeDetails getOwner() {
		return owner;
	}
	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}
	public EmployeeDetails getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(EmployeeDetails lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public boolean isInvoiced() {
		return invoiced;
	}
	public void setInvoiced(boolean invoiced) {
		this.invoiced = invoiced;
	}
	public float getWccAmount() {
		return wccAmount;
	}
	public void setWccAmount(float wccAmount) {
		this.wccAmount = wccAmount;
	}
	public float getWccTaxAmount() {
		return wccTaxAmount;
	}
	public void setWccTaxAmount(float wccTaxAmount) {
		this.wccTaxAmount = wccTaxAmount;
	}
	public float getWccTotalAmount() {
		return wccTotalAmount;
	}
	public void setWccTotalAmount(float wccTotalAmount) {
		this.wccTotalAmount = wccTotalAmount;
	}
	
	

	@javax.persistence.Transient transient WccDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(WccDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public WccDetails gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
