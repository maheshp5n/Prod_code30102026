package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.BtProductTransferItem;

public class BtProductTransferItemResponse {
	long btProductTransferItemId;
	String btProductTransferItemManualId;
	long btProductTransferId;
	String btProductTransferManualId;
	long btProductRequestId;
	String btProductRequestManualId;
	String productPartCode;
	String partDescription;
	float quantitySent;
	float quantityReceived;
	boolean receivedAll;
	
	long sourceLocationId;
	String sourceLocationName;
	long destinationLocationId;
	String destinationLocationName;

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
	
	public BtProductTransferItemResponse() {}
	public BtProductTransferItemResponse(BtProductTransferItem tItem) {
		this.btProductTransferItemId=tItem.getId();
		this.btProductTransferItemManualId=tItem.getManualId();
		if(tItem.getBtProductTransfer()!=null) {
			this.btProductTransferId=tItem.getBtProductTransfer().getId();
			this.btProductTransferManualId=tItem.getBtProductTransfer().getManualId();
			
			if(tItem.getBtProductTransfer().getOwner()!=null) {
				this.ownerEmailId=tItem.getBtProductTransfer().getOwner().getEmailId();
				this.ownerFirstName=tItem.getBtProductTransfer().getOwner().getFirstName();
				this.ownerMiddleName=tItem.getBtProductTransfer().getOwner().getMiddleName();
				this.ownerLastName=tItem.getBtProductTransfer().getOwner().getLastName();
			}
			if(tItem.getBtProductTransfer().getCreatedBy()!=null) {
				this.createdByEmailId=tItem.getBtProductTransfer().getCreatedBy().getEmailId();
				this.createdByFirstName=tItem.getBtProductTransfer().getCreatedBy().getFirstName();
				this.createdByMiddleName=tItem.getBtProductTransfer().getCreatedBy().getMiddleName();
				this.createdByLastName=tItem.getBtProductTransfer().getCreatedBy().getLastName();
			}
			if(tItem.getBtProductTransfer().getLastModifiedBy()!=null) {
				this.modifiedByEmailId=tItem.getBtProductTransfer().getLastModifiedBy().getEmailId();
				this.modifiedByFirstName=tItem.getBtProductTransfer().getLastModifiedBy().getFirstName();
				this.modifiedByMiddleName=tItem.getBtProductTransfer().getLastModifiedBy().getMiddleName();
				this.modifiedByLastName=tItem.getBtProductTransfer().getLastModifiedBy().getLastName();
				
			}
			this.createdDate=tItem.getBtProductTransfer().getCreatedDate();
			this.modifiedDate=tItem.getBtProductTransfer().getLastModifiedDate();
			this.shippingMode=tItem.getBtProductTransfer().getShippingMode();
			this.shipToName=tItem.getBtProductTransfer().getShippingToName();
			this.trackingNumber=tItem.getBtProductTransfer().getTrackingNumber();
			this.expectedDate=tItem.getBtProductTransfer().getActualDeliveryDate();
		}
		
		this.quantitySent=tItem.getTransferQuantity();
		this.quantityReceived=tItem.getReceivedQuantity();
		this.receivedAll=this.quantityReceived==this.quantitySent;
		
		
	}
	
	/**
	 * @return the btProductTransferItemId
	 */
	public long getBtProductTransferItemId() {
		return btProductTransferItemId;
	}
	/**
	 * @return the btProductTransferItemManualId
	 */
	public String getBtProductTransferItemManualId() {
		return btProductTransferItemManualId;
	}
	/**
	 * @return the btProductTransferId
	 */
	public long getBtProductTransferId() {
		return btProductTransferId;
	}
	/**
	 * @return the btProductTransferManualId
	 */
	public String getBtProductTransferManualId() {
		return btProductTransferManualId;
	}
	/**
	 * @return the btProductRequestId
	 */
	public long getBtProductRequestId() {
		return btProductRequestId;
	}
	/**
	 * @return the btProductRequestManualId
	 */
	public String getBtProductRequestManualId() {
		return btProductRequestManualId;
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
		this.btProductTransferItemId = locationProductTransferItemId;
	}
	/**
	 * @param btProductTransferItemManualId the btProductTransferItemManualId to set
	 */
	public void setBtProductTransferItemManualId(String btProductTransferItemManualId) {
		this.btProductTransferItemManualId = btProductTransferItemManualId;
	}
	/**
	 * @param btProductTransferId the btProductTransferId to set
	 */
	public void setBtProductTransferId(long btProductTransferId) {
		this.btProductTransferId = btProductTransferId;
	}
	/**
	 * @param btProductTransferManualId the btProductTransferManualId to set
	 */
	public void setBtProductTransferManualId(String btProductTransferManualId) {
		this.btProductTransferManualId = btProductTransferManualId;
	}
	/**
	 * @param btProductRequestId the btProductRequestId to set
	 */
	public void setBtProductRequestId(long btProductRequestId) {
		this.btProductRequestId = btProductRequestId;
	}
	/**
	 * @param btProductRequestManualId the btProductRequestManualId to set
	 */
	public void setBtProductRequestManualId(String btProductRequestManualId) {
		this.btProductRequestManualId = btProductRequestManualId;
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
