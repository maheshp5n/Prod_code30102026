package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BtLocationProductItem implements Serializable,OldPresist<BtLocationProductItem>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long id;
	String locationProductItemId;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	BtProducts product;
	float quantityInOnHand;
	String quantityUnitOfMasur;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	Location location;
	
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	BtLocationProductItem parentLocationProductItem;
	
	float hslQuantity;
	String hslStatus;
	float issuedQuantity;
	float mslQuantity;
	String mslQuantityStatus;
	float okQuantity;
	float faultyQuantity;
	float customerInventory;
	float scrapQty=0;
	float rejectedQty=0;
	float berQty=0;
	float refurbished=0;
	float doaQuantity=0;
	
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	java.util.Date createdDate;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date modifiedDate;
	
	public BtLocationProductItem() {}
	public BtLocationProductItem(long id) {
		this.id=id;
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the locationProductItemId
	 */
	public String getLocationProductItemId() {
		return locationProductItemId;
	}
	/**
	 * @return the product
	 */
	public BtProducts getProduct() {
		return product;
	}
	/**
	 * @return the quantityInOnHand
	 */
	public float getQuantityInOnHand() {
		return quantityInOnHand;
	}
	/**
	 * @return the quantityUnitOfMasur
	 */
	public String getQuantityUnitOfMasur() {
		return quantityUnitOfMasur;
	}
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * @return the parentLocationProductItem
	 */
	public BtLocationProductItem getParentLocationProductItem() {
		return parentLocationProductItem;
	}
	/**
	 * @return the hslQuantity
	 */
	public float getHslQuantity() {
		return hslQuantity;
	}
	/**
	 * @return the hslStatus
	 */
	public String getHslStatus() {
		return hslStatus;
	}
	/**
	 * @return the issuedQuantity
	 */
	public float getIssuedQuantity() {
		return issuedQuantity;
	}
	/**
	 * @return the mslQuantity
	 */
	public float getMslQuantity() {
		return mslQuantity;
	}
	/**
	 * @return the mslQuantityStatus
	 */
	public String getMslQuantityStatus() {
		return mslQuantityStatus;
	}
	/**
	 * @return the okQuantity
	 */
	public float getOkQuantity() {
		return okQuantity;
	}
	/**
	 * @return the faultyQuantity
	 */
	public float getFaultyQuantity() {
		return faultyQuantity;
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
	 * @param locationProductItemId the locationProductItemId to set
	 */
	public void setLocationProductItemId(String locationProductItemId) {
		this.locationProductItemId = locationProductItemId;
	}
	/**
	 * @param product the product to set
	 */
	public void setBtProducts(BtProducts product) {
		this.product = product;
	}
	/**
	 * @param quantityInOnHand the quantityInOnHand to set
	 */
	public void setQuantityInOnHand(float quantityInOnHand) {
		this.quantityInOnHand = quantityInOnHand;
	}
	/**
	 * @param quantityUnitOfMasur the quantityUnitOfMasur to set
	 */
	public void setQuantityUnitOfMasur(String quantityUnitOfMasur) {
		this.quantityUnitOfMasur = quantityUnitOfMasur;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	/**
	 * @param parentLocationProductItem the parentLocationProductItem to set
	 */
	public void setParentLocationProductItem(BtLocationProductItem parentLocationProductItem) {
		this.parentLocationProductItem = parentLocationProductItem;
	}
	/**
	 * @param hslQuantity the hslQuantity to set
	 */
	public void setHslQuantity(float hslQuantity) {
		this.hslQuantity = hslQuantity;
	}
	/**
	 * @param hslStatus the hslStatus to set
	 */
	public void setHslStatus(String hslStatus) {
		this.hslStatus = hslStatus;
	}
	/**
	 * @param issuedQuantity the issuedQuantity to set
	 */
	public void setIssuedQuantity(float issuedQuantity) {
		this.issuedQuantity = issuedQuantity;
	}
	/**
	 * @param mslQuantity the mslQuantity to set
	 */
	public void setMslQuantity(float mslQuantity) {
		this.mslQuantity = mslQuantity;
	}
	/**
	 * @param mslQuantityStatus the mslQuantityStatus to set
	 */
	public void setMslQuantityStatus(String mslQuantityStatus) {
		this.mslQuantityStatus = mslQuantityStatus;
	}
	/**
	 * @param okQuantity the okQuantity to set
	 */
	public void setOkQuantity(float okQuantity) {
		this.okQuantity = okQuantity;
	}
	/**
	 * @param faultyQuantity the faultyQuantity to set
	 */
	public void setFaultyQuantity(float faultyQuantity) {
		this.faultyQuantity = faultyQuantity;
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
	 * @return the customerInventory
	 */
	public float getCustomerInventory() {
		return customerInventory;
	}
	/**
	 * @param customerInventory the customerInventory to set
	 */
	public void setCustomerInventory(float customerInventory) {
		this.customerInventory = customerInventory;
	}
	public float getScrapQty() {
		return scrapQty;
	}
	public void setScrapQty(float scrapQty) {
		this.scrapQty = scrapQty;
	}
	public float getRejectedQty() {
		return rejectedQty;
	}
	public void setRejectedQty(float rejectedQty) {
		this.rejectedQty = rejectedQty;
	}
	public float getBerQty() {
		return berQty;
	}
	public void setBerQty(float berQty) {
		this.berQty = berQty;
	}
	public float getRefurbished() {
		return refurbished;
	}
	public void setRefurbished(float refurbished) {
		this.refurbished = refurbished;
	}
	
	
	

	public float getDoaQuantity() {
		return doaQuantity;
	}
	public void setDoaQuantity(float doaQuantity) {
		this.doaQuantity = doaQuantity;
	}




	@javax.persistence.Transient transient BtLocationProductItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BtLocationProductItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BtLocationProductItem gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
