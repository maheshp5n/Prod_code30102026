package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class ProductLineItem implements java.io.Serializable,OldPresist<ProductLineItem>{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Products product;
	String serialNumber;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ProductLineItemProductState state;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ProductLineItemStatus status;
	
	String position;
	String positionState;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	Boolean customerInventory=false;
	Boolean refurbished=false;
	
	String accountSapNumber;
	String accountName;
	
	String remarks;
	String lotNo;
	public ProductLineItem() {
		
	}
	
	public ProductLineItem(Long id) {
		this.id=id;
	}
	
	@javax.persistence.Transient transient ProductLineItem old;
	
	@javax.persistence.PostLoad
	@Override
	public void old() {
		old=(ProductLineItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}

	@Override
	public ProductLineItem gOld() {
		// TODO Auto-generated method stub
		return old;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public ProductLineItemProductState getState() {
		return state;
	}

	public void setState(ProductLineItemProductState state) {
		this.state = state;
	}

	public ProductLineItemStatus getStatus() {
		return status;
	}

	public void setStatus(ProductLineItemStatus status) {
		this.status = status;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPositionState() {
		return positionState;
	}

	public void setPositionState(String positionState) {
		this.positionState = positionState;
	}

	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String toString() {
		return String.valueOf(this.id);
	}

	public Boolean getCustomerInventory() {
		return customerInventory;
	}

	public void setCustomerInventory(Boolean customerInventory) {
		this.customerInventory = customerInventory;
	}

	public Boolean getRefurbished() {
		return refurbished;
	}

	public void setRefurbished(Boolean refurbished) {
		this.refurbished = refurbished;
	}

	public String getAccountSapNumber() {
		return accountSapNumber;
	}

	public void setAccountSapNumber(String accountSapNumber) {
		this.accountSapNumber = accountSapNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
	
	
}
