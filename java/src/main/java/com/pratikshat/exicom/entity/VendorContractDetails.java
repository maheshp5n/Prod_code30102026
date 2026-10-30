package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class VendorContractDetails implements java.io.Serializable,OldPresist<VendorContractDetails>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	//	EKKO-EBELN
	String contractNumber;
	//EKPO-EBELP
	String lineItem;
	//EKKO-LIFNR
	String vendorCode;
	//EKKO-BSART
	String aggrementType;
	//EKKO-AEDAT
	String aggrementDate;
	//EKKO-EKORG
	String purOrg;
	//EKKO-EKGRP
	String purGrp;
	//EKPO-PSTYP
	String itemCatagory;
	//EKPO-KNTTP
	String acctAssigmentCat;
	//EKPO-MATKL
	String materialGroup;
	//EKKO-KDATE
	Date validFrom;
	//EKKO-KDATB
	Date validTo;
	//EKKO-KTWRT
	String targetValue;
	//ESLL-SRVPOS
	String activityNumber;
	//ESLL-MENGE
	float qty;
	float remaningQty;
	//ESLL-TBTWR
	float rate;
	//EKKN-SAKTO
	String glCode;
	//EKKN-KOSTL
	String costCenter;
	//EKKO-FRGKE
	String releaseStatus;
	//EKPO-LOEKZ
	String deletionIndecator;
	
	String venodrName;
	
	public VendorContractDetails() {}
	public VendorContractDetails(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getAggrementType() {
		return aggrementType;
	}
	public void setAggrementType(String aggrementType) {
		this.aggrementType = aggrementType;
	}
	public String getAggrementDate() {
		return aggrementDate;
	}
	public void setAggrementDate(String aggrementDate) {
		this.aggrementDate = aggrementDate;
	}
	public String getPurOrg() {
		return purOrg;
	}
	public void setPurOrg(String purOrg) {
		this.purOrg = purOrg;
	}
	public String getPurGrp() {
		return purGrp;
	}
	public void setPurGrp(String purGrp) {
		this.purGrp = purGrp;
	}
	public String getItemCatagory() {
		return itemCatagory;
	}
	public void setItemCatagory(String itemCatagory) {
		this.itemCatagory = itemCatagory;
	}
	public String getAcctAssigmentCat() {
		return acctAssigmentCat;
	}
	public void setAcctAssigmentCat(String acctAssigmentCat) {
		this.acctAssigmentCat = acctAssigmentCat;
	}
	public String getMaterialGroup() {
		return materialGroup;
	}
	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}
	public Date getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public Date getValidTo() {
		return validTo;
	}
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	public String getTargetValue() {
		return targetValue;
	}
	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}
	public String getActivityNumber() {
		return activityNumber;
	}
	public void setActivityNumber(String activityNumber) {
		this.activityNumber = activityNumber;
	}
	public float getQty() {
		return qty;
	}
	public void setQty(float qty) {
		this.qty = qty;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public String getGlCode() {
		return glCode;
	}
	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getReleaseStatus() {
		return releaseStatus;
	}
	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
	public String getDeletionIndecator() {
		return deletionIndecator;
	}
	public void setDeletionIndecator(String deletionIndecator) {
		this.deletionIndecator = deletionIndecator;
	}
	public String getVenodrName() {
		return venodrName;
	}
	public void setVenodrName(String venodrName) {
		this.venodrName = venodrName;
	}
	public float getRemaningQty() {
		return remaningQty;
	}
	public void setRemaningQty(float remaningQty) {
		this.remaningQty = remaningQty;
	}
	

	@javax.persistence.Transient transient VendorContractDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(VendorContractDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public VendorContractDetails gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
