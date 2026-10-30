package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class CustomerInventoryOut implements java.io.Serializable,OldPresist<CustomerInventoryOut>{
	@Id
	@ManyToOne(fetch=FetchType.LAZY)
	CustomerInventory inventory;
	@ManyToOne(fetch=FetchType.LAZY)
	Products product;
	String serialNumber;
	Date dispatchDate;
	String dispatchChallanNumber;
	String dispatchDocketNumber;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ProductLineItemProductState previousState;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	String lotNo;
	String remark;
	
	public CustomerInventoryOut() {}
	
	public CustomerInventoryOut(CustomerInventory inventory) {
		this.inventory=inventory;
	}
	
	public CustomerInventory getInventory() {
		return inventory;
	}
	public void setInventory(CustomerInventory inventory) {
		this.inventory = inventory;
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
	public Date getDispatchDate() {
		return dispatchDate;
	}
	public void setDispatchDate(Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	public String getDispatchChallanNumber() {
		return dispatchChallanNumber;
	}
	public void setDispatchChallanNumber(String dispatchChallanNumber) {
		this.dispatchChallanNumber = dispatchChallanNumber;
	}
	public String getDispatchDocketNumber() {
		return dispatchDocketNumber;
	}
	public void setDispatchDocketNumber(String dispatchDocketNumber) {
		this.dispatchDocketNumber = dispatchDocketNumber;
	}
	public ProductLineItemProductState getPreviousState() {
		return previousState;
	}
	public void setPreviousState(ProductLineItemProductState previousState) {
		this.previousState = previousState;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public EmployeeDetails getOwner() {
		return owner;
	}
	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}
	

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}



	@javax.persistence.Transient transient CustomerInventoryOut old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CustomerInventoryOut)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CustomerInventoryOut gOld() {
		return old;
	}
	public String toString() {
			return ""+inventory;
	}
}
