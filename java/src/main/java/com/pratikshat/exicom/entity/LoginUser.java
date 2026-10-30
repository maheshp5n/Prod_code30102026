package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Cacheable
@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LoginUser implements UserDetails,java.io.Serializable,OldPresist<LoginUser>{

	private static final long serialVersionUID = 1L;
	@NotNull
	@NotBlank
	@NotEmpty
	@Id
	String username;
	@NotNull
	@NotBlank
	@NotEmpty
	String password;
	boolean accountNonExpired;
	boolean accountNonLocked;
	boolean credentialsNonExpired;
	boolean enabled;
	@OneToMany(mappedBy = "loginUser",fetch=FetchType.EAGER)
	Set<LoginUserRoles> authorities; 
	
	public LoginUser() {}
	public LoginUser(String username) {
		this.username=username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param accountNonExpired the accountNonExpired to set
	 */
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * @param accountNonLocked the accountNonLocked to set
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	/**
	 * @param credentialsNonExpired the credentialsNonExpired to set
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.enabled;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(Set<LoginUserRoles> authorities) {
		this.authorities = authorities;
	}


	@javax.persistence.Transient transient LoginUser old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LoginUser)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LoginUser gOld() {
		return old;
	}
	public String toString() {
			return ""+username;
	}
}
