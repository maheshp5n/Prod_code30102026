package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class CityRecentVisit implements java.io.Serializable,OldPresist<CityRecentVisit>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String cityName;
	String visitoryId;
	Date visitDate;
	public CityRecentVisit() {}
	/**
	 * @param cityName
	 * @param visitoryId
	 * @param visitDate
	 */
	public CityRecentVisit(String cityName, String visitoryId, Date visitDate) {
		this.cityName = cityName;
		this.visitoryId = visitoryId;
		this.visitDate = visitDate;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @return the visitoryId
	 */
	public String getVisitoryId() {
		return visitoryId;
	}
	/**
	 * @return the visitDate
	 */
	public Date getVisitDate() {
		return visitDate;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @param visitoryId the visitoryId to set
	 */
	public void setVisitoryId(String visitoryId) {
		this.visitoryId = visitoryId;
	}
	/**
	 * @param visitDate the visitDate to set
	 */
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	@javax.persistence.Transient transient CityRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CityRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CityRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
