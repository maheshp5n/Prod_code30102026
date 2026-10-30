package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

//DEPRECATED 
@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ServiceOrderJobCardReturn implements java.io.Serializable,OldPresist<ServiceOrderJobCardReturn>{
	@Id
	@ManyToOne(fetch=FetchType.LAZY)
	ServiceOrderJobCard jobCard;
	Date returnDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Products product;
	
	String serialNumber;
	
	@ManyToOne(fetch=FetchType.LAZY)
	CircleDetails circle;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Accounts customer;
	
	boolean customerInventory;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ProductLineItemProductState productState;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails lastModifiedBy;
	
	Date createdDate;
	Date lastModifiedDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails returnTo;
	
	boolean received;

	public ServiceOrderJobCard getJobCard() {
		return jobCard;
	}

	public void setJobCard(ServiceOrderJobCard jobCard) {
		this.jobCard = jobCard;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
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

	public CircleDetails getCircle() {
		return circle;
	}

	public void setCircle(CircleDetails circle) {
		this.circle = circle;
	}

	public Accounts getCustomer() {
		return customer;
	}

	public void setCustomer(Accounts customer) {
		this.customer = customer;
	}

	public boolean isCustomerInventory() {
		return customerInventory;
	}

	public void setCustomerInventory(boolean customerInventory) {
		this.customerInventory = customerInventory;
	}

	public ProductLineItemProductState getProductState() {
		return productState;
	}

	public void setProductState(ProductLineItemProductState productState) {
		this.productState = productState;
	}

	public EmployeeDetails getOwner() {
		return owner;
	}

	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}

	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	public EmployeeDetails getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(EmployeeDetails lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public EmployeeDetails getReturnTo() {
		return returnTo;
	}

	public void setReturnTo(EmployeeDetails returnTo) {
		this.returnTo = returnTo;
	}

	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}
	
	

	@javax.persistence.Transient transient ServiceOrderJobCardReturn old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ServiceOrderJobCardReturn)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ServiceOrderJobCardReturn gOld() {
		return old;
	}
	public String toString() {
			return ""+jobCard;
	}
}
