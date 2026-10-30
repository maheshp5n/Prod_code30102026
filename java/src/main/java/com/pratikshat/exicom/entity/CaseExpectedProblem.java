package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.ManyToOne;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class CaseExpectedProblem implements java.io.Serializable,OldPresist<CaseExpectedProblem>{

	@javax.persistence.Id
	long id;
	
	String problemName;
	
	boolean synced;
	
	@ManyToOne
	BusinessDomain businessDomain;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the problemName
	 */
	public String getProblemName() {
		return problemName;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param problemName the problemName to set
	 */
	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}

	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}
	
	

	@javax.persistence.Transient transient CaseExpectedProblem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CaseExpectedProblem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CaseExpectedProblem gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
