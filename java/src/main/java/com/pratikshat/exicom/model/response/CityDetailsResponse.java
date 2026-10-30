package com.pratikshat.exicom.model.response;

import java.util.Date;

public class CityDetailsResponse {
	String cityName;
	String ownerId;
	String ownerFirstName;
	String ownerMiddleName;
	String ownerLastName;
	String cityCatagoryName;
	int cityCategoryId;
	String createdById;
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	Date createdDate;
	String modifiedById;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	Date modifiedDate;
	
	
	/**
	 * 
	 */
	public CityDetailsResponse() {
	}
	
	public CityDetailsResponse(com.pratikshat.exicom.entity.CityDetails city) {
		this.cityName=city.getCityName();
		if(city.getOwnerDetails()!=null) {
			this.ownerId=city.getOwnerDetails().getEmailId();
			this.ownerFirstName=city.getOwnerDetails().getFirstName();
			this.ownerMiddleName=city.getOwnerDetails().getMiddleName();
			this.ownerLastName=city.getOwnerDetails().getLastName();
		}
		if(city.getCityCatagory()!=null) {
			this.cityCatagoryName=city.getCityCatagory().getCityCatagoryName();
			this.cityCategoryId = city.getCityCatagory().getCityCategoryId();
		}
		if(city.getCreatedBy()!=null) {
			this.createdById=city.getCreatedBy().getEmailId();
			this.createdByFirstName=city.getCreatedBy().getFirstName();
			this.createdByMiddleName=city.getCreatedBy().getMiddleName();
			this.createdByLastName=city.getCreatedBy().getLastName();
		}
		this.createdDate=city.getCreatedDate();
		if(city.getModifiedBy()!=null) {
			this.modifiedById=city.getModifiedBy().getEmailId();
			this.modifiedByFirstName=city.getModifiedBy().getFirstName();
			this.modifiedByMiddleName=city.getModifiedBy().getMiddleName();
			this.modifiedByLastName=city.getModifiedBy().getLastName();
		}
		this.modifiedDate=city.getModifiedDate();
	}
	
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @return the ownerId
	 */
	public String getOwnerId() {
		return ownerId;
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
	 * @return the cityCatagoryName
	 */
	public String getCityCatagoryName() {
		return cityCatagoryName;
	}
	/**
	 * @return the createdById
	 */
	public String getCreatedById() {
		return createdById;
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
	 * @return the modifiedById
	 */
	public String getModifiedById() {
		return modifiedById;
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
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
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
	 * @param cityCatagoryName the cityCatagoryName to set
	 */
	public void setCityCatagoryName(String cityCatagoryName) {
		this.cityCatagoryName = cityCatagoryName;
	}
	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
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
	 * @param modifiedById the modifiedById to set
	 */
	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
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

	public int getCityCategoryId() {
		return cityCategoryId;
	}

	public void setCityCategoryId(int cityCategoryId) {
		this.cityCategoryId = cityCategoryId;
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
