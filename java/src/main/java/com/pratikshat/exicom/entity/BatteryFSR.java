package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BatteryFSR implements java.io.Serializable,OldPresist<BatteryFSR>{
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	
	String manualId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	ServiceAppointment serviceAppointment;

	Date dateOfVisit;
	
	String vendorEngName;
	String vendorEngContact;
	
	String faultReportedByCustomer;
	String faultObservedByEngineer;
	
	@ManyToOne
	BatterySiteType siteType;
	
	String siteEnvCond;
	String EBAvailability;
	String DGRun;
	String DGRating;
	String earthingStatus;
	String DCLoad;
	String powerPlantMakeAndCapacity;
	String ambientTemp;
	String softVersionCBMS;
	String batteryVoltageBeforRepair;
	String batteryVoltageAfterRepair;
	
	@Column(length = 2000)
	String correctiveAction;
	String sparePartReplaced;
	
	@Column(length = 2000)
	String remark;
	@ManyToOne
	PendingResponsibility pendingResponsibility;
	
	String problemCustomerOrExicom;
	String customerOrSiteProblem;
	
	@ManyToOne
	BatteryComplaintStatus complaintStatus;
	
	@Column(length = 2000)
	String otherPendingIssue;

	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails mdifiedBy;
	Date modifiedDate;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getManualId() {
		return manualId;
	}
	public void setManualId(String manualId) {
		this.manualId = manualId;
	}
	public ServiceAppointment getServiceAppointment() {
		return serviceAppointment;
	}
	public void setServiceAppointment(ServiceAppointment serviceAppointment) {
		this.serviceAppointment = serviceAppointment;
	}
	public Date getDateOfVisit() {
		return dateOfVisit;
	}
	public void setDateOfVisit(Date dateOfVisit) {
		this.dateOfVisit = dateOfVisit;
	}
	public String getVendorEngName() {
		return vendorEngName;
	}
	public void setVendorEngName(String vendorEngName) {
		this.vendorEngName = vendorEngName;
	}
	public String getVendorEngContact() {
		return vendorEngContact;
	}
	public void setVendorEngContact(String vendorEngContact) {
		this.vendorEngContact = vendorEngContact;
	}
	public String getFaultReportedByCustomer() {
		return faultReportedByCustomer;
	}
	public void setFaultReportedByCustomer(String faultReportedByCustomer) {
		this.faultReportedByCustomer = faultReportedByCustomer;
	}
	public String getFaultObservedByEngineer() {
		return faultObservedByEngineer;
	}
	public void setFaultObservedByEngineer(String faultObservedByEngineer) {
		this.faultObservedByEngineer = faultObservedByEngineer;
	}
	public BatterySiteType getSiteType() {
		return siteType;
	}
	public void setSiteType(BatterySiteType siteType) {
		this.siteType = siteType;
	}
	public String getSiteEnvCond() {
		return siteEnvCond;
	}
	public void setSiteEnvCond(String siteEnvCond) {
		this.siteEnvCond = siteEnvCond;
	}
	public String getEBAvailability() {
		return EBAvailability;
	}
	public void setEBAvailability(String eBAvailability) {
		EBAvailability = eBAvailability;
	}
	public String getDGRun() {
		return DGRun;
	}
	public void setDGRun(String dGRun) {
		DGRun = dGRun;
	}
	public String getDGRating() {
		return DGRating;
	}
	public void setDGRating(String dGRating) {
		DGRating = dGRating;
	}
	public String getEarthingStatus() {
		return earthingStatus;
	}
	public void setEarthingStatus(String earthingStatus) {
		this.earthingStatus = earthingStatus;
	}
	public String getDCLoad() {
		return DCLoad;
	}
	public void setDCLoad(String dCLoad) {
		DCLoad = dCLoad;
	}
	public String getPowerPlantMakeAndCapacity() {
		return powerPlantMakeAndCapacity;
	}
	public void setPowerPlantMakeAndCapacity(String powerPlantMakeAndCapacity) {
		this.powerPlantMakeAndCapacity = powerPlantMakeAndCapacity;
	}
	public String getAmbientTemp() {
		return ambientTemp;
	}
	public void setAmbientTemp(String ambientTemp) {
		this.ambientTemp = ambientTemp;
	}
	public String getSoftVersionCBMS() {
		return softVersionCBMS;
	}
	public void setSoftVersionCBMS(String softVersionCBMS) {
		this.softVersionCBMS = softVersionCBMS;
	}
	public String getBatteryVoltageBeforRepair() {
		return batteryVoltageBeforRepair;
	}
	public void setBatteryVoltageBeforRepair(String batteryVoltageBeforRepair) {
		this.batteryVoltageBeforRepair = batteryVoltageBeforRepair;
	}
	public String getBatteryVoltageAfterRepair() {
		return batteryVoltageAfterRepair;
	}
	public void setBatteryVoltageAfterRepair(String batteryVoltageAfterRepair) {
		this.batteryVoltageAfterRepair = batteryVoltageAfterRepair;
	}
	public String getCorrectiveAction() {
		return correctiveAction;
	}
	public void setCorrectiveAction(String correctiveAction) {
		this.correctiveAction = correctiveAction;
	}
	public String getSparePartReplaced() {
		return sparePartReplaced;
	}
	public void setSparePartReplaced(String sparePartReplaced) {
		this.sparePartReplaced = sparePartReplaced;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public PendingResponsibility getPendingResponsibility() {
		return pendingResponsibility;
	}
	public void setPendingResponsibility(PendingResponsibility pendingResponsibility) {
		this.pendingResponsibility = pendingResponsibility;
	}
	public String getProblemCustomerOrExicom() {
		return problemCustomerOrExicom;
	}
	public void setProblemCustomerOrExicom(String problemCustomerOrExicom) {
		this.problemCustomerOrExicom = problemCustomerOrExicom;
	}
	public String getCustomerOrSiteProblem() {
		return customerOrSiteProblem;
	}
	public void setCustomerOrSiteProblem(String customerOrSiteProblem) {
		this.customerOrSiteProblem = customerOrSiteProblem;
	}
	public BatteryComplaintStatus getComplaintStatus() {
		return complaintStatus;
	}
	public void setComplaintStatus(BatteryComplaintStatus complaintStatus) {
		this.complaintStatus = complaintStatus;
	}
	public String getOtherPendingIssue() {
		return otherPendingIssue;
	}
	public void setOtherPendingIssue(String otherPendingIssue) {
		this.otherPendingIssue = otherPendingIssue;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public EmployeeDetails getMdifiedBy() {
		return mdifiedBy;
	}
	public void setMdifiedBy(EmployeeDetails mdifiedBy) {
		this.mdifiedBy = mdifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	

	@javax.persistence.Transient transient BatteryFSR old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BatteryFSR)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BatteryFSR gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
