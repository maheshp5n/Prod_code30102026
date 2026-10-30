package com.pratikshat.exicom.model.request;

import java.util.List;

public class DctRepairConsumedItemsRequest {
	long locationId;
	float qty;
	
	List<String> serialNumber;
	
	public long getLocationId() {
		return locationId;
	}
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	public float getQty() {
		return qty;
	}
	public void setQty(float qty) {
		this.qty = qty;
	}
	public List<String> getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(List<String> serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	
}
