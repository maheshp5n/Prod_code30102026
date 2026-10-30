package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class VendorPoDetails implements java.io.Serializable,OldPresist<VendorPoDetails>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne
	VendorPoCategory poCategory;
	
	//EKPO_WERKS Activity Number
	@ManyToOne
	VendorPoType poType;
	//EKKO-EBELN
	String poNumber;
	Date poStartDate;
	Date poExpiryDate;
	//ESLL-MENGE
	float totalQuantity;
	float remainingQuantity;
	//ESLL-TBTWR Rate
	float activityPrice;
	@ManyToOne(fetch=FetchType.LAZY)
	CircleDetails circle;
	
	//EKKN-SAKTO
	String glCode;
	//EKKN-KOSTL
	String costCentre;
	
	String busineedArea;
	String codeForInternalRef;
	String vendorName;
	
	//EKKO-LIFNR
	String vendorCode;
	boolean active;
	boolean synked;
	
	float taxRate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails lastModifiedBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	Date createdDate;
	Date lastModifiedDate;
	Date poDate;
	
	//EKPO-EBELP
	String lineItem;
	//EKPO-KONNR
	String contractNumber;
	//EKPO_WERKS
	String plant;
	//EKKO-FRGKE
	String releaseStatus;
	//EKPO-LOEKZ
	String deletionIndicator;
	///ActivityPriceListCode
	String activityNumber;
	String activityProceListDescription;
	String serviceActivityCode;
	String serviceActivityDescription;
	String glCodeDescription;
	String coasCenterDescription;
	
	public VendorPoDetails() {}
	public VendorPoDetails(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public VendorPoCategory getPoCategory() {
		return poCategory;
	}
	public void setPoCategory(VendorPoCategory poCategory) {
		this.poCategory = poCategory;
	}
	public VendorPoType getPoType() {
		return poType;
	}
	public void setPoType(VendorPoType poType) {
		this.poType = poType;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public Date getPoStartDate() {
		return poStartDate;
	}
	public void setPoStartDate(Date poStartDate) {
		this.poStartDate = poStartDate;
	}
	public Date getPoExpiryDate() {
		return poExpiryDate;
	}
	public void setPoExpiryDate(Date poExpiryDate) {
		this.poExpiryDate = poExpiryDate;
	}
	public float getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(float totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public float getRemainingQuantity() {
		return remainingQuantity;
	}
	public void setRemainingQuantity(float remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
	public float getActivityPrice() {
		return activityPrice;
	}
	public void setActivityPrice(float activityPrice) {
		this.activityPrice = activityPrice;
	}
	public CircleDetails getCircle() {
		return circle;
	}
	public void setCircle(CircleDetails circle) {
		this.circle = circle;
	}
	public String getGlCode() {
		return glCode;
	}
	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}
	public String getCostCentre() {
		return costCentre;
	}
	public void setCostCentre(String costCentre) {
		this.costCentre = costCentre;
	}
	public String getBusineedArea() {
		return busineedArea;
	}
	public void setBusineedArea(String busineedArea) {
		this.busineedArea = busineedArea;
	}
	public String getCodeForInternalRef() {
		return codeForInternalRef;
	}
	public void setCodeForInternalRef(String codeForInternalRef) {
		this.codeForInternalRef = codeForInternalRef;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isSynked() {
		return synked;
	}
	public void setSynked(boolean synked) {
		this.synked = synked;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public EmployeeDetails getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(EmployeeDetails lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
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
	public Date getPoDate() {
		return poDate;
	}
	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}
	public String getLineItem() {
		return lineItem;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public String getReleaseStatus() {
		return releaseStatus;
	}
	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
	public String getDeletionIndicator() {
		return deletionIndicator;
	}
	public void setDeletionIndicator(String deletionIndicator) {
		this.deletionIndicator = deletionIndicator;
	}
	public String getActivityNumber() {
		return activityNumber;
	}
	public void setActivityNumber(String activityNumber) {
		this.activityNumber = activityNumber;
	}
	public String getActivityProceListDescription() {
		return activityProceListDescription;
	}
	public void setActivityProceListDescription(String activityProceListDescription) {
		this.activityProceListDescription = activityProceListDescription;
	}
	public String getServiceActivityCode() {
		return serviceActivityCode;
	}
	public void setServiceActivityCode(String serviceActivityCode) {
		this.serviceActivityCode = serviceActivityCode;
	}
	public String getServiceActivityDescription() {
		return serviceActivityDescription;
	}
	public void setServiceActivityDescription(String serviceActivityDescription) {
		this.serviceActivityDescription = serviceActivityDescription;
	}
	public String getGlCodeDescription() {
		return glCodeDescription;
	}
	public void setGlCodeDescription(String glCodeDescription) {
		this.glCodeDescription = glCodeDescription;
	}
	public String getCoasCenterDescription() {
		return coasCenterDescription;
	}
	public void setCoasCenterDescription(String coasCenterDescription) {
		this.coasCenterDescription = coasCenterDescription;
	}
	public float getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(float taxRate) {
		this.taxRate = taxRate;
	}
	
	

	@javax.persistence.Transient transient VendorPoDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(VendorPoDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public VendorPoDetails gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
