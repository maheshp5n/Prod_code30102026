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
public class ExpenseManagementExpenseType implements java.io.Serializable,OldPresist<ExpenseManagementExpenseType>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int expenseTypeId;
	String expenseTypeName;
	@ManyToOne(fetch = FetchType.LAZY)
	BusinessDomain division;
	boolean synced;
	public ExpenseManagementExpenseType() {
		
	}
	public ExpenseManagementExpenseType(int expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}
	public int getExpenseTypeId() {
		return expenseTypeId;
	}
	public void setExpenseTypeId(int expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}
	public String getExpenseTypeName() {
		return expenseTypeName;
	}
	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
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
	
	

	@javax.persistence.Transient transient ExpenseManagementExpenseType old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ExpenseManagementExpenseType)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ExpenseManagementExpenseType gOld() {
		return old;
	}
	public String toString() {
			return ""+expenseTypeId;
	}
}
