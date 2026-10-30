package com.pratikshat.exicom.entity;

import java.io.Serializable;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LPReturnOrderReceiveLineItem implements java.io.Serializable,OldPresist<LPReturnOrderReceiveLineItem>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long lproReceiveLineitemId;
	String lproReceiveLineitemManualId;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LPReturnOrderReceiveItem lprOrderReceiveItem;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LProductReturnOrderLineItem lprOrderLineitem;;
	String productSerialNumber;
	java.util.Date receiveDate;
	float totalReceiveQuantity;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails owner;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	
	boolean doa=false;
	
	public LPReturnOrderReceiveLineItem() {}
	public LPReturnOrderReceiveLineItem(long lproReceiveLineitemId) {
		this.lproReceiveLineitemId=lproReceiveLineitemId;
	}
	/**
	 * @return the lproReceiveLineitemId
	 */
	public long getLproReceiveLineitemId() {
		return lproReceiveLineitemId;
	}
	/**
	 * @return the lproReceiveLineitemManualId
	 */
	public String getLproReceiveLineitemManualId() {
		return lproReceiveLineitemManualId;
	}
	/**
	 * @return the lprOrderReceiveItem
	 */
	public LPReturnOrderReceiveItem getLprOrderReceiveItem() {
		return lprOrderReceiveItem;
	}
	/**
	 * @return the lprOrderLineitem
	 */
	public LProductReturnOrderLineItem getLprOrderLineitem() {
		return lprOrderLineitem;
	}
	/**
	 * @return the productSerialNumber
	 */
	public String getProductSerialNumber() {
		return productSerialNumber;
	}
	/**
	 * @return the receiveDate
	 */
	public java.util.Date getReceiveDate() {
		return receiveDate;
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
	 * @param lproReceiveLineitemId the lproReceiveLineitemId to set
	 */
	public void setLproReceiveLineitemId(long lproReceiveLineitemId) {
		this.lproReceiveLineitemId = lproReceiveLineitemId;
	}
	/**
	 * @param lproReceiveLineitemManualId the lproReceiveLineitemManualId to set
	 */
	public void setLproReceiveLineitemManualId(String lproReceiveLineitemManualId) {
		this.lproReceiveLineitemManualId = lproReceiveLineitemManualId;
	}
	/**
	 * @param lprOrderReceiveItem the lprOrderReceiveItem to set
	 */
	public void setLprOrderReceiveItem(LPReturnOrderReceiveItem lprOrderReceiveItem) {
		this.lprOrderReceiveItem = lprOrderReceiveItem;
	}
	/**
	 * @param lprOrderLineitem the lprOrderLineitem to set
	 */
	public void setLprOrderLineitem(LProductReturnOrderLineItem lprOrderLineitem) {
		this.lprOrderLineitem = lprOrderLineitem;
	}
	/**
	 * @param productSerialNumber the productSerialNumber to set
	 */
	public void setProductSerialNumber(String productSerialNumber) {
		this.productSerialNumber = productSerialNumber;
	}
	/**
	 * @param receiveDate the receiveDate to set
	 */
	public void setReceiveDate(java.util.Date receiveDate) {
		this.receiveDate = receiveDate;
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
	
	

	public boolean isDoa() {
		return doa;
	}
	public void setDoa(boolean doa) {
		this.doa = doa;
	}



	@javax.persistence.Transient transient LPReturnOrderReceiveLineItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LPReturnOrderReceiveLineItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LPReturnOrderReceiveLineItem gOld() {
		return old;
	}
	public String toString() {
			return ""+lproReceiveLineitemId;
	}
}
