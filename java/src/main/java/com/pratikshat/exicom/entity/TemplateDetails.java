package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;


@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class TemplateDetails implements java.io.Serializable,OldPresist<TemplateDetails>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@CreatedBy
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	@LastModifiedBy
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails lastModifiedBy;
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	Date createdDate;
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	Date lastModifiedDate;
	@Enumerated(EnumType.STRING)
	TemplateType templateType;
	@Column(columnDefinition = "TEXT")
	String body;
	
	
	boolean active;
	String templaeCode;
	
	String eventName;
	
	@ManyToOne
	BusinessDomain businessDomain;
	
	public TemplateDetails() {}
	public TemplateDetails(Long id) {
		this.id=id;
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public TemplateType getTemplateType() {
		return templateType;
	}
	public void setTemplateType(TemplateType templateType) {
		this.templateType = templateType;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getTemplaeCode() {
		return templaeCode;
	}
	public void setTemplaeCode(String templaeCode) {
		this.templaeCode = templaeCode;
	}
	
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}
	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}


	@javax.persistence.Transient transient TemplateDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(TemplateDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public TemplateDetails gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
