package com.pratikshat.exicom.entity;

import java.io.Serializable;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LPReturnOrderReceive implements java.io.Serializable,OldPresist<LPReturnOrderReceive>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long returnOrderReceiveId;
	String returnOrderReceiveManualId;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	Location location;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LProductReturnOrder returnOrder;
	java.util.Date receiveDate;
	float totalQuantityReceived;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails owner;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifirdBy;
	
	public LPReturnOrderReceive() {}
	public LPReturnOrderReceive(long returnOrderReceiveId) {
		this.returnOrderReceiveId=returnOrderReceiveId;
	}
	/**
	 * @return the returnOrderReceiveId
	 */
	public long getReturnOrderReceiveId() {
		return returnOrderReceiveId;
	}
	/**
	 * @return the returnOrderReceiveManualId
	 */
	public String getReturnOrderReceiveManualId() {
		return returnOrderReceiveManualId;
	}
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * @return the returnOrder
	 */
	public LProductReturnOrder getReturnOrder() {
		return returnOrder;
	}
	/**
	 * @return the receiveDate
	 */
	public java.util.Date getReceiveDate() {
		return receiveDate;
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
	 * @return the modifirdBy
	 */
	public java.util.Date getModifirdBy() {
		return modifirdBy;
	}
	/**
	 * @param returnOrderReceiveId the returnOrderReceiveId to set
	 */
	public void setReturnOrderReceiveId(long returnOrderReceiveId) {
		this.returnOrderReceiveId = returnOrderReceiveId;
	}
	/**
	 * @param returnOrderReceiveManualId the returnOrderReceiveManualId to set
	 */
	public void setReturnOrderReceiveManualId(String returnOrderReceiveManualId) {
		this.returnOrderReceiveManualId = returnOrderReceiveManualId;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	/**
	 * @param returnOrder the returnOrder to set
	 */
	public void setReturnOrder(LProductReturnOrder returnOrder) {
		this.returnOrder = returnOrder;
	}
	/**
	 * @param receiveDate the receiveDate to set
	 */
	public void setReceiveDate(java.util.Date receiveDate) {
		this.receiveDate = receiveDate;
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
	 * @param modifirdBy the modifirdBy to set
	 */
	public void setModifirdBy(java.util.Date modifirdBy) {
		this.modifirdBy = modifirdBy;
	}
	
	

	@javax.persistence.Transient transient LPReturnOrderReceive old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LPReturnOrderReceive)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LPReturnOrderReceive gOld() {
		return old;
	}
	public String toString() {
			return ""+returnOrderReceiveId;
	}
}
