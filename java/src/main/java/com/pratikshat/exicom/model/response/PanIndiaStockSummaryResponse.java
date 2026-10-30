package com.pratikshat.exicom.model.response;

public class PanIndiaStockSummaryResponse {
	String productPartCode;
	String pdoductDescription;
	float productPrice;
	String productType;
	float qtyOnHand;
	float qtyOk;
	float qtyFaulty;
	String productFailySubProductName;
	String productFamilyName;
	
	public String getProductPartCode() {
		return productPartCode;
	}
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	public String getPdoductDescription() {
		return pdoductDescription;
	}
	public void setPdoductDescription(String pdoductDescription) {
		this.pdoductDescription = pdoductDescription;
	}
	public float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public float getQtyOnHand() {
		return qtyOnHand;
	}
	public void setQtyOnHand(float qtyOnHand) {
		this.qtyOnHand = qtyOnHand;
	}
	public float getQtyOk() {
		return qtyOk;
	}
	public void setQtyOk(float qtyOk) {
		this.qtyOk = qtyOk;
	}
	public float getQtyFaulty() {
		return qtyFaulty;
	}
	public void setQtyFaulty(float qtyFaulty) {
		this.qtyFaulty = qtyFaulty;
	}
	public String getProductFailySubProductName() {
		return productFailySubProductName;
	}
	public void setProductFailySubProductName(String productFailySubProductName) {
		this.productFailySubProductName = productFailySubProductName;
	}
	public String getProductFamilyName() {
		return productFamilyName;
	}
	public void setProductFamilyName(String productFamilyName) {
		this.productFamilyName = productFamilyName;
	}
	
}
