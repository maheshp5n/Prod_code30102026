package com.pratikshat.exicom.model.response;

import java.util.Date;

public class LocationProductRequestItemResponse {
	long lpRequestItemId;
	String lpRequestItemManualId;
	long lpRequestId;
	String lpRequestManualId;
	String partCode;
	String partDescription;
	float quantityRequired;
	float quantityReceived;
	float quantityIssued;
	String productType;
	
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	String createdByEmailId;
	
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	String modifiedByEmailId;
	
	Date createdDate;
	Date modifiedDate;
	String lineItemStatus;
	
	float sortCloseQty;
	
	public String getLineItemStatus() {
		return lineItemStatus;
	}
	public void setLineItemStatus(String lineItemStatus) {
		this.lineItemStatus = lineItemStatus;
	}
	boolean disabled;
	
	public LocationProductRequestItemResponse() {}
	public LocationProductRequestItemResponse(com.pratikshat.exicom.entity.LProductRequestLineItem items) {
		this.lpRequestItemId=items.getLocationProductRequestLineItemId();
		this.lpRequestItemManualId=items.getLocationProductRequestLineItemManualId();
		this.lineItemStatus=items.getLineItemStatus();
		if(items.getLocationProductRequest()!=null) {
			this.lpRequestId=items.getLocationProductRequest().getLocationProductRequestId();
			this.lpRequestManualId=items.getLocationProductRequest().getLocationProductRequestManualId();	
		}
		if(items.getProduct()!=null) {
			this.partCode=items.getProduct().getProductPartCode();
			this.partDescription=items.getProduct().getPdoductDescription();
			this.productType=items.getProduct().getProductType();
		}
		this.sortCloseQty = items.getShortCloseQty();
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
		
		if(items.getModifiedBy()!=null){
			this.modifiedByFirstName = items.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = items.getModifiedBy().getMiddleName();
			this.modifiedByLastName = items.getModifiedBy().getLastName();
			this.modifiedByEmailId = items.getModifiedBy().getEmailId();
			this.modifiedDate = items.getModifiedDate();
		}
	}
	/**
	 * @return the lpRequestItemId
	 */
	public long getLpRequestItemId() {
		return lpRequestItemId;
	}
	/**
	 * @return the lpRequestItemManualId
	 */
	public String getLpRequestItemManualId() {
		return lpRequestItemManualId;
	}
	/**
	 * @return the lpRequestId
	 */
	public long getLpRequestId() {
		return lpRequestId;
	}
	/**
	 * @return the lpRequestManualId
	 */
	public String getLpRequestManualId() {
		return lpRequestManualId;
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
	 * @param lpRequestItemId the lpRequestItemId to set
	 */
	public void setLpRequestItemId(long lpRequestItemId) {
		this.lpRequestItemId = lpRequestItemId;
	}
	/**
	 * @param lpRequestItemManualId the lpRequestItemManualId to set
	 */
	public void setLpRequestItemManualId(String lpRequestItemManualId) {
		this.lpRequestItemManualId = lpRequestItemManualId;
	}
	/**
	 * @param lpRequestId the lpRequestId to set
	 */
	public void setLpRequestId(long lpRequestId) {
		this.lpRequestId = lpRequestId;
	}
	/**
	 * @param lpRequestManualId the lpRequestManualId to set
	 */
	public void setLpRequestManualId(String lpRequestManualId) {
		this.lpRequestManualId = lpRequestManualId;
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
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	
}
