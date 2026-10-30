package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BatteryFSRRecentVisit implements java.io.Serializable,OldPresist<BatteryFSRRecentVisit>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long visitId;
	long batteryFSRId;
	String visitorId;
	java.util.Date visitDate;
	
	public BatteryFSRRecentVisit() {}
	public BatteryFSRRecentVisit(long batteryFSRId,String visitorId) {
		this.batteryFSRId=batteryFSRId;
		this.visitorId=visitorId;
		this.visitDate=new java.util.Date();
	}
	public long getVisitId() {
		return visitId;
	}
	public void setVisitId(long visitId) {
		this.visitId = visitId;
	}
	public long getBatteryFSRId() {
		return batteryFSRId;
	}
	public void setBatteryFSRId(long batteryFSRId) {
		this.batteryFSRId = batteryFSRId;
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
	
	

	@javax.persistence.Transient transient BatteryFSRRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BatteryFSRRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BatteryFSRRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+visitId;
	}
}
