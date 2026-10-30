package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LocalConveyanceExpense implements java.io.Serializable,OldPresist<LocalConveyanceExpense>{

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
	
	Date date;
	
	String fromPlace;
	String toPlace;
	
	double amount;
	double distance;
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

	public LocalConveyanceExpense() {
		
	}

	public LocalConveyanceExpense(long id) {
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

	public double getAmountToDisbursed() {
		return amountToDisbursed;
	}

	public void setAmountToDisbursed(double amountToDisbursed) {
		this.amountToDisbursed = amountToDisbursed;
	}
	
	
	

	@javax.persistence.Transient transient LocalConveyanceExpense old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LocalConveyanceExpense)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LocalConveyanceExpense gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
