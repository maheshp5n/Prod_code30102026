package com.pratikshat.exicom.model.request;

import java.util.List;

public class BillOfMaterialRequest {
	Long id;
	String productPartCode;
	float requiredQuantity;
	List<String> locationName;
	List<String> drawaingRef;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductPartCode() {
		return productPartCode;
	}
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	public float getRequiredQuantity() {
		return requiredQuantity;
	}
	public void setRequiredQuantity(float requiredQuantity) {
		this.requiredQuantity = requiredQuantity;
	}
	public List<String> getLocationName() {
		return locationName;
	}
	public void setLocationName(List<String> locationName) {
		this.locationName = locationName;
	}
	public List<String> getDrawaingRef() {
		return drawaingRef;
	}
	public void setDrawaingRef(List<String> drawaingRef) {
		this.drawaingRef = drawaingRef;
	}
	
	
}
