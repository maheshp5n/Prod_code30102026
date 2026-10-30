package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Column;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LProductRequest implements java.io.Serializable,OldPresist<LProductRequest>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long locationProductRequestId;
	String locationProductRequestManualId;//ID initials will be PR-
	
	@Column(length = 500)
	String description;
	java.util.Date needByDateTime;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	Location sourceLocation;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	Location destinationLocation;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	
	String locationProductRequestStatus;
	float totalQuantityRequired;
	float totalQuantityReceived;
	float totalQuantityIssued;
	@Column(name="total_short_close_qty")
	float totalShortCloseQty;
	
	String dummayStatus;
	
	@Column(name="short_closed")
	boolean shortClosed=false;

	
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails ownerDetails;
	
	boolean synked;
	
	public LProductRequest() {}
	public LProductRequest(long locationProductRequestId) {
		this.locationProductRequestId=locationProductRequestId;
	}
	/**
	 * @return the locationProductRequestId
	 */
	public long getLocationProductRequestId() {
		return locationProductRequestId;
	}
	/**
	 * @return the locationProductRequestManualId
	 */
	public String getLocationProductRequestManualId() {
		return locationProductRequestManualId;
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
	 * @return the sourceLocation
	 */
	public Location getSourceLocation() {
		return sourceLocation;
	}
	/**
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	/**
	 * @return the modifiedBy
	 */
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @return the createdDate
	 */
	public java.util.Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the modifiedDate
	 */
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @return the locationProductRequestStatus
	 */
	public String getLocationProductRequestStatus() {
		return locationProductRequestStatus;
	}
	/**
	 * @return the totalQuantityRequired
	 */
	public float getTotalQuantityRequired() {
		return totalQuantityRequired;
	}
	/**
	 * @return the totalQuantityReceived
	 */
	public float getTotalQuantityReceived() {
		return totalQuantityReceived;
	}
	/**
	 * @return the totalQuantityIssued
	 */
	public float getTotalQuantityIssued() {
		return totalQuantityIssued;
	}
	/**
	 * @return the ownerDetails
	 */
	public EmployeeDetails getOwnerDetails() {
		return ownerDetails;
	}
	/**
	 * @param locationProductRequestId the locationProductRequestId to set
	 */
	public void setLocationProductRequestId(long locationProductRequestId) {
		this.locationProductRequestId = locationProductRequestId;
	}
	/**
	 * @param locationProductRequestManualId the locationProductRequestManualId to set
	 */
	public void setLocationProductRequestManualId(String locationProductRequestManualId) {
		this.locationProductRequestManualId = locationProductRequestManualId;
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
	 * @param sourceLocation the sourceLocation to set
	 */
	public void setSourceLocation(Location sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @param locationProductRequestStatus the locationProductRequestStatus to set
	 */
	public void setLocationProductRequestStatus(String locationProductRequestStatus) {
		this.locationProductRequestStatus = locationProductRequestStatus;
	}
	/**
	 * @param totalQuantityRequired the totalQuantityRequired to set
	 */
	public void setTotalQuantityRequired(float totalQuantityRequired) {
		this.totalQuantityRequired = totalQuantityRequired;
	}
	/**
	 * @param totalQuantityReceived the totalQuantityReceived to set
	 */
	public void setTotalQuantityReceived(float totalQuantityReceived) {
		this.totalQuantityReceived = totalQuantityReceived;
	}
	/**
	 * @param totalQuantityIssued the totalQuantityIssued to set
	 */
	public void setTotalQuantityIssued(float totalQuantityIssued) {
		this.totalQuantityIssued = totalQuantityIssued;
	}
	/**
	 * @param ownerDetails the ownerDetails to set
	 */
	public void setOwnerDetails(EmployeeDetails ownerDetails) {
		this.ownerDetails = ownerDetails;
	}
	public Location getDestinationLocation() {
		return destinationLocation;
	}
	public void setDestinationLocation(Location destinationLocation) {
		this.destinationLocation = destinationLocation;
	}
	public boolean isSynked() {
		return synked;
	}
	public void setSynked(boolean synked) {
		this.synked = synked;
	}
	
	

	public float getTotalShortCloseQty() {
		return totalShortCloseQty;
	}
	public void setTotalShortCloseQty(float totalShortCloseQty) {
		this.totalShortCloseQty = totalShortCloseQty;
	}



	@javax.persistence.Transient transient LProductRequest old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LProductRequest)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LProductRequest gOld() {
		return old;
	}
	public String toString() {
			return ""+locationProductRequestId;
	}
	public String getDummayStatus() {
		return dummayStatus;
	}
	public void setDummayStatus(String dummayStatus) {
		this.dummayStatus = dummayStatus;
	}
	public boolean isShortClosed() {
		return shortClosed;
	}
	public void setShortClosed(boolean shortClosed) {
		this.shortClosed = shortClosed;
	}
	
	
	
}
