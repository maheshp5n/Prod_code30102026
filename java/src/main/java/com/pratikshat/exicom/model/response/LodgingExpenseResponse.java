package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.LodgingExpense;

public class LodgingExpenseResponse {

	long id;
	String manualId;

	long expenseManagementId;
	String expenseManagementManualId;
	
	int businessDomainId;
	String businessDomainName;

	Date fromDate;
	String fromTime;
	
	Date toDate;
	String toTime;
	
	double noOfDays;

	String cityCategoryName;
	int cityCategoryId;
	String cityName;

	String otherCity;

	boolean isSelfArrangement;

	boolean isComapnyPaid;

	String hotelName;
	String billNo;
	double amount;
	double approvedAmount;
	String remark;

	String cityCategory;
	double entitlement;
	
	String createdByEmailId;
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	Date createDate;

	String modifiedByEmailId;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	Date modifiedDate;

	String ownerEmailId;
	String ownerFirstName;
	String ownerMiddleName;
	String ownerLastName;
	
	double amountToDisbursed;

	public LodgingExpenseResponse() {}
	
	public LodgingExpenseResponse(LodgingExpense lodgingExpense) {

		this.id = lodgingExpense.getId();
		this.manualId = lodgingExpense.getManualId();

		if (lodgingExpense.getExpenseManagement() != null) {
			this.expenseManagementId = lodgingExpense.getExpenseManagement().getExpenseManagementId();
			this.expenseManagementManualId = lodgingExpense.getExpenseManagement().getExpenseManagementManualId();
			
			if(lodgingExpense.getExpenseManagement().getBusinessDomain()!=null) {
				this.businessDomainId = lodgingExpense.getExpenseManagement().getBusinessDomain().getBusinessDomainId();
				this.businessDomainName = lodgingExpense.getExpenseManagement().getBusinessDomain().getBusinessDomainName();
			}
		}

		if (lodgingExpense.getCity() != null) {
			this.cityName = lodgingExpense.getCity().getCityName();

			if (lodgingExpense.getCity().getCityCatagory() != null) {
				this.cityCategoryName = lodgingExpense.getCity().getCityCatagory().getCityCatagoryName();
				this.cityCategoryId = lodgingExpense.getCity().getCityCatagory().getCityCategoryId();
			}

		}

		this.fromDate = lodgingExpense.getFromDate();
		this.fromTime = lodgingExpense.getFromTime();
		this.toDate = lodgingExpense.getToDate();
		this.toTime = lodgingExpense.getToTime();
		this.otherCity = lodgingExpense.getOtherCity();
		this.isSelfArrangement = lodgingExpense.isSelfArrangement();
		this.isComapnyPaid = lodgingExpense.isComapnyPaid();
		this.hotelName = lodgingExpense.getHotelName();
		this.billNo = lodgingExpense.getBillNo();
		this.amount = lodgingExpense.getAmount();
		this.approvedAmount = lodgingExpense.getApprovedAmount();
		this.remark = lodgingExpense.getRemark();
		this.cityCategory = lodgingExpense.getCityCategory();
		this.entitlement = lodgingExpense.getEntitlement();
		this.noOfDays = lodgingExpense.getNoOfDayes();
		this.amountToDisbursed = lodgingExpense.getAmountToDisbursed();

		if (lodgingExpense.getCreatedBy() != null) {
			this.createdByEmailId = lodgingExpense.getCreatedBy().getEmailId();
			this.createdByFirstName = lodgingExpense.getCreatedBy().getFirstName();
			this.createdByMiddleName = lodgingExpense.getCreatedBy().getMiddleName();
			this.createdByLastName = lodgingExpense.getCreatedBy().getLastName();
			this.createDate = lodgingExpense.getCreateDate();
		}

		if (lodgingExpense.getModifiedBy() != null) {
			this.modifiedByEmailId = lodgingExpense.getModifiedBy().getEmailId();
			this.modifiedByFirstName = lodgingExpense.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = lodgingExpense.getModifiedBy().getMiddleName();
			this.modifiedByLastName = lodgingExpense.getModifiedBy().getLastName();
			this.modifiedDate = lodgingExpense.getModifiedDate();
		}

		if (lodgingExpense.getOwnerDetails() != null) {
			this.ownerEmailId = lodgingExpense.getOwnerDetails().getEmailId();
			this.ownerFirstName = lodgingExpense.getOwnerDetails().getFirstName();
			this.ownerMiddleName = lodgingExpense.getOwnerDetails().getMiddleName();
			this.ownerLastName = lodgingExpense.getOwnerDetails().getLastName();
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

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getOtherCity() {
		return otherCity;
	}

	public void setOtherCity(String otherCity) {
		this.otherCity = otherCity;
	}

	public boolean isSelfArrangement() {
		return isSelfArrangement;
	}

	public void setSelfArrangement(boolean isSelfArrangement) {
		this.isSelfArrangement = isSelfArrangement;
	}

	public boolean isComapnyPaid() {
		return isComapnyPaid;
	}

	public void setComapnyPaid(boolean isComapnyPaid) {
		this.isComapnyPaid = isComapnyPaid;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(double approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getCityCategoryName() {
		return cityCategoryName;
	}

	public void setCityCategoryName(String cityCategoryName) {
		this.cityCategoryName = cityCategoryName;
	}

	public int getCityCategoryId() {
		return cityCategoryId;
	}

	public void setCityCategoryId(int cityCategoryId) {
		this.cityCategoryId = cityCategoryId;
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

	public String getCityCategory() {
		return cityCategory;
	}

	public void setCityCategory(String cityCategory) {
		this.cityCategory = cityCategory;
	}

	public double getEntitlement() {
		return entitlement;
	}

	public void setEntitlement(double entitlement) {
		this.entitlement = entitlement;
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

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public double getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(double noOfDays) {
		this.noOfDays = noOfDays;
	}

	public double getAmountToDisbursed() {
		return amountToDisbursed;
	}

	public void setAmountToDisbursed(double amountToDisbursed) {
		this.amountToDisbursed = amountToDisbursed;
	}
	
	

}
