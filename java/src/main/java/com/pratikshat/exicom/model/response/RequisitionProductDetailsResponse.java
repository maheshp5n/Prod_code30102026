package com.pratikshat.exicom.model.response;

import com.pratikshat.exicom.entity.RequisitionProductDetails;

public class RequisitionProductDetailsResponse {

	String productPartCode;
	String proudctDescription;
	
	float quantity;
	
	

	public RequisitionProductDetailsResponse(RequisitionProductDetails requisitionProductDetails) {
		if(requisitionProductDetails.getProducts()!=null) {
			this.productPartCode = requisitionProductDetails.getProducts().getProductPartCode();
			this.proudctDescription = requisitionProductDetails.getProducts().getPdoductDescription();
		}
		this.quantity = requisitionProductDetails.getQuantitty();
		
	}

	public RequisitionProductDetailsResponse() {
		
	}

	public String getProductPartCode() {
		return productPartCode;
	}

	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}

	public String getProudctDescription() {
		return proudctDescription;
	}

	public void setProudctDescription(String proudctDescription) {
		this.proudctDescription = proudctDescription;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	
	
}
