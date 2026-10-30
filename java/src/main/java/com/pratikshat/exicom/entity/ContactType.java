package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ContactType implements java.io.Serializable,OldPresist<ContactType>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long typeId;
	String typeName;
	boolean synced;
	
	public ContactType(long typeId) {
		this.typeId = typeId;
	}
	public ContactType() {
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
	
	

	@javax.persistence.Transient transient ContactType old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ContactType)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ContactType gOld() {
		return old;
	}
	public String toString() {
			return ""+typeId;
	}
}
