package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
@Entity
//@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class UserRoles implements java.io.Serializable,OldPresist<UserRoles>{
	@Id
	String authorities;
	String defaultRoutingPage;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	UserRoles parentUserRoles;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@ManyToOne(fetch=FetchType.LAZY)
	RoleGroup roleGroup;
	
	@ManyToOne
	BusinessDomain businessDomain;
	
	public UserRoles() {}
	public UserRoles(String authorities) {
		this.authorities=authorities;
	}
	/**
	 * @return the authorities
	 */
	public String getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	public String getDefaultRoutingPage() {
		return defaultRoutingPage;
	}
	public void setDefaultRoutingPage(String defaultRoutingPage) {
		this.defaultRoutingPage = defaultRoutingPage;
	}
	public UserRoles getParentUserRoles() {
		return parentUserRoles;
	}
	public void setParentUserRoles(UserRoles parentUserRoles) {
		this.parentUserRoles = parentUserRoles;
	}
	
	public RoleGroup getRoleGroup() {
		return roleGroup;
	}
	public void setRoleGroup(RoleGroup roleGroup) {
		this.roleGroup = roleGroup;
	}



	@javax.persistence.Transient transient UserRoles old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(UserRoles)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public UserRoles gOld() {
		return old;
	}
	public String toString() {
			return ""+authorities;
	}
	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}
	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}
	
	
}
