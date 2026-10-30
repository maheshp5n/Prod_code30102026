package com.pratikshat.exicom.entity;

import javax.persistence.Column;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class WorkFlowEntity implements java.io.Serializable,OldPresist<WorkFlowEntity>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	Long id;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	ObjectDefinition object;
	boolean active;
	@Column(length = 10)
	String onevent;
	@Column(length = 10)
	String dmlCommand;
	@Column(length = 1000)
	String classLoaderUrl;
	String className;
	@Column(length=500)
	String description;
	@Column(columnDefinition = "TEXT")
	String codeSnip;
	
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails owner;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ObjectDefinition getObject() {
		return object;
	}
	public void setObject(ObjectDefinition object) {
		this.object = object;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getDmlCommand() {
		return dmlCommand;
	}
	public void setDmlCommand(String dmlCommand) {
		this.dmlCommand = dmlCommand;
	}
	public EmployeeDetails getOwner() {
		return owner;
	}
	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public java.util.Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getClassLoaderUrl() {
		return classLoaderUrl;
	}
	public void setClassLoaderUrl(String classLoaderUrl) {
		this.classLoaderUrl = classLoaderUrl;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOnevent() {
		return onevent;
	}
	public void setOnevent(String onevent) {
		this.onevent = onevent;
	}
	public String getCodeSnip() {
		return codeSnip;
	}
	public void setCodeSnip(String codeSnip) {
		this.codeSnip = codeSnip;
	}
	

	@javax.persistence.Transient transient WorkFlowEntity old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(WorkFlowEntity)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public WorkFlowEntity gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
