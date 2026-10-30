package com.pratikshat.exicom.model.response;

import java.util.Date;
import java.util.Set;

import com.pratikshat.exicom.entity.BatteryFSR;
import com.pratikshat.exicom.entity.CaseProblemReported;

public class BatteryFSRResponse {

	long id;
	String manualId;
	
	long serviceAppointmentId;
	String serviceAppointmentManualId;
	
	long caseId;
	String caseManualId;
	
	String technicianFirstName;
	String technicianMiddleName;
	String technicianLastName;
	String technicianEmailId;
	
	String technicianContact;

	Date dateOfVisit;
	
	Set<CaseProblemReported> problemReported;
	
	String vendorEngName;
	String vendorEngContact;
	
	String faultReportedByCustomer;
	String faultObservedByEngineer;
	
	int siteTypeId;
	String siteTypeName;
	
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
	String pendingResponsibilityName;
	
	String problemCustomerOrExicom;
	String customerOrSiteProblem;
	
	int complaintStatusId;
	String complaintStatusName;
	
	String otherPendingIssue;

	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	String createdByEmailId;
	Date createdDate;
	
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	String modifiedByEmailId;
	Date modifiedDate;
	public BatteryFSRResponse() {}
	
	public BatteryFSRResponse(BatteryFSR batteryFSR) {
		
		this.id = batteryFSR.getId();
		this.manualId = batteryFSR.getManualId();
		
		if(batteryFSR.getServiceAppointment()!=null) {
			this.serviceAppointmentId = batteryFSR.getServiceAppointment().getServiceAppointmentId();
			this.serviceAppointmentManualId = batteryFSR.getServiceAppointment().getAppointmentNumber();
			if(batteryFSR.getServiceAppointment().getCaseDetails()!=null) {
				this.caseId = batteryFSR.getServiceAppointment().getCaseDetails().getCaseId();
				this.caseManualId = batteryFSR.getServiceAppointment().getCaseDetails().getManualId();
			}
			
			if(batteryFSR.getServiceAppointment().getServiceResource()!=null) {
				if(batteryFSR.getServiceAppointment().getServiceResource().getContact()!=null) {
				
					this.technicianFirstName = batteryFSR.getServiceAppointment().getServiceResource().getContact().getFirstName();
					this.technicianMiddleName = batteryFSR.getServiceAppointment().getServiceResource().getContact().getMiddleName();
					this.technicianLastName = batteryFSR.getServiceAppointment().getServiceResource().getContact().getLastName();
					this.technicianEmailId = batteryFSR.getServiceAppointment().getServiceResource().getContact().getEmailId();
					
					this.technicianContact = batteryFSR.getServiceAppointment().getServiceResource().getContact().getMobile();
				}
			}
			
		}
	

		this.dateOfVisit = batteryFSR.getDateOfVisit();
		
		this.vendorEngName = batteryFSR.getVendorEngName();
		this.vendorEngContact = batteryFSR.getVendorEngContact();
		
		this.faultReportedByCustomer = batteryFSR.getFaultReportedByCustomer();
		this.faultObservedByEngineer = batteryFSR.getFaultObservedByEngineer();
		
		if(batteryFSR.getSiteType()!=null) {
			this.siteTypeId = batteryFSR.getSiteType().getId();
			this.siteTypeName = batteryFSR.getSiteType().getName();
		}
		
		
		this.siteEnvCond = batteryFSR.getSiteEnvCond();
		this.EBAvailability = batteryFSR.getEBAvailability();
		this.DGRun = batteryFSR.getDGRun();
		this.DGRating = batteryFSR.getDGRating();
		this.earthingStatus = batteryFSR.getEarthingStatus();
		this.DCLoad = batteryFSR.getDCLoad();
		this.powerPlantMakeAndCapacity = batteryFSR.getPowerPlantMakeAndCapacity();
		this.ambientTemp = batteryFSR.getAmbientTemp();
		this.softVersionCBMS = batteryFSR.getSoftVersionCBMS();
		this.batteryVoltageBeforRepair = batteryFSR.getBatteryVoltageBeforRepair();
		this.batteryVoltageAfterRepair = batteryFSR.getBatteryVoltageAfterRepair();
		
		this.correctiveAction = batteryFSR.getCorrectiveAction();
		this.sparePartReplaced = batteryFSR.getSparePartReplaced();
		this.remark = batteryFSR.getRemark();
		
		if(batteryFSR.getPendingResponsibility()!=null) {
			this.pendingResponsibilityId = batteryFSR.getPendingResponsibility().getPendingResponsibilityId();
			this.pendingResponsibilityName = batteryFSR.getPendingResponsibility().getPendingResponsibilityName();
		}
		
		this.problemCustomerOrExicom = batteryFSR.getProblemCustomerOrExicom();
		this.customerOrSiteProblem = batteryFSR.getCustomerOrSiteProblem();
		
		if(batteryFSR.getComplaintStatus()!=null) {
			this.complaintStatusId = batteryFSR.getComplaintStatus().getId();
			this.complaintStatusName = batteryFSR.getComplaintStatus().getName();
		}
		
		
		this.otherPendingIssue = batteryFSR.getOtherPendingIssue();

		if(batteryFSR.getCreatedBy()!=null) {
			this.createdByFirstName = batteryFSR.getCreatedBy().getFirstName();
			this.createdByMiddleName = batteryFSR.getCreatedBy().getMiddleName();
			this.createdByLastName = batteryFSR.getCreatedBy().getLastName();
			this.createdByEmailId = batteryFSR.getCreatedBy().getEmailId();
			this.createdDate = batteryFSR.getCreatedDate();
		}
		
		if(batteryFSR.getMdifiedBy()!=null) {
			this.modifiedByFirstName = batteryFSR.getMdifiedBy().getFirstName();
			this.modifiedByLastName = batteryFSR.getMdifiedBy().getLastName();
			this.modifiedByMiddleName = batteryFSR.getMdifiedBy().getMiddleName();
			this.modifiedByEmailId = batteryFSR.getMdifiedBy().getEmailId();
			this.modifiedDate = batteryFSR.getModifiedDate();	
		}
		
		
	}
	
	
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
	public long getServiceAppointmentId() {
		return serviceAppointmentId;
	}
	public void setServiceAppointmentId(long serviceAppointmentId) {
		this.serviceAppointmentId = serviceAppointmentId;
	}
	public String getServiceAppointmentManualId() {
		return serviceAppointmentManualId;
	}
	public void setServiceAppointmentManualId(String serviceAppointmentManualId) {
		this.serviceAppointmentManualId = serviceAppointmentManualId;
	}
	public long getCaseId() {
		return caseId;
	}
	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}
	public String getCaseManualId() {
		return caseManualId;
	}
	public void setCaseManualId(String caseManualId) {
		this.caseManualId = caseManualId;
	}
	public String getTechnicianFirstName() {
		return technicianFirstName;
	}
	public void setTechnicianFirstName(String technicianFirstName) {
		this.technicianFirstName = technicianFirstName;
	}
	public String getTechnicianLastName() {
		return technicianLastName;
	}
	public void setTechnicianLastName(String technicianLastName) {
		this.technicianLastName = technicianLastName;
	}
	public String getTechnicianEmailId() {
		return technicianEmailId;
	}
	public void setTechnicianEmailId(String technicianEmailId) {
		this.technicianEmailId = technicianEmailId;
	}
	public String getTechnicianContact() {
		return technicianContact;
	}
	public void setTechnicianContact(String technicianContact) {
		this.technicianContact = technicianContact;
	}
	public Date getDateOfVisit() {
		return dateOfVisit;
	}
	public void setDateOfVisit(Date dateOfVisit) {
		this.dateOfVisit = dateOfVisit;
	}
	public Set<CaseProblemReported> getProblemReported() {
		return problemReported;
	}
	public void setProblemReported(Set<CaseProblemReported> problemReported) {
		this.problemReported = problemReported;
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
	public String getSiteTypeName() {
		return siteTypeName;
	}
	public void setSiteTypeName(String siteTypeName) {
		this.siteTypeName = siteTypeName;
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
	public String getPendingResponsibilityName() {
		return pendingResponsibilityName;
	}
	public void setPendingResponsibilityName(String pendingResponsibilityName) {
		this.pendingResponsibilityName = pendingResponsibilityName;
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
	public String getComplaintStatusName() {
		return complaintStatusName;
	}
	public void setComplaintStatusName(String complaintStatusName) {
		this.complaintStatusName = complaintStatusName;
	}
	public String getOtherPendingIssue() {
		return otherPendingIssue;
	}
	public void setOtherPendingIssue(String otherPendingIssue) {
		this.otherPendingIssue = otherPendingIssue;
	}
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}

	public void setMdifiedByFirstName(String mdifiedByFirstName) {
		this.modifiedByFirstName = mdifiedByFirstName;
	}

	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getTechnicianMiddleName() {
		return technicianMiddleName;
	}

	public void setTechnicianMiddleName(String technicianMiddleName) {
		this.technicianMiddleName = technicianMiddleName;
	}

	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}

	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}

	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}

	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}
	
	
}
