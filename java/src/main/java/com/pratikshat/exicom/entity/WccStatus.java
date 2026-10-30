package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class WccStatus implements java.io.Serializable,OldPresist<WccStatus>{
	@Id
	int id;
	String name;
	
	public WccStatus() {}
	public WccStatus(int id) {
		this.id=id;
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
	
	

	@javax.persistence.Transient transient WccStatus old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(WccStatus)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public WccStatus gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
