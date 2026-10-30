package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class AssetsRecentVisit implements java.io.Serializable,OldPresist<AssetsRecentVisit>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long visitId;
	String assetsSerialId;
	String visitorId;
	java.util.Date visitDate;
	
	public AssetsRecentVisit() {}
	public AssetsRecentVisit(String assetsSerialId,String visitorId) {
		this.assetsSerialId=assetsSerialId;
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
	 * @return the assetsSerialId
	 */
	public String getAssetsSerialId() {
		return assetsSerialId;
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
	 * @param assetsSerialId the assetsSerialId to set
	 */
	public void setAssetsSerialId(String assetsSerialId) {
		this.assetsSerialId = assetsSerialId;
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
	
	

	@javax.persistence.Transient transient AssetsRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(AssetsRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public AssetsRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+visitId;
	}
}
