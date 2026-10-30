package com.pratikshat.exicom.entity;

import javax.persistence.FetchType;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class WarehouseProductStock implements java.io.Serializable,OldPresist<WarehouseProductStock>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long stockId;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	Products product;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	Warehouse warehouse;
	boolean synked;
	float okQty;
	float faultyQty;
	float scrapedQty;
	
	java.util.Date createdDate;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	java.util.Date modifiedDate;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	
	public WarehouseProductStock() {}
	public WarehouseProductStock(long stockId) {
		this.stockId=stockId;
	}
	
	/**
	 * @return the stockId
	 */
	public long getStockId() {
		return stockId;
	}
	/**
	 * @return the product
	 */
	public Products getProduct() {
		return product;
	}
	/**
	 * @return the warehouse
	 */
	public Warehouse getWarehouse() {
		return warehouse;
	}
	/**
	 * @return the okQty
	 */
	public float getOkQty() {
		return okQty;
	}
	/**
	 * @return the faultyQty
	 */
	public float getFaultyQty() {
		return faultyQty;
	}
	/**
	 * @return the createdDate
	 */
	public java.util.Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	/**
	 * @return the modifiedDate
	 */
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @return the modifiedBy
	 */
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param stockId the stockId to set
	 */
	public void setStockId(long stockId) {
		this.stockId = stockId;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Products product) {
		this.product = product;
	}
	/**
	 * @param warehouse the warehouse to set
	 */
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	/**
	 * @param okQty the okQty to set
	 */
	public void setOkQty(float okQty) {
		this.okQty = okQty;
	}
	/**
	 * @param faultyQty the faultyQty to set
	 */
	public void setFaultyQty(float faultyQty) {
		this.faultyQty = faultyQty;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the scrapedQty
	 */
	public float getScrapedQty() {
		return scrapedQty;
	}
	/**
	 * @param scrapedQty the scrapedQty to set
	 */
	public void setScrapedQty(float scrapedQty) {
		this.scrapedQty = scrapedQty;
	}
	/**
	 * @return the synked
	 */
	public boolean isSynked() {
		return synked;
	}
	/**
	 * @param synked the synked to set
	 */
	public void setSynked(boolean synked) {
		this.synked = synked;
	}
	
	

	@javax.persistence.Transient transient WarehouseProductStock old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(WarehouseProductStock)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public WarehouseProductStock gOld() {
		return old;
	}
	public String toString() {
			return ""+stockId;
	}
}
