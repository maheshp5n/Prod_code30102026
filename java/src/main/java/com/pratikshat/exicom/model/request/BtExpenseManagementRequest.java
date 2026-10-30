package com.pratikshat.exicom.model.request;

import java.util.Date;

public class BtExpenseManagementRequest {
	
	private long id;
	private int expensePurposeId;
	private int expenseTypeId;
	private int costCenterId;
	private String purposeOfVisit;
	private String remark;
	private String narration;
	private int businessAreaId;
	private int businessPlaceId;
	private int businessDomainID;
	
	long contactId;
	Date documentDate;
	boolean isPost;
	boolean isExpenseSynced;
	double advanceAmount;
	String visibleToFinance;
	boolean isSynced;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the expensePurposeId
	 */
	public int getExpensePurposeId() {
		return expensePurposeId;
	}
	/**
	 * @param expensePurposeId the expensePurposeId to set
	 */
	public void setExpensePurposeId(int expensePurposeId) {
		this.expensePurposeId = expensePurposeId;
	}
	/**
	 * @return the expenseTypeId
	 */
	public int getExpenseTypeId() {
		return expenseTypeId;
	}
	/**
	 * @param expenseTypeId the expenseTypeId to set
	 */
	public void setExpenseTypeId(int expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}
	/**
	 * @return the costCenterId
	 */
	public int getCostCenterId() {
		return costCenterId;
	}
	/**
	 * @param costCenterId the costCenterId to set
	 */
	public void setCostCenterId(int costCenterId) {
		this.costCenterId = costCenterId;
	}
	/**
	 * @return the purposeOfVisit
	 */
	public String getPurposeOfVisit() {
		return purposeOfVisit;
	}
	/**
	 * @param purposeOfVisit the purposeOfVisit to set
	 */
	public void setPurposeOfVisit(String purposeOfVisit) {
		this.purposeOfVisit = purposeOfVisit;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the narration
	 */
	public String getNarration() {
		return narration;
	}
	/**
	 * @param narration the narration to set
	 */
	public void setNarration(String narration) {
		this.narration = narration;
	}
	/**
	 * @return the businessAreaId
	 */
	public int getBusinessAreaId() {
		return businessAreaId;
	}
	/**
	 * @param businessAreaId the businessAreaId to set
	 */
	public void setBusinessAreaId(int businessAreaId) {
		this.businessAreaId = businessAreaId;
	}
	/**
	 * @return the businessPlaceId
	 */
	public int getBusinessPlaceId() {
		return businessPlaceId;
	}
	/**
	 * @param businessPlaceId the businessPlaceId to set
	 */
	public void setBusinessPlaceId(int businessPlaceId) {
		this.businessPlaceId = businessPlaceId;
	}
	/**
	 * @return the businessDomainID
	 */
	public int getBusinessDomainID() {
		return businessDomainID;
	}
	/**
	 * @param businessDomainID the businessDomainID to set
	 */
	public void setBusinessDomainID(int businessDomainID) {
		this.businessDomainID = businessDomainID;
	}
	/**
	 * @return the contactId
	 */
	public long getContactId() {
		return contactId;
	}
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	/**
	 * @return the documentDate
	 */
	public Date getDocumentDate() {
		return documentDate;
	}
	/**
	 * @param documentDate the documentDate to set
	 */
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	/**
	 * @return the isPost
	 */
	public boolean isPost() {
		return isPost;
	}
	/**
	 * @param isPost the isPost to set
	 */
	public void setPost(boolean isPost) {
		this.isPost = isPost;
	}
	/**
	 * @return the isExpenseSynced
	 */
	public boolean isExpenseSynced() {
		return isExpenseSynced;
	}
	/**
	 * @param isExpenseSynced the isExpenseSynced to set
	 */
	public void setExpenseSynced(boolean isExpenseSynced) {
		this.isExpenseSynced = isExpenseSynced;
	}
	/**
	 * @return the advanceAmount
	 */
	public double getAdvanceAmount() {
		return advanceAmount;
	}
	/**
	 * @param advanceAmount the advanceAmount to set
	 */
	public void setAdvanceAmount(double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}
	/**
	 * @return the visibleToFinance
	 */
	public String getVisibleToFinance() {
		return visibleToFinance;
	}
	/**
	 * @param visibleToFinance the visibleToFinance to set
	 */
	public void setVisibleToFinance(String visibleToFinance) {
		this.visibleToFinance = visibleToFinance;
	}
	/**
	 * @return the isSynced
	 */
	public boolean isSynced() {
		return isSynced;
	}
	/**
	 * @param isSynced the isSynced to set
	 */
	public void setSynced(boolean isSynced) {
		this.isSynced = isSynced;
	}
	@Override
	public String toString() {
		return "BtExpenseManagementRequest [id=" + id + ", expensePurposeId=" + expensePurposeId + ", expenseTypeId="
				+ expenseTypeId + ", costCenterId=" + costCenterId + ", purposeOfVisit=" + purposeOfVisit + ", remark="
				+ remark + ", narration=" + narration + ", businessAreaId=" + businessAreaId + ", businessPlaceId="
				+ businessPlaceId + ", businessDomainID=" + businessDomainID + ", contactId=" + contactId
				+ ", documentDate=" + documentDate + ", isPost=" + isPost + ", isExpenseSynced=" + isExpenseSynced
				+ ", advanceAmount=" + advanceAmount + ", visibleToFinance=" + visibleToFinance + ", isSynced="
				+ isSynced + "]";
	}
	

	
	
	
	
	
	

}
