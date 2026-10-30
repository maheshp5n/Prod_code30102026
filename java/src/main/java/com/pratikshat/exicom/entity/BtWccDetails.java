package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({ObjectAuditListener.class,AuditingEntityListener.class})
public class BtWccDetails extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtWccDetails>{
	
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
	EmployeeDetails owner;
	
	boolean invoiced=false;
	
	float wccAmount;
	float wccTaxAmount;
	float wccTotalAmount;
	
	
	
	
	
	public BtWccDetails() {
	}

	public BtWccDetails(Long id) {
		this.id = id;
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

	@Transient transient BtWccDetails old;
	@PostLoad
	@Override
	public void old() {
		old=(BtWccDetails) SerializationUtils.clone(old);
	}

	@Override
	public BtWccDetails gOld() {
		// TODO Auto-generated method stub
		return this.old;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
		
	}

	@Override
	public EmployeeDetails getOwner() {
		// TODO Auto-generated method stub
		return this.owner;
	}

	@Override
	public void setOwner(EmployeeDetails u) {
				this.owner=u;
	}

	@Override
	public String getManualId() {
		// TODO Auto-generated method stub
		return this.manualId;
	}

	@Override
	public void setManualId(String manualId) {
		this.manualId=manualId;
		
	}

}
