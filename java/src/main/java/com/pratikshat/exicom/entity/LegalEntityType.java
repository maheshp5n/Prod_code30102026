package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LegalEntityType implements java.io.Serializable,OldPresist<LegalEntityType>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long typeId;
	String typeName;
	boolean synced;
	public LegalEntityType() {
		
	}
	public LegalEntityType(long typeId) {
		this.typeId = typeId;
	}
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	

	@javax.persistence.Transient transient LegalEntityType old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LegalEntityType)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LegalEntityType gOld() {
		return old;
	}
	public String toString() {
			return ""+typeId;
	}
}
