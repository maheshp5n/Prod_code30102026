package com.pratikshat.exicom.model.request;

public class ServiceAppointmentReAssignRequest {

	long serviceAppointmentId;
	long caseId;
	long serviceCenterId;
	long serviceResourceId;
	
	int businessDomainId;

	public long getServiceAppointmentId() {
		return serviceAppointmentId;
	}

	public void setServiceAppointmentId(long serviceAppointmentId) {
		this.serviceAppointmentId = serviceAppointmentId;
	}

	public long getServiceCenterId() {
		return serviceCenterId;
	}

	public void setServiceCenterId(long serviceCenterId) {
		this.serviceCenterId = serviceCenterId;
	}

	public long getServiceResourceId() {
		return serviceResourceId;
	}

	public void setServiceResourceId(long serviceResourceId) {
		this.serviceResourceId = serviceResourceId;
	}

	public int getBusinessDomainId() {
		return businessDomainId;
	}

	public void setBusinessDomainId(int businessDomainId) {
		this.businessDomainId = businessDomainId;
	}

	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}
	
	
	
	
}
