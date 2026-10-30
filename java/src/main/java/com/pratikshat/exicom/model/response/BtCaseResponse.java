package com.pratikshat.exicom.model.response;

import java.util.Date;
import java.util.Set;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtRMADetails;

public class BtCaseResponse {

	long caseId;
	String circleName;
	String cmp;
	String faultObservedByCustomer;
	
	String caseManualId;
	
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
	long assetsId;
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
	Set<BtCaseProblemResponse> problemReported;
	Set<PodFileUploadResponse> fileUploadResponses;
	String casesubReason;
	public Set<PodFileUploadResponse> getFileUploadResponses() {
		return fileUploadResponses;
	}

	public void setFileUploadResponses(Set<PodFileUploadResponse> fileUploadResponses) {
		this.fileUploadResponses = fileUploadResponses;
	}


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
	String PODNumber;
	Date PODDate;
	String ponumber;
	Date podate;
	String invoiceNumber;
	Date invoiceDate;
	String vendorInvoiceNumber;
	Date vendorInvoiceDate;
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
	String caseOwnerEmailId;
	boolean isAssignByAssignmentRule;
	boolean assignByAssignmentRule;
	String inputFromEng;
	String internalRemark;

	String podShippingMode;
	String podShippingToName;
	String podShippingTrackingNumber;
	String podShippingExpectedDate;
	String actlAssetsSerialNumber;

	String vendorName;
	String vendorSapCustomerNumber;

	String siteCustomer;

	String prcManualId;
	long prcId;

	int assetsTypeChoiceId;

	String assetstypeChoiceName;
	String poamount;
	String lineitem;
	int problemMasterId;
	String problemmasterType;
	
	boolean isRmaCreated;
	long rmaId;
	String rmaManualId;
	String rmaStatus;
	String jmsNo;
	String customer;
	boolean isRolCreated;
	String rolRemark;
	
	
	
	
	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}


	String openDate;
	String ticketNo;
	


	public String getJmsNo() {
		return jmsNo;
	}

	public void setJmsNo(String jmsNo) {
		this.jmsNo = jmsNo;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public long getRmaId() {
		return rmaId;
	}

	public void setRmaId(long rmaId) {
		this.rmaId = rmaId;
	}

	public String getRmaManualId() {
		return rmaManualId;
	}

	public void setRmaManualId(String rmaManualId) {
		this.rmaManualId = rmaManualId;
	}

	public String getRmaStatus() {
		return rmaStatus;
	}

	public void setRmaStatus(String rmaStatus) {
		this.rmaStatus = rmaStatus;
	}

	public boolean isRmaCreated() {
		return isRmaCreated;
	}

	public void setRmaCreated(boolean isRmaCreated) {
		this.isRmaCreated = isRmaCreated;
	}

	public String getAssetstypeChoiceName() {
		return assetstypeChoiceName;
	}

	public String getPoamount() {
		return poamount;
	}

	public void setPoamount(String poamount) {
		this.poamount = poamount;
	}

	public String getLineitem() {
		return lineitem;
	}

	public void setLineitem(String lineitem) {
		this.lineitem = lineitem;
	}

	public void setAssetstypeChoiceName(String assetstypeChoiceName) {
		this.assetstypeChoiceName = assetstypeChoiceName;
	}

	public int getAssetsTypeChoiceId() {
		return assetsTypeChoiceId;
	}

	public void setAssetsTypeChoiceId(int assetsTypeChoiceId) {
		this.assetsTypeChoiceId = assetsTypeChoiceId;
	}

	public String getInternalRemark() {
		return internalRemark;
	}

	public void setInternalRemark(String internalRemark) {
		this.internalRemark = internalRemark;
	}

	public BtCaseResponse() {
		// TODO Auto-generated constructor stub
	}
	
	public BtCaseResponse(BtCase caseDetails) {
		setData(caseDetails, null,null,null);
	}

	public BtCaseResponse(BtCase caseDetails,BtRMADetails rma) {
		setData(caseDetails, rma,null,null);
	}

	public BtCaseResponse(BtCase caseDetails,BtRMADetails rma, Set<BtCaseProblemResponse> problemReported,Set<PodFileUploadResponse> fileUploadResponses) {
		setData(caseDetails,rma, problemReported,fileUploadResponses);
	}

	public BtCaseResponse(long caseId, String caseManualId, int caseRecordTypeId, String caseRecordTypeName,
			long caseTypeId, int verticalTypeId, String verticalTypeName, String caseTypeName, long caseReasonId,
			String caseReasonName, String caseReasonVerticalName, int caseReasonVerticalId, String assetsSerialNumber,
			String assetsPartCode, String assetDescription, String assetsName, String caseStatusName, int caseStatusId,
			String caseStageName, int caseStageId, String caseOriginName, int caseOriginId, String casePriorityName,
			int casePriorityId, long contactPersonId, String contactPersonFirstName, String contactPersonMiddleName,
			String contactPersonLastName, String contactPersonEmailId, String noOfSmr, String remarks,
			String createdByEmailId, String createdByFirstName, String createdByMiddleName, String createdByLastName,
			Date createdDate, Date closedDate, String modifiedByEmailId, String modifiedByFirstName,
			String modifiedByMiddleName, String modifiedByLastName, Date modifiedDate, String otherProblemReported,
			String contactPersonContactNo, Date dateOfVisit, Date dateTimeOpened, Date dateTimeClosed,
			String rJIL_TT01_No, String pendingResponsibility, long pendingResponsibilityId, String sLATime,
			String pODNumber, Date pODDate, String pONumber, Date pODate, String invoiceNumber, Date invoiceDate,
			String vendorInvoiceNumber, Date vendorInvoiceDate, String callCenterRepFirstName,
			String callCenterRepMiddleName, String callCenterRepLastName, String accountSapNumber, String accountName,
			String primaryAccountSapNumber, String primaryAccountName, String siteManualId, String siteName,
			long siteId, String caseStageVerticalName, int caseStageVerticalId, long serviceResourceId,
			long serviceResouceContactId, String serviceResourceName, long serviceCenterId, String serviceCenterName,
			String missingAssets, String caseOwnerFirstName, String caseOwnerMiddleName, String caseOwnerLastName,
			boolean isAssignByAssignmentRule, String inputFromEng, String internalRemark, String caseOwnerEmailId,
			String poamount,String lineitem,long assetsId,boolean isRmaCreated,long rmaId,boolean isRolCreated,String rolRemark,
			String openDate,String ticketNo,String casesubReason) {

		this.caseId = caseId;
		this.assetsId=assetsId;
		this.caseManualId = caseManualId;
		this.caseRecordTypeId = caseRecordTypeId;
		this.caseRecordTypeName = caseRecordTypeName;
		this.caseTypeId = caseTypeId;
		this.verticalTypeId = verticalTypeId;
		this.verticalTypeName = verticalTypeName;
		this.caseTypeName = caseTypeName;
		this.caseReasonId = caseReasonId;
		this.caseReasonName = caseReasonName;
		this.caseReasonVerticalName = caseReasonVerticalName;
		this.caseReasonVerticalId = caseReasonVerticalId;
		this.assetsSerialNumber = assetsSerialNumber;
		this.assetsPartCode = assetsPartCode;
		this.assetDescription = assetDescription;
		this.assetsName = assetsName;
		this.caseStatusName = caseStatusName;
		this.caseStatusId = caseStatusId;
		this.caseStageName = caseStageName;
		this.caseStageId = caseStageId;
		this.caseOriginName = caseOriginName;
		this.caseOriginId = caseOriginId;
		this.casePriorityName = casePriorityName;
		this.casePriorityId = casePriorityId;
		this.contactPersonId = contactPersonId;
		this.contactPersonFirstName = contactPersonFirstName;
		this.contactPersonMiddleName = contactPersonMiddleName;
		this.contactPersonLastName = contactPersonLastName;
		this.contactPersonEmailId = contactPersonEmailId;
		this.noOfSmr = noOfSmr;
		this.remarks = remarks;
		this.createdByEmailId = createdByEmailId;
		this.createdByFirstName = createdByFirstName;
		this.createdByMiddleName = createdByMiddleName;
		this.createdByLastName = createdByLastName;
		this.createdDate = createdDate;
		this.closedDate = closedDate;
		this.modifiedByEmailId = modifiedByEmailId;
		this.modifiedByFirstName = modifiedByFirstName;
		this.modifiedByMiddleName = modifiedByMiddleName;
		this.modifiedByLastName = modifiedByLastName;
		this.modifiedDate = modifiedDate;
		this.otherProblemReported = otherProblemReported;
		this.contactPersonContactNo = contactPersonContactNo;
		this.dateOfVisit = dateOfVisit;
		this.dateTimeOpened = dateTimeOpened;
		this.dateTimeClosed = dateTimeClosed;
		this.RJIL_TT01_No = rJIL_TT01_No;
		this.pendingResponsibility = pendingResponsibility;
		this.pendingResponsibilityId = pendingResponsibilityId;
		this.SLATime = sLATime;
		this.PODNumber = pODNumber;
		this.PODDate = pODDate;
		this.ponumber = pONumber;
		this.podate = pODate;
		this.invoiceNumber = invoiceNumber;
		this.invoiceDate = invoiceDate;
		this.vendorInvoiceNumber = vendorInvoiceNumber;
		this.vendorInvoiceDate = vendorInvoiceDate;
		this.callCenterRepFirstName = callCenterRepFirstName;
		this.callCenterRepMiddleName = callCenterRepMiddleName;
		this.callCenterRepLastName = callCenterRepLastName;
		this.accountSapNumber = accountSapNumber;
		this.accountName = accountName;
		this.primaryAccountSapNumber = primaryAccountSapNumber;
		this.primaryAccountName = primaryAccountName;
		this.siteManualId = siteManualId;
		this.siteName = siteName;
		this.siteId = siteId;
		this.caseStageVerticalName = caseStageVerticalName;
		this.caseStageVerticalId = caseStageVerticalId;
		this.serviceResourceId = serviceResourceId;
		this.serviceResouceContactId = serviceResouceContactId;
		this.serviceResourceName = serviceResourceName;
		this.serviceCenterId = serviceCenterId;
		this.serviceCenterName = serviceCenterName;
		this.missingAssets = missingAssets;
		this.caseOwnerFirstName = caseOwnerFirstName;
		this.caseOwnerMiddleName = caseOwnerMiddleName;
		this.caseOwnerLastName = caseOwnerLastName;
		this.isAssignByAssignmentRule = isAssignByAssignmentRule;
		this.assignByAssignmentRule=isAssignByAssignmentRule;
		this.inputFromEng = inputFromEng;
		this.internalRemark = internalRemark;
		this.caseOwnerEmailId = caseOwnerEmailId;
		this.poamount=poamount;
		this.lineitem=lineitem;
		this.isRmaCreated=isRmaCreated;
		this.rmaId=rmaId;
		this.isRolCreated=isRolCreated;
		this.rolRemark=rolRemark;
		this.openDate=openDate;
		this.ticketNo=ticketNo;
		this.casesubReason=casesubReason;
	}

	public void setContactPersonMiddleName(String contactPersonMiddleName) {
		this.contactPersonMiddleName = contactPersonMiddleName;
	}

	private void setData(BtCase caseDetails,BtRMADetails rma, java.util.Set<BtCaseProblemResponse> problemReported,Set<PodFileUploadResponse> fileUploadResponses) {
		this.caseId = caseDetails.getId();
		this.caseManualId = caseDetails.getManualId();
		this.missingAssets = caseDetails.getMissingAssets();
		this.poamount=caseDetails.getPoamount();
		this.lineitem=caseDetails.getLineitem();
		this.jmsNo=caseDetails.getJmsNo();
		this.customer=caseDetails.getCustomer();
		this.openDate=caseDetails.getOpenDate();
		this.ticketNo=caseDetails.getTicketNo();
		this.casesubReason=caseDetails.getCasesubReason();
		if (caseDetails.getBusinessDomain() != null) {
			this.caseRecordTypeName = caseDetails.getBusinessDomain().getBusinessDomainName();
			this.caseRecordTypeId = caseDetails.getBusinessDomain().getBusinessDomainId();
		}
		if(caseDetails.getBtProblemMaster()!=null) {
			this.problemMasterId=caseDetails.getBtProblemMaster().getId();
			this.problemmasterType=caseDetails.getBtProblemMaster().getType();
		}
		if (caseDetails.getAssetsTypeChoice() != null) {
			this.assetstypeChoiceName = caseDetails.getAssetsTypeChoice().getName();
			this.assetsTypeChoiceId = caseDetails.getAssetsTypeChoice().getId();
		}

		if (caseDetails.getCaseType() != null) {
			this.caseTypeId = caseDetails.getCaseType().getCaseTypeId();
			if(caseDetails.getCaseType().getVertical()!=null) {
				this.verticalTypeName = caseDetails.getCaseType().getVertical().getBusinessDomainName();
				this.verticalTypeId = caseDetails.getCaseType().getVertical().getBusinessDomainId();
			}
			
			
			this.caseTypeName = caseDetails.getCaseType().getCaseTypesName();

		}
		
		if (caseDetails.getCaseReason() != null) {
			this.caseReasonId = caseDetails.getCaseReason().getReasonId();
			this.caseReasonName = caseDetails.getCaseReason().getCaseReasonDetailsName();
			if(caseDetails.getCaseReason().getVertical()!=null) {
				this.caseReasonVerticalName = caseDetails.getCaseReason().getVertical().getBusinessDomainName();
				this.caseReasonVerticalId = caseDetails.getCaseReason().getVertical().getBusinessDomainId();
			}
			
		}
		if (caseDetails.getBtAssets() != null) {
			this.assetsSerialNumber = caseDetails.getBtAssets().getManualId();
			this.assetsId=caseDetails.getBtAssets().getId();
			if (caseDetails.getBtAssets().getProducts() != null) {
				this.assetsPartCode = caseDetails.getBtAssets().getProducts().getManualId();
				this.assetsName = caseDetails.getBtAssets().getProducts().getProductDescription();
				this.assetDescription = caseDetails.getBtAssets().getProducts().getProductDescription();
			}

		}

		if (caseDetails.getSite() != null) {
			this.siteId = caseDetails.getSite().getId();
			this.siteManualId = caseDetails.getSite().getManualId();
			this.siteName = caseDetails.getSite().getSiteName();
			this.cmp=caseDetails.getSite().getCmp();

			if (caseDetails.getSite().getAccount() != null) {
				this.accountName = caseDetails.getSite().getAccount().getName();
				this.accountSapNumber = caseDetails.getSite().getAccount().getSapCustomerNumber();
			}

			if (caseDetails.getSite().getPrimaryAccount() != null) {
				this.primaryAccountName = caseDetails.getSite().getPrimaryAccount().getName();
				this.primaryAccountSapNumber = caseDetails.getSite().getPrimaryAccount().getSapCustomerNumber();
			}
			if(caseDetails.getSite().getCircle()!=null) {
				this.circleName=caseDetails.getSite().getCircle().getCircleName();
			}

			this.siteCustomer = caseDetails.getSite().getCustomer();
		}

		if (caseDetails.getStatus() != null) {
			this.caseStatusName = caseDetails.getStatus().getCaseStatusName();
			this.caseStatusId = caseDetails.getStatus().getId();
		}

		if (caseDetails.getStage() != null) {
			this.caseStageName = caseDetails.getStage().getCaseStageName();
			this.caseStageId = caseDetails.getStage().getId();
			if (caseDetails.getStage().getDivision() != null) {
				this.caseStageVerticalName = caseDetails.getStage().getDivision().getBusinessDomainName();
				this.caseStageVerticalId = caseDetails.getStage().getDivision().getBusinessDomainId();
			}
		}

		if (caseDetails.getOrigin() != null) {
			this.caseOriginName = caseDetails.getOrigin().getCaseOrignName();
			this.caseOriginId = caseDetails.getOrigin().getCaseOriginId();
		}

		if (caseDetails.getPriority() != null) {
			this.casePriorityName = caseDetails.getPriority().getCasePriorityName();
			this.casePriorityId = caseDetails.getPriority().getCasePriorityId();
		}

		if (problemReported != null) {
			this.problemReported = problemReported;
		}
		if (caseDetails.getContactPerson() != null) {
			this.contactPersonId = caseDetails.getContactPerson().getContactId();
			this.contactPersonFirstName = caseDetails.getContactPerson().getFirstName();
			this.contactPersonMiddleName = caseDetails.getContactPerson().getMiddleName();
			this.contactPersonLastName = caseDetails.getContactPerson().getLastName();
			this.contactPersonEmailId = caseDetails.getContactPerson().getEmailId();
			this.contactPersonContactNo = caseDetails.getContactPerson().getMobile();
		}
		this.noOfSmr = caseDetails.getNoOfsmr();
		this.remarks = caseDetails.getRemarks();
		if (caseDetails.getCreatedBy() != null) {
			this.createdByEmailId = caseDetails.getCreatedBy().getEmailId();
			this.createdByFirstName = caseDetails.getCreatedBy().getFirstName();
			this.createdByMiddleName = caseDetails.getCreatedBy().getMiddleName();
			this.createdByLastName = caseDetails.getCreatedBy().getLastName();
			this.callCenterRepFirstName = caseDetails.getCreatedBy().getFirstName();
			this.callCenterRepLastName = caseDetails.getCreatedBy().getLastName();
			this.callCenterRepMiddleName = caseDetails.getCreatedBy().getMiddleName();
		}
		this.createdDate = caseDetails.getCreatedDate();
		this.closedDate = caseDetails.getCloseDate();
		if (caseDetails.getLastModifiedBy() != null) {
			this.modifiedByEmailId = caseDetails.getLastModifiedBy().getEmailId();
			this.modifiedByFirstName = caseDetails.getLastModifiedBy().getFirstName();
			this.modifiedByMiddleName = caseDetails.getLastModifiedBy().getMiddleName();
			this.modifiedByLastName = caseDetails.getLastModifiedBy().getLastName();
		}
		this.modifiedDate = caseDetails.getLastModifiedDate();
		if (caseDetails.getServiceCenter() != null) {
			this.serviceCenterId = caseDetails.getServiceCenter().getLegalEntityId();
			this.serviceCenterName = caseDetails.getServiceCenter().getLegalEntityName();
		}
		if (caseDetails.getEngineer() != null) {
			this.serviceResourceId = caseDetails.getEngineer().getServiceResourceId();
			this.serviceResourceName = caseDetails.getEngineer().getName();
			if (caseDetails.getEngineer().getContact() != null) {
				this.serviceResouceContactId = caseDetails.getEngineer().getContact().getContactId();
				if (caseDetails.getEngineer().getContact().getAccount() != null) {
					if (caseDetails.getEngineer().getContact().getAccount().getAccountType() != null) {
						if (caseDetails.getEngineer().getContact().getAccount().getAccountType()
								.getAccountTypeId() == 1) { // Vendor
							this.vendorName = caseDetails.getEngineer().getContact().getAccount().getName();
							this.vendorSapCustomerNumber = caseDetails.getEngineer().getContact().getAccount()
									.getSapCustomerNumber();
						}
					}
				}
			}

		}

		if (caseDetails.getPendingResponsibility() != null) {
			this.pendingResponsibilityId = caseDetails.getPendingResponsibility().getPendingResponsibilityId();
			this.pendingResponsibility = caseDetails.getPendingResponsibility().getPendingResponsibilityName();
		}

		if (caseDetails.getOwnerDetails() != null) {
			this.caseOwnerFirstName = caseDetails.getOwnerDetails().getFirstName();
			this.caseOwnerMiddleName = caseDetails.getOwnerDetails().getMiddleName();
			this.caseOwnerLastName = caseDetails.getOwnerDetails().getLastName();
			this.caseOwnerEmailId = caseDetails.getOwnerDetails().getEmailId();
		}

		//ROL
		this.isRolCreated=caseDetails.isRol();
		this.rolRemark=caseDetails.getRolRemark();
		
		this.isRmaCreated=caseDetails.isRma();
		
		if(rma!=null) {
			this.rmaId=rma.getId();
			this.rmaManualId=rma.getManualId();
			this.rmaStatus=rma.getRmaStatus();
		}
		
		this.RJIL_TT01_No = caseDetails.getRJILTT01No();
		this.otherProblemReported = caseDetails.getOtherProblemReported();
		this.isAssignByAssignmentRule = caseDetails.isAssignByAssignmentRule();
		this.assignByAssignmentRule=this.isAssignByAssignmentRule;
		this.inputFromEng = caseDetails.getInputFromEng();
		this.internalRemark = caseDetails.getInternalRemark();

		this.PODNumber = caseDetails.getPODNumber();
		this.PODDate = caseDetails.getPODDate();
		this.podShippingMode = caseDetails.getPodShippingMode();
		this.podShippingToName = caseDetails.getPodShippingToName();
		this.podShippingTrackingNumber = caseDetails.getPodShippingTrackingNumber();
		this.podShippingExpectedDate = caseDetails.getPodShippingExpectedDate();

		this.podate = caseDetails.getPODate();
		this.ponumber = caseDetails.getPONumber();

		this.invoiceNumber = caseDetails.getInvoiceNumber();
		this.invoiceDate = caseDetails.getInvoiceDate();

		this.vendorInvoiceDate = caseDetails.getVendorInvoiceDate();
		this.vendorInvoiceNumber = caseDetails.getVendorInvoiceNumber();
		this.faultObservedByCustomer=caseDetails.getOtherProblemReported();
		

		if (caseDetails.getStatus() != null) {
			if (caseDetails.getStatus().getId() == 2) { // Closed

				this.SLATime = MiscellaneousUtils.slaCalculation(caseDetails.getCreatedDate(),
						caseDetails.getCloseDate());
			} else {
				this.SLATime = MiscellaneousUtils.slaCalculation(caseDetails.getCreatedDate(), new Date());
			}
		} else {
			this.SLATime = MiscellaneousUtils.slaCalculation(caseDetails.getCreatedDate(), new Date());
		}

		if (caseDetails.getPrc() != null) {
			this.prcId = caseDetails.getPrc().getId();
			this.prcManualId = caseDetails.getPrc().getManualId();
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
	public java.util.Set<BtCaseProblemResponse> getProblemReported() {
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

	public long getAssetsId() {
		return assetsId;
	}

	public void setAssetsId(long assetsId) {
		this.assetsId = assetsId;
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
	public void setProblemReported(Set<BtCaseProblemResponse> problemReported) {
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
		return ponumber;
	}

	public void setPONumber(String pONumber) {
		this.ponumber = pONumber;
	}

	public Date getPODate() {
		return podate;
	}

	public void setPODate(Date pODate) {
		this.podate = pODate;
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

	public String getCaseOwnerEmailId() {
		return caseOwnerEmailId;
	}

	public void setCaseOwnerEmailId(String caseOwnerEmailId) {
		this.caseOwnerEmailId = caseOwnerEmailId;
	}

	public String getActlAssetsSerialNumber() {
		return actlAssetsSerialNumber;
	}

	public void setActlAssetsSerialNumber(String actlAssetsSerialNumber) {
		this.actlAssetsSerialNumber = actlAssetsSerialNumber;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorSapCustomerNumber() {
		return vendorSapCustomerNumber;
	}

	public void setVendorSapCustomerNumber(String vendorSapCustomerNumber) {
		this.vendorSapCustomerNumber = vendorSapCustomerNumber;
	}

	public String getSiteCustomer() {
		return siteCustomer;
	}

	public void setSiteCustomer(String siteCustomer) {
		this.siteCustomer = siteCustomer;
	}

	public String getPrcManualId() {
		return prcManualId;
	}

	public void setPrcManualId(String prcManualId) {
		this.prcManualId = prcManualId;
	}

	public long getPrcId() {
		return prcId;
	}

	public void setPrcId(long prcId) {
		this.prcId = prcId;
	}

	

	public int getProblemMasterId() {
		return problemMasterId;
	}

	public void setProblemMasterId(int problemMasterId) {
		this.problemMasterId = problemMasterId;
	}

	public String getProblemmasterType() {
		return problemmasterType;
	}

	public void setProblemmasterType(String problemmasterType) {
		this.problemmasterType = problemmasterType;
	}

	public String getFaultObservedByCustomer() {
		return faultObservedByCustomer;
	}

	public void setFaultObservedByCustomer(String faultObservedByCustomer) {
		this.faultObservedByCustomer = faultObservedByCustomer;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getCmp() {
		return cmp;
	}

	public void setCmp(String cmp) {
		this.cmp = cmp;
	}
	

	public boolean getisAssignByAssignmentRule() {
		return this.isAssignByAssignmentRule;
	}

	public boolean isRolCreated() {
		return isRolCreated;
	}

	public void setRolCreated(boolean isRolCreated) {
		this.isRolCreated = isRolCreated;
	}

	public String getRolRemark() {
		return rolRemark;
	}

	public void setRolRemark(String rolRemark) {
		this.rolRemark = rolRemark;
	}

	public String getCasesubReason() {
		return casesubReason;
	}

	public void setCasesubReason(String casesubReason) {
		this.casesubReason = casesubReason;
	}
	
	
	
}
