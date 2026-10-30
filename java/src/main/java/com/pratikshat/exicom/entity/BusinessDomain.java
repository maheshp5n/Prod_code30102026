package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BusinessDomain implements java.io.Serializable,OldPresist<BusinessDomain>{
	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	int businessDomainId;
	String businessDomainName;
	
	boolean synced;
	
	public BusinessDomain() {
		
	}

	public BusinessDomain(int businessDomainId) {
		this.businessDomainId = businessDomainId;
	}

	public int getBusinessDomainId() {
		return businessDomainId;
	}

	public void setBusinessDomainId(int businessDomainId) {
		this.businessDomainId = businessDomainId;
	}

	public String getBusinessDomainName() {
		return businessDomainName;
	}

	public void setBusinessDomainName(String businessDomainName) {
		this.businessDomainName = businessDomainName;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	public String toString(){
		return String.valueOf(businessDomainId);
	 }
	

	@javax.persistence.Transient transient BusinessDomain old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BusinessDomain)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BusinessDomain gOld() {
		return old;
	}}
