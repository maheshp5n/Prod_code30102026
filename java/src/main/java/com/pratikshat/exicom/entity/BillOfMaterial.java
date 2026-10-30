package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BillOfMaterial implements java.io.Serializable,OldPresist<BillOfMaterial>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Products fProduct;
	@ManyToOne(fetch=FetchType.LAZY)
	Products rProduct;
	Float reqQty;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	Date createdDate;
	Date modifiedDate;
	
	//SAP PROPERTY 
	String level;
	String umo;
	String materialType;
	String plant1;
	String plant2;
	
	
	public BillOfMaterial() {}
	public BillOfMaterial(Long id) {
		
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Products getfProduct() {
		return fProduct;
	}
	public void setfProduct(Products fProduct) {
		this.fProduct = fProduct;
	}
	public Products getrProduct() {
		return rProduct;
	}
	public void setrProduct(Products rProduct) {
		this.rProduct = rProduct;
	}
	public Float getReqQty() {
		return reqQty;
	}
	public void setReqQty(Float reqQty) {
		this.reqQty = reqQty;
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

	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getUmo() {
		return umo;
	}
	public void setUmo(String umo) {
		this.umo = umo;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getPlant1() {
		return plant1;
	}
	public void setPlant1(String plant1) {
		this.plant1 = plant1;
	}
	public String getPlant2() {
		return plant2;
	}
	public void setPlant2(String plant2) {
		this.plant2 = plant2;
	}



	@javax.persistence.Transient transient BillOfMaterial old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BillOfMaterial)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BillOfMaterial gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
