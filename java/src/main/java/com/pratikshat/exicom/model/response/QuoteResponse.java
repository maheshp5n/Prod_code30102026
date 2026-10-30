package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.Quote;

public class QuoteResponse {

	long id;
	
	String quoteNumber;
	
	String quoteName;
	
	boolean isApproved;
	int approvalCheck;
	
	long oppertunityId;
	String oppertunityManualId;
	
	String sapCustomerNumber;
	String accountName;
	
	int validityDays;
	Date expiryDate;
	
	int quoteStatusId;
	String quoteStatusName;
	
	boolean isSyncing;
	Date submittedDate;
	Date approvedByStoreDate;
	Date approvedByCommercialDate;
	Date quoteSentDate;
	
	String remark;
	
	double sgstAmount;
	double igstAmount;
	double cgstAmount;
	double subTotal;
	double totalAmountWithTax;
	double discount;
	double totalAmountAfterDiscount;
	
	String billingName;
	String billingStreet;
	String billingCity;
	String billingCountryId;
	String billingCountryName;
	long billingStateId;
	String billingStateName;
	String billingPostalCode;
	
	String shippingName;
	String shippingStreet;
	String shippingCity;
	
	String shippingCountryId;
	String shippingCountryName;
	
	long shippingStateId;
	String shippingStateName;
	String shippingPostalCode;
	
	String otherTermsAndConditions;
	String toEmailIds;
	String ccEmailIds;
	
	
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	String createdByEmailId;
	Date createdDate;
	
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	String modifiedByEmailId;
	Date modifiedDate;
	
	String ownerFirstName;
	String ownerMiddleName;
	String ownerLastName;
	String ownerEmailId;
	int businessDomainId;
	
	private String workOrderNumber;
	private Date workOrderDate;
	
	private long caseId;
	private String caseNumber;

	public QuoteResponse() {}
	
	public QuoteResponse(Quote quote) {
		this.id = quote.getId();
		this.quoteNumber = quote.getQuoteNumber();
		this.quoteName = quote.getQuoteName();
		
		this.isApproved = quote.isApproved();
		this.approvalCheck = quote.getApprovalCheck();
		
		if(quote.getOppertunity()!=null) {
			this.oppertunityId = quote.getOppertunity().getOppertunityId();
			this.oppertunityManualId = quote.getOppertunity().getOppertunityNumber();
			
			if(quote.getOppertunity().getAccounts()!=null) {
				this.sapCustomerNumber = quote.getOppertunity().getAccounts().getSapCustomerNumber();
				this.accountName = quote.getOppertunity().getAccounts().getName();
			}
			
			if(quote.getOppertunity().getCaseDetails()!=null) {
				this.caseId = quote.getOppertunity().getCaseDetails().getCaseId();
				this.caseNumber = quote.getOppertunity().getCaseDetails().getManualId();
			}
		}
		
		this.validityDays = quote.getValidityDays();
		this.expiryDate = quote.getExpiryDate();
		
		if(quote.getQuoteStatus()!=null) {
			this.quoteStatusId = quote.getQuoteStatus().getId();
			this.quoteStatusName = quote.getQuoteStatus().getName();
		}
		
		this.isSyncing = quote.isSyncing();
		this.submittedDate = quote.getSubmittedDate();
		this.approvedByStoreDate = quote.getApprovedByStoreDate();
		this.approvedByCommercialDate = quote.getApprovedByCommercialDate();
		this.quoteSentDate = quote.getQuoteSentDate();
		this.remark = quote.getRemark();
		if(quote.getBusinessDomain()!=null) {
			this.businessDomainId = quote.getBusinessDomain().getBusinessDomainId();	
		}
		
		this.sgstAmount = quote.getSgstAmount();
		this.igstAmount = quote.getIgstAmount();
		this.cgstAmount = quote.getCgstAmount();
		this.subTotal = quote.getSubTotal();
		this.totalAmountWithTax = quote.getTotalAmountWithTax();
		this.discount = quote.getDiscount();
		this.totalAmountAfterDiscount = quote.getTotalAmountAfterDiscount();
		
		this.billingName = quote.getBillingName();
		this.billingStreet = quote.getBillingStreet();
		this.billingCity = quote.getBillingCity();
		if(quote.getBillingCountry()!=null) {
			this.billingCountryId = quote.getBillingCountry().getConuntryId();
			this.billingCountryName = quote.getBillingCountry().getCountryName();
		}
		if(quote.getBillingState()!=null) {
			this.billingStateId = quote.getBillingState().getStateId();
			this.billingStateName = quote.getBillingState().getStateName();
		}
		this.billingPostalCode = quote.getBillingPostalCode();
		
		
		this.shippingName = quote.getShippingName();
		this.shippingStreet = quote.getShippingStreet();
		this.shippingCity = quote.getShippingCity();
		if(quote.getShippingCountry()!=null) {
			this.shippingCountryId = quote.getShippingCountry().getConuntryId();
			this.shippingCountryName = quote.getShippingCountry().getCountryName();
		}
		if(quote.getShippingState()!=null) {
			this.shippingStateId = quote.getShippingState().getStateId();
			this.shippingStateName = quote.getShippingState().getStateName();	
		}
		this.shippingPostalCode = quote.getShippingPostalCode();
		
		this.otherTermsAndConditions = quote.getOtherTermsAndConditions();
		this.toEmailIds = quote.getToEmailIds();
		this.ccEmailIds = quote.getCcEmailIds();
		
		if(quote.getCreatedBy()!=null) {
			this.createdByFirstName = quote.getCreatedBy().getFirstName();
			this.createdByMiddleName = quote.getCreatedBy().getMiddleName();
			this.createdByLastName = quote.getCreatedBy().getLastName();
			this.createdByEmailId = quote.getCreatedBy().getEmailId();
			this.createdDate = quote.getCreatedDate();
		}
		
		if(quote.getModifiedBy()!=null) {
			this.modifiedByFirstName = quote.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = quote.getModifiedBy().getMiddleName();
			this.modifiedByLastName = quote.getModifiedBy().getLastName();
			this.modifiedByEmailId = quote.getModifiedBy().getEmailId();
			this.modifiedDate = quote.getModifiedDate();
		}
		
		if(quote.getOwner()!=null) {
			this.ownerFirstName = quote.getOwner().getFirstName();
			this.ownerMiddleName = quote.getOwner().getMiddleName();
			this.ownerLastName = quote.getOwner().getLastName();
			this.ownerEmailId = quote.getOwner().getEmailId();
		}
		
		this.workOrderDate = quote.getWorkOrderDate();
		this.workOrderNumber = quote.getWorkOrderNumber();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getQuoteNumber() {
		return quoteNumber;
	}
	public void setQuoteNumber(String quoteNumber) {
		this.quoteNumber = quoteNumber;
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
	public String getOppertunityManualId() {
		return oppertunityManualId;
	}
	public void setOppertunityManualId(String oppertunityManualId) {
		this.oppertunityManualId = oppertunityManualId;
	}
	public String getSapCustomerNumber() {
		return sapCustomerNumber;
	}
	public void setSapCustomerNumber(String sapCustomerNumber) {
		this.sapCustomerNumber = sapCustomerNumber;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
	public String getQuoteStatusName() {
		return quoteStatusName;
	}
	public void setQuoteStatusName(String quoteStatusName) {
		this.quoteStatusName = quoteStatusName;
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
	public double getSgstAmount() {
		return sgstAmount;
	}
	public void setSgstAmount(double sgstAmount) {
		this.sgstAmount = sgstAmount;
	}
	public double getIgstAmount() {
		return igstAmount;
	}
	public void setIgstAmount(double igstAmount) {
		this.igstAmount = igstAmount;
	}
	public double getCgstAmount() {
		return cgstAmount;
	}
	public void setCgstAmount(double cgstAmount) {
		this.cgstAmount = cgstAmount;
	}
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	public double getTotalAmountWithTax() {
		return totalAmountWithTax;
	}
	public void setTotalAmountWithTax(double totalAmountWithTax) {
		this.totalAmountWithTax = totalAmountWithTax;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getTotalAmountAfterDiscount() {
		return totalAmountAfterDiscount;
	}
	public void setTotalAmountAfterDiscount(double totalAmountAfterDiscount) {
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
	public String getBillingCountryName() {
		return billingCountryName;
	}
	public void setBillingCountryName(String billingCountryName) {
		this.billingCountryName = billingCountryName;
	}
	public long getBillingStateId() {
		return billingStateId;
	}
	public void setBillingStateId(long billingStateId) {
		this.billingStateId = billingStateId;
	}
	public String getBillingStateName() {
		return billingStateName;
	}
	public void setBillingStateName(String billingStateName) {
		this.billingStateName = billingStateName;
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
	public String getShippingCountryName() {
		return shippingCountryName;
	}
	public void setShippingCountryName(String shippingCountryName) {
		this.shippingCountryName = shippingCountryName;
	}
	public long getShippingStateId() {
		return shippingStateId;
	}
	public void setShippingStateId(long shippingStateId) {
		this.shippingStateId = shippingStateId;
	}
	public String getShippingStateName() {
		return shippingStateName;
	}
	public void setShippingStateName(String shippingStateName) {
		this.shippingStateName = shippingStateName;
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

	public int getBusinessDomainId() {
		return businessDomainId;
	}

	public void setBusinessDomainId(int businessDomainId) {
		this.businessDomainId = businessDomainId;
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

	public String getWorkOrderNumber() {
		return workOrderNumber;
	}

	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}

	public Date getWorkOrderDate() {
		return workOrderDate;
	}

	public void setWorkOrderDate(Date workOrderDate) {
		this.workOrderDate = workOrderDate;
	}

	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	
	
}
