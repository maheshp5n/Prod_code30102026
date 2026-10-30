package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@javax.persistence.EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtServiceAppointmentExp extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtServiceAppointmentExp> {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;
    private String ManualId;
    private String costCenter;
    
    
     @OneToOne(fetch = FetchType.LAZY)
     BtServiceAppointment btServiceAppointment;
    
     @ManyToOne(fetch = FetchType.LAZY)
     BtExpenseManagement btExpenseManagement;
    
    
    private double claimAmount;
    
    private double amountToDisbursed;
    
    @Column(length=1000)
	String remark;
	
	boolean isDeleted;

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
	 * @return the btServiceAppointment
	 */
	public BtServiceAppointment getBtServiceAppointment() {
		return btServiceAppointment;
	}

	/**
	 * @param btServiceAppointment the btServiceAppointment to set
	 */
	public void setBtServiceAppointment(BtServiceAppointment btServiceAppointment) {
		this.btServiceAppointment = btServiceAppointment;
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

	
	public double getClaimAmount() {
		return claimAmount;
	}

	/**
	 * @param claimAmount the claimAmount to set
	 */
	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
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
    
   
	@javax.persistence.Transient transient BtServiceAppointmentExp old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BtServiceAppointmentExp)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BtServiceAppointmentExp gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}

	public String getManualId() {
		return ManualId;
	}

	public void setManualId(String manualId) {
		ManualId = manualId;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public double getAmountToDisbursed() {
		return amountToDisbursed;
	}

	public void setAmountToDisbursed(double amountToDisbursed) {
		this.amountToDisbursed = amountToDisbursed;
	}


	
	
}
