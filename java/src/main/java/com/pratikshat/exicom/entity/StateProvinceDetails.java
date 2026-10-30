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
public class StateProvinceDetails implements java.io.Serializable,OldPresist<StateProvinceDetails>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long stateId;
	String stateName;
	String gstStateCode;
	
	@ManyToOne
	@JoinColumn(name="country_id")
	CountryDetails country;
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
	public StateProvinceDetails(long id){
		this.stateId=id;
	}
	/**
	 * 
	 */
	public StateProvinceDetails() {
	}
	/**
	 * @param stateId
	 * @param stateName
	 * @param country
	 */
	public StateProvinceDetails(long stateId, String stateName, CountryDetails country) {
		this.stateId = stateId;
		this.stateName = stateName;
		this.country = country;
	}
	/**
	 * @return the stateId
	 */
	public long getStateId() {
		return stateId;
	}
	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}
	/**
	 * @return the country
	 */
	public CountryDetails getCountry() {
		return country;
	}
	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(long stateId) {
		this.stateId = stateId;
	}
	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(CountryDetails country) {
		this.country = country;
	}
	public String getGstStateCode() {
		return gstStateCode;
	}
	public void setGstStateCode(String gstStateCode) {
		this.gstStateCode = gstStateCode;
	}

	 public String toString(){
		return String.valueOf(stateId);
	 }

	@javax.persistence.Transient transient StateProvinceDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(StateProvinceDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public StateProvinceDetails gOld() {
		return old;
	}}
