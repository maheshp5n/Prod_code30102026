package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class UserSummaryPageFilterPin implements java.io.Serializable,OldPresist<UserSummaryPageFilterPin>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	UserSummaryPageFilter filter;
	
	boolean pind;
	
	public UserSummaryPageFilterPin() {}
	public UserSummaryPageFilterPin(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmployeeDetails getUser() {
		return user;
	}

	public void setUser(EmployeeDetails user) {
		this.user = user;
	}

	public UserSummaryPageFilter getFilter() {
		return filter;
	}

	public void setFilter(UserSummaryPageFilter filter) {
		this.filter = filter;
	}

	public boolean isPind() {
		return pind;
	}

	public void setPind(boolean pind) {
		this.pind = pind;
	}
	
	

	@javax.persistence.Transient transient UserSummaryPageFilterPin old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(UserSummaryPageFilterPin)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public UserSummaryPageFilterPin gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
