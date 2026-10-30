package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class TimeList implements java.io.Serializable,OldPresist<TimeList>{

	@Id
	String time;
	
	
	
	public TimeList() {
		
	}
	public TimeList(String time) {
		this.time = time;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	

	@javax.persistence.Transient transient TimeList old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(TimeList)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public TimeList gOld() {
		return old;
	}
	public String toString() {
			return ""+time;
	}
}
