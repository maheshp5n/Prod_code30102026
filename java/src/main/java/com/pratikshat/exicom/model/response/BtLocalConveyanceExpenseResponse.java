package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.BtLocalConveyanceExpense;
import com.pratikshat.exicom.entity.LocalConveyanceExpense;

public class BtLocalConveyanceExpenseResponse {

	long id;
	String manualId;

	long btExpenseManagementId;
	String btExpenseManagementManualId;
	
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
	
	public BtLocalConveyanceExpenseResponse(){}
	
	public BtLocalConveyanceExpenseResponse(BtLocalConveyanceExpense conveyanceExpense) {
		
		this.id = conveyanceExpense.getId();
		this.manualId = conveyanceExpense.getManualId();

		if (conveyanceExpense.getBtExpenseManagement() != null) {
			this.btExpenseManagementId = conveyanceExpense.getBtExpenseManagement().getId();
			this.btExpenseManagementManualId = conveyanceExpense.getBtExpenseManagement().getManualId();
			if(conveyanceExpense.getBtExpenseManagement().getBusinessDomain()!=null) {
				this.businessDomainId = conveyanceExpense.getBtExpenseManagement().getBusinessDomain().getBusinessDomainId();
				this.businessDomainName = conveyanceExpense.getBtExpenseManagement().getBusinessDomain().getBusinessDomainName();
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
			this.createDate = conveyanceExpense.getCreatedDate();
		}

		if (conveyanceExpense.getLastModifiedBy() != null) {
			this.modifiedByEmailId = conveyanceExpense.getLastModifiedBy().getEmailId();
			this.modifiedByFirstName = conveyanceExpense.getLastModifiedBy().getFirstName();
			this.modifiedByMiddleName = conveyanceExpense.getLastModifiedBy().getMiddleName();
			this.modifiedByLastName = conveyanceExpense.getLastModifiedBy().getLastName();
			this.modifiedDate = conveyanceExpense.getLastModifiedDate();
		}

		if (conveyanceExpense.getOwner() != null) {
			this.ownerEmailId = conveyanceExpense.getOwner().getEmailId();
			this.ownerFirstName = conveyanceExpense.getOwner().getFirstName();
			this.ownerMiddleName = conveyanceExpense.getOwner().getMiddleName();
			this.ownerLastName = conveyanceExpense.getOwner().getLastName();
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
