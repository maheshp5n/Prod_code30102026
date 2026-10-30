package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class OppertunityRecentVisit implements java.io.Serializable,OldPresist<OppertunityRecentVisit>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long visitId;
	long oppertunityId;
	String visitorId;
	
	Date visitedDate;

	
	
	public OppertunityRecentVisit(long oppertunityId, String visitorId) {
		this.oppertunityId = oppertunityId;
		this.visitorId = visitorId;
		this.visitedDate = new Date();
	}

	
	
	public OppertunityRecentVisit() {
		
	}



	public long getVisitId() {
		return visitId;
	}

	public void setVisitId(long visitId) {
		this.visitId = visitId;
	}

	public long getOppertunityId() {
		return oppertunityId;
	}

	public void setOppertunityId(long oppertunityId) {
		this.oppertunityId = oppertunityId;
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
	
	
	

	@javax.persistence.Transient transient OppertunityRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(OppertunityRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public OppertunityRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+visitId;
	}
}
