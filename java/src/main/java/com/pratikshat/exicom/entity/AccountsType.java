package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class AccountsType implements java.io.Serializable,OldPresist<AccountsType>{
	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	int accountTypeId;
	String accountTypeName;
	boolean synced;
	
	
	public AccountsType() {
	}


	public AccountsType(int accountTypeId) {
		this.accountTypeId = accountTypeId;
	}


	public int getAccountTypeId() {
		return accountTypeId;
	}


	public void setAccountTypeId(int accountTypeId) {
		this.accountTypeId = accountTypeId;
	}


	public String getAccountTypeName() {
		return accountTypeName;
	}


	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}


	public boolean isSynced() {
		return synced;
	}


	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	public String toString() {
		return ""+accountTypeId;
	}
	
	

	@javax.persistence.Transient transient AccountsType old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(AccountsType)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public AccountsType gOld() {
		return old;
	}}
