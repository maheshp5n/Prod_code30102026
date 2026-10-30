package com.pratikshat.exicom.model.request;

import java.util.Date;

public class LocalConveyanceExpenseRequest {

	long id;
	long expenseManagementId;
	
	int modeOfTravelId;
	int ownConveyanceId;
	
	Date date;
	
	String fromPlace;
	String toPlace;
	
	double amount;
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
	public double getAmountToDisbursed() {
		return amountToDisbursed;
	}
	public void setAmountToDisbursed(double amountToDisbursed) {
		this.amountToDisbursed = amountToDisbursed;
	}
	
	
	
}
