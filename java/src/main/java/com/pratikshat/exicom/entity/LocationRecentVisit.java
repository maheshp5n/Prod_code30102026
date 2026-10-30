package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LocationRecentVisit implements java.io.Serializable,OldPresist<LocationRecentVisit>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long visitId;
	long locationId;
	String visitorId;
	java.util.Date visitDate;
	
	public LocationRecentVisit() {}
	public LocationRecentVisit(long locationId,String visitorId) {
		this.locationId=locationId;
		this.visitorId=visitorId;
		this.visitDate=new java.util.Date();
	}
	public long getVisitId() {
		return visitId;
	}
	public void setVisitId(long visitId) {
		this.visitId = visitId;
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
	public long getLocationId() {
		return locationId;
	}
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	

	@javax.persistence.Transient transient LocationRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LocationRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LocationRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+visitId;
	}
}
