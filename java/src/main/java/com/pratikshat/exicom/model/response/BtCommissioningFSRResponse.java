package com.pratikshat.exicom.model.response;

import java.util.Date;
import java.util.Set;

import com.pratikshat.exicom.entity.BtCommissioningFSR;

public class BtCommissioningFSRResponse {

	private long commFSRId;
	private long serviceAppointmentId;
	private String manualId;
	private String caseNo;
	private String customerName;	
	private String circle;
	private String cmp;
	private String siteId;
	private String siteName;
	private String caseLoggedBy;
	private long caseLoggedByContactNo;
	private String contactPersonAtSite;
	private long contactPersonAtSiteContactNumber;
	private Date dateOfCaseGenerated;
	private Date dateOfSiteVisit;

	private long siteTypeId;
	private String siteType;
	private long siteEnvironmentalConditionsId;
	private String siteEnvironmentalConditions;
	private float earthingStatus;
	private long coolingMethodId;
	private String coolingMethod;
	private float ambientTemp;
	private String commissionScope;
	private float totalEquipmentDCLoad;
	private String installationStatus;
	private float smpsToCbmsVolGap;
	private float cbmsToBatVolGap;
	private float dcLoadCurrent;
	private float maxModuleTemp;
	private float chargeVoltage;
	private float floatVoltage;
	private long powerPlantMakeId;
	private String powerPlantMake;
	private long activeRectifers;
	private Set<BtCommissioningAssetMasterUpdateResponse> asseResponse;
	private String exicomEngRemarks;
	private String customerRemarks;
	private String commStatus;
	private String pendingBucket;
	private String pendingReasonExicomBucket;
	private long pendingReasonCustomerBucketId;
	private String pendingReasonCustomerBucket;
	private String complainAttandByEngName;
	private long engineerNumber;
//	//observation
	private String observation1;
	private String observation2;
	private String observation3;
	private String observation4;

	public BtCommissioningFSRResponse() {

	}

	public BtCommissioningFSRResponse(BtCommissioningFSR btCommissioningFSR) {
		setData(btCommissioningFSR, null);
	}

	public BtCommissioningFSRResponse(long commFSRId,long serviceAppointmentId, String manualId, String caseNo, String customerName,
			String circle, String cmp, String siteId, String siteName, String caseLoggedBy, long caseLoggedByContactNo,
			String contactPersonAtSite, long contactPersonAtSiteContactNumber, Date dateOfCaseGenerated,
			Date dateOfSiteVisit, long siteTypeId, String siteType, long siteEnvironmentalConditionsId,
			String siteEnvironmentalConditions, float earthingStatus, long coolingMethodId, String coolingMethod,
			float ambientTemp, String commissionScope, float totalEquipmentDCLoad, String installationStatus,
			float smpsToCbmsVolGap, float cbmsToBatVolGap, float dcLoadCurrent, float maxModuleTemp,
			float chargeVoltage, float floatVoltage, long powerPlantMakeId, String powerPlantMake, long activeRectifers,
			Set<BtCommissioningAssetMasterUpdateResponse> btCommissioningAssetMasterUpdateResponse,
			String exicomEngRemarks, String customerRemarks, String commStatus, String pendingBucket,
			String pendingReasonExicomBucket,String observation1, String observation2, String observation3, String observation4, long pendingReasonCustomerBucketId, String pendingReasonCustomerBucket,
//			String pendingReasonExicomBucket, long pendingReasonCustomerBucketId, String pendingReasonCustomerBucket,
			String complainAttandByEngName, long engineerNumber) {
		this.serviceAppointmentId= serviceAppointmentId;
		this.commFSRId = commFSRId;
		this.manualId = manualId;
		this.caseNo = caseNo;
		this.customerName = customerName;
		this.circle = circle;
		this.cmp = cmp;
		this.siteId = siteId;
		this.siteName = siteName;
		this.caseLoggedBy = caseLoggedBy;
		this.caseLoggedByContactNo = caseLoggedByContactNo;
		this.contactPersonAtSite = contactPersonAtSite;
		this.contactPersonAtSiteContactNumber = contactPersonAtSiteContactNumber;
		this.dateOfCaseGenerated = dateOfCaseGenerated;
		this.dateOfSiteVisit = dateOfSiteVisit;
		this.siteTypeId = siteTypeId;
		this.siteType = siteType;
		this.siteEnvironmentalConditionsId = siteEnvironmentalConditionsId;
		this.siteEnvironmentalConditions = siteEnvironmentalConditions;
		this.earthingStatus = earthingStatus;
		this.coolingMethodId = coolingMethodId;
		this.coolingMethod = coolingMethod;
		this.ambientTemp = ambientTemp;
		this.commissionScope = commissionScope;
		this.totalEquipmentDCLoad = totalEquipmentDCLoad;
		this.installationStatus = installationStatus;
		this.smpsToCbmsVolGap = smpsToCbmsVolGap;
		this.cbmsToBatVolGap = cbmsToBatVolGap;
		this.dcLoadCurrent = dcLoadCurrent;
		this.maxModuleTemp = maxModuleTemp;
		this.chargeVoltage = chargeVoltage;
		this.floatVoltage = floatVoltage;
		this.powerPlantMakeId = powerPlantMakeId;
		this.powerPlantMake = powerPlantMake;
		this.activeRectifers = activeRectifers;
		this.asseResponse = btCommissioningAssetMasterUpdateResponse;
		this.exicomEngRemarks = exicomEngRemarks;
		this.customerRemarks = customerRemarks;
		this.commStatus = commStatus;
		this.pendingBucket = pendingBucket;
		this.pendingReasonExicomBucket = pendingReasonExicomBucket;
		this.pendingReasonCustomerBucketId = pendingReasonCustomerBucketId;
		this.pendingReasonCustomerBucket = pendingReasonCustomerBucket;
		this.complainAttandByEngName = complainAttandByEngName;
		this.engineerNumber = engineerNumber;
		this.observation1 = observation1;
		this.observation2 = observation2;
		this.observation3 = observation3;
		this.observation4 = observation4;
	}

	public BtCommissioningFSRResponse(BtCommissioningFSR commissioningFSR,
			Set<BtCommissioningAssetMasterUpdateResponse> assetMasterUpdateResponses) {
		setData(commissioningFSR, assetMasterUpdateResponses);
	}

	private void setData(BtCommissioningFSR commissioningFSR,
			Set<BtCommissioningAssetMasterUpdateResponse> assetMasterUpdateResponses) {
		this.commFSRId = commissioningFSR.getId();
		this.manualId = commissioningFSR.getManualId();
		if (commissioningFSR.getBtServiceAppointment() != null) {
			this.serviceAppointmentId=commissioningFSR.getBtServiceAppointment().getId();
			this.caseNo = commissioningFSR.getBtServiceAppointment().getCaseDetails().getManualId();
			String firstName = commissioningFSR.getBtServiceAppointment().getCaseDetails().getContactPerson()
					.getFirstName();
			String lastName = commissioningFSR.getBtServiceAppointment().getCaseDetails().getContactPerson()
					.getLastName();
			this.customerName = firstName + " " + lastName;
			this.circle = commissioningFSR.getBtServiceAppointment().getCaseDetails().getSite().getCircle()
					.getCircleName();
			this.cmp = commissioningFSR.getBtServiceAppointment().getCaseDetails().getSite().getCmp();
			this.siteId = commissioningFSR.getBtServiceAppointment().getCaseDetails().getSite().getManualId();
			this.siteName = commissioningFSR.getBtServiceAppointment().getCaseDetails().getSite().getSiteName();
			String first = commissioningFSR.getBtServiceAppointment().getCaseDetails().getOwnerDetails().getFirstName();
			String last = commissioningFSR.getBtServiceAppointment().getCaseDetails().getOwnerDetails().getLastName();
			this.caseLoggedBy = first + " " + last;
			this.caseLoggedByContactNo = Long.parseLong(
					commissioningFSR.getBtServiceAppointment().getCaseDetails().getContactPerson().getMobile());
			this.contactPersonAtSite = commissioningFSR.getContactPersonAtSite();
			this.contactPersonAtSiteContactNumber = commissioningFSR.getContactPersonAtSiteContactNo();
			this.dateOfCaseGenerated = commissioningFSR.getBtServiceAppointment().getCaseDetails().getCreatedDate();
			this.dateOfSiteVisit = commissioningFSR.getDateOfSiteVisit();
			this.siteTypeId = commissioningFSR.getBtSiteType().getId();
			this.siteType = commissioningFSR.getBtSiteType().getTypeName();
			this.siteEnvironmentalConditionsId = commissioningFSR.getBtSiteEnvironmentalConditions().getId();
			this.siteEnvironmentalConditions = commissioningFSR.getBtSiteEnvironmentalConditions().getTypeName();
			this.earthingStatus = commissioningFSR.getEarthingStatus();
			this.coolingMethodId = commissioningFSR.getBtCoolingMethod().getId();
			this.coolingMethod = commissioningFSR.getBtCoolingMethod().getTypeValue();
			this.ambientTemp = commissioningFSR.getAmbientTemperature();
			this.commissionScope = commissioningFSR.getCommissionScope();
			this.totalEquipmentDCLoad = commissioningFSR.getTotalEquipmentDCLoad();
			this.installationStatus = commissioningFSR.getInstallationStatus();
			this.smpsToCbmsVolGap = commissioningFSR.getSmpsToCbmsVolGap();
			this.cbmsToBatVolGap = commissioningFSR.getCbmsToBatVolGap();
			this.dcLoadCurrent = commissioningFSR.getDcLoadCurrent();
			this.maxModuleTemp = commissioningFSR.getMaxModuleTemp();
			this.chargeVoltage = commissioningFSR.getChargeVoltage();
			this.floatVoltage = commissioningFSR.getFloatVoltage();
			this.powerPlantMakeId = commissioningFSR.getBtPowerPlantMake().getId();
			this.powerPlantMake = commissioningFSR.getBtPowerPlantMake().getTypeValue();
			this.activeRectifers = commissioningFSR.getActiveRectifiers();
			this.exicomEngRemarks = commissioningFSR.getExicomEngRemarks();
			this.customerRemarks = commissioningFSR.getCustomerRemarks();
			//observations
			this.observation1 = commissioningFSR.getObservation1();
			this.observation2 = commissioningFSR.getObservation2();
			this.observation3 = commissioningFSR.getObservation3();
			this.observation4 = commissioningFSR.getObservation4();
			
			this.commStatus = commissioningFSR.getCommStatus();
			if (commissioningFSR.getCommStatus().toString().toLowerCase().equalsIgnoreCase("incompleted")) {
				this.pendingBucket = commissioningFSR.getPendingBucket();
				this.pendingReasonExicomBucket = commissioningFSR.getPendingReasonExicomBucket();
				if (commissioningFSR.getBtPendingReasonCustomerBucket() != null) {
					this.pendingReasonCustomerBucketId = commissioningFSR.getBtPendingReasonCustomerBucket().getId();
					this.pendingReasonCustomerBucket = commissioningFSR.getBtPendingReasonCustomerBucket()
							.getTypeValue();
				}
			}
			this.complainAttandByEngName = commissioningFSR.getComplainAttandbyEngName();
			this.engineerNumber = commissioningFSR.getEngNo();

		}

	}

	public long getCommFSRId() {
		return commFSRId;
	}

	public void setCommFSRId(long commFSRId) {
		this.commFSRId = commFSRId;
	}
	
	

	public long getServiceAppointmentId() {
		return serviceAppointmentId;
	}

	public void setServiceAppointmentId(long serviceAppointmentId) {
		this.serviceAppointmentId = serviceAppointmentId;
	}

	public String getManualId() {
		return manualId;
	}

	public void setManualId(String manualId) {
		this.manualId = manualId;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getCmp() {
		return cmp;
	}

	public void setCmp(String cmp) {
		this.cmp = cmp;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getCaseLoggedBy() {
		return caseLoggedBy;
	}

	public void setCaseLoggedBy(String caseLoggedBy) {
		this.caseLoggedBy = caseLoggedBy;
	}

	public long getCaseLoggedByContactNo() {
		return caseLoggedByContactNo;
	}

	public void setCaseLoggedByContactNo(long caseLoggedByContactNo) {
		this.caseLoggedByContactNo = caseLoggedByContactNo;
	}

	public Date getDateOfCaseGenerated() {
		return dateOfCaseGenerated;
	}

	public void setDateOfCaseGenerated(Date dateOfCaseGenerated) {
		this.dateOfCaseGenerated = dateOfCaseGenerated;
	}

	public Date getDateOfSiteVisit() {
		return dateOfSiteVisit;
	}

	public void setDateOfSiteVisit(Date dateOfSiteVisit) {
		this.dateOfSiteVisit = dateOfSiteVisit;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public String getSiteEnvironmentalConditions() {
		return siteEnvironmentalConditions;
	}

	public void setSiteEnvironmentalConditions(String siteEnvironmentalConditions) {
		this.siteEnvironmentalConditions = siteEnvironmentalConditions;
	}

	public float getEarthingStatus() {
		return earthingStatus;
	}

	public void setEarthingStatus(float earthingStatus) {
		this.earthingStatus = earthingStatus;
	}

	public String getCoolingMethod() {
		return coolingMethod;
	}

	public void setCoolingMethod(String coolingMethod) {
		this.coolingMethod = coolingMethod;
	}

	public float getAmbientTemp() {
		return ambientTemp;
	}

	public void setAmbientTemp(float ambientTemp) {
		this.ambientTemp = ambientTemp;
	}

	public String getCommissionScope() {
		return commissionScope;
	}

	public void setCommissionScope(String commissionScope) {
		this.commissionScope = commissionScope;
	}

	public float getTotalEquipmentDCLoad() {
		return totalEquipmentDCLoad;
	}

	public void setTotalEquipmentDCLoad(float totalEquipmentDCLoad) {
		this.totalEquipmentDCLoad = totalEquipmentDCLoad;
	}

	public String getInstallationStatus() {
		return installationStatus;
	}

	public void setInstallationStatus(String installationStatus) {
		this.installationStatus = installationStatus;
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

	public float getChargeVoltage() {
		return chargeVoltage;
	}

	public void setChargeVoltage(float chargeVoltage) {
		this.chargeVoltage = chargeVoltage;
	}

	public float getFloatVoltage() {
		return floatVoltage;
	}

	public void setFloatVoltage(float floatVoltage) {
		this.floatVoltage = floatVoltage;
	}

	public String getPowerPlantMake() {
		return powerPlantMake;
	}

	public void setPowerPlantMake(String powerPlantMake) {
		this.powerPlantMake = powerPlantMake;
	}

	public long getActiveRectifers() {
		return activeRectifers;
	}

	public void setActiveRectifers(long activeRectifers) {
		this.activeRectifers = activeRectifers;
	}

	public Set<BtCommissioningAssetMasterUpdateResponse> getAsseResponse() {
		return asseResponse;
	}

	public void setAsseResponse(Set<BtCommissioningAssetMasterUpdateResponse> asseResponse) {
		this.asseResponse = asseResponse;
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

	public String getPendingReasonCustomerBucket() {
		return pendingReasonCustomerBucket;
	}

	public void setPendingReasonCustomerBucket(String pendingReasonCustomerBucket) {
		this.pendingReasonCustomerBucket = pendingReasonCustomerBucket;
	}

	public String getComplainAttandByEngName() {
		return complainAttandByEngName;
	}

	public void setComplainAttandByEngName(String complainAttandByEngName) {
		this.complainAttandByEngName = complainAttandByEngName;
	}

	public long getEngineerNumber() {
		return engineerNumber;
	}

	public void setEngineerNumber(long engineerNumber) {
		this.engineerNumber = engineerNumber;
	}

	public String getContactPersonAtSite() {
		return contactPersonAtSite;
	}

	public void setContactPersonAtSite(String contactPersonAtSite) {
		this.contactPersonAtSite = contactPersonAtSite;
	}

	public long getContactPersonAtSiteContactNumber() {
		return contactPersonAtSiteContactNumber;
	}

	public void setContactPersonAtSiteContactNumber(long contactPersonAtSiteContactNumber) {
		this.contactPersonAtSiteContactNumber = contactPersonAtSiteContactNumber;
	}

	public long getSiteTypeId() {
		return siteTypeId;
	}

	public void setSiteTypeId(long siteTypeId) {
		this.siteTypeId = siteTypeId;
	}

	public long getSiteEnvironmentalConditionsId() {
		return siteEnvironmentalConditionsId;
	}

	public void setSiteEnvironmentalConditionsId(long siteEnvironmentalConditionsId) {
		this.siteEnvironmentalConditionsId = siteEnvironmentalConditionsId;
	}

	public long getCoolingMethodId() {
		return coolingMethodId;
	}

	public void setCoolingMethodId(long coolingMethodId) {
		this.coolingMethodId = coolingMethodId;
	}

	public long getPowerPlantMakeId() {
		return powerPlantMakeId;
	}

	public void setPowerPlantMakeId(long powerPlantMakeId) {
		this.powerPlantMakeId = powerPlantMakeId;
	}

	public long getPendingReasonCustomerBucketId() {
		return pendingReasonCustomerBucketId;
	}

	public void setPendingReasonCustomerBucketId(long pendingReasonCustomerBucketId) {
		this.pendingReasonCustomerBucketId = pendingReasonCustomerBucketId;
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
