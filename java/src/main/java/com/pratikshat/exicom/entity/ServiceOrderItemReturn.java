package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ServiceOrderItemReturn implements java.io.Serializable,OldPresist<ServiceOrderItemReturn>{
	@Id
	Long id; //USE AS SERVICE ORDER ITEM RECEIVE ID
	
	//@OneToOne(fetch=FetchType.LAZY)
	//ServiceOrderItemReceive receiveId;
	
	Date returnDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Location returnWarehouse;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ServiceOrderReturn serviceOrderReturn;
	
	boolean received;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ProductLineItemProductState state;
	
	boolean customerInventory;
	boolean refurbished;
	String customerName;
	String customerSapNumber;
	
	Date createdDate;
	
	Date modifiedDate;
	
	String remarks;
	String lotNo;
	
	public ServiceOrderItemReturn() {}
	public ServiceOrderItemReturn(Long id) {
		this.id=id;
	}
	
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public Location getReturnWarehouse() {
		return returnWarehouse;
	}
	public void setReturnWarehouse(Location returnWarehouse) {
		this.returnWarehouse = returnWarehouse;
	}
	public boolean isReceived() {
		return received;
	}
	public void setReceived(boolean received) {
		this.received = received;
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
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public ProductLineItemProductState getState() {
		return state;
	}
	public void setState(ProductLineItemProductState state) {
		this.state = state;
	}
	public boolean isCustomerInventory() {
		return customerInventory;
	}
	public void setCustomerInventory(boolean customerInventory) {
		this.customerInventory = customerInventory;
	}
	public boolean isRefurbished() {
		return refurbished;
	}
	public void setRefurbished(boolean refurbished) {
		this.refurbished = refurbished;
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
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ServiceOrderReturn getServiceOrderReturn() {
		return serviceOrderReturn;
	}
	public void setServiceOrderReturn(ServiceOrderReturn serviceOrderReturn) {
		this.serviceOrderReturn = serviceOrderReturn;
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



	@javax.persistence.Transient transient ServiceOrderItemReturn old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ServiceOrderItemReturn)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ServiceOrderItemReturn gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
