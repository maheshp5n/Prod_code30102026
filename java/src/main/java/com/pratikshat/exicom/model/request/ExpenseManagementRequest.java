package com.pratikshat.exicom.model.request;

import java.util.Date;

public class ExpenseManagementRequest {

	long expenseManagementId;
	
	
	long contactId;
	
	
	Date documentDate;
	
	
	int expensePurposeId;
	
	
	int expenseTypeId;
	
	Date tourStartDate;
	String tourStartTime;
	
	Date tourEndDate;
	String tourEndTime;
	
	
	int costCenterId;
	
	String purposeOfVisit;
	
	
	int statusId;
	
	String remark;
	String narration;
	
	
	int businessAreaId;
	
	
	int businessPlaceId;
	
	boolean isPost;
	
	boolean isExpenseSynced;
	double advanceAmount;
	String visibleToFinance;
	
	boolean isSynced;
	
	int businessDomainID;

	public long getExpenseManagementId() {
		return expenseManagementId;
	}

	public void setExpenseManagementId(long expenseManagementId) {
		this.expenseManagementId = expenseManagementId;
	}

	
	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public int getExpensePurposeId() {
		return expensePurposeId;
	}

	public void setExpensePurposeId(int expensePurposeId) {
		this.expensePurposeId = expensePurposeId;
	}

	public int getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(int expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
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

	public int getCostCenterId() {
		return costCenterId;
	}

	public void setCostCenterId(int costCenterId) {
		this.costCenterId = costCenterId;
	}

	public String getPurposeOfVisit() {
		return purposeOfVisit;
	}

	public void setPurposeOfVisit(String purposeOfVisit) {
		this.purposeOfVisit = purposeOfVisit;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
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

	public int getBusinessAreaId() {
		return businessAreaId;
	}

	public void setBusinessAreaId(int businessAreaId) {
		this.businessAreaId = businessAreaId;
	}

	public int getBusinessPlaceId() {
		return businessPlaceId;
	}

	public void setBusinessPlaceId(int businessPlaceId) {
		this.businessPlaceId = businessPlaceId;
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

	public int getBusinessDomainID() {
		return businessDomainID;
	}

	public void setBusinessDomainID(int businessDomainID) {
		this.businessDomainID = businessDomainID;
	}
	
	
}
