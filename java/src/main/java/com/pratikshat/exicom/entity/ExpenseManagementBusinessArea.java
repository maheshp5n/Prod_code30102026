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
public class ExpenseManagementBusinessArea implements java.io.Serializable,OldPresist<ExpenseManagementBusinessArea>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int BusinessAreaId;
	String BusinessAreaName;
	@ManyToOne(fetch = FetchType.LAZY)
	BusinessDomain division;
	boolean synced;
	public ExpenseManagementBusinessArea() {
		
	}
	public ExpenseManagementBusinessArea(int businessAreaId) {
		this.BusinessAreaId = businessAreaId;
	}
	public int getBusinessAreaId() {
		return BusinessAreaId;
	}
	public void setBusinessAreaId(int businessAreaId) {
		this.BusinessAreaId = businessAreaId;
	}
	public String getBusinessAreaName() {
		return BusinessAreaName;
	}
	public void setBusinessAreaName(String businessAreaName) {
		this.BusinessAreaName = businessAreaName;
	}
	
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	public BusinessDomain getDivision() {
		return division;
	}
	public void setDivision(BusinessDomain division) {
		this.division = division;
	}
	
	

	@javax.persistence.Transient transient ExpenseManagementBusinessArea old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ExpenseManagementBusinessArea)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ExpenseManagementBusinessArea gOld() {
		return old;
	}
	public String toString() {
			return ""+BusinessAreaId;
	}
}
