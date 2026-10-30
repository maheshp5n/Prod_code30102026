package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ServiceOrderJobCard implements java.io.Serializable,OldPresist<ServiceOrderJobCard>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String manualId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id_item_id", referencedColumnName = "item_id", nullable = false)
	private ServiceOrderItemReceive itemId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ServiceRecource repairEng;
	
	Date assignedDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails lastModifiedBy;
	
	Date lastModifiedDate;
	
	boolean reparied;
	
	boolean returned;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ProductLineItemProductState productState;
	
	String jobCardStatus="Submitted";

	public ServiceOrderJobCard() {}
	public ServiceOrderJobCard(Long id) {
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

	public ServiceOrderItemReceive getItemId() {
		return itemId;
	}

	public void setItemId(ServiceOrderItemReceive itemId) {
		this.itemId = itemId;
	}

	public ServiceRecource getRepairEng() {
		return repairEng;
	}

	public void setRepairEng(ServiceRecource repairEng) {
		this.repairEng = repairEng;
	}

	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	public EmployeeDetails getOwner() {
		return owner;
	}

	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}

	public EmployeeDetails getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(EmployeeDetails lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public boolean isReparied() {
		return reparied;
	}

	public void setReparied(boolean reparied) {
		this.reparied = reparied;
	}
	public boolean isReturned() {
		return returned;
	}
	public void setReturned(boolean returned) {
		this.returned = returned;
	}
	public ProductLineItemProductState getProductState() {
		return productState;
	}
	public void setProductState(ProductLineItemProductState productState) {
		this.productState = productState;
	}
	
	public String getJobCardStatus() {
		return jobCardStatus;
	}
	public void setJobCardStatus(String jobCardStatus) {
		this.jobCardStatus = jobCardStatus;
	}



	@javax.persistence.Transient transient ServiceOrderJobCard old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ServiceOrderJobCard)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ServiceOrderJobCard gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
