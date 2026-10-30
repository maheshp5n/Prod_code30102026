package com.pratikshat.exicom.entity;

import javax.persistence.Entity;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class OppertunityForecastCategory implements java.io.Serializable,OldPresist<OppertunityForecastCategory>{

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long oppertunityForecastCategoryId;
	
	String oppertunityForecastCategoryName;
	
	boolean synced;

	public OppertunityForecastCategory(long oppertunityForecastCategoryId) {
		this.oppertunityForecastCategoryId = oppertunityForecastCategoryId;
	}
	
	

	public OppertunityForecastCategory() {
	}



	public long getOppertunityForecastCategoryId() {
		return oppertunityForecastCategoryId;
	}

	public void setOppertunityForecastCategoryId(long oppertunityForecastCategoryId) {
		this.oppertunityForecastCategoryId = oppertunityForecastCategoryId;
	}

	public String getOppertunityForecastCategoryName() {
		return oppertunityForecastCategoryName;
	}

	public void setOppertunityForecastCategoryName(String oppertunityForecastCategoryName) {
		this.oppertunityForecastCategoryName = oppertunityForecastCategoryName;
	}



	public boolean isSynced() {
		return synced;
	}



	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	


	@javax.persistence.Transient transient OppertunityForecastCategory old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(OppertunityForecastCategory)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public OppertunityForecastCategory gOld() {
		return old;
	}
	public String toString() {
			return ""+oppertunityForecastCategoryId;
	}
}
