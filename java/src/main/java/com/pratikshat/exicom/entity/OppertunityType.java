package com.pratikshat.exicom.entity;

import javax.persistence.Entity;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class OppertunityType implements java.io.Serializable,OldPresist<OppertunityType>{

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long oppertunityTypeId;
	
	String oppertunityTypeName;
	boolean synced;
	
	public OppertunityType(long oppertunityTypeId) {
		this.oppertunityTypeId = oppertunityTypeId;
	}

	public OppertunityType() {
		
	}

	public long getOppertunityTypeId() {
		return oppertunityTypeId;
	}

	public void setOppertunityTypeId(long oppertunityTypeId) {
		this.oppertunityTypeId = oppertunityTypeId;
	}

	public String getOppertunityTypeName() {
		return oppertunityTypeName;
	}

	public void setOppertunityTypeName(String oppertunityTypeName) {
		this.oppertunityTypeName = oppertunityTypeName;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	
	

	@javax.persistence.Transient transient OppertunityType old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(OppertunityType)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public OppertunityType gOld() {
		return old;
	}
	public String toString() {
			return ""+oppertunityTypeId;
	}
}
