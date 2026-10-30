package com.pratikshat.exicom.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class WarehouseRecentVisit implements java.io.Serializable,OldPresist<WarehouseRecentVisit>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long visitId;
	long warehouseId;
	String visitorId;
	java.util.Date visitDate;
	public WarehouseRecentVisit() {}
	public WarehouseRecentVisit(long warehouseid,String visitorId) {
		this.warehouseId=warehouseid;
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
	 * @return the warehouseId
	 */
	public long getWarehouseId() {
		return warehouseId;
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
	 * @param warehouseId the warehouseId to set
	 */
	public void setWarehouseId(long warehouseId) {
		this.warehouseId = warehouseId;
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

	@javax.persistence.Transient transient WarehouseRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(WarehouseRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public WarehouseRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+visitId;
	}
}
