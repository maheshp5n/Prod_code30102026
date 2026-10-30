package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class InternalServiceCode implements java.io.Serializable,OldPresist<InternalServiceCode>{
	@Id
	Integer id;
	String internalServiceCode;

	@ManyToOne
	BusinessDomain businessDomain;
	
	public InternalServiceCode() {}
	
	public InternalServiceCode(Integer id) {
		this.id=id;
	}
	
	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInternalServiceCode() {
		return internalServiceCode;
	}
	public void setInternalServiceCode(String internalServiceCode) {
		this.internalServiceCode = internalServiceCode;
	}
	
	

	@javax.persistence.Transient transient InternalServiceCode old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(InternalServiceCode)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public InternalServiceCode gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
