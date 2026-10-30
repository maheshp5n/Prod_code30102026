package com.pratikshat.exicom.model.response;

public class ProductsForList {
	String productDescription;
	String productPartCode;
	public ProductsForList(String productDescription,String productPartCode) {
		this.productDescription=productDescription;
		this.productPartCode=productPartCode;
	}
	/**
	 * @return the productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}
	/**
	 * @return the productPartCode
	 */
	public String getProductPartCode() {
		return productPartCode;
	}
	/**
	 * @param productDescription the productDescription to set
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	/**
	 * @param productPartCode the productPartCode to set
	 */
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	
}
