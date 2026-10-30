package com.pratikshat.exicom.model.request;

import java.util.Date;

public class InvoiceUpdateRequest {

	String caseNumber;
	
	Date PODate;
	String PONumber;
	
	String invoiceNumber;
	Date invoiceDate;
	
	Date vendorInvoiceDate;
	String vendorInvoiceNumber;
	
	public Date getPODate() {
		return PODate;
	}
	public void setPODate(Date pODate) {
		PODate = pODate;
	}
	public String getPONumber() {
		return PONumber;
	}
	public void setPONumber(String pONumber) {
		PONumber = pONumber;
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
	public Date getVendorInvoiceDate() {
		return vendorInvoiceDate;
	}
	public void setVendorInvoiceDate(Date vendorInvoiceDate) {
		this.vendorInvoiceDate = vendorInvoiceDate;
	}
	public String getVendorInvoiceNumber() {
		return vendorInvoiceNumber;
	}
	public void setVendorInvoiceNumber(String vendorInvoiceNumber) {
		this.vendorInvoiceNumber = vendorInvoiceNumber;
	}
	public String getCaseNumber() {
		return caseNumber;
	}
	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}
	
}
