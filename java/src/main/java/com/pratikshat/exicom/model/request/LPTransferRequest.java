package com.pratikshat.exicom.model.request;

public class LPTransferRequest {
	long locationProductTransferId;
	long locationProductRequestId;
	String shippingToName;
	String trackingNumber;
	String shippingMode;
	java.util.Date actualDeliveryDate;
	java.util.Set<LPTransferItemRequest> items;
	
	/**
	 * @return the locationProductTransferId
	 */
	public long getLocationProductTransferId() {
		return locationProductTransferId;
	}
	/**
	 * @return the locationProductRequestId
	 */
	public long getLocationProductRequestId() {
		return locationProductRequestId;
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
	 * @param locationProductTransferId the locationProductTransferId to set
	 */
	public void setLocationProductTransferId(long locationProductTransferId) {
		this.locationProductTransferId = locationProductTransferId;
	}
	/**
	 * @param locationProductRequestId the locationProductRequestId to set
	 */
	public void setLocationProductRequestId(long locationProductRequestId) {
		this.locationProductRequestId = locationProductRequestId;
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
	public java.util.Set<LPTransferItemRequest> getItems() {
		return items;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(java.util.Set<LPTransferItemRequest> items) {
		this.items = items;
	}
	
}
