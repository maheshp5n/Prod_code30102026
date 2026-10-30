package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class SummaryPageFilter implements java.io.Serializable,OldPresist<SummaryPageFilter>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	UINavigationModules navigationModule;
	String fileterName;
	String filterParam;
	
	public SummaryPageFilter() {}
	
	public SummaryPageFilter(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UINavigationModules getNavigationModule() {
		return navigationModule;
	}
	public void setNavigationModule(UINavigationModules navigationModule) {
		this.navigationModule = navigationModule;
	}
	public String getFileterName() {
		return fileterName;
	}
	public void setFileterName(String fileterName) {
		this.fileterName = fileterName;
	}
	public String getFilterParam() {
		return filterParam;
	}
	public void setFilterParam(String filterParam) {
		this.filterParam = filterParam;
	}
	

	@javax.persistence.Transient transient SummaryPageFilter old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(SummaryPageFilter)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public SummaryPageFilter gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
