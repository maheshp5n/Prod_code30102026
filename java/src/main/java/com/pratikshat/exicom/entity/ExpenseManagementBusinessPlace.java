package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity

@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ExpenseManagementBusinessPlace implements java.io.Serializable,OldPresist<ExpenseManagementBusinessPlace>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int BusinessPlaceId;
	String BusinessPlaceName;
	@ManyToOne(fetch = FetchType.LAZY)
	BusinessDomain division;
	boolean synced;
	public ExpenseManagementBusinessPlace() {
		
	}
	public ExpenseManagementBusinessPlace(int businessPlaceId) {
		this.BusinessPlaceId = businessPlaceId;
	}
	public int getBusinessPlaceId() {
		return BusinessPlaceId;
	}
	public void setBusinessPlaceId(int businessPlaceId) {
		this.BusinessPlaceId = businessPlaceId;
	}
	public String getBusinessPlaceName() {
		return BusinessPlaceName;
	}
	public void setBusinessPlaceName(String businessPlaceName) {
		this.BusinessPlaceName = businessPlaceName;
	}
	
	public BusinessDomain getDivision() {
		return division;
	}
	public void setDivision(BusinessDomain division) {
		this.division = division;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	public String toString(){
		
		return String.valueOf(BusinessPlaceId);
	 }
	

	@javax.persistence.Transient transient ExpenseManagementBusinessPlace old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ExpenseManagementBusinessPlace)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ExpenseManagementBusinessPlace gOld() {
		return old;
	}}
