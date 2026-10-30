package com.pratikshat.exicom.model.request;

public class LPItemRequest {
	long id;
	String manualId;
	String productPartCode;
	long lprId;
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
	 * @return the lprId
	 */
	public long getLprId() {
		return lprId;
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
	 * @param lprId the lprId to set
	 */
	public void setLprId(long lprId) {
		this.lprId = lprId;
	}
	/**
	 * @param requestQuantity the requestQuantity to set
	 */
	public void setRequestQuantity(float requestQuantity) {
		this.requestQuantity = requestQuantity;
	}
	
	
}
