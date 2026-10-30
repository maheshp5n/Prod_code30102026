package com.pratikshat.exicom.entity;

import java.io.Serializable;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LocationProductLineItems implements java.io.Serializable,OldPresist<LocationProductLineItems>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long id;
	String locationProductLineItemId;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LocationProductItem locationProductItem;
	String itemSerialNumber;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	ProductLineItemStatus locationProductLineItemStatus;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	ProductLineItemProductState locationProeuctLineItemState;
	boolean inStock;
	boolean replaced;
	boolean returned;
	
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	java.util.Date createdDate;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date modifiedDate;
	
	//IN CASE OF CUSTOMER INVENTORY FLAG WILL BE TRUE AND CUSTOER SAP NUMBER, NAME, REMARK AND LOT NO TO BE UPDATE
	
	boolean customerInventory;
	String customerSapNumber;
	String customerName;
	String lotNo;
	boolean refurbished=false;
	String remarks;
	public LocationProductLineItems() {}
	public LocationProductLineItems(long id) {
		this.id=id;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the locationProductLineItemId
	 */
	public String getLocationProductLineItemId() {
		return locationProductLineItemId;
	}
	/**
	 * @return the locationProductItem
	 */
	public LocationProductItem getLocationProductItem() {
		return locationProductItem;
	}
	/**
	 * @return the itemSerialNumber
	 */
	public String getItemSerialNumber() {
		return itemSerialNumber;
	}
	
	/**
	 * @return the inStock
	 */
	public boolean isInStock() {
		return inStock;
	}
	/**
	 * @return the replaced
	 */
	public boolean isReplaced() {
		return replaced;
	}
	/**
	 * @return the returned
	 */
	public boolean isReturned() {
		return returned;
	}
	/**
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	/**
	 * @return the createdDate
	 */
	public java.util.Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the modifiedBy
	 */
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @return the modifiedDate
	 */
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param locationProductLineItemId the locationProductLineItemId to set
	 */
	public void setLocationProductLineItemId(String locationProductLineItemId) {
		this.locationProductLineItemId = locationProductLineItemId;
	}
	/**
	 * @param locationProductItem the locationProductItem to set
	 */
	public void setLocationProductItem(LocationProductItem locationProductItem) {
		this.locationProductItem = locationProductItem;
	}
	/**
	 * @param itemSerialNumber the itemSerialNumber to set
	 */
	public void setItemSerialNumber(String itemSerialNumber) {
		this.itemSerialNumber = itemSerialNumber;
	}
	
	/**
	 * @param inStock the inStock to set
	 */
	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}
	/**
	 * @param replaced the replaced to set
	 */
	public void setReplaced(boolean replaced) {
		this.replaced = replaced;
	}
	/**
	 * @param returned the returned to set
	 */
	public void setReturned(boolean returned) {
		this.returned = returned;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the locationProductLineItemStatus
	 */
	public ProductLineItemStatus getLocationProductLineItemStatus() {
		return locationProductLineItemStatus;
	}
	/**
	 * @return the locationProeuctLineItemState
	 */
	public ProductLineItemProductState getLocationProeuctLineItemState() {
		return locationProeuctLineItemState;
	}
	/**
	 * @param locationProductLineItemStatus the locationProductLineItemStatus to set
	 */
	public void setLocationProductLineItemStatus(ProductLineItemStatus locationProductLineItemStatus) {
		this.locationProductLineItemStatus = locationProductLineItemStatus;
	}
	/**
	 * @param locationProeuctLineItemState the locationProeuctLineItemState to set
	 */
	public void setLocationProeuctLineItemState(ProductLineItemProductState locationProeuctLineItemState) {
		this.locationProeuctLineItemState = locationProeuctLineItemState;
	}
	/**
	 * @return the customerInventory
	 */
	public boolean isCustomerInventory() {
		return customerInventory;
	}
	/**
	 * @param customerInventory the customerInventory to set
	 */
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
	
	
	

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}




	@javax.persistence.Transient transient LocationProductLineItems old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LocationProductLineItems)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LocationProductLineItems gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
