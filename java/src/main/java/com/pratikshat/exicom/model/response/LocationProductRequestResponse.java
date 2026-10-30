package com.pratikshat.exicom.model.response;

public class LocationProductRequestResponse {
	long prId;
	String prManualId;
	long destinationLocationId;
	String destinationLocationName;
	long sourceLocationId;
	String sourceLocationName;
	java.util.Date createdDate;
	String description;
	java.util.Date needByDateTime;
	String status;
	String ownerEmailid;
	String ownerFirstName;
	String ownerMiddleName;
	String ownerLastName;
	float totalQuantityRequired;
	float totalQuantityIssued;
	float totalQuantityReceived;
	String createdByEmail;
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	String modifiedByEmail;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	java.util.Date modifiedDate;
	String sourceLocationOwnerEmailId;
	String destinationLocationOwnerEmailId;
	long sourceLocationTypeId;
	long destinationLocationTypeId;
	float totalSortCloseQty;
	boolean shortClosed=false;
	
	public float getTotalSortCloseQty() {
		return totalSortCloseQty;
	}
	public void setTotalSortCloseQty(float totalSortCloseQty) {
		this.totalSortCloseQty = totalSortCloseQty;
	}
	public LocationProductRequestResponse() {}
	public LocationProductRequestResponse(com.pratikshat.exicom.entity.LProductRequest prRequest) {
		this.prId=prRequest.getLocationProductRequestId();
		this.prManualId=prRequest.getLocationProductRequestManualId();
		this.totalSortCloseQty = prRequest.getTotalShortCloseQty();
		this.shortClosed = prRequest.isShortClosed();
		if(prRequest.getDestinationLocation()!=null) {
			this.destinationLocationId=prRequest.getDestinationLocation().getLocationId();
			this.destinationLocationName=prRequest.getDestinationLocation().getLocationName();
			if(prRequest.getDestinationLocation().getOwner()!=null)
				this.destinationLocationOwnerEmailId=prRequest.getDestinationLocation().getOwner().getEmailId();
			if(prRequest.getDestinationLocation().getLocationType()!=null)
				this.destinationLocationTypeId=prRequest.getDestinationLocation().getLocationType().getLocationTypeId();
		}
		if(prRequest.getSourceLocation()!=null) {
			this.sourceLocationId=prRequest.getSourceLocation().getLocationId();
			this.sourceLocationName=prRequest.getSourceLocation().getLocationName();
			if(prRequest.getSourceLocation().getOwner()!=null)
				this.sourceLocationOwnerEmailId=prRequest.getSourceLocation().getOwner().getEmailId();
			if(prRequest.getSourceLocation().getLocationType()!=null)
				this.sourceLocationTypeId=prRequest.getSourceLocation().getLocationType().getLocationTypeId();
		}
		this.createdDate=prRequest.getCreatedDate();
		this.description=prRequest.getDescription();
		this.needByDateTime=prRequest.getNeedByDateTime();
		this.status=prRequest.getLocationProductRequestStatus();
		if(prRequest.getOwnerDetails()!=null) {
			this.ownerEmailid=prRequest.getOwnerDetails().getEmailId();
			this.ownerFirstName=prRequest.getOwnerDetails().getFirstName();
			this.ownerMiddleName=prRequest.getOwnerDetails().getMiddleName();
			this.ownerLastName=prRequest.getOwnerDetails().getLastName();
		}
		if(prRequest.getCreatedBy()!=null) {
			this.createdByEmail=prRequest.getCreatedBy().getEmailId();
			this.createdByFirstName=prRequest.getCreatedBy().getFirstName();
			this.createdByMiddleName=prRequest.getCreatedBy().getMiddleName();
			this.createdByLastName=prRequest.getCreatedBy().getLastName();
		}
		if(prRequest.getModifiedBy()!=null) {
			this.modifiedByEmail=prRequest.getModifiedBy().getEmailId();
			this.modifiedByFirstName=prRequest.getModifiedBy().getFirstName();
			this.modifiedByMiddleName=prRequest.getModifiedBy().getMiddleName();
			this.modifiedByLastName=prRequest.getModifiedBy().getLastName();
			
		}
		this.modifiedDate=prRequest.getModifiedDate();
		this.totalQuantityRequired=prRequest.getTotalQuantityRequired();
		this.totalQuantityIssued=prRequest.getTotalQuantityIssued();
		this.totalQuantityReceived=prRequest.getTotalQuantityReceived();
	}
	/**
	 * @return the prId
	 */
	public long getPrId() {
		return prId;
	}
	/**
	 * @return the prManualId
	 */
	public String getPrManualId() {
		return prManualId;
	}
	/**
	 * @return the destinationLocationId
	 */
	public long getDestinationLocationId() {
		return destinationLocationId;
	}
	/**
	 * @return the destinationLocationName
	 */
	public String getDestinationLocationName() {
		return destinationLocationName;
	}
	/**
	 * @return the sourceLocationId
	 */
	public long getSourceLocationId() {
		return sourceLocationId;
	}
	/**
	 * @return the sourceLocationName
	 */
	public String getSourceLocationName() {
		return sourceLocationName;
	}
	/**
	 * @return the createdDate
	 */
	public java.util.Date getCreatedDate() {
		return createdDate;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param prId the prId to set
	 */
	public void setPrId(long prId) {
		this.prId = prId;
	}
	/**
	 * @param prManualId the prManualId to set
	 */
	public void setPrManualId(String prManualId) {
		this.prManualId = prManualId;
	}
	/**
	 * @param destinationLocationId the destinationLocationId to set
	 */
	public void setDestinationLocationId(long destinationLocationId) {
		this.destinationLocationId = destinationLocationId;
	}
	/**
	 * @param destinationLocationName the destinationLocationName to set
	 */
	public void setDestinationLocationName(String destinationLocationName) {
		this.destinationLocationName = destinationLocationName;
	}
	/**
	 * @param sourceLocationId the sourceLocationId to set
	 */
	public void setSourceLocationId(long sourceLocationId) {
		this.sourceLocationId = sourceLocationId;
	}
	/**
	 * @param sourceLocationName the sourceLocationName to set
	 */
	public void setSourceLocationName(String sourceLocationName) {
		this.sourceLocationName = sourceLocationName;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
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
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the ownerEmailid
	 */
	public String getOwnerEmailid() {
		return ownerEmailid;
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
	 * @return the totalQuantityRequired
	 */
	public float getTotalQuantityRequired() {
		return totalQuantityRequired;
	}
	/**
	 * @return the totalQuantityIssued
	 */
	public float getTotalQuantityIssued() {
		return totalQuantityIssued;
	}
	/**
	 * @return the totalQuantityReceived
	 */
	public float getTotalQuantityReceived() {
		return totalQuantityReceived;
	}
	/**
	 * @return the createdByEmail
	 */
	public String getCreatedByEmail() {
		return createdByEmail;
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
	 * @return the modifiedByEmail
	 */
	public String getModifiedByEmail() {
		return modifiedByEmail;
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
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param ownerEmailid the ownerEmailid to set
	 */
	public void setOwnerEmailid(String ownerEmailid) {
		this.ownerEmailid = ownerEmailid;
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
	 * @param totalQuantityRequired the totalQuantityRequired to set
	 */
	public void setTotalQuantityRequired(float totalQuantityRequired) {
		this.totalQuantityRequired = totalQuantityRequired;
	}
	/**
	 * @param totalQuantityIssued the totalQuantityIssued to set
	 */
	public void setTotalQuantityIssued(float totalQuantityIssued) {
		this.totalQuantityIssued = totalQuantityIssued;
	}
	/**
	 * @param totalQuantityReceived the totalQuantityReceived to set
	 */
	public void setTotalQuantityReceived(float totalQuantityReceived) {
		this.totalQuantityReceived = totalQuantityReceived;
	}
	/**
	 * @param createdByEmail the createdByEmail to set
	 */
	public void setCreatedByEmail(String createdByEmail) {
		this.createdByEmail = createdByEmail;
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
	 * @param modifiedByEmail the modifiedByEmail to set
	 */
	public void setModifiedByEmail(String modifiedByEmail) {
		this.modifiedByEmail = modifiedByEmail;
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
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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
	public String getSourceLocationOwnerEmailId() {
		return sourceLocationOwnerEmailId;
	}
	public void setSourceLocationOwnerEmailId(String sourceLocationOwnerEmailId) {
		this.sourceLocationOwnerEmailId = sourceLocationOwnerEmailId;
	}
	public String getDestinationLocationOwnerEmailId() {
		return destinationLocationOwnerEmailId;
	}
	public void setDestinationLocationOwnerEmailId(String destinationLocationOwnerEmailId) {
		this.destinationLocationOwnerEmailId = destinationLocationOwnerEmailId;
	}
	public long getSourceLocationTypeId() {
		return sourceLocationTypeId;
	}
	public void setSourceLocationTypeId(long sourceLocationTypeId) {
		this.sourceLocationTypeId = sourceLocationTypeId;
	}
	public long getDestinationLocationTypeId() {
		return destinationLocationTypeId;
	}
	public void setDestinationLocationTypeId(long destinationLocationTypeId) {
		this.destinationLocationTypeId = destinationLocationTypeId;
	}
	public boolean isShortClosed() {
		return shortClosed;
	}
	public void setShortClosed(boolean shortClosed) {
		this.shortClosed = shortClosed;
	}
	
	
	
}
