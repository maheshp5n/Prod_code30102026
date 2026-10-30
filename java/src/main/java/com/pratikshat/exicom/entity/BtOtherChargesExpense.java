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
public class BtOtherChargesExpense extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtOtherChargesExpense> {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;
    private String manualId; 
    
	@ManyToOne(fetch = FetchType.LAZY)
	BtExpenseManagement btExpenseManagement;
	
	@ManyToOne(fetch = FetchType.LAZY)
	OtherChargesTypes otherChargesType;
	
	Date date;
	String billNo;
	double amount;
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
	 * @param expenseManagement2 the expenseManagement to set
	 */
	public void setBtExpenseManagement(BtExpenseManagement btExpenseManagement) {
		this.btExpenseManagement = btExpenseManagement;
	}

	/**
	 * @return the otherChargesType
	 */
	public OtherChargesTypes getOtherChargesType() {
		return otherChargesType;
	}

	/**
	 * @param otherChargesType the otherChargesType to set
	 */
	public void setOtherChargesType(OtherChargesTypes otherChargesType) {
		this.otherChargesType = otherChargesType;
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
	
	@javax.persistence.Transient transient BtOtherChargesExpense old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BtOtherChargesExpense)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BtOtherChargesExpense gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
