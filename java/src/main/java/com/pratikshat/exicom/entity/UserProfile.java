package com.pratikshat.exicom.entity;

import javax.persistence.ManyToOne;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class UserProfile implements java.io.Serializable,OldPresist<UserProfile>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private long id;
	String profileName;
	String profileDescription;
	String defaultPage;
	
	@ManyToOne
	BusinessDomain businessDomain;
	
	public UserProfile() {}
	public UserProfile(long id) {
		this.id=id;
	}
	
	public UserProfile(long id,String profileName,String profileDescription) {
		this.id=id;
		this.profileName=profileName;
		this.profileDescription=profileDescription;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getProfileDescription() {
		return profileDescription;
	}
	public void setProfileDescription(String profileDescription) {
		this.profileDescription = profileDescription;
	}
	public String getDefaultPage() {
		return defaultPage;
	}
	public void setDefaultPage(String defaultPage) {
		this.defaultPage = defaultPage;
	}
	

	@javax.persistence.Transient transient UserProfile old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(UserProfile)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public UserProfile gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}
	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}
	
	
}
