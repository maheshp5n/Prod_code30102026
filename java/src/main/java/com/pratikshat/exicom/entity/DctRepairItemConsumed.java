package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class DctRepairItemConsumed implements java.io.Serializable,OldPresist<DctRepairItemConsumed>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(fetch=FetchType.LAZY)
	DctRepair repair;
	@ManyToOne(fetch=FetchType.LAZY)
	Products product;
	
	String location;
	float quantity;
	double repairAmount;
	String subAssemblyPartcode;
	
	boolean synked=false;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DctRepair getRepair() {
		return repair;
	}
	public void setRepair(DctRepair repair) {
		this.repair = repair;
	}
	public Products getProduct() {
		return product;
	}
	public void setProduct(Products product) {
		this.product = product;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public double getRepairAmount() {
		return repairAmount;
	}
	public void setRepairAmount(double repairAmount) {
		this.repairAmount = repairAmount;
	}
	public boolean isSynked() {
		return synked;
	}
	public void setSynked(boolean synked) {
		this.synked = synked;
	}
	
	
	

	@javax.persistence.Transient transient DctRepairItemConsumed old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(DctRepairItemConsumed)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public DctRepairItemConsumed gOld() {
		return old;
	}

	public String getSubAssemblyPartcode() {
		return subAssemblyPartcode;
	}

	public void setSubAssemblyPartcode(String subAssemblyPartcode) {
		this.subAssemblyPartcode = subAssemblyPartcode;
	}

	public String toString() {
			return ""+id;
	}
}
