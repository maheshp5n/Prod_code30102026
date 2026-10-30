package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class CustomerInventory implements java.io.Serializable,OldPresist<CustomerInventory>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Accounts client;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Products product;
	
	String serialNumber;
	Date receiveDate;
	Date returnDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ProductLineItemProductState previousState;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	CircleDetails circle;
	
	String customerChallanNumber;
	Date customerChallanDate;
	String customerDocketNumber;
	String redmrks;
	String warrantyStatus;
	
	boolean returned;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	String lotNo;

	public CustomerInventory() {}
	public CustomerInventory(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Accounts getClient() {
		return client;
	}

	public void setClient(Accounts client) {
		this.client = client;
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

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public ProductLineItemProductState getPreviousState() {
		return previousState;
	}

	public void setPreviousState(ProductLineItemProductState previousState) {
		this.previousState = previousState;
	}

	public CircleDetails getCircle() {
		return circle;
	}

	public void setCircle(CircleDetails circle) {
		this.circle = circle;
	}

	public String getCustomerChallanNumber() {
		return customerChallanNumber;
	}

	public void setCustomerChallanNumber(String customerChallanNumber) {
		this.customerChallanNumber = customerChallanNumber;
	}

	public Date getCustomerChallanDate() {
		return customerChallanDate;
	}

	public void setCustomerChallanDate(Date customerChallanDate) {
		this.customerChallanDate = customerChallanDate;
	}

	public String getCustomerDocketNumber() {
		return customerDocketNumber;
	}

	public void setCustomerDocketNumber(String customerDocketNumber) {
		this.customerDocketNumber = customerDocketNumber;
	}

	public String getRedmrks() {
		return redmrks;
	}

	public void setRedmrks(String redmrks) {
		this.redmrks = redmrks;
	}

	public String getWarrantyStatus() {
		return warrantyStatus;
	}

	public void setWarrantyStatus(String warrantyStatus) {
		this.warrantyStatus = warrantyStatus;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
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



	@javax.persistence.Transient transient CustomerInventory old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CustomerInventory)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CustomerInventory gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
