package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({ObjectAuditListener.class,AuditingEntityListener.class})
public class BtServiceFSR extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtServiceFSR>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String manualId;
	
	private String customerName;
	private String contactNumber;
	private String CMP;
	private long batterySNo1;
	private long batterySNo2;
	private long batterySNo3;
	private long batterySNo4;
	private String earthingStatus;
	private String ambientTemperature;
	private String totalEquipmentDcLoad;
	private String smpsToCbmsVolGap;
	private String siteEnvironmentCondition;
	private String coolingMethod;
	private String powerPlantMake;
	private String activeRectifier;
	private String cbmsToBatVolGap;
	private String cbmsSNO;
	private String softwareVersion;
	private String modelNo;
	
	private String vol;
	private String soc;
	private String soh;
	private int minCellVoltage;
	private int maxCellVoltage;
	private int volGap;
	private int batteryTemp;
	private String lastCycleDel;
	private String logStatus;
	private boolean BatteryRequridAtBrc;
	private String pendingBucket;
	private String pendingReason;
	private Date problemSince;
	private String activeAlarm;
	private String equipmentSrNo;
	private String warrentyStatus;
	private Date installationDate;
	private String currentCondition;
	
	@Column(length = 2000)
	private String faultReportedByCustomer;
	private String faultObservedByExicomEngineer;
	private Date dateOfSitevisit;
	private String correctiveActionsInDetail;
	@ManyToOne
	private BtResolutionMethod resolutionMethod;
	@ManyToOne
	private BtSolutionImparted solutionImparted;
	private String customerRemark;
	
	
	@ManyToOne
	BtProducts btproduct;
	@ManyToOne
	BtCase btCase;
	@ManyToOne
	BtServiceAppointment btServiceAppointment;
	@ManyToOne
	BtSiteDetails btSite;
	@ManyToOne
	BtSiteType siteType;
	@ManyToOne 
	BtAssets btAssets;
	@ManyToOne
	ServiceRecource engineer;
	@ManyToOne
	EmployeeDetails owner;
	

	public String getCMP() {
		return CMP;
	}

	public void setCMP(String cMP) {
		CMP = cMP;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public long getBatterySNo1() {
		return batterySNo1;
	}

	public void setBatterySNo1(long batterySNo1) {
		this.batterySNo1 = batterySNo1;
	}

	public long getBatterySNo2() {
		return batterySNo2;
	}

	public void setBatterySNo2(long batterySNo2) {
		this.batterySNo2 = batterySNo2;
	}

	public long getBatterySNo3() {
		return batterySNo3;
	}

	public void setBatterySNo3(long batterySNo3) {
		this.batterySNo3 = batterySNo3;
	}

	public long getBatterySNo4() {
		return batterySNo4;
	}

	public void setBatterySNo4(long batterySNo4) {
		this.batterySNo4 = batterySNo4;
	}

	public String getEarthingStatus() {
		return earthingStatus;
	}

	public void setEarthingStatus(String earthingStatus) {
		this.earthingStatus = earthingStatus;
	}

	public String getAmbientTemperature() {
		return ambientTemperature;
	}

	public void setAmbientTemperature(String ambientTemperature) {
		this.ambientTemperature = ambientTemperature;
	}

	public String getTotalEquipmentDcLoad() {
		return totalEquipmentDcLoad;
	}

	public void setTotalEquipmentDcLoad(String totalEquipmentDcLoad) {
		this.totalEquipmentDcLoad = totalEquipmentDcLoad;
	}

	public String getSmpsToCbmsVolGap() {
		return smpsToCbmsVolGap;
	}

	public void setSmpsToCbmsVolGap(String smpsToCbmsVolGap) {
		this.smpsToCbmsVolGap = smpsToCbmsVolGap;
	}

	public String getSiteEnvironmentCondition() {
		return siteEnvironmentCondition;
	}

	public void setSiteEnvironmentCondition(String siteEnvironmentCondition) {
		this.siteEnvironmentCondition = siteEnvironmentCondition;
	}

	public String getCoolingMethod() {
		return coolingMethod;
	}

	public void setCoolingMethod(String coolingMethod) {
		this.coolingMethod = coolingMethod;
	}

	public String getPowerPlantMake() {
		return powerPlantMake;
	}

	public void setPowerPlantMake(String powerPlantMake) {
		this.powerPlantMake = powerPlantMake;
	}

	public String getActiveRectifier() {
		return activeRectifier;
	}

	public void setActiveRectifier(String activeRectifier) {
		this.activeRectifier = activeRectifier;
	}

	public String getCbmsToBatVolGap() {
		return cbmsToBatVolGap;
	}

	public void setCbmsToBatVolGap(String cbmsToBatVolGap) {
		this.cbmsToBatVolGap = cbmsToBatVolGap;
	}

	public String getCbmsSNO() {
		return cbmsSNO;
	}

	public void setCbmsSNO(String cbmsSNO) {
		this.cbmsSNO = cbmsSNO;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getVol() {
		return vol;
	}

	public void setVol(String vol) {
		this.vol = vol;
	}

	public String getSoc() {
		return soc;
	}

	public void setSoc(String soc) {
		this.soc = soc;
	}

	public String getSoh() {
		return soh;
	}

	public void setSoh(String soh) {
		this.soh = soh;
	}

	public int getMinCellVoltage() {
		return minCellVoltage;
	}

	public void setMinCellVoltage(int minCellVoltage) {
		this.minCellVoltage = minCellVoltage;
	}

	public int getMaxCellVoltage() {
		return maxCellVoltage;
	}

	public void setMaxCellVoltage(int maxCellVoltage) {
		this.maxCellVoltage = maxCellVoltage;
	}

	public int getVolGap() {
		return volGap;
	}

	public void setVolGap(int voltagegap) {
		this.volGap = voltagegap;
	}

	public int getBatteryTemp() {
		return batteryTemp;
	}

	public void setBatteryTemp(int batteryTemp) {
		this.batteryTemp = batteryTemp;
	}

	public String getLastCycleDel() {
		return lastCycleDel;
	}

	public void setLastCycleDel(String lastCycleDel) {
		this.lastCycleDel = lastCycleDel;
	}

	public String getLogStatus() {
		return logStatus;
	}

	public void setLogStatus(String logStatus) {
		this.logStatus = logStatus;
	}

	public boolean isBatteryRequridAtBrc() {
		return BatteryRequridAtBrc;
	}

	public void setBatteryRequridAtBrc(boolean batteryRequridAtBrc) {
		BatteryRequridAtBrc = batteryRequridAtBrc;
	}

	public String getPendingBucket() {
		return pendingBucket;
	}

	public void setPendingBucket(String pendingBucket) {
		this.pendingBucket = pendingBucket;
	}

	public String getPendingReason() {
		return pendingReason;
	}

	public void setPendingReason(String pendingReason) {
		this.pendingReason = pendingReason;
	}

	public Date getProblemSince() {
		return problemSince;
	}

	public void setProblemSince(Date problemSince) {
		this.problemSince = problemSince;
	}

	public String getActiveAlarm() {
		return activeAlarm;
	}

	public void setActiveAlarm(String activeAlarm) {
		this.activeAlarm = activeAlarm;
	}

	public String getEquipmentSrNo() {
		return equipmentSrNo;
	}

	public void setEquipmentSrNo(String equipmentSrNo) {
		this.equipmentSrNo = equipmentSrNo;
	}

	public String getWarrentyStatus() {
		return warrentyStatus;
	}

	public void setWarrentyStatus(String warrentyStatus) {
		this.warrentyStatus = warrentyStatus;
	}

	public Date getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}

	public String getCurrentCondition() {
		return currentCondition;
	}

	public void setCurrentCondition(String currentCondition) {
		this.currentCondition = currentCondition;
	}

	public String getFaultReportedByCustomer() {
		return faultReportedByCustomer;
	}

	public void setFaultReportedByCustomer(String faultReportedByCustomer) {
		this.faultReportedByCustomer = faultReportedByCustomer;
	}

	public String getFaultObservedByExicomEngineer() {
		return faultObservedByExicomEngineer;
	}

	public void setFaultObservedByExicomEngineer(String faultObservedByExicomEngineer) {
		this.faultObservedByExicomEngineer = faultObservedByExicomEngineer;
	}

	public Date getDateOfSitevisit() {
		return dateOfSitevisit;
	}

	public void setDateOfSitevisit(Date dateOfSitevisit) {
		this.dateOfSitevisit = dateOfSitevisit;
	}

	public String getCorrectiveActionsInDetail() {
		return correctiveActionsInDetail;
	}

	public void setCorrectiveActionsInDetail(String correctiveActionsInDetail) {
		this.correctiveActionsInDetail = correctiveActionsInDetail;
	}

	public BtResolutionMethod getResolutionMethod() {
		return resolutionMethod;
	}

	public void setResolutionMethod(BtResolutionMethod resolutionMethod) {
		this.resolutionMethod = resolutionMethod;
	}

	public BtSolutionImparted getSolutionImparted() {
		return solutionImparted;
	}

	public void setSolutionImparted(BtSolutionImparted solutionImparted) {
		this.solutionImparted = solutionImparted;
	}

	public String getCustomerRemark() {
		return customerRemark;
	}

	public void setCustomerRemark(String customerRemark) {
		this.customerRemark = customerRemark;
	}

	public BtProducts getBtproduct() {
		return btproduct;
	}

	public void setBtproduct(BtProducts btproduct) {
		this.btproduct = btproduct;
	}

	public BtCase getBtCase() {
		return btCase;
	}

	public void setBtCase(BtCase btCase) {
		this.btCase = btCase;
	}

	public BtServiceAppointment getBtServiceAppointment() {
		return btServiceAppointment;
	}

	public void setBtServiceAppointment(BtServiceAppointment btServiceAppointment) {
		this.btServiceAppointment = btServiceAppointment;
	}

	public BtSiteDetails getBtSite() {
		return btSite;
	}

	public void setBtSite(BtSiteDetails btSite) {
		this.btSite = btSite;
	}

	public BtSiteType getSiteType() {
		return siteType;
	}

	public void setSiteType(BtSiteType siteType) {
		this.siteType = siteType;
	}

	public BtAssets getBtAssets() {
		return btAssets;
	}

	public void setBtAssets(BtAssets btAssets) {
		this.btAssets = btAssets;
	}

	public ServiceRecource getEngineer() {
		return engineer;
	}

	public void setEngineer(ServiceRecource engineer) {
		this.engineer = engineer;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Transient  transient  BtServiceFSR old;
	@Override
	@PostLoad
	public void old() {
		old=(BtServiceFSR) SerializationUtils.clone(this);
	}

	@Override
	public BtServiceFSR gOld() {
		return old;
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
		return this.manualId;
	}

	@Override
	public void setManualId(String manualId) {
		this.manualId=manualId;
		
	}

	public String toString() {
		return " "+ id ;
	}
	
	

}