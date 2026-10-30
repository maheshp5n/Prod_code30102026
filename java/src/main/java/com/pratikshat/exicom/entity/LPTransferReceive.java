package com.pratikshat.exicom.entity;

import java.io.Serializable;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LPTransferReceive implements java.io.Serializable,OldPresist<LPTransferReceive>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long receiveId;
	String receiveManualId;
	java.util.Date receiveDate;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	Location location;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LPTrasfer productTransfer;
	float totalQuantityReceived;	
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails owner;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	
	public LPTransferReceive() {}
	public LPTransferReceive(long receiveId) {
		this.receiveId=receiveId;
	}
	/**
	 * @return the receiveId
	 */
	public long getReceiveId() {
		return receiveId;
	}
	/**
	 * @return the receiveManualId
	 */
	public String getReceiveManualId() {
		return receiveManualId;
	}
	/**
	 * @return the receiveDate
	 */
	public java.util.Date getReceiveDate() {
		return receiveDate;
	}
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * @return the productTransfer
	 */
	public LPTrasfer getProductTransfer() {
		return productTransfer;
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
	 * @param receiveId the receiveId to set
	 */
	public void setReceiveId(long receiveId) {
		this.receiveId = receiveId;
	}
	/**
	 * @param receiveManualId the receiveManualId to set
	 */
	public void setReceiveManualId(String receiveManualId) {
		this.receiveManualId = receiveManualId;
	}
	/**
	 * @param receiveDate the receiveDate to set
	 */
	public void setReceiveDate(java.util.Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	/**
	 * @param productTransfer the productTransfer to set
	 */
	public void setProductTransfer(LPTrasfer productTransfer) {
		this.productTransfer = productTransfer;
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
	
	

	@javax.persistence.Transient transient LPTransferReceive old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LPTransferReceive)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LPTransferReceive gOld() {
		return old;
	}
	public String toString() {
			return ""+receiveId;
	}
}
