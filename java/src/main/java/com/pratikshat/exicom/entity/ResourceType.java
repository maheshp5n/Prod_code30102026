package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ResourceType implements java.io.Serializable,OldPresist<ResourceType>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int resourceTypeId;
	
	String resourceTypeName;
	
	boolean synced;
	
	public ResourceType(int resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}

	public ResourceType() {
	
	}

	public int getResourceTypeId() {
		return resourceTypeId;
	}

	public void setResourceTypeId(int resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}

	public String getResourceTypeName() {
		return resourceTypeName;
	}

	public void setResourceTypeName(String resourceTypeName) {
		this.resourceTypeName = resourceTypeName;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	

	@javax.persistence.Transient transient ResourceType old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ResourceType)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ResourceType gOld() {
		return old;
	}
	public String toString() {
			return ""+resourceTypeId;
	}
}
