package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class CircleRecentVisit implements java.io.Serializable,OldPresist<CircleRecentVisit>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long visitId;
	String circleName;
	String visitorId;
	java.util.Date visitDate;
	
	public CircleRecentVisit() {}
	public CircleRecentVisit(String circleName,String visitorId) {
		this.circleName=circleName;
		this.visitorId=visitorId;
		this.visitDate=new java.util.Date();
	}
	/**
	 * @return the visitId
	 */
	public long getVisitId() {
		return visitId;
	}
	/**
	 * @return the circleName
	 */
	public String getCircleName() {
		return circleName;
	}
	/**
	 * @return the visitorId
	 */
	public String getVisitorId() {
		return visitorId;
	}
	/**
	 * @return the visitDate
	 */
	public java.util.Date getVisitDate() {
		return visitDate;
	}
	/**
	 * @param visitId the visitId to set
	 */
	public void setVisitId(long visitId) {
		this.visitId = visitId;
	}
	/**
	 * @param circleName the circleName to set
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	/**
	 * @param visitorId the visitorId to set
	 */
	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}
	/**
	 * @param visitDate the visitDate to set
	 */
	public void setVisitDate(java.util.Date visitDate) {
		this.visitDate = visitDate;
	}
	

	@javax.persistence.Transient transient CircleRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CircleRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CircleRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+visitId;
	}
}
