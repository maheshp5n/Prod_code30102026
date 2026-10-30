package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.LocalConveyanceExpense;

public class LocalConveyanceExpenseResponse {

	long id;
	String manualId;

	long expenseManagementId;
	String expenseManagementManualId;
	
	int businessDomainId;
	String businessDomainName;

	int modeOfTravelId;
	String modeOfTravelName;

	int ownConveyanceId;
	String ownConveyanceName;
	
	Date date;
	
	String fromPlace;
	String toPlace;
	
	double amount;
	double distance;
	String remark;
	
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
	
	public LocalConveyanceExpenseResponse(){}
	
	public LocalConveyanceExpenseResponse(LocalConveyanceExpense conveyanceExpense) {
		
		this.id = conveyanceExpense.getId();
		this.manualId = conveyanceExpense.getManualId();

		if (conveyanceExpense.getExpenseManagement() != null) {
			this.expenseManagementId = conveyanceExpense.getExpenseManagement().getExpenseManagementId();
			this.expenseManagementManualId = conveyanceExpense.getExpenseManagement().getExpenseManagementManualId();
			if(conveyanceExpense.getExpenseManagement().getBusinessDomain()!=null) {
				this.businessDomainId = conveyanceExpense.getExpenseManagement().getBusinessDomain().getBusinessDomainId();
				this.businessDomainName = conveyanceExpense.getExpenseManagement().getBusinessDomain().getBusinessDomainName();
			}
		}

		if (conveyanceExpense.getModeOfTravel() != null) {
			this.modeOfTravelId = conveyanceExpense.getModeOfTravel().getId();
			this.modeOfTravelName = conveyanceExpense.getModeOfTravel().getName();
		}

		if (conveyanceExpense.getOwnConveyance() != null) {
			this.ownConveyanceId = conveyanceExpense.getOwnConveyance().getId();
			this.ownConveyanceName = conveyanceExpense.getOwnConveyance().getName();
		}

		this.date = conveyanceExpense.getDate();
		
		this.fromPlace = conveyanceExpense.getFromPlace();
		this.toPlace = conveyanceExpense.getToPlace();
		
		this.amount = conveyanceExpense.getAmount();
		this.distance = conveyanceExpense.getDistance();
		this.remark = conveyanceExpense.getRemark();
		this.amountToDisbursed = conveyanceExpense.getAmountToDisbursed();

		if (conveyanceExpense.getCreatedBy() != null) {
			this.createdByEmailId = conveyanceExpense.getCreatedBy().getEmailId();
			this.createdByFirstName = conveyanceExpense.getCreatedBy().getFirstName();
			this.createdByMiddleName = conveyanceExpense.getCreatedBy().getMiddleName();
			this.createdByLastName = conveyanceExpense.getCreatedBy().getLastName();
			this.createDate = conveyanceExpense.getCreateDate();
		}

		if (conveyanceExpense.getModifiedBy() != null) {
			this.modifiedByEmailId = conveyanceExpense.getModifiedBy().getEmailId();
			this.modifiedByFirstName = conveyanceExpense.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = conveyanceExpense.getModifiedBy().getMiddleName();
			this.modifiedByLastName = conveyanceExpense.getModifiedBy().getLastName();
			this.modifiedDate = conveyanceExpense.getModifiedDate();
		}

		if (conveyanceExpense.getOwnerDetails() != null) {
			this.ownerEmailId = conveyanceExpense.getOwnerDetails().getEmailId();
			this.ownerFirstName = conveyanceExpense.getOwnerDetails().getFirstName();
			this.ownerMiddleName = conveyanceExpense.getOwnerDetails().getMiddleName();
			this.ownerLastName = conveyanceExpense.getOwnerDetails().getLastName();
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


	public int getModeOfTravelId() {
		return modeOfTravelId;
	}


	public void setModeOfTravelId(int modeOfTravelId) {
		this.modeOfTravelId = modeOfTravelId;
	}


	public String getModeOfTravelName() {
		return modeOfTravelName;
	}


	public void setModeOfTravelName(String modeOfTravelName) {
		this.modeOfTravelName = modeOfTravelName;
	}


	public int getOwnConveyanceId() {
		return ownConveyanceId;
	}


	public void setOwnConveyanceId(int ownConveyanceId) {
		this.ownConveyanceId = ownConveyanceId;
	}


	public String getOwnConveyanceName() {
		return ownConveyanceName;
	}


	public void setOwnConveyanceName(String ownConveyanceName) {
		this.ownConveyanceName = ownConveyanceName;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getFromPlace() {
		return fromPlace;
	}


	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}


	public String getToPlace() {
		return toPlace;
	}


	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public double getDistance() {
		return distance;
	}


	public void setDistance(double distance) {
		this.distance = distance;
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

	public double getAmountToDisbursed() {
		return amountToDisbursed;
	}

	public void setAmountToDisbursed(double amountToDisbursed) {
		this.amountToDisbursed = amountToDisbursed;
	}
	
	
	
}
