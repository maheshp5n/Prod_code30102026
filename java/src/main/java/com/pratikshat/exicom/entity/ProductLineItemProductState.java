package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ProductLineItemProductState implements java.io.Serializable,OldPresist<ProductLineItemProductState>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long productStateId;
	String productStateName;
	boolean synced;
	public ProductLineItemProductState() {
		
	}
	public ProductLineItemProductState(long productStateId) {
		this.productStateId = productStateId;
	}
	public long getProductStateId() {
		return productStateId;
	}
	public void setProductStateId(long productStateId) {
		this.productStateId = productStateId;
	}
	public String getProductStateName() {
		return productStateName;
	}
	public void setProductStateName(String productStateName) {
		this.productStateName = productStateName;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	

	@javax.persistence.Transient transient ProductLineItemProductState old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ProductLineItemProductState)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ProductLineItemProductState gOld() {
		return old;
	}
	public String toString() {
			return ""+productStateId;
	}
}
