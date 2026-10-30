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
public class DctRepairProblemMaster implements java.io.Serializable,OldPresist<DctRepairProblemMaster>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Products product;
	
	String faultName;
	@ManyToOne(fetch = FetchType.LAZY)
	DctRepairProblemMaster parentFault;
	String resolution;
	
	public DctRepairProblemMaster() {}
	
	public DctRepairProblemMaster(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Products getProduct() {
		return product;
	}
	public void setProduct(Products product) {
		this.product = product;
	}
	public String getFaultName() {
		return faultName;
	}
	public void setFaultName(String faultName) {
		this.faultName = faultName;
	}
	public DctRepairProblemMaster getParentFault() {
		return parentFault;
	}
	public void setParentFault(DctRepairProblemMaster parentFault) {
		this.parentFault = parentFault;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	
	
	

	@javax.persistence.Transient transient DctRepairProblemMaster old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(DctRepairProblemMaster)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public DctRepairProblemMaster gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
