package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class BtSiteType implements java.io.Serializable,OldPresist<BtSiteType>{
	
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

	public BusinessDomain getBussinessDomain() {
		return bussinessDomain;
	}

	public void setBussinessDomain(BusinessDomain bussinessDomain) {
		this.bussinessDomain = bussinessDomain;
	}

	public boolean isActivation() {
		return activation;
	}

	public void setActivation(boolean activation) {
		this.activation = activation;
	}

	@javax.persistence.Transient transient BtSiteType old;
	@Override
	public void old() {
		old=(BtSiteType)org.apache.commons.lang.SerializationUtils.clone(this);
	}

	@Override
	public BtSiteType gOld() {
		// TODO Auto-generated method stub
		return old;
	}

}