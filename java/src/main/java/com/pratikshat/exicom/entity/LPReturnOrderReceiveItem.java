package com.pratikshat.exicom.entity;

import java.io.Serializable;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LPReturnOrderReceiveItem implements java.io.Serializable,OldPresist<LPReturnOrderReceiveItem>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long receiveReturnOrderItemId;
	String receiveReturnOrderItemManualId;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LPReturnOrderReceive locationProductReturnOrderReceive;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LProductReturnOrderItem locationProductReturnOrderItem;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LocationProductItem locationProductItem;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	Products products;
	float totalReceiveQuantity;
	java.util.Date receiveDate;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails owner;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	
	public LPReturnOrderReceiveItem() {}
	public LPReturnOrderReceiveItem(long receiveReturnOrderItemId) {
		this.receiveReturnOrderItemId=receiveReturnOrderItemId;
	}
	/**
	 * @return the receiveReturnOrderItemId
	 */
	public long getReceiveReturnOrderItemId() {
		return receiveReturnOrderItemId;
	}
	/**
	 * @return the receiveReturnOrderItemManualId
	 */
	public String getReceiveReturnOrderItemManualId() {
		return receiveReturnOrderItemManualId;
	}
	/**
	 * @return the locationProductReturnOrderReceive
	 */
	public LPReturnOrderReceive getLocationProductReturnOrderReceive() {
		return locationProductReturnOrderReceive;
	}
	/**
	 * @return the locationProductReturnOrderItem
	 */
	public LProductReturnOrderItem getLocationProductReturnOrderItem() {
		return locationProductReturnOrderItem;
	}
	/**
	 * @return the locationProductItem
	 */
	public LocationProductItem getLocationProductItem() {
		return locationProductItem;
	}
	/**
	 * @return the products
	 */
	public Products getProducts() {
		return products;
	}
	/**
	 * @return the totalReceiveQuantity
	 */
	public float getTotalReceiveQuantity() {
		return totalReceiveQuantity;
	}
	/**
	 * @return the receiveDate
	 */
	public java.util.Date getReceiveDate() {
		return receiveDate;
	}
	/**
	 * @return the owner
	 */
	public EmployeeDetails getOwner() {
		return owner;
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
	 * @param receiveReturnOrderItemId the receiveReturnOrderItemId to set
	 */
	public void setReceiveReturnOrderItemId(long receiveReturnOrderItemId) {
		this.receiveReturnOrderItemId = receiveReturnOrderItemId;
	}
	/**
	 * @param receiveReturnOrderItemManualId the receiveReturnOrderItemManualId to set
	 */
	public void setReceiveReturnOrderItemManualId(String receiveReturnOrderItemManualId) {
		this.receiveReturnOrderItemManualId = receiveReturnOrderItemManualId;
	}
	/**
	 * @param locationProductReturnOrderReceive the locationProductReturnOrderReceive to set
	 */
	public void setLocationProductReturnOrderReceive(LPReturnOrderReceive locationProductReturnOrderReceive) {
		this.locationProductReturnOrderReceive = locationProductReturnOrderReceive;
	}
	/**
	 * @param locationProductReturnOrderItem the locationProductReturnOrderItem to set
	 */
	public void setLocationProductReturnOrderItem(LProductReturnOrderItem locationProductReturnOrderItem) {
		this.locationProductReturnOrderItem = locationProductReturnOrderItem;
	}
	/**
	 * @param locationProductItem the locationProductItem to set
	 */
	public void setLocationProductItem(LocationProductItem locationProductItem) {
		this.locationProductItem = locationProductItem;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(Products products) {
		this.products = products;
	}
	/**
	 * @param totalReceiveQuantity the totalReceiveQuantity to set
	 */
	public void setTotalReceiveQuantity(float totalReceiveQuantity) {
		this.totalReceiveQuantity = totalReceiveQuantity;
	}
	/**
	 * @param receiveDate the receiveDate to set
	 */
	public void setReceiveDate(java.util.Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
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
	
	

	@javax.persistence.Transient transient LPReturnOrderReceiveItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LPReturnOrderReceiveItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LPReturnOrderReceiveItem gOld() {
		return old;
	}
	public String toString() {
			return ""+receiveReturnOrderItemId;
	}
}
