package com.pratikshat.exicom.model.response;

public class LocationFaultyItemResponse {
	long locationId;
	long locationItemId;
	String locationItemManualId;
	String partCode;
	String partDescription;
	float faultyQuantity;
	java.util.List<LocationFaultyLineItemResponse> lineItems;
	public LocationFaultyItemResponse() {}
	public LocationFaultyItemResponse(com.pratikshat.exicom.entity.LocationProductItem item) {
		locationItemId=item.getId();
		this.locationItemManualId=item.getLocationProductItemId();
		if(item.getProduct()!=null) {
			this.partCode=item.getProduct().getProductPartCode();
			this.partDescription=item.getProduct().getPdoductDescription();	
		}
		this.faultyQuantity=item.getFaultyQuantity();
		if(item.getLocation()!=null) {
			item.getLocation().getLocationId();
		}
	}
	/**
	 * @return the locationId
	 */
	public long getLocationId() {
		return locationId;
	}
	/**
	 * @return the locationItemId
	 */
	public long getLocationItemId() {
		return locationItemId;
	}
	/**
	 * @return the locationItemManualId
	 */
	public String getLocationItemManualId() {
		return locationItemManualId;
	}
	/**
	 * @return the partCode
	 */
	public String getPartCode() {
		return partCode;
	}
	/**
	 * @return the partDescription
	 */
	public String getPartDescription() {
		return partDescription;
	}
	/**
	 * @return the faultyQuantity
	 */
	public float getFaultyQuantity() {
		return faultyQuantity;
	}
	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	/**
	 * @param locationItemId the locationItemId to set
	 */
	public void setLocationItemId(long locationItemId) {
		this.locationItemId = locationItemId;
	}
	/**
	 * @param locationItemManualId the locationItemManualId to set
	 */
	public void setLocationItemManualId(String locationItemManualId) {
		this.locationItemManualId = locationItemManualId;
	}
	/**
	 * @param partCode the partCode to set
	 */
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	/**
	 * @param partDescription the partDescription to set
	 */
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	/**
	 * @param faultyQuantity the faultyQuantity to set
	 */
	public void setFaultyQuantity(float faultyQuantity) {
		this.faultyQuantity = faultyQuantity;
	}
	/**
	 * @return the lineItems
	 */
	public java.util.List<LocationFaultyLineItemResponse> getLineItems() {
		return lineItems;
	}
	/**
	 * @param lineItems the lineItems to set
	 */
	public void setLineItems(java.util.List<LocationFaultyLineItemResponse> lineItems) {
		this.lineItems = lineItems;
	}
	
}
