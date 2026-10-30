package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ContactsRecentVisit implements java.io.Serializable,OldPresist<ContactsRecentVisit>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long visitId;
	private long contactId;
	private Date visitedDate;
	private String visitedByid;
	
	
	
	/**
	 * 
	 */
	public ContactsRecentVisit() {
	}
	/**
	 * @param contactId
	 * @param visitedDate
	 * @param visitedByid
	 */
	public ContactsRecentVisit(long contactId, Date visitedDate, String visitedByid) {
		this.contactId = contactId;
		this.visitedDate = visitedDate;
		this.visitedByid = visitedByid;
	}
	/**
	 * @param visitId
	 * @param contactId
	 * @param visitedDate
	 * @param visitedByid
	 */
	public ContactsRecentVisit(long visitId, long contactId, Date visitedDate, String visitedByid) {
		this.visitId = visitId;
		this.contactId = contactId;
		this.visitedDate = visitedDate;
		this.visitedByid = visitedByid;
	}
	/**
	 * @return the visitId
	 */
	public long getVisitId() {
		return visitId;
	}
	/**
	 * @return the contactId
	 */
	public long getContactId() {
		return contactId;
	}
	/**
	 * @return the visitedDate
	 */
	public Date getVisitedDate() {
		return visitedDate;
	}
	/**
	 * @return the visitedByid
	 */
	public String getVisitedByid() {
		return visitedByid;
	}
	/**
	 * @param visitId the visitId to set
	 */
	public void setVisitId(long visitId) {
		this.visitId = visitId;
	}
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	/**
	 * @param visitedDate the visitedDate to set
	 */
	public void setVisitedDate(Date visitedDate) {
		this.visitedDate = visitedDate;
	}
	/**
	 * @param visitedByid the visitedByid to set
	 */
	public void setVisitedByid(String visitedByid) {
		this.visitedByid = visitedByid;
	}

	@javax.persistence.Transient transient ContactsRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ContactsRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ContactsRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+visitId;
	}
}
