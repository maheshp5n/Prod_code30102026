package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.ExpenseAccountsOnly;
import com.pratikshat.exicom.entity.ExpenseManagement;

public class ExpenseManagementResponse {

	long expenseManagementId;
	String expenseManagementManualId;
	
	long contactId;
	String contactFirstName;
	String contactMiddleName;
	String contactLastName;
	String contactEmployeeCode;
	
	Date documentDate;
	
	int businessDomainId;
	String businessDomainName;
	
	
	long expensePurposeId;
	String expensePurposeName;
	
	
	long expenseTypeId;
	String expenseTypeName;
	
	Date tourStartDate;
	String tourStartTime;
	
	Date tourEndDate;
	String tourEndTime;
	
	
	long costCenterId;
	String costCenterName;
	
	String purposeOfVisit;
	
	
	long statusId;
	String statusName;
	
	String remark;
	String narration;
	
	long businessAreaId;
	String businessAreaName;
	
	long businessPlaceId;
	String businessPlaceName;
	
	boolean isPost;
	
	boolean isExpenseSynced;
	double advanceAmount;
	String visibleToFinance;
	
	double totalTourAmount;
	double totalLodgingAmount;
	double totalBoardingAmount;
	double totalBpAmount;
	double totalLocalConveyanceAmount;
	double totalOtherChargesAmount;
	double totalExpense;
	double totalTourAmountCompanyPaid;
	double totalLodgingCompanyPaid;
	double totalCompanyPaid;
	double netAmountToPay;
	
	double totalTourDays;
	double eligibleTourDays;
	double totalBoardingDays;
	double totalLodgingDays;
	
	String createdByEmailId;
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	Date createdDate;
	
	String modifiedByEmailId;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	Date modifiedDate;
	
	String ownerEmailId;
	String ownerFirstName;
	String ownerMiddleName;
	String ownerLastName;
	
	boolean isSynced;
	float totalAmount;
	
	String gradeName;
	
	public ExpenseManagementResponse() {}

	public ExpenseManagementResponse(ExpenseManagement expenseManagement, ExpenseAccountsOnly expenseAccountsOnly) {
		
		this.expenseManagementId = expenseManagement.getExpenseManagementId();
		this.expenseManagementManualId = expenseManagement.getExpenseManagementManualId();
		
		if(expenseManagement.getContact()!=null) {
			this.contactId = expenseManagement.getContact().getContactId();
			this.contactFirstName = expenseManagement.getContact().getFirstName();
			this.contactMiddleName = expenseManagement.getContact().getMiddleName();
			this.contactLastName = expenseManagement.getContact().getLastName();
			this.contactEmployeeCode = expenseManagement.getContact().getEmployeeCode();
			
			if(expenseManagement.getContact().getGrade()!=null) {
				this.gradeName = expenseManagement.getContact().getGrade().getBoardingName();
			}
		}
		
		if(expenseManagement.getBusinessDomain()!=null) {
			this.businessDomainId = expenseManagement.getBusinessDomain().getBusinessDomainId();
			this.businessDomainName = expenseManagement.getBusinessDomain().getBusinessDomainName();
		}
		
		if(expenseManagement.getExpensePurpose()!=null) {
			this.expensePurposeId = expenseManagement.getExpensePurpose().getExpensePurposeId();
			this.expensePurposeName = expenseManagement.getExpensePurpose().getExpensePurposeName();
		}
		
		if(expenseManagement.getExpenseType()!=null) {
			this.expenseTypeId = expenseManagement.getExpenseType().getExpenseTypeId();
			this.expenseTypeName = expenseManagement.getExpenseType().getExpenseTypeName();
		}
		
		if(expenseManagement.getCostCenter()!=null) {
			this.costCenterId = expenseManagement.getCostCenter().getCostCenterId();
			this.costCenterName = expenseManagement.getCostCenter().getCostCenterName();
		}
		
		if(expenseManagement.getStatus()!=null) {
			this.statusId = expenseManagement.getStatus().getStatusId();
			this.statusName = expenseManagement.getStatus().getStatusName();
		}
		
		if(expenseAccountsOnly!=null) {
			if(expenseAccountsOnly.getBusinessPlace()!=null) {
				this.businessPlaceId = expenseAccountsOnly.getBusinessPlace().getBusinessPlaceId();
				this.businessPlaceName = expenseAccountsOnly.getBusinessPlace().getBusinessPlaceName();
			}
			
			if(expenseAccountsOnly.getBusinessArea()!=null) {
				this.businessAreaId = expenseAccountsOnly.getBusinessArea().getBusinessAreaId();
				this.businessAreaName = expenseAccountsOnly.getBusinessArea().getBusinessAreaName();
			}
			
			this.documentDate= expenseAccountsOnly.getDocumentDate();
			
			this.narration = expenseAccountsOnly.getNarration();
		}
		

		this.tourStartDate = expenseManagement.getTourStartDate();
		this.tourStartTime = expenseManagement.getTourStartTime();
		this.tourEndDate = expenseManagement.getTourEndDate();
		this.tourEndTime = expenseManagement.getTourEndTime();
		
		this.purposeOfVisit = expenseManagement.getPurposeOfVisit();
		this.remark = expenseManagement.getRemark();
		this.isPost = expenseManagement.isPost();
		this.isExpenseSynced = expenseManagement.isExpenseSynced();
		this.advanceAmount = expenseManagement.getAdvanceAmount();
		this.visibleToFinance = expenseManagement.getVisibleToFinance();
		this.isSynced = expenseManagement.isSynced();
		
		
		this.totalTourAmount = expenseManagement.getTotalTourAmount();
		this.totalLodgingAmount = expenseManagement.getTotalLodgingAmount();
		this.totalBoardingAmount = expenseManagement.getTotalBoardingAmount();
		this.totalBpAmount = expenseManagement.getTotalBpAmount();
		this.totalLocalConveyanceAmount = expenseManagement.getTotalLocalConveyanceAmount();
		this.totalOtherChargesAmount = expenseManagement.getTotalOtherChargesAmount();
		this.totalExpense = expenseManagement.getTotalExpense();
		this.totalTourAmountCompanyPaid = expenseManagement.getTotalTourAmountCompanyPaid();
		this.totalLodgingCompanyPaid = expenseManagement.getTotalLodgingCompanyPaid();
		this.totalCompanyPaid = expenseManagement.getTotalCompanyPaid();
		this.netAmountToPay = expenseManagement.getNetAmountToPay();
		
		this.totalTourDays = expenseManagement.getTotalTourDays();
		this.eligibleTourDays = expenseManagement.getEligibleTourDays();
		this.totalBoardingDays = expenseManagement.getTotalBoardingDays();
		this.totalLodgingDays = expenseManagement.getTotalLodgingDays();
		
		if(expenseManagement.getCreatedBy()!=null) {
			this.createdByEmailId = expenseManagement.getCreatedBy().getEmailId();
			this.createdByFirstName = expenseManagement.getCreatedBy().getFirstName();
			this.createdByMiddleName = expenseManagement.getCreatedBy().getMiddleName();
			this.createdByLastName = expenseManagement.getCreatedBy().getLastName();
			this.createdDate = expenseManagement.getCreatedDate();
		}
		
		if(expenseManagement.getModifiedBy()!=null) {
			this.modifiedByEmailId = expenseManagement.getModifiedBy().getEmailId();
			this.modifiedByFirstName = expenseManagement.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = expenseManagement.getModifiedBy().getMiddleName();
			this.modifiedByLastName = expenseManagement.getModifiedBy().getLastName();
			this.modifiedDate = expenseManagement.getModifiedDate();
		}
		
		if(expenseManagement.getOwner()!=null) {
			this.ownerEmailId = expenseManagement.getOwner().getEmailId();
			this.ownerFirstName = expenseManagement.getOwner().getFirstName();
			this.ownerMiddleName = expenseManagement.getOwner().getMiddleName();
			this.ownerLastName = expenseManagement.getOwner().getLastName();
		}
		
	}

	public long getExpenseManagementId() {
		return expenseManagementId;
	}

	public void setExpenseManagementId(long expenseManagementId) {
		this.expenseManagementId = expenseManagementId;
	}

	public String getExpenseManagementManualId() {
		return expenseManagementManualId;
	}

	public void setExpenseManagementManualId(String expenseManagementManualId) {
		this.expenseManagementManualId = expenseManagementManualId;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public long getExpensePurposeId() {
		return expensePurposeId;
	}

	public void setExpensePurposeId(long expensePurposeId) {
		this.expensePurposeId = expensePurposeId;
	}

	public String getExpensePurposeName() {
		return expensePurposeName;
	}

	public void setExpensePurposeName(String expensePurposeName) {
		this.expensePurposeName = expensePurposeName;
	}

	public long getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public String getExpenseTypeName() {
		return expenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}

	public Date getTourStartDate() {
		return tourStartDate;
	}

	public void setTourStartDate(Date tourStartDate) {
		this.tourStartDate = tourStartDate;
	}

	public String getTourStartTime() {
		return tourStartTime;
	}

	public void setTourStartTime(String tourStartTime) {
		this.tourStartTime = tourStartTime;
	}

	public Date getTourEndDate() {
		return tourEndDate;
	}

	public void setTourEndDate(Date tourEndDate) {
		this.tourEndDate = tourEndDate;
	}

	public String getTourEndTime() {
		return tourEndTime;
	}

	public void setTourEndTime(String tourEndTime) {
		this.tourEndTime = tourEndTime;
	}

	public long getCostCenterId() {
		return costCenterId;
	}

	public void setCostCenterId(long costCenterId) {
		this.costCenterId = costCenterId;
	}

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	public String getPurposeOfVisit() {
		return purposeOfVisit;
	}

	public void setPurposeOfVisit(String purposeOfVisit) {
		this.purposeOfVisit = purposeOfVisit;
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public long getBusinessAreaId() {
		return businessAreaId;
	}

	public void setBusinessAreaId(long businessAreaId) {
		this.businessAreaId = businessAreaId;
	}

	public String getBusinessAreaName() {
		return businessAreaName;
	}

	public void setBusinessAreaName(String businessAreaName) {
		this.businessAreaName = businessAreaName;
	}

	public long getBusinessPlaceId() {
		return businessPlaceId;
	}

	public void setBusinessPlaceId(long businessPlaceId) {
		this.businessPlaceId = businessPlaceId;
	}

	public String getBusinessPlaceName() {
		return businessPlaceName;
	}

	public void setBusinessPlaceName(String businessPlaceName) {
		this.businessPlaceName = businessPlaceName;
	}

	public boolean isPost() {
		return isPost;
	}

	public void setPost(boolean isPost) {
		this.isPost = isPost;
	}

	public boolean isExpenseSynced() {
		return isExpenseSynced;
	}

	public void setExpenseSynced(boolean isExpenseSynced) {
		this.isExpenseSynced = isExpenseSynced;
	}

	public double getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public String getVisibleToFinance() {
		return visibleToFinance;
	}

	public void setVisibleToFinance(String visibleToFinance) {
		this.visibleToFinance = visibleToFinance;
	}

	public String getCreatedByEmailId() {
		return createdByEmailId;
	}

	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
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

	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}

	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
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

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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

	public String getOwnerLastName() {
		return ownerLastName;
	}

	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}

	public boolean isSynced() {
		return isSynced;
	}

	public void setSynced(boolean isSynced) {
		this.isSynced = isSynced;
	}

	public long getContactId() {
		return contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
	}

	public String getContactFirstName() {
		return contactFirstName;
	}

	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}

	public String getContactLastName() {
		return contactLastName;
	}

	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	public String getContactEmployeeCode() {
		return contactEmployeeCode;
	}

	public void setContactEmployeeCode(String contactEmployeeCode) {
		this.contactEmployeeCode = contactEmployeeCode;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getBusinessDomainId() {
		return businessDomainId;
	}

	public void setBusinessDomainId(int businessDomainId) {
		this.businessDomainId = businessDomainId;
	}

	public String getBusinessDomainName() {
		return businessDomainName;
	}

	public void setBusinessDomainName(String businessDomainName) {
		this.businessDomainName = businessDomainName;
	}

	public double getTotalTourAmount() {
		return totalTourAmount;
	}

	public void setTotalTourAmount(double totalTourAmount) {
		this.totalTourAmount = totalTourAmount;
	}

	public double getTotalLodgingAmount() {
		return totalLodgingAmount;
	}

	public void setTotalLodgingAmount(double totalLodgingAmount) {
		this.totalLodgingAmount = totalLodgingAmount;
	}

	public double getTotalBoardingAmount() {
		return totalBoardingAmount;
	}

	public void setTotalBoardingAmount(double totalBoardingAmount) {
		this.totalBoardingAmount = totalBoardingAmount;
	}

	public double getTotalBpAmount() {
		return totalBpAmount;
	}

	public void setTotalBpAmount(double totalBpAmount) {
		this.totalBpAmount = totalBpAmount;
	}

	public double getTotalLocalConveyanceAmount() {
		return totalLocalConveyanceAmount;
	}

	public void setTotalLocalConveyanceAmount(double totalLocalConveyanceAmount) {
		this.totalLocalConveyanceAmount = totalLocalConveyanceAmount;
	}

	public double getTotalOtherChargesAmount() {
		return totalOtherChargesAmount;
	}

	public void setTotalOtherChargesAmount(double totalOtherChargesAmount) {
		this.totalOtherChargesAmount = totalOtherChargesAmount;
	}

	public double getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(double totalExpense) {
		this.totalExpense = totalExpense;
	}

	public double getTotalTourAmountCompanyPaid() {
		return totalTourAmountCompanyPaid;
	}

	public void setTotalTourAmountCompanyPaid(double totalTourAmountCompanyPaid) {
		this.totalTourAmountCompanyPaid = totalTourAmountCompanyPaid;
	}

	public double getTotalLodgingCompanyPaid() {
		return totalLodgingCompanyPaid;
	}

	public void setTotalLodgingCompanyPaid(double totalLodgingCompanyPaid) {
		this.totalLodgingCompanyPaid = totalLodgingCompanyPaid;
	}

	public double getTotalCompanyPaid() {
		return totalCompanyPaid;
	}

	public void setTotalCompanyPaid(double totalCompanyPaid) {
		this.totalCompanyPaid = totalCompanyPaid;
	}

	public double getNetAmountToPay() {
		return netAmountToPay;
	}

	public void setNetAmountToPay(double netAmountToPay) {
		this.netAmountToPay = netAmountToPay;
	}

	public double getTotalTourDays() {
		return totalTourDays;
	}

	public void setTotalTourDays(double totalTourDays) {
		this.totalTourDays = totalTourDays;
	}

	public double getEligibleTourDays() {
		return eligibleTourDays;
	}

	public void setEligibleTourDays(double eligibleTourDays) {
		this.eligibleTourDays = eligibleTourDays;
	}

	public double getTotalBoardingDays() {
		return totalBoardingDays;
	}

	public void setTotalBoardingDays(double totalBoardingDays) {
		this.totalBoardingDays = totalBoardingDays;
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

	public double getTotalLodgingDays() {
		return totalLodgingDays;
	}

	public void setTotalLodgingDays(double totalLodgingDays) {
		this.totalLodgingDays = totalLodgingDays;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
	
	
}
