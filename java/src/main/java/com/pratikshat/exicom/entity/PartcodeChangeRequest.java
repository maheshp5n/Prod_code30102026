package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class PartcodeChangeRequest implements java.io.Serializable,OldPresist<PartcodeChangeRequest> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	String manualId;
	
	Date requestDate;
	String circle;
	String engineerName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Products faultyProduct;
	
	String faultyProductSerialNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Products consumedProduct;
	
	String consumedProductSerialNumber;
	String saId;
	String saManualId;
	String priceVariance;
	String assignedTo;
	
	Boolean approvedStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	
	Date createDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails modifiedBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails initiatedBy;
	
	Date modifiedDate;
	
	String flowType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Location sourceLocation;
	
	//INITIATOR LOCATION NAME
	String locationName;
	
	//INITIATOR LEGAL ENTITY
	String legalEntity;
	
	//REFRENCE FOR NON WORK FLOW PART CODE CHANGE REQUEST
	@ManyToOne(fetch=FetchType.LAZY)
	PartcodeChangeSa partcodeChangeSa;
	
	@Transient 
	transient PartcodeChangeRequest old;
	
	@Override
	@PostLoad
	public void old() {
		this.old=(PartcodeChangeRequest)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public PartcodeChangeRequest gOld() {
		// TODO Auto-generated method stub
		return old;
	}
	
	public PartcodeChangeRequest() {}
	public PartcodeChangeRequest(Long id) {
		this.id=id;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public String getEngineerName() {
		return engineerName;
	}
	public void setEngineerName(String engineerName) {
		this.engineerName = engineerName;
	}
	public Products getFaultyProduct() {
		return faultyProduct;
	}
	public void setFaultyProduct(Products faultyProduct) {
		this.faultyProduct = faultyProduct;
	}
	public String getFaultyProductSerialNumber() {
		return faultyProductSerialNumber;
	}
	public void setFaultyProductSerialNumber(String faultyProductSerialNumber) {
		this.faultyProductSerialNumber = faultyProductSerialNumber;
	}
	public Products getConsumedProduct() {
		return consumedProduct;
	}
	public void setConsumedProduct(Products consumedProduct) {
		this.consumedProduct = consumedProduct;
	}
	public String getConsumedProductSerialNumber() {
		return consumedProductSerialNumber;
	}
	public void setConsumedProductSerialNumber(String consumedProductSerialNumber) {
		this.consumedProductSerialNumber = consumedProductSerialNumber;
	}
	public String getSaId() {
		return saId;
	}
	public void setSaId(String saId) {
		this.saId = saId;
	}
	public String getSaManualId() {
		return saManualId;
	}
	public void setSaManualId(String saManualId) {
		this.saManualId = saManualId;
	}
	public String getPriceVariance() {
		return priceVariance;
	}
	public void setPriceVariance(String priceVariance) {
		this.priceVariance = priceVariance;
	}
	public Boolean getApprovedStatus() {
		return approvedStatus;
	}
	public void setApprovedStatus(Boolean approvedStatus) {
		this.approvedStatus = approvedStatus;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
		
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public EmployeeDetails getInitiatedBy() {
		return initiatedBy;
	}
	public void setInitiatedBy(EmployeeDetails initiatedBy) {
		this.initiatedBy = initiatedBy;
	}
	
	public String getFlowType() {
		return flowType;
	}
	
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	
	public Location getSourceLocation() {
		return sourceLocation;
	}
	
	public void setSourceLocation(Location sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	
	
	public PartcodeChangeSa getPartcodeChangeSa() {
		return partcodeChangeSa;
	}
	public void setPartcodeChangeSa(PartcodeChangeSa partcodeChangeSa) {
		this.partcodeChangeSa = partcodeChangeSa;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.id);
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getLegalEntity() {
		return legalEntity;
	}
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}
	public String getManualId() {
		return manualId;
	}
	public void setManualId(String manualId) {
		this.manualId = manualId;
	}
	
	
}
