package com.pratikshat.exicom.model.request;

public class LPReturnOrderRequest {
	long lprOrderid;
	long sourceLocationId;
	long destinationLocationId;
	float totalQuantityReturned;
	String shipToName;
	String shippingMode;
	String trackingNumber;
	java.util.Date expectedArrivalDate;
	
	java.util.Set<LPReturnOrderItemRequest> items;
	
	String lprOrderidManual;
	
	/**
	 * @return the lprOrderid
	 */
	public long getLprOrderid() {
		return lprOrderid;
	}
	/**
	 * @return the sourceLocationId
	 */
	public long getSourceLocationId() {
		return sourceLocationId;
	}
	/**
	 * @return the destinationLocationId
	 */
	public long getDestinationLocationId() {
		return destinationLocationId;
	}
	/**
	 * @return the totalQuantityReturned
	 */
	public float getTotalQuantityReturned() {
		return totalQuantityReturned;
	}
	/**
	 * @return the shipToName
	 */
	public String getShipToName() {
		return shipToName;
	}
	/**
	 * @return the shippingMode
	 */
	public String getShippingMode() {
		return shippingMode;
	}
	/**
	 * @return the trackingNumber
	 */
	public String getTrackingNumber() {
		return trackingNumber;
	}
	/**
	 * @return the expectedArrivalDate
	 */
	public java.util.Date getExpectedArrivalDate() {
		return expectedArrivalDate;
	}
	/**
	 * @param lprOrderid the lprOrderid to set
	 */
	public void setLprOrderid(long lprOrderid) {
		this.lprOrderid = lprOrderid;
	}
	/**
	 * @param sourceLocationId the sourceLocationId to set
	 */
	public void setSourceLocationId(long sourceLocationId) {
		this.sourceLocationId = sourceLocationId;
	}
	/**
	 * @param destinationLocationId the destinationLocationId to set
	 */
	public void setDestinationLocationId(long destinationLocationId) {
		this.destinationLocationId = destinationLocationId;
	}
	/**
	 * @param totalQuantityReturned the totalQuantityReturned to set
	 */
	public void setTotalQuantityReturned(float totalQuantityReturned) {
		this.totalQuantityReturned = totalQuantityReturned;
	}
	/**
	 * @param shipToName the shipToName to set
	 */
	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}
	/**
	 * @param shippingMode the shippingMode to set
	 */
	public void setShippingMode(String shippingMode) {
		this.shippingMode = shippingMode;
	}
	/**
	 * @param trackingNumber the trackingNumber to set
	 */
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	/**
	 * @param expectedArrivalDate the expectedArrivalDate to set
	 */
	public void setExpectedArrivalDate(java.util.Date expectedArrivalDate) {
		this.expectedArrivalDate = expectedArrivalDate;
	}
	/**
	 * @return the items
	 */
	public java.util.Set<LPReturnOrderItemRequest> getItems() {
		return items;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(java.util.Set<LPReturnOrderItemRequest> items) {
		this.items = items;
	}
	public String getLprOrderidManual() {
		return lprOrderidManual;
	}
	public void setLprOrderidManual(String lprOrderidManual) {
		this.lprOrderidManual = lprOrderidManual;
	}
	
}
