package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ObjectLog implements java.io.Serializable,OldPresist<ObjectLog>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String objectName;
	String objectId;
	String keyName;
	@Column(columnDefinition = "TEXT")
	String value;
	@Column(columnDefinition = "TEXT")
	String oldValue;
	@CreatedDate
	Date createdDate;
	@ManyToOne
	@CreatedBy
	EmployeeDetails loggedBy;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public EmployeeDetails getLoggedBy() {
		return loggedBy;
	}
	public void setLoggedBy(EmployeeDetails loggedBy) {
		this.loggedBy = loggedBy;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	
	

	@javax.persistence.Transient transient ObjectLog old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ObjectLog)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ObjectLog gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
