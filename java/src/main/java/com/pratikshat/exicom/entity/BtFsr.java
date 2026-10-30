package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtFsr extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtFsr> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String manualId;
	
	@ManyToOne(fetch =FetchType.LAZY)
	private BtCase btCase;
	
	@ManyToOne(fetch =FetchType.LAZY)
	private BtServiceAppointment btServiceAppointment;
	
	
	private String contactPersonAtSite;
	
	private long contactPersonAtSiteContactNo;
	
	private Date dateOfSiteVisit;
	
	@ManyToOne(fetch =FetchType.LAZY)
	BtSiteType btSiteType;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	BtSiteEnvironmentalConditions btSiteEnvironmentalConditions;
	
	private float earthingStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	BtCoolingMethod  btCoolingMethod;
	
	private float ambientTemperature;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private BtPowerPlantMake btPowerPlantMake; 
	
	private float totalEquipmentDCLoad;
	
	private float activeRectifiers;
	
	private float smpsToCbmsVolGap;
	
	private float cbmsToBatVolGap;
	
	private float dcLoadCurrent;
	
	private float maxModuleTemp;
	
	@Column(length = 200)
	private String faultObservedByExicomEng;
	
	private String correctiveActionsInDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private BtResolutionMethod  btResolutionMethod ;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private BtSolutionImparted btSolutionImparted;
	
	private String caseStatus;
	
	private String pendingBucket;
	
	private String pendingReasonExicomBucket;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private BtPendingReasonCustomerBucket btPendingReasonCustomerBucket; 
	
	private String complainAttendByEngName;
	
	private long engineerNo;
	
	@Column(length = 200)
	private String customerRemarks;
	
	@ManyToOne(fetch =FetchType.LAZY)
	EmployeeDetails owner;
	
	//Observations
	private String observation1;
	private String observation2;
	private String observation3;
	private String observation4;
	
	public BtCase getBtCase() {
		return btCase;
	}
	

	public void setBtCase(BtCase btCase) {
		this.btCase = btCase;
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

	public BtPowerPlantMake getBtPowerPlantMake() {
		return btPowerPlantMake;
	}

	public void setBtPowerPlantMake(BtPowerPlantMake btPowerPlantMake) {
		this.btPowerPlantMake = btPowerPlantMake;
	}

	public float getTotalEquipmentDCLoad() {
		return totalEquipmentDCLoad;
	}

	public void setTotalEquipmentDCLoad(float totalEquipmentDCLoad) {
		this.totalEquipmentDCLoad = totalEquipmentDCLoad;
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

	public String getCorrectiveActionsInDetails() {
		return correctiveActionsInDetails;
	}

	public void setCorrectiveActionsInDetails(String correctiveActionsInDetails) {
		this.correctiveActionsInDetails = correctiveActionsInDetails;
	}

	public BtResolutionMethod getBtResolutionMethod() {
		return btResolutionMethod;
	}

	public void setBtResolutionMethod(BtResolutionMethod btResolutionMethod) {
		this.btResolutionMethod = btResolutionMethod;
	}

	public BtSolutionImparted getBtSolutionImparted() {
		return btSolutionImparted;
	}

	public void setBtSolutionImparted(BtSolutionImparted btSolutionImparted) {
		this.btSolutionImparted = btSolutionImparted;
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

	public BtPendingReasonCustomerBucket getBtPendingReasonCustomerBucket() {
		return btPendingReasonCustomerBucket;
	}

	public void setBtPendingReasonCustomerBucket(BtPendingReasonCustomerBucket btPendingReasonCustomerBucket) {
		this.btPendingReasonCustomerBucket = btPendingReasonCustomerBucket;
	}

	public String getComplainAttendByEngName() {
		return complainAttendByEngName;
	}

	public void setComplainAttendByEngName(String complainAttendByEngName) {
		this.complainAttendByEngName = complainAttendByEngName;
	}

	public long getEngineerNo() {
		return engineerNo;
	}

	public void setEngineerNo(long engineerNo) {
		this.engineerNo = engineerNo;
	}

	public String getCustomerRemarks() {
		return customerRemarks;
	}

	public void setCustomerRemarks(String customerRemarks) {
		this.customerRemarks = customerRemarks;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Transient transient BtFsr old;
	@PostLoad
	@Override
	public void old() {
		old=(BtFsr) SerializationUtils.clone(this);
		
	}

	@Override
	public BtFsr gOld() {
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
	public void setOwner(EmployeeDetails owner) {
		this.owner=owner;
		
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

	public BtServiceAppointment getBtServiceAppointment() {
		return btServiceAppointment;
	}

	public void setBtServiceAppointment(BtServiceAppointment btServiceAppointment) {
		this.btServiceAppointment = btServiceAppointment;
	}
	
	//observations getter and setter
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


	public String toString() {
		return " "+ id ;
	}
	
	

	
}
