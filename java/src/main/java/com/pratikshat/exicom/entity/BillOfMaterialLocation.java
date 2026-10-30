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
public class BillOfMaterialLocation implements java.io.Serializable,OldPresist<BillOfMaterialLocation>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(fetch=FetchType.LAZY)
	BillOfMaterial bom;
	String locationName;
	
	Date createdDate;
	Date modifiedDate;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	
	
	public BillOfMaterialLocation() {
	}
	
	public BillOfMaterialLocation(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BillOfMaterial getBom() {
		return bom;
	}
	public void setBom(BillOfMaterial bom) {
		this.bom = bom;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
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
	
	

	@javax.persistence.Transient transient BillOfMaterialLocation old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BillOfMaterialLocation)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BillOfMaterialLocation gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
