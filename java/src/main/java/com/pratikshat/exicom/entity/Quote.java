package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class Quote implements java.io.Serializable,OldPresist<Quote>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	String quoteNumber;
	
	String quoteName;
	
	boolean isApproved;
	int approvalCheck;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Oppertunity oppertunity;
	
	int validityDays;
	Date expiryDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	QuoteStatus quoteStatus;
	
	boolean isSyncing;
	Date submittedDate;
	Date approvedByStoreDate;
	Date approvedByCommercialDate;
	Date quoteSentDate;
	
	@Column(length=1000)
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
	@ManyToOne(fetch = FetchType.LAZY)
	CountryDetails billingCountry;
	@ManyToOne(fetch = FetchType.LAZY)
	StateProvinceDetails billingState;
	String billingPostalCode;
	
	String shippingName;
	String shippingStreet;
	String shippingCity;
	@ManyToOne(fetch = FetchType.LAZY)
	CountryDetails shippingCountry;
	@ManyToOne(fetch = FetchType.LAZY)
	StateProvinceDetails shippingState;
	String shippingPostalCode;
	
	@Column(columnDefinition = "TEXT")
	String otherTermsAndConditions;
	@Column(columnDefinition = "TEXT")
	String toEmailIds;
	@Column(columnDefinition = "TEXT")
	String ccEmailIds;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails owner;
	
	boolean isDeleted;
	
	@ManyToOne
	BusinessDomain businessDomain;
	
	String workOrderNumber;
	Date workOrderDate;
	
	public Quote() {
	}

	public Quote(long id) {
		this.id = id;
	}

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

	public Oppertunity getOppertunity() {
		return oppertunity;
	}

	public void setOppertunity(Oppertunity oppertunity) {
		this.oppertunity = oppertunity;
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

	public CountryDetails getBillingCountry() {
		return billingCountry;
	}

	public void setBillingCountry(CountryDetails billingCountry) {
		this.billingCountry = billingCountry;
	}

	public StateProvinceDetails getBillingState() {
		return billingState;
	}

	public void setBillingState(StateProvinceDetails billingState) {
		this.billingState = billingState;
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

	public CountryDetails getShippingCountry() {
		return shippingCountry;
	}

	public void setShippingCountry(CountryDetails shippingCountry) {
		this.shippingCountry = shippingCountry;
	}

	public StateProvinceDetails getShippingState() {
		return shippingState;
	}

	public void setShippingState(StateProvinceDetails shippingState) {
		this.shippingState = shippingState;
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

	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public QuoteStatus getQuoteStatus() {
		return quoteStatus;
	}

	public void setQuoteStatus(QuoteStatus quoteStatus) {
		this.quoteStatus = quoteStatus;
	}

	public EmployeeDetails getOwner() {
		return owner;
	}

	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}

	public String getQuoteNumber() {
		return quoteNumber;
	}

	public void setQuoteNumber(String quoteNumber) {
		this.quoteNumber = quoteNumber;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
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
	
	
	

	@javax.persistence.Transient transient Quote old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(Quote)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public Quote gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
