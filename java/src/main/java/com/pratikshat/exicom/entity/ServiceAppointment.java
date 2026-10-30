package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ServiceAppointment implements java.io.Serializable,OldPresist<ServiceAppointment>{
	
	public ServiceAppointment(long serviceAppointmentId) {
		this.serviceAppointmentId = serviceAppointmentId;
	}
	
	public ServiceAppointment() {
	}

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long serviceAppointmentId;
	
	String appointmentNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	CaseDetails caseDetails;
	
	@Column(length=1000)
	String faultObserved;
	@Column(length=1000)
	String correctiveActions;
	@Column(length=1000)
	String punchPointExiom;
	@Column(length=1000)
	String punchPointCustomer;
	java.util.Date visitDate;
	String customerRepresentativeName;
	String customerRepresentativeContact;
	@Column(length=1000)
	String remark;
	
	@javax.persistence.ManyToOne
	ServiceRecource serviceResource;
	
	@ManyToOne
	ServiceAppointmentStatus status;
	
	@ManyToOne
	PendingResponsibility pendingResponsibility;
	
	String earliestStartPermittedDate;
	String earliestStartPermittedTime;
	
	String dueDate;
	String dueTime;
	String tFlag;
	boolean syncFlag;

	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	java.util.Date createDate;
	java.util.Date closeDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date modifiedDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails ownerDetails;
	@ManyToOne(fetch=FetchType.LAZY)
	CircleDetails circle;
	@ManyToOne(fetch=FetchType.LAZY)
	ContactDetails circleManager;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ClaimStatus claimStatus;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Assets assets;
	@ManyToOne(fetch=FetchType.LAZY)
	Products product;
	
	@Column(columnDefinition="integer default 0")
	int approvalStage;
	
	boolean isApprovedByFinance;
	Date approvedByFinance;
	String costCenter;
	
	@ManyToOne
	BusinessDomain businessDomain;
	@Column(columnDefinition = "bit default 0")
	boolean wcc=false;
	
	@Column(columnDefinition = "bit default 0")
	boolean reassign = false;
	
	String wccId;
	String wccManualId;
	
	String acknowledgedByName;
	String acknowledgedByPhone;
	
	double fixedPayout;
	Double latitude;
	Double longitude;
	@Column(columnDefinition = "TEXT")
	String address;
	
	String closedFromPlatform;
	
	String vfCode;
	
	@Column(columnDefinition = "bit default 0")
	boolean engAssigned = false;
	
	
	public long getServiceAppointmentId() {
		return serviceAppointmentId;
	}

	public void setServiceAppointmentId(long serviceAppointmentId) {
		this.serviceAppointmentId = serviceAppointmentId;
	}

	public CaseDetails getCaseDetails() {
		return caseDetails;
	}

	public void setCaseDetails(CaseDetails caseDetails) {
		this.caseDetails = caseDetails;
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

	public ServiceAppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(ServiceAppointmentStatus status) {
		this.status = status;
	}

	public PendingResponsibility getPendingResponsibility() {
		return pendingResponsibility;
	}

	public void setPendingResponsibility(PendingResponsibility pendingResponsibility) {
		this.pendingResponsibility = pendingResponsibility;
	}

	public String gettFlag() {
		return tFlag;
	}

	public void settFlag(String tFlag) {
		this.tFlag = tFlag;
	}

	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.util.Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(java.util.Date closeDate) {
		this.closeDate = closeDate;
	}

	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public EmployeeDetails getOwnerDetails() {
		return ownerDetails;
	}

	public void setOwnerDetails(EmployeeDetails ownerDetails) {
		this.ownerDetails = ownerDetails;
	}
	
	
	public boolean isSyncFlag() {
		return syncFlag;
	}

	public void setSyncFlag(boolean syncFlag) {
		this.syncFlag = syncFlag;
	}

	/**
	 * @return the appointmentNumber
	 */
	public String getAppointmentNumber() {
		return appointmentNumber;
	}

	/**
	 * @return the serviceResource
	 */
	public ServiceRecource getServiceResource() {
		return serviceResource;
	}

	/**
	 * @param appointmentNumber the appointmentNumber to set
	 */
	public void setAppointmentNumber(String appointmentNumber) {
		this.appointmentNumber = appointmentNumber;
	}

	/**
	 * @param serviceResource the serviceResource to set
	 */
	public void setServiceResource(ServiceRecource serviceResource) {
		this.serviceResource = serviceResource;
	}

	/**
	 * @return the circle
	 */
	public CircleDetails getCircle() {
		return circle;
	}

	/**
	 * @return the circleManager
	 */
	public ContactDetails getCircleManager() {
		return circleManager;
	}

	/**
	 * @param circle the circle to set
	 */
	public void setCircle(CircleDetails circle) {
		this.circle = circle;
	}

	/**
	 * @param circleManager the circleManager to set
	 */
	public void setCircleManager(ContactDetails circleManager) {
		this.circleManager = circleManager;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
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
	 * @return the assets
	 */
	public Assets getAssets() {
		return assets;
	}

	public boolean isEngAssigned() {
		return engAssigned;
	}

	public void setEngAssigned(boolean engAssigned) {
		this.engAssigned = engAssigned;
	}

	/**
	 * @return the product
	 */
	public Products getProduct() {
		return product;
	}

	/**
	 * @param assets the assets to set
	 */
	public void setAssets(Assets assets) {
		this.assets = assets;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Products product) {
		this.product = product;
	}

	public int getApprovalStage() {
		return approvalStage;
	}

	public void setApprovalStage(int approvalStage) {
		this.approvalStage = approvalStage;
	}

	public boolean isApprovedByFinance() {
		return isApprovedByFinance;
	}

	public void setApprovedByFinance(boolean isApprovedByFinance) {
		this.isApprovedByFinance = isApprovedByFinance;
	}

	public Date getApprovedByFinance() {
		return approvedByFinance;
	}

	public void setApprovedByFinance(Date approvedByFinance) {
		this.approvedByFinance = approvedByFinance;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}

	public boolean isWcc() {
		return wcc;
	}

	public void setWcc(boolean wcc) {
		this.wcc = wcc;
	}

	public boolean isReassign() {
		return reassign;
	}

	public void setReassign(boolean reassign) {
		this.reassign = reassign;
	}
	
	

	public String getWccId() {
		return wccId;
	}

	public void setWccId(String wccId) {
		this.wccId = wccId;
	}

	public String getWccManualId() {
		return wccManualId;
	}

	public void setWccManualId(String wccManualId) {
		this.wccManualId = wccManualId;
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

	@javax.persistence.Transient transient ServiceAppointment old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ServiceAppointment)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ServiceAppointment gOld() {
		return old;
	}
	public String toString() {
			return ""+serviceAppointmentId;
	}

	public double getFixedPayout() {
		return fixedPayout;
	}

	public void setFixedPayout(double fixedPayout) {
		this.fixedPayout = fixedPayout;
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

	public String getClosedFromPlatform() {
		return closedFromPlatform;
	}

	public void setClosedFromPlatform(String closedFromPlatform) {
		this.closedFromPlatform = closedFromPlatform;
	}

	public String getVfCode() {
		return vfCode;
	}

	public void setVfCode(String vfCode) {
		this.vfCode = vfCode;
	}

	
	
	
}
