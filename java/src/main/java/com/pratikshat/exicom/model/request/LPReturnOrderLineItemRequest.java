package com.pratikshat.exicom.model.request;

public class LPReturnOrderLineItemRequest {
	long lproLineitemId;
	long returnOrderItemId;
	String productLineItemSerialNumber;
	float quantityReturned;
	
	/**
	 * @return the lproLineitemId
	 */
	public long getLproLineitemId() {
		return lproLineitemId;
	}
	/**
	 * @return the returnOrderItemId
	 */
	public long getReturnOrderItemId() {
		return returnOrderItemId;
	}
	/**
	 * @return the productLineItemSerialNumber
	 */
	public String getProductLineItemSerialNumber() {
		return productLineItemSerialNumber;
	}
	/**
	 * @return the quantityReturned
	 */
	public float getQuantityReturned() {
		return quantityReturned;
	}
	/**
	 * @param lproLineitemId the lproLineitemId to set
	 */
	public void setLproLineitemId(long lproLineitemId) {
		this.lproLineitemId = lproLineitemId;
	}
	/**
	 * @param returnOrderItemId the returnOrderItemId to set
	 */
	public void setReturnOrderItemId(long returnOrderItemId) {
		this.returnOrderItemId = returnOrderItemId;
	}
	/**
	 * @param productLineItemSerialNumber the productLineItemSerialNumber to set
	 */
	public void setProductLineItemSerialNumber(String productLineItemSerialNumber) {
		this.productLineItemSerialNumber = productLineItemSerialNumber;
	}
	/**
	 * @param quantityReturned the quantityReturned to set
	 */
	public void setQuantityReturned(float quantityReturned) {
		this.quantityReturned = quantityReturned;
	}
	
}
