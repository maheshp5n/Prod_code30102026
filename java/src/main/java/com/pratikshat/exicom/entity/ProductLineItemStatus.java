package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ProductLineItemStatus implements java.io.Serializable,OldPresist<ProductLineItemStatus>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long statusId;
	String statusName;
	boolean synced;
	public ProductLineItemStatus() {
	
	}
	public ProductLineItemStatus(long statusId) {
		this.statusId = statusId;
	}
	public long getStatusId() {
		return statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	

	@javax.persistence.Transient transient ProductLineItemStatus old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ProductLineItemStatus)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ProductLineItemStatus gOld() {
		return old;
	}
	public String toString() {
			return ""+statusId;
	}
}
