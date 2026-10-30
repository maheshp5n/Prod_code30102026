package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class AnnexureLineItem implements java.io.Serializable,OldPresist<AnnexureLineItem>{

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	
	String manualId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Annexure annexure;
	
	@ManyToOne(fetch = FetchType.LAZY)
	ServiceAppointment serviceAppointment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	AnnexureGLCode glCode;
	
	Date workCompletionDate;
	
	String activity;
	
	String workStatus;
	
	double amount;
	double amountWithTax;
	
	Date exicomInvoiceDate;
	String exicomInvoiceNumber;
	
	@Column(length = 2000)
	String remark;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	boolean isDeleted;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getManualId() {
		return manualId;
	}
	public void setManualId(String manualId) {
		this.manualId = manualId;
	}
	public Annexure getAnnexure() {
		return annexure;
	}
	public void setAnnexure(Annexure annexure) {
		this.annexure = annexure;
	}
	public ServiceAppointment getServiceAppointment() {
		return serviceAppointment;
	}
	public void setServiceAppointment(ServiceAppointment serviceAppointment) {
		this.serviceAppointment = serviceAppointment;
	}
	public AnnexureGLCode getGlCode() {
		return glCode;
	}
	public void setGlCode(AnnexureGLCode glCode) {
		this.glCode = glCode;
	}
	public Date getWorkCompletionDate() {
		return workCompletionDate;
	}
	public void setWorkCompletionDate(Date workCompletionDate) {
		this.workCompletionDate = workCompletionDate;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getAmountWithTax() {
		return amountWithTax;
	}
	public void setAmountWithTax(double amountWithTax) {
		this.amountWithTax = amountWithTax;
	}
	public Date getExicomInvoiceDate() {
		return exicomInvoiceDate;
	}
	public void setExicomInvoiceDate(Date exicomInvoiceDate) {
		this.exicomInvoiceDate = exicomInvoiceDate;
	}
	public String getExicomInvoiceNumber() {
		return exicomInvoiceNumber;
	}
	public void setExicomInvoiceNumber(String exicomInvoiceNumber) {
		this.exicomInvoiceNumber = exicomInvoiceNumber;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	

	@javax.persistence.Transient transient AnnexureLineItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(AnnexureLineItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public AnnexureLineItem gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
