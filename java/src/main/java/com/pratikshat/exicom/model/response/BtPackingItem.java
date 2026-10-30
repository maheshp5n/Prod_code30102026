package com.pratikshat.exicom.model.response;

public class BtPackingItem {
    String partCode;
    String description;
    String productType;
    String productStatus;
    Float quantity;
	String serialNumber;

	public BtPackingItem(String partCode, String description, String productType, String productStatus,
			Float quantity) {
		this.partCode = partCode;
		this.description = description;
		this.productType = productType;
		this.productStatus = productStatus;
		this.quantity = quantity;
	}
	public BtPackingItem() {
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public Float getQuantity() {
		return quantity;
	}
	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	
    
}
