package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class AssetsStatus implements java.io.Serializable,OldPresist<AssetsStatus>{
	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	int assetsStatusId;
	String assetsStatusName;
	boolean synced;
	
	public AssetsStatus() {}
	
	

	public AssetsStatus(int assetsStatusId) {
		this.assetsStatusId = assetsStatusId;
	}



	public int getAssetsStatusId() {
		return assetsStatusId;
	}

	public void setAssetsStatusId(int assetsStatusId) {
		this.assetsStatusId = assetsStatusId;
	}

	public String getAssetsStatusName() {
		return assetsStatusName;
	}

	public void setAssetsStatusName(String assetsStatusName) {
		this.assetsStatusName = assetsStatusName;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}


	@Override
	public String toString() {
		return ""+ assetsStatusId;
	}
	
	

	@javax.persistence.Transient transient AssetsStatus old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(AssetsStatus)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public AssetsStatus gOld() {
		return old;
	}}
