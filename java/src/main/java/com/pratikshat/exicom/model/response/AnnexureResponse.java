package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.Annexure;

public class AnnexureResponse {

	long id;
	
	String manualId;
	
	
	String vendorFirstName;
	String vendorLastName;
	String vendorMiddleName;
	String vendorEmailId;
	
	String vendorCode;
	
	long serviceCenterId;
	String serviceCenterName;
	
	long circleManagerContactId;
	String circleManagerFirstName;
	String circleManagerMiddleName;
	String circleManagerLastName;
	String circleManagerEmailId;
	
	String invoiceNumber;
	
	Date invoiceDate;
	Date submittedDate;
	Date approvedByCommercialDate;
	Date approvedByCircleDate;
	boolean isInvoiceReceived;
	long lineItemCount;
	double totalAmount;
	
	String poNumber;
	Date poDate;
	
	int approvalStatusId;
	String approvalStatusName;
	
	String createdByFirstName;
	String createdByLastName;
	String createdByMiddleName;
	String createdByEmailId;
	Date createdDate;
	
	String modifiedByFirstName;
	String modifiedByLastName;
	String modifiedByMiddleName;
	String modifiedByEmailId;
	Date modifiedDate;
	
	String ownerFirstName;
	String ownerLastName;
	String ownerMiddleName;
	String ownerEmailId;
	
	
	
	public AnnexureResponse() {
		
	}



	public AnnexureResponse(Annexure annexure) {
		
		this.id = annexure.getId();
		
		this.manualId = annexure.getManualId();
		
		if(annexure.getVendor()!=null) {
			this.vendorFirstName = annexure.getVendor().getFirstName();
			this.vendorLastName = annexure.getVendor().getLastName();
			this.vendorMiddleName = annexure.getVendor().getMiddleName();
			this.vendorEmailId = annexure.getVendor().getEmailId();
			
			this.vendorCode = annexure.getVendor().getEmployeeCode();
		}
		
		if(annexure.getVendor().getLegalEntity()!=null) {
			this.serviceCenterId = annexure.getVendor().getLegalEntity().getLegalEntityId();
			this.serviceCenterName = annexure.getVendor().getLegalEntity().getLegalEntityName();
		}
		
		if(annexure.getCircle()!=null) {
			if(annexure.getBusinessDomain()!=null) {
				if(annexure.getBusinessDomain().getBusinessDomainId()==1) {
					//Battery
					if(annexure.getCircle().getCircleManagerBattry()!=null) {
						this.circleManagerContactId=annexure.getCircle().getCircleManagerBattry().getContactId();
						this.circleManagerFirstName = annexure.getCircle().getCircleManagerBattry().getFirstName();
						this.circleManagerLastName = annexure.getCircle().getCircleManagerBattry().getLastName();
						this.circleManagerMiddleName = annexure.getCircle().getCircleManagerBattry().getMiddleName();
						this.circleManagerEmailId = annexure.getCircle().getCircleManagerBattry().getEmailId();
					}
				}else if(annexure.getBusinessDomain().getBusinessDomainId()==2) {
					//DCT
					if(annexure.getCircle().getCircleManagerPower()!=null) {
						this.circleManagerContactId=annexure.getCircle().getCircleManagerPower().getContactId();
						this.circleManagerFirstName = annexure.getCircle().getCircleManagerPower().getFirstName();
						this.circleManagerLastName = annexure.getCircle().getCircleManagerPower().getLastName();
						this.circleManagerMiddleName = annexure.getCircle().getCircleManagerPower().getMiddleName();
						this.circleManagerEmailId = annexure.getCircle().getCircleManagerPower().getEmailId();
					}
				}else if(annexure.getBusinessDomain().getBusinessDomainId()==3) {
					//EV
					if(annexure.getCircle().getCircleManagerEv()!=null) {
						this.circleManagerContactId=annexure.getCircle().getCircleManagerEv().getContactId();
						this.circleManagerFirstName = annexure.getCircle().getCircleManagerEv().getFirstName();
						this.circleManagerLastName = annexure.getCircle().getCircleManagerEv().getLastName();
						this.circleManagerMiddleName = annexure.getCircle().getCircleManagerEv().getMiddleName();
						this.circleManagerEmailId = annexure.getCircle().getCircleManagerEv().getEmailId();
					}
				}
			}
		}
		
		
		this.invoiceNumber = annexure.getInvoiceNumber();
		
		this.invoiceDate = annexure.getInvoiceDate();
		this.submittedDate = annexure.getSubmittedDate();
		this.approvedByCommercialDate = annexure.getApprovedByCommercialDate();
		this.approvedByCircleDate = annexure.getApprovedByCircleDate();
		this.isInvoiceReceived = annexure.isInvoiceReceived();
		this.lineItemCount = annexure.getLineItemCount();
		this.totalAmount = annexure.getTotalAmount();
		this.poNumber = annexure.getPoNumber();
		this.poDate = annexure.getPoDate();
		
		if(annexure.getApprovalStatus()!=null) {
			this.approvalStatusId = annexure.getApprovalStatus().getId();
			this.approvalStatusName = annexure.getApprovalStatus().getName();
		}
		
		if(annexure.getCreatedBy()!=null) {
			this.createdByFirstName = annexure.getCreatedBy().getFirstName();
			this.createdByLastName = annexure.getCreatedBy().getLastName();
			this.createdByMiddleName = annexure.getCreatedBy().getMiddleName();
			this.createdByEmailId = annexure.getCreatedBy().getEmailId();
			this.createdDate = annexure.getCreatedDate();
		}
		
		if(annexure.getModifiedBy()!=null) {
			this.modifiedByFirstName = annexure.getModifiedBy().getFirstName();
			this.modifiedByLastName = annexure.getModifiedBy().getLastName();
			this.modifiedByMiddleName = annexure.getModifiedBy().getMiddleName();
			this.modifiedByEmailId = annexure.getModifiedBy().getEmailId();
			this.modifiedDate = annexure.getModifiedDate();
		}
		
		if(annexure.getOwner()!=null) {
			this.ownerFirstName = annexure.getOwner().getFirstName();
			this.ownerLastName = annexure.getOwner().getLastName();
			this.ownerMiddleName = annexure.getOwner().getMiddleName();
			this.ownerEmailId = annexure.getOwner().getEmailId();	
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
	public String getVendorFirstName() {
		return vendorFirstName;
	}
	public void setVendorFirstName(String vendorFirstName) {
		this.vendorFirstName = vendorFirstName;
	}
	public String getVendorLastName() {
		return vendorLastName;
	}
	public void setVendorLastName(String vendorLastName) {
		this.vendorLastName = vendorLastName;
	}
	public String getVendorEmailId() {
		return vendorEmailId;
	}
	public void setVendorEmailId(String vendorEmailId) {
		this.vendorEmailId = vendorEmailId;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public long getServiceCenterId() {
		return serviceCenterId;
	}
	public void setServiceCenterId(long serviceCenterId) {
		this.serviceCenterId = serviceCenterId;
	}
	public String getServiceCenterName() {
		return serviceCenterName;
	}
	public void setServiceCenterName(String serviceCenterName) {
		this.serviceCenterName = serviceCenterName;
	}
	public String getCircleManagerFirstName() {
		return circleManagerFirstName;
	}
	public void setCircleManagerFirstName(String circleManagerFirstName) {
		this.circleManagerFirstName = circleManagerFirstName;
	}
	public String getCircleManagerLastName() {
		return circleManagerLastName;
	}
	public void setCircleManagerLastName(String circleManagerLastName) {
		this.circleManagerLastName = circleManagerLastName;
	}
	public String getCircleManagerEmailId() {
		return circleManagerEmailId;
	}
	public void setCircleManagerEmailId(String circleManagerEmailId) {
		this.circleManagerEmailId = circleManagerEmailId;
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
	public Date getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}
	public Date getApprovedByCommercialDate() {
		return approvedByCommercialDate;
	}
	public void setApprovedByCommercialDate(Date approvedByCommercialDate) {
		this.approvedByCommercialDate = approvedByCommercialDate;
	}
	public Date getApprovedByCircleDate() {
		return approvedByCircleDate;
	}
	public void setApprovedByCircleDate(Date approvedByCircleDate) {
		this.approvedByCircleDate = approvedByCircleDate;
	}
	public boolean isInvoiceReceived() {
		return isInvoiceReceived;
	}
	public void setInvoiceReceived(boolean isInvoiceReceived) {
		this.isInvoiceReceived = isInvoiceReceived;
	}
	public long getLineItemCount() {
		return lineItemCount;
	}
	public void setLineItemCount(long lineItemCount) {
		this.lineItemCount = lineItemCount;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getApprovalStatusId() {
		return approvalStatusId;
	}
	public void setApprovalStatusId(int approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}
	public String getApprovalStatusName() {
		return approvalStatusName;
	}
	public void setApprovalStatusName(String approvalStatusName) {
		this.approvalStatusName = approvalStatusName;
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
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	public String getOwnerFirstName() {
		return ownerFirstName;
	}
	public void setOwnerFirstName(String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}
	public String getOwnerLastName() {
		return ownerLastName;
	}
	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}
	public String getOwnerEmailId() {
		return ownerEmailId;
	}
	public void setOwnerEmailId(String ownerEmailId) {
		this.ownerEmailId = ownerEmailId;
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



	public String getVendorMiddleName() {
		return vendorMiddleName;
	}



	public void setVendorMiddleName(String vendorMiddleName) {
		this.vendorMiddleName = vendorMiddleName;
	}



	public String getCircleManagerMiddleName() {
		return circleManagerMiddleName;
	}



	public void setCircleManagerMiddleName(String circleManagerMiddleName) {
		this.circleManagerMiddleName = circleManagerMiddleName;
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



	public String getOwnerMiddleName() {
		return ownerMiddleName;
	}



	public void setOwnerMiddleName(String ownerMiddleName) {
		this.ownerMiddleName = ownerMiddleName;
	}



	public long getCircleManagerContactId() {
		return circleManagerContactId;
	}



	public void setCircleManagerContactId(long circleManagerContactId) {
		this.circleManagerContactId = circleManagerContactId;
	}
	
	
	
}
