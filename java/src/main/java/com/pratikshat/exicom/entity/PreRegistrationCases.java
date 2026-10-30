package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class PreRegistrationCases implements java.io.Serializable,OldPresist<PreRegistrationCases> {

	public PreRegistrationCases(long id) {
		this.id = id;
	}
	
	public PreRegistrationCases() {
	
	}

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long id;
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
	@ManyToOne(fetch=FetchType.LAZY)
	LegalEntity serviceCenter;
	
	@ManyToOne
	ServiceRecource engineer;
	String missingAssets;
	String RJILTT01No;
	@Column(length=1000)
	String otherProblemReported;
	boolean isAssignByAssignmentRule;
	
	String lastAction;
	String lastActionComment;
	
	String PONumber;
	Date PODate;
	boolean iSmsSend;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}

	public CaseTypes getCaseType() {
		return caseType;
	}

	public void setCaseType(CaseTypes caseType) {
		this.caseType = caseType;
	}

	public CaseReasonDetails getCaseReason() {
		return caseReason;
	}

	public void setCaseReason(CaseReasonDetails caseReason) {
		this.caseReason = caseReason;
	}

	public Assets getAssets() {
		return assets;
	}

	public void setAssets(Assets assets) {
		this.assets = assets;
	}

	public SiteDetails getSite() {
		return site;
	}

	public void setSite(SiteDetails site) {
		this.site = site;
	}

	public CaseStatusDetails getStatus() {
		return status;
	}

	public void setStatus(CaseStatusDetails status) {
		this.status = status;
	}

	public PendingResponsibility getPendingResponsibility() {
		return pendingResponsibility;
	}

	public void setPendingResponsibility(PendingResponsibility pendingResponsibility) {
		this.pendingResponsibility = pendingResponsibility;
	}

	public CaseStageDetails getStage() {
		return stage;
	}

	public void setStage(CaseStageDetails stage) {
		this.stage = stage;
	}

	public CaseOriginDetails getOrigin() {
		return origin;
	}

	public void setOrigin(CaseOriginDetails origin) {
		this.origin = origin;
	}

	public CasePriorityDetails getPriority() {
		return priority;
	}

	public void setPriority(CasePriorityDetails priority) {
		this.priority = priority;
	}

	public ContactDetails getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(ContactDetails contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getNoOfsmr() {
		return noOfsmr;
	}

	public void setNoOfsmr(String noOfsmr) {
		this.noOfsmr = noOfsmr;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getInputFromEng() {
		return inputFromEng;
	}

	public void setInputFromEng(String inputFromEng) {
		this.inputFromEng = inputFromEng;
	}

	public String getInternalRemark() {
		return internalRemark;
	}

	public void setInternalRemark(String internalRemark) {
		this.internalRemark = internalRemark;
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

	public String getManualId() {
		return manualId;
	}

	public void setManualId(String manualId) {
		this.manualId = manualId;
	}

	public LegalEntity getServiceCenter() {
		return serviceCenter;
	}

	public void setServiceCenter(LegalEntity serviceCenter) {
		this.serviceCenter = serviceCenter;
	}

	public ServiceRecource getEngineer() {
		return engineer;
	}

	public void setEngineer(ServiceRecource engineer) {
		this.engineer = engineer;
	}

	public String getMissingAssets() {
		return missingAssets;
	}

	public void setMissingAssets(String missingAssets) {
		this.missingAssets = missingAssets;
	}

	public String getRJILTT01No() {
		return RJILTT01No;
	}

	public void setRJILTT01No(String rJILTT01No) {
		RJILTT01No = rJILTT01No;
	}

	public String getOtherProblemReported() {
		return otherProblemReported;
	}

	public void setOtherProblemReported(String otherProblemReported) {
		this.otherProblemReported = otherProblemReported;
	}

	public boolean isAssignByAssignmentRule() {
		return isAssignByAssignmentRule;
	}

	public void setAssignByAssignmentRule(boolean isAssignByAssignmentRule) {
		this.isAssignByAssignmentRule = isAssignByAssignmentRule;
	}

	public String getLastAction() {
		return lastAction;
	}

	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}

	public EmployeeDetails getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(EmployeeDetails closedBy) {
		this.closedBy = closedBy;
	}

	public java.util.Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(java.util.Date closeDate) {
		this.closeDate = closeDate;
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

	public String getLastActionComment() {
		return lastActionComment;
	}

	public void setLastActionComment(String lastActionComment) {
		this.lastActionComment = lastActionComment;
	}

	@Transient transient PreRegistrationCases old;
	@Override
	@PostLoad
	public void old() {
		old=(PreRegistrationCases)SerializationUtils.clone(this);
	}
	@Override
	public PreRegistrationCases gOld() {
		// TODO Auto-generated method stub
		return old;
	}

	public boolean isiSmsSend() {
		return iSmsSend;
	}

	public void setiSmsSend(boolean iSmsSend) {
		this.iSmsSend = iSmsSend;
	}
	
	
	
}
