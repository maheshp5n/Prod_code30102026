package com.pratikshat.exicom.model.request;

import java.util.Date;

public class VendorInvoiceRequest {
	Long id;
	String manualId;
	Long wccId;
	String vendorInvoiceNumber;
	Date vendorInvoiceDate;
	Long vendorPoId;
	String remarks;
	
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
	public Long getWccId() {
		return wccId;
	}
	public void setWccId(Long wccId) {
		this.wccId = wccId;
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
	public Long getVendorPoId() {
		return vendorPoId;
	}
	public void setVendorPoId(Long vendorPoId) {
		this.vendorPoId = vendorPoId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
