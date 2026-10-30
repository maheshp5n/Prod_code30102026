package com.pratikshat.exicom.model.response;

import java.util.Date;

public class LpReturnOrderResponse {
	long returnOrderId;
	String returnOrderManualId;
	long destinationLocationId;
	String destinationLocationName;
	long sourceLocationId;
	String sourceLocationName;
	java.util.Date returnDate;
	String shippingToName;
	String shippingModel;
	String trackingNumber;
	java.util.Date expectedDateOf;
	float totalQuantityReturned;
	float totalQuantityReceived;
	
	String ownerFirstName;
	String ownerMiddleName;
	String ownerLastName;
	String ownerEmailId;
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	String createdByEmailId;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	String modifiedByEmailId;
	String status;
	boolean received;
	
	String sourceLocationOwnerEmailId;
	String destinationLocationOwnerEmailId;
	
	
	public LpReturnOrderResponse() {}

	public LpReturnOrderResponse(com.pratikshat.exicom.entity.LProductReturnOrder returnOrder) {
		this.returnOrderId=returnOrder.getLprOrderid();
		this.returnOrderManualId=returnOrder.getLprOrderManualId();
		if(returnOrder.getDestinationLocation()!=null) {
			this.destinationLocationId=returnOrder.getDestinationLocation().getLocationId();
			this.destinationLocationName=returnOrder.getDestinationLocation().getLocationName();
			if(returnOrder.getDestinationLocation().getOwner()!=null)
				this.destinationLocationOwnerEmailId=returnOrder.getDestinationLocation().getOwner().getEmailId();
		}
		if(returnOrder.getSourceLocation()!=null) {
			this.sourceLocationId=returnOrder.getSourceLocation().getLocationId();
			this.sourceLocationName=returnOrder.getSourceLocation().getLocationName();
			if(returnOrder.getSourceLocation().getOwner()!=null)
				this.sourceLocationOwnerEmailId=returnOrder.getSourceLocation().getOwner().getEmailId();
		}
		this.returnDate=returnOrder.getCreatedDate();
		this.shippingToName=returnOrder.getShipToName();
		this.shippingModel=returnOrder.getShippingMode();
		this.trackingNumber=returnOrder.getTrackingNumber();
		this.expectedDateOf=returnOrder.getExpectedArrivalDate();
		this.totalQuantityReturned=returnOrder.getTotalQuantityReturned();
		this.totalQuantityReceived=returnOrder.getTotalQuantityReceived();
		if(returnOrder.getOwner()!=null) {
			this.ownerFirstName=returnOrder.getOwner().getFirstName();
			this.ownerMiddleName=returnOrder.getOwner().getMiddleName();
			this.ownerLastName=returnOrder.getOwner().getLastName();
			this.ownerEmailId=returnOrder.getOwner().getEmailId();
		}
		if(returnOrder.getCreatedBy()!=null) {
			this.createdByFirstName=returnOrder.getCreatedBy().getFirstName();
			this.createdByMiddleName=returnOrder.getCreatedBy().getMiddleName();
			this.createdByLastName=returnOrder.getCreatedBy().getLastName();
			this.createdByEmailId=returnOrder.getCreatedBy().getEmailId();
		}
		if(returnOrder.getModifiedBy()!=null) {
			this.modifiedByEmailId=returnOrder.getModifiedBy().getEmailId();
			this.modifiedByFirstName=returnOrder.getModifiedBy().getFirstName();
			this.modifiedByMiddleName=returnOrder.getModifiedBy().getMiddleName();
			this.modifiedByLastName=returnOrder.getModifiedBy().getLastName();
		}
		this.createdDate=returnOrder.getCreatedDate();
		
		this.modifiedDate=returnOrder.getModifiedDate();
		this.status=returnOrder.getLocationReturnOrderStatus();
		
		this.received=this.totalQuantityReceived==this.totalQuantityReturned||this.totalQuantityReceived>this.totalQuantityReturned;
		
	}
	
	/**
	 * @return the returnOrderId
	 */
	public long getReturnOrderId() {
		return returnOrderId;
	}
	/**
	 * @return the returnOrderManualId
	 */
	public String getReturnOrderManualId() {
		return returnOrderManualId;
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
	 * @return the returnDate
	 */
	public java.util.Date getReturnDate() {
		return returnDate;
	}
	/**
	 * @return the shippingToName
	 */
	public String getShippingToName() {
		return shippingToName;
	}
	/**
	 * @return the shippingModel
	 */
	public String getShippingModel() {
		return shippingModel;
	}
	/**
	 * @return the trackingNumber
	 */
	public String getTrackingNumber() {
		return trackingNumber;
	}
	/**
	 * @return the expectedDateOf
	 */
	public java.util.Date getExpectedDateOf() {
		return expectedDateOf;
	}
	/**
	 * @return the totalQuantityReturned
	 */
	public float getTotalQuantityReturned() {
		return totalQuantityReturned;
	}
	/**
	 * @return the totalQuantityReceived
	 */
	public float getTotalQuantityReceived() {
		return totalQuantityReceived;
	}
	/**
	 * @param returnOrderId the returnOrderId to set
	 */
	public void setReturnOrderId(long returnOrderId) {
		this.returnOrderId = returnOrderId;
	}
	/**
	 * @param returnOrderManualId the returnOrderManualId to set
	 */
	public void setReturnOrderManualId(String returnOrderManualId) {
		this.returnOrderManualId = returnOrderManualId;
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
	 * @param returnDate the returnDate to set
	 */
	public void setReturnDate(java.util.Date returnDate) {
		this.returnDate = returnDate;
	}
	/**
	 * @param shippingToName the shippingToName to set
	 */
	public void setShippingToName(String shippingToName) {
		this.shippingToName = shippingToName;
	}
	/**
	 * @param shippingModel the shippingModel to set
	 */
	public void setShippingModel(String shippingModel) {
		this.shippingModel = shippingModel;
	}
	/**
	 * @param trackingNumber the trackingNumber to set
	 */
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	/**
	 * @param expectedDateOf the expectedDateOf to set
	 */
	public void setExpectedDateOf(java.util.Date expectedDateOf) {
		this.expectedDateOf = expectedDateOf;
	}
	/**
	 * @param totalQuantityReturned the totalQuantityReturned to set
	 */
	public void setTotalQuantityReturned(float totalQuantityReturned) {
		this.totalQuantityReturned = totalQuantityReturned;
	}
	/**
	 * @param totalQuantityReceived the totalQuantityReceived to set
	 */
	public void setTotalQuantityReceived(float totalQuantityReceived) {
		this.totalQuantityReceived = totalQuantityReceived;
	}
	/**
	 * @return the sourceLocationName
	 */
	public String getSourceLocationName() {
		return sourceLocationName;
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
	 * @return the ownerEmailId
	 */
	public String getOwnerEmailId() {
		return ownerEmailId;
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
	 * @return the createdByEmailId
	 */
	public String getCreatedByEmailId() {
		return createdByEmailId;
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
	 * @return the modifiedByEmailId
	 */
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}
	/**
	 * @param sourceLocationName the sourceLocationName to set
	 */
	public void setSourceLocationName(String sourceLocationName) {
		this.sourceLocationName = sourceLocationName;
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
	 * @param ownerEmailId the ownerEmailId to set
	 */
	public void setOwnerEmailId(String ownerEmailId) {
		this.ownerEmailId = ownerEmailId;
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
	 * @param createdByEmailId the createdByEmailId to set
	 */
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
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
	 * @param modifiedByEmailId the modifiedByEmailId to set
	 */
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @return the received
	 */
	public boolean isReceived() {
		return received;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @param received the received to set
	 */
	public void setReceived(boolean received) {
		this.received = received;
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
	
	
}
