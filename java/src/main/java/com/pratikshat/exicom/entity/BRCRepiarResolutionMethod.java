package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BRCRepiarResolutionMethod implements java.io.Serializable,OldPresist<BRCRepiarResolutionMethod>{

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	int resolutionMethodId;
	String resolutionMethodName;
	boolean synced;
	
	public BRCRepiarResolutionMethod() {
		
	}
	public BRCRepiarResolutionMethod(int resolutionMethodId) {
		this.resolutionMethodId = resolutionMethodId;
	}
	public int getResolutionMethodId() {
		return resolutionMethodId;
	}
	public void setResolutionMethodId(int resolutionMethodId) {
		this.resolutionMethodId = resolutionMethodId;
	}
	public String getResolutionMethodName() {
		return resolutionMethodName;
	}
	public void setResolutionMethodName(String resolutionMethodName) {
		this.resolutionMethodName = resolutionMethodName;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	

	@javax.persistence.Transient transient BRCRepiarResolutionMethod old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BRCRepiarResolutionMethod)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BRCRepiarResolutionMethod gOld() {
		return old;
	}
	public String toString() {
			return ""+resolutionMethodId;
	}
}
