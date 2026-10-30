package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
public class ActiveAlarms implements Serializable,OldPresist<ActiveAlarms>  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String typeName;
	private boolean activation;
	
	@ManyToOne
	private BusinessDomain bussinessDomain;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public boolean isActivation() {
		return activation;
	}

	public void setActivation(boolean activation) {
		this.activation = activation;
	}

	public BusinessDomain getBussinessDomain() {
		return bussinessDomain;
	}

	public void setBussinessDomain(BusinessDomain bussinessDomain) {
		this.bussinessDomain = bussinessDomain;
	}

	@Transient transient ActiveAlarms old;
	@PostLoad
	@Override
	public void old() {
		old=(ActiveAlarms) SerializationUtils.clone(this);
		
	}

	@Override
	public ActiveAlarms gOld() {
		
		return this.old;
	}
	
	

}
