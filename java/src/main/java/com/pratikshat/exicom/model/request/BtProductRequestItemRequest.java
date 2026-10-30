package com.pratikshat.exicom.model.request;

public class BtProductRequestItemRequest {
	long id;
	String manualId;
	String productPartCode;
	long btprId;
	float requestQuantity;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the manualId
	 */
	public String getManualId() {
		return manualId;
	}
	/**
	 * @return the productPartCode
	 */
	public String getProductPartCode() {
		return productPartCode;
	}
	/**
	 * @return the btprId
	 */
	public long getBtprId() {
		return btprId;
	}
	/**
	 * @return the requestQuantity
	 */
	public float getRequestQuantity() {
		return requestQuantity;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param manualId the manualId to set
	 */
	public void setManualId(String manualId) {
		this.manualId = manualId;
	}
	/**
	 * @param productPartCode the productPartCode to set
	 */
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	/**
	 * @param btprId the btprId to set
	 */
	public void setBtprId(long btprId) {
		this.btprId =btprId;
	}
	/**
	 * @param requestQuantity the requestQuantity to set
	 */
	public void setRequestQuantity(float requestQuantity) {
		this.requestQuantity = requestQuantity;
	}
	
	
}
 