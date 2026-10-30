package com.pratikshat.exicom.model.response;

public class LocationForList {
	private long locationId;
	private String locationName;
	public LocationForList() {}
	public LocationForList(long locationId,String locationName) {
		this.locationId=locationId;
		this.locationName=locationName;
	}
	/**
	 * @return the locationId
	 */
	public long getLocationId() {
		return locationId;
	}
	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}
	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
}
