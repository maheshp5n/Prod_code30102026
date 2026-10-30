package com.pratikshat.exicom.entity;

import java.io.Serializable;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class FaultObserved implements java.io.Serializable,OldPresist<FaultObserved>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	int id;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	BusinessDomain domain;
	String faultObserved;
	boolean synced;
	
	
	
	
	public FaultObserved() {
		
	}
	public FaultObserved(FaultObserved faultObserved) {
		this.id=faultObserved.getId();
		this.faultObserved=faultObserved.getFaultObserved();
		if(faultObserved.getDomain()!=null) {
			this.domain=faultObserved.getDomain();
		}
	}
	public FaultObserved(int id) {
		this.id=id;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the domain
	 */
	public BusinessDomain getDomain() {
		return domain;
	}
	/**
	 * @return the faultObserved
	 */
	public String getFaultObserved() {
		return faultObserved;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @param domain the domain to set
	 */
	public void setDomain(BusinessDomain domain) {
		this.domain = domain;
	}
	/**
	 * @param faultObserved the faultObserved to set
	 */
	public void setFaultObserved(String faultObserved) {
		this.faultObserved = faultObserved;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	

	@javax.persistence.Transient transient FaultObserved old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(FaultObserved)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public FaultObserved gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
