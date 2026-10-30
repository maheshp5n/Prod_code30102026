package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class VendorInvoiceGrn implements java.io.Serializable,OldPresist<VendorInvoiceGrn>{
	@Id
	@OneToOne(fetch=FetchType.LAZY)
	VendorInvoiceDetails invoice;
	
	String grnNumber;
	String grnDate;
	String entrySheetNumber;
	
	@ManyToOne(fetch=FetchType.LAZY)
	InternalServiceCode internalServiceCode;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails lastModifiedBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	Date createdDate;
	
	Date lastModifiedDate;
	
	public VendorInvoiceGrn() {}
	public VendorInvoiceGrn(VendorInvoiceDetails invoice) {
		this.invoice=invoice;
	}
	public VendorInvoiceDetails getInvoice() {
		return invoice;
	}
	public void setInvoice(VendorInvoiceDetails invoice) {
		this.invoice = invoice;
	}
	public String getGrnNumber() {
		return grnNumber;
	}
	public void setGrnNumber(String grnNumber) {
		this.grnNumber = grnNumber;
	}
	public String getGrnDate() {
		return grnDate;
	}
	public void setGrnDate(String grnDate) {
		this.grnDate = grnDate;
	}
	public String getEntrySheetNumber() {
		return entrySheetNumber;
	}
	public void setEntrySheetNumber(String entrySheetNumber) {
		this.entrySheetNumber = entrySheetNumber;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public EmployeeDetails getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(EmployeeDetails lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public EmployeeDetails getOwner() {
		return owner;
	}
	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
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
	public InternalServiceCode getInternalServiceCode() {
		return internalServiceCode;
	}
	public void setInternalServiceCode(InternalServiceCode internalServiceCode) {
		this.internalServiceCode = internalServiceCode;
	}
	
	
	

	@javax.persistence.Transient transient VendorInvoiceGrn old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(VendorInvoiceGrn)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public VendorInvoiceGrn gOld() {
		return old;
	}
	public String toString() {
			return ""+invoice;
	}
}
