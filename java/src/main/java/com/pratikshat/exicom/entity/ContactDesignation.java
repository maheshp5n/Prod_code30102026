package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ContactDesignation implements java.io.Serializable,OldPresist<ContactDesignation>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long designationId;
	String designationName;
	boolean synced;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="contact_type_id")
	ContactType contactType;
	
	public ContactDesignation() {
	}
	public ContactDesignation(long designationId) {
		this.designationId = designationId;
	}
	public long getDesignationId() {
		return designationId;
	}
	public void setDesignationId(long designationId) {
		this.designationId = designationId;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	

	@javax.persistence.Transient transient ContactDesignation old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ContactDesignation)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ContactDesignation gOld() {
		return old;
	}
	public String toString() {
			return ""+designationId;
	}
}
