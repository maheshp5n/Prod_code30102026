package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class PurchaseOrderStatus implements java.io.Serializable,OldPresist<PurchaseOrderStatus>{
	@Id
	Integer id;
	String poStatusName;
	
	public PurchaseOrderStatus() {}
	public PurchaseOrderStatus(Integer id) {
		this.id=id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPoStatusName() {
		return poStatusName;
	}
	public void setPoStatusName(String poStatusName) {
		this.poStatusName = poStatusName;
	}
	

	@javax.persistence.Transient transient PurchaseOrderStatus old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(PurchaseOrderStatus)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public PurchaseOrderStatus gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
