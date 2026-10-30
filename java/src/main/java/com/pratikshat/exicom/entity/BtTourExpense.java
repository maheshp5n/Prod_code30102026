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
public class BtTourExpense extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtTourExpense> {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;
    private String manualId;
    
    @ManyToOne(fetch = FetchType.LAZY)
	BtExpenseManagement btExpenseManagement;
	
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
	
	
	boolean isDeleted;
	
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
	 * @return the modeOfTravel
	 */
	public ModeOfTravel getModeOfTravel() {
		return modeOfTravel;
	}

	/**
	 * @param modeOfTravel the modeOfTravel to set
	 */
	public void setModeOfTravel(ModeOfTravel modeOfTravel) {
		this.modeOfTravel = modeOfTravel;
	}

	/**
	 * @return the ownConveyance
	 */
	public OwnConveyance getOwnConveyance() {
		return ownConveyance;
	}

	/**
	 * @param ownConveyance the ownConveyance to set
	 */
	public void setOwnConveyance(OwnConveyance ownConveyance) {
		this.ownConveyance = ownConveyance;
	}

	/**
	 * @return the departureDate
	 */
	public Date getDepartureDate() {
		return departureDate;
	}

	/**
	 * @param departureDate the departureDate to set
	 */
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	/**
	 * @return the departureTime
	 */
	public String getDepartureTime() {
		return departureTime;
	}

	/**
	 * @param departureTime the departureTime to set
	 */
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	/**
	 * @return the arrivalDate
	 */
	public Date getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * @param arrivalDate the arrivalDate to set
	 */
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @return the arrivalTime
	 */
	public String getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * @param arrivalTime the arrivalTime to set
	 */
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * @return the fromPlace
	 */
	public String getFromPlace() {
		return fromPlace;
	}

	/**
	 * @param fromPlace the fromPlace to set
	 */
	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	/**
	 * @return the toPlace
	 */
	public String getToPlace() {
		return toPlace;
	}

	/**
	 * @param toPlace the toPlace to set
	 */
	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	/**
	 * @return the companyPaid
	 */
	public boolean isCompanyPaid() {
		return companyPaid;
	}

	/**
	 * @param companyPaid the companyPaid to set
	 */
	public void setCompanyPaid(boolean companyPaid) {
		this.companyPaid = companyPaid;
	}

	/**
	 * @return the fare
	 */
	public double getFare() {
		return fare;
	}

	/**
	 * @param fare the fare to set
	 */
	public void setFare(double fare) {
		this.fare = fare;
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
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return the noOfDays
	 */
	public double getNoOfDays() {
		return noOfDays;
	}

	/**
	 * @param noOfDays the noOfDays to set
	 */
	public void setNoOfDays(double noOfDays) {
		this.noOfDays = noOfDays;
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

	@javax.persistence.Transient transient BtTourExpense old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BtTourExpense)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BtTourExpense gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
	
	
}
