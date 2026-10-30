package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.BtRMADetails;



public class BtRmaResponse {

	Long id;

	String manualId;

	String rmaStatus;

	long caseDetails;

	long serviceAppointment;

	Long batteryGrnId;

	Date closeDate;

	

	public BtRmaResponse() {
		super();
		// TODO Auto-generated constructor stub
	}


	public BtRmaResponse(BtRMADetails rmaDetails) {
		this.id = rmaDetails.getId();
		this.manualId = rmaDetails.getManualId();
		this.rmaStatus = rmaDetails.getManualId();
		this.caseDetails = rmaDetails.getCaseDetails().getId();
		if (rmaDetails.getServiceAppointment() != null)
			this.serviceAppointment = rmaDetails.getServiceAppointment().getId();
		if(rmaDetails.getBatteryGrnId()!=null)
		this.batteryGrnId = rmaDetails.getBatteryGrnId();
		if (rmaDetails.getCloseDate() != null)
			this.closeDate = rmaDetails.getCloseDate();
	}
	

	public BtRmaResponse(Long id, String manualId, String rmaStatus, long caseDetails, long serviceAppointment,
			Long batteryGrnId, Date closeDate) {;
		this.id = id;
		this.manualId = manualId;
		this.rmaStatus = rmaStatus;
		this.caseDetails = caseDetails;
		this.serviceAppointment = serviceAppointment;
		this.batteryGrnId = batteryGrnId;
		this.closeDate = closeDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getManualId() {
		return manualId;
	}

	public void setManualId(String manualId) {
		this.manualId = manualId;
	}

	public String getRmaStatus() {
		return rmaStatus;
	}

	public void setRmaStatus(String rmaStatus) {
		this.rmaStatus = rmaStatus;
	}

	public long getCaseDetails() {
		return caseDetails;
	}

	public void setCaseDetails(long caseDetails) {
		this.caseDetails = caseDetails;
	}

	public long getServiceAppointment() {
		return serviceAppointment;
	}

	public void setServiceAppointment(long serviceAppointment) {
		this.serviceAppointment = serviceAppointment;
	}

	public Long getBatteryGrnId() {
		return batteryGrnId;
	}

	public void setBatteryGrnId(Long batteryGrnId) {
		this.batteryGrnId = batteryGrnId;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

}
