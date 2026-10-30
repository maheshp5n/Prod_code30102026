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
public class ServiceOrder implements java.io.Serializable,OldPresist<ServiceOrder>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	String manualId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Location wareHouse;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Location repariCenter;
	
	float totalItem;
	float totalReceived;
	
	Date createdDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	
	Date lastModifiedDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ServiceOrderStatus orderStatus;
	
	String shippingMode;
	String shipToName;
	String trackingNumber;
	
	
	String soStatus="Submitted";
	String invoice;
	
	
	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public ServiceOrder() {}
	
	public ServiceOrder(Long id) {
		this.id=id;
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

	public Location getWareHouse() {
		return wareHouse;
	}

	public void setWareHouse(Location wareHouse) {
		this.wareHouse = wareHouse;
	}

	public Location getRepariCenter() {
		return repariCenter;
	}

	public void setRepariCenter(Location repariCenter) {
		this.repariCenter = repariCenter;
	}

	public float getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(float totalItem) {
		this.totalItem = totalItem;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public ServiceOrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(ServiceOrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getShippingMode() {
		return shippingMode;
	}

	public void setShippingMode(String shippingMode) {
		this.shippingMode = shippingMode;
	}

	public String getShipToName() {
		return shipToName;
	}

	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public float getTotalReceived() {
		return totalReceived;
	}

	public void setTotalReceived(float totalReceived) {
		this.totalReceived = totalReceived;
	}
	
	

	public String getSoStatus() {
		return soStatus;
	}

	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
	}



	@javax.persistence.Transient transient ServiceOrder old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ServiceOrder)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ServiceOrder gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
