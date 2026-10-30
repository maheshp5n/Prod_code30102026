package com.pratikshat.exicom.model.request;

public class SecondServiceAppointmentRequest {

	long caseId;

	long serviceResourceId;

	boolean isAssignmentRule;

	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	public long getServiceResourceId() {
		return serviceResourceId;
	}

	public void setServiceResourceId(long serviceResourceId) {
		this.serviceResourceId = serviceResourceId;
	}

	public boolean isAssignmentRule() {
		return isAssignmentRule;
	}

	public void setAssignmentRule(boolean isAssignmentRule) {
		this.isAssignmentRule = isAssignmentRule;
	}

}
