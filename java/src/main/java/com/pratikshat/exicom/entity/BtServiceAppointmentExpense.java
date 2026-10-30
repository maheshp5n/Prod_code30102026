package com.pratikshat.exicom.entity;

import java.io.Serializable;

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
public class BtServiceAppointmentExpense extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtServiceAppointmentExpense> {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;
    private String manualId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private BtExpenseManagement btExpenseManagement;
   
    @ManyToOne(fetch = FetchType.LAZY)
    private BtServiceAppointment btServiceAppointment; 
    
    private String costCenter;
    
    private double totalClaimAmount;
    
    
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
	 * @return the costCenter
	 */
	public String getCostCenter() {
		return costCenter;
	}

	/**
	 * @param costCenter the costCenter to set
	 */
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	/**
	 * @return the claimAmount
	 */
	public double getTotalClaimAmount() {
		return totalClaimAmount;
	}

	/**
	 * @param claimAmount the claimAmount to set
	 */
	public void setTotalClaimAmount(double totalClaimAmount) {
		this.totalClaimAmount = totalClaimAmount;
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

	@javax.persistence.Transient transient BtServiceAppointmentExpense old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BtServiceAppointmentExpense)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BtServiceAppointmentExpense gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	} 
    
}
