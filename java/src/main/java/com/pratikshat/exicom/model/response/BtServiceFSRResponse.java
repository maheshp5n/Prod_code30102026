package com.pratikshat.exicom.model.response;

import java.sql.Date;
import java.util.Set;

import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtFsr;
import com.pratikshat.exicom.model.request.BtAssetMasterUpdateRequest;

public class BtServiceFSRResponse {

	long serviceFSRId;
	String serviceManualId;
	String caseManualId;
	long appointment;
	String appointmentManualId;
	long siteEnvironmentalConditionsId;
	String siteEnvironmentalConditions;
	long coolingMethodId;
	String coolingMethod;
	long powerPlantMakeId;
	String powerPlantMake;
	long resolutionMethodId;
	String resolutionMethod;
	long pendingReasonCustomerBucketId;
	String pendingReasonCustomerBucket;
	long siteTypeId;
	String siteType;
	long solutionImpartedId;
	String solutionImparted;

	String contactPersonAtSite;
	long contactPersonAtSiteNo;
	java.util.Date dateOfsiteVisit;

	float earthigStatus;
	float ambientTemp;

	float totalEquipmentDcLoad;
	float activeRectifiers;
	float smpsToCbmsVolGap;
	float cbmsToBatVolGap;
	float dcLoadCurrent;
	float maxModuleTemp;
	String faultObservedByExicomEng;
	String correctiveActionInDetails;
	String caseStatus;
	String pendingBucket;
	String pendingReasonExicomBucket;
	String complainAttendByEngName;
	long engNumber;
	String customerRemark;
	//observation
	String observation1;
	String observation2;
	String observation3;
	String observation4;

	Set<BtAssetMasterUpdateResponse> asseResponse;
	Set<BtFaultyAssetParametersResponse> faultyAsset;
	

	public BtServiceFSRResponse(long serviceFSRId, String serviceManualId, String caseManualId, long appointment,
			String appointmentManualId, long siteEnvironmentalConditionsId, String siteEnvironmentalConditions,
			long coolingMethodId, String coolingMethod, long powerPlantMakeId, String powerPlantMake,
			long resolutionMethodId, String resolutionMethod, long pendingReasonCustomerBucketId,
			String pendingReasonCustomerBucket, long siteTypeId, String siteType, long solutionImpartedId,
			String solutionImparted, String contactPersonAtSite, long contactPersonAtSiteNo,
			java.util.Date dateOfsiteVisit, float earthigStatus, float ambientTemp, float totalEquipmentDcLoad,
			float activeRectifiers, float smpsToCbmsVolGap, float cbmsToBatVolGap, float dcLoadCurrent,
			float maxModuleTemp, String faultObservedByExicomEng, String correctiveActionInDetails, String caseStatus,
			String pendingBucket, String pendingReasonExicomBucket, String complainAttendByEngName, long engNumber,
			String customerRemark,String observation1,String observation2,String observation3,String observation4, Set<BtAssetMasterUpdateResponse> asseResponse,
//			String customerRemark,Set<BtAssetMasterUpdateResponse> asseResponse,
			Set<BtFaultyAssetParametersResponse> faultyAsset) {
		this.serviceFSRId = serviceFSRId;
		this.serviceManualId = serviceManualId;
		this.caseManualId = caseManualId;
		this.appointment = appointment;
		this.appointmentManualId = appointmentManualId;
		this.siteEnvironmentalConditionsId = siteEnvironmentalConditionsId;
		this.siteEnvironmentalConditions = siteEnvironmentalConditions;
		this.coolingMethodId = coolingMethodId;
		this.coolingMethod = coolingMethod;
		this.powerPlantMakeId = powerPlantMakeId;
		this.powerPlantMake = powerPlantMake;
		this.resolutionMethodId = resolutionMethodId;
		this.resolutionMethod = resolutionMethod;
		this.pendingReasonCustomerBucketId = pendingReasonCustomerBucketId;
		this.pendingReasonCustomerBucket = pendingReasonCustomerBucket;
		this.siteTypeId = siteTypeId;
		this.siteType = siteType;
		this.solutionImpartedId = solutionImpartedId;
		this.solutionImparted = solutionImparted;
		this.contactPersonAtSite = contactPersonAtSite;
		this.contactPersonAtSiteNo = contactPersonAtSiteNo;
		this.dateOfsiteVisit = dateOfsiteVisit;
		this.earthigStatus = earthigStatus;
		this.ambientTemp = ambientTemp;
		this.totalEquipmentDcLoad = totalEquipmentDcLoad;
		this.activeRectifiers = activeRectifiers;
		this.smpsToCbmsVolGap = smpsToCbmsVolGap;
		this.cbmsToBatVolGap = cbmsToBatVolGap;
		this.dcLoadCurrent = dcLoadCurrent;
		this.maxModuleTemp = maxModuleTemp;
		this.faultObservedByExicomEng = faultObservedByExicomEng;
		this.correctiveActionInDetails = correctiveActionInDetails;
		this.caseStatus = caseStatus;
		this.pendingBucket = pendingBucket;
		this.pendingReasonExicomBucket = pendingReasonExicomBucket;
		this.complainAttendByEngName = complainAttendByEngName;
		this.engNumber = engNumber;
		this.customerRemark = customerRemark;
		//observations
		this.observation1 = observation1;
		this.observation2 = observation2;
		this.observation3 = observation3;
		this.observation4 = observation4;
		
		this.asseResponse = asseResponse;
		this.faultyAsset = faultyAsset;
	}

	public BtServiceFSRResponse() {	
	}
	
	public BtServiceFSRResponse(BtFsr btFsr) {
		setData(btFsr,null,null);
	}

	public BtServiceFSRResponse(BtFsr fsrDetails, Set<BtAssetMasterUpdateResponse> asseResponse,Set<BtFaultyAssetParametersResponse> faultyAsset) {
		setData(fsrDetails, asseResponse,faultyAsset);
	}

	private void setData(BtFsr fsrDetails, Set<BtAssetMasterUpdateResponse> asseResponse,Set<BtFaultyAssetParametersResponse> faultyAsset) {
		this.serviceFSRId= fsrDetails.getId();
		this.serviceManualId=fsrDetails.getManualId();
		if (fsrDetails.getBtServiceAppointment() != null) {
			
			this.appointmentManualId=fsrDetails.getBtServiceAppointment().getmanualId();
			this.appointment = fsrDetails.getBtServiceAppointment().getServiceAppointmentId();
			if(fsrDetails.getBtServiceAppointment().getCaseDetails()!=null) {
				this.caseManualId=fsrDetails.getBtServiceAppointment().getCaseDetails().getManualId();
			}
		}
		if (fsrDetails.getBtSiteEnvironmentalConditions() != null) {
			this.siteEnvironmentalConditionsId=fsrDetails.getBtSiteEnvironmentalConditions().getId();
			this.siteEnvironmentalConditions = fsrDetails.getBtSiteEnvironmentalConditions().getTypeName();
		}

		if (fsrDetails.getBtCoolingMethod() != null) {
			this.coolingMethodId = fsrDetails.getBtCoolingMethod().getId();
			this.coolingMethod = fsrDetails.getBtCoolingMethod().getTypeValue();
		}

		if (fsrDetails.getBtPowerPlantMake() != null) {
			this.powerPlantMakeId=fsrDetails.getBtPowerPlantMake().getId();
			this.powerPlantMake = fsrDetails.getBtPowerPlantMake().getTypeValue();
		}

		if (fsrDetails.getBtResolutionMethod() != null) {
			this.resolutionMethodId=fsrDetails.getBtResolutionMethod().getId();
			this.resolutionMethod = fsrDetails.getBtResolutionMethod().getName();
		}

		if (fsrDetails.getBtSiteType() != null) {
			this.siteTypeId =fsrDetails.getBtSiteType().getId();
			this.siteType = fsrDetails.getBtSiteType().getTypeName();
		}
		if (fsrDetails.getBtSolutionImparted() != null) {
			this.solutionImpartedId=fsrDetails.getBtSolutionImparted().getId();
			this.solutionImparted = fsrDetails.getBtSolutionImparted().getName();
		}
		if(asseResponse!=null) {
			this.asseResponse=asseResponse;
		}
		if(faultyAsset!=null) {
			this.faultyAsset=faultyAsset;
		}

		this.contactPersonAtSite = fsrDetails.getContactPersonAtSite();

		this.contactPersonAtSiteNo = fsrDetails.getContactPersonAtSiteContactNo();

		this.dateOfsiteVisit = fsrDetails.getDateOfSiteVisit();

		this.earthigStatus = fsrDetails.getEarthingStatus();

		this.ambientTemp = fsrDetails.getAmbientTemperature();

		this.totalEquipmentDcLoad = fsrDetails.getTotalEquipmentDCLoad();

		this.activeRectifiers = fsrDetails.getActiveRectifiers();

		this.smpsToCbmsVolGap = fsrDetails.getSmpsToCbmsVolGap();

		this.cbmsToBatVolGap = fsrDetails.getCbmsToBatVolGap();

		this.dcLoadCurrent = fsrDetails.getDcLoadCurrent();

		this.maxModuleTemp = fsrDetails.getMaxModuleTemp();

		this.faultObservedByExicomEng = fsrDetails.getFaultObservedByExicomEng();

		this.correctiveActionInDetails = fsrDetails.getCorrectiveActionsInDetails();

		this.caseStatus = fsrDetails.getCaseStatus();
		if(fsrDetails.getCaseStatus().toString().toLowerCase().equalsIgnoreCase("incompleted")) {
			this.pendingBucket = fsrDetails.getPendingBucket();
			this.pendingReasonExicomBucket = fsrDetails.getPendingReasonExicomBucket();
			if (fsrDetails.getBtPendingReasonCustomerBucket() != null) {
				this.pendingReasonCustomerBucketId=fsrDetails.getBtPendingReasonCustomerBucket().getId();
				this.pendingReasonCustomerBucket = fsrDetails.getBtPendingReasonCustomerBucket().getTypeValue();
			}
		}

		this.complainAttendByEngName = fsrDetails.getComplainAttendByEngName();

		this.engNumber = fsrDetails.getEngineerNo();

		this.customerRemark = fsrDetails.getCustomerRemarks();
//		//observations
		this.observation1 = fsrDetails.getObservation1();
		this.observation2 = fsrDetails.getObservation2();
		this.observation3 = fsrDetails.getObservation3();
		this.observation4 = fsrDetails.getObservation4();

		if (asseResponse != null) {
			this.asseResponse = asseResponse;
		}

	}

	public long getServiceFSRId() {
		return serviceFSRId;
	}

	public void setServiceFSRId(long serviceFSRId) {
		this.serviceFSRId = serviceFSRId;
	}

	public String getServiceManualId() {
		return serviceManualId;
	}

	public void setServiceManualId(String serviceManualId) {
		this.serviceManualId = serviceManualId;
	}

	public String getCaseManualId() {
		return caseManualId;
	}

	public void setCaseManualId(String caseManualId) {
		this.caseManualId = caseManualId;
	}

	public long getAppointment() {
		return appointment;
	}

	public void setAppointment(long appointment) {
		this.appointment = appointment;
	}

	public String getAppointmentManualId() {
		return appointmentManualId;
	}

	public void setAppointmentManualId(String appointmentManualId) {
		this.appointmentManualId = appointmentManualId;
	}

	public long getSiteEnvironmentalConditionsId() {
		return siteEnvironmentalConditionsId;
	}

	public void setSiteEnvironmentalConditionsId(long siteEnvironmentalConditionsId) {
		this.siteEnvironmentalConditionsId = siteEnvironmentalConditionsId;
	}

	public String getSiteEnvironmentalConditions() {
		return siteEnvironmentalConditions;
	}

	public void setSiteEnvironmentalConditions(String siteEnvironmentalConditions) {
		this.siteEnvironmentalConditions = siteEnvironmentalConditions;
	}

	public long getCoolingMethodId() {
		return coolingMethodId;
	}

	public void setCoolingMethodId(long coolingMethodId) {
		this.coolingMethodId = coolingMethodId;
	}

	public String getCoolingMethod() {
		return coolingMethod;
	}

	public void setCoolingMethod(String coolingMethod) {
		this.coolingMethod = coolingMethod;
	}

	public long getPowerPlantMakeId() {
		return powerPlantMakeId;
	}

	public void setPowerPlantMakeId(long powerPlantMakeId) {
		this.powerPlantMakeId = powerPlantMakeId;
	}

	public String getPowerPlantMake() {
		return powerPlantMake;
	}

	public void setPowerPlantMake(String powerPlantMake) {
		this.powerPlantMake = powerPlantMake;
	}

	public long getResolutionMethodId() {
		return resolutionMethodId;
	}

	public void setResolutionMethodId(long resolutionMethodId) {
		this.resolutionMethodId = resolutionMethodId;
	}

	public String getResolutionMethod() {
		return resolutionMethod;
	}

	public void setResolutionMethod(String resolutionMethod) {
		this.resolutionMethod = resolutionMethod;
	}

	public long getPendingReasonCustomerBucketId() {
		return pendingReasonCustomerBucketId;
	}

	public void setPendingReasonCustomerBucketId(long pendingReasonCustomerBucketId) {
		this.pendingReasonCustomerBucketId = pendingReasonCustomerBucketId;
	}

	public String getPendingReasonCustomerBucket() {
		return pendingReasonCustomerBucket;
	}

	public void setPendingReasonCustomerBucket(String pendingReasonCustomerBucket) {
		this.pendingReasonCustomerBucket = pendingReasonCustomerBucket;
	}

	public long getSiteTypeId() {
		return siteTypeId;
	}

	public void setSiteTypeId(long siteTypeId) {
		this.siteTypeId = siteTypeId;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		siteType = siteType;
	}

	public long getSolutionImpartedId() {
		return solutionImpartedId;
	}

	public void setSolutionImpartedId(long solutionImpartedId) {
		this.solutionImpartedId = solutionImpartedId;
	}

	public String getSolutionImparted() {
		return solutionImparted;
	}

	public void setSolutionImparted(String solutionImparted) {
		this.solutionImparted = solutionImparted;
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

	public java.util.Date getDateOfsiteVisit() {
		return dateOfsiteVisit;
	}

	public void setDateOfsiteVisit(java.util.Date dateOfsiteVisit) {
		this.dateOfsiteVisit = dateOfsiteVisit;
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

	public Set<BtAssetMasterUpdateResponse> getAsseResponse() {
		return asseResponse;
	}

	public void setAsseResponse(Set<BtAssetMasterUpdateResponse> asseResponse) {
		this.asseResponse = asseResponse;
	}

	public Set<BtFaultyAssetParametersResponse> getFaultyAsset() {
		return faultyAsset;
	}

	public void setFaultyAsset(Set<BtFaultyAssetParametersResponse> faultyAsset) {
		this.faultyAsset = faultyAsset;
	}
	//observations

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