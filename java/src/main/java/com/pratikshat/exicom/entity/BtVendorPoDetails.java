package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtVendorPoDetails extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtVendorPoDetails>{

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
	EmployeeDetails owner;

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
	

	

	public BtVendorPoDetails() {
	}

	public BtVendorPoDetails(Long id) {
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

	public float getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(float taxRate) {
		this.taxRate = taxRate;
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

	@Transient transient BtVendorPoDetails old;
	@Override
	@PostLoad
	public void old() {
		old=(BtVendorPoDetails) SerializationUtils.clone(this);
	}

	@Override
	public BtVendorPoDetails gOld() {
		// TODO Auto-generated method stub
		return this.old;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
		
	}

	@Override
	public EmployeeDetails getOwner() {
		// TODO Auto-generated method stub
		return this.owner;
	}

	@Override
	public void setOwner(EmployeeDetails u) {
		this.owner=u;
		
	}

	@Override
	public String getManualId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setManualId(String manualId) {
		// TODO Auto-generated method stub
		
	}

}
