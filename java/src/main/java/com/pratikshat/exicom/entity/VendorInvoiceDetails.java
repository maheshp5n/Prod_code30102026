package com.pratikshat.exicom.entity;

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
public class VendorInvoiceDetails implements java.io.Serializable,OldPresist<VendorInvoiceDetails>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String manualId;
	Date createdDate;
	float invoiceAmount;
	String vendorName;
	String vendorId;
	String vendorInvoiceNumber;
	Date vendorInvoiceDate;
	@ManyToOne(fetch=FetchType.LAZY)
	VendorPoDetails vendorPo;
	
	String invoiceCopyUrl;
	
	@ManyToOne(fetch=FetchType.LAZY)
	WccDetails wccDetails;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails lastModifiedBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	Date lastModifiedDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	VendorInvoiceStatus invoiceStatus;
	@Column(length = 500)
	String remarks;
	Date poDate;
	float invoiceTaxAmount;
	float invoiceTotalAmount;
	
	public VendorInvoiceDetails() {}
	public VendorInvoiceDetails(Long id) {
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public float getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(float invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorInvoiceNumber() {
		return vendorInvoiceNumber;
	}

	public void setVendorInvoiceNumber(String vendorInvoiceNumber) {
		this.vendorInvoiceNumber = vendorInvoiceNumber;
	}

	public Date getVendorInvoiceDate() {
		return vendorInvoiceDate;
	}

	public void setVendorInvoiceDate(Date vendorInvoiceDate) {
		this.vendorInvoiceDate = vendorInvoiceDate;
	}

	public VendorPoDetails getVendorPo() {
		return vendorPo;
	}

	public void setVendorPo(VendorPoDetails vendorPo) {
		this.vendorPo = vendorPo;
	}


	public WccDetails getWccDetails() {
		return wccDetails;
	}

	public void setWccDetails(WccDetails wccDetails) {
		this.wccDetails = wccDetails;
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

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public VendorInvoiceStatus getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(VendorInvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getPoDate() {
		return poDate;
	}
	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}
	public String getInvoiceCopyUrl() {
		return invoiceCopyUrl;
	}
	public void setInvoiceCopyUrl(String invoiceCopyUrl) {
		this.invoiceCopyUrl = invoiceCopyUrl;
	}
	public float getInvoiceTaxAmount() {
		return invoiceTaxAmount;
	}
	public void setInvoiceTaxAmount(float invoiceTaxAmount) {
		this.invoiceTaxAmount = invoiceTaxAmount;
	}
	public float getInvoiceTotalAmount() {
		return invoiceTotalAmount;
	}
	public void setInvoiceTotalAmount(float invoiceTotalAmount) {
		this.invoiceTotalAmount = invoiceTotalAmount;
	}
	

	@javax.persistence.Transient transient VendorInvoiceDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(VendorInvoiceDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public VendorInvoiceDetails gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
