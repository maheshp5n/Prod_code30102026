package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class SSA implements java.io.Serializable,OldPresist<SSA>{

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	
	String name;
	
	@ManyToOne(fetch= FetchType.LAZY)
	CircleDetails circle;
	
	@ManyToOne(fetch= FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	
	@ManyToOne(fetch= FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	@ManyToOne(fetch= FetchType.LAZY)
	EmployeeDetails owner;
	
	public SSA() {
	}

	public SSA(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CircleDetails getCircle() {
		return circle;
	}

	public void setCircle(CircleDetails circle) {
		this.circle = circle;
	}

	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public EmployeeDetails getOwner() {
		return owner;
	}

	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}

	@javax.persistence.Transient transient SSA old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(SSA)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public SSA gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
