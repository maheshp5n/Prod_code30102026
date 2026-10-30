package com.pratikshat.exicom.model.request;

import java.util.Date;

public class QuoteRequest {

	long id;
	
	String quoteName;
	
	boolean isApproved;
	int approvalCheck;
	
	long oppertunityId;
	
	int validityDays;
	Date expiryDate;
	
	int quoteStatusId;
	
	boolean isSyncing;
	Date submittedDate;
	Date approvedByStoreDate;
	Date approvedByCommercialDate;
	Date quoteSentDate;
	
	String remark;
	
	float sgst;
	float igst;
	float cgst;
	float sgstAmount;
	float igstAmount;
	float cgstAmount;
	float visitCharge;
	float subTotal;
	float totalAmountWithTax;
	float discount;
	float totalAmountAfterDiscount;
	
	String billingName;
	String billingStreet;
	String billingCity;
	
	String billingCountryId;
	long billingStateId;
	String billingPostalCode;
	
	String shippingName;
	String shippingStreet;
	String shippingCity;
	
	String shippingCountryId;
	long shippingStateId;
	String shippingPostalCode;
	
	String otherTermsAndConditions;
	String toEmailIds;
	String ccEmailIds;
	int businessDomainId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getQuoteName() {
		return quoteName;
	}
	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	public int getApprovalCheck() {
		return approvalCheck;
	}
	public void setApprovalCheck(int approvalCheck) {
		this.approvalCheck = approvalCheck;
	}
	public long getOppertunityId() {
		return oppertunityId;
	}
	public void setOppertunityId(long oppertunityId) {
		this.oppertunityId = oppertunityId;
	}
	public int getValidityDays() {
		return validityDays;
	}
	public void setValidityDays(int validityDays) {
		this.validityDays = validityDays;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public int getQuoteStatusId() {
		return quoteStatusId;
	}
	public void setQuoteStatusId(int quoteStatusId) {
		this.quoteStatusId = quoteStatusId;
	}
	public boolean isSyncing() {
		return isSyncing;
	}
	public void setSyncing(boolean isSyncing) {
		this.isSyncing = isSyncing;
	}
	public Date getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}
	public Date getApprovedByStoreDate() {
		return approvedByStoreDate;
	}
	public void setApprovedByStoreDate(Date approvedByStoreDate) {
		this.approvedByStoreDate = approvedByStoreDate;
	}
	public Date getApprovedByCommercialDate() {
		return approvedByCommercialDate;
	}
	public void setApprovedByCommercialDate(Date approvedByCommercialDate) {
		this.approvedByCommercialDate = approvedByCommercialDate;
	}
	public Date getQuoteSentDate() {
		return quoteSentDate;
	}
	public void setQuoteSentDate(Date quoteSentDate) {
		this.quoteSentDate = quoteSentDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public float getSgst() {
		return sgst;
	}
	public void setSgst(float sgst) {
		this.sgst = sgst;
	}
	public float getIgst() {
		return igst;
	}
	public void setIgst(float igst) {
		this.igst = igst;
	}
	public float getCgst() {
		return cgst;
	}
	public void setCgst(float cgst) {
		this.cgst = cgst;
	}
	public float getSgstAmount() {
		return sgstAmount;
	}
	public void setSgstAmount(float sgstAmount) {
		this.sgstAmount = sgstAmount;
	}
	public float getIgstAmount() {
		return igstAmount;
	}
	public void setIgstAmount(float igstAmount) {
		this.igstAmount = igstAmount;
	}
	public float getCgstAmount() {
		return cgstAmount;
	}
	public void setCgstAmount(float cgstAmount) {
		this.cgstAmount = cgstAmount;
	}
	public float getVisitCharge() {
		return visitCharge;
	}
	public void setVisitCharge(float visitCharge) {
		this.visitCharge = visitCharge;
	}
	public float getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(float subTotal) {
		this.subTotal = subTotal;
	}
	public float getTotalAmountWithTax() {
		return totalAmountWithTax;
	}
	public void setTotalAmountWithTax(float totalAmountWithTax) {
		this.totalAmountWithTax = totalAmountWithTax;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public float getTotalAmountAfterDiscount() {
		return totalAmountAfterDiscount;
	}
	public void setTotalAmountAfterDiscount(float totalAmountAfterDiscount) {
		this.totalAmountAfterDiscount = totalAmountAfterDiscount;
	}
	public String getBillingName() {
		return billingName;
	}
	public void setBillingName(String billingName) {
		this.billingName = billingName;
	}
	public String getBillingStreet() {
		return billingStreet;
	}
	public void setBillingStreet(String billingStreet) {
		this.billingStreet = billingStreet;
	}
	public String getBillingCity() {
		return billingCity;
	}
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	public String getBillingCountryId() {
		return billingCountryId;
	}
	public void setBillingCountryId(String billingCountryId) {
		this.billingCountryId = billingCountryId;
	}
	public long getBillingStateId() {
		return billingStateId;
	}
	public void setBillingStateId(long billingStateId) {
		this.billingStateId = billingStateId;
	}
	public String getBillingPostalCode() {
		return billingPostalCode;
	}
	public void setBillingPostalCode(String billingPostalCode) {
		this.billingPostalCode = billingPostalCode;
	}
	public String getShippingName() {
		return shippingName;
	}
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	public String getShippingStreet() {
		return shippingStreet;
	}
	public void setShippingStreet(String shippingStreet) {
		this.shippingStreet = shippingStreet;
	}
	public String getShippingCity() {
		return shippingCity;
	}
	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}
	public String getShippingCountryId() {
		return shippingCountryId;
	}
	public void setShippingCountryId(String shippingCountryId) {
		this.shippingCountryId = shippingCountryId;
	}
	public long getShippingStateId() {
		return shippingStateId;
	}
	public void setShippingStateId(long shippingStateId) {
		this.shippingStateId = shippingStateId;
	}
	public String getShippingPostalCode() {
		return shippingPostalCode;
	}
	public void setShippingPostalCode(String shippingPostalCode) {
		this.shippingPostalCode = shippingPostalCode;
	}
	public String getOtherTermsAndConditions() {
		return otherTermsAndConditions;
	}
	public void setOtherTermsAndConditions(String otherTermsAndConditions) {
		this.otherTermsAndConditions = otherTermsAndConditions;
	}
	public String getToEmailIds() {
		return toEmailIds;
	}
	public void setToEmailIds(String toEmailIds) {
		this.toEmailIds = toEmailIds;
	}
	public String getCcEmailIds() {
		return ccEmailIds;
	}
	public void setCcEmailIds(String ccEmailIds) {
		this.ccEmailIds = ccEmailIds;
	}
	public int getBusinessDomainId() {
		return businessDomainId;
	}
	public void setBusinessDomain(int businessDomainId) {
		this.businessDomainId = businessDomainId;
	}
	
	
}
