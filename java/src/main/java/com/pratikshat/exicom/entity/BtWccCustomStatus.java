package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BtWccCustomStatus implements java.io.Serializable,OldPresist<BtWccCustomStatus>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String status;

	public BtWccCustomStatus() {
		
	}
	public BtWccCustomStatus(int id) {
		this.id=id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return String.valueOf(id);
	}

	@javax.persistence.Transient transient BtWccCustomStatus old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		this.old=(BtWccCustomStatus)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BtWccCustomStatus gOld() {
		return this.old;
	}
	

}
