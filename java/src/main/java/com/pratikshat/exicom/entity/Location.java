package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class Location implements java.io.Serializable,OldPresist<Location>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long locationId;

	String locationName;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Location parentLocation;
	
	boolean mobileLocation;
	
	@ManyToOne(fetch=FetchType.LAZY)
	LocationType locationType;
	
	boolean inventoryLocation;
	@ManyToOne(fetch=FetchType.LAZY)
	LegalEntity legalEntity;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	
	Date createdDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	
	Date modifiedDate;
	String plant;
	
	//SAP  PROPERTY START
	String name2;
	//HOUSE NUMBER AND STREET
	String houseNumber;
	String city;
	String postalCode;
	String reasonCode;
	String countryKey;
	String remarks;
	String companyCode;
	String profitCenter;
	//SAP PROPERTY END
	
	@ManyToOne
	BusinessDomain businessDomain;
	
	public Location() {}
	public Location(long locationId) {
		this.locationId=locationId;
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
	 * @return the owner
	 */
	public EmployeeDetails getOwner() {
		return owner;
	}
	/**
	 * @return the parentLocation
	 */
	public Location getParentLocation() {
		return parentLocation;
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
	 * @return the legalEntity
	 */
	public LegalEntity getLegalEntity() {
		return legalEntity;
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
	 * @param owner the owner to set
	 */
	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}
	/**
	 * @param parentLocation the parentLocation to set
	 */
	public void setParentLocation(Location parentLocation) {
		this.parentLocation = parentLocation;
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
	 * @param legalEntity the legalEntity to set
	 */
	public void setLegalEntity(LegalEntity legalEntity) {
		this.legalEntity = legalEntity;
	}
	/**
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the modifiedBy
	 */
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public LocationType getLocationType() {
		return locationType;
	}
	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getCountryKey() {
		return countryKey;
	}
	public void setCountryKey(String countryKey) {
		this.countryKey = countryKey;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getProfitCenter() {
		return profitCenter;
	}
	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter;
	}
	
	
	

	@javax.persistence.Transient transient Location old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(Location)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public Location gOld() {
		return old;
	}
	public String toString() {
			return ""+locationId;
	}
	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}
	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}
	
	
}
