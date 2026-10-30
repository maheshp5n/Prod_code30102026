package com.pratikshat.exicom.model.request;

import java.util.Date;

public class PrcRequest {

	long caseId;
	int caseRecordTypeId;
	long caseTypeId;
	long caseReasonId;
	long siteId;
	String assetsSerialNumber;
	int caseStatusId;
	int caseStageId;
	int caseOriginId;
	int casePriorityId;
	java.util.Set<com.pratikshat.exicom.entity.PrcProblemReported> caseProblemReported;
	long contactPersonId;
	String noOfSmr;
	String remarks;
	String ownerEmailId;
	long serviceCenterId;
	long serviceResourceId;
	String missingAssets;
	String RJILTT01No;
	String internalRemark;
	String otherProblemReported;
	
	boolean isAssignByAssignmentRule;
	String inputFromEng;
	long pendingResponsibility;
	
	
	String poNumber;
	Date poDate;
	boolean iSmsSend;
	
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

	/**
	 * @return the caseId
	 */
	public long getCaseId() {
		return caseId;
	}
	
	/**
	 * @return the caseTypeId
	 */
	public long getCaseTypeId() {
		return caseTypeId;
	}
	/**
	 * @return the caseReasonId
	 */
	public long getCaseReasonId() {
		return caseReasonId;
	}
	/**
	 * @return the assetsSerialNumber
	 */
	public String getAssetsSerialNumber() {
		return assetsSerialNumber;
	}
	
	
	
	/**
	 * @return the caseProblemReported
	 */
	public java.util.Set<com.pratikshat.exicom.entity.PrcProblemReported> getCaseProblemReported() {
		return caseProblemReported;
	}
	/**
	 * @return the contactPersonId
	 */
	public long getContactPersonId() {
		return contactPersonId;
	}
	/**
	 * @return the noOfSmr
	 */
	public String getNoOfSmr() {
		return noOfSmr;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @return the ownerEmailId
	 */
	public String getOwnerEmailId() {
		return ownerEmailId;
	}
	/**
	 * @param caseId the caseId to set
	 */
	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}
	
	/**
	 * @param caseTypeId the caseTypeId to set
	 */
	public void setCaseTypeId(long caseTypeId) {
		this.caseTypeId = caseTypeId;
	}
	/**
	 * @param caseReasonId the caseReasonId to set
	 */
	public void setCaseReasonId(long caseReasonId) {
		this.caseReasonId = caseReasonId;
	}
	/**
	 * @param assetsSerialNumber the assetsSerialNumber to set
	 */
	public void setAssetsSerialNumber(String assetsSerialNumber) {
		this.assetsSerialNumber = assetsSerialNumber;
	}
	
	
	
	/**
	 * @param caseProblemReported the caseProblemReported to set
	 */
	public void setCaseProblemReported(
			java.util.Set<com.pratikshat.exicom.entity.PrcProblemReported> caseProblemReported) {
		this.caseProblemReported = caseProblemReported;
	}
	/**
	 * @param contactPersonId the contactPersonId to set
	 */
	public void setContactPersonId(long contactPersonId) {
		this.contactPersonId = contactPersonId;
	}
	/**
	 * @param noOfSmr the noOfSmr to set
	 */
	public void setNoOfSmr(String noOfSmr) {
		this.noOfSmr = noOfSmr;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @param ownerEmailId the ownerEmailId to set
	 */
	public void setOwnerEmailId(String ownerEmailId) {
		this.ownerEmailId = ownerEmailId;
	}
	public int getCaseStatusId() {
		return caseStatusId;
	}
	public void setCaseStatusId(int caseStatusId) {
		this.caseStatusId = caseStatusId;
	}
	public int getCaseStageId() {
		return caseStageId;
	}
	public void setCaseStageId(int caseStageId) {
		this.caseStageId = caseStageId;
	}

	public int getCaseRecordTypeId() {
		return caseRecordTypeId;
	}

	public void setCaseRecordTypeId(int caseRecordTypeId) {
		this.caseRecordTypeId = caseRecordTypeId;
	}

	public int getCaseOriginId() {
		return caseOriginId;
	}

	public void setCaseOriginId(int caseOriginId) {
		this.caseOriginId = caseOriginId;
	}

	public int getCasePriorityId() {
		return casePriorityId;
	}

	public void setCasePriorityId(int casePriorityId) {
		this.casePriorityId = casePriorityId;
	}

	/**
	 * @return the serviceCenterId
	 */
	public long getServiceCenterId() {
		return serviceCenterId;
	}

	/**
	 * @return the serviceResourceId
	 */
	public long getServiceResourceId() {
		return serviceResourceId;
	}

	/**
	 * @param serviceCenterId the serviceCenterId to set
	 */
	public void setServiceCenterId(long serviceCenterId) {
		this.serviceCenterId = serviceCenterId;
	}

	/**
	 * @param serviceResourceId the serviceResourceId to set
	 */
	public void setServiceResourceId(long serviceResourceId) {
		this.serviceResourceId = serviceResourceId;
	}

	public String getRJILTT01No() {
		return RJILTT01No;
	}

	public void setRJILTT01No(String rJILTT01No) {
		RJILTT01No = rJILTT01No;
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

	public long getSiteId() {
		return siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public boolean isAssignByAssignmentRule() {
		return isAssignByAssignmentRule;
	}

	public void setAssignByAssignmentRule(boolean isAssignByAssignmentRule) {
		this.isAssignByAssignmentRule = isAssignByAssignmentRule;
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

	public long getPendingResponsibility() {
		return pendingResponsibility;
	}

	public void setPendingResponsibility(long pendingResponsibility) {
		this.pendingResponsibility = pendingResponsibility;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Date getPoDate() {
		return poDate;
	}

	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}

	public boolean isiSmsSend() {
		return iSmsSend;
	}

	public void setiSmsSend(boolean iSmsSend) {
		this.iSmsSend = iSmsSend;
	}
	
}
