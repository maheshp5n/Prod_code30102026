package com.pratikshat.exicom.model.response;

public class BtProductRequestResponse {
	long prId;
	String prManualId;
	long destinationLocationId;
	String destinationLocationName;
	long sourceLocationId;
	String sourceLocationName;
	java.util.Date createdDate;
	java.util.Date lastModifiedDate;
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
	String lastModifiedByEmail;
	String lastModifiedByFirstName;
	String lastModifiedByMiddleName;
	String lastModifiedByLastName;
	String sourceLocationOwnerEmailId;
	String destinationLocationOwnerEmailId;
	long sourceLocationTypeId;
	long destinationLocationTypeId;
	
	public BtProductRequestResponse() {}
	public BtProductRequestResponse(com.pratikshat.exicom.entity.BtProductRequest prRequest) {
		this.prId=prRequest.getId();
		this.prManualId=prRequest.getManualId();
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
		this.lastModifiedDate =prRequest.getLastModifiedDate();
		this.description=prRequest.getDescription();
		this.needByDateTime=prRequest.getNeedByDateTime();
		this.status=prRequest.getBtProductRequestStatus();
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
		if(prRequest.getLastModifiedBy()!=null) {
			this.lastModifiedByEmail=prRequest.getLastModifiedBy().getEmailId();
			this.lastModifiedByFirstName=prRequest.getLastModifiedBy().getFirstName();
			this.lastModifiedByMiddleName=prRequest.getLastModifiedBy().getMiddleName();
			this.lastModifiedByLastName=prRequest.getLastModifiedBy().getLastName();
		}
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
	
	public java.util.Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(java.util.Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
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
	
	public String getLastModifiedByEmail() {
		return lastModifiedByEmail;
	}
	public void setLastModifiedByEmail(String lastModifiedByEmail) {
		this.lastModifiedByEmail = lastModifiedByEmail;
	}
	public String getLastModifiedByFirstName() {
		return lastModifiedByFirstName;
	}
	public void setLastModifiedByFirstName(String lastModifiedByFirstName) {
		this.lastModifiedByFirstName = lastModifiedByFirstName;
	}
	public String getLastModifiedByMiddleName() {
		return lastModifiedByMiddleName;
	}
	public void setLastModifiedByMiddleName(String lastModifiedByMiddleName) {
		this.lastModifiedByMiddleName = lastModifiedByMiddleName;
	}
	public String getLastModifiedByLastName() {
		return lastModifiedByLastName;
	}
	public void setLastModifiedByLastName(String lastModifiedByLastName) {
		this.lastModifiedByLastName = lastModifiedByLastName;
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
	
	
	
}
