package com.pratikshat.exicom.model.response;

import com.pratikshat.exicom.entity.DctRepairItemConsumed;

public class DctRepairItemConsumedResponse {

	long id;
	String partcode;
	String productDescription;
	
	String location;
	float quantity;
	double repairAmount;
	
	public DctRepairItemConsumedResponse() {
		
	}

	public DctRepairItemConsumedResponse(DctRepairItemConsumed dctRepairItemConsumed) {
		
		this.id = dctRepairItemConsumed.getId();
		
		if(dctRepairItemConsumed.getProduct()!=null) {
			this.partcode = dctRepairItemConsumed.getProduct().getProductPartCode();
			this.productDescription = dctRepairItemConsumed.getProduct().getPdoductDescription();	
		}
		
		this.location = dctRepairItemConsumed.getLocation();
		this.quantity = dctRepairItemConsumed.getQuantity();
		this.repairAmount = dctRepairItemConsumed.getRepairAmount();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPartcode() {
		return partcode;
	}

	public void setPartcode(String partcode) {
		this.partcode = partcode;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public double getRepairAmount() {
		return repairAmount;
	}

	public void setRepairAmount(double repairAmount) {
		this.repairAmount = repairAmount;
	}
	
	
}
