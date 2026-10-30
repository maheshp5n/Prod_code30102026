package com.pratikshat.exicom.model.request;

public class LocationRequest {
	long locationId;
	String locationName;
	String ownerEmailId;
	long parentLocationId;
	boolean mobileLocation;
	int locationTypeId;
	long legalEntityId;
	boolean inventoryLocation;
	String plant;
	
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
	 * @return the ownerEmailId
	 */
	public String getOwnerEmailId() {
		return ownerEmailId;
	}
	/**
	 * @return the parentLocationId
	 */
	public long getParentLocationId() {
		return parentLocationId;
	}
	/**
	 * @return the mobileLocation
	 */
	public boolean isMobileLocation() {
		return mobileLocation;
	}
	/**
	
	/**
	 * @return the legalEntityId
	 */
	public long getLegalEntityId() {
		return legalEntityId;
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
	/**
	 * @param ownerEmailId the ownerEmailId to set
	 */
	public void setOwnerEmailId(String ownerEmailId) {
		this.ownerEmailId = ownerEmailId;
	}
	/**
	 * @param parentLocationId the parentLocationId to set
	 */
	public void setParentLocationId(long parentLocationId) {
		this.parentLocationId = parentLocationId;
	}
	/**
	 * @param mobileLocation the mobileLocation to set
	 */
	public void setMobileLocation(boolean mobileLocation) {
		this.mobileLocation = mobileLocation;
	}
	
	/**
	 * @param legalEntityId the legalEntityId to set
	 */
	public void setLegalEntityId(long legalEntityId) {
		this.legalEntityId = legalEntityId;
	}
	/**
	 * @return the inventoryLocation
	 */
	public boolean isInventoryLocation() {
		return inventoryLocation;
	}
	/**
	 * @param inventoryLocation the inventoryLocation to set
	 */
	public void setInventoryLocation(boolean inventoryLocation) {
		this.inventoryLocation = inventoryLocation;
	}

	public int getLocationTypeId() {
		return locationTypeId;
	}

	public void setLocationTypeId(int locationTypeId) {
		this.locationTypeId = locationTypeId;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}
	
}
