package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class ServiceRecource implements java.io.Serializable,OldPresist<ServiceRecource>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long serviceResourceId;
	String name;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Location location;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ResourceType recourceType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ContactDetails contact;
	
	boolean isActive;
	
	@ManyToOne
	BusinessDomain businessDomain;
	
	public ServiceRecource() {}
	public ServiceRecource(long serviceRecourceId ) {
		this.serviceResourceId=serviceRecourceId;
	}
	
	/**
	 * @return the serviceResourceId
	 */
	public long getServiceResourceId() {
		return serviceResourceId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the user
	 */
	public EmployeeDetails getUser() {
		return user;
	}
	
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * @param serviceResourceId the serviceResourceId to set
	 */
	public void setServiceResourceId(long serviceResourceId) {
		this.serviceResourceId = serviceResourceId;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(EmployeeDetails user) {
		this.user = user;
	}
	
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	/**
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the modifiedBy
	 */
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public ResourceType getRecourceType() {
		return recourceType;
	}
	public void setRecourceType(ResourceType recourceType) {
		this.recourceType = recourceType;
	}
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	/**
	 * @return the contact
	 */
	public ContactDetails getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(ContactDetails contact) {
		this.contact = contact;
	}
	
	@Transient transient ServiceRecource old;
	
	@Override
	@PostLoad
	public void old() {
		old=(ServiceRecource)SerializationUtils.clone(this);
	}
	@Override
	public ServiceRecource gOld() {
		// TODO Auto-generated method stub
		return old;
	}
	
	public String toString(){
		return String.valueOf(serviceResourceId);
	 }
	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}
	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}

}
