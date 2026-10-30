package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class UserNavigationModule implements java.io.Serializable,OldPresist<UserNavigationModule>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@Deprecated
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	UserRoles loginUserRoles;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	UserProfile userProfile;
	
	@ManyToOne
	UINavigationModules navigationModules;
	
	Boolean uiActive=true;
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the loginUserRoles
	 */
	public UserRoles getLoginUserRoles() {
		return loginUserRoles;
	}
	/**
	 * @return the navigationModules
	 */
	public UINavigationModules getNavigationModules() {
		return navigationModules;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param loginUserRoles the loginUserRoles to set
	 */
	public void setLoginUserRoles(UserRoles loginUserRoles) {
		this.loginUserRoles = loginUserRoles;
	}
	/**
	 * @param navigationModules the navigationModules to set
	 */
	public void setNavigationModules(UINavigationModules navigationModules) {
		this.navigationModules = navigationModules;
	}
	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	

	@javax.persistence.Transient transient UserNavigationModule old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(UserNavigationModule)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public UserNavigationModule gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
	public Boolean getUiActive() {
		return uiActive;
	}
	public void setUiActive(Boolean uiActive) {
		this.uiActive = uiActive;
	}
	
}
