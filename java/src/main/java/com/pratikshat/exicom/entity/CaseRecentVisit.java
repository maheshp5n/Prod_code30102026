package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class CaseRecentVisit implements java.io.Serializable,OldPresist<CaseRecentVisit>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long visitId;
	long caseId;
	String visitorId;
	
	java.util.Date visitedDate;
	
	public CaseRecentVisit() {}
	public CaseRecentVisit(long caseId,String visitorId) {
		this.visitorId=visitorId;
		this.caseId=caseId;
		this.visitedDate=new java.util.Date();
	}
	/**
	 * @return the visitId
	 */
	public long getVisitId() {
		return visitId;
	}
	/**
	 * @return the caseId
	 */
	public long getCaseId() {
		return caseId;
	}
	/**
	 * @return the visitorId
	 */
	public String getVisitorId() {
		return visitorId;
	}
	/**
	 * @return the visitedDate
	 */
	public java.util.Date getVisitedDate() {
		return visitedDate;
	}
	/**
	 * @param visitId the visitId to set
	 */
	public void setVisitId(long visitId) {
		this.visitId = visitId;
	}
	/**
	 * @param caseId the caseId to set
	 */
	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}
	/**
	 * @param visitorId the visitorId to set
	 */
	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}
	/**
	 * @param visitedDate the visitedDate to set
	 */
	public void setVisitedDate(java.util.Date visitedDate) {
		this.visitedDate = visitedDate;
	}
	

	@javax.persistence.Transient transient CaseRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CaseRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CaseRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+visitId;
	}
}
