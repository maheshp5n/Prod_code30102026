package com.pratikshat.exicom.model.request;

public class Products {
	String partCode;
	String productDescription;
	long subProductFamilyId;
	String type;
	float price;
	/**
	 * @return the partCode
	 */
	public String getPartCode() {
		return partCode;
	}
	/**
	 * @return the productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}
	/**
	 * @return the subProductFamilyId
	 */
	public long getSubProductFamilyId() {
		return subProductFamilyId;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}
	/**
	 * @param partCode the partCode to set
	 */
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	/**
	 * @param productDescription the productDescription to set
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	/**
	 * @param subProductFamilyId the subProductFamilyId to set
	 */
	public void setSubProductFamilyId(long subProductFamilyId) {
		this.subProductFamilyId = subProductFamilyId;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	
}
