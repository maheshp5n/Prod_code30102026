package com.pratikshat.exicom.model.request;

import java.util.Date;



public class TourExpenseRequest {
	long id;
	long expenseManagementId;
	int modeOfTravelId;
	int ownConveyanceId;
	
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
	
	double amountToDisbursed;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getExpenseManagementId() {
		return expenseManagementId;
	}
	public void setExpenseManagementId(long expenseManagementId) {
		this.expenseManagementId = expenseManagementId;
	}
	public int getModeOfTravelId() {
		return modeOfTravelId;
	}
	public void setModeOfTravelId(int modeOfTravelId) {
		this.modeOfTravelId = modeOfTravelId;
	}
	public int getOwnConveyanceId() {
		return ownConveyanceId;
	}
	public void setOwnConveyanceId(int ownConveyanceId) {
		this.ownConveyanceId = ownConveyanceId;
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
	public double getAmountToDisbursed() {
		return amountToDisbursed;
	}
	public void setAmountToDisbursed(double amountToDisbursed) {
		this.amountToDisbursed = amountToDisbursed;
	}
	
}
