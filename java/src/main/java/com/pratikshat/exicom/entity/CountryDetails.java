package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class CountryDetails implements java.io.Serializable,OldPresist<CountryDetails>{
	@Id
	String conuntryId;
	String countryName;
	boolean synced;
	
	/**
	 * @return the synced
	 */
	public boolean isSynced() {
		return synced;
	}

	/**
	 * @param synced the synced to set
	 */
	public void setSynced(boolean synced) {
		this.synced = synced;
	}

	public CountryDetails(String countryId) {
		this.conuntryId=countryId;
	}
	
	/**
	 * 
	 */
	public CountryDetails() {
	}
	/**
	 * @param conuntryId
	 * @param countryName
	 */
	public CountryDetails(String conuntryId, String countryName) {
		this.conuntryId = conuntryId;
		this.countryName = countryName;
	}
	/**
	 * @return the conuntryId
	 */
	public String getConuntryId() {
		return conuntryId;
	}
	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}
	/**
	 * @param conuntryId the conuntryId to set
	 */
	public void setConuntryId(String conuntryId) {
		this.conuntryId = conuntryId;
	}
	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	

	 public String toString(){
		return conuntryId;
	 }

	@javax.persistence.Transient transient CountryDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CountryDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CountryDetails gOld() {
		return old;
	}}
