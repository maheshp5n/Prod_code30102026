package com.pratikshat.exicom.model.response;

import java.util.Date;

public class BtProductRequestItemResponse {
	long btpRequestItemId;
	String btpRequestItemManualId;
	long btpRequestId;
	String btpRequestManualId;
	String partCode;
	String partDescription;
	float quantityRequired;
	float quantityReceived;
	float quantityIssued;
	String productType;
	String itemStatus;
	
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	String createdByEmailId;
	
	Date createdDate;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	String modifiedByEmailId;
	
	Date modifiedDate;
	
	boolean disabled;
	
	public BtProductRequestItemResponse() {}
	public BtProductRequestItemResponse(com.pratikshat.exicom.entity.BtProductRequestItem items) {
		this.btpRequestItemId=items.getId();
		this.btpRequestItemManualId=items.getManualId();
		this.itemStatus = items.getItemStatus();
		if(items.getBtProductRequest()!=null) {
			this.btpRequestId=items.getBtProductRequest().getId();
			this.btpRequestManualId=items.getBtProductRequest().getManualId();	
		}
		if(items.getBtProduct()!=null) {
			this.partCode=items.getBtProduct().getManualId();
			this.partDescription=items.getBtProduct().getProductDescription();
			this.productType=items.getBtProduct().getProductType();
		}
	
		this.quantityIssued=items.getQuantityIssued();
		this.quantityRequired=items.getQuantity();
		this.quantityReceived=items.getQuantityReceived();
		this.disabled=items.isDisabledItem();
		
		if(items.getCreatedBy()!=null){
			this.createdByFirstName = items.getCreatedBy().getFirstName();
			this.createdByMiddleName = items.getCreatedBy().getMiddleName();
			this.createdByLastName = items.getCreatedBy().getLastName();
			this.createdByEmailId = items.getCreatedBy().getEmailId();
			this.createdDate = items.getCreatedDate();
		}
		if(items.getLastModifiedBy()!=null){
			this.modifiedByFirstName = items.getLastModifiedBy().getFirstName();
			this.modifiedByMiddleName = items.getLastModifiedBy().getMiddleName();
			this.modifiedByLastName = items.getLastModifiedBy().getLastName();
			this.modifiedByEmailId = items.getLastModifiedBy().getEmailId();
			this.modifiedDate = items.getLastModifiedDate();
		}
		
		
	}
	/**
	 * @return the btpRequestItemId
	 */
	public long getBtpRequestItemId() {
		return btpRequestItemId;
	}
	/**
	 * @return the btpRequestItemManualId
	 */
	public String getBtpRequestItemManualId() {
		return btpRequestItemManualId;
	}
	/**
	 * @return the btpRequestId
	 */
	public long getBtRequestId() {
		return btpRequestId;
	}
	/**
	 * @return the btpRequestManualId
	 */
	public String getBtRequestManualId() {
		return btpRequestManualId;
	}
	/**
	 * @return the partCode
	 */
	public String getPartCode() {
		return partCode;
	}
	/**
	 * @return the partDescription
	 */
	public String getPartDescription() {
		return partDescription;
	}
	/**
	 * @return the quantityRequired
	 */
	public float getQuantityRequired() {
		return quantityRequired;
	}
	/**
	 * @return the quantityReceived
	 */
	public float getQuantityReceived() {
		return quantityReceived;
	}
	/**
	 * @return the quantityIssued
	 */
	public float getQuantityIssued() {
		return quantityIssued;
	}
	/**
	 * @param btpRequestItemId the btpRequestItemId to set
	 */
	public void setBtItemId(long btpRequestItemId) {
		this.btpRequestItemId = btpRequestItemId;
	}
	/**
	 * @param btpRequestItemManualId the btpRequestItemManualId to set
	 */
	public void setBtItemManualId(String btpRequestItemManualId) {
		this.btpRequestItemManualId = btpRequestItemManualId;
	}
	/**
	 * @param btpRequestId the btpRequestId to set
	 */
	public void setBtprId(long btpRequestId) {
		this.btpRequestId = btpRequestId;
	}
	/**
	 * @param btpRequestManualId the btpRequestManualId to set
	 */
	public void setBtManualId(String btpRequestManualId) {
		this.btpRequestManualId = btpRequestManualId;
	}
	/**
	 * @param partCode the partCode to set
	 */
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	/**
	 * @param partDescription the partDescription to set
	 */
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	/**
	 * @param quantityRequired the quantityRequired to set
	 */
	public void setQuantityRequired(float quantityRequired) {
		this.quantityRequired = quantityRequired;
	}
	/**
	 * @param quantityReceived the quantityReceived to set
	 */
	public void setQuantityReceived(float quantityReceived) {
		this.quantityReceived = quantityReceived;
	}
	/**
	 * @param quantityIssued the quantityIssued to set
	 */
	public void setQuantityIssued(float quantityIssued) {
		this.quantityIssued = quantityIssued;
	}

	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}
	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}
	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public String getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	
}
