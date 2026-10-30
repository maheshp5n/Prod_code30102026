package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.BtBoardingExpense;

public class BtBoardingExpenseResponse {

	long id;
	String manualId;

	long btExpenseManagementId;
	String btExpenseManagementManualId;
	
	int businessDomainId;
	String businessDomainName;

	Date fromDate;
	String fromTime;
	
	Date toDate;
	String toTime;

	String cityCategoryName;
	int cityCategoryId;
	String cityName;

	String otherCity;

	double NoOfDayes;
	double amount;

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
	String ownerLastName;
	String ownerMiddleName;
	
	double amountToDisbursed;

	public BtBoardingExpenseResponse() {}
	
	public BtBoardingExpenseResponse(BtBoardingExpense boardingExpense) {

		this.id = boardingExpense.getId();
		this.manualId = boardingExpense.getManualId();

		if (boardingExpense.getBtExpenseManagement() != null) {
			this.btExpenseManagementId = boardingExpense.getBtExpenseManagement().getId();
			this.btExpenseManagementManualId = boardingExpense.getBtExpenseManagement().getManualId();
			
			if(boardingExpense.getBtExpenseManagement().getBusinessDomain()!=null) {
				this.businessDomainId = boardingExpense.getBtExpenseManagement().getBusinessDomain().getBusinessDomainId();
				this.businessDomainName = boardingExpense.getBtExpenseManagement().getBusinessDomain().getBusinessDomainName();
			}
		}

		if (boardingExpense.getCity() != null) {
			this.cityName = boardingExpense.getCity().getCityName();

			if (boardingExpense.getCity().getCityCatagory() != null) {
				this.cityCategoryName = boardingExpense.getCity().getCityCatagory().getCityCatagoryName();
				this.cityCategoryId = boardingExpense.getCity().getCityCatagory().getCityCategoryId();
			}

		}

		this.fromDate = boardingExpense.getFromDate();
		this.fromTime = boardingExpense.getFromTime();
		this.toDate = boardingExpense.getToDate();
		this.toTime = boardingExpense.getToTime();
		this.otherCity = boardingExpense.getOtherCity();
		this.amount = boardingExpense.getAmount();
		this.remark = boardingExpense.getRemark();
		this.NoOfDayes = boardingExpense.getNoOfDayes();
		this.cityCategory = boardingExpense.getCityCategory();
		this.entitlement = boardingExpense.getEntitlement();
		this.amountToDisbursed = boardingExpense.getAmountToDisbursed();

		if (boardingExpense.getCreatedBy() != null) {
			this.createdByEmailId = boardingExpense.getCreatedBy().getEmailId();
			this.createdByFirstName = boardingExpense.getCreatedBy().getFirstName();
			this.createdByMiddleName = boardingExpense.getCreatedBy().getMiddleName();
			this.createdByLastName = boardingExpense.getCreatedBy().getLastName();
			this.createDate = boardingExpense.getCreatedDate();
		}

		if (boardingExpense.getLastModifiedBy() != null) {
			this.modifiedByEmailId = boardingExpense.getLastModifiedBy().getEmailId();
			this.modifiedByFirstName = boardingExpense.getLastModifiedBy().getFirstName();
			this.modifiedByMiddleName = boardingExpense.getLastModifiedBy().getMiddleName();
			this.modifiedByLastName = boardingExpense.getLastModifiedBy().getLastName();
			this.modifiedDate = boardingExpense.getLastModifiedDate();
		}

		if (boardingExpense.getOwner() != null) {
			this.ownerEmailId = boardingExpense.getOwner().getEmailId();
			this.ownerFirstName = boardingExpense.getOwner().getFirstName();
			this.ownerMiddleName = boardingExpense.getOwner().getMiddleName();
			this.ownerLastName = boardingExpense.getOwner().getLastName();
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

	public long getBtExpenseManagementId() {
		return btExpenseManagementId;
	}

	public void setBtExpenseManagementId(long btExpenseManagementId) {
		this.btExpenseManagementId = btExpenseManagementId;
	}

	public String getBtExpenseManagementManualId() {
		return btExpenseManagementManualId;
	}

	public void setBtExpenseManagementManualId(String btExpenseManagementManualId) {
		this.btExpenseManagementManualId = btExpenseManagementManualId;
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

	public double getNoOfDayes() {
		return NoOfDayes;
	}

	public void setNoOfDayes(double noOfDayes) {
		NoOfDayes = noOfDayes;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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

	public double getAmountToDisbursed() {
		return amountToDisbursed;
	}

	public void setAmountToDisbursed(double amountToDisbursed) {
		this.amountToDisbursed = amountToDisbursed;
	}

	@Override
	public String toString() {
		return "BtBoardingExpenseResponse [id=" + id + ", manualId=" + manualId + ", btExpenseManagementId="
				+ btExpenseManagementId + ", btExpenseManagementManualId=" + btExpenseManagementManualId
				+ ", businessDomainId=" + businessDomainId + ", businessDomainName=" + businessDomainName
				+ ", fromDate=" + fromDate + ", fromTime=" + fromTime + ", toDate=" + toDate + ", toTime=" + toTime
				+ ", cityCategoryName=" + cityCategoryName + ", cityCategoryId=" + cityCategoryId + ", cityName="
				+ cityName + ", otherCity=" + otherCity + ", NoOfDayes=" + NoOfDayes + ", amount=" + amount
				+ ", remark=" + remark + ", cityCategory=" + cityCategory + ", entitlement=" + entitlement
				+ ", createdByEmailId=" + createdByEmailId + ", createdByFirstName=" + createdByFirstName
				+ ", createdByMiddleName=" + createdByMiddleName + ", createdByLastName=" + createdByLastName
				+ ", createDate=" + createDate + ", modifiedByEmailId=" + modifiedByEmailId + ", modifiedByFirstName="
				+ modifiedByFirstName + ", modifiedByMiddleName=" + modifiedByMiddleName + ", modifiedByLastName="
				+ modifiedByLastName + ", modifiedDate=" + modifiedDate + ", ownerEmailId=" + ownerEmailId
				+ ", ownerFirstName=" + ownerFirstName + ", ownerLastName=" + ownerLastName + ", ownerMiddleName="
				+ ownerMiddleName + ", amountToDisbursed=" + amountToDisbursed + "]";
	}

	
}
