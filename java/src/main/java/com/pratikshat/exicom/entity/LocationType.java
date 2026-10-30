package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LocationType implements java.io.Serializable,OldPresist<LocationType>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int locationTypeId;
	
	String locationTypeName;
	
	boolean synced;
	
	public LocationType() {
		
	}


	public LocationType(int locationTypeId) {
		this.locationTypeId = locationTypeId;
	}

	public int getLocationTypeId() {
		return locationTypeId;
	}

	public void setLocationTypeId(int locationTypeId) {
		this.locationTypeId = locationTypeId;
	}

	public String getLocationTypeName() {
		return locationTypeName;
	}

	public void setLocationTypeName(String locationTypeName) {
		this.locationTypeName = locationTypeName;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	

	@javax.persistence.Transient transient LocationType old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LocationType)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LocationType gOld() {
		return old;
	}
	public String toString() {
			return ""+locationTypeId;
	}
}
