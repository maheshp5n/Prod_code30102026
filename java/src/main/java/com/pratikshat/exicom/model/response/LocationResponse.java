package com.pratikshat.exicom.model.response;

import java.util.Date;

public class LocationResponse {
	private long locationId;
	
	private String locationName;
	private String ownerEmailId;
	private String ownerFirstName;
	private String ownerMiddleName;
	private String ownerLastName;
	private long parentLocationId;
	private String parentLocationName;
	private boolean mobileLocation;
	private int locationTypeId;
	private String locationTypeName;
	private boolean inventoryLocation;
	private long legalEntityId;
	private String legalEntityName;
	
	private String createdByEmailId;
	private String createdByFirstName;
	private String createdByMiddleName;
	private String createdByLastName;
	private Date createdDate;
	private String modifiedByEmailId;
	private String modifiedByFirstName;
	private String modifiedByMiddleName;
	private String modifiedByLastName;
	private Date modifiedDate;
	private String plant;
	
	public LocationResponse() {}
	public LocationResponse(com.pratikshat.exicom.entity.Location location) {
		this.locationId=location.getLocationId();
		this.locationName=location.getLocationName();
		this.plant = location.getPlant();
		if(location.getOwner()!=null) {
			this.ownerEmailId=location.getOwner().getEmailId();
			this.ownerFirstName=location.getOwner().getFirstName();
			this.ownerMiddleName=location.getOwner().getMiddleName();
			this.ownerLastName=location.getOwner().getLastName();
			
		}
		if(location.getParentLocation()!=null) {
			this.parentLocationId=location.getParentLocation().getLocationId();
			this.parentLocationName=location.getParentLocation().getLocationName();
			
		}
		this.mobileLocation=location.isMobileLocation();
		this.inventoryLocation=location.isInventoryLocation();
		
		if(location.getLegalEntity()!=null) {
			this.legalEntityId=location.getLegalEntity().getLegalEntityId();
			this.legalEntityName=location.getLegalEntity().getLegalEntityName();
		}
		if(location.getLocationType()!=null) {
			this.locationTypeId = location.getLocationType().getLocationTypeId();
			this.locationTypeName = location.getLocationType().getLocationTypeName();
		}
		if(location.getCreatedBy()!=null) {
			this.createdByEmailId=location.getCreatedBy().getEmailId();
			this.createdByFirstName=location.getCreatedBy().getFirstName();
			this.createdByMiddleName=location.getCreatedBy().getMiddleName();
			this.createdByLastName=location.getCreatedBy().getLastName();
			this.createdDate=location.getCreatedDate();
		}
		
		if(location.getModifiedBy()!=null) {
			this.modifiedByEmailId=location.getModifiedBy().getEmailId();
			this.modifiedByFirstName=location.getModifiedBy().getFirstName();
			this.modifiedByMiddleName=location.getModifiedBy().getMiddleName();
			this.modifiedByLastName=location.getModifiedBy().getLastName();
			this.modifiedDate=location.getModifiedDate();
		}
		
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
	 * @return the ownerEmailId
	 */
	public String getOwnerEmailId() {
		return ownerEmailId;
	}
	/**
	 * @return the ownerFirstName
	 */
	public String getOwnerFirstName() {
		return ownerFirstName;
	}
	/**
	 * @return the ownerLastName
	 */
	public String getOwnerLastName() {
		return ownerLastName;
	}
	/**
	 * @return the parentLocationId
	 */
	public long getParentLocationId() {
		return parentLocationId;
	}
	/**
	 * @return the parentLocationName
	 */
	public String getParentLocationName() {
		return parentLocationName;
	}
	/**
	 * @return the mobileLocation
	 */
	public boolean isMobileLocation() {
		return mobileLocation;
	}
	
	/**
	 * @return the inventoryLocation
	 */
	public boolean isInventoryLocation() {
		return inventoryLocation;
	}
	/**
	 * @return the legalEntityId
	 */
	public long getLegalEntityId() {
		return legalEntityId;
	}
	/**
	 * @return the legalEntityName
	 */
	public String getLegalEntityName() {
		return legalEntityName;
	}
	/**
	 * @return the createdByEmailId
	 */
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	/**
	 * @return the createdByFirstName
	 */
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	/**
	 * @return the createdByLastName
	 */
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the modifiedByEmailId
	 */
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}
	/**
	 * @return the modifiedByFirstName
	 */
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	/**
	 * @return the modifiedByLastName
	 */
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
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
	 * @param ownerFirstName the ownerFirstName to set
	 */
	public void setOwnerFirstName(String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}
	/**
	 * @param ownerLastName the ownerLastName to set
	 */
	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}
	/**
	 * @param parentLocationId the parentLocationId to set
	 */
	public void setParentLocationId(long parentLocationId) {
		this.parentLocationId = parentLocationId;
	}
	/**
	 * @param parentLocationName the parentLocationName to set
	 */
	public void setParentLocationName(String parentLocationName) {
		this.parentLocationName = parentLocationName;
	}
	/**
	 * @param mobileLocation the mobileLocation to set
	 */
	public void setMobileLocation(boolean mobileLocation) {
		this.mobileLocation = mobileLocation;
	}
	
	/**
	 * @param inventoryLocation the inventoryLocation to set
	 */
	public void setInventoryLocation(boolean inventoryLocation) {
		this.inventoryLocation = inventoryLocation;
	}
	/**
	 * @param legalEntityId the legalEntityId to set
	 */
	public void setLegalEntityId(long legalEntityId) {
		this.legalEntityId = legalEntityId;
	}
	/**
	 * @param legalEntityName the legalEntityName to set
	 */
	public void setLegalEntityName(String legalEntityName) {
		this.legalEntityName = legalEntityName;
	}
	/**
	 * @param createdByEmailId the createdByEmailId to set
	 */
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	/**
	 * @param createdByFirstName the createdByFirstName to set
	 */
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	/**
	 * @param createdByLastName the createdByLastName to set
	 */
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param modifiedByEmailId the modifiedByEmailId to set
	 */
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	/**
	 * @param modifiedByFirstName the modifiedByFirstName to set
	 */
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	/**
	 * @param modifiedByLastName the modifiedByLastName to set
	 */
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public int getLocationTypeId() {
		return locationTypeId;
	}
	public void setLocationTypeId(int locationTypeId) {
		this.locationTypeId = locationTypeId;
	}
	public String getLocationTypeName() {
		return locationTypeName;
	}
	public void setLocationTypeName(String locationTypeName) {
		this.locationTypeName = locationTypeName;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public String getOwnerMiddleName() {
		return ownerMiddleName;
	}
	public void setOwnerMiddleName(String ownerMiddleName) {
		this.ownerMiddleName = ownerMiddleName;
	}
	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}
	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}
	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}
	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}
	
	
}
