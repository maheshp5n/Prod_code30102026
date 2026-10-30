package com.pratikshat.exicom.model.request;

import java.util.Date;

public class LodgingExpenseRequest {
	
	long id;
	
	long expenseManagementId;
	
	Date fromDate;
	String fromTime;
	
	Date toDate;
	String toTime;
	
	String cityName;
	
	String otherCity;
	
	boolean isSelfArrangement;
	
	boolean isComapnyPaid;
	
	String hotelName;
	String billNo;
	double amount;
	double approvedAmount;
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
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
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
		return "LodgingExpenseRequest [id=" + id + ", expenseManagementId=" + expenseManagementId + ", fromDate="
				+ fromDate + ", fromTime=" + fromTime + ", toDate=" + toDate + ", toTime=" + toTime + ", cityName="
				+ cityName + ", otherCity=" + otherCity + ", isSelfArrangement=" + isSelfArrangement
				+ ", isComapnyPaid=" + isComapnyPaid + ", hotelName=" + hotelName + ", billNo=" + billNo + ", amount="
				+ amount + ", approvedAmount=" + approvedAmount + ", remark=" + remark + ", amountToDisbursed="
				+ amountToDisbursed + "]";
	}
	
	
	
	
}
