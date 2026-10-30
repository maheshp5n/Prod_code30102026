package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Column;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LProductRequestLineItem implements java.io.Serializable,OldPresist<LProductRequestLineItem>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long locationProductRequestLineItemId;
	String locationProductRequestLineItemManualId;//ID initials will start with PRLI-
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	Products product;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LProductRequest locationProductRequest;
	float quantity;
	float quantityReceived;
	float quantityIssued;
	@Column(name="short_close_qty")
	float shortCloseQty;
	
	
	String description;
	
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	
	EmployeeDetails modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	boolean synked=false;
	
	String lineItemStatus;
	
	public String getLineItemStatus() {
		return lineItemStatus;
	}
	public void setLineItemStatus(String lineItemStatus) {
		this.lineItemStatus = lineItemStatus;
	}



	boolean disabledItem=false;
	
	public LProductRequestLineItem() {}
	public LProductRequestLineItem(long locationProductRequestLineItemId) {
		this.locationProductRequestLineItemId=locationProductRequestLineItemId;
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
	 * @return the product
	 */
	public Products getProduct() {
		return product;
	}
	/**
	 * @return the locationProductRequest
	 */
	public LProductRequest getLocationProductRequest() {
		return locationProductRequest;
	}
	/**
	 * @return the quantity
	 */
	public float getQuantity() {
		return quantity;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
	 * @param product the product to set
	 */
	public void setProduct(Products product) {
		this.product = product;
	}
	/**
	 * @param locationProductRequest the locationProductRequest to set
	 */
	public void setLocationProductRequest(LProductRequest locationProductRequest) {
		this.locationProductRequest = locationProductRequest;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(float quantity) {
		this.quantity = quantity;
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
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	public boolean isSynked() {
		return synked;
	}
	public void setSynked(boolean synked) {
		this.synked = synked;
	}
	
	
	

	public float getShortCloseQty() {
		return shortCloseQty;
	}
	public void setShortCloseQty(float shortCloseQty) {
		this.shortCloseQty = shortCloseQty;
	}



	@javax.persistence.Transient transient LProductRequestLineItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LProductRequestLineItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LProductRequestLineItem gOld() {
		return old;
	}
	public String toString() {
			return ""+locationProductRequestLineItemId;
	}
	public boolean isDisabledItem() {
		return disabledItem;
	}
	public void setDisabledItem(boolean disabledItem) {
		this.disabledItem = disabledItem;
	}
	
	
}
