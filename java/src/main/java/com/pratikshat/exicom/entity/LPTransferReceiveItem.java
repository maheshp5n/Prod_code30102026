package com.pratikshat.exicom.entity;

import java.io.Serializable;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LPTransferReceiveItem implements java.io.Serializable,OldPresist<LPTransferReceiveItem>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long receiveItemId;
	String receiveItemManualId;
	java.util.Date receiveDate;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LPTransferReceive transferReceive;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LPTransferItem transferItem;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	Products product;
	float totalQuantityReceived;
	
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails owner;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdby;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	
	public LPTransferReceiveItem() {}
	public LPTransferReceiveItem(long receiveItemId) {
		this.receiveItemId=receiveItemId;
	}
	/**
	 * @return the receiveItemId
	 */
	public long getReceiveItemId() {
		return receiveItemId;
	}
	/**
	 * @return the receiveItemManualId
	 */
	public String getReceiveItemManualId() {
		return receiveItemManualId;
	}
	/**
	 * @return the receiveDate
	 */
	public java.util.Date getReceiveDate() {
		return receiveDate;
	}
	/**
	 * @return the transferReceive
	 */
	public LPTransferReceive getTransferReceive() {
		return transferReceive;
	}
	/**
	 * @return the transferItem
	 */
	public LPTransferItem getTransferItem() {
		return transferItem;
	}
	/**
	 * @return the product
	 */
	public Products getProduct() {
		return product;
	}
	/**
	 * @return the totalQuantityReceived
	 */
	public float getTotalQuantityReceived() {
		return totalQuantityReceived;
	}
	/**
	 * @return the owner
	 */
	public EmployeeDetails getOwner() {
		return owner;
	}
	/**
	 * @return the createdby
	 */
	public EmployeeDetails getCreatedby() {
		return createdby;
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
	 * @param receiveItemId the receiveItemId to set
	 */
	public void setReceiveItemId(long receiveItemId) {
		this.receiveItemId = receiveItemId;
	}
	/**
	 * @param receiveItemManualId the receiveItemManualId to set
	 */
	public void setReceiveItemManualId(String receiveItemManualId) {
		this.receiveItemManualId = receiveItemManualId;
	}
	/**
	 * @param receiveDate the receiveDate to set
	 */
	public void setReceiveDate(java.util.Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	/**
	 * @param transferReceive the transferReceive to set
	 */
	public void setTransferReceive(LPTransferReceive transferReceive) {
		this.transferReceive = transferReceive;
	}
	/**
	 * @param transferItem the transferItem to set
	 */
	public void setTransferItem(LPTransferItem transferItem) {
		this.transferItem = transferItem;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Products product) {
		this.product = product;
	}
	/**
	 * @param totalQuantityReceived the totalQuantityReceived to set
	 */
	public void setTotalQuantityReceived(float totalQuantityReceived) {
		this.totalQuantityReceived = totalQuantityReceived;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}
	/**
	 * @param createdby the createdby to set
	 */
	public void setCreatedby(EmployeeDetails createdby) {
		this.createdby = createdby;
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
	
	

	@javax.persistence.Transient transient LPTransferReceiveItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LPTransferReceiveItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LPTransferReceiveItem gOld() {
		return old;
	}
	public String toString() {
			return ""+receiveItemId;
	}
}
