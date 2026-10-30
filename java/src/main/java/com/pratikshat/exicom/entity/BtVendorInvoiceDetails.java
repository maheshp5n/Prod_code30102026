package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
@EntityListeners({ObjectAuditListener.class, AuditingEntityListener.class})
public class BtVendorInvoiceDetails extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtVendorInvoiceDetails>{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String manualId;
	float invoiceAmount;
	String vendorName;
	String vendorId;
	String vendorInvoiceNumber;
	Date vendorInvoiceDate;
	@ManyToOne(fetch=FetchType.LAZY)
	BtVendorPoDetails vendorPo;
	
	String invoiceCopyUrl;
	
	@ManyToOne(fetch=FetchType.LAZY)
	BtWccDetails wccDetails;

	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	VendorInvoiceStatus invoiceStatus;
	@Column(length = 500)
	String remarks;
	Date poDate;
	float invoiceTaxAmount;
	float invoiceTotalAmount;
	
	
	
	public BtVendorInvoiceDetails(Long id) {
		this.id = id;
	}
	public BtVendorInvoiceDetails() {
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
	public String getInvoiceCopyUrl() {
		return invoiceCopyUrl;
	}
	public void setInvoiceCopyUrl(String invoiceCopyUrl) {
		this.invoiceCopyUrl = invoiceCopyUrl;
	}	
	public BtVendorPoDetails getVendorPo() {
		return vendorPo;
	}
	public void setVendorPo(BtVendorPoDetails vendorPo) {
		this.vendorPo = vendorPo;
	}
	public BtWccDetails getWccDetails() {
		return wccDetails;
	}
	public void setWccDetails(BtWccDetails wccDetails) {
		this.wccDetails = wccDetails;
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
	@Transient transient BtVendorInvoiceDetails old;
	
	@Override
	@PostLoad
	public void old() {
		old = (BtVendorInvoiceDetails) SerializationUtils.clone(this);	
	}
	@Override
	public BtVendorInvoiceDetails gOld() {
		// TODO Auto-generated method stub
		return this.old;
	}
	@Override
	public Long getId() {
		
		return this.id;
	}
	@Override
	public void setId(Long id) {
		this.id =id;
		
	}
	@Override
	public EmployeeDetails getOwner() {
		// TODO Auto-generated method stub
		return this.owner;
	}
	@Override
	public void setOwner(EmployeeDetails u) {
		this.owner = u;
		
	}
	@Override
	public String getManualId() {
		
		return this.manualId;
	}
	@Override
	public void setManualId(String manualId) {
		this.manualId = manualId;
		
	}
}
