package com.pratikshat.exicom.model.request;

public class BtProductRequestRequest {
	long id;
	String manualId;
	String description;
	java.util.Date needByDateTime;
	long sourceLocationId;
	long destinationLocationId;
	String btProductRequestStatus;
	java.util.Set<BtProductRequestItemRequest> items;
	
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
	 * @return the btProductRequestStatus
	 */
	public String getBtProductRequestStatus() {
		return btProductRequestStatus;
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
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @param btProductRequestStatus the btProductRequestStatus to set
	 */
	public void setBtProductRequestStatus(String btProductRequestStatus) {
		this.btProductRequestStatus = btProductRequestStatus;
	}
	/**
	 * @return the items
	 */
	public java.util.Set<BtProductRequestItemRequest> getitems() {
		return items;
	}
	/**
	 * @param items the items to set
	 */
	public void setitems(java.util.Set<BtProductRequestItemRequest> items) {
		this.items = items;
	}
	
}
