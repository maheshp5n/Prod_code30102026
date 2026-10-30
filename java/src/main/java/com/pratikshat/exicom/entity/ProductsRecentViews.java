package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ProductsRecentViews implements java.io.Serializable,OldPresist<ProductsRecentViews>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	String visitedPartCode;
	String userId;
	Date dateOfVisits;
	
	public ProductsRecentViews() {}
	public ProductsRecentViews(String visitedPartcode,String userId) {
		this.visitedPartCode=visitedPartcode;
		this.userId=userId;
		this.dateOfVisits=new Date();
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the visitedPartCode
	 */
	public String getVisitedPartCode() {
		return visitedPartCode;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @return the dateOfVisits
	 */
	public Date getDateOfVisits() {
		return dateOfVisits;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param visitedPartCode the visitedPartCode to set
	 */
	public void setVisitedPartCode(String visitedPartCode) {
		this.visitedPartCode = visitedPartCode;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @param dateOfVisits the dateOfVisits to set
	 */
	public void setDateOfVisits(Date dateOfVisits) {
		this.dateOfVisits = dateOfVisits;
	}

	@javax.persistence.Transient transient ProductsRecentViews old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ProductsRecentViews)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ProductsRecentViews gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
