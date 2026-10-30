package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ServiceOrderItem implements java.io.Serializable,OldPresist<ServiceOrderItem>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ServiceOrder serviceOrder;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Products product;
	
	String serialNumber;
	
	String circle;
	
	boolean customerInventory;
	
	String customerSap;
	
	String customerName;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ProductLineItemProductState state;
	
	boolean synked;
	
	boolean received;
	
	String remark;
	String lotNo;

	public ServiceOrderItem(){}
	public ServiceOrderItem(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceOrder getServiceOrder() {
		return serviceOrder;
	}

	public void setServiceOrder(ServiceOrder serviceOrder) {
		this.serviceOrder = serviceOrder;
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

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public boolean isCustomerInventory() {
		return customerInventory;
	}

	public void setCustomerInventory(boolean customerInventory) {
		this.customerInventory = customerInventory;
	}

	public String getCustomerSap() {
		return customerSap;
	}

	public void setCustomerSap(String customerSap) {
		this.customerSap = customerSap;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public boolean isSynked() {
		return synked;
	}

	public void setSynked(boolean synked) {
		this.synked = synked;
	}

	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}
	public ProductLineItemProductState getState() {
		return state;
	}
	public void setState(ProductLineItemProductState state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}




	@javax.persistence.Transient transient ServiceOrderItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ServiceOrderItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ServiceOrderItem gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
