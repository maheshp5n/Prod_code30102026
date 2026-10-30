package com.pratikshat.exicom.entity;

import javax.persistence.Entity;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ProductType implements java.io.Serializable,OldPresist<ProductType>{

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long typeId;
	
	String typeName;
	boolean synced;
	
	public ProductType(long typeId) {
		this.typeId = typeId;
	}

	public ProductType() {
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
	
	

	@javax.persistence.Transient transient ProductType old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ProductType)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ProductType gOld() {
		return old;
	}
	public String toString() {
			return ""+typeId;
	}
}
