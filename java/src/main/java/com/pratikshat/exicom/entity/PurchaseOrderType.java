package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class PurchaseOrderType implements java.io.Serializable,OldPresist<PurchaseOrderType>{
	@Id
	Integer id;
	String poTypeName;
	
	public PurchaseOrderType() {}
	public PurchaseOrderType(Integer id) {
		this.id=id;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPoTypeName() {
		return poTypeName;
	}
	public void setPoTypeName(String poTypeName) {
		this.poTypeName = poTypeName;
	}
	
	

	@javax.persistence.Transient transient PurchaseOrderType old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(PurchaseOrderType)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public PurchaseOrderType gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
