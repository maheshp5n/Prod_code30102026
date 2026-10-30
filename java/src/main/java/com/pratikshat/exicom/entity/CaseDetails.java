package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@javax.persistence.Entity
@EntityListeners(ObjectAuditListener.class)
public class CaseDetails implements java.io.Serializable,OldPresist<CaseDetails>{
	public CaseDetails(long caseId) {
		this.caseId = caseId;
	}
	
	public CaseDetails() {
	
	}

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long caseId;
	@ManyToOne
	BusinessDomain businessDomain;
	@ManyToOne
	CaseTypes caseType;
	@ManyToOne
	CaseReasonDetails caseReason;
	@ManyToOne(fetch=FetchType.LAZY)
	Assets assets;
	@ManyToOne(fetch=FetchType.LAZY)
	SiteDetails site;
	@ManyToOne
	CaseStatusDetails status;
	@ManyToOne
	PendingResponsibility pendingResponsibility;
	@ManyToOne
	CaseStageDetails stage;
	@ManyToOne
	CaseOriginDetails origin;
	@ManyToOne
	CasePriorityDetails priority;
	
	@ManyToOne
	ContactDetails contactPerson;
	
	String noOfsmr;
	@Column(length=1000)
	String remarks;
	@Column(length=1000)
	String inputFromEng;
	@Column(length=1000)
	String internalRemark;
	//Record Created and updated details
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	java.util.Date createDate;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date modifiedDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails closedBy;
	java.util.Date closeDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails ownerDetails;
	
	String manualId;
	boolean synked;
	@ManyToOne(fetch=FetchType.LAZY)
	LegalEntity serviceCenter;
	
	@ManyToOne
	ServiceRecource engineer;
	String missingAssets;
	String RJILTT01No;
	@Column(length=1000)
	String otherProblemReported;
	boolean isAssignByAssignmentRule;
	
	String PODNumber;
	Date PODDate;
	String PONumber;
	Date PODate;
	String invoiceNumber;
	Date invoiceDate;
	String vendorInvoiceNumber;
	Date vendorInvoiceDate;
	
	String otherSARequest;
	
	String podShippingMode;
	String podShippingToName;
	String podShippingTrackingNumber;
	String podShippingExpectedDate;
	
   boolean iSmsSend;
	
	@ManyToOne(fetch=FetchType.LAZY)
	PreRegistrationCases prc;
	/**
	 * @return the caseId
	 */
	public long getCaseId() {
		return caseId;
	}

	

	/**
	 * @return the caseType
	 */
	public CaseTypes getCaseType() {
		return caseType;
	}

	/**
	 * @return the caseReason
	 */
	public CaseReasonDetails getCaseReason() {
		return caseReason;
	}

	/**
	 * @return the assets
	 */
	public Assets getAssets() {
		return assets;
	}

	/**
	 * @return the status
	 */
	public CaseStatusDetails getStatus() {
		return status;
	}

	/**
	 * @return the stage
	 */
	public CaseStageDetails getStage() {
		return stage;
	}

	/**
	 * @return the origin
	 */
	public CaseOriginDetails getOrigin() {
		return origin;
	}

	/**
	 * @return the priority
	 */
	public CasePriorityDetails getPriority() {
		return priority;
	}

	/**
	 * @return the noOfsmr
	 */
	public String getNoOfsmr() {
		return noOfsmr;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	/**
	 * @return the createDate
	 */
	public java.util.Date getCreateDate() {
		return createDate;
	}

	/**
	 * @return the modifiedBy
	 */
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @return the modifiedDate
	 */
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @return the ownerDetails
	 */
	public EmployeeDetails getOwnerDetails() {
		return ownerDetails;
	}

	/**
	 * @param caseId the caseId to set
	 */
	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	

	/**
	 * @param caseType the caseType to set
	 */
	public void setCaseType(CaseTypes caseType) {
		this.caseType = caseType;
	}

	/**
	 * @param caseReason the caseReason to set
	 */
	public void setCaseReason(CaseReasonDetails caseReason) {
		this.caseReason = caseReason;
	}

	/**
	 * @param assets the assets to set
	 */
	public void setAssets(Assets assets) {
		this.assets = assets;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(CaseStatusDetails status) {
		this.status = status;
	}

	/**
	 * @param stage the stage to set
	 */
	public void setStage(CaseStageDetails stage) {
		this.stage = stage;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(CaseOriginDetails origin) {
		this.origin = origin;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(CasePriorityDetails priority) {
		this.priority = priority;
	}

	/**
	 * @param noOfsmr the noOfsmr to set
	 */
	public void setNoOfsmr(String noOfsmr) {
		this.noOfsmr = noOfsmr;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @param ownerDetails the ownerDetails to set
	 */
	public void setOwnerDetails(EmployeeDetails ownerDetails) {
		this.ownerDetails = ownerDetails;
	}

	/**
	 * @return the contactPerson
	 */
	public ContactDetails getContactPerson() {
		return contactPerson;
	}

	/**
	 * @return the manualId
	 */
	public String getManualId() {
		return manualId;
	}

	/**
	 * @return the synked
	 */
	public boolean isSynked() {
		return synked;
	}

	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(ContactDetails contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @param manualId the manualId to set
	 */
	public void setManualId(String manualId) {
		this.manualId = manualId;
	}

	/**
	 * @param synked the synked to set
	 */
	public void setSynked(boolean synked) {
		this.synked = synked;
	}

	public java.util.Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(java.util.Date closeDate) {
		this.closeDate = closeDate;
	}

	public EmployeeDetails getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(EmployeeDetails closedBy) {
		this.closedBy = closedBy;
	}

	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}

	/**
	 * @return the serviceCenter
	 */
	public LegalEntity getServiceCenter() {
		return serviceCenter;
	}

	/**
	 * @return the engineer
	 */
	public ServiceRecource getEngineer() {
		return engineer;
	}

	/**
	 * @param serviceCenter the serviceCenter to set
	 */
	public void setServiceCenter(LegalEntity serviceCenter) {
		this.serviceCenter = serviceCenter;
	}

	/**
	 * @param engineer the engineer to set
	 */
	public void setEngineer(ServiceRecource engineer) {
		this.engineer = engineer;
	}

	/**
	 * @return the missingAssets
	 */
	public String getMissingAssets() {
		return missingAssets;
	}

	/**
	 * @param missingAssets the missingAssets to set
	 */
	public void setMissingAssets(String missingAssets) {
		this.missingAssets = missingAssets;
	}

	public String getRJILTT01No() {
		return RJILTT01No;
	}

	public void setRJILTT01No(String rJILTT01No) {
		RJILTT01No = rJILTT01No;
	}

	public PendingResponsibility getPendingResponsibility() {
		return pendingResponsibility;
	}

	public void setPendingResponsibility(PendingResponsibility pendingResponsibility) {
		this.pendingResponsibility = pendingResponsibility;
	}
	/**
	 * @return the otherProblemReported
	 */
	public String getOtherProblemReported() {
		return otherProblemReported;
	}

	/**
	 * @param otherProblemReported the otherProblemReported to set
	 */
	public void setOtherProblemReported(String otherProblemReported) {
		this.otherProblemReported = otherProblemReported;

	}

	public SiteDetails getSite() {
		return site;
	}

	public void setSite(SiteDetails site) {
		this.site = site;
	}

	public String getInputFromEng() {
		return inputFromEng;
	}

	public void setInputFromEng(String inputFromEng) {
		this.inputFromEng = inputFromEng;
	}

	public boolean isAssignByAssignmentRule() {
		return isAssignByAssignmentRule;
	}

	public void setAssignByAssignmentRule(boolean isAssignByAssignmentRule) {
		this.isAssignByAssignmentRule = isAssignByAssignmentRule;
	}

	public String getInternalRemark() {
		return internalRemark;
	}

	public void setInternalRemark(String internalRemark) {
		this.internalRemark = internalRemark;
	}

	public String getPODNumber() {
		return PODNumber;
	}

	public void setPODNumber(String pODNumber) {
		PODNumber = pODNumber;
	}

	public Date getPODDate() {
		return PODDate;
	}

	public void setPODDate(Date pODDate) {
		PODDate = pODDate;
	}

	public String getPONumber() {
		return PONumber;
	}

	public void setPONumber(String pONumber) {
		PONumber = pONumber;
	}

	public Date getPODate() {
		return PODate;
	}

	public void setPODate(Date pODate) {
		PODate = pODate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getVendorInvoiceNumber() {
		return vendorInvoiceNumber;
	}

	public void setVendorInvoiceNumber(String vendorInvoiceNumber) {
		this.vendorInvoiceNumber = vendorInvoiceNumber;
	}

	public Date getVendorInvoiceDate() {
		return vendorInvoiceDate;
	}

	public void setVendorInvoiceDate(Date vendorInvoiceDate) {
		this.vendorInvoiceDate = vendorInvoiceDate;
	}

	public String getOtherSARequest() {
		return otherSARequest;
	}

	public void setOtherSARequest(String otherSARequest) {
		this.otherSARequest = otherSARequest;
	}

	public String getPodShippingMode() {
		return podShippingMode;
	}

	public void setPodShippingMode(String podShippingMode) {
		this.podShippingMode = podShippingMode;
	}

	public String getPodShippingToName() {
		return podShippingToName;
	}

	public void setPodShippingToName(String podShippingToName) {
		this.podShippingToName = podShippingToName;
	}

	public String getPodShippingTrackingNumber() {
		return podShippingTrackingNumber;
	}

	public void setPodShippingTrackingNumber(String podShippingTrackingNumber) {
		this.podShippingTrackingNumber = podShippingTrackingNumber;
	}

	public String getPodShippingExpectedDate() {
		return podShippingExpectedDate;
	}

	public void setPodShippingExpectedDate(String podShippingExpectedDate) {
		this.podShippingExpectedDate = podShippingExpectedDate;
	}
	
	public PreRegistrationCases getPrc() {
		return prc;
	}

	public void setPrc(PreRegistrationCases prc) {
		this.prc = prc;
	}

	@Transient transient CaseDetails old;
	
	@Override
	@PostLoad
	public void old() {
		old=(CaseDetails)SerializationUtils.clone(this);
	}

	@Override
	public CaseDetails gOld() {
		// TODO Auto-generated method stub
		return old;
	}
	public String toString() {
		return String.valueOf(caseId);
	}

	public boolean isiSmsSend() {
		return iSmsSend;
	}

	public void setiSmsSend(boolean iSmsSend) {
		this.iSmsSend = iSmsSend;
	}
	
	
	

}
