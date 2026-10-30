package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ExpenseAccountsOnly implements java.io.Serializable,OldPresist<ExpenseAccountsOnly>{

	@Id
	@OneToOne(fetch=FetchType.LAZY)
	ExpenseManagement expense;
	
	Date documentDate;
	
	@Column(length=500)
	String narration;
	
	@ManyToOne(fetch=FetchType.EAGER)
	ExpenseManagementBusinessArea businessArea;
	
	@ManyToOne(fetch=FetchType.EAGER)
	ExpenseManagementBusinessPlace businessPlace;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails lastModifiedBy;
	
	Date createdDate;
	Date lastModifiedDate;
	
	boolean postPark=false;
	
	public ExpenseManagement getExpense() {
		return expense;
	}
	public void setExpense(ExpenseManagement expense) {
		this.expense = expense;
	}
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	public ExpenseManagementBusinessArea getBusinessArea() {
		return businessArea;
	}
	public void setBusinessArea(ExpenseManagementBusinessArea businessArea) {
		this.businessArea = businessArea;
	}
	public ExpenseManagementBusinessPlace getBusinessPlace() {
		return businessPlace;
	}
	public void setBusinessPlace(ExpenseManagementBusinessPlace businessPlace) {
		this.businessPlace = businessPlace;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public EmployeeDetails getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(EmployeeDetails lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@javax.persistence.Transient transient ExpenseAccountsOnly old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ExpenseAccountsOnly)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ExpenseAccountsOnly gOld() {
		return old;
	}
	public String toString() {
			return ""+expense;
	}
	public boolean isPostPark() {
		return postPark;
	}
	public void setPostPark(boolean postPark) {
		this.postPark = postPark;
	}
	
	
}
