package com.pratikshat.exicom.model.request;

public class MSLRequestRequest {

	long mslRequestId;
	long locationId;
	long statusId;
	
	java.util.List<MSLItemRequestRequest> itemRequest;
	
	public void setWarehouseId(long locationId) {
		this.locationId = locationId;
	}
	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	
	public long getMslRequestId() {
		return mslRequestId;
	}

	public void setMslRequestId(long mslRequestId) {
		this.mslRequestId = mslRequestId;
	}
	/**
	 * @return the locationId
	 */
	public long getLocationId() {
		return locationId;
	}
	/**
	 * @return the itemRequest
	 */
	public java.util.List<MSLItemRequestRequest> getItemRequest() {
		return itemRequest;
	}
	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	/**
	 * @param itemRequest the itemRequest to set
	 */
	public void setItemRequest(java.util.List<MSLItemRequestRequest> itemRequest) {
		this.itemRequest = itemRequest;
	}
	
}
