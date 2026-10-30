package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.annotation.Transient;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BtWccServiceAppointment implements Serializable,OldPresist<BtWccServiceAppointment>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(fetch=FetchType.LAZY)
	BtWccDetails wcc;
	@ManyToOne(fetch=FetchType.LAZY)
	BtServiceAppointment serviceAppointment;
	@ManyToOne(fetch=FetchType.LAZY)
	BtVendorPoDetails poDetails;
	
	float amount;
	float taxAmount;
	float totalAmount;
	float taxRate;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BtWccDetails getWcc() {
		return wcc;
	}
	public void setWcc(BtWccDetails wcc) {
		this.wcc = wcc;
	}
	public BtServiceAppointment getServiceAppointment() {
		return serviceAppointment;
	}
	public void setServiceAppointment(BtServiceAppointment serviceAppointment) {
		this.serviceAppointment = serviceAppointment;
	}
	public BtVendorPoDetails getPoDetails() {
		return poDetails;
	}
	public void setPoDetails(BtVendorPoDetails poDetails) {
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
	
	@Transient transient BtWccServiceAppointment old;
	@Override
	@PostLoad
	public void old() {
		old=(BtWccServiceAppointment) SerializationUtils.clone(this);
		
	}
	@Override
	public BtWccServiceAppointment gOld() {
		// TODO Auto-generated method stub
		return this.old;
	}
	

}
