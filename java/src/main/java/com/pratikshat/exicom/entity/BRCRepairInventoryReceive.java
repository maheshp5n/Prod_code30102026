package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.FetchType;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BRCRepairInventoryReceive implements java.io.Serializable,OldPresist<BRCRepairInventoryReceive>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long receivingId;
	String receivingName;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	CaseDetails caseDetails;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	Assets assets;
	String invoiceNumber;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	Products product;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	LegalEntity legalEntity;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	SiteDetails site;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	ContactDetails contact;
	java.util.Date invoiceDate;
	String alterSerialNumber;
	String challanNumber;
	java.util.Date rjilChallanDate;
	String locationStateOrCircle;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.EAGER)
	PackingCondition packingCondition;	
	java.util.Date receivedDate;
	String physicalDamageIfAny;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.EAGER)
	FaultObserved faultObserved;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.EAGER)
	BRCRepiarStatus brcRepariStatus;
	String batteryVoltageBefoureRepair;
	String remarks;
	boolean synked;
	@javax.persistence.OneToOne(mappedBy="receiveDetails")
	BRCRepairInventoryReceiveRepairItem repariItem;
	
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails ownerDetails;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	
	
	public BRCRepairInventoryReceive() {}
	public BRCRepairInventoryReceive(long receivingId) {
		this.receivingId=receivingId;
	}
	
	/**
	 * @return the synked
	 */
	public boolean isSynked() {
		return synked;
	}
	/**
	 * @param synked the synked to set
	 */
	public void setSynked(boolean synked) {
		this.synked = synked;
	}
	/**
	 * @return the receivingId
	 */
	public long getReceivingId() {
		return receivingId;
	}
	/**
	 * @return the receivingName
	 */
	public String getReceivingName() {
		return receivingName;
	}
	/**
	 * @return the caseDetails
	 */
	public CaseDetails getCaseDetails() {
		return caseDetails;
	}
	/**
	 * @return the assets
	 */
	public Assets getAssets() {
		return assets;
	}
	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	/**
	 * @return the product
	 */
	public Products getProduct() {
		return product;
	}
	/**
	 * @return the legalEntity
	 */
	public LegalEntity getLegalEntity() {
		return legalEntity;
	}
	/**
	 * @return the site
	 */
	public SiteDetails getSite() {
		return site;
	}
	/**
	 * @return the contact
	 */
	public ContactDetails getContact() {
		return contact;
	}
	/**
	 * @return the invoiceDate
	 */
	public java.util.Date getInvoiceDate() {
		return invoiceDate;
	}
	/**
	 * @return the alterSerialNumber
	 */
	public String getAlterSerialNumber() {
		return alterSerialNumber;
	}
	/**
	 * @return the challanNumber
	 */
	public String getChallanNumber() {
		return challanNumber;
	}
	/**
	 * @return the rjilChallanDate
	 */
	public java.util.Date getRjilChallanDate() {
		return rjilChallanDate;
	}
	/**
	 * @return the locationStateOrCircle
	 */
	public String getLocationStateOrCircle() {
		return locationStateOrCircle;
	}
	/**
	 * @return the packingCondition
	 */
	public PackingCondition getPackingCondition() {
		return packingCondition;
	}
	/**
	 * @return the receivedDate
	 */
	public java.util.Date getReceivedDate() {
		return receivedDate;
	}
	/**
	 * @return the physicalDamageIfAny
	 */
	public String getPhysicalDamageIfAny() {
		return physicalDamageIfAny;
	}
	/**
	 * @return the faultObserved
	 */
	public FaultObserved getFaultObserved() {
		return faultObserved;
	}
	/**
	 * @return the batteryVoltageBefoureRepair
	 */
	public String getBatteryVoltageBefoureRepair() {
		return batteryVoltageBefoureRepair;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param receivingId the receivingId to set
	 */
	public void setReceivingId(long receivingId) {
		this.receivingId = receivingId;
	}
	/**
	 * @param receivingName the receivingName to set
	 */
	public void setReceivingName(String receivingName) {
		this.receivingName = receivingName;
	}
	/**
	 * @param caseDetails the caseDetails to set
	 */
	public void setCaseDetails(CaseDetails caseDetails) {
		this.caseDetails = caseDetails;
	}
	/**
	 * @param assets the assets to set
	 */
	public void setAssets(Assets assets) {
		this.assets = assets;
	}
	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Products product) {
		this.product = product;
	}
	/**
	 * @param legalEntity the legalEntity to set
	 */
	public void setLegalEntity(LegalEntity legalEntity) {
		this.legalEntity = legalEntity;
	}
	/**
	 * @param site the site to set
	 */
	public void setSite(SiteDetails site) {
		this.site = site;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(ContactDetails contact) {
		this.contact = contact;
	}
	/**
	 * @param invoiceDate the invoiceDate to set
	 */
	public void setInvoiceDate(java.util.Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	/**
	 * @param alterSerialNumber the alterSerialNumber to set
	 */
	public void setAlterSerialNumber(String alterSerialNumber) {
		this.alterSerialNumber = alterSerialNumber;
	}
	/**
	 * @param challanNumber the challanNumber to set
	 */
	public void setChallanNumber(String challanNumber) {
		this.challanNumber = challanNumber;
	}
	/**
	 * @param rjilChallanDate the rjilChallanDate to set
	 */
	public void setRjilChallanDate(java.util.Date rjilChallanDate) {
		this.rjilChallanDate = rjilChallanDate;
	}
	/**
	 * @param locationStateOrCircle the locationStateOrCircle to set
	 */
	public void setLocationStateOrCircle(String locationStateOrCircle) {
		this.locationStateOrCircle = locationStateOrCircle;
	}
	/**
	 * @param packingCondition the packingCondition to set
	 */
	public void setPackingCondition(PackingCondition packingCondition) {
		this.packingCondition = packingCondition;
	}
	/**
	 * @param receivedDate the receivedDate to set
	 */
	public void setReceivedDate(java.util.Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	/**
	 * @param physicalDamageIfAny the physicalDamageIfAny to set
	 */
	public void setPhysicalDamageIfAny(String physicalDamageIfAny) {
		this.physicalDamageIfAny = physicalDamageIfAny;
	}
	/**
	 * @param faultObserved the faultObserved to set
	 */
	public void setFaultObserved(FaultObserved faultObserved) {
		this.faultObserved = faultObserved;
	}
	/**
	 * @param batteryVoltageBefoureRepair the batteryVoltageBefoureRepair to set
	 */
	public void setBatteryVoltageBefoureRepair(String batteryVoltageBefoureRepair) {
		this.batteryVoltageBefoureRepair = batteryVoltageBefoureRepair;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the repariItem
	 */
	public BRCRepairInventoryReceiveRepairItem getRepariItem() {
		return repariItem;
	}
	/**
	 * @param repariItem the repariItem to set
	 */
	public void setRepariItem(BRCRepairInventoryReceiveRepairItem repariItem) {
		this.repariItem = repariItem;
	}
	/**
	 * @return the ownerDetails
	 */
	public EmployeeDetails getOwnerDetails() {
		return ownerDetails;
	}
	/**
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	/**
	 * @return the modifiedBy
	 */
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @return the createdDate
	 */
	public java.util.Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the modifiedDate
	 */
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param ownerDetails the ownerDetails to set
	 */
	public void setOwnerDetails(EmployeeDetails ownerDetails) {
		this.ownerDetails = ownerDetails;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the brcRepariStatus
	 */
	public BRCRepiarStatus getBrcRepariStatus() {
		return brcRepariStatus;
	}
	/**
	 * @param brcRepariStatus the brcRepariStatus to set
	 */
	public void setBrcRepariStatus(BRCRepiarStatus brcRepariStatus) {
		this.brcRepariStatus = brcRepariStatus;
	}
	
	

	@javax.persistence.Transient transient BRCRepairInventoryReceive old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BRCRepairInventoryReceive)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BRCRepairInventoryReceive gOld() {
		return old;
	}
	public String toString() {
			return ""+receivingId;
	}
}
