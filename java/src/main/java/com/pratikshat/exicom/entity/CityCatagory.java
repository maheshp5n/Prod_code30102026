package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class CityCatagory implements java.io.Serializable,OldPresist<CityCatagory>{
	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	int cityCategoryId;
	String cityCatagoryName;
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

	/**
	 * 
	 */
	public CityCatagory() {
	}


	
	
	public CityCatagory(int cityCategoryId) {
		this.cityCategoryId = cityCategoryId;
	}

	/**
	 * @return the cityCatagoryName
	 */
	public String getCityCatagoryName() {
		return cityCatagoryName;
	}

	/**
	 * @param cityCatagoryName the cityCatagoryName to set
	 */
	public void setCityCatagoryName(String cityCatagoryName) {
		this.cityCatagoryName = cityCatagoryName;
	}

	public int getCityCategoryId() {
		return cityCategoryId;
	}

	public void setCityCategoryId(int cityCategoryId) {
		this.cityCategoryId = cityCategoryId;
	}
	
	

	@javax.persistence.Transient transient CityCatagory old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CityCatagory)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CityCatagory gOld() {
		return old;
	}
	public String toString() {
			return ""+cityCategoryId;
	}
}
