package com.pratikshat.exicom.entity;

import java.io.Serializable;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LProductReturnOrderItem implements java.io.Serializable,OldPresist<LProductReturnOrderItem>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long lproItemid;
	String lproItemManualId;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LProductReturnOrder locationProductReturnOrder;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LocationProductItem productItem;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	Products product;
	java.util.Date returnDate;
	float totalReturnQuantity;
	float totalReceiveQuantity;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails owner;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	boolean synked=false;
	public LProductReturnOrderItem() {}
	public LProductReturnOrderItem(long lproItemid) {
		this.lproItemid=lproItemid;
	}
	
	/**
	 * @return the locationProductReturnOrder
	 */
	public LProductReturnOrder getLocationProductReturnOrder() {
		return locationProductReturnOrder;
	}
	/**
	 * @return the productItem
	 */
	public LocationProductItem getProductItem() {
		return productItem;
	}
	/**
	 * @return the product
	 */
	public Products getProduct() {
		return product;
	}
	/**
	 * @return the returnDate
	 */
	public java.util.Date getReturnDate() {
		return returnDate;
	}
	/**
	 * @return the totalReturnQuantity
	 */
	public float getTotalReturnQuantity() {
		return totalReturnQuantity;
	}
	/**
	 * @return the totalReceiveQuantity
	 */
	public float getTotalReceiveQuantity() {
		return totalReceiveQuantity;
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
	 * @param locationProductReturnOrder the locationProductReturnOrder to set
	 */
	public void setLocationProductReturnOrder(LProductReturnOrder locationProductReturnOrder) {
		this.locationProductReturnOrder = locationProductReturnOrder;
	}
	/**
	 * @param productItem the productItem to set
	 */
	public void setProductItem(LocationProductItem productItem) {
		this.productItem = productItem;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Products product) {
		this.product = product;
	}
	/**
	 * @param returnDate the returnDate to set
	 */
	public void setReturnDate(java.util.Date returnDate) {
		this.returnDate = returnDate;
	}
	/**
	 * @param totalReturnQuantity the totalReturnQuantity to set
	 */
	public void setTotalReturnQuantity(float totalReturnQuantity) {
		this.totalReturnQuantity = totalReturnQuantity;
	}
	/**
	 * @param totalReceiveQuantity the totalReceiveQuantity to set
	 */
	public void setTotalReceiveQuantity(float totalReceiveQuantity) {
		this.totalReceiveQuantity = totalReceiveQuantity;
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
	/**
	 * @return the lproItemid
	 */
	public long getLproItemid() {
		return lproItemid;
	}
	/**
	 * @return the lproItemManualId
	 */
	public String getLproItemManualId() {
		return lproItemManualId;
	}
	/**
	 * @param lproItemid the lproItemid to set
	 */
	public void setLproItemid(long lproItemid) {
		this.lproItemid = lproItemid;
	}
	/**
	 * @param lproItemManualId the lproItemManualId to set
	 */
	public void setLproItemManualId(String lproItemManualId) {
		this.lproItemManualId = lproItemManualId;
	}
	public boolean isSynked() {
		return synked;
	}
	public void setSynked(boolean synked) {
		this.synked = synked;
	}
	

	@javax.persistence.Transient transient LProductReturnOrderItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LProductReturnOrderItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LProductReturnOrderItem gOld() {
		return old;
	}
	public String toString() {
			return ""+lproItemid;
	}
}
