package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class PurchaseOrderDetails implements java.io.Serializable,OldPresist<PurchaseOrderDetails>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String manualId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Accounts supplier;
	
	Date orderDate;
	Date orderValidTill;
	String billToAddressOne;
	String billToAddressTwo;
	String billToCity;
	String billToPinCode;
	Long billStateId;
	String shipToAddressOne;
	String shipToAddressTwo;
	String shipToCity;
	String shipToPinCode;
	Long shipStateId;
	Double poTotalAmount;
	Double poReceivedTotalAmount;
	Double paidAmount;
	
	@ManyToOne(fetch=FetchType.LAZY)
	PurchaseOrderType poType;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	Date createdDate;
	Date modifiedDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	PurchaseOrderStatus poStatus;

	
	public PurchaseOrderDetails() {
	}

	public PurchaseOrderDetails(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getManualId() {
		return manualId;
	}

	public void setManualId(String manualId) {
		this.manualId = manualId;
	}

	public Accounts getSupplier() {
		return supplier;
	}

	public void setSupplier(Accounts supplier) {
		this.supplier = supplier;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getOrderValidTill() {
		return orderValidTill;
	}

	public void setOrderValidTill(Date orderValidTill) {
		this.orderValidTill = orderValidTill;
	}

	public String getBillToAddressOne() {
		return billToAddressOne;
	}

	public void setBillToAddressOne(String billToAddressOne) {
		this.billToAddressOne = billToAddressOne;
	}

	public String getBillToAddressTwo() {
		return billToAddressTwo;
	}

	public void setBillToAddressTwo(String billToAddressTwo) {
		this.billToAddressTwo = billToAddressTwo;
	}

	public String getBillToCity() {
		return billToCity;
	}

	public void setBillToCity(String billToCity) {
		this.billToCity = billToCity;
	}

	public String getBillToPinCode() {
		return billToPinCode;
	}

	public void setBillToPinCode(String billToPinCode) {
		this.billToPinCode = billToPinCode;
	}

	public Long getBillStateId() {
		return billStateId;
	}

	public void setBillStateId(Long billStateId) {
		this.billStateId = billStateId;
	}

	public String getShipToAddressOne() {
		return shipToAddressOne;
	}

	public void setShipToAddressOne(String shipToAddressOne) {
		this.shipToAddressOne = shipToAddressOne;
	}

	public String getShipToAddressTwo() {
		return shipToAddressTwo;
	}

	public void setShipToAddressTwo(String shipToAddressTwo) {
		this.shipToAddressTwo = shipToAddressTwo;
	}

	public String getShipToCity() {
		return shipToCity;
	}

	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}

	public String getShipToPinCode() {
		return shipToPinCode;
	}

	public void setShipToPinCode(String shipToPinCode) {
		this.shipToPinCode = shipToPinCode;
	}

	public Long getShipStateId() {
		return shipStateId;
	}

	public void setShipStateId(Long shipStateId) {
		this.shipStateId = shipStateId;
	}

	public Double getPoTotalAmount() {
		return poTotalAmount;
	}

	public void setPoTotalAmount(Double poTotalAmount) {
		this.poTotalAmount = poTotalAmount;
	}

	public Double getPoReceivedTotalAmount() {
		return poReceivedTotalAmount;
	}

	public void setPoReceivedTotalAmount(Double poReceivedTotalAmount) {
		this.poReceivedTotalAmount = poReceivedTotalAmount;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public PurchaseOrderType getPoType() {
		return poType;
	}

	public void setPoType(PurchaseOrderType poType) {
		this.poType = poType;
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

	public PurchaseOrderStatus getPoStatus() {
		return poStatus;
	}

	public void setPoStatus(PurchaseOrderStatus poStatus) {
		this.poStatus = poStatus;
	}
	
	
	

	@javax.persistence.Transient transient PurchaseOrderDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(PurchaseOrderDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public PurchaseOrderDetails gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
