package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class OwnConveyance implements java.io.Serializable,OldPresist<OwnConveyance>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String name;
	boolean synced;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	ModeOfTravel modeOfTravel;
	
	public OwnConveyance() {
	}
	public OwnConveyance(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	public ModeOfTravel getModeOfTravel() {
		return modeOfTravel;
	}
	public void setModeOfTravel(ModeOfTravel modeOfTravel) {
		this.modeOfTravel = modeOfTravel;
	}
	
	
	

	@javax.persistence.Transient transient OwnConveyance old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(OwnConveyance)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public OwnConveyance gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
