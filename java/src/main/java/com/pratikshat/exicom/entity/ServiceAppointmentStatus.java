package com.pratikshat.exicom.entity;

import javax.persistence.Entity;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ServiceAppointmentStatus implements java.io.Serializable,OldPresist<ServiceAppointmentStatus>{
	
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long statusId;
	
	String statusName;
	boolean synced;
	
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
	
	public ServiceAppointmentStatus(long statusId) {
		this.statusId = statusId;
	}
	
	
	public ServiceAppointmentStatus() {
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}


	@javax.persistence.Transient transient ServiceAppointmentStatus old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ServiceAppointmentStatus)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ServiceAppointmentStatus gOld() {
		return old;
	}
	public String toString() {
			return ""+statusId;
	}
}
