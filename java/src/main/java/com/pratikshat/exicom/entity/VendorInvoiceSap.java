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
public class VendorInvoiceSap implements java.io.Serializable,OldPresist<VendorInvoiceSap>{
	@Id
	@OneToOne(fetch=FetchType.LAZY)
	VendorInvoiceDetails invoice;
	String sapNumber;
	Date sapDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails lastModifiedBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	Date createdDate;
	Date lastModifiedDate;
	
	public VendorInvoiceSap() {}
	public VendorInvoiceSap(VendorInvoiceDetails invoice) {
		this.invoice=invoice;
	}
	public VendorInvoiceDetails getInvoice() {
		return invoice;
	}
	public void setInvoice(VendorInvoiceDetails invoice) {
		this.invoice = invoice;
	}
	public String getSapNumber() {
		return sapNumber;
	}
	public void setSapNumber(String sapNumber) {
		this.sapNumber = sapNumber;
	}
	public Date getSapDate() {
		return sapDate;
	}
	public void setSapDate(Date sapDate) {
		this.sapDate = sapDate;
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
	
	

	@javax.persistence.Transient transient VendorInvoiceSap old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(VendorInvoiceSap)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public VendorInvoiceSap gOld() {
		return old;
	}
	public String toString() {
			return ""+invoice;
	}
}
