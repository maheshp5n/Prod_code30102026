package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class IGSTBillingState implements java.io.Serializable,OldPresist<IGSTBillingState>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long stateId;
	String stateName;
	@ManyToOne
	@JoinColumn(name="country_id")
	CountryDetails country;
	boolean synced;
	
	public IGSTBillingState() {
		
	}
	
	
	
	public IGSTBillingState(long stateId) {
		this.stateId = stateId;
	}



	public long getStateId() {
		return stateId;
	}
	public void setStateId(long stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public CountryDetails getCountry() {
		return country;
	}
	public void setCountry(CountryDetails country) {
		this.country = country;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	

	@javax.persistence.Transient transient IGSTBillingState old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(IGSTBillingState)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public IGSTBillingState gOld() {
		return old;
	}
	public String toString() {
			return ""+stateId;
	}
}
