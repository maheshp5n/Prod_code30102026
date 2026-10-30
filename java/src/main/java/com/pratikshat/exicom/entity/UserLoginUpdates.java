package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class UserLoginUpdates implements java.io.Serializable,OldPresist<UserLoginUpdates>{
	@Id
	String userEmailid;
	boolean active;
	Date lastLoginDate;
	String lastSessionId;
	
	public String getUserEmailid() {
		return userEmailid;
	}
	public void setUserEmailid(String userEmailid) {
		this.userEmailid = userEmailid;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getLastSessionId() {
		return lastSessionId;
	}
	public void setLastSessionId(String lastSessionId) {
		this.lastSessionId = lastSessionId;
	}
	
	

	@javax.persistence.Transient transient UserLoginUpdates old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(UserLoginUpdates)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public UserLoginUpdates gOld() {
		return old;
	}
	public String toString() {
			return ""+userEmailid;
	}
}
