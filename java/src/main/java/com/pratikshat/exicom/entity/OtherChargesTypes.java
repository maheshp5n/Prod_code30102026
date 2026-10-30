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
public class OtherChargesTypes implements java.io.Serializable,OldPresist<OtherChargesTypes>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String name;
	boolean synced;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	BusinessDomain businessDoamin;

	public OtherChargesTypes() {
		
	}

	public OtherChargesTypes(int id) {
		
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

	public BusinessDomain getBusinessDoamin() {
		return businessDoamin;
	}

	public void setBusinessDoamin(BusinessDomain businessDoamin) {
		this.businessDoamin = businessDoamin;
	}
	
	
	

	@javax.persistence.Transient transient OtherChargesTypes old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(OtherChargesTypes)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public OtherChargesTypes gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
