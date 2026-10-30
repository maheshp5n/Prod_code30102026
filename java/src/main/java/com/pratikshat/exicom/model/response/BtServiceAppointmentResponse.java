package com.pratikshat.exicom.model.response;

import java.util.Date;
import java.util.Set;

import com.pratikshat.exicom.entity.BtCommissioningFSR;
import com.pratikshat.exicom.entity.BtFsr;
import com.pratikshat.exicom.entity.BtSaEngRating;
import com.pratikshat.exicom.entity.BtSchUseOnlyDetails;
import com.pratikshat.exicom.entity.BtServiceAppointment;

public class BtServiceAppointmentResponse {

	long appointmentId;
	String appointmentManualId;
	String faultObserve;
	String correctiveAction;
	String punchPointExicom;
	String punchPointCustomer;
	long circleManagerContactId;
	String serviceCenterName;
	String circleName;
	String caseManualId;
	String modifiedByEmailId;
	long caseId;
	String acknowledgedByName;
	String circleManagerContactEmailId;
	String ownerLastName;
	String serviceResourceName;
	String contactPhoneNumber;
	double fixedPayout;
	String businessPlace;
	long employeeCode;
	String PendingResource;
	Date visitDate;
	String customerRepresentativeContact;
	String grade;
	String createdByLastName;
	String assetsPartCode;
	long serviceFsrId;
	long commFsrId;
	String assetsSapSerialNumber;
	String costCenter;
	long assetId;
	String modifiedByFirstName;
	String wccId;
	String assetDescription;
	String serviceAppointmentStatusName;
	String caseReasonName;
	String caseStatus;
	String sparePartReplaced;
	String ownerEmailId;
	String ownerFirstName;
	boolean isApprovedByFinanceCheck;
	long resolutionMethodId;
	String contactMiddleName;
	long serviceAppointmentStatusId;
	String caseTypeName;
	String accountName;
	String siteName;
	boolean wcc;
	String acknowledgedByPhone;
	String sparePartName;
	String ssaName;
	long siteId;
	long SchOnlyId;
	String tFlag;
	long serviceCenterId;
	long caseType;
	long sparePartQuantity;
	String createdByFirstName;
	String createdByMiddleName;
	String dueDate;
	String serviceCenterManualId;
	String modifiedByMiddleName;
	String wccManualId;
	Date createDate;
	String ownerMiddleName;
	String circleManagerContactFirstName;
	long pendingResponsibilityId;
	String contactLastName;
	String accountSapNumber;
	boolean isFsrCreated;
	long ssaId;
	String siteManualId;
	String modifiedByLastName;
	String remark;
	String pendingResponsibilityName;
	String dueTime;
	String contactFirstName;
	String customerRepresentativeName;
	String claimStatusName;
	Date modifiedDate;
	Date approvedByFinance;
	String circleManagerContactMiddleName;
	String circleManagerContactLastName;
	long claimStatusId;
	long serviceResourceId;
	long solutionImpartedId;
	long caseReason;
	String createByEmailId;
	String owner_details_email_id;
	long contactId;
	double latitude;
	double longitude;
	String address;
	long productId;
	long customerBucketReasonId;
	String customerBucketReasonName;
	long servicePayoutId;
	String faultyObeserved;
	boolean rmaCreated;
	String sparePartReplacedFrom;
	boolean schFill;
	String eSignature;
	int customerRating;
	String customerRemarks;
	boolean billingRequired;
	boolean uploadfsrMandatory;
	boolean fsrMandatory;

	Set<BtSolutionImpartedProblemResponse> btSolutionImpartedProblemResponses;
//	Set<BtSchOnlyResponse> btSchOnlyResponses;
	
	

	public BtServiceAppointmentResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BtServiceAppointmentResponse(BtServiceAppointment appointment) {
		setData(appointment, null, null, null);
	}


	public BtServiceAppointmentResponse(BtServiceAppointment appointment,  BtFsr serFsr) {
		setData(appointment,  serFsr, null, null);
	}

	public BtServiceAppointmentResponse(BtServiceAppointment appointment, BtFsr serFsr,
			BtCommissioningFSR comFsr) {
		setData(appointment, serFsr, comFsr, null);
	}
	
//	public BtServiceAppointmentResponse(BtServiceAppointment appointment, BtFsr serFsr,
//			BtCommissioningFSR comFsr, BtSaEngRating rating) {
//		setData(appointment, serFsr, comFsr, rating);
//	}

//	public BtServiceAppointmentResponse(BtServiceAppointment appointment, BtFsr serFsr,
//			BtCommissioningFSR comFsr, BtSaEngRating rating, Set<BtSolutionImpartedProblemResponse> impartedProblemResponses,Set<BtSchOnlyResponse> btSchOnlyResponses) {
////		System.out.println("In 1");
//		setData(appointment, serFsr, comFsr, rating, impartedProblemResponses,btSchOnlyResponses);
//	}
	public BtServiceAppointmentResponse(BtServiceAppointment appointment, BtFsr serFsr,
			BtCommissioningFSR comFsr, BtSaEngRating rating) {
//		System.out.println("In 1");
		setData(appointment, serFsr, comFsr, rating);
	}
//	public void setData(BtServiceAppointment appointment, BtFsr serFsr,
//			BtCommissioningFSR comFsr,BtSaEngRating rating, Set<BtSolutionImpartedProblemResponse> impartedProblemResponses,Set<BtSchOnlyResponse> btSchOnlyResponses) {
	public void setData(BtServiceAppointment appointment, BtFsr serFsr,
			BtCommissioningFSR comFsr,BtSaEngRating rating) {
	this.appointmentId = appointment.getId();
		this.appointmentManualId = appointment.getmanualId();
		this.schFill=appointment.isSchFill();
		this.billingRequired=appointment.isBillingRequired();
		if (serFsr != null) {
			this.serviceFsrId = serFsr.getId();
		}
		if (comFsr != null) {
			this.commFsrId = comFsr.getId();
		}
		if (appointment.getPendingReasonCustomerBucket() != null) {
			this.customerBucketReasonId = appointment.getPendingReasonCustomerBucket().getId();
			this.customerBucketReasonName = appointment.getPendingReasonCustomerBucket().getTypeValue();
		}
		if (appointment.getBtServicePayout() != null) {
			this.servicePayoutId = appointment.getBtServicePayout().getId();
		}
		if (appointment.getCaseDetails() != null) {
			this.caseId = appointment.getCaseDetails().getId();
			this.caseManualId = appointment.getCaseDetails().getManualId();
			this.caseReason = appointment.getCaseDetails().getCaseReason().getReasonId();
			this.caseReasonName = appointment.getCaseDetails().getCaseReason().getCaseReasonDetailsName();
			this.caseStatus = appointment.getCaseDetails().getStatus().getCaseStatusName();
			this.caseType = appointment.getCaseDetails().getCaseType().getCaseTypeId();
			this.caseTypeName = appointment.getCaseDetails().getCaseType().getCaseTypesName();
			this.uploadfsrMandatory=appointment.getCaseDetails().getCaseReason().isUploadfsrMandatory();
			this.fsrMandatory=appointment.getCaseDetails().getCaseReason().isFsrMandatory();
		}
		this.rmaCreated = appointment.isRma();
		this.faultObserve = appointment.getFaultObserved();
//		this.correctiveAction = appointment.getCorrectiveActions();
		this.faultyObeserved = appointment.getFaultObserved();

		this.punchPointExicom = appointment.getPunchPointExiom();
		this.punchPointCustomer = appointment.getPunchPointCustomer();
		this.visitDate = appointment.getVisitDate();
		this.customerRepresentativeName = appointment.getCustomerRepresentativeName();
		this.customerRepresentativeContact = appointment.getCustomerRepresentativeContact();
		this.remark = appointment.getRemark();
		if (appointment.getServiceResource() != null) {
			this.serviceResourceId = appointment.getServiceResource().getServiceResourceId();
			this.serviceResourceName = appointment.getServiceResource().getName();
		}
		if (appointment.getStatus() != null) {
			this.serviceAppointmentStatusId = appointment.getStatus().getStatusId();
			this.serviceAppointmentStatusName = appointment.getStatus().getStatusName();
		}
		if (appointment.getPendingResponsibility() != null) {
			this.pendingResponsibilityId = appointment.getPendingResponsibility().getPendingResponsibilityId();
			this.pendingResponsibilityName = appointment.getPendingResponsibility().getPendingResponsibilityName();
		}
		this.PendingResource = appointment.getPendingResource();
		this.dueDate = appointment.getDueDate();
		this.dueTime = appointment.getDueTime();
		this.tFlag = appointment.gettFlag();
		this.createDate = appointment.getCreatedDate();
		if (appointment.getCircleManager() != null) {
			this.circleManagerContactId = appointment.getCircleManager().getContactId();
			this.circleManagerContactEmailId = appointment.getCircleManager().getEmailId();
			this.circleManagerContactFirstName = appointment.getCircleManager().getFirstName();
			if (appointment.getCircleManager().getMiddleName() != null)
				this.circleManagerContactMiddleName = appointment.getCircleManager().getMiddleName();
			this.circleManagerContactLastName = appointment.getCircleManager().getLastName();
		}
		if (appointment.getCircle().getCircleName() != null)
			this.circleName = appointment.getCircle().getCircleName();
		if (appointment.getClaimStatus() != null) {
			this.claimStatusId = appointment.getClaimStatus().getClaimStatusId();
			this.claimStatusName = appointment.getClaimStatus().getClaimStatusName();
		}
		if (appointment.getCaseDetails().getBtAssets() != null) {
			this.assetId = appointment.getCaseDetails().getBtAssets().getId();
			this.assetsSapSerialNumber = appointment.getCaseDetails().getBtAssets().getManualId();
			if (appointment.getCaseDetails().getBtAssets().getAccount() != null) {
				this.accountName = appointment.getCaseDetails().getBtAssets().getAccount().getName();
				this.accountSapNumber = appointment.getCaseDetails().getBtAssets().getAccount().getSapCustomerNumber();
			}
		}
		if (appointment.getProduct() != null) {
			this.productId = appointment.getProduct().getId();
			this.assetsPartCode = appointment.getProduct().getManualId();
			this.assetDescription = appointment.getProduct().getProductDescription();
		}
		this.isApprovedByFinanceCheck = appointment.isApprovedByFinance();
		if (appointment.getApprovedByFinance() != null)
			this.approvedByFinance = appointment.getApprovedByFinance();
		if (appointment.getCostCenter() != null)
			this.costCenter = appointment.getCostCenter();
		this.wcc = appointment.isWcc();
		if (appointment.getWccId() != null)
			this.wccId = appointment.getWccId();

		if (appointment.getWccManualId() != null)
			this.wccManualId = appointment.getWccManualId();

		if (appointment.getAcknowledgedByName() != null)
			this.acknowledgedByName = appointment.getAcknowledgedByName();

		if (appointment.getAcknowledgedByPhone() != null)
			this.acknowledgedByPhone = appointment.getAcknowledgedByPhone();
		this.fixedPayout = appointment.getFixedPayout();

		if (appointment.getAddress() != null)
			this.address = appointment.getAddress();

		if (appointment.getLatitude() != null)
			this.latitude = appointment.getLatitude();

		if (appointment.getLongitude() != null)
			this.longitude = appointment.getLongitude();
//		if (schOnly != null) {
//			this.SchOnlyId = schOnly.getId();
//			this.sparePartReplaced = schOnly.getSparePartReplaced();
//			this.sparePartQuantity = schOnly.getSparePartQuantity();
//			this.sparePartName = schOnly.getSparePartName();
//			this.resolutionMethodId = schOnly.getResolutionMethod().getId();
//			this.sparePartReplacedFrom = schOnly.getSparePartReplacedFrom();
//			
//		}
//		if (impartedProblemResponses != null) {
//			this.btSolutionImpartedProblemResponses = impartedProblemResponses;
//		}
//		
//		if(btSchOnlyResponses !=null) {
//			this.btSchOnlyResponses=btSchOnlyResponses;
//		}
		if (appointment.getOwnerDetails() != null) {
			this.ownerEmailId = appointment.getOwner().getEmailId();
			this.owner_details_email_id = appointment.getOwner().getEmailId();
			this.ownerFirstName = appointment.getOwner().getFirstName();
			this.ownerMiddleName = appointment.getOwner().getMiddleName();
			this.ownerLastName = appointment.getOwner().getLastName();
		}
		if (appointment.getCreatedBy() != null) {
			this.createByEmailId = appointment.getCreatedBy().getEmailId();
			this.createdByFirstName = appointment.getCreatedBy().getFirstName();
			this.createdByMiddleName = appointment.getCreatedBy().getMiddleName();
			this.createdByLastName = appointment.getCreatedBy().getLastName();
		}
		if (appointment.getLastModifiedBy() != null) {
			this.modifiedByEmailId = appointment.getLastModifiedBy().getEmailId();
			this.modifiedByFirstName = appointment.getLastModifiedBy().getFirstName();
			this.modifiedByMiddleName = appointment.getLastModifiedBy().getMiddleName();
			this.modifiedByLastName = appointment.getLastModifiedBy().getLastName();
		}
		this.createDate = appointment.getCreateDate();
		this.modifiedDate = appointment.getLastModifiedDate();
		if (appointment.getCaseDetails().getServiceCenter() != null) {
			this.serviceCenterId = appointment.getCaseDetails().getServiceCenter().getLegalEntityId();
			this.serviceCenterManualId = appointment.getCaseDetails().getServiceCenter().getLegalEntityManualId();
			this.serviceCenterName = appointment.getCaseDetails().getServiceCenter().getLegalEntityName();
		}
		if (appointment.getServiceResource().getContact().getBusinessPlace() != null) {
			this.businessPlace = appointment.getServiceResource().getContact().getBusinessPlace()
					.getBusinessPlaceName();
		}
		this.visitDate = appointment.getVisitDate();
		if (appointment.getServiceResource().getContact().getGrade() != null) {
			this.grade = appointment.getServiceResource().getContact().getGrade().getBoardingName();
		}
		this.createDate = appointment.getCreatedDate();
		if (appointment.getCaseDetails().getContactPerson() != null) {
			this.contactFirstName = appointment.getCaseDetails().getContactPerson().getFirstName();
			this.contactMiddleName = appointment.getCaseDetails().getContactPerson().getMiddleName();
			this.contactLastName = appointment.getCaseDetails().getContactPerson().getLastName();
			this.contactId = appointment.getCaseDetails().getContactPerson().getContactId();
			this.contactPhoneNumber=appointment.getCaseDetails().getContactPerson().getMobile();
		}
		if (appointment.getCaseDetails().getSite() != null) {
			this.siteId = appointment.getCaseDetails().getSite().getId();
			this.siteManualId = appointment.getCaseDetails().getSite().getManualId();
			this.siteName = appointment.getCaseDetails().getSite().getSiteName();
			if (appointment.getCaseDetails().getSite().getSsa() != null) {
				this.ssaId = appointment.getCaseDetails().getSite().getSsa().getId();
				this.ssaName = appointment.getCaseDetails().getSite().getSsa().getName();
			}if(rating!=null) {
				this.customerRating=rating.getEngrating();
				if(rating.getUserRemarks()!=null) {
					this.customerRemarks=rating.getUserRemarks();
				}
			}
			
			if(appointment.getEsignature()!=null) {
				this.eSignature=appointment.getEsignature();
			}
		}

		this.isFsrCreated = appointment.isFsrCreated();

	}

	
	public boolean isUploadfsrMandatory() {
		return uploadfsrMandatory;
	}

	public void setUploadfsrMandatory(boolean uploadfsrMandatory) {
		this.uploadfsrMandatory = uploadfsrMandatory;
	}

	public boolean isFsrMandatory() {
		return fsrMandatory;
	}

	public void setFsrMandatory(boolean fsrMandatory) {
		this.fsrMandatory = fsrMandatory;
	}

	public boolean isBillingRequired() {
		return billingRequired;
	}

	public void setBillingRequired(boolean billingRequired) {
		this.billingRequired = billingRequired;
	}

	public long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getAppointmentManualId() {
		return appointmentManualId;
	}

	public void setAppointmentManualId(String appointmentManualId) {
		this.appointmentManualId = appointmentManualId;
	}

	public String getFaultObserve() {
		return faultObserve;
	}

	public void setFaultObserve(String faultObserve) {
		this.faultObserve = faultObserve;
	}

	public String getCorrectiveAction() {
		return correctiveAction;
	}

	public void setCorrectiveAction(String correctiveAction) {
		this.correctiveAction = correctiveAction;
	}

	public String getPunchPointExicom() {
		return punchPointExicom;
	}

	public String getSparePartReplacedFrom() {
		return sparePartReplacedFrom;
	}

	public void setSparePartReplacedFrom(String sparePartReplacedFrom) {
		this.sparePartReplacedFrom = sparePartReplacedFrom;
	}

	public void setPunchPointExicom(String punchPointExicom) {
		this.punchPointExicom = punchPointExicom;
	}

	public long getCircleManagerContactId() {
		return circleManagerContactId;
	}

	public void setCircleManagerContactId(long circleManagerContactId) {
		this.circleManagerContactId = circleManagerContactId;
	}

	public String getSiteManualId() {
		return siteManualId;
	}

	public void setSiteManualId(String siteManualId) {
		this.siteManualId = siteManualId;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getServiceCenterName() {
		return serviceCenterName;
	}

	public void setServiceCenterName(String serviceCenterName) {
		this.serviceCenterName = serviceCenterName;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getCaseManualId() {
		return caseManualId;
	}

	public void setCaseManualId(String caseManualId) {
		this.caseManualId = caseManualId;
	}

	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}

	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}

	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	public String getAcknowledgedByName() {
		return acknowledgedByName;
	}

	public void setAcknowledgedByName(String acknowledgedByName) {
		this.acknowledgedByName = acknowledgedByName;
	}

	public String getCircleManagerContactEmailId() {
		return circleManagerContactEmailId;
	}

	public void setCircleManagerContactEmailId(String circleManagerContactEmailId) {
		this.circleManagerContactEmailId = circleManagerContactEmailId;
	}

	public String getOwnerLastName() {
		return ownerLastName;
	}

	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}

	public String getServiceResourceName() {
		return serviceResourceName;
	}

	public void setServiceResourceName(String serviceResourceName) {
		this.serviceResourceName = serviceResourceName;
	}
	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}

	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}

	public double getFixedPayout() {
		return fixedPayout;
	}

	public void setFixedPayout(double fixedPayout) {
		this.fixedPayout = fixedPayout;
	}

	public String getBusinessPlace() {
		return businessPlace;
	}

	public void setBusinessPlace(String businessPlace) {
		this.businessPlace = businessPlace;
	}

	public long getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(long employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getPendingResource() {
		return PendingResource;
	}

	public void setPendingResource(String pendingResource) {
		PendingResource = pendingResource;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getCustomerRepresentativeContact() {
		return customerRepresentativeContact;
	}

	public void setCustomerRepresentativeContact(String customerRepresentativeContact) {
		this.customerRepresentativeContact = customerRepresentativeContact;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getCreatedByLastName() {
		return createdByLastName;
	}

	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}

	public String getAssetsPartCode() {
		return assetsPartCode;
	}

	public void setAssetsPartCode(String assetsPartCode) {
		this.assetsPartCode = assetsPartCode;
	}

	public long getServiceFsrId() {
		return serviceFsrId;
	}

	public void setServiceFsrId(long serviceFsrId) {
		this.serviceFsrId = serviceFsrId;
	}

	public long getCommFsrId() {
		return commFsrId;
	}

	public void setCommFsrId(long commFsrId) {
		this.commFsrId = commFsrId;
	}

	public String getAssetsSapSerialNumber() {
		return assetsSapSerialNumber;
	}

	public void setAssetsSapSerialNumber(String assetsSapSerialNumber) {
		this.assetsSapSerialNumber = assetsSapSerialNumber;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public long getAssetId() {
		return assetId;
	}

	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}

	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}

	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}

	public String getWccId() {
		return wccId;
	}

	public void setWccId(String wccId) {
		this.wccId = wccId;
	}

	public String getAssetDescription() {
		return assetDescription;
	}

	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}

	public String getServiceAppointmentStatusName() {
		return serviceAppointmentStatusName;
	}

	public void setServiceAppointmentStatusName(String serviceAppointmentStatusName) {
		this.serviceAppointmentStatusName = serviceAppointmentStatusName;
	}

	public String getCaseReasonName() {
		return caseReasonName;
	}

	public void setCaseReasonName(String caseReasonName) {
		this.caseReasonName = caseReasonName;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getSparePartReplaced() {
		return sparePartReplaced;
	}

	public void setSparePartReplaced(String sparePartReplaced) {
		this.sparePartReplaced = sparePartReplaced;
	}

	public String getOwnerEmailId() {
		return ownerEmailId;
	}

	public void setOwnerEmailId(String ownerEmailId) {
		this.ownerEmailId = ownerEmailId;
	}

	public String getOwnerFirstName() {
		return ownerFirstName;
	}

	public void setOwnerFirstName(String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}

	public boolean isApprovedByFinanceCheck() {
		return isApprovedByFinanceCheck;
	}

	public void setApprovedByFinanceCheck(boolean isApprovedByFinanceCheck) {
		this.isApprovedByFinanceCheck = isApprovedByFinanceCheck;
	}

	public long getResolutionMethodId() {
		return resolutionMethodId;
	}

	public void setResolutionMethodId(long resolutionMethodId) {
		this.resolutionMethodId = resolutionMethodId;
	}

	public String getContactMiddleName() {
		return contactMiddleName;
	}

	public void setContactMiddleName(String contactMiddleName) {
		this.contactMiddleName = contactMiddleName;
	}

	public long getServiceAppointmentStatusId() {
		return serviceAppointmentStatusId;
	}

	public void setServiceAppointmentStatusId(long serviceAppointmentStatusId) {
		this.serviceAppointmentStatusId = serviceAppointmentStatusId;
	}

	public String getCaseTypeName() {
		return caseTypeName;
	}

	public void setCaseTypeName(String caseTypeName) {
		this.caseTypeName = caseTypeName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public boolean isWcc() {
		return wcc;
	}

	public void setWcc(boolean wcc) {
		this.wcc = wcc;
	}

	public String getAcknowledgedByPhone() {
		return acknowledgedByPhone;
	}

	public void setAcknowledgedByPhone(String acknowledgedByPhone) {
		this.acknowledgedByPhone = acknowledgedByPhone;
	}

	public String getSparePartName() {
		return sparePartName;
	}

	public void setSparePartName(String sparePartName) {
		this.sparePartName = sparePartName;
	}

	public String getSsaName() {
		return ssaName;
	}

	public void setSsaName(String ssaName) {
		this.ssaName = ssaName;
	}

	public long getSiteId() {
		return siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public long getSchOnlyId() {
		return SchOnlyId;
	}

	public void setSchOnlyId(long schOnlyId) {
		SchOnlyId = schOnlyId;
	}

	public String gettFlag() {
		return tFlag;
	}

	public void settFlag(String tFlag) {
		this.tFlag = tFlag;
	}

	public long getServiceCenterId() {
		return serviceCenterId;
	}

	public void setServiceCenterId(long serviceCenterId) {
		this.serviceCenterId = serviceCenterId;
	}

	public long getCaseType() {
		return caseType;
	}

	public void setCaseType(long caseType) {
		this.caseType = caseType;
	}

	public long getSparePartQuantity() {
		return sparePartQuantity;
	}

	public void setSparePartQuantity(long sparePartQuantity) {
		this.sparePartQuantity = sparePartQuantity;
	}

	public String getCreatedByFirstName() {
		return createdByFirstName;
	}

	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}

	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}

	public boolean isRmaCreated() {
		return rmaCreated;
	}

	public void setRmaCreated(boolean rmaCreated) {
		this.rmaCreated = rmaCreated;
	}

	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getServiceCenterManualId() {
		return serviceCenterManualId;
	}

	public void setServiceCenterManualId(String serviceCenterManualId) {
		this.serviceCenterManualId = serviceCenterManualId;
	}

	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}

	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}

	public String getWccManualId() {
		return wccManualId;
	}

	public void setWccManualId(String wccManualId) {
		this.wccManualId = wccManualId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOwnerMiddleName() {
		return ownerMiddleName;
	}

	public void setOwnerMiddleName(String ownerMiddleName) {
		this.ownerMiddleName = ownerMiddleName;
	}

	public String getCircleManagerContactFirstName() {
		return circleManagerContactFirstName;
	}

	public void setCircleManagerContactFirstName(String circleManagerContactFirstName) {
		this.circleManagerContactFirstName = circleManagerContactFirstName;
	}

	public long getPendingResponsibilityId() {
		return pendingResponsibilityId;
	}

	public void setPendingResponsibilityId(long pendingResponsibilityId) {
		this.pendingResponsibilityId = pendingResponsibilityId;
	}

	public String getContactLastName() {
		return contactLastName;
	}

	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	public String getAccountSapNumber() {
		return accountSapNumber;
	}

	public void setAccountSapNumber(String accountSapNumber) {
		this.accountSapNumber = accountSapNumber;
	}

	public boolean isFsrCreated() {
		return isFsrCreated;
	}

	public void setFsrCreated(boolean isFsrCreated) {
		this.isFsrCreated = isFsrCreated;
	}

	public long getSsaId() {
		return ssaId;
	}

	public void setSsaId(long ssaId) {
		this.ssaId = ssaId;
	}

	public String getSiteIdManual() {
		return siteManualId;
	}

	public void setSiteIdManual(String siteManualId) {
		this.siteManualId = siteManualId;
	}

	public String getModifiedByLastName() {
		return modifiedByLastName;
	}

	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPendingResponsibilityName() {
		return pendingResponsibilityName;
	}

	public void setPendingResponsibilityName(String pendingResponsibilityName) {
		this.pendingResponsibilityName = pendingResponsibilityName;
	}

	public String getDueTime() {
		return dueTime;
	}

	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}

	public String getContactFirstName() {
		return contactFirstName;
	}

	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}

	public String getCustomerRepresentativeName() {
		return customerRepresentativeName;
	}

	public void setCustomerRepresentativeName(String customerRepresentativeName) {
		this.customerRepresentativeName = customerRepresentativeName;
	}

	public String getClaimStatusName() {
		return claimStatusName;
	}

	public void setClaimStatusName(String claimStatusName) {
		this.claimStatusName = claimStatusName;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getApprovedByFinance() {
		return approvedByFinance;
	}

	public void setApprovedByFinance(Date approvedByFinance) {
		this.approvedByFinance = approvedByFinance;
	}

	public String getCircleManagerContactMiddleName() {
		return circleManagerContactMiddleName;
	}

	public void setCircleManagerContactMiddleName(String circleManagerContactMiddleName) {
		this.circleManagerContactMiddleName = circleManagerContactMiddleName;
	}

	public String getCircleManagerContactLastName() {
		return circleManagerContactLastName;
	}

	public void setCircleManagerContactLastName(String circleManagerContactLastName) {
		this.circleManagerContactLastName = circleManagerContactLastName;
	}

	public long getClaimStatusId() {
		return claimStatusId;
	}

	public void setClaimStatusId(long claimStatusId) {
		this.claimStatusId = claimStatusId;
	}

	public long getServiceResourceId() {
		return serviceResourceId;
	}

	public void setServiceResourceId(long serviceResourceId) {
		this.serviceResourceId = serviceResourceId;
	}

	public long getSolutionImpartedId() {
		return solutionImpartedId;
	}

	public void setSolutionImpartedId(long solutionImpartedId) {
		this.solutionImpartedId = solutionImpartedId;
	}

	public long getCaseReason() {
		return caseReason;
	}

	public void setCaseReason(long caseReason) {
		this.caseReason = caseReason;
	}

	public String getCreateByEmailId() {
		return createByEmailId;
	}

	public void setCreateByEmailId(String createByEmailId) {
		this.createByEmailId = createByEmailId;
	}

	public String getOwner_details_email_id() {
		return owner_details_email_id;
	}

	public void setOwner_details_email_id(String owner_details_email_id) {
		this.owner_details_email_id = owner_details_email_id;
	}

	public long getContactId() {
		return contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
	}

	public String getPunchPointCustomer() {
		return punchPointCustomer;
	}

	public void setPunchPointCustomer(String punchPointCustomer) {
		this.punchPointCustomer = punchPointCustomer;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

//	public Set<BtSolutionImpartedProblemResponse> getBtSolutionImpartedProblemResponses() {
//		return btSolutionImpartedProblemResponses;
//	}
//
//	public void setBtSolutionImpartedProblemResponses(
//			Set<BtSolutionImpartedProblemResponse> btSolutionImpartedProblemResponses) {
//		this.btSolutionImpartedProblemResponses = btSolutionImpartedProblemResponses;
//	}

	public long getCustomerBucketReasonId() {
		return customerBucketReasonId;
	}

	public void setCustomerBucketReasonId(long customerBucketReasonId) {
		this.customerBucketReasonId = customerBucketReasonId;
	}

	public String getCustomerBucketReasonName() {
		return customerBucketReasonName;
	}

	public void setCustomerBucketReasonName(String customerBucketReasonName) {
		this.customerBucketReasonName = customerBucketReasonName;
	}

	public long getServicePayoutId() {
		return servicePayoutId;
	}

	public void setServicePayoutId(long servicePayoutId) {
		this.servicePayoutId = servicePayoutId;
	}

	public String getFaultyObeserved() {
		return faultyObeserved;
	}

	public void setFaultyObeserved(String faultyObeserved) {
		this.faultyObeserved = faultyObeserved;
	}

//	public Set<BtSchOnlyResponse> getBtSchOnlyResponses() {
//		return btSchOnlyResponses;
//	}
//
//	public void setBtSchOnlyResponses(Set<BtSchOnlyResponse> btSchOnlyResponses) {
//		this.btSchOnlyResponses = btSchOnlyResponses;
//	}

	public boolean isSchFill() {
		return schFill;
	}

	public void setSchFill(boolean schFill) {
		this.schFill = schFill;
	}

	public String geteSignature() {
		return eSignature;
	}

	public void seteSignature(String eSignature) {
		this.eSignature = eSignature;
	}

	public int getCustomerRating() {
		return customerRating;
	}

	public void setCustomerRating(int customerRating) {
		this.customerRating = customerRating;
	}

	public String getCustomerRemarks() {
		return customerRemarks;
	}

	public void setCustomerRemarks(String customerRemarks) {
		this.customerRemarks = customerRemarks;
	}
	
	
	

}
