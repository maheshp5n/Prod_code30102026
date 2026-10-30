package com.pratikshat.exicom.model.response;

public class UserRoleResponse {
	
	String authorities;
	String parentUserAuthorities;
	Integer roleGroup;
	
	public UserRoleResponse() {}
	public UserRoleResponse(String authorities, String parentUserAuthorities) {
		this.authorities=authorities;
		this.parentUserAuthorities=parentUserAuthorities;
	}
	public String getAuthorities() {
		return authorities;
	}
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	public String getParentUserAuthorities() {
		return parentUserAuthorities;
	}
	public void setParentUserAuthorities(String parentUserAuthorities) {
		this.parentUserAuthorities = parentUserAuthorities;
	}
	public Integer getRoleGroup() {
		return roleGroup;
	}
	public void setRoleGroup(Integer roleGroup) {
		this.roleGroup = roleGroup;
	}
	
	
}
