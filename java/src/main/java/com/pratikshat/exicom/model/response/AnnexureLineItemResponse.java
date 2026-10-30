package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.AnnexureLineItem;

public class AnnexureLineItemResponse {

	long id;
	String manualId;
	
	
	long annexureId;
	String annexureManualId;
	
	long serviceAppointmentId;
	String serviceAppointmentName;
	
	String circleName;
	
	long serviceCenterId;
	String serviceCenterManualId;
	String serviceCenterName;
	
	long siteId;
	String siteIdManual;
	String siteName;
	
	long caseId;
	String caseIdManual;
	
	String accountSapNumber;
	String accountName;
	
	int glCodeId;
	String glCodeName;
	
	Date workCompletionDate;
	
	String activity;
	
	String workStatus;
	
	double amount;
	double amountWithTax;
	
	Date exicomInvoiceDate;
	String exicomInvoiceNumber;
	
	String remark;

	String createdByFirstName;
	String createdByLastName;
	String createdByMiddleName;
	Date createdDate;
	String createByEmailId;
	 
	String modifiedByFirstName;
	String modifiedByLastName;
	String modifiedByMiddleName;
	String modifiedByEmailId;
	Date modifiedDate;
	
	
	
	public AnnexureLineItemResponse (AnnexureLineItem annexureLineItem) {
		
		this.id = annexureLineItem.getId();
		this.manualId = annexureLineItem.getManualId();
		
		if(annexureLineItem.getAnnexure()!=null) {
			this.annexureId = annexureLineItem.getAnnexure().getId();
			this.annexureManualId = annexureLineItem.getAnnexure().getManualId();
		}
		
		if(annexureLineItem.getServiceAppointment()!=null) {
			this.serviceAppointmentId = annexureLineItem.getServiceAppointment().getServiceAppointmentId();
			this.serviceAppointmentName = annexureLineItem.getServiceAppointment().getAppointmentNumber();
			if(annexureLineItem.getServiceAppointment().getCircle()!=null) {
				this.circleName=annexureLineItem.getServiceAppointment().getCircle().getCircleName();
				
			}
			
			
			if(annexureLineItem.getServiceAppointment().getCaseDetails()!=null) {
				
				this.caseId=annexureLineItem.getServiceAppointment().getCaseDetails().getCaseId();
				this.caseIdManual=annexureLineItem.getServiceAppointment().getCaseDetails().getManualId();
				
				if(annexureLineItem.getServiceAppointment().getCaseDetails().getServiceCenter()!=null) {
					this.serviceCenterId=annexureLineItem.getServiceAppointment().getCaseDetails().getServiceCenter().getLegalEntityId();
					this.serviceCenterManualId=annexureLineItem.getServiceAppointment().getCaseDetails().getServiceCenter().getManualId();
					this.serviceCenterName=annexureLineItem.getServiceAppointment().getCaseDetails().getServiceCenter().getLegalEntityName();
				}
				
				if(annexureLineItem.getServiceAppointment().getCaseDetails().getSite()!=null) {
					this.siteId=annexureLineItem.getServiceAppointment().getCaseDetails().getSite().getSiteId();
					this.siteIdManual=annexureLineItem.getServiceAppointment().getCaseDetails().getSite().getManualSiteId();
					this.siteName=annexureLineItem.getServiceAppointment().getCaseDetails().getSite().getSiteName();
					
					if(annexureLineItem.getServiceAppointment().getCaseDetails().getSite().getAccount()!=null) {
						this.accountSapNumber=annexureLineItem.getServiceAppointment().getCaseDetails().getSite().getAccount().getSapCustomerNumber();
						this.accountName=annexureLineItem.getServiceAppointment().getCaseDetails().getSite().getAccount().getName();
					}
				}
			}
			
		}
		
		
		if(annexureLineItem.getGlCode()!=null) {
			this.glCodeId=annexureLineItem.getGlCode().getId();
			this.glCodeName=annexureLineItem.getGlCode().getName();
		}
		
		
		this.workCompletionDate=annexureLineItem.getWorkCompletionDate();
		
		this.activity=annexureLineItem.getActivity();
		
		this.workStatus=annexureLineItem.getWorkStatus();
		
		this.amount=annexureLineItem.getAmount();
		this.amountWithTax=annexureLineItem.getAmountWithTax();
		
		this.exicomInvoiceDate=annexureLineItem.getExicomInvoiceDate();
		this.exicomInvoiceNumber=annexureLineItem.getExicomInvoiceNumber();
		
		this.remark=annexureLineItem.getRemark();

		if(annexureLineItem.getCreatedBy()!=null) {
			this.createdByFirstName=annexureLineItem.getCreatedBy().getFirstName();
			this.createdByLastName=annexureLineItem.getCreatedBy().getLastName();
			this.createdByMiddleName = annexureLineItem.getCreatedBy().getMiddleName();
			this.createByEmailId=annexureLineItem.getCreatedBy().getEmailId();
			this.createdDate=annexureLineItem.getCreatedDate();
		}
		
		if(annexureLineItem.getModifiedBy()!=null) {
			this.modifiedByFirstName=annexureLineItem.getModifiedBy().getFirstName();
			this.modifiedByLastName=annexureLineItem.getModifiedBy().getLastName();
			this.modifiedByMiddleName = annexureLineItem.getModifiedBy().getMiddleName();
			this.modifiedByEmailId=annexureLineItem.getModifiedBy().getEmailId();
			this.modifiedDate=annexureLineItem.getModifiedDate();
		}
		
		 
		
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getManualId() {
		return manualId;
	}
	public void setManualId(String manualId) {
		this.manualId = manualId;
	}
	public long getAnnexureId() {
		return annexureId;
	}
	public void setAnnexureId(long annexureId) {
		this.annexureId = annexureId;
	}
	public String getAnnexureManualId() {
		return annexureManualId;
	}
	public void setAnnexureManualId(String annexureManualId) {
		this.annexureManualId = annexureManualId;
	}
	public long getServiceAppointmentId() {
		return serviceAppointmentId;
	}
	public void setServiceAppointmentId(long serviceAppointmentId) {
		this.serviceAppointmentId = serviceAppointmentId;
	}
	public String getServiceAppointmentName() {
		return serviceAppointmentName;
	}
	public void setServiceAppointmentName(String serviceAppointmentName) {
		this.serviceAppointmentName = serviceAppointmentName;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public long getServiceCenterId() {
		return serviceCenterId;
	}
	public void setServiceCenterId(long serviceCenterId) {
		this.serviceCenterId = serviceCenterId;
	}
	public String getServiceCenterManualId() {
		return serviceCenterManualId;
	}
	public void setServiceCenterManualId(String serviceCenterManualId) {
		this.serviceCenterManualId = serviceCenterManualId;
	}
	public String getServiceCenterName() {
		return serviceCenterName;
	}
	public void setServiceCenterName(String serviceCenterName) {
		this.serviceCenterName = serviceCenterName;
	}
	public long getSiteId() {
		return siteId;
	}
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	public String getSiteIdManual() {
		return siteIdManual;
	}
	public void setSiteIdManual(String siteIdManual) {
		this.siteIdManual = siteIdManual;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public long getCaseId() {
		return caseId;
	}
	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}
	public String getCaseIdManual() {
		return caseIdManual;
	}
	public void setCaseIdManual(String caseIdManual) {
		this.caseIdManual = caseIdManual;
	}
	public String getAccountSapNumber() {
		return accountSapNumber;
	}
	public void setAccountSapNumber(String accountSapNumber) {
		this.accountSapNumber = accountSapNumber;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public int getGlCodeId() {
		return glCodeId;
	}
	public void setGlCodeId(int glCodeId) {
		this.glCodeId = glCodeId;
	}
	public String getGlCodeName() {
		return glCodeName;
	}
	public void setGlCodeName(String glCodeName) {
		this.glCodeName = glCodeName;
	}
	public Date getWorkCompletionDate() {
		return workCompletionDate;
	}
	public void setWorkCompletionDate(Date workCompletionDate) {
		this.workCompletionDate = workCompletionDate;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getAmountWithTax() {
		return amountWithTax;
	}
	public void setAmountWithTax(double amountWithTax) {
		this.amountWithTax = amountWithTax;
	}
	public Date getExicomInvoiceDate() {
		return exicomInvoiceDate;
	}
	public void setExicomInvoiceDate(Date exicomInvoiceDate) {
		this.exicomInvoiceDate = exicomInvoiceDate;
	}
	public String getExicomInvoiceNumber() {
		return exicomInvoiceNumber;
	}
	public void setExicomInvoiceNumber(String exicomInvoiceNumber) {
		this.exicomInvoiceNumber = exicomInvoiceNumber;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreateByEmailId() {
		return createByEmailId;
	}
	public void setCreateByEmailId(String createByEmailId) {
		this.createByEmailId = createByEmailId;
	}
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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
	
	
	
	
}
