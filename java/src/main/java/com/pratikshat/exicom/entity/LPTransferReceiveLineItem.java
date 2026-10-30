package com.pratikshat.exicom.entity;

import java.io.Serializable;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LPTransferReceiveLineItem implements java.io.Serializable,OldPresist<LPTransferReceiveLineItem>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long receiveLineItemId;
	String receiveLineItemManulId;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LPTransferReceiveItem receiveItem;
	java.util.Date receiveDate;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LPTransferLineItem transferLineItem;
	float quantityReceived;
	String productSerialNumber;
	
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails owner;
	
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	
	boolean refurbished=false;
	
	public LPTransferReceiveLineItem() {}
	public LPTransferReceiveLineItem(long receiveLineItemId) {
		this.receiveLineItemId=receiveLineItemId;
	}
	/**
	 * @return the receiveLineItemId
	 */
	public long getReceiveLineItemId() {
		return receiveLineItemId;
	}
	/**
	 * @return the receiveLineItemManulId
	 */
	public String getReceiveLineItemManulId() {
		return receiveLineItemManulId;
	}
	/**
	 * @return the receiveItem
	 */
	public LPTransferReceiveItem getReceiveItem() {
		return receiveItem;
	}
	/**
	 * @return the receiveDate
	 */
	public java.util.Date getReceiveDate() {
		return receiveDate;
	}
	/**
	 * @return the transferLineItem
	 */
	public LPTransferLineItem getTransferLineItem() {
		return transferLineItem;
	}
	/**
	 * @return the quantityReceived
	 */
	public float getQuantityReceived() {
		return quantityReceived;
	}
	/**
	 * @return the productSerialNumber
	 */
	public String getProductSerialNumber() {
		return productSerialNumber;
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
	 * @param receiveLineItemId the receiveLineItemId to set
	 */
	public void setReceiveLineItemId(long receiveLineItemId) {
		this.receiveLineItemId = receiveLineItemId;
	}
	/**
	 * @param receiveLineItemManulId the receiveLineItemManulId to set
	 */
	public void setReceiveLineItemManulId(String receiveLineItemManulId) {
		this.receiveLineItemManulId = receiveLineItemManulId;
	}
	/**
	 * @param receiveItem the receiveItem to set
	 */
	public void setReceiveItem(LPTransferReceiveItem receiveItem) {
		this.receiveItem = receiveItem;
	}
	/**
	 * @param receiveDate the receiveDate to set
	 */
	public void setReceiveDate(java.util.Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	/**
	 * @param transferLineItem the transferLineItem to set
	 */
	public void setTransferLineItem(LPTransferLineItem transferLineItem) {
		this.transferLineItem = transferLineItem;
	}
	/**
	 * @param quantityReceived the quantityReceived to set
	 */
	public void setQuantityReceived(float quantityReceived) {
		this.quantityReceived = quantityReceived;
	}
	/**
	 * @param productSerialNumber the productSerialNumber to set
	 */
	public void setProductSerialNumber(String productSerialNumber) {
		this.productSerialNumber = productSerialNumber;
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
	public boolean isRefurbished() {
		return refurbished;
	}
	public void setRefurbished(boolean refurbished) {
		this.refurbished = refurbished;
	}
	

	@javax.persistence.Transient transient LPTransferReceiveLineItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LPTransferReceiveLineItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LPTransferReceiveLineItem gOld() {
		return old;
	}
	public String toString() {
			return ""+receiveLineItemId;
	}
}
