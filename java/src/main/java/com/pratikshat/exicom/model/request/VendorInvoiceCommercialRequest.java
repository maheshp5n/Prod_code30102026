package com.pratikshat.exicom.model.request;

import java.util.Date;

public class VendorInvoiceCommercialRequest {
	String grnNumber;
	Date grnDate;
	String entrySheetNumber;
	Long vendorInvoiceId;
	Integer internalServiceCode;
	public String getGrnNumber() {
		return grnNumber;
	}
	public void setGrnNumber(String grnNumber) {
		this.grnNumber = grnNumber;
	}
	public Date getGrnDate() {
		return grnDate;
	}
	public void setGrnDate(Date grnDate) {
		this.grnDate = grnDate;
	}
	public String getEntrySheetNumber() {
		return entrySheetNumber;
	}
	public void setEntrySheetNumber(String entrySheetNumber) {
		this.entrySheetNumber = entrySheetNumber;
	}
	public Long getVendorInvoiceId() {
		return vendorInvoiceId;
	}
	public void setVendorInvoiceId(Long vendorInvoiceId) {
		this.vendorInvoiceId = vendorInvoiceId;
	}
	public Integer getInternalServiceCode() {
		return internalServiceCode;
	}
	public void setInternalServiceCode(Integer internalServiceCode) {
		this.internalServiceCode = internalServiceCode;
	}
	
	
}
