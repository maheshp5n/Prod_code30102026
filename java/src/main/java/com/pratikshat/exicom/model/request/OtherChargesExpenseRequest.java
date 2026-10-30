package com.pratikshat.exicom.model.request;

import java.util.Date;

public class OtherChargesExpenseRequest {

	long id;
	long expenseManagementId;
	int otherChargesTypeId;
	
	Date date;
	String billNo;
	double amount;
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
	public int getOtherChargesTypeId() {
		return otherChargesTypeId;
	}
	public void setOtherChargesTypeId(int otherChargesTypeId) {
		this.otherChargesTypeId = otherChargesTypeId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
