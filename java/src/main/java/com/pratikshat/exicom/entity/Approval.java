package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Length;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class Approval implements java.io.Serializable,OldPresist<Approval>{

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	String objectName;
	String objectClassName;
	String objectIdFiledName;
	int approvalStages;
	String description;
	boolean active;
	@Length(max=Integer.MAX_VALUE)
	String entryCreteria;
	@Length(max = Integer.MAX_VALUE)
	String initialSubmitters;
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	@ManyToOne
	BusinessDomain businessDomain;
	
	public Approval() {
		
	}
	
	public Approval(long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the objectName
	 */
	public String getObjectName() {
		return objectName;
	}

	/**
	 * @return the objectClassName
	 */
	public String getObjectClassName() {
		return objectClassName;
	}

	/**
	 * @return the objectIdFiledName
	 */
	public String getObjectIdFiledName() {
		return objectIdFiledName;
	}

	/**
	 * @return the approvalStages
	 */
	public int getApprovalStages() {
		return approvalStages;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @return the entryCreteria
	 */
	public String getEntryCreteria() {
		return entryCreteria;
	}

	/**
	 * @return the initialSubmitters
	 */
	public String getInitialSubmitters() {
		return initialSubmitters;
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
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param objectName the objectName to set
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	/**
	 * @param objectClassName the objectClassName to set
	 */
	public void setObjectClassName(String objectClassName) {
		this.objectClassName = objectClassName;
	}

	/**
	 * @param objectIdFiledName the objectIdFiledName to set
	 */
	public void setObjectIdFiledName(String objectIdFiledName) {
		this.objectIdFiledName = objectIdFiledName;
	}

	/**
	 * @param approvalStages the approvalStages to set
	 */
	public void setApprovalStages(int approvalStages) {
		this.approvalStages = approvalStages;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @param entryCreteria the entryCreteria to set
	 */
	public void setEntryCreteria(String entryCreteria) {
		this.entryCreteria = entryCreteria;
	}

	/**
	 * @param initialSubmitters the initialSubmitters to set
	 */
	public void setInitialSubmitters(String initialSubmitters) {
		this.initialSubmitters = initialSubmitters;
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

	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}
	
	

	@javax.persistence.Transient transient Approval old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(Approval)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public Approval gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
