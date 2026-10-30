package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class SiteDetailsRecentVisit implements java.io.Serializable,OldPresist<SiteDetailsRecentVisit>{
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	long visitId;
	long siteId;
	String visitorId;
	java.util.Date visitDate;
	
	public SiteDetailsRecentVisit(long siteId,String visitorId) {
		this.siteId=siteId;
		this.visitorId=visitorId;
		this.visitDate=new java.util.Date();
	}
	public SiteDetailsRecentVisit() {}
	/**
	 * @return the visitId
	 */
	public long getVisitId() {
		return visitId;
	}
	/**
	 * @return the siteId
	 */
	public long getSiteId() {
		return siteId;
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
	 * @param siteId the siteId to set
	 */
	public void setSiteId(long siteId) {
		this.siteId = siteId;
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
	

	@javax.persistence.Transient transient SiteDetailsRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(SiteDetailsRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public SiteDetailsRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+visitId;
	}
}
