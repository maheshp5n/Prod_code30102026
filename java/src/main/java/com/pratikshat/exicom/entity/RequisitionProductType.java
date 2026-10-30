package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class RequisitionProductType implements java.io.Serializable,OldPresist<RequisitionProductType>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long productTypeId;
	
	String productTypeName;
	boolean synced;
	public RequisitionProductType() {
		
	}
	public RequisitionProductType(long productTypeId) {
		this.productTypeId = productTypeId;
	}
	public long getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(long productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	

	@javax.persistence.Transient transient RequisitionProductType old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(RequisitionProductType)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public RequisitionProductType gOld() {
		return old;
	}
	public String toString() {
			return ""+productTypeId;
	}
}
