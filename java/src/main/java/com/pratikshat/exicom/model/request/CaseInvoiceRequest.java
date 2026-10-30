package com.pratikshat.exicom.model.request;

import java.util.Date;

public class CaseInvoiceRequest {

	long caseId;
	
	String poNumber;
	Date poDate;
	String invoiceNumber;
	Date invoiceDate;
	String vendorInvoiceNumber;
	Date vendorInvoiceDate;
	
	
	public long getCaseId() {
		return caseId;
	}
	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}
	
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public Date getPoDate() {
		return poDate;
	}
	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
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
	
	
}
