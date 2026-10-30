package com.pratikshat.exicom.model.response;

import java.util.Date;
import com.pratikshat.exicom.entity.TourExpense;

public class TourExpenseResponse {
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

	Date departureDate;
	String departureTime;

	Date arrivalDate;
	String arrivalTime;

	String fromPlace;
	String toPlace;

	boolean companyPaid;
	double fare;
	double approvedAmount;
	double distance;
	String remark;
	double noOfDays;

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
	
	public TourExpenseResponse() {}

	public TourExpenseResponse(TourExpense tourExpense) {
		this.id = tourExpense.getId();
		this.manualId = tourExpense.getManualId();

		if (tourExpense.getExpenseManagement() != null) {
			this.expenseManagementId = tourExpense.getExpenseManagement().getExpenseManagementId();
			this.expenseManagementManualId = tourExpense.getExpenseManagement().getExpenseManagementManualId();
			
			if(tourExpense.getExpenseManagement().getBusinessDomain()!=null) {
				this.businessDomainId = tourExpense.getExpenseManagement().getBusinessDomain().getBusinessDomainId();
				this.businessDomainName = tourExpense.getExpenseManagement().getBusinessDomain().getBusinessDomainName();
			}
		}

		if (tourExpense.getModeOfTravel() != null) {
			this.modeOfTravelId = tourExpense.getModeOfTravel().getId();
			this.modeOfTravelName = tourExpense.getModeOfTravel().getName();
		}

		if (tourExpense.getOwnConveyance() != null) {
			this.ownConveyanceId = tourExpense.getOwnConveyance().getId();
			this.ownConveyanceName = tourExpense.getOwnConveyance().getName();
		}

		this.departureDate = tourExpense.getDepartureDate();
		this.departureTime = tourExpense.getDepartureTime();

		this.arrivalDate = tourExpense.getArrivalDate();
		this.arrivalTime = tourExpense.getArrivalTime();

		this.fromPlace = tourExpense.getFromPlace();
		this.toPlace = tourExpense.getToPlace();

		this.companyPaid = tourExpense.isCompanyPaid();
		this.fare = tourExpense.getFare();
		this.approvedAmount = tourExpense.getApprovedAmount();
		this.distance = tourExpense.getDistance();
		this.remark = tourExpense.getRemark();
		
		this.noOfDays = tourExpense.getNoOfDays();
		this.amountToDisbursed = tourExpense.getAmountToDisbursed();

		if (tourExpense.getCreatedBy() != null) {
			this.createdByEmailId = tourExpense.getCreatedBy().getEmailId();
			this.createdByFirstName = tourExpense.getCreatedBy().getFirstName();
			this.createdByMiddleName = tourExpense.getCreatedBy().getMiddleName();
			this.createdByLastName = tourExpense.getCreatedBy().getLastName();
			this.createDate = tourExpense.getCreateDate();
		}

		if (tourExpense.getModifiedBy() != null) {
			this.modifiedByEmailId = tourExpense.getModifiedBy().getEmailId();
			this.modifiedByFirstName = tourExpense.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = tourExpense.getModifiedBy().getMiddleName();
			this.modifiedByLastName = tourExpense.getModifiedBy().getLastName();
			this.modifiedDate = tourExpense.getModifiedDate();
		}

		if (tourExpense.getOwnerDetails() != null) {
			this.ownerEmailId = tourExpense.getOwnerDetails().getEmailId();
			this.ownerFirstName = tourExpense.getOwnerDetails().getFirstName();
			this.ownerMiddleName = tourExpense.getOwnerDetails().getMiddleName();
			this.ownerLastName = tourExpense.getOwnerDetails().getLastName();
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

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
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

	public boolean isCompanyPaid() {
		return companyPaid;
	}

	public void setCompanyPaid(boolean companyPaid) {
		this.companyPaid = companyPaid;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	public double getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(double approvedAmount) {
		this.approvedAmount = approvedAmount;
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
