package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BRCRepiarBalancedAt implements java.io.Serializable,OldPresist<BRCRepiarBalancedAt>{

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	int balancedAtId;
	String balancedAtName;
	boolean synced;
	
	
	
	public BRCRepiarBalancedAt() {
		
	}
	public BRCRepiarBalancedAt(int balancedAtId) {
		this.balancedAtId = balancedAtId;
	}
	public int getBalancedAtId() {
		return balancedAtId;
	}
	public void setBalancedAtId(int balancedAtId) {
		this.balancedAtId = balancedAtId;
	}
	public String getBalancedAtName() {
		return balancedAtName;
	}
	public void setBalancedAtName(String balancedAtName) {
		this.balancedAtName = balancedAtName;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	

	@javax.persistence.Transient transient BRCRepiarBalancedAt old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BRCRepiarBalancedAt)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BRCRepiarBalancedAt gOld() {
		return old;
	}
	public String toString() {
			return ""+balancedAtId;
	}
}
