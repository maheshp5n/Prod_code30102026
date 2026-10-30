package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class VendorPoCategory implements java.io.Serializable,OldPresist<VendorPoCategory>{
	@Id
	Integer id;
	String name;
	
	public VendorPoCategory() {}
	public VendorPoCategory(Integer id) {
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
	
	

	@javax.persistence.Transient transient VendorPoCategory old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(VendorPoCategory)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public VendorPoCategory gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
