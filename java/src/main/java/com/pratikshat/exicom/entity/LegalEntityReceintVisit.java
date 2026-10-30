package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LegalEntityReceintVisit implements java.io.Serializable,OldPresist<LegalEntityReceintVisit>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long legalEntityId;
	Date dateOfVisit;
	String visitorId;
	
	public LegalEntityReceintVisit() {}
	
	
	
	/**
	 * @param legalEntityId
	 * @param dateOfVisit
	 * @param visitorId
	 */
	public LegalEntityReceintVisit(long legalEntityId, Date dateOfVisit, String visitorId) {
		this.legalEntityId = legalEntityId;
		this.dateOfVisit = dateOfVisit;
		this.visitorId = visitorId;
	}



	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the legalEntityId
	 */
	public long getLegalEntityId() {
		return legalEntityId;
	}
	/**
	 * @return the dateOfVisit
	 */
	public Date getDateOfVisit() {
		return dateOfVisit;
	}
	/**
	 * @return the visitorId
	 */
	public String getVisitorId() {
		return visitorId;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param legalEntityId the legalEntityId to set
	 */
	public void setLegalEntityId(long legalEntityId) {
		this.legalEntityId = legalEntityId;
	}
	/**
	 * @param dateOfVisit the dateOfVisit to set
	 */
	public void setDateOfVisit(Date dateOfVisit) {
		this.dateOfVisit = dateOfVisit;
	}
	/**
	 * @param visitorId the visitorId to set
	 */
	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	@javax.persistence.Transient transient LegalEntityReceintVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LegalEntityReceintVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LegalEntityReceintVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
