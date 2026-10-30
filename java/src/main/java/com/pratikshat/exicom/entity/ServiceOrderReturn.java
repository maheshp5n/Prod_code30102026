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
public class ServiceOrderReturn implements java.io.Serializable,OldPresist<ServiceOrderReturn>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String manualId;
	String shipToName;
	String shippingMode;
	String trackingNumber;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Location sourceLoccation;
	@ManyToOne(fetch=FetchType.LAZY)
	Location destinationLocation;
	
	
	float totalItem=0;
	float totalReceive=0;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails ownner;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	
	Date createdDate;
	Date modifiedDate;
	String Invoice;
	public String getInvoice() {
		return Invoice;
	}
	public void setInvoice(String invoice) {
		Invoice = invoice;
	}
	public ServiceOrderReturn() {}
	public ServiceOrderReturn(Long id) {
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
	public String getShipToName() {
		return shipToName;
	}
	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}
	public String getShippingMode() {
		return shippingMode;
	}
	public void setShippingMode(String shippingMode) {
		this.shippingMode = shippingMode;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public EmployeeDetails getOwnner() {
		return ownner;
	}
	public void setOwnner(EmployeeDetails ownner) {
		this.ownner = ownner;
	}
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
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
	public float getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(float totalItem) {
		this.totalItem = totalItem;
	}
	public float getTotalReceive() {
		return totalReceive;
	}
	public void setTotalReceive(float totalReceive) {
		this.totalReceive = totalReceive;
	}
	
	

	public Location getSourceLoccation() {
		return sourceLoccation;
	}
	public void setSourceLoccation(Location sourceLoccation) {
		this.sourceLoccation = sourceLoccation;
	}
	public Location getDestinationLocation() {
		return destinationLocation;
	}
	public void setDestinationLocation(Location destinationLocation) {
		this.destinationLocation = destinationLocation;
	}



	@javax.persistence.Transient transient ServiceOrderReturn old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ServiceOrderReturn)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ServiceOrderReturn gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
