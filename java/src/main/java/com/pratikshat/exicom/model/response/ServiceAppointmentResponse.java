package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.ESignatureOnSA;
import com.pratikshat.exicom.entity.SaEngRating;
import com.pratikshat.exicom.entity.ServiceAppointment;

public class ServiceAppointmentResponse  {
	private long serviceAppointmentId;
	private String serviceAppointmentManulaId;
	private long siteId;
	private String siteIdManual;
	private long caseId;
	private String caseIdManual;
	private String caseReason;
	private String siteName;
	private String ssaName;
	private long ssaId;
	private long contactId;
	private String faultObserved;
	private String correctiveActions;
	private String punchPointExiom;
	private String punchPointCustomer;
	private java.util.Date visitDate;
	private String customerRepresentativeName;
	private String customerRepresentativeContact;
	private String remark;
	private String pendingResponsibilityName;
	private long pendingResponsibilityId;
	private String earliestStartPermittedDate;
	private String earliestStartPermittedTime;
	private String dueDate;
	private String dueTime;
	private String tFlag;
	private String contactFirstName;
	private String contactMiddleName;
	private String contactLastName;
	private String contactEmailId;
	private String contactPhoneNumber;
	private String caseType;
	private String assetsSapSerialNumber;
	private String caseStatus;
	private String serviceAppointmentStatusName;
	private long serviceAppointmentStatusId;
	private String createdByFirstName;
	private String createdByMiddleName;
	private String createdByLastName;
	private java.util.Date createdDate;
	private String createByEmailId;
	private String ownerFirstName;
	private String ownerMiddleName;
	private String ownerLastName;
	private String ownerEmailId;
	private String modifiedByFirstName;
	private String modifiedByMiddleName;
	private String modifiedByLastName;
	private String modifiedByEmailId;
	private java.util.Date modifiedDate;
	private String circleName;
	
	private String accountSapNumber;
	private String accountName;
	private String assetsPartCode;
	private String serviceResourceName;
	private long serviceResourceId;
	private long circleManagerContactId;
	private String circleManagerContactFirstName;
	private String circleManagerContactMiddleName;
	private String circleManagerContactLastName;
	private String circleManagerContactEmailId;
	private long serviceCenterId;
	private String serviceCenterManualId;
	private String serviceCenterName;
	private long claimStatusId;
	private String claimStatusName;
	String employeeCode;
	String grade;
	double fixedPayout;
	String businessPlace;
	boolean isApprovedByFinanceCheck;
	Date approvedByFinance;
	String costCenter;
	Date caseCloserDate;
	String assetDescription;

	boolean wcc=false;
	String wccId;
	String wccManualId;
	String actlAssetsSerialNumber;
	
	String acknowledgedByName;
	String acknowledgedByPhone;
	
	Double latitude;
	Double longitude;
	String address;
	
	String eSignature;
	String closedFromPlatform; 
	
	
	int customerRating;
	String customerRemarks;
	boolean engAssign;
	
	public ServiceAppointmentResponse(){}
	
	public ServiceAppointmentResponse(com.pratikshat.exicom.entity.ServiceAppointment serviceAppointment, ESignatureOnSA esg) {
		
		if(serviceAppointment!=null) {
			this.serviceAppointmentId=serviceAppointment.getServiceAppointmentId();
			this.serviceAppointmentManulaId=serviceAppointment.getAppointmentNumber();
			this.faultObserved=serviceAppointment.getFaultObserved();
			this.correctiveActions=serviceAppointment.getCorrectiveActions();
			this.punchPointExiom=serviceAppointment.getPunchPointExiom();
			this.punchPointCustomer=serviceAppointment.getPunchPointCustomer();
			this.visitDate=serviceAppointment.getVisitDate();
			this.customerRepresentativeName=serviceAppointment.getCustomerRepresentativeName();
			this.customerRepresentativeContact=serviceAppointment.getCustomerRepresentativeContact();
			this.remark=serviceAppointment.getRemark();
			this.isApprovedByFinanceCheck = serviceAppointment.isApprovedByFinance();
			this.approvedByFinance = serviceAppointment.getApprovedByFinance();
			this.costCenter = serviceAppointment.getCostCenter();
			this.wcc=serviceAppointment.isWcc();
			if(serviceAppointment.getPendingResponsibility()!=null) {
				this.pendingResponsibilityName=serviceAppointment.getPendingResponsibility().getPendingResponsibilityName();
				this.pendingResponsibilityId = serviceAppointment.getPendingResponsibility().getPendingResponsibilityId();
			}
			
			this.earliestStartPermittedDate=serviceAppointment.getEarliestStartPermittedDate();
			this.earliestStartPermittedTime=serviceAppointment.getEarliestStartPermittedTime();
			this.dueDate=serviceAppointment.getDueDate();
			this.dueTime=serviceAppointment.getDueTime();
			this.tFlag=serviceAppointment.gettFlag();
			this.engAssign=serviceAppointment.isEngAssigned();
			if(serviceAppointment.getStatus()!=null) {
				this.serviceAppointmentStatusName=serviceAppointment.getStatus().getStatusName();
				this.serviceAppointmentStatusId = serviceAppointment.getStatus().getStatusId();
			}
			
			if(serviceAppointment.getOwnerDetails()!=null) {
				this.ownerEmailId=serviceAppointment.getOwnerDetails().getEmailId();
				this.ownerFirstName=serviceAppointment.getOwnerDetails().getFirstName();
				this.ownerMiddleName=serviceAppointment.getOwnerDetails().getMiddleName();
				this.ownerLastName=serviceAppointment.getOwnerDetails().getLastName();
				if(serviceAppointment.getOwnerDetails().getContact()!=null) {
					this.employeeCode = serviceAppointment.getOwnerDetails().getContact().getEmployeeCode();	
				}
				
			}
			if(serviceAppointment.getCreatedBy()!=null) {
				this.createByEmailId=serviceAppointment.getCreatedBy().getEmailId();
				this.createdByFirstName=serviceAppointment.getCreatedBy().getFirstName();
				this.createdByMiddleName=serviceAppointment.getCreatedBy().getMiddleName();
				this.createdByLastName=serviceAppointment.getCreatedBy().getLastName();
			
			}
			this.createdDate=serviceAppointment.getCreateDate();
			if(serviceAppointment.getModifiedBy()!=null) {
				this.modifiedByEmailId=serviceAppointment.getModifiedBy().getEmailId();
				this.modifiedByFirstName=serviceAppointment.getModifiedBy().getFirstName();
				this.modifiedByMiddleName=serviceAppointment.getModifiedBy().getMiddleName();
				this.modifiedByLastName=serviceAppointment.getModifiedBy().getLastName();
			}
			if(serviceAppointment.getCircle()!=null) {
				this.circleName=serviceAppointment.getCircle().getCircleName();
				
			}
			if(serviceAppointment.getCircleManager()!=null) {
				this.circleManagerContactId=serviceAppointment.getCircleManager().getContactId();
				this.circleManagerContactFirstName=serviceAppointment.getCircleManager().getFirstName();
				this.circleManagerContactMiddleName=serviceAppointment.getCircleManager().getMiddleName();
				this.circleManagerContactLastName=serviceAppointment.getCircleManager().getLastName();
				this.circleManagerContactEmailId=serviceAppointment.getCircleManager().getEmailId();
			}
			if(serviceAppointment.getCaseDetails().getServiceCenter()!=null) {
				this.serviceCenterId=serviceAppointment.getCaseDetails().getServiceCenter().getLegalEntityId();
				this.serviceCenterManualId=serviceAppointment.getCaseDetails().getServiceCenter().getManualId();
				this.serviceCenterName=serviceAppointment.getCaseDetails().getServiceCenter().getLegalEntityName();
				this.fixedPayout = serviceAppointment.getFixedPayout();
			}
			this.modifiedDate=serviceAppointment.getModifiedDate();
			if(serviceAppointment.getCaseDetails()!=null) {
				
				this.caseCloserDate = serviceAppointment.getCaseDetails().getCloseDate();
				
				if(serviceAppointment.getCaseDetails().getSite()!=null) {
					this.siteId=serviceAppointment.getCaseDetails().getSite().getSiteId();
					this.siteIdManual=serviceAppointment.getCaseDetails().getSite().getManualSiteId();
					this.siteName=serviceAppointment.getCaseDetails().getSite().getSiteName();
					
					if(serviceAppointment.getCaseDetails().getSite().getSsa()!=null) {
						this.ssaName=serviceAppointment.getCaseDetails().getSite().getSsa().getName();
						this.ssaId = serviceAppointment.getCaseDetails().getSite().getSsa().getId();
					}
					if(serviceAppointment.getCaseDetails().getSite().getAccount()!=null) {
						this.accountName=serviceAppointment.getCaseDetails().getSite().getAccount().getName();
						this.accountSapNumber=serviceAppointment.getCaseDetails().getSite().getAccount().getSapCustomerNumber();
					}
				}
				
				if(serviceAppointment.getCaseDetails().getAssets()!=null) {
					this.assetsSapSerialNumber=serviceAppointment.getCaseDetails().getAssets().getSerialNumber();
					
					if(serviceAppointment.getCaseDetails().getAssets().getProduct()!=null) {
						this.assetsPartCode=serviceAppointment.getCaseDetails().getAssets().getProduct().getProductPartCode();
						this.assetDescription = serviceAppointment.getCaseDetails().getAssets().getProduct().getPdoductDescription();
					}
					this.actlAssetsSerialNumber=serviceAppointment.getCaseDetails().getAssets().getActlSerialNumber();
					
				}
				if(serviceAppointment.getCaseDetails().getCaseReason()!=null)
					this.caseReason=serviceAppointment.getCaseDetails().getCaseReason().getCaseReasonDetailsName();
				this.caseId=serviceAppointment.getCaseDetails().getCaseId();
				this.caseIdManual=serviceAppointment.getCaseDetails().getManualId();
				if(serviceAppointment.getCaseDetails().getStatus()!=null)
					this.caseStatus=serviceAppointment.getCaseDetails().getStatus().getCaseStatusName();
				if(serviceAppointment.getCaseDetails().getCaseType()!=null)
					this.caseType=serviceAppointment.getCaseDetails().getCaseType().getCaseTypesName();
				if(serviceAppointment.getCaseDetails().getContactPerson()!=null) {
					this.contactEmailId=serviceAppointment.getCaseDetails().getContactPerson().getEmailId();
					this.contactFirstName=serviceAppointment.getCaseDetails().getContactPerson().getFirstName();
					this.contactMiddleName=serviceAppointment.getCaseDetails().getContactPerson().getMiddleName();
					this.contactLastName=serviceAppointment.getCaseDetails().getContactPerson().getLastName();
					this.contactId=serviceAppointment.getCaseDetails().getContactPerson().getContactId();
					this.contactPhoneNumber=serviceAppointment.getCaseDetails().getContactPerson().getMobile();
				}
			}
			if(serviceAppointment.getServiceResource()!=null) {
				this.serviceResourceId=serviceAppointment.getServiceResource().getServiceResourceId();
				this.serviceResourceName=serviceAppointment.getServiceResource().getName();
				
				if(serviceAppointment.getServiceResource().getContact()!=null) {
					if(serviceAppointment.getServiceResource().getContact().getBusinessPlace()!=null) {
						this.businessPlace = serviceAppointment.getServiceResource().getContact().getBusinessPlace().getBusinessPlaceName();
					}
					
					if(serviceAppointment.getServiceResource().getContact().getGrade()!=null) {
						this.grade = serviceAppointment.getServiceResource().getContact().getGrade().getBoardingName();
					}
				}
			}
			
			if(serviceAppointment.getClaimStatus()!=null) {
				this.claimStatusId = serviceAppointment.getClaimStatus().getClaimStatusId();
				this.claimStatusName = serviceAppointment.getClaimStatus().getClaimStatusName();
			}
			if(serviceAppointment.isWcc()) {
				this.wccId=serviceAppointment.getWccId();
				this.wccManualId=serviceAppointment.getWccManualId();
			}
		}
		
		this.acknowledgedByName=serviceAppointment.getAcknowledgedByName();
		this.acknowledgedByPhone=serviceAppointment.getAcknowledgedByPhone();
		
		this.latitude = serviceAppointment.getLatitude();
		this.longitude = serviceAppointment.getLongitude();
		this.address = serviceAppointment.getAddress();
		this.closedFromPlatform = serviceAppointment.getClosedFromPlatform();
		if(esg!=null) {
			this.eSignature = esg.geteSignature();
		}
	
	}
	
	public ServiceAppointmentResponse(com.pratikshat.exicom.entity.ServiceAppointment serviceAppointment) {
		if(serviceAppointment!=null) {
			this.serviceAppointmentId=serviceAppointment.getServiceAppointmentId();
			this.engAssign=serviceAppointment.isEngAssigned();
			this.serviceAppointmentManulaId=serviceAppointment.getAppointmentNumber();
			this.faultObserved=serviceAppointment.getFaultObserved();
			this.correctiveActions=serviceAppointment.getCorrectiveActions();
			this.punchPointExiom=serviceAppointment.getPunchPointExiom();
			this.punchPointCustomer=serviceAppointment.getPunchPointCustomer();
			this.visitDate=serviceAppointment.getVisitDate();
			this.customerRepresentativeName=serviceAppointment.getCustomerRepresentativeName();
			this.customerRepresentativeContact=serviceAppointment.getCustomerRepresentativeContact();
			this.remark=serviceAppointment.getRemark();
			this.isApprovedByFinanceCheck = serviceAppointment.isApprovedByFinance();
			this.approvedByFinance = serviceAppointment.getApprovedByFinance();
			this.costCenter = serviceAppointment.getCostCenter();
			this.wcc=serviceAppointment.isWcc();
			if(serviceAppointment.getPendingResponsibility()!=null) {
				this.pendingResponsibilityName=serviceAppointment.getPendingResponsibility().getPendingResponsibilityName();
				this.pendingResponsibilityId = serviceAppointment.getPendingResponsibility().getPendingResponsibilityId();
			}
			
			this.earliestStartPermittedDate=serviceAppointment.getEarliestStartPermittedDate();
			this.earliestStartPermittedTime=serviceAppointment.getEarliestStartPermittedTime();
			this.dueDate=serviceAppointment.getDueDate();
			this.dueTime=serviceAppointment.getDueTime();
			this.tFlag=serviceAppointment.gettFlag();
			if(serviceAppointment.getStatus()!=null) {
				this.serviceAppointmentStatusName=serviceAppointment.getStatus().getStatusName();
				this.serviceAppointmentStatusId = serviceAppointment.getStatus().getStatusId();
			}
			
			if(serviceAppointment.getOwnerDetails()!=null) {
				this.ownerEmailId=serviceAppointment.getOwnerDetails().getEmailId();
				this.ownerFirstName=serviceAppointment.getOwnerDetails().getFirstName();
				this.ownerMiddleName=serviceAppointment.getOwnerDetails().getMiddleName();
				this.ownerLastName=serviceAppointment.getOwnerDetails().getLastName();
				if(serviceAppointment.getOwnerDetails().getContact()!=null) {
					this.employeeCode = serviceAppointment.getOwnerDetails().getContact().getEmployeeCode();	
				}
				
			}
			if(serviceAppointment.getCreatedBy()!=null) {
				this.createByEmailId=serviceAppointment.getCreatedBy().getEmailId();
				this.createdByFirstName=serviceAppointment.getCreatedBy().getFirstName();
				this.createdByMiddleName=serviceAppointment.getCreatedBy().getMiddleName();
				this.createdByLastName=serviceAppointment.getCreatedBy().getLastName();
			
			}
			this.createdDate=serviceAppointment.getCreateDate();
			if(serviceAppointment.getModifiedBy()!=null) {
				this.modifiedByEmailId=serviceAppointment.getModifiedBy().getEmailId();
				this.modifiedByFirstName=serviceAppointment.getModifiedBy().getFirstName();
				this.modifiedByMiddleName=serviceAppointment.getModifiedBy().getMiddleName();
				this.modifiedByLastName=serviceAppointment.getModifiedBy().getLastName();
			}
			if(serviceAppointment.getCircle()!=null) {
				this.circleName=serviceAppointment.getCircle().getCircleName();
				
			}
			if(serviceAppointment.getCircleManager()!=null) {
				this.circleManagerContactId=serviceAppointment.getCircleManager().getContactId();
				this.circleManagerContactFirstName=serviceAppointment.getCircleManager().getFirstName();
				this.circleManagerContactMiddleName=serviceAppointment.getCircleManager().getMiddleName();
				this.circleManagerContactLastName=serviceAppointment.getCircleManager().getLastName();
				this.circleManagerContactEmailId=serviceAppointment.getCircleManager().getEmailId();
			}
			if(serviceAppointment.getCaseDetails().getServiceCenter()!=null) {
				this.serviceCenterId=serviceAppointment.getCaseDetails().getServiceCenter().getLegalEntityId();
				this.serviceCenterManualId=serviceAppointment.getCaseDetails().getServiceCenter().getManualId();
				this.serviceCenterName=serviceAppointment.getCaseDetails().getServiceCenter().getLegalEntityName();
				this.fixedPayout = serviceAppointment.getFixedPayout();
			}
			this.modifiedDate=serviceAppointment.getModifiedDate();
			if(serviceAppointment.getCaseDetails()!=null) {
				
				this.caseCloserDate = serviceAppointment.getCaseDetails().getCloseDate();
				
				if(serviceAppointment.getCaseDetails().getSite()!=null) {
					this.siteId=serviceAppointment.getCaseDetails().getSite().getSiteId();
					this.siteIdManual=serviceAppointment.getCaseDetails().getSite().getManualSiteId();
					this.siteName=serviceAppointment.getCaseDetails().getSite().getSiteName();
					
					if(serviceAppointment.getCaseDetails().getSite().getSsa()!=null) {
						this.ssaName=serviceAppointment.getCaseDetails().getSite().getSsa().getName();
						this.ssaId = serviceAppointment.getCaseDetails().getSite().getSsa().getId();
					}
					if(serviceAppointment.getCaseDetails().getSite().getAccount()!=null) {
						this.accountName=serviceAppointment.getCaseDetails().getSite().getAccount().getName();
						this.accountSapNumber=serviceAppointment.getCaseDetails().getSite().getAccount().getSapCustomerNumber();
					}
				}
				
				if(serviceAppointment.getCaseDetails().getAssets()!=null) {
					this.assetsSapSerialNumber=serviceAppointment.getCaseDetails().getAssets().getSerialNumber();
					
					if(serviceAppointment.getCaseDetails().getAssets().getProduct()!=null) {
						this.assetsPartCode=serviceAppointment.getCaseDetails().getAssets().getProduct().getProductPartCode();
						this.assetDescription = serviceAppointment.getCaseDetails().getAssets().getProduct().getPdoductDescription();
					}
					this.actlAssetsSerialNumber=serviceAppointment.getCaseDetails().getAssets().getActlSerialNumber();
					
				}
				if(serviceAppointment.getCaseDetails().getCaseReason()!=null)
					this.caseReason=serviceAppointment.getCaseDetails().getCaseReason().getCaseReasonDetailsName();
				this.caseId=serviceAppointment.getCaseDetails().getCaseId();
				this.caseIdManual=serviceAppointment.getCaseDetails().getManualId();
				if(serviceAppointment.getCaseDetails().getStatus()!=null)
					this.caseStatus=serviceAppointment.getCaseDetails().getStatus().getCaseStatusName();
				if(serviceAppointment.getCaseDetails().getCaseType()!=null)
					this.caseType=serviceAppointment.getCaseDetails().getCaseType().getCaseTypesName();
				if(serviceAppointment.getCaseDetails().getContactPerson()!=null) {
					this.contactEmailId=serviceAppointment.getCaseDetails().getContactPerson().getEmailId();
					this.contactFirstName=serviceAppointment.getCaseDetails().getContactPerson().getFirstName();
					this.contactMiddleName=serviceAppointment.getCaseDetails().getContactPerson().getMiddleName();
					this.contactLastName=serviceAppointment.getCaseDetails().getContactPerson().getLastName();
					this.contactId=serviceAppointment.getCaseDetails().getContactPerson().getContactId();
					this.contactPhoneNumber=serviceAppointment.getCaseDetails().getContactPerson().getMobile();
				}
			}
			if(serviceAppointment.getServiceResource()!=null) {
				this.serviceResourceId=serviceAppointment.getServiceResource().getServiceResourceId();
				this.serviceResourceName=serviceAppointment.getServiceResource().getName();
				
				if(serviceAppointment.getServiceResource().getContact()!=null) {
					if(serviceAppointment.getServiceResource().getContact().getBusinessPlace()!=null) {
						this.businessPlace = serviceAppointment.getServiceResource().getContact().getBusinessPlace().getBusinessPlaceName();
					}
					
					if(serviceAppointment.getServiceResource().getContact().getGrade()!=null) {
						this.grade = serviceAppointment.getServiceResource().getContact().getGrade().getBoardingName();
					}
				}
			}
			
			if(serviceAppointment.getClaimStatus()!=null) {
				this.claimStatusId = serviceAppointment.getClaimStatus().getClaimStatusId();
				this.claimStatusName = serviceAppointment.getClaimStatus().getClaimStatusName();
			}
			if(serviceAppointment.isWcc()) {
				this.wccId=serviceAppointment.getWccId();
				this.wccManualId=serviceAppointment.getWccManualId();
			}
		}
		
		this.acknowledgedByName=serviceAppointment.getAcknowledgedByName();
		this.acknowledgedByPhone=serviceAppointment.getAcknowledgedByPhone();
		
		this.latitude = serviceAppointment.getLatitude();
		this.longitude = serviceAppointment.getLongitude();
		this.address = serviceAppointment.getAddress();
		this.closedFromPlatform = serviceAppointment.getClosedFromPlatform();
	}
	public boolean isEngAssign() {
		return engAssign;
	}

	public void setEngAssign(boolean engAssign) {
		this.engAssign = engAssign;
	}

	public ServiceAppointmentResponse(com.pratikshat.exicom.entity.ServiceAppointment serviceAppointment, ESignatureOnSA esg,SaEngRating rating) {
		this(serviceAppointment,esg);
		if(rating!=null) {
			this.customerRating=rating.getEngrating();
			this.customerRemarks=rating.getUserRemarks();
		}
	}
	
	/**
	 * @return the serviceAppointmentId
	 */
	public long getServiceAppointmentId() {
		return serviceAppointmentId;
	}
	/**
	 * @return the serviceAppointmentManulaId
	 */
	public String getServiceAppointmentManulaId() {
		return serviceAppointmentManulaId;
	}
	/**
	 * @return the siteId
	 */
	public long getSiteId() {
		return siteId;
	}
	/**
	 * @return the siteIdManual
	 */
	public String getSiteIdManual() {
		return siteIdManual;
	}
	/**
	 * @return the caseId
	 */
	public long getCaseId() {
		return caseId;
	}
	/**
	 * @return the caseIdManual
	 */
	public String getCaseIdManual() {
		return caseIdManual;
	}
	/**
	 * @return the caseReason
	 */
	public String getCaseReason() {
		return caseReason;
	}
	/**
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}
	
	/**
	 * @return the contactId
	 */
	public long getContactId() {
		return contactId;
	}
	/**
	 * @return the faultObserved
	 */
	public String getFaultObserved() {
		return faultObserved;
	}
	/**
	 * @return the correctiveActions
	 */
	public String getCorrectiveActions() {
		return correctiveActions;
	}
	/**
	 * @return the punchPointExiom
	 */
	public String getPunchPointExiom() {
		return punchPointExiom;
	}
	/**
	 * @return the punchPointCustomer
	 */
	public String getPunchPointCustomer() {
		return punchPointCustomer;
	}
	/**
	 * @return the visitDate
	 */
	public java.util.Date getVisitDate() {
		return visitDate;
	}
	/**
	 * @return the customerRepresentativeName
	 */
	public String getCustomerRepresentativeName() {
		return customerRepresentativeName;
	}
	/**
	 * @return the customerRepresentativeContact
	 */
	public String getCustomerRepresentativeContact() {
		return customerRepresentativeContact;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	
	/**
	 * @return the tFlag
	 */
	public String istFlag() {
		return tFlag;
	}
	/**
	 * @return the contactFirstName
	 */
	public String getContactFirstName() {
		return contactFirstName;
	}
	/**
	 * @return the contactLastName
	 */
	public String getContactLastName() {
		return contactLastName;
	}
	/**
	 * @return the contactEmailId
	 */
	public String getContactEmailId() {
		return contactEmailId;
	}
	/**
	 * @return the contactPhoneNumber
	 */
	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}
	/**
	 * @return the caseType
	 */
	public String getCaseType() {
		return caseType;
	}
	/**
	 * @return the assetsSapSerialNumber
	 */
	public String getAssetsSapSerialNumber() {
		return assetsSapSerialNumber;
	}
	/**
	 * @return the caseStatus
	 */
	public String getCaseStatus() {
		return caseStatus;
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
	 * @return the createByEmailId
	 */
	public String getCreateByEmailId() {
		return createByEmailId;
	}
	/**
	 * @return the ownerFirstName
	 */
	public String getOwnerFirstName() {
		return ownerFirstName;
	}
	/**
	 * @return the ownerLastName
	 */
	public String getOwnerLastName() {
		return ownerLastName;
	}
	/**
	 * @return the ownerEmailId
	 */
	public String getOwnerEmailId() {
		return ownerEmailId;
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
	 * @return the modifiedByEmailId
	 */
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}
	/**
	 * @return the modifiedDate
	 */
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param serviceAppointmentId the serviceAppointmentId to set
	 */
	public void setServiceAppointmentId(long serviceAppointmentId) {
		this.serviceAppointmentId = serviceAppointmentId;
	}
	/**
	 * @param serviceAppointmentManulaId the serviceAppointmentManulaId to set
	 */
	public void setServiceAppointmentManulaId(String serviceAppointmentManulaId) {
		this.serviceAppointmentManulaId = serviceAppointmentManulaId;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	/**
	 * @param siteIdManual the siteIdManual to set
	 */
	public void setSiteIdManual(String siteIdManual) {
		this.siteIdManual = siteIdManual;
	}
	/**
	 * @param caseId the caseId to set
	 */
	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}
	/**
	 * @param caseIdManual the caseIdManual to set
	 */
	public void setCaseIdManual(String caseIdManual) {
		this.caseIdManual = caseIdManual;
	}
	/**
	 * @param caseReason the caseReason to set
	 */
	public void setCaseReason(String caseReason) {
		this.caseReason = caseReason;
	}
	/**
	 * @param siteName the siteName to set
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	/**
	 * @param faultObserved the faultObserved to set
	 */
	public void setFaultObserved(String faultObserved) {
		this.faultObserved = faultObserved;
	}
	/**
	 * @param correctiveActions the correctiveActions to set
	 */
	public void setCorrectiveActions(String correctiveActions) {
		this.correctiveActions = correctiveActions;
	}
	/**
	 * @param punchPointExiom the punchPointExiom to set
	 */
	public void setPunchPointExiom(String punchPointExiom) {
		this.punchPointExiom = punchPointExiom;
	}
	/**
	 * @param punchPointCustomer the punchPointCustomer to set
	 */
	public void setPunchPointCustomer(String punchPointCustomer) {
		this.punchPointCustomer = punchPointCustomer;
	}
	/**
	 * @param visitDate the visitDate to set
	 */
	public void setVisitDate(java.util.Date visitDate) {
		this.visitDate = visitDate;
	}
	/**
	 * @param customerRepresentativeName the customerRepresentativeName to set
	 */
	public void setCustomerRepresentativeName(String customerRepresentativeName) {
		this.customerRepresentativeName = customerRepresentativeName;
	}
	/**
	 * @param customerRepresentativeContact the customerRepresentativeContact to set
	 */
	public void setCustomerRepresentativeContact(String customerRepresentativeContact) {
		this.customerRepresentativeContact = customerRepresentativeContact;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @param tFlag the tFlag to set
	 */
	public void settFlag(String tFlag) {
		this.tFlag = tFlag;
	}
	/**
	 * @param contactFirstName the contactFirstName to set
	 */
	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}
	/**
	 * @param contactLastName the contactLastName to set
	 */
	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}
	/**
	 * @param contactEmailId the contactEmailId to set
	 */
	public void setContactEmailId(String contactEmailId) {
		this.contactEmailId = contactEmailId;
	}
	/**
	 * @param contactPhoneNumber the contactPhoneNumber to set
	 */
	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}
	/**
	 * @param caseType the caseType to set
	 */
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	/**
	 * @param assetsSapSerialNumber the assetsSapSerialNumber to set
	 */
	public void setAssetsSapSerialNumber(String assetsSapSerialNumber) {
		this.assetsSapSerialNumber = assetsSapSerialNumber;
	}
	/**
	 * @param caseStatus the caseStatus to set
	 */
	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
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
	 * @param createByEmailId the createByEmailId to set
	 */
	public void setCreateByEmailId(String createByEmailId) {
		this.createByEmailId = createByEmailId;
	}
	/**
	 * @param ownerFirstName the ownerFirstName to set
	 */
	public void setOwnerFirstName(String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}
	/**
	 * @param ownerLastName the ownerLastName to set
	 */
	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}
	/**
	 * @param ownerEmailId the ownerEmailId to set
	 */
	public void setOwnerEmailId(String ownerEmailId) {
		this.ownerEmailId = ownerEmailId;
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
	 * @param modifiedByEmailId the modifiedByEmailId to set
	 */
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String gettFlag() {
		return tFlag;
	}
	public String getPendingResponsibilityName() {
		return pendingResponsibilityName;
	}
	public void setPendingResponsibilityName(String pendingResponsibilityName) {
		this.pendingResponsibilityName = pendingResponsibilityName;
	}
	public long getPendingResponsibilityId() {
		return pendingResponsibilityId;
	}
	public void setPendingResponsibilityId(long pendingResponsibilityId) {
		this.pendingResponsibilityId = pendingResponsibilityId;
	}
	public String getServiceAppointmentStatusName() {
		return serviceAppointmentStatusName;
	}
	public void setServiceAppointmentStatusName(String serviceAppointmentStatusName) {
		this.serviceAppointmentStatusName = serviceAppointmentStatusName;
	}
	public long getServiceAppointmentStatusId() {
		return serviceAppointmentStatusId;
	}
	public void setServiceAppointmentStatusId(long serviceAppointmentStatusId) {
		this.serviceAppointmentStatusId = serviceAppointmentStatusId;
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
	 * @return the assetsPartCode
	 */
	public String getAssetsPartCode() {
		return assetsPartCode;
	}
	/**
	 * @return the serviceResourceName
	 */
	public String getServiceResourceName() {
		return serviceResourceName;
	}
	/**
	 * @return the serviceResourceId
	 */
	public long getServiceResourceId() {
		return serviceResourceId;
	}
	
	/**
	 * @return the circleManagerContactId
	 */
	public long getCircleManagerContactId() {
		return circleManagerContactId;
	}
	/**
	 * @return the circleManagerContactFirstName
	 */
	public String getCircleManagerContactFirstName() {
		return circleManagerContactFirstName;
	}
	/**
	 * @return the circleManagerContactLastName
	 */
	public String getCircleManagerContactLastName() {
		return circleManagerContactLastName;
	}
	/**
	 * @return the circleManagerContactEmailId
	 */
	public String getCircleManagerContactEmailId() {
		return circleManagerContactEmailId;
	}
	/**
	 * @return the serviceCenterId
	 */
	public long getServiceCenterId() {
		return serviceCenterId;
	}
	/**
	 * @return the serviceCenterManualId
	 */
	public String getServiceCenterManualId() {
		return serviceCenterManualId;
	}
	/**
	 * @return the serviceCenterName
	 */
	public String getServiceCenterName() {
		return serviceCenterName;
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
	 * @param assetsPartCode the assetsPartCode to set
	 */
	public void setAssetsPartCode(String assetsPartCode) {
		this.assetsPartCode = assetsPartCode;
	}
	/**
	 * @param serviceResourceName the serviceResourceName to set
	 */
	public void setServiceResourceName(String serviceResourceName) {
		this.serviceResourceName = serviceResourceName;
	}
	/**
	 * @param serviceResourceId the serviceResourceId to set
	 */
	public void setServiceResourceId(long serviceResourceId) {
		this.serviceResourceId = serviceResourceId;
	}
	/**
	 * @param circleManagerContactId the circleManagerContactId to set
	 */
	public void setCircleManagerContactId(long circleManagerContactId) {
		this.circleManagerContactId = circleManagerContactId;
	}
	/**
	 * @param circleManagerContactFirstName the circleManagerContactFirstName to set
	 */
	public void setCircleManagerContactFirstName(String circleManagerContactFirstName) {
		this.circleManagerContactFirstName = circleManagerContactFirstName;
	}
	/**
	 * @param circleManagerContactLastName the circleManagerContactLastName to set
	 */
	public void setCircleManagerContactLastName(String circleManagerContactLastName) {
		this.circleManagerContactLastName = circleManagerContactLastName;
	}
	/**
	 * @param circleManagerContactEmailId the circleManagerContactEmailId to set
	 */
	public void setCircleManagerContactEmailId(String circleManagerContactEmailId) {
		this.circleManagerContactEmailId = circleManagerContactEmailId;
	}
	/**
	 * @param serviceCenterId the serviceCenterId to set
	 */
	public void setServiceCenterId(long serviceCenterId) {
		this.serviceCenterId = serviceCenterId;
	}
	/**
	 * @param serviceCenterManualId the serviceCenterManualId to set
	 */
	public void setServiceCenterManualId(String serviceCenterManualId) {
		this.serviceCenterManualId = serviceCenterManualId;
	}
	/**
	 * @param serviceCenterName the serviceCenterName to set
	 */
	public void setServiceCenterName(String serviceCenterName) {
		this.serviceCenterName = serviceCenterName;
	}
	public long getClaimStatusId() {
		return claimStatusId;
	}
	public void setClaimStatusId(long claimStatusId) {
		this.claimStatusId = claimStatusId;
	}
	public String getClaimStatusName() {
		return claimStatusName;
	}
	public void setClaimStatusName(String claimStatusName) {
		this.claimStatusName = claimStatusName;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getBusinessPlace() {
		return businessPlace;
	}
	public void setBusinessPlace(String businessPlace) {
		this.businessPlace = businessPlace;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public double getFixedPayout() {
		return fixedPayout;
	}
	public void setFixedPayout(double fixedPayout) {
		this.fixedPayout = fixedPayout;
	}
	public Date getApprovedByFinance() {
		return approvedByFinance;
	}
	public void setApprovedByFinance(Date approvedByFinance) {
		this.approvedByFinance = approvedByFinance;
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
	public String getSsaName() {
		return ssaName;
	}
	public void setSsaName(String ssaName) {
		this.ssaName = ssaName;
	}
	public long getSsaId() {
		return ssaId;
	}
	public void setSsaId(long ssaId) {
		this.ssaId = ssaId;
	}
	public String getContactMiddleName() {
		return contactMiddleName;
	}
	public void setContactMiddleName(String contactMiddleName) {
		this.contactMiddleName = contactMiddleName;
	}
	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}
	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}
	public String getOwnerMiddleName() {
		return ownerMiddleName;
	}
	public void setOwnerMiddleName(String ownerMiddleName) {
		this.ownerMiddleName = ownerMiddleName;
	}
	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}
	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}
	public String getCircleManagerContactMiddleName() {
		return circleManagerContactMiddleName;
	}
	public void setCircleManagerContactMiddleName(String circleManagerContactMiddleName) {
		this.circleManagerContactMiddleName = circleManagerContactMiddleName;
	}
	public Date getCaseCloserDate() {
		return caseCloserDate;
	}
	public void setCaseCloserDate(Date caseCloserDate) {
		this.caseCloserDate = caseCloserDate;
	}

	public boolean isApprovedByFinanceCheck() {
		return isApprovedByFinanceCheck;
	}
	public void setApprovedByFinanceCheck(boolean isApprovedByFinanceCheck) {
		this.isApprovedByFinanceCheck = isApprovedByFinanceCheck;
	}

	public String getAssetDescription() {
		return assetDescription;
	}
	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}
	public boolean isWcc() {
		return wcc;
	}
	public void setWcc(boolean wcc) {
		this.wcc = wcc;
	}
	public String getActlAssetsSerialNumber() {
		return actlAssetsSerialNumber;
	}
	public void setActlAssetsSerialNumber(String actlAssetsSerialNumber) {
		this.actlAssetsSerialNumber = actlAssetsSerialNumber;
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
