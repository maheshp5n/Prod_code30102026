package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Pratikshat
 *
 */
@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class MailerProperty implements java.io.Serializable,OldPresist<MailerProperty>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(fetch=FetchType.LAZY)
	MailerEntity mailerEntity;
	String mailerPropertyName;
	String mailerpropertyValue;
	boolean active;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails lastModifiedBy;
	Date createdDate;
	Date lastModifiedDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MailerEntity getMailerEntity() {
		return mailerEntity;
	}
	public void setMailerEntity(MailerEntity mailerEntity) {
		this.mailerEntity = mailerEntity;
	}
	public String getMailerPropertyName() {
		return mailerPropertyName;
	}
	public void setMailerPropertyName(String mailerPropertyName) {
		this.mailerPropertyName = mailerPropertyName;
	}
	public String getMailerpropertyValue() {
		return mailerpropertyValue;
	}
	public void setMailerpropertyValue(String mailerpropertyValue) {
		this.mailerpropertyValue = mailerpropertyValue;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public EmployeeDetails getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(EmployeeDetails lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	
	

	@javax.persistence.Transient transient MailerProperty old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(MailerProperty)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public MailerProperty gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
