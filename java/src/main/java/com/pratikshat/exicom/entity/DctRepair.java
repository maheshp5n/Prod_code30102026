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
public class DctRepair implements java.io.Serializable,OldPresist<DctRepair>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ServiceOrderJobCard jobCard;
	
	String serialNumber;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Products fProduct;
	
	Date repariDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails repairBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	Date createdDate;
	
	Date lastModifiedDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ProductLineItemProductState previousStatus;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ProductLineItemProductState currentStatus;
	
	double totalRepairAmount;
	
	String remarks;
	
	String repariEng;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Products getfProduct() {
		return fProduct;
	}

	public void setfProduct(Products fProduct) {
		this.fProduct = fProduct;
	}

	public Date getRepariDate() {
		return repariDate;
	}

	public void setRepariDate(Date repariDate) {
		this.repariDate = repariDate;
	}

	public EmployeeDetails getRepairBy() {
		return repairBy;
	}

	public void setRepairBy(EmployeeDetails repairBy) {
		this.repairBy = repairBy;
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

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	

	public ProductLineItemProductState getPreviousStatus() {
		return previousStatus;
	}

	public void setPreviousStatus(ProductLineItemProductState previousStatus) {
		this.previousStatus = previousStatus;
	}

	public ProductLineItemProductState getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(ProductLineItemProductState currentStatus) {
		this.currentStatus = currentStatus;
	}

	public double getTotalRepairAmount() {
		return totalRepairAmount;
	}

	public void setTotalRepairAmount(double totalRepairAmount) {
		this.totalRepairAmount = totalRepairAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ServiceOrderJobCard getJobCard() {
		return jobCard;
	}

	public void setJobCard(ServiceOrderJobCard jobCard) {
		this.jobCard = jobCard;
	}
	
	

	public String getRepariEng() {
		return repariEng;
	}

	public void setRepariEng(String repariEng) {
		this.repariEng = repariEng;
	}



	@javax.persistence.Transient transient DctRepair old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(DctRepair)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public DctRepair gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
