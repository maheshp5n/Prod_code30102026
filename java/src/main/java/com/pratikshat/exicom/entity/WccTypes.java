package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class WccTypes implements java.io.Serializable,OldPresist<WccTypes>{
	@Id
	int id;
	String wccTypeName;
	int vendorPoGlcode;
	public WccTypes() {}
	public WccTypes(int id) {
		this.id=id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWccTypeName() {
		return wccTypeName;
	}
	public void setWccTypeName(String wccTypeName) {
		this.wccTypeName = wccTypeName;
	}
	
	public int getVendorPoGlcode() {
		return vendorPoGlcode;
	}
	public void setVendorPoGlcode(int vendorPoGlcode) {
		this.vendorPoGlcode = vendorPoGlcode;
	}



	@javax.persistence.Transient transient WccTypes old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(WccTypes)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public WccTypes gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
