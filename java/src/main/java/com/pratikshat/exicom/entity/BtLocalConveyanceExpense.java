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
public class BtLocalConveyanceExpense extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtLocalConveyanceExpense> {

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
	
	Date date;
	
	String fromPlace;
	String toPlace;
	
	double amount;
	double distance;
	@Column(length=1000)
	String remark;
	
	boolean isDeleted;
	
	double amountToDisbursed;
	
public BtLocalConveyanceExpense() {
		
	}

	public BtLocalConveyanceExpense(Long id) {
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
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
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
	
	@javax.persistence.Transient transient BtLocalConveyanceExpense old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BtLocalConveyanceExpense)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BtLocalConveyanceExpense gOld() {
		return old;
	}
	@Override
	public String toString() {
		return ""+id;
	}
}
