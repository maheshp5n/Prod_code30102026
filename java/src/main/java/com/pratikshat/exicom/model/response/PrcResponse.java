package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.CaseDetails;

public class PrcResponse {
	
	long prcCaseId;
	String prcCaseManualId;
	int caseRecordTypeId;
	String caseRecordTypeName;
	long caseTypeId;
	int verticalTypeId;
	String verticalTypeName;
	String caseTypeName;
	long caseReasonId;
	String caseReasonName;
	String caseReasonVerticalName;
	int caseReasonVerticalId;
	String assetsSerialNumber;
	String assetsPartCode;
	String assetDescription;
	String assetsName;
	String caseStatusName;
	int caseStatusId;
	String caseStageName;
	int caseStageId;
	String caseOriginName;
	int caseOriginId;
	String casePriorityName;
	int casePriorityId;
	java.util.Set<com.pratikshat.exicom.entity.PrcProblemReported> problemReported;
	long contactPersonId;
	String contactPersonFirstName;
	String contactPersonMiddleName;
	String contactPersonLastName;
	String contactPersonEmailId;
	String noOfSmr;
	String remarks;
	String createdByEmailId;
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	java.util.Date createdDate;
	java.util.Date closedDate;
	String modifiedByEmailId;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	java.util.Date modifiedDate;
	String otherProblemReported;
	String contactPersonContactNo;
	Date dateOfVisit;
	Date dateTimeOpened;
	Date dateTimeClosed;
	String RJIL_TT01_No;
	String pendingResponsibility;
	long pendingResponsibilityId;
	
	String SLATime;
	String callCenterRepFirstName;
	String callCenterRepMiddleName;
	String callCenterRepLastName;
	
	String accountSapNumber;
	String accountName;
	String primaryAccountSapNumber;
	String primaryAccountName;
	String siteManualId;
	String siteName;
	long siteId;
	String caseStageVerticalName;
	int caseStageVerticalId;
	long serviceResourceId;
	long serviceResouceContactId;
	String serviceResourceName;
	long serviceCenterId;
	String serviceCenterName;
	String missingAssets;
	String caseOwnerFirstName;
	String caseOwnerMiddleName;
	String caseOwnerLastName;
	boolean isAssignByAssignmentRule;
	String inputFromEng;
	String internalRemark;
	String lastAction;
	String lastActionComment;
	
	String caseManualId;
	long caseId;
	boolean iSmsSend;
	public String getInternalRemark() {
		return internalRemark;
	}
	public void setInternalRemark(String internalRemark) {
		this.internalRemark = internalRemark;
	}
	public PrcResponse() {
		// TODO Auto-generated constructor stub
	}
	public PrcResponse(com.pratikshat.exicom.entity.PreRegistrationCases prcCaseDetails, CaseDetails caseDetails) {
		setData(prcCaseDetails,null,caseDetails);
	}
//	public PrcResponse(com.pratikshat.exicom.entity.PreRegistrationCases prcCaseDetails,java.util.Set<com.pratikshat.exicom.entity.PrcProblemReported> problemReported ) {
//		setData(prcCaseDetails,problemReported,null);
//	}
	
	private void setData(com.pratikshat.exicom.entity.PreRegistrationCases prcCaseDetails,java.util.Set<com.pratikshat.exicom.entity.PrcProblemReported> problemReported, CaseDetails caseDetails) {
		this.prcCaseId=prcCaseDetails.getId();
		this.prcCaseManualId=prcCaseDetails.getManualId();
		this.missingAssets = prcCaseDetails.getMissingAssets();
		this.iSmsSend=prcCaseDetails.isiSmsSend();
		if(prcCaseDetails.getBusinessDomain()!=null) {
			this.caseRecordTypeName=prcCaseDetails.getBusinessDomain().getBusinessDomainName();
			this.caseRecordTypeId = prcCaseDetails.getBusinessDomain().getBusinessDomainId();
		}
			
		if(prcCaseDetails.getCaseType()!=null) {
			this.caseTypeId=prcCaseDetails.getCaseType().getCaseTypeId();
			this.verticalTypeName=prcCaseDetails.getCaseType().getVertical().getBusinessDomainName();
			this.verticalTypeId = prcCaseDetails.getCaseType().getVertical().getBusinessDomainId();
			this.caseTypeName=prcCaseDetails.getCaseType().getCaseTypesName();
			
		}
		if(prcCaseDetails.getCaseReason()!=null) {
			this.caseReasonId=prcCaseDetails.getCaseReason().getReasonId();
			this.caseReasonName=prcCaseDetails.getCaseReason().getCaseReasonDetailsName();
			this.caseReasonVerticalName=prcCaseDetails.getCaseReason().getVertical().getBusinessDomainName();
			this.caseReasonVerticalId = prcCaseDetails.getCaseReason().getVertical().getBusinessDomainId();
		}
		if(prcCaseDetails.getAssets()!=null) {
			this.assetsSerialNumber=prcCaseDetails.getAssets().getSerialNumber();
			
			if(prcCaseDetails.getAssets().getProduct()!=null) {
				this.assetsPartCode=prcCaseDetails.getAssets().getProduct().getProductPartCode();
				this.assetsName=prcCaseDetails.getAssets().getProduct().getPdoductDescription();
				this.assetDescription = prcCaseDetails.getAssets().getProduct().getPdoductDescription();
			}
			
		} 
		
		if(prcCaseDetails.getSite()!=null) {
			this.siteId=prcCaseDetails.getSite().getSiteId();
			this.siteManualId=prcCaseDetails.getSite().getManualSiteId();
			this.siteName=prcCaseDetails.getSite().getSiteName();
			
			if(prcCaseDetails.getSite().getAccount()!=null) {
				this.accountName=prcCaseDetails.getSite().getAccount().getName();
				this.accountSapNumber=prcCaseDetails.getSite().getAccount().getSapCustomerNumber();
			}
			
			if(prcCaseDetails.getSite().getPrimaryAccount()!=null) {
				this.primaryAccountName=prcCaseDetails.getSite().getPrimaryAccount().getName();
				this.primaryAccountSapNumber=prcCaseDetails.getSite().getPrimaryAccount().getSapCustomerNumber();
			}
		}
		
		if(prcCaseDetails.getStatus()!=null) {
			this.caseStatusName=prcCaseDetails.getStatus().getCaseStatusName();
			this.caseStatusId = prcCaseDetails.getStatus().getId();
		}
			
		if(prcCaseDetails.getStage()!=null) {
			this.caseStageName=prcCaseDetails.getStage().getCaseStageName();
			this.caseStageId = prcCaseDetails.getStage().getId();
			this.caseStageVerticalName= prcCaseDetails.getStage().getDivision().getBusinessDomainName();
			this.caseStageVerticalId= prcCaseDetails.getStage().getDivision().getBusinessDomainId();
		}
			
		if(prcCaseDetails.getOrigin()!=null) {
			this.caseOriginName=prcCaseDetails.getOrigin().getCaseOrignName();
			this.caseOriginId = prcCaseDetails.getOrigin().getCaseOriginId();
		}
			
		if(prcCaseDetails.getPriority()!=null) {
			this.casePriorityName=prcCaseDetails.getPriority().getCasePriorityName();
			this.casePriorityId = prcCaseDetails.getPriority().getCasePriorityId();
		}
			
		if(problemReported!=null) {
			this.problemReported=problemReported;
		}
		if(prcCaseDetails.getContactPerson()!=null) {
			this.contactPersonId=prcCaseDetails.getContactPerson().getContactId();
			this.contactPersonFirstName=prcCaseDetails.getContactPerson().getFirstName();
			this.contactPersonMiddleName=prcCaseDetails.getContactPerson().getMiddleName();
			this.contactPersonLastName=prcCaseDetails.getContactPerson().getLastName();
			this.contactPersonEmailId=prcCaseDetails.getContactPerson().getEmailId();
			this.contactPersonContactNo = prcCaseDetails.getContactPerson().getMobile();
		}
		this.noOfSmr=prcCaseDetails.getNoOfsmr();
		this.remarks=prcCaseDetails.getRemarks();
		if(prcCaseDetails.getCreatedBy()!=null) {
			this.createdByEmailId=prcCaseDetails.getCreatedBy().getEmailId();
			this.createdByFirstName=prcCaseDetails.getCreatedBy().getFirstName();
			this.createdByMiddleName=prcCaseDetails.getCreatedBy().getMiddleName();
			this.createdByLastName=prcCaseDetails.getCreatedBy().getLastName();
			this.callCenterRepFirstName=prcCaseDetails.getCreatedBy().getFirstName();
			this.callCenterRepLastName=prcCaseDetails.getCreatedBy().getLastName();
			this.callCenterRepMiddleName=prcCaseDetails.getCreatedBy().getMiddleName();
		}
		this.createdDate=prcCaseDetails.getCreateDate();
		this.closedDate=prcCaseDetails.getCloseDate();
		if(prcCaseDetails.getModifiedBy()!=null) {
			this.modifiedByEmailId=prcCaseDetails.getModifiedBy().getEmailId();
			this.modifiedByFirstName=prcCaseDetails.getModifiedBy().getFirstName();
			this.modifiedByMiddleName=prcCaseDetails.getModifiedBy().getMiddleName();
			this.modifiedByLastName=prcCaseDetails.getModifiedBy().getLastName();
		}
		this.modifiedDate=prcCaseDetails.getModifiedDate();
		if(prcCaseDetails.getServiceCenter()!=null) {
			this.serviceCenterId=prcCaseDetails.getServiceCenter().getLegalEntityId();
			this.serviceCenterName=prcCaseDetails.getServiceCenter().getLegalEntityName();
		}
		if(prcCaseDetails.getEngineer()!=null) {
			this.serviceResourceId=prcCaseDetails.getEngineer().getServiceResourceId();
			this.serviceResourceName=prcCaseDetails.getEngineer().getName();
			if(prcCaseDetails.getEngineer().getContact()!=null)
				this.serviceResouceContactId=prcCaseDetails.getEngineer().getContact().getContactId();
		}
		
		if(prcCaseDetails.getPendingResponsibility()!=null) {
			this.pendingResponsibilityId = prcCaseDetails.getPendingResponsibility().getPendingResponsibilityId();
			this.pendingResponsibility = prcCaseDetails.getPendingResponsibility().getPendingResponsibilityName();
		}
			
		if(prcCaseDetails.getOwnerDetails()!=null) {
			this.caseOwnerFirstName=prcCaseDetails.getOwnerDetails().getFirstName();
			this.caseOwnerMiddleName=prcCaseDetails.getOwnerDetails().getMiddleName();
			this.caseOwnerLastName=prcCaseDetails.getOwnerDetails().getLastName();
		}
		
		this.RJIL_TT01_No = prcCaseDetails.getRJILTT01No();
		this.otherProblemReported=prcCaseDetails.getOtherProblemReported();
		this.isAssignByAssignmentRule = prcCaseDetails.isAssignByAssignmentRule();
		this.inputFromEng = prcCaseDetails.getInputFromEng();
		this.internalRemark = prcCaseDetails.getInternalRemark();
		
		if(prcCaseDetails.getStatus()!=null) {
			if(prcCaseDetails.getStatus().getId()==2) { // Closed
				this.SLATime = MiscellaneousUtils.slaCalculation(prcCaseDetails.getCreateDate(), prcCaseDetails.getCloseDate());
			}else {
				this.SLATime = MiscellaneousUtils.slaCalculation(prcCaseDetails.getCreateDate(), new Date());
			}
		}else {
			this.SLATime = MiscellaneousUtils.slaCalculation(prcCaseDetails.getCreateDate(), new Date());
		}
		
		this.lastAction = prcCaseDetails.getLastAction();
		this.lastActionComment = prcCaseDetails.getLastActionComment();
		
		if(caseDetails!=null) {
			this.caseId = caseDetails.getCaseId();
			this.caseManualId = caseDetails.getManualId();
		}
	}
	/**
	 * @return the caseId
	 */
	public long getCaseId() {
		return caseId;
	}

	/**
	 * @return the caseRecordTypeName
	 */
	public String getCaseRecordTypeName() {
		return caseRecordTypeName;
	}

	/**
	 * @return the caseTypeId
	 */
	public long getCaseTypeId() {
		return caseTypeId;
	}

	

	/**
	 * @return the caseTypeName
	 */
	public String getCaseTypeName() {
		return caseTypeName;
	}

	/**
	 * @return the caseReasonId
	 */
	public long getCaseReasonId() {
		return caseReasonId;
	}

	/**
	 * @return the caseReasonName
	 */
	public String getCaseReasonName() {
		return caseReasonName;
	}

	

	/**
	 * @return the assetsSerialNumber
	 */
	public String getAssetsSerialNumber() {
		return assetsSerialNumber;
	}

	/**
	 * @return the assetsPartCode
	 */
	public String getAssetsPartCode() {
		return assetsPartCode;
	}

	/**
	 * @return the assetsName
	 */
	public String getAssetsName() {
		return assetsName;
	}

	/**
	 * @return the caseStatusName
	 */
	public String getCaseStatusName() {
		return caseStatusName;
	}

	/**
	 * @return the caseStageName
	 */
	public String getCaseStageName() {
		return caseStageName;
	}

	/**
	 * @return the caseOriginName
	 */
	public String getCaseOriginName() {
		return caseOriginName;
	}

	
	/**
	 * @return the problemReported
	 */
	public java.util.Set<com.pratikshat.exicom.entity.PrcProblemReported> getProblemReported() {
		return problemReported;
	}

	/**
	 * @return the contactPersonId
	 */
	public long getContactPersonId() {
		return contactPersonId;
	}

	/**
	 * @return the contactPersonName
	 */
	public String getContactPersonFirstName() {
		return contactPersonFirstName;
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
	 * @return the createdByEmailId
	 */
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}

	/**
	 * @return the createdByFirstName
	 */
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}

	/**
	 * @return the createdByLastName
	 */
	public String getCreatedByLastName() {
		return createdByLastName;
	}

	/**
	 * @return the createdDate
	 */
	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @return the modifiedByEmailId
	 */
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}

	/**
	 * @return the modifiedByFirstName
	 */
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}

	/**
	 * @return the modifiedByLastName
	 */
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}

	/**
	 * @return the modifiedDate
	 */
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param caseId the caseId to set
	 */
	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	/**
	 * @param caseRecordTypeName the caseRecordTypeName to set
	 */
	public void setCaseRecordTypeName(String caseRecordTypeName) {
		this.caseRecordTypeName = caseRecordTypeName;
	}

	/**
	 * @param caseTypeId the caseTypeId to set
	 */
	public void setCaseTypeId(long caseTypeId) {
		this.caseTypeId = caseTypeId;
	}

	
	/**
	 * @param caseTypeName the caseTypeName to set
	 */
	public void setCaseTypeName(String caseTypeName) {
		this.caseTypeName = caseTypeName;
	}

	/**
	 * @param caseReasonId the caseReasonId to set
	 */
	public void setCaseReasonId(long caseReasonId) {
		this.caseReasonId = caseReasonId;
	}

	/**
	 * @param caseReasonName the caseReasonName to set
	 */
	public void setCaseReasonName(String caseReasonName) {
		this.caseReasonName = caseReasonName;
	}

	

	/**
	 * @param assetsSerialNumber the assetsSerialNumber to set
	 */
	public void setAssetsSerialNumber(String assetsSerialNumber) {
		this.assetsSerialNumber = assetsSerialNumber;
	}

	/**
	 * @param assetsPartCode the assetsPartCode to set
	 */
	public void setAssetsPartCode(String assetsPartCode) {
		this.assetsPartCode = assetsPartCode;
	}

	/**
	 * @param assetsName the assetsName to set
	 */
	public void setAssetsName(String assetsName) {
		this.assetsName = assetsName;
	}

	/**
	 * @param caseStatusName the caseStatusName to set
	 */
	public void setCaseStatusName(String caseStatusName) {
		this.caseStatusName = caseStatusName;
	}

	/**
	 * @param caseStageName the caseStageName to set
	 */
	public void setCaseStageName(String caseStageName) {
		this.caseStageName = caseStageName;
	}

	/**
	 * @param caseOriginName the caseOriginName to set
	 */
	public void setCaseOriginName(String caseOriginName) {
		this.caseOriginName = caseOriginName;
	}

	

	/**
	 * @param problemReported the problemReported to set
	 */
	public void setProblemReported(java.util.Set<com.pratikshat.exicom.entity.PrcProblemReported> problemReported) {
		this.problemReported = problemReported;
	}

	/**
	 * @param contactPersonId the contactPersonId to set
	 */
	public void setContactPersonId(long contactPersonId) {
		this.contactPersonId = contactPersonId;
	}

	/**
	 * @param contactPersonName the contactPersonName to set
	 */
	public void setContactPersonName(String contactPersonFirstName) {
		this.contactPersonFirstName = contactPersonFirstName;
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
	 * @param createdByEmailId the createdByEmailId to set
	 */
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}

	/**
	 * @param createdByFirstName the createdByFirstName to set
	 */
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}

	/**
	 * @param createdByLastName the createdByLastName to set
	 */
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @param modifiedByEmailId the modifiedByEmailId to set
	 */
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}

	/**
	 * @param modifiedByFirstName the modifiedByFirstName to set
	 */
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}

	/**
	 * @param modifiedByLastName the modifiedByLastName to set
	 */
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the contactPersonLastName
	 */
	public String getContactPersonLastName() {
		return contactPersonLastName;
	}
	/**
	 * @return the contactPersonEmailId
	 */
	public String getContactPersonEmailId() {
		return contactPersonEmailId;
	}
	/**
	 * @param contactPersonFirstName the contactPersonFirstName to set
	 */
	public void setContactPersonFirstName(String contactPersonFirstName) {
		this.contactPersonFirstName = contactPersonFirstName;
	}
	/**
	 * @param contactPersonLastName the contactPersonLastName to set
	 */
	public void setContactPersonLastName(String contactPersonLastName) {
		this.contactPersonLastName = contactPersonLastName;
	}
	/**
	 * @param contactPersonEmailId the contactPersonEmailId to set
	 */
	public void setContactPersonEmailId(String contactPersonEmailId) {
		this.contactPersonEmailId = contactPersonEmailId;
	}
	/**
	 * @return the accountSapNumber
	 */
	public String getAccountSapNumber() {
		return accountSapNumber;
	}
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @return the siteManualId
	 */
	public String getSiteManualId() {
		return siteManualId;
	}
	/**
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}
	/**
	 * @return the siteId
	 */
	public long getSiteId() {
		return siteId;
	}
	/**
	 * @param accountSapNumber the accountSapNumber to set
	 */
	public void setAccountSapNumber(String accountSapNumber) {
		this.accountSapNumber = accountSapNumber;
	}
	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * @param siteManualId the siteManualId to set
	 */
	public void setSiteManualId(String siteManualId) {
		this.siteManualId = siteManualId;
	}
	/**
	 * @param siteName the siteName to set
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	public java.util.Date getClosedDate() {
		return closedDate;
	}
	public void setClosedDate(java.util.Date closedDate) {
		this.closedDate = closedDate;
	}
	
	public String getContactPersonContactNo() {
		return contactPersonContactNo;
	}
	public void setContactPersonContactNo(String contactPersonContactNo) {
		this.contactPersonContactNo = contactPersonContactNo;
	}
	public Date getDateOfVisit() {
		return dateOfVisit;
	}
	public void setDateOfVisit(Date dateOfVisit) {
		this.dateOfVisit = dateOfVisit;
	}
	public Date getDateTimeOpened() {
		return dateTimeOpened;
	}
	public void setDateTimeOpened(Date dateTimeOpened) {
		this.dateTimeOpened = dateTimeOpened;
	}
	public Date getDateTimeClosed() {
		return dateTimeClosed;
	}
	public void setDateTimeClosed(Date dateTimeClosed) {
		this.dateTimeClosed = dateTimeClosed;
	}
	public String getRJIL_TT01_No() {
		return RJIL_TT01_No;
	}
	public void setRJIL_TT01_No(String rJIL_TT01_No) {
		RJIL_TT01_No = rJIL_TT01_No;
	}
	public String getSLATime() {
		return SLATime;
	}
	public void setSLATime(String sLATime) {
		SLATime = sLATime;
	}
	
	public String getCallCenterRepFirstName() {
		return callCenterRepFirstName;
	}
	public void setCallCenterRepFirstName(String callCenterRepFirstName) {
		this.callCenterRepFirstName = callCenterRepFirstName;
	}
	public String getCallCenterRepLastName() {
		return callCenterRepLastName;
	}
	public void setCallCenterRepLastName(String callCenterRepLastName) {
		this.callCenterRepLastName = callCenterRepLastName;
	}
	public String getCaseManualId() {
		return caseManualId;
	}
	public void setCaseManualId(String caseManualId) {
		this.caseManualId = caseManualId;
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
	public String getCasePriorityName() {
		return casePriorityName;
	}
	public void setCasePriorityName(String casePriorityName) {
		this.casePriorityName = casePriorityName;
	}
	public int getCasePriorityId() {
		return casePriorityId;
	}
	public void setCasePriorityId(int casePriorityId) {
		this.casePriorityId = casePriorityId;
	}
	public int getVerticalTypeId() {
		return verticalTypeId;
	}
	public void setVerticalTypeId(int verticalTypeId) {
		this.verticalTypeId = verticalTypeId;
	}
	public String getVerticalTypeName() {
		return verticalTypeName;
	}
	public void setVerticalTypeName(String verticalTypeName) {
		this.verticalTypeName = verticalTypeName;
	}
	public String getCaseReasonVerticalName() {
		return caseReasonVerticalName;
	}
	public void setCaseReasonVerticalName(String caseReasonVerticalName) {
		this.caseReasonVerticalName = caseReasonVerticalName;
	}
	public int getCaseReasonVerticalId() {
		return caseReasonVerticalId;
	}
	public void setCaseReasonVerticalId(int caseReasonVerticalId) {
		this.caseReasonVerticalId = caseReasonVerticalId;
	}
	public String getCaseStageVerticalName() {
		return caseStageVerticalName;
	}
	public void setCaseStageVerticalName(String caseStageVerticalName) {
		this.caseStageVerticalName = caseStageVerticalName;
	}
	public int getCaseStageVerticalId() {
		return caseStageVerticalId;
	}
	public void setCaseStageVerticalId(int caseStageVerticalId) {
		this.caseStageVerticalId = caseStageVerticalId;
	}
	/**
	 * @return the serviceResourceId
	 */
	public long getServiceResourceId() {
		return serviceResourceId;
	}
	/**
	 * @return the serviceResouceContactId
	 */
	public long getServiceResouceContactId() {
		return serviceResouceContactId;
	}
	/**
	 * @return the serviceResourceName
	 */
	public String getServiceResourceName() {
		return serviceResourceName;
	}
	/**
	 * @return the serviceCenterId
	 */
	public long getServiceCenterId() {
		return serviceCenterId;
	}
	/**
	 * @return the serviceCenterName
	 */
	public String getServiceCenterName() {
		return serviceCenterName;
	}
	/**
	 * @param serviceResourceId the serviceResourceId to set
	 */
	public void setServiceResourceId(long serviceResourceId) {
		this.serviceResourceId = serviceResourceId;
	}
	/**
	 * @param serviceResouceContactId the serviceResouceContactId to set
	 */
	public void setServiceResouceContactId(long serviceResouceContactId) {
		this.serviceResouceContactId = serviceResouceContactId;
	}
	/**
	 * @param serviceResourceName the serviceResourceName to set
	 */
	public void setServiceResourceName(String serviceResourceName) {
		this.serviceResourceName = serviceResourceName;
	}
	/**
	 * @param serviceCenterId the serviceCenterId to set
	 */
	public void setServiceCenterId(long serviceCenterId) {
		this.serviceCenterId = serviceCenterId;
	}
	/**
	 * @param serviceCenterName the serviceCenterName to set
	 */
	public void setServiceCenterName(String serviceCenterName) {
		this.serviceCenterName = serviceCenterName;
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
	
	public String getPendingResponsibility() {
		return pendingResponsibility;
	}
	public void setPendingResponsibility(String pendingResponsibility) {
		this.pendingResponsibility = pendingResponsibility;
	}
	public long getPendingResponsibilityId() {
		return pendingResponsibilityId;
	}
	public void setPendingResponsibilityId(long pendingResponsibilityId) {
		this.pendingResponsibilityId = pendingResponsibilityId;
	}
	public String getOtherProblemReported() {
		return otherProblemReported;
	}
	public void setOtherProblemReported(String otherProblemReported) {
		this.otherProblemReported = otherProblemReported;
	}
	
	
	
	public String getCaseOwnerFirstName() {
		return caseOwnerFirstName;
	}
	public void setCaseOwnerFirstName(String caseOwnerFirstName) {
		this.caseOwnerFirstName = caseOwnerFirstName;
	}
	public String getCaseOwnerMiddleName() {
		return caseOwnerMiddleName;
	}
	public void setCaseOwnerMiddleName(String caseOwnerMiddleName) {
		this.caseOwnerMiddleName = caseOwnerMiddleName;
	}
	public String getCaseOwnerLastName() {
		return caseOwnerLastName;
	}
	public void setCaseOwnerLastName(String caseOwnerLastName) {
		this.caseOwnerLastName = caseOwnerLastName;
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
	public String getAssetDescription() {
		return assetDescription;
	}
	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}
	public String getContactPersonMiddleName() {
		return contactPersonMiddleName;
	}
	public void setContactPesrsonMiddleName(String contactPersonMiddleName) {
		this.contactPersonMiddleName = contactPersonMiddleName;
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
	public String getCallCenterRepMiddleName() {
		return callCenterRepMiddleName;
	}
	public void setCallCenterRepMiddleName(String callCenterRepMiddleName) {
		this.callCenterRepMiddleName = callCenterRepMiddleName;
	}
	public String getPrimaryAccountSapNumber() {
		return primaryAccountSapNumber;
	}
	public void setPrimaryAccountSapNumber(String primaryAccountSapNumber) {
		this.primaryAccountSapNumber = primaryAccountSapNumber;
	}
	public String getPrimaryAccountName() {
		return primaryAccountName;
	}
	public void setPrimaryAccountName(String primaryAccountName) {
		this.primaryAccountName = primaryAccountName;
	}
	public String getLastAction() {
		return lastAction;
	}
	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}
	public void setContactPersonMiddleName(String contactPersonMiddleName) {
		this.contactPersonMiddleName = contactPersonMiddleName;
	}
	public String getLastActionComment() {
		return lastActionComment;
	}
	public void setLastActionComment(String lastActionComment) {
		this.lastActionComment = lastActionComment;
	}
	public long getPrcCaseId() {
		return prcCaseId;
	}
	public void setPrcCaseId(long prcCaseId) {
		this.prcCaseId = prcCaseId;
	}
	public String getPrcCaseManualId() {
		return prcCaseManualId;
	}
	public void setPrcCaseManualId(String prcCaseManualId) {
		this.prcCaseManualId = prcCaseManualId;
	}
	public boolean isiSmsSend() {
		return iSmsSend;
	}
	public void setiSmsSend(boolean iSmsSend) {
		this.iSmsSend = iSmsSend;
	}
	
	
	
	
}
