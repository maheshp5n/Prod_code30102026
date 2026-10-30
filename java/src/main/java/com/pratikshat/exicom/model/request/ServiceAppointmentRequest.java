package com.pratikshat.exicom.model.request;

public class ServiceAppointmentRequest {

	long serviceAppointmentId;
	String faultObserved;
	long caseId;
	String correctiveActions;
	String punchPointExiom;
	String punchPointCustomer;
	java.util.Date visitDate;
	String customerRepresentativeName;
	String customerRepresentativeContact;
	String remark;
	
	long statusId;
	long pendingResponsibilityId;
	
	String earliestStartPermittedDate;
	String earliestStartPermittedTime;
	
	String dueDate;
	String dueTime;
	String tFlag;
	
	String ownerId;
	String assetsSerialNumber;
	String partCode;
	
	int businessDomainId;
	
	String acknowledgedByName;
	String acknowledgedByPhone;
	
	Double latitude;
	Double longitude;
	String address;
	
	String eSignature;
	
	String closedFromPlatform;
	
	public long getServiceAppointmentId() {
		return serviceAppointmentId;
	}

	public void setServiceAppointmentId(long serviceAppointmentId) {
		this.serviceAppointmentId = serviceAppointmentId;
	}

	public String getFaultObserved() {
		return faultObserved;
	}

	public void setFaultObserved(String faultObserved) {
		this.faultObserved = faultObserved;
	}

	public String getCorrectiveActions() {
		return correctiveActions;
	}

	public void setCorrectiveActions(String correctiveActions) {
		this.correctiveActions = correctiveActions;
	}

	public String getPunchPointExiom() {
		return punchPointExiom;
	}

	public void setPunchPointExiom(String punchPointExiom) {
		this.punchPointExiom = punchPointExiom;
	}

	public String getPunchPointCustomer() {
		return punchPointCustomer;
	}

	public void setPunchPointCustomer(String punchPointCustomer) {
		this.punchPointCustomer = punchPointCustomer;
	}

	public java.util.Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(java.util.Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getCustomerRepresentativeName() {
		return customerRepresentativeName;
	}

	public void setCustomerRepresentativeName(String customerRepresentativeName) {
		this.customerRepresentativeName = customerRepresentativeName;
	}

	public String getCustomerRepresentativeContact() {
		return customerRepresentativeContact;
	}

	public void setCustomerRepresentativeContact(String customerRepresentativeContact) {
		this.customerRepresentativeContact = customerRepresentativeContact;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public long getPendingResponsibilityId() {
		return pendingResponsibilityId;
	}

	public void setPendingResponsibilityId(long pendingResponsibilityId) {
		this.pendingResponsibilityId = pendingResponsibilityId;
	}

	public String gettFlag() {
		return tFlag;
	}

	public void settFlag(String tFlag) {
		this.tFlag = tFlag;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the caseId
	 */
	public long getCaseId() {
		return caseId;
	}

	/**
	 * @param caseId the caseId to set
	 */
	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	public String getEarliestStartPermittedDate() {
		return earliestStartPermittedDate;
	}

	public void setEarliestStartPermittedDate(String earliestStartPermittedDate) {
		this.earliestStartPermittedDate = earliestStartPermittedDate;
	}

	public String getEarliestStartPermittedTime() {
		return earliestStartPermittedTime;
	}

	public void setEarliestStartPermittedTime(String earliestStartPermittedTime) {
		this.earliestStartPermittedTime = earliestStartPermittedTime;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getDueTime() {
		return dueTime;
	}

	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}

	/**
	 * @return the assetsSerialNumber
	 */
	public String getAssetsSerialNumber() {
		return assetsSerialNumber;
	}

	/**
	 * @return the partCode
	 */
	public String getPartCode() {
		return partCode;
	}

	/**
	 * @param assetsSerialNumber the assetsSerialNumber to set
	 */
	public void setAssetsSerialNumber(String assetsSerialNumber) {
		this.assetsSerialNumber = assetsSerialNumber;
	}

	/**
	 * @param partCode the partCode to set
	 */
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public int getBusinessDomainId() {
		return businessDomainId;
	}

	public void setBusinessDomainId(int businessDomainId) {
		this.businessDomainId = businessDomainId;
	}

	public String getAcknowledgedByName() {
		return acknowledgedByName;
	}

	public void setAcknowledgedByName(String acknowledgedByName) {
		this.acknowledgedByName = acknowledgedByName;
	}

	public String getAcknowledgedByPhone() {
		return acknowledgedByPhone;
	}

	public void setAcknowledgedByPhone(String acknowledgedByPhone) {
		this.acknowledgedByPhone = acknowledgedByPhone;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String geteSignature() {
		return eSignature;
	}

	public void seteSignature(String eSignature) {
		this.eSignature = eSignature;
	}

	public String getClosedFromPlatform() {
		return closedFromPlatform;
	}

	public void setClosedFromPlatform(String closedFromPlatform) {
		this.closedFromPlatform = closedFromPlatform;
	}
}
