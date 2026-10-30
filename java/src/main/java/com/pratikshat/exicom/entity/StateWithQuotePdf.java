package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class StateWithQuotePdf implements java.io.Serializable,OldPresist<StateWithQuotePdf>{

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	StateProvinceDetails state;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	QuotePdfHeaderAndFooter quotePdfHeaderAndFooter;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	BusinessDomain businessDomain;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public StateProvinceDetails getState() {
		return state;
	}
	public void setState(StateProvinceDetails state) {
		this.state = state;
	}
	public QuotePdfHeaderAndFooter getQuotePdfHeaderAndFooter() {
		return quotePdfHeaderAndFooter;
	}
	public void setQuotePdfHeaderAndFooter(QuotePdfHeaderAndFooter quotePdfHeaderAndFooter) {
		this.quotePdfHeaderAndFooter = quotePdfHeaderAndFooter;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}
	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}
	
	

	@javax.persistence.Transient transient StateWithQuotePdf old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(StateWithQuotePdf)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public StateWithQuotePdf gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
