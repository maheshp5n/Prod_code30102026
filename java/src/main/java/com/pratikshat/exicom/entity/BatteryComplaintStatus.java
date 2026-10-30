package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BatteryComplaintStatus implements java.io.Serializable,OldPresist<BatteryComplaintStatus>{

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	
	String name;
	
	boolean synced;
	public BatteryComplaintStatus() {}

	public BatteryComplaintStatus(int id) {
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

	@javax.persistence.Transient transient BatteryComplaintStatus old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BatteryComplaintStatus)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BatteryComplaintStatus gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
