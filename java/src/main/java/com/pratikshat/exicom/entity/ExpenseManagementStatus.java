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
public class ExpenseManagementStatus implements java.io.Serializable,OldPresist<ExpenseManagementStatus>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int statusId;
	String statusName;
	@ManyToOne(fetch = FetchType.LAZY)
	BusinessDomain division;
	boolean synced;
	public ExpenseManagementStatus() {
		
	}
	public ExpenseManagementStatus(int statusId) {
		this.statusId = statusId;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
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
	
	

	@javax.persistence.Transient transient ExpenseManagementStatus old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ExpenseManagementStatus)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ExpenseManagementStatus gOld() {
		return old;
	}
	public String toString() {
			return ""+statusId;
	}
}
