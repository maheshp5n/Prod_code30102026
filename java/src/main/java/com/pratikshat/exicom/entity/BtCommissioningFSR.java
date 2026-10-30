package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
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
@EntityListeners({ AuditingEntityListener.class, ObjectAuditListener.class })
public class BtCommissioningFSR extends Auditable<EmployeeDetails> implements Serializable, OldPresist<BtCommissioningFSR> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String manualId;

	@ManyToOne(fetch = FetchType.LAZY)
	private BtServiceAppointment btServiceAppointment;

	private String contactPersonAtSite;

	private long contactPersonAtSiteContactNo;

	private Date dateOfSiteVisit;

	@ManyToOne(fetch = FetchType.LAZY)
	BtSiteType btSiteType;

	@ManyToOne(fetch = FetchType.LAZY)
	BtSiteEnvironmentalConditions btSiteEnvironmentalConditions;

	private float earthingStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	BtCoolingMethod btCoolingMethod;

	private float ambientTemperature;

	private String commissionScope;

	private float totalEquipmentDCLoad;

	private String installationStatus;

	private float smpsToCbmsVolGap;

	private float cbmsToBatVolGap;

	private float dcLoadCurrent;

	private float maxModuleTemp;

	private float chargeVoltage;
	
	private float floatVoltage;

	@ManyToOne(fetch = FetchType.LAZY)
	private BtPowerPlantMake btPowerPlantMake;

	private long activeRectifiers;

	@Column(length = 3000)
	private String exicomEngRemarks;

	@Column(length = 3000)
	private String customerRemarks;

	private String commStatus;

	private String pendingBucket;

	private String pendingReasonExicomBucket;

	@ManyToOne(fetch = FetchType.LAZY)
	private BtPendingReasonCustomerBucket btPendingReasonCustomerBucket;
	
	private String complainAttandbyEngName;
	
	private long engNo;
	
	//observations
	private String observation1;
	private String observation2;
	private String observation3;
	private String observation4;
	
	

	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails owner;

	public BtServiceAppointment getBtServiceAppointment() {
		return btServiceAppointment;
	}

	public void setBtServiceAppointment(BtServiceAppointment btServiceAppointment) {
		this.btServiceAppointment = btServiceAppointment;
	}

	public String getContactPersonAtSite() {
		return contactPersonAtSite;
	}

	public void setContactPersonAtSite(String contactPersonAtSite) {
		this.contactPersonAtSite = contactPersonAtSite;
	}

	public long getContactPersonAtSiteContactNo() {
		return contactPersonAtSiteContactNo;
	}

	public void setContactPersonAtSiteContactNo(long contactPersonAtSiteContactNo) {
		this.contactPersonAtSiteContactNo = contactPersonAtSiteContactNo;
	}

	public Date getDateOfSiteVisit() {
		return dateOfSiteVisit;
	}

	public void setDateOfSiteVisit(Date dateOfSiteVisit) {
		this.dateOfSiteVisit = dateOfSiteVisit;
	}

	public BtSiteType getBtSiteType() {
		return btSiteType;
	}

	public void setBtSiteType(BtSiteType btSiteType) {
		this.btSiteType = btSiteType;
	}

	public BtSiteEnvironmentalConditions getBtSiteEnvironmentalConditions() {
		return btSiteEnvironmentalConditions;
	}

	public void setBtSiteEnvironmentalConditions(BtSiteEnvironmentalConditions btSiteEnvironmentalConditions) {
		this.btSiteEnvironmentalConditions = btSiteEnvironmentalConditions;
	}

	public float getEarthingStatus() {
		return earthingStatus;
	}

	public void setEarthingStatus(float earthingStatus) {
		this.earthingStatus = earthingStatus;
	}

	public BtCoolingMethod getBtCoolingMethod() {
		return btCoolingMethod;
	}

	public void setBtCoolingMethod(BtCoolingMethod btCoolingMethod) {
		this.btCoolingMethod = btCoolingMethod;
	}

	public float getAmbientTemperature() {
		return ambientTemperature;
	}

	public void setAmbientTemperature(float ambientTemperature) {
		this.ambientTemperature = ambientTemperature;
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

	public BtPowerPlantMake getBtPowerPlantMake() {
		return btPowerPlantMake;
	}

	public void setBtPowerPlantMake(BtPowerPlantMake btPowerPlantMake) {
		this.btPowerPlantMake = btPowerPlantMake;
	}

	public long getActiveRectifiers() {
		return activeRectifiers;
	}

	public void setActiveRectifiers(long activeRectifiers) {
		this.activeRectifiers = activeRectifiers;
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

	public BtPendingReasonCustomerBucket getBtPendingReasonCustomerBucket() {
		return btPendingReasonCustomerBucket;
	}

	public void setBtPendingReasonCustomerBucket(BtPendingReasonCustomerBucket btPendingReasonCustomerBucket) {
		this.btPendingReasonCustomerBucket = btPendingReasonCustomerBucket;
	}
	
	public String getComplainAttandbyEngName() {
		return complainAttandbyEngName;
	}

	public void setComplainAttandbyEngName(String complainAttandbyEngName) {
		this.complainAttandbyEngName = complainAttandbyEngName;
	}

	public long getEngNo() {
		return engNo;
	}

	public void setEngNo(long engNo) {
		this.engNo = engNo;
	}

	public void setId(long id) {
		this.id = id;
	}
	
//	//ob getter setter
	public String getObservation1() {
		System.out.println("ob1 get");
		System.out.println(observation1);
		return observation1;
	}

	public void setObservation1(String observation1) {
		System.out.println("ob1 set");
		this.observation1 = observation1;
		
		System.out.println(observation1);
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


	@Transient
	transient BtCommissioningFSR old;

	@PostLoad
	@Override
	public void old() {
		old = (BtCommissioningFSR) SerializationUtils.clone(this);

	}

	@Override
	public BtCommissioningFSR gOld() {
		return this.old;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public EmployeeDetails getOwner() {
		return this.owner;
	}

	@Override
	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}

	@Override
	public String getManualId() {
		// TODO Auto-generated method stub
		return this.manualId;
	}

	@Override
	public void setManualId(String manualId) {
		this.manualId = manualId;

	}
	
	public String toString() {
		return " "+id;
	}
	
	

}
