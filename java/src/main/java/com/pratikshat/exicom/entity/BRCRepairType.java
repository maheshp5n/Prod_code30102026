package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BRCRepairType implements java.io.Serializable,OldPresist<BRCRepairType>{

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	int typeId;
	String typeName;
	boolean synced;
	
	public BRCRepairType() {
		
	}
	
	
	public BRCRepairType(int typeId) {
		this.typeId = typeId;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
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
	
	

	@javax.persistence.Transient transient BRCRepairType old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BRCRepairType)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BRCRepairType gOld() {
		return old;
	}
	public String toString() {
			return ""+typeId;
	}
}
