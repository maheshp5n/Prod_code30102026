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
public class MrnDetails implements java.io.Serializable,OldPresist<MrnDetails>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String manualId;
	@ManyToOne(fetch=FetchType.LAZY)
	Accounts vendor;
	Date mrnDate;
	String poReferenceNumber;
	
	String vendorInvoiceNumber;
	String vendorChallanNumber;
	
	String transporter;
	String vehicleNumber;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Location warehouse;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	Date createdDate;
	Date modifiedDate;
	
	
	
	public MrnDetails() {}
	public MrnDetails(Long id) {
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
	public Accounts getVendor() {
		return vendor;
	}
	public void setVendor(Accounts vendor) {
		this.vendor = vendor;
	}
	public Date getMrnDate() {
		return mrnDate;
	}
	public void setMrnDate(Date mrnDate) {
		this.mrnDate = mrnDate;
	}
	public String getPoReferenceNumber() {
		return poReferenceNumber;
	}
	public void setPoReferenceNumber(String poReferenceNumber) {
		this.poReferenceNumber = poReferenceNumber;
	}
	public String getVendorInvoiceNumber() {
		return vendorInvoiceNumber;
	}
	public void setVendorInvoiceNumber(String vendorInvoiceNumber) {
		this.vendorInvoiceNumber = vendorInvoiceNumber;
	}
	public String getVendorChallanNumber() {
		return vendorChallanNumber;
	}
	public void setVendorChallanNumber(String vendorChallanNumber) {
		this.vendorChallanNumber = vendorChallanNumber;
	}
	public String getTransporter() {
		return transporter;
	}
	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public Location getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Location warehouse) {
		this.warehouse = warehouse;
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
	
	

	@javax.persistence.Transient transient MrnDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(MrnDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public MrnDetails gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
