package com.pratikshat.exicom.entity;

import java.io.Serializable;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LPTransferLineItem implements java.io.Serializable,OldPresist<LPTransferLineItem>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long lpTransferLineitemId;
	String lpTransferLineitemIdManualId;
	String productSerialNumber;
	String productStatus;
	float quantityTransfer;
	float quantityReceived;
	boolean received;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LPTransferItem transferItem;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails owner;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	boolean customerInventory=false;
	String customerSapNumber;
	String customerName;
	boolean refurbished=false;
	boolean synked=false;
	public LPTransferLineItem(){}
	public LPTransferLineItem(long lpTransferLineitemId) {
		this.lpTransferLineitemId=lpTransferLineitemId;
	}
	/**
	 * @return the lpTransferLineitemId
	 */
	public long getLpTransferLineitemId() {
		return lpTransferLineitemId;
	}
	/**
	 * @return the lpTransferLineitemIdManualId
	 */
	public String getLpTransferLineitemIdManualId() {
		return lpTransferLineitemIdManualId;
	}
	/**
	 * @return the productSerialNumber
	 */
	public String getProductSerialNumber() {
		return productSerialNumber;
	}
	/**
	 * @return the productStatus
	 */
	public String getProductStatus() {
		return productStatus;
	}
	/**
	 * @return the quantityTransfer
	 */
	public float getQuantityTransfer() {
		return quantityTransfer;
	}
	/**
	 * @return the quantityReceived
	 */
	public float getQuantityReceived() {
		return quantityReceived;
	}
	/**
	 * @return the received
	 */
	public boolean isReceived() {
		return received;
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
	 * @param lpTransferLineitemId the lpTransferLineitemId to set
	 */
	public void setLpTransferLineitemId(long lpTransferLineitemId) {
		this.lpTransferLineitemId = lpTransferLineitemId;
	}
	/**
	 * @param lpTransferLineitemIdManualId the lpTransferLineitemIdManualId to set
	 */
	public void setLpTransferLineitemIdManualId(String lpTransferLineitemIdManualId) {
		this.lpTransferLineitemIdManualId = lpTransferLineitemIdManualId;
	}
	/**
	 * @param productSerialNumber the productSerialNumber to set
	 */
	public void setProductSerialNumber(String productSerialNumber) {
		this.productSerialNumber = productSerialNumber;
	}
	/**
	 * @param productStatus the productStatus to set
	 */
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	/**
	 * @param quantityTransfer the quantityTransfer to set
	 */
	public void setQuantityTransfer(float quantityTransfer) {
		this.quantityTransfer = quantityTransfer;
	}
	/**
	 * @param quantityReceived the quantityReceived to set
	 */
	public void setQuantityReceived(float quantityReceived) {
		this.quantityReceived = quantityReceived;
	}
	/**
	 * @param received the received to set
	 */
	public void setReceived(boolean received) {
		this.received = received;
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
	 * @return the transferItem
	 */
	public LPTransferItem getTransferItem() {
		return transferItem;
	}
	/**
	 * @param transferItem the transferItem to set
	 */
	public void setTransferItem(LPTransferItem transferItem) {
		this.transferItem = transferItem;
	}
	public boolean isCustomerInventory() {
		return customerInventory;
	}
	public void setCustomerInventory(boolean customerInventory) {
		this.customerInventory = customerInventory;
	}
	public String getCustomerSapNumber() {
		return customerSapNumber;
	}
	public void setCustomerSapNumber(String customerSapNumber) {
		this.customerSapNumber = customerSapNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public boolean isRefurbished() {
		return refurbished;
	}
	public void setRefurbished(boolean refurbished) {
		this.refurbished = refurbished;
	}
	public boolean isSynked() {
		return synked;
	}
	public void setSynked(boolean synked) {
		this.synked = synked;
	}
	

	@javax.persistence.Transient transient LPTransferLineItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LPTransferLineItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LPTransferLineItem gOld() {
		return old;
	}
	public String toString() {
			return ""+lpTransferLineitemId;
	}
}
