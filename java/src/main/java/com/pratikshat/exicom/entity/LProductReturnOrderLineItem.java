package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.GenerationType;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LProductReturnOrderLineItem implements java.io.Serializable,OldPresist<LProductReturnOrderLineItem>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = GenerationType.IDENTITY)
	long lproLineitemId;
	String lproLineitemManualId;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LProductReturnOrderItem returnOrderItem;
	String productLineItemSerialNumber;
	float quantityReturned;
	float quantityReceived;
	String reasonForReturn;
	String returnStatus;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	
	long lineItemState;
	
	boolean customerInventory;
	String customerName;
	String customerSapNumber;
	
	String remarks;
	
	public LProductReturnOrderLineItem() {}
	public LProductReturnOrderLineItem(long lproLineitemId) {
		this.lproLineitemId=lproLineitemId;
	}
	/**
	 * @return the lproLineitemId
	 */
	public long getLproLineitemId() {
		return lproLineitemId;
	}
	/**
	 * @return the lproLineitemManualId
	 */
	public String getLproLineitemManualId() {
		return lproLineitemManualId;
	}
	/**
	 * @return the returnOrderItem
	 */
	public LProductReturnOrderItem getReturnOrderItem() {
		return returnOrderItem;
	}
	/**
	 * @return the productLineItemSerialNumber
	 */
	public String getProductLineItemSerialNumber() {
		return productLineItemSerialNumber;
	}
	/**
	 * @return the quantityReturned
	 */
	public float getQuantityReturned() {
		return quantityReturned;
	}
	/**
	 * @return the quantityReceived
	 */
	public float getQuantityReceived() {
		return quantityReceived;
	}
	/**
	 * @return the reasonForReturn
	 */
	public String getReasonForReturn() {
		return reasonForReturn;
	}
	/**
	 * @return the returnStatus
	 */
	public String getReturnStatus() {
		return returnStatus;
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
	 * @param lproLineitemId the lproLineitemId to set
	 */
	public void setLproLineitemId(long lproLineitemId) {
		this.lproLineitemId = lproLineitemId;
	}
	/**
	 * @param lproLineitemManualId the lproLineitemManualId to set
	 */
	public void setLproLineitemManualId(String lproLineitemManualId) {
		this.lproLineitemManualId = lproLineitemManualId;
	}
	/**
	 * @param returnOrderItem the returnOrderItem to set
	 */
	public void setReturnOrderItem(LProductReturnOrderItem returnOrderItem) {
		this.returnOrderItem = returnOrderItem;
	}
	/**
	 * @param productLineItemSerialNumber the productLineItemSerialNumber to set
	 */
	public void setProductLineItemSerialNumber(String productLineItemSerialNumber) {
		this.productLineItemSerialNumber = productLineItemSerialNumber;
	}
	/**
	 * @param quantityReturned the quantityReturned to set
	 */
	public void setQuantityReturned(float quantityReturned) {
		this.quantityReturned = quantityReturned;
	}
	/**
	 * @param quantityReceived the quantityReceived to set
	 */
	public void setQuantityReceived(float quantityReceived) {
		this.quantityReceived = quantityReceived;
	}
	/**
	 * @param reasonForReturn the reasonForReturn to set
	 */
	public void setReasonForReturn(String reasonForReturn) {
		this.reasonForReturn = reasonForReturn;
	}
	/**
	 * @param returnStatus the returnStatus to set
	 */
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
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
	public long getLineItemState() {
		return lineItemState;
	}
	public void setLineItemState(long lineItemState) {
		this.lineItemState = lineItemState;
	}
	public boolean isCustomerInventory() {
		return customerInventory;
	}
	public void setCustomerInventory(boolean customerInventory) {
		this.customerInventory = customerInventory;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerSapNumber() {
		return customerSapNumber;
	}
	public void setCustomerSapNumber(String customerSapNumber) {
		this.customerSapNumber = customerSapNumber;
	}
	
	

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}



	@javax.persistence.Transient transient LProductReturnOrderLineItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LProductReturnOrderLineItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LProductReturnOrderLineItem gOld() {
		return old;
	}
	public String toString() {
			return ""+lproLineitemId;
	}
}
