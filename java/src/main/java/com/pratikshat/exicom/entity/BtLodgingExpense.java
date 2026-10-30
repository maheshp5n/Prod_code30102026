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

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@javax.persistence.EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtLodgingExpense extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtLodgingExpense> {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;
    private String manualId;
    
    @ManyToOne(fetch = FetchType.LAZY)
	BtExpenseManagement btExpenseManagement;
	
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
	
	
	

	double amountToDisbursed;

	public BtLodgingExpense() {
		
	}

	public BtLodgingExpense(long id) {
		this.id = id;
	}

	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the owner
	 */
	public EmployeeDetails getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}

	/**
	 * @return the manualId
	 */
	public String getManualId() {
		return manualId;
	}

	/**
	 * @param manualId the manualId to set
	 */
	public void setManualId(String manualId) {
		this.manualId = manualId;
	}

	/**
	 * @return the expenseManagement
	 */
	public BtExpenseManagement getBtExpenseManagement() {
		return btExpenseManagement;
	}

	/**
	 * @param expenseManagement the expenseManagement to set
	 */
	public void setBtExpenseManagement(BtExpenseManagement btExpenseManagement) {
		this.btExpenseManagement = btExpenseManagement;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the fromTime
	 */
	public String getFromTime() {
		return fromTime;
	}

	/**
	 * @param fromTime the fromTime to set
	 */
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the toTime
	 */
	public String getToTime() {
		return toTime;
	}

	/**
	 * @param toTime the toTime to set
	 */
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	/**
	 * @return the noOfDayes
	 */
	public double getNoOfDayes() {
		return NoOfDayes;
	}

	/**
	 * @param noOfDayes the noOfDayes to set
	 */
	public void setNoOfDayes(double noOfDayes) {
		NoOfDayes = noOfDayes;
	}

	/**
	 * @return the city
	 */
	public CityDetails getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(CityDetails city) {
		this.city = city;
	}

	/**
	 * @return the otherCity
	 */
	public String getOtherCity() {
		return otherCity;
	}

	/**
	 * @param otherCity the otherCity to set
	 */
	public void setOtherCity(String otherCity) {
		this.otherCity = otherCity;
	}

	/**
	 * @return the isSelfArrangement
	 */
	public boolean isSelfArrangement() {
		return isSelfArrangement;
	}

	/**
	 * @param isSelfArrangement the isSelfArrangement to set
	 */
	public void setSelfArrangement(boolean isSelfArrangement) {
		this.isSelfArrangement = isSelfArrangement;
	}

	/**
	 * @return the isComapnyPaid
	 */
	public boolean isComapnyPaid() {
		return isComapnyPaid;
	}

	/**
	 * @param isComapnyPaid the isComapnyPaid to set
	 */
	public void setComapnyPaid(boolean isComapnyPaid) {
		this.isComapnyPaid = isComapnyPaid;
	}

	/**
	 * @return the hotelName
	 */
	public String getHotelName() {
		return hotelName;
	}

	/**
	 * @param hotelName the hotelName to set
	 */
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	/**
	 * @return the billNo
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * @param billNo the billNo to set
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the approvedAmount
	 */
	public double getApprovedAmount() {
		return approvedAmount;
	}

	/**
	 * @param approvedAmount the approvedAmount to set
	 */
	public void setApprovedAmount(double approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the cityCategory
	 */
	public String getCityCategory() {
		return cityCategory;
	}

	/**
	 * @param cityCategory the cityCategory to set
	 */
	public void setCityCategory(String cityCategory) {
		this.cityCategory = cityCategory;
	}

	/**
	 * @return the entitlement
	 */
	public double getEntitlement() {
		return entitlement;
	}

	/**
	 * @param entitlement the entitlement to set
	 */
	public void setEntitlement(double entitlement) {
		this.entitlement = entitlement;
	}

	/**
	 * @return the isDeleted
	 */
	public boolean isDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the amountToDisbursed
	 */
	public double getAmountToDisbursed() {
		return amountToDisbursed;
	}

	/**
	 * @param amountToDisbursed the amountToDisbursed to set
	 */
	public void setAmountToDisbursed(double amountToDisbursed) {
		this.amountToDisbursed = amountToDisbursed;
	}

	@javax.persistence.Transient transient BtLodgingExpense old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BtLodgingExpense)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BtLodgingExpense gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}

	

	
	
	
	
    
}
