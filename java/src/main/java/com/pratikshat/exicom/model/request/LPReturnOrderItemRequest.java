package com.pratikshat.exicom.model.request;

public class LPReturnOrderItemRequest {
	long returnOrderItemId;
	long lpReturnOderId;
	long productItemId;
	String productPartCode;
	java.util.Date returnDate;
	float totalReturnQuantity;
	java.util.Set<LPReturnOrderLineItemRequest> lineItems;
	
	/**
	 * @return the returnOrderItemId
	 */
	public long getReturnOrderItemId() {
		return returnOrderItemId;
	}
	/**
	 * @return the lpReturnOderId
	 */
	public long getLpReturnOderId() {
		return lpReturnOderId;
	}
	/**
	 * @return the productItemId
	 */
	public long getProductItemId() {
		return productItemId;
	}
	/**
	 * @return the productPartCode
	 */
	public String getProductPartCode() {
		return productPartCode;
	}
	/**
	 * @return the returnDate
	 */
	public java.util.Date getReturnDate() {
		return returnDate;
	}
	/**
	 * @return the totalReturnQuantity
	 */
	public float getTotalReturnQuantity() {
		return totalReturnQuantity;
	}
	/**
	 * @param returnOrderItemId the returnOrderItemId to set
	 */
	public void setReturnOrderItemId(long returnOrderItemId) {
		this.returnOrderItemId = returnOrderItemId;
	}
	/**
	 * @param lpReturnOderId the lpReturnOderId to set
	 */
	public void setLpReturnOderId(long lpReturnOderId) {
		this.lpReturnOderId = lpReturnOderId;
	}
	/**
	 * @param productItemId the productItemId to set
	 */
	public void setProductItemId(long productItemId) {
		this.productItemId = productItemId;
	}
	/**
	 * @param productPartCode the productPartCode to set
	 */
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	/**
	 * @param returnDate the returnDate to set
	 */
	public void setReturnDate(java.util.Date returnDate) {
		this.returnDate = returnDate;
	}
	/**
	 * @param totalReturnQuantity the totalReturnQuantity to set
	 */
	public void setTotalReturnQuantity(float totalReturnQuantity) {
		this.totalReturnQuantity = totalReturnQuantity;
	}
	/**
	 * @return the lineItems
	 */
	public java.util.Set<LPReturnOrderLineItemRequest> getLineItems() {
		return lineItems;
	}
	/**
	 * @param lineItems the lineItems to set
	 */
	public void setLineItems(java.util.Set<LPReturnOrderLineItemRequest> lineItems) {
		this.lineItems = lineItems;
	}
	
}
