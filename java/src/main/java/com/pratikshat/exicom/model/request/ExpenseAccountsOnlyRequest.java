package com.pratikshat.exicom.model.request;

import java.util.Date;

public class ExpenseAccountsOnlyRequest {

	long expenseManagementId;
	
	int businessAreaId;
	int businessPlaceId;
	
	Date documentDate;
	
	boolean postPark;
	
	String narration;
	
	public long getExpenseManagementId() {
		return expenseManagementId;
	}
	public void setExpenseManagementId(long expenseManagementId) {
		this.expenseManagementId = expenseManagementId;
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
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	public boolean isPostPark() {
		return postPark;
	}
	public void setPostPark(boolean postPark) {
		this.postPark = postPark;
	}
	
	
}
