package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ServiceOrderStatus implements java.io.Serializable,OldPresist<ServiceOrderStatus>{
	@Id
	Integer id;
	String name;
	
	public ServiceOrderStatus() {}
	public ServiceOrderStatus(Integer id) {
		this.id=id;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

	@javax.persistence.Transient transient ServiceOrderStatus old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ServiceOrderStatus)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ServiceOrderStatus gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
