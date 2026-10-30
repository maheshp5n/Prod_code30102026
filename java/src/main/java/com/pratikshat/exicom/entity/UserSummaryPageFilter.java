package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class UserSummaryPageFilter implements java.io.Serializable,OldPresist<UserSummaryPageFilter>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	UserProfile userProfile;
	
	@ManyToOne(fetch=FetchType.LAZY)
	SummaryPageFilter pageFilter;
	
	public UserSummaryPageFilter() {}
	
	public UserSummaryPageFilter(Long id) {
		this.id=id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public SummaryPageFilter getPageFilter() {
		return pageFilter;
	}

	public void setPageFilter(SummaryPageFilter pageFilter) {
		this.pageFilter = pageFilter;
	}
	

	@javax.persistence.Transient transient UserSummaryPageFilter old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(UserSummaryPageFilter)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public UserSummaryPageFilter gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
