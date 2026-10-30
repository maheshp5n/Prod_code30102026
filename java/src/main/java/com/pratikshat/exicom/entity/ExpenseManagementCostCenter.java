package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ExpenseManagementCostCenter implements java.io.Serializable,OldPresist<ExpenseManagementCostCenter>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int costCenterId;
	String costCenterName;
	@ManyToOne(fetch = FetchType.LAZY)
	BusinessDomain division;
	boolean synced;
	public ExpenseManagementCostCenter() {
	
	}
	public ExpenseManagementCostCenter(int costCenterId) {
		this.costCenterId = costCenterId;
	}
	public int getCostCenterId() {
		return costCenterId;
	}
	public void setCostCenterId(int costCenterId) {
		this.costCenterId = costCenterId;
	}
	public String getCostCenterName() {
		return costCenterName;
	}
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
	
	public BusinessDomain getDivision() {
		return division;
	}
	public void setDivision(BusinessDomain division) {
		this.division = division;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	
	

	@javax.persistence.Transient transient ExpenseManagementCostCenter old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ExpenseManagementCostCenter)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ExpenseManagementCostCenter gOld() {
		return old;
	}
	public String toString() {
			return ""+costCenterId;
	}
}
