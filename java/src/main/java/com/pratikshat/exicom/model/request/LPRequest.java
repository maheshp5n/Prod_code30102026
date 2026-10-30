package com.pratikshat.exicom.model.request;

public class LPRequest {
	long id;
	String manualId;
	String descirption;
	java.util.Date needByDateTime;
	long sourceLocationId;
	long destinationLocationId;
	String locationProductRequestStatus;
	java.util.Set<LPItemRequest> lileItems;
	
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
	 * @return the descirption
	 */
	public String getDescirption() {
		return descirption;
	}
	/**
	 * @return the needByDateTime
	 */
	public java.util.Date getNeedByDateTime() {
		return needByDateTime;
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
	 * @return the locationProductRequestStatus
	 */
	public String getLocationProductRequestStatus() {
		return locationProductRequestStatus;
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
	 * @param descirption the descirption to set
	 */
	public void setDescirption(String descirption) {
		this.descirption = descirption;
	}
	/**
	 * @param needByDateTime the needByDateTime to set
	 */
	public void setNeedByDateTime(java.util.Date needByDateTime) {
		this.needByDateTime = needByDateTime;
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
	 * @param locationProductRequestStatus the locationProductRequestStatus to set
	 */
	public void setLocationProductRequestStatus(String locationProductRequestStatus) {
		this.locationProductRequestStatus = locationProductRequestStatus;
	}
	/**
	 * @return the lileItems
	 */
	public java.util.Set<LPItemRequest> getLileItems() {
		return lileItems;
	}
	/**
	 * @param lileItems the lileItems to set
	 */
	public void setLileItems(java.util.Set<LPItemRequest> lileItems) {
		this.lileItems = lileItems;
	}
	
}
