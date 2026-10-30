package com.pratikshat.exicom.entity;

import javax.persistence.Id;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ProductConsumptionType implements java.io.Serializable,OldPresist<ProductConsumptionType>{
	
	@Id
	String consumptionTypeName;
	boolean synced;
	public ProductConsumptionType() {
		
	}
	public ProductConsumptionType(String consumptionTypeName) {
		this.consumptionTypeName = consumptionTypeName;
	}
	
	
	public String getConsumptionTypeName() {
		return consumptionTypeName;
	}
	public void setConsumptionTypeName(String consumptionTypeName) {
		this.consumptionTypeName = consumptionTypeName;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	

	@javax.persistence.Transient transient ProductConsumptionType old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ProductConsumptionType)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ProductConsumptionType gOld() {
		return old;
	}
	public String toString() {
			return ""+consumptionTypeName;
	}
}
