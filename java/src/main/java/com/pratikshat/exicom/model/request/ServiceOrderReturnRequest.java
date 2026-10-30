package com.pratikshat.exicom.model.request;

import java.util.Set;

public class ServiceOrderReturnRequest {
	Long id;
	String manualId;
	String shipToName;
	String shippingMode;
	float totalItem;
	float totalReceive;
	String trackingNumber;
	
	Set<ServiceOrderReturnRequestItem> items;
	
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
	public String getShipToName() {
		return shipToName;
	}
	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}
	public String getShippingMode() {
		return shippingMode;
	}
	public void setShippingMode(String shippingMode) {
		this.shippingMode = shippingMode;
	}
	public float getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(float totalItem) {
		this.totalItem = totalItem;
	}
	public float getTotalReceive() {
		return totalReceive;
	}
	public void setTotalReceive(float totalReceive) {
		this.totalReceive = totalReceive;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public Set<ServiceOrderReturnRequestItem> getItems() {
		return items;
	}
	public void setItems(Set<ServiceOrderReturnRequestItem> items) {
		this.items = items;
	}
	
	
	
}
