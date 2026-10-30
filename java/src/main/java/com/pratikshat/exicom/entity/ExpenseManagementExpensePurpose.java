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
public class ExpenseManagementExpensePurpose implements java.io.Serializable,OldPresist<ExpenseManagementExpensePurpose>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int expensePurposeId;
	String expensePurposeName;
	@ManyToOne(fetch = FetchType.LAZY)
	BusinessDomain division;
	boolean synced;
	public ExpenseManagementExpensePurpose() {
		
	}
	public ExpenseManagementExpensePurpose(int expensePurposeId) {
		this.expensePurposeId = expensePurposeId;
	}
	public int getExpensePurposeId() {
		return expensePurposeId;
	}
	public void setExpensePurposeId(int expensePurposeId) {
		this.expensePurposeId = expensePurposeId;
	}
	public String getExpensePurposeName() {
		return expensePurposeName;
	}
	public void setExpensePurposeName(String expensePurposeName) {
		this.expensePurposeName = expensePurposeName;
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
	
	
	

	@javax.persistence.Transient transient ExpenseManagementExpensePurpose old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ExpenseManagementExpensePurpose)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ExpenseManagementExpensePurpose gOld() {
		return old;
	}
	public String toString() {
			return ""+expensePurposeId;
	}
}
