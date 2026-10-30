package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class WccServiceAppointment implements java.io.Serializable,OldPresist<WccServiceAppointment>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(fetch=FetchType.LAZY)
	WccDetails wcc;
	@ManyToOne(fetch=FetchType.LAZY)
	ServiceAppointment serviceAppointment;
	@ManyToOne(fetch=FetchType.LAZY)
	VendorPoDetails poDetails;
	
	float amount;
	float taxAmount;
	float totalAmount;
	float taxRate;
	
	public WccServiceAppointment() {}
	public WccServiceAppointment(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public WccDetails getWcc() {
		return wcc;
	}
	public void setWcc(WccDetails wcc) {
		this.wcc = wcc;
	}
	public ServiceAppointment getServiceAppointment() {
		return serviceAppointment;
	}
	public void setServiceAppointment(ServiceAppointment serviceAppointment) {
		this.serviceAppointment = serviceAppointment;
	}
	public VendorPoDetails getPoDetails() {
		return poDetails;
	}
	public void setPoDetails(VendorPoDetails poDetails) {
		this.poDetails = poDetails;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public float getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(float taxAmount) {
		this.taxAmount = taxAmount;
	}
	public float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public float getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(float taxRate) {
		this.taxRate = taxRate;
	}
	

	@javax.persistence.Transient transient WccServiceAppointment old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(WccServiceAppointment)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public WccServiceAppointment gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
