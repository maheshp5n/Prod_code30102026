package com.pratikshat.exicom.entity;

import java.io.Serializable;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LPTransferItem implements java.io.Serializable,OldPresist<LPTransferItem>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long lpTransferItemId;
	String lpTransferItemIdManualId;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LPTrasfer lpTranfer;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LProductRequestLineItem lpRequestLineItem;
	float transferQuantity;
	float receivedQuantity;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	Products product;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails owner;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	boolean synked=false;
	public LPTransferItem() {}
	public LPTransferItem(long lpTransferItemId) {
		this.lpTransferItemId=lpTransferItemId;
	}
	/**
	 * @return the lpTransferItemId
	 */
	public long getLpTransferItemId() {
		return lpTransferItemId;
	}
	/**
	 * @return the lpTransferItemIdManualId
	 */
	public String getLpTransferItemIdManualId() {
		return lpTransferItemIdManualId;
	}
	/**
	 * @return the lpTranfer
	 */
	public LPTrasfer getLpTranfer() {
		return lpTranfer;
	}
	/**
	 * @return the lpRequestLineItem
	 */
	public LProductRequestLineItem getLpRequestLineItem() {
		return lpRequestLineItem;
	}
	/**
	 * @return the transferQuantity
	 */
	public float getTransferQuantity() {
		return transferQuantity;
	}
	/**
	 * @return the receivedQuantity
	 */
	public float getReceivedQuantity() {
		return receivedQuantity;
	}
	/**
	 * @return the product
	 */
	public Products getProduct() {
		return product;
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
	 * @param lpTransferItemId the lpTransferItemId to set
	 */
	public void setLpTransferItemId(long lpTransferItemId) {
		this.lpTransferItemId = lpTransferItemId;
	}
	/**
	 * @param lpTransferItemIdManualId the lpTransferItemIdManualId to set
	 */
	public void setLpTransferItemIdManualId(String lpTransferItemIdManualId) {
		this.lpTransferItemIdManualId = lpTransferItemIdManualId;
	}
	/**
	 * @param lpTranfer the lpTranfer to set
	 */
	public void setLpTranfer(LPTrasfer lpTranfer) {
		this.lpTranfer = lpTranfer;
	}
	/**
	 * @param lpRequestLineItem the lpRequestLineItem to set
	 */
	public void setLpRequestLineItem(LProductRequestLineItem lpRequestLineItem) {
		this.lpRequestLineItem = lpRequestLineItem;
	}
	/**
	 * @param transferQuantity the transferQuantity to set
	 */
	public void setTransferQuantity(float transferQuantity) {
		this.transferQuantity = transferQuantity;
	}
	/**
	 * @param receivedQuantity the receivedQuantity to set
	 */
	public void setReceivedQuantity(float receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Products product) {
		this.product = product;
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
	public boolean isSynked() {
		return synked;
	}
	public void setSynked(boolean synked) {
		this.synked = synked;
	}
	

	@javax.persistence.Transient transient LPTransferItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LPTransferItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LPTransferItem gOld() {
		return old;
	}
	public String toString() {
			return ""+lpTransferItemId;
	}
}
