package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LoginUserRoles implements GrantedAuthority,java.io.Serializable,OldPresist<LoginUserRoles>{

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String authority;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="login_username")
	LoginUser loginUser;
	
	public LoginUserRoles() {}
	public LoginUserRoles(String authority,LoginUser loginUser) {
		this.authority=authority;
		this.loginUser=loginUser;
	}
	@Override
	public String getAuthority() {
		
		return authority;
	}

	/**
	 * @return the loginUser
	 */
	public LoginUser getLoginUser() {
		return loginUser;
	}

	/**
	 * @param loginUser the loginUser to set
	 */
	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}

	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	

	@javax.persistence.Transient transient LoginUserRoles old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LoginUserRoles)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LoginUserRoles gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
