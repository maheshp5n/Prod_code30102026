package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ServiceResourceRecentVisit implements java.io.Serializable,OldPresist<ServiceResourceRecentVisit>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long visitId;
	long serviceResourceId;
	String visitorId;
	
	java.util.Date visitedDate;
	
	public ServiceResourceRecentVisit() {}
	public ServiceResourceRecentVisit(long serviceResourceId,String visitorId) {
		this.visitorId=visitorId;
		this.serviceResourceId=serviceResourceId;
		this.visitedDate=new java.util.Date();
	}
	public long getVisitId() {
		return visitId;
	}
	public void setVisitId(long visitId) {
		this.visitId = visitId;
	}
	public long getServiceResourceId() {
		return serviceResourceId;
	}
	public void setServiceResourceId(long serviceResourceId) {
		this.serviceResourceId = serviceResourceId;
	}
	public String getVisitorId() {
		return visitorId;
	}
	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}
	public java.util.Date getVisitedDate() {
		return visitedDate;
	}
	public void setVisitedDate(java.util.Date visitedDate) {
		this.visitedDate = visitedDate;
	}
	
	

	@javax.persistence.Transient transient ServiceResourceRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ServiceResourceRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ServiceResourceRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+visitId;
	}
}
