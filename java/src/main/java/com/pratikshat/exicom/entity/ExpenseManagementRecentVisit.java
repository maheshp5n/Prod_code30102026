package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ExpenseManagementRecentVisit implements java.io.Serializable,OldPresist<ExpenseManagementRecentVisit>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long visitId;
	long expenseManagementId;
	String visitorId;
	
	Date visitedDate;

	public ExpenseManagementRecentVisit() {
		
	}

	public ExpenseManagementRecentVisit(long expenseManagementId, String visitorId) {
		this.expenseManagementId = expenseManagementId;
		this.visitorId = visitorId;
		this.visitedDate = new Date();
	}

	public long getVisitId() {
		return visitId;
	}

	public void setVisitId(long visitId) {
		this.visitId = visitId;
	}

	public long getExpenseManagementId() {
		return expenseManagementId;
	}

	public void setExpenseManagementId(long expenseManagementId) {
		this.expenseManagementId = expenseManagementId;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public Date getVisitedDate() {
		return visitedDate;
	}

	public void setVisitedDate(Date visitedDate) {
		this.visitedDate = visitedDate;
	}
	
	
	

	@javax.persistence.Transient transient ExpenseManagementRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ExpenseManagementRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ExpenseManagementRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+visitId;
	}
}
