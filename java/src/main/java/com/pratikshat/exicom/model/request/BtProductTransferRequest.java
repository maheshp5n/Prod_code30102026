package com.pratikshat.exicom.model.request;

public class BtProductTransferRequest {
	long btProductTransferId;
	long btProductRequestId;
	String shippingToName;
	String trackingNumber;
	String shippingMode;
	java.util.Date actualDeliveryDate;
	java.util.Set<BtProductTransferItemRequest> items;
	
	/**
	 * @return the btProductTransferId
	 */
	public long getBtProductTransferId() {
		return btProductTransferId;
	}
	/**
	 * @return the btProductRequestId
	 */
	public long getBtProductRequestId() {
		return btProductRequestId;
	}
	/**
	 * @return the shippingToName
	 */
	public String getShippingToName() {
		return shippingToName;
	}
	/**
	 * @return the trackingNumber
	 */
	public String getTrackingNumber() {
		return trackingNumber;
	}
	/**
	 * @return the shippingMode
	 */
	public String getShippingMode() {
		return shippingMode;
	}
	/**
	 * @return the actualDeliveryDate
	 */
	public java.util.Date getActualDeliveryDate() {
		return actualDeliveryDate;
	}
	/**
	 * @param btProductTransferId the btProductTransferId to set
	 */
	public void setBtProductTransferId(long btProductTransferId) {
		this.btProductTransferId = btProductTransferId;
	}
	/**
	 * @param btProductRequestId the btProductRequestId to set
	 */
	public void setBtProductRequestId(long btProductRequestId) {
		this.btProductRequestId = btProductRequestId;
	}
	/**
	 * @param shippingToName the shippingToName to set
	 */
	public void setShippingToName(String shippingToName) {
		this.shippingToName = shippingToName;
	}
	/**
	 * @param trackingNumber the trackingNumber to set
	 */
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	/**
	 * @param shippingMode the shippingMode to set
	 */
	public void setShippingMode(String shippingMode) {
		this.shippingMode = shippingMode;
	}
	/**
	 * @param actualDeliveryDate the actualDeliveryDate to set
	 */
	public void setActualDeliveryDate(java.util.Date actualDeliveryDate) {
		this.actualDeliveryDate = actualDeliveryDate;
	}
	/**
	 * @return the items
	 */
	public java.util.Set<BtProductTransferItemRequest> getItems() {
		return items;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(java.util.Set<BtProductTransferItemRequest> items) {
		this.items = items;
	}
	
}
