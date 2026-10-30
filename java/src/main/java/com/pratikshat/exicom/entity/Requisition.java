package com.pratikshat.exicom.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class Requisition implements java.io.Serializable,OldPresist<Requisition>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long id;
	String manualId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	Date requiredDate;
	@ManyToOne
	RequisitionStatus requisitionStatus;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	@OneToMany(mappedBy="requisition",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	Set<RequisitionProductDetails> requisitionProductDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Warehouse fromWarehouse;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Warehouse toWarehouse;
	
	boolean receivedStatus;
	boolean sentStatus;
	String sentStatusMessage;
	
	@ManyToOne(fetch = FetchType.LAZY)
	RequisitionProductType requisitionProductType;
	@ManyToOne(fetch = FetchType.LAZY)
	RequisitionApprovalStatus requisitionApprovalStatus;
	
	public Requisition() {
		
	}
	
	
	public Requisition(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getManualId() {
		return manualId;
	}
	public void setManualId(String manualId) {
		this.manualId = manualId;
	}
	public EmployeeDetails getOwner() {
		return owner;
	}
	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}
	public Date getRequiredDate() {
		return requiredDate;
	}
	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}
	public RequisitionStatus getRequisitionStatus() {
		return requisitionStatus;
	}
	public void setRequisitionStatus(RequisitionStatus requisitionStatus) {
		this.requisitionStatus = requisitionStatus;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Set<RequisitionProductDetails> getRequisitionProductDetails() {
		return requisitionProductDetails;
	}
	public void setRequisitionProductDetails(Set<RequisitionProductDetails> requisitionProductDetails) {
		this.requisitionProductDetails = requisitionProductDetails;
	}
	public Warehouse getFromWarehouse() {
		return fromWarehouse;
	}
	public void setFromWarehouse(Warehouse fromWarehouse) {
		this.fromWarehouse = fromWarehouse;
	}
	public Warehouse getToWarehouse() {
		return toWarehouse;
	}
	public void setToWarehouse(Warehouse toWarehouse) {
		this.toWarehouse = toWarehouse;
	}
	public boolean isReceivedStatus() {
		return receivedStatus;
	}
	public void setReceivedStatus(boolean receivedStatus) {
		this.receivedStatus = receivedStatus;
	}
	public boolean isSentStatus() {
		return sentStatus;
	}
	public void setSentStatus(boolean sentStatus) {
		this.sentStatus = sentStatus;
	}
	public String getSentStatusMessage() {
		return sentStatusMessage;
	}
	public void setSentStatusMessage(String sentStatusMessage) {
		this.sentStatusMessage = sentStatusMessage;
	}
	public RequisitionProductType getRequisitionProductType() {
		return requisitionProductType;
	}
	public void setRequisitionProductType(RequisitionProductType requisitionProductType) {
		this.requisitionProductType = requisitionProductType;
	}
	public RequisitionApprovalStatus getRequisitionApprovalStatus() {
		return requisitionApprovalStatus;
	}
	public void setRequisitionApprovalStatus(RequisitionApprovalStatus requisitionApprovalStatus) {
		this.requisitionApprovalStatus = requisitionApprovalStatus;
	}
	
	

	@javax.persistence.Transient transient Requisition old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(Requisition)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public Requisition gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
