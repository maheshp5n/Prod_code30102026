package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class QuoteRecentVisit implements java.io.Serializable,OldPresist<QuoteRecentVisit>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long visitId;
	long quoteId;
	String visitorId;
	java.util.Date visitDate;
	
	public QuoteRecentVisit() {}
	public QuoteRecentVisit(long quoteId,String visitorId) {
		this.quoteId=quoteId;
		this.visitorId=visitorId;
		this.visitDate=new java.util.Date();
	}
	public long getVisitId() {
		return visitId;
	}
	public void setVisitId(long visitId) {
		this.visitId = visitId;
	}
	public long getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(long quoteId) {
		this.quoteId = quoteId;
	}
	public String getVisitorId() {
		return visitorId;
	}
	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}
	public java.util.Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(java.util.Date visitDate) {
		this.visitDate = visitDate;
	}
	
	

	@javax.persistence.Transient transient QuoteRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(QuoteRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public QuoteRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+visitId;
	}
}
