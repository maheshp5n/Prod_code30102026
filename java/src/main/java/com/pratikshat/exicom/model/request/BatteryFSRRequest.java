package com.pratikshat.exicom.model.request;

import java.util.Date;

public class BatteryFSRRequest {

	long id;
	
	long serviceAppointmentId;
	
	Date dateOfVisit;
	String vendorEngName;
	String vendorEngContact;
	String faultReportedByCustomer;
	String faultObservedByEngineer;
	
	int siteTypeId;
	
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
	
	String correctiveAction;
	String sparePartReplaced;
	
	String remark;

	long pendingResponsibilityId;
	
	String problemCustomerOrExicom;
	String customerOrSiteProblem;
	
	
	int complaintStatusId;
	
	String otherPendingIssue;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getServiceAppointmentId() {
		return serviceAppointmentId;
	}

	public void setServiceAppointmentId(long serviceAppointmentId) {
		this.serviceAppointmentId = serviceAppointmentId;
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

	public int getSiteTypeId() {
		return siteTypeId;
	}

	public void setSiteTypeId(int siteTypeId) {
		this.siteTypeId = siteTypeId;
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

	public long getPendingResponsibilityId() {
		return pendingResponsibilityId;
	}

	public void setPendingResponsibilityId(long pendingResponsibilityId) {
		this.pendingResponsibilityId = pendingResponsibilityId;
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

	public int getComplaintStatusId() {
		return complaintStatusId;
	}

	public void setComplaintStatusId(int complaintStatusId) {
		this.complaintStatusId = complaintStatusId;
	}

	public String getOtherPendingIssue() {
		return otherPendingIssue;
	}

	public void setOtherPendingIssue(String otherPendingIssue) {
		this.otherPendingIssue = otherPendingIssue;
	}
	
	
	

}
