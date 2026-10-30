package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class MSLRequestRecentVisit implements java.io.Serializable,OldPresist<MSLRequestRecentVisit>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long visitId;
	long mslId;
	String visitorId;
	
	Date visitedDate;


	public MSLRequestRecentVisit(long mslId, String visitorId) {
		this.mslId = mslId;
		this.visitorId = visitorId;
		this.visitedDate = new Date();
	}


	public MSLRequestRecentVisit() {
		
	}


	public long getVisitId() {
		return visitId;
	}


	public void setVisitId(long visitId) {
		this.visitId = visitId;
	}


	public long getMslId() {
		return mslId;
	}


	public void setMslId(long mslId) {
		this.mslId = mslId;
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
	
	

	@javax.persistence.Transient transient MSLRequestRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(MSLRequestRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public MSLRequestRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+visitId;
	}
}
