package com.pratikshat.exicom.model.request;

import java.util.Date;
import java.util.Set;

public class MrnRequest {
	Long id;
	String manualId;
	String accountSapNumber;
	Date mrnDate;
	String poReferenceNumber;
	String vendorInvoiceNumber;
	String vendorChallanNumber;
	String transporter;
	String vehicleNumber;
	long warehouseId;
	
	Set<MrnItemsRequest> items;

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

	public String getAccountSapNumber() {
		return accountSapNumber;
	}

	public void setAccountSapNumber(String accountSapNumber) {
		this.accountSapNumber = accountSapNumber;
	}

	public Date getMrnDate() {
		return mrnDate;
	}

	public void setMrnDate(Date mrnDate) {
		this.mrnDate = mrnDate;
	}

	public String getPoReferenceNumber() {
		return poReferenceNumber;
	}

	public void setPoReferenceNumber(String poReferenceNumber) {
		this.poReferenceNumber = poReferenceNumber;
	}

	public String getVendorInvoiceNumber() {
		return vendorInvoiceNumber;
	}

	public void setVendorInvoiceNumber(String vendorInvoiceNumber) {
		this.vendorInvoiceNumber = vendorInvoiceNumber;
	}

	public String getVendorChallanNumber() {
		return vendorChallanNumber;
	}

	public void setVendorChallanNumber(String vendorChallanNumber) {
		this.vendorChallanNumber = vendorChallanNumber;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Set<MrnItemsRequest> getItems() {
		return items;
	}

	public void setItems(Set<MrnItemsRequest> items) {
		this.items = items;
	}
	
	
	
}
