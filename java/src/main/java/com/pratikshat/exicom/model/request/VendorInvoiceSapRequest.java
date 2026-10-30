package com.pratikshat.exicom.model.request;

import java.util.Date;

public class VendorInvoiceSapRequest {
	Long vendorInvoiceId;
	String sapNumber;
	Date sapDate;
	public Long getVendorInvoiceId() {
		return vendorInvoiceId;
	}
	public void setVendorInvoiceId(Long vendorInvoiceId) {
		this.vendorInvoiceId = vendorInvoiceId;
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
	
}
