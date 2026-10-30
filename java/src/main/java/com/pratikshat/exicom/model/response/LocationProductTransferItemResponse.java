package com.pratikshat.exicom.model.response;

import java.util.Date;

public class LocationProductTransferItemResponse {
	long locationProductTransferItemId;
	String locationProductTransferItemManualId;
	long locationProductTransferId;
	String locationProductTransferManualId;
	long locationProductRequestId;
	String locationProductRequestManualId;
	String productPartCode;
	String partDescription;
	float quantitySent;
	float quantityReceived;
	boolean receivedAll;
	
	long sourceLocationId;
	String sourceLocationName;
	long destinationLocationId;
	String destinationLocationName;
	long locationProductRequestLineItemId;
	String locationProductRequestLineItemManualId;
	
	
	String ownerFirstName;
	String ownerMiddleName;
	String ownerLastName;
	String ownerEmailId;
	String createdByEmailId;
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	String modifiedByEmailId;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	
	String trackingNumber;
	String shipToName;
	String shippingMode;
	java.util.Date expectedDate;
	
	public LocationProductTransferItemResponse() {}
	public LocationProductTransferItemResponse(com.pratikshat.exicom.entity.LPTransferItem tItem) {
		this.locationProductTransferItemId=tItem.getLpTransferItemId();
		this.locationProductTransferItemManualId=tItem.getLpTransferItemIdManualId();
		if(tItem.getLpTranfer()!=null) {
			this.locationProductTransferId=tItem.getLpTranfer().getLocationProductTransferId();
			this.locationProductTransferManualId=tItem.getLpTranfer().getLocationProductTransferManualId();
			if(tItem.getLpTranfer().getLocationProductRequest()!=null) {
				this.locationProductRequestId=tItem.getLpTranfer().getLocationProductRequest().getLocationProductRequestId();
				this.locationProductRequestManualId=tItem.getLpTranfer().getLocationProductRequest().getLocationProductRequestManualId();
				if(tItem.getLpTranfer().getLocationProductRequest().getDestinationLocation()!=null) {
					this.destinationLocationId=tItem.getLpTranfer().getLocationProductRequest().getDestinationLocation().getLocationId();
					this.destinationLocationName=tItem.getLpTranfer().getLocationProductRequest().getDestinationLocation().getLocationName();
				}
				if(tItem.getLpTranfer().getLocationProductRequest().getSourceLocation()!=null) {
					this.sourceLocationId=tItem.getLpTranfer().getLocationProductRequest().getSourceLocation().getLocationId();
					this.sourceLocationName=tItem.getLpTranfer().getLocationProductRequest().getSourceLocation().getLocationName();
				}
			}
			if(tItem.getLpTranfer().getOwner()!=null) {
				this.ownerEmailId=tItem.getLpTranfer().getOwner().getEmailId();
				this.ownerFirstName=tItem.getLpTranfer().getOwner().getFirstName();
				this.ownerMiddleName=tItem.getLpTranfer().getOwner().getMiddleName();
				this.ownerLastName=tItem.getLpTranfer().getOwner().getLastName();
			}
			if(tItem.getLpTranfer().getCreatedBy()!=null) {
				this.createdByEmailId=tItem.getLpTranfer().getCreatedBy().getEmailId();
				this.createdByFirstName=tItem.getLpTranfer().getCreatedBy().getFirstName();
				this.createdByMiddleName=tItem.getLpTranfer().getCreatedBy().getMiddleName();
				this.createdByLastName=tItem.getLpTranfer().getCreatedBy().getLastName();
			}
			if(tItem.getLpTranfer().getModifiedBy()!=null) {
				this.modifiedByEmailId=tItem.getLpTranfer().getModifiedBy().getEmailId();
				this.modifiedByFirstName=tItem.getLpTranfer().getModifiedBy().getFirstName();
				this.modifiedByMiddleName=tItem.getLpTranfer().getModifiedBy().getMiddleName();
				this.modifiedByLastName=tItem.getLpTranfer().getModifiedBy().getLastName();
				
			}
			this.createdDate=tItem.getLpTranfer().getCreatedDate();
			this.modifiedDate=tItem.getLpTranfer().getModifiedDate();
			this.shippingMode=tItem.getLpTranfer().getShippingMode();
			this.shipToName=tItem.getLpTranfer().getShippingToName();
			this.trackingNumber=tItem.getLpTranfer().getTrackingNumber();
			this.expectedDate=tItem.getLpTranfer().getActualDeliveryDate();
		}
		if(tItem.getLpRequestLineItem()!=null) {
			this.locationProductRequestLineItemId=tItem.getLpRequestLineItem().getLocationProductRequestLineItemId();
			this.locationProductRequestLineItemManualId=tItem.getLpRequestLineItem().getLocationProductRequestLineItemManualId();
		}
		if(tItem.getProduct()!=null) {
			this.productPartCode=tItem.getProduct().getProductPartCode();
			this.partDescription=tItem.getProduct().getPdoductDescription();	
		}
		this.quantitySent=tItem.getTransferQuantity();
		this.quantityReceived=tItem.getReceivedQuantity();
		this.receivedAll=this.quantityReceived==this.quantitySent;
		
		
	}
	
	/**
	 * @return the locationProductTransferItemId
	 */
	public long getLocationProductTransferItemId() {
		return locationProductTransferItemId;
	}
	/**
	 * @return the locationProductTransferItemManualId
	 */
	public String getLocationProductTransferItemManualId() {
		return locationProductTransferItemManualId;
	}
	/**
	 * @return the locationProductTransferId
	 */
	public long getLocationProductTransferId() {
		return locationProductTransferId;
	}
	/**
	 * @return the locationProductTransferManualId
	 */
	public String getLocationProductTransferManualId() {
		return locationProductTransferManualId;
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
	 * @return the productPartCode
	 */
	public String getProductPartCode() {
		return productPartCode;
	}
	/**
	 * @return the partDescription
	 */
	public String getPartDescription() {
		return partDescription;
	}
	/**
	 * @return the quantitySent
	 */
	public float getQuantitySent() {
		return quantitySent;
	}
	/**
	 * @return the quantityReceived
	 */
	public float getQuantityReceived() {
		return quantityReceived;
	}
	/**
	 * @return the receivedAll
	 */
	public boolean isReceivedAll() {
		return receivedAll;
	}
	/**
	 * @param locationProductTransferItemId the locationProductTransferItemId to set
	 */
	public void setLocationProductTransferItemId(long locationProductTransferItemId) {
		this.locationProductTransferItemId = locationProductTransferItemId;
	}
	/**
	 * @param locationProductTransferItemManualId the locationProductTransferItemManualId to set
	 */
	public void setLocationProductTransferItemManualId(String locationProductTransferItemManualId) {
		this.locationProductTransferItemManualId = locationProductTransferItemManualId;
	}
	/**
	 * @param locationProductTransferId the locationProductTransferId to set
	 */
	public void setLocationProductTransferId(long locationProductTransferId) {
		this.locationProductTransferId = locationProductTransferId;
	}
	/**
	 * @param locationProductTransferManualId the locationProductTransferManualId to set
	 */
	public void setLocationProductTransferManualId(String locationProductTransferManualId) {
		this.locationProductTransferManualId = locationProductTransferManualId;
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
	 * @param productPartCode the productPartCode to set
	 */
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	/**
	 * @param partDescription the partDescription to set
	 */
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	/**
	 * @param quantitySent the quantitySent to set
	 */
	public void setQuantitySent(float quantitySent) {
		this.quantitySent = quantitySent;
	}
	/**
	 * @param quantityReceived the quantityReceived to set
	 */
	public void setQuantityReceived(float quantityReceived) {
		this.quantityReceived = quantityReceived;
	}
	/**
	 * @param receivedAll the receivedAll to set
	 */
	public void setReceivedAll(boolean receivedAll) {
		this.receivedAll = receivedAll;
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
	 * @return the locationProductRequestLineItemId
	 */
	public long getLocationProductRequestLineItemId() {
		return locationProductRequestLineItemId;
	}
	/**
	 * @return the locationProductRequestLineItemManualId
	 */
	public String getLocationProductRequestLineItemManualId() {
		return locationProductRequestLineItemManualId;
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
	 * @param locationProductRequestLineItemId the locationProductRequestLineItemId to set
	 */
	public void setLocationProductRequestLineItemId(long locationProductRequestLineItemId) {
		this.locationProductRequestLineItemId = locationProductRequestLineItemId;
	}
	/**
	 * @param locationProductRequestLineItemManualId the locationProductRequestLineItemManualId to set
	 */
	public void setLocationProductRequestLineItemManualId(String locationProductRequestLineItemManualId) {
		this.locationProductRequestLineItemManualId = locationProductRequestLineItemManualId;
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
	 * @return the trackingNumber
	 */
	public String getTrackingNumber() {
		return trackingNumber;
	}
	/**
	 * @return the shipToName
	 */
	public String getShipToName() {
		return shipToName;
	}
	/**
	 * @return the shippingMode
	 */
	public String getShippingMode() {
		return shippingMode;
	}
	/**
	 * @return the expectedDate
	 */
	public java.util.Date getExpectedDate() {
		return expectedDate;
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
	 * @param trackingNumber the trackingNumber to set
	 */
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	/**
	 * @param shipToName the shipToName to set
	 */
	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}
	/**
	 * @param shippingMode the shippingMode to set
	 */
	public void setShippingMode(String shippingMode) {
		this.shippingMode = shippingMode;
	}
	/**
	 * @param expectedDate the expectedDate to set
	 */
	public void setExpectedDate(java.util.Date expectedDate) {
		this.expectedDate = expectedDate;
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
