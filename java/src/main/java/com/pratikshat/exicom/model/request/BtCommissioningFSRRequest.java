package com.pratikshat.exicom.model.request;

import java.sql.Date;
import java.util.Set;

import com.pratikshat.exicom.entity.BtCommissioningAssetMasterUpdate;

public class BtCommissioningFSRRequest {
	
	private Long appointment;
	private String contactPersonAtSite;
	private Long contactPersonAtSiteContactNumber;
	private Date dateOfSiteVisit;
	private Long siteType;
	private Long btSiteEnvironmentalConditions;
	private Float earthigStatus;
	private Long btCoolingMethod;
	private  Float ambientTemp;
	private String commissionScope;
	private Float totalEquipmentDcLoad;
	private String installtionStatus;
	private Float smpsToCbmsVolGap;
	private Float cbmsToBatVolGap;
	private Float dcLoadCurrent;
	private Float maxModuleTemp;
	private Float chargeVoltage;
	private Float floatVoltage;
	private Long powerPlantMake;
	private Long activeRectifers;
	private Set<BtCommissioningAssetMasterUpdateRequest> btCommissioningAssetMasterUpdateRequests;
	private String exicomEngRemarks;
	private String customerRemarks;
	private String commStatus;
	private String pendingBucket;
	private String pendingReasonExicomBucket;
	private Long pendingReasonCustomerBucket;
	private String complainAttandByEngName;
	private Long  engNumber;
//	//observations
	private String observation1;
	private String observation2;
	private String observation3;
	private String observation4;
	
	public Long getAppointment() {
		return appointment;
	}
	public void setAppointment(Long appointment) {
		this.appointment = appointment;
	}
	public String getContactPersonAtSite() {
		return contactPersonAtSite;
	}
	public void setContactPersonAtSite(String contactPersonAtSite) {
		this.contactPersonAtSite = contactPersonAtSite;
	}
	public Long getContactPersonAtSiteContactNumber() {
		return contactPersonAtSiteContactNumber;
	}
	public void setContactPersonAtSiteContactNumber(Long contactPersonAtSiteContactNumber) {
		this.contactPersonAtSiteContactNumber = contactPersonAtSiteContactNumber;
	}
	public Date getDateOfSiteVisit() {
		return dateOfSiteVisit;
	}
	public void setDateOfSiteVisit(Date dateOfSiteVisit) {
		this.dateOfSiteVisit = dateOfSiteVisit;
	}
	public Long getSiteType() {
		return siteType;
	}
	public void setSiteType(Long siteType) {
		this.siteType = siteType;
	}
	public Long getBtSiteEnvironmentalConditions() {
		return btSiteEnvironmentalConditions;
	}
	public void setBtSiteEnvironmentalConditions(Long btSiteEnvironmentalConditions) {
		this.btSiteEnvironmentalConditions = btSiteEnvironmentalConditions;
	}
	public Float getEarthigStatus() {
		return earthigStatus;
	}
	public void setEarthigStatus(Float earthigStatus) {
		this.earthigStatus = earthigStatus;
	}
	public Long getBtCoolingMethod() {
		return btCoolingMethod;
	}
	public void setBtCoolingMethod(Long btCoolingMethod) {
		this.btCoolingMethod = btCoolingMethod;
	}
	public Float getAmbientTemp() {
		return ambientTemp;
	}
	public void setAmbientTemp(Float ambientTemp) {
		this.ambientTemp = ambientTemp;
	}
	public String getCommissionScope() {
		return commissionScope;
	}
	public void setCommissionScope(String commissionScope) {
		this.commissionScope = commissionScope;
	}
	public Float getTotalEquipmentDcLoad() {
		return totalEquipmentDcLoad;
	}
	public void setTotalEquipmentDcLoad(Float totalEquipmentDcLoad) {
		this.totalEquipmentDcLoad = totalEquipmentDcLoad;
	}
	public String getInstalltionStatus() {
		return installtionStatus;
	}
	public void setInstalltionStatus(String installtionStatus) {
		this.installtionStatus = installtionStatus;
	}
	public Float getSmpsToCbmsVolGap() {
		return smpsToCbmsVolGap;
	}
	public void setSmpsToCbmsVolGap(Float smpsToCbmsVolGap) {
		this.smpsToCbmsVolGap = smpsToCbmsVolGap;
	}
	public Float getCbmsToBatVolGap() {
		return cbmsToBatVolGap;
	}
	public void setCbmsToBatVolGap(Float cbmsToBatVolGap) {
		this.cbmsToBatVolGap = cbmsToBatVolGap;
	}
	public Float getDcLoadCurrent() {
		return dcLoadCurrent;
	}
	public void setDcLoadCurrent(Float dcLoadCurrent) {
		this.dcLoadCurrent = dcLoadCurrent;
	}
	public Float getMaxModuleTemp() {
		return maxModuleTemp;
	}
	public void setMaxModuleTemp(Float maxModuleTemp) {
		this.maxModuleTemp = maxModuleTemp;
	}
	public Float getChargeVoltage() {
		return chargeVoltage;
	}
	public void setChargeVoltage(Float chargeVoltage) {
		this.chargeVoltage = chargeVoltage;
	}
	public Float getFloatVoltage() {
		return floatVoltage;
	}
	public void setFloatVoltage(Float floatVoltage) {
		this.floatVoltage = floatVoltage;
	}
	public Long getPowerPlantMake() {
		return powerPlantMake;
	}
	public void setPowerPlantMake(Long powerPlantMake) {
		this.powerPlantMake = powerPlantMake;
	}
	public Long getActiveRectifers() {
		return activeRectifers;
	}
	public void setActiveRectifers(Long activeRectifers) {
		this.activeRectifers = activeRectifers;
	}
	public Set<BtCommissioningAssetMasterUpdateRequest> getBtCommissioningAssetMasterUpdateRequests() {
		return btCommissioningAssetMasterUpdateRequests;
	}
	public void setBtCommissioningAssetMasterUpdateRequests(
			Set<BtCommissioningAssetMasterUpdateRequest> btCommissioningAssetMasterUpdateRequests) {
		this.btCommissioningAssetMasterUpdateRequests = btCommissioningAssetMasterUpdateRequests;
	}
	public String getExicomEngRemarks() {
		return exicomEngRemarks;
	}
	public void setExicomEngRemarks(String exicomEngRemarks) {
		this.exicomEngRemarks = exicomEngRemarks;
	}
	public String getCustomerRemarks() {
		return customerRemarks;
	}
	public void setCustomerRemarks(String customerRemarks) {
		this.customerRemarks = customerRemarks;
	}
	public String getCommStatus() {
		return commStatus;
	}
	public void setCommStatus(String commStatus) {
		this.commStatus = commStatus;
	}
	
	public String getPendingBucket() {
		return pendingBucket;
	}
	public void setPendingBucket(String pendingBucket) {
		this.pendingBucket = pendingBucket;
	}
	public String getPendingReasonExicomBucket() {
		return pendingReasonExicomBucket;
	}
	public void setPendingReasonExicomBucket(String pendingReasonExicomBucket) {
		this.pendingReasonExicomBucket = pendingReasonExicomBucket;
	}
	public Long getPendingReasonCustomerBucket() {
		return pendingReasonCustomerBucket;
	}
	public void setPendingReasonCustomerBucket(Long pendingReasonCustomerBucket) {
		this.pendingReasonCustomerBucket = pendingReasonCustomerBucket;
	}
	public String getComplainAttandByEngName() {
		return complainAttandByEngName;
	}
	public void setComplainAttandByEngName(String complainAttandByEngName) {
		this.complainAttandByEngName = complainAttandByEngName;
	}
	public Long getEngNumber() {
		return engNumber;
	}
	public void setEngNumber(Long engNumber) {
		this.engNumber = engNumber;
	}
	public String getObservation1() {
		return observation1;
	}
	public void setObservation1(String observation1) {
		this.observation1 = observation1;
	}
	public String getObservation2() {
		return observation2;
	}
	public void setObservation2(String observation2) {
		this.observation2 = observation2;
	}
	public String getObservation3() {
		return observation3;
	}
	public void setObservation3(String observation3) {
		this.observation3 = observation3;
	}
	public String getObservation4() {
		return observation4;
	}
	public void setObservation4(String observation4) {
		this.observation4 = observation4;
	}
	
	
}
