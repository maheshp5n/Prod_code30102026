package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LodgingExpense implements java.io.Serializable,OldPresist<LodgingExpense>{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	long id;
	
	String manualId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	ExpenseManagement expenseManagement;
	
	Date fromDate;
	String fromTime;
	Date toDate;
	String toTime;
	
	double NoOfDayes;
	
	@ManyToOne(fetch = FetchType.LAZY)
	CityDetails city;
	
	String otherCity;
	
	boolean isSelfArrangement;
	
	boolean isComapnyPaid;
	
	String hotelName;
	String billNo;
	double amount;
	double approvedAmount;
	@Column(length=1000)
	String remark;
	
	String cityCategory;
	double entitlement;
	
	boolean isDeleted;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails ownerDetails;
	
	double amountToDisbursed;

	public LodgingExpense() {
		
	}

	public LodgingExpense(long id) {
		this.id = id;
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

	public CityDetails getCity() {
		return city;
	}

	public void setCity(CityDetails city) {
		this.city = city;
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

	public double getNoOfDayes() {
		return NoOfDayes;
	}

	public void setNoOfDayes(double noOfDayes) {
		NoOfDayes = noOfDayes;
	}

	public double getAmountToDisbursed() {
		return amountToDisbursed;
	}

	public void setAmountToDisbursed(double amountToDisbursed) {
		this.amountToDisbursed = amountToDisbursed;
	}
	
	

	@javax.persistence.Transient transient LodgingExpense old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LodgingExpense)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LodgingExpense gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
