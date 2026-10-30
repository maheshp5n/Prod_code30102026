package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class CityDetails implements java.io.Serializable,OldPresist<CityDetails>{
	@Id
	String cityName;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="city_catagory")
	CityCatagory cityCatagory;
	
	
	@ManyToOne
	EmployeeDetails createdBy;
	Date createdDate;
	@ManyToOne
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	@ManyToOne
	EmployeeDetails ownerDetails;
	boolean synced;
	
	/**
	 * @return the synced
	 */
	public boolean isSynced() {
		return synced;
	}
	/**
	 * @param synced the synced to set
	 */
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	/**
	 * 
	 */
	public CityDetails() {
	}
	public CityDetails(String cityName,int cityCategoryId) {
		this.cityName=cityName;
		this.cityCatagory=new CityCatagory(cityCategoryId);
	}
	public CityDetails(String cityName,CityCatagory catagoryCategory) {
		this.cityName=cityName;
		this.cityCatagory=catagoryCategory;
	}
	
	
	public CityDetails(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * 
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @return the cityCatagory
	 */
	public CityCatagory getCityCatagory() {
		return cityCatagory;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @param cityCatagory the cityCatagory to set
	 */
	public void setCityCatagory(CityCatagory cityCatagory) {
		this.cityCatagory = cityCatagory;
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
	/**
	 * @return the ownerDetails
	 */
	public EmployeeDetails getOwnerDetails() {
		return ownerDetails;
	}
	/**
	 * @param ownerDetails the ownerDetails to set
	 */
	public void setOwnerDetails(EmployeeDetails ownerDetails) {
		this.ownerDetails = ownerDetails;
	}
	
	

	@javax.persistence.Transient transient CityDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CityDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CityDetails gOld() {
		return old;
	}
	public String toString() {
			return ""+cityName;
	}
}
