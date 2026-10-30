package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class TourExpense implements java.io.Serializable,OldPresist<TourExpense>{
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long id;
	
	String manualId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	ExpenseManagement expenseManagement;
	
	@ManyToOne(fetch = FetchType.LAZY)
	ModeOfTravel modeOfTravel;
	
	@ManyToOne(fetch = FetchType.LAZY)
	OwnConveyance ownConveyance;
	
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
	double noOfDays;
	@Column(length=1000)
	String remark;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails ownerDetails;
	
	boolean isDeleted;
	
	double amountToDisbursed;

	public TourExpense(long id) {
		this.id = id;
	}

	public TourExpense() {
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

	public ExpenseManagement getExpenseManagement() {
		return expenseManagement;
	}

	public void setExpenseManagement(ExpenseManagement expenseManagement) {
		this.expenseManagement = expenseManagement;
	}

	public ModeOfTravel getModeOfTravel() {
		return modeOfTravel;
	}

	public void setModeOfTravel(ModeOfTravel modeOfTravel) {
		this.modeOfTravel = modeOfTravel;
	}

	public OwnConveyance getOwnConveyance() {
		return ownConveyance;
	}

	public void setOwnConveyance(OwnConveyance ownConveyance) {
		this.ownConveyance = ownConveyance;
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

	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public EmployeeDetails getOwnerDetails() {
		return ownerDetails;
	}

	public void setOwnerDetails(EmployeeDetails ownerDetails) {
		this.ownerDetails = ownerDetails;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
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
	
	
	


	@javax.persistence.Transient transient TourExpense old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(TourExpense)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public TourExpense gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
