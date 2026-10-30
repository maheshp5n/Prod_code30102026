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
public class BtBoardingExpense extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtBoardingExpense> {

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
	
	@ManyToOne(fetch = FetchType.LAZY)
	CityDetails city;
	
	String otherCity;
	
	double NoOfDayes;
	double amount;
	@Column(length=1000)
	String remark;
	
	boolean isDeleted;
	
	String cityCategory;
	double entitlement;
	
	double amountToDisbursed;

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
	 * @return the btExpenseManagement
	 */
	public BtExpenseManagement getBtExpenseManagement() {
		return btExpenseManagement;
	}

	/**
	 * @param btExpenseManagement the btExpenseManagement to set
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
	 * @return the amountToDisbursed
	 */
	public double getAmountToDisbursed() {
		return amountToDisbursed;
	}

	
	@javax.persistence.Transient transient BtBoardingExpense old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BtBoardingExpense)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BtBoardingExpense gOld() {
		return old;
	}
	/**
	 * @param amountToDisbursed the amountToDisbursed to set
	 */
	public void setAmountToDisbursed(double amountToDisbursed) {
		this.amountToDisbursed = amountToDisbursed;
	}

	@Override
	public String toString() {
		return ""+id;
	}

	
	
	

}