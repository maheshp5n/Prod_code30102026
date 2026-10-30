package com.pratikshat.exicom.model.request;

import java.util.Date;
import java.util.Set;

public class BtServiceFSRUpdateRequest {
	
	private long serviceFSRId;
	private long appointment;
	private long btSiteEnvironmentalConditions;
	private long btCoolingMethod;
	private long btPowerPlantMake;
	private long btResolutionMethod;
	private long btSiteType;
	private long btSolutionImparted;
	
	private String contactPersonAtSite;
	private long contactPersonAtSiteNo;
	private Date dateOfSiteVisit;
	
	private float earthigStatus;
	private  float ambientTemp;
	 
	private float totalEquipmentDcLoad;
	private float activeRectifiers;
	private float smpsToCbmsVolGap;
	private float cbmsToBatVolGap;
	private float dcLoadCurrent;
	private float maxModuleTemp;
	private String faultObservedByExicomEng;
	private  String correctiveActionInDetails;
	private String caseStatus;
	private String pendingBucket;
	private String pendingReasonExicomBucket;
	private long btPendingReasonCustomerBucket;
	private String complainAttendByEngName;
	private long engNumber;
	private String customerRemark;
//	//observations
	private String observation1;
	private String observation2;
	private String observation3;
	private String observation4;
	
	private Set<BtAssetMasterUpdateRequest> asseRequests;


	public long getServiceFSRId() {
		return serviceFSRId;
	}

	public void setServiceFSRId(long serviceFSRId) {
		this.serviceFSRId = serviceFSRId;
	}

	public long getAppointment() {
		return appointment;
	}

	public void setAppointment(long appointment) {
		this.appointment = appointment;
	}

	public long getBtSiteEnvironmentalConditions() {
		return btSiteEnvironmentalConditions;
	}

	public void setBtSiteEnvironmentalConditions(long btSiteEnvironmentalConditions) {
		this.btSiteEnvironmentalConditions = btSiteEnvironmentalConditions;
	}

	public long getBtCoolingMethod() {
		return btCoolingMethod;
	}

	public void setBtCoolingMethod(long btCoolingMethod) {
		this.btCoolingMethod = btCoolingMethod;
	}

	public long getBtPowerPlantMake() {
		return btPowerPlantMake;
	}

	public void setBtPowerPlantMake(long btPowerPlantMake) {
		this.btPowerPlantMake = btPowerPlantMake;
	}

	public long getBtResolutionMethod() {
		return btResolutionMethod;
	}

	public void setBtResolutionMethod(long btResolutionMethod) {
		this.btResolutionMethod = btResolutionMethod;
	}

	public long getBtPendingReasonCustomerBucket() {
		return btPendingReasonCustomerBucket;
	}

	public void setBtPendingReasonCustomerBucket(long btPendingReasonCustomerBucket) {
		this.btPendingReasonCustomerBucket = btPendingReasonCustomerBucket;
	}

	public long getBtSiteType() {
		return btSiteType;
	}

	public void setBtSiteType(long btSiteType) {
		this.btSiteType = btSiteType;
	}

	public long getBtSolutionImparted() {
		return btSolutionImparted;
	}

	public void setBtSolutionImparted(long btSolutionImparted) {
		this.btSolutionImparted = btSolutionImparted;
	}

	public String getContactPersonAtSite() {
		return contactPersonAtSite;
	}

	public void setContactPersonAtSite(String contactPersonAtSite) {
		this.contactPersonAtSite = contactPersonAtSite;
	}

	public long getContactPersonAtSiteNo() {
		return contactPersonAtSiteNo;
	}

	public void setContactPersonAtSiteNo(long contactPersonAtSiteNo) {
		this.contactPersonAtSiteNo = contactPersonAtSiteNo;
	}

	public float getEarthigStatus() {
		return earthigStatus;
	}

	public void setEarthigStatus(float earthigStatus) {
		this.earthigStatus = earthigStatus;
	}

	public float getAmbientTemp() {
		return ambientTemp;
	}

	public void setAmbientTemp(float ambientTemp) {
		this.ambientTemp = ambientTemp;
	}

	public float getTotalEquipmentDcLoad() {
		return totalEquipmentDcLoad;
	}

	public void setTotalEquipmentDcLoad(float totalEquipmentDcLoad) {
		this.totalEquipmentDcLoad = totalEquipmentDcLoad;
	}

	public float getActiveRectifiers() {
		return activeRectifiers;
	}

	public void setActiveRectifiers(float activeRectifiers) {
		this.activeRectifiers = activeRectifiers;
	}

	public float getSmpsToCbmsVolGap() {
		return smpsToCbmsVolGap;
	}

	public void setSmpsToCbmsVolGap(float smpsToCbmsVolGap) {
		this.smpsToCbmsVolGap = smpsToCbmsVolGap;
	}

	public float getCbmsToBatVolGap() {
		return cbmsToBatVolGap;
	}

	public void setCbmsToBatVolGap(float cbmsToBatVolGap) {
		this.cbmsToBatVolGap = cbmsToBatVolGap;
	}

	public float getDcLoadCurrent() {
		return dcLoadCurrent;
	}

	public void setDcLoadCurrent(float dcLoadCurrent) {
		this.dcLoadCurrent = dcLoadCurrent;
	}

	public float getMaxModuleTemp() {
		return maxModuleTemp;
	}

	public void setMaxModuleTemp(float maxModuleTemp) {
		this.maxModuleTemp = maxModuleTemp;
	}

	public String getFaultObservedByExicomEng() {
		return faultObservedByExicomEng;
	}

	public void setFaultObservedByExicomEng(String faultObservedByExicomEng) {
		this.faultObservedByExicomEng = faultObservedByExicomEng;
	}

	public String getCorrectiveActionInDetails() {
		return correctiveActionInDetails;
	}

	public void setCorrectiveActionInDetails(String correctiveActionInDetails) {
		this.correctiveActionInDetails = correctiveActionInDetails;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
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

	public String getComplainAttendByEngName() {
		return complainAttendByEngName;
	}

	public void setComplainAttendByEngName(String complainAttendByEngName) {
		this.complainAttendByEngName = complainAttendByEngName;
	}

	public long getEngNumber() {
		return engNumber;
	}

	public void setEngNumber(long engNumber) {
		this.engNumber = engNumber;
	}

	public String getCustomerRemark() {
		return customerRemark;
	}

	public void setCustomerRemark(String customerRemark) {
		this.customerRemark = customerRemark;
	}

	public Set<BtAssetMasterUpdateRequest> getAsseRequests() {
		return asseRequests;
	}

	public void setAsseRequests(Set<BtAssetMasterUpdateRequest> asseRequests) {
		this.asseRequests = asseRequests;
	}

	public Date getDateOfSiteVisit() {
		return dateOfSiteVisit;
	}

	public void setDateOfSiteVisit(Date dateOfSiteVisit) {
		this.dateOfSiteVisit = dateOfSiteVisit;
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
