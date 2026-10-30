package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BtCustomerWccReject implements java.io.Serializable, OldPresist<BtCustomerWccReject> {
	
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	int id;
	String wccCustomerRejectList;
	
	
	

	public BtCustomerWccReject(int id) {
		this.id = id;
	}
	public BtCustomerWccReject() {}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWccCustomerRejectList() {
		return wccCustomerRejectList;
	}

	public void setWccCustomerRejectList(String wccCustomerRejectList) {
		this.wccCustomerRejectList = wccCustomerRejectList;
	}


	@javax.persistence.Transient transient BtCustomerWccReject old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		this.old=(BtCustomerWccReject)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BtCustomerWccReject gOld() {
		return this.old;
	}


	public String toString() {
		return String.valueOf(id);
	}
}
