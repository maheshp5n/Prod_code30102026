package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ServiceAppointmentRecentVisit implements java.io.Serializable,OldPresist<ServiceAppointmentRecentVisit>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long visitId;
	long serviceAppointmentId;
	String visitorId;
	
	java.util.Date visitedDate;
	
	public ServiceAppointmentRecentVisit() {}
	public ServiceAppointmentRecentVisit(long serviceAppointmentId,String visitorId) {
		this.visitorId=visitorId;
		this.serviceAppointmentId=serviceAppointmentId;
		this.visitedDate=new java.util.Date();
	}
	public long getVisitId() {
		return visitId;
	}
	public void setVisitId(long visitId) {
		this.visitId = visitId;
	}
	public long getServiceAppointmentId() {
		return serviceAppointmentId;
	}
	public void setServiceAppointmentId(long serviceAppointmentId) {
		this.serviceAppointmentId = serviceAppointmentId;
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
	
	
	

	@javax.persistence.Transient transient ServiceAppointmentRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ServiceAppointmentRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ServiceAppointmentRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+visitId;
	}
}
