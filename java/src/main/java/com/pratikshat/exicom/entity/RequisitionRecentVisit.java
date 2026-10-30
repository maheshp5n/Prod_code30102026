package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class RequisitionRecentVisit implements java.io.Serializable,OldPresist<RequisitionRecentVisit>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long visitId;
	long requisitionId;
	String visitorId;
	
	Date visitedDate;
	
	public RequisitionRecentVisit(long requisitionId, String visitorId) {
		this.requisitionId = requisitionId;
		this.visitorId = visitorId;
		this.visitedDate = new Date();
	}
	
	

	public RequisitionRecentVisit() {
		
	}



	public long getVisitId() {
		return visitId;
	}

	public void setVisitId(long visitId) {
		this.visitId = visitId;
	}

	public long getRequisitionId() {
		return requisitionId;
	}

	public void setRequisitionId(long requisitionId) {
		this.requisitionId = requisitionId;
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
	
	
	

	@javax.persistence.Transient transient RequisitionRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(RequisitionRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public RequisitionRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+visitId;
	}
}
