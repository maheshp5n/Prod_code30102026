package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class AnnexureApprovalStatus implements java.io.Serializable,OldPresist<AnnexureApprovalStatus>{

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	String name;
	boolean synced;
	
	public AnnexureApprovalStatus() {
		
	}
	
	public AnnexureApprovalStatus(int id) {
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
	public String toString() {
		return String.valueOf(id);
	}
	

	@javax.persistence.Transient transient AnnexureApprovalStatus old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(AnnexureApprovalStatus)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public AnnexureApprovalStatus gOld() {
		return old;
	}}
