package com.pratikshat.exicom.entity;


import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;


@Entity
@EntityListeners(ObjectAuditListener.class)
public class BtSiteStatus implements java.io.Serializable,OldPresist<BtSiteStatus> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private boolean activation;

	@ManyToOne
	private BusinessDomain bussinessDomain;
	
	public int getId() {
		return id;
	}
	public boolean isActivation() {
		return activation;
	}

	public void setActivation(boolean activation) {
		this.activation = activation;
	}


	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BusinessDomain getBussinessDomain() {
		return bussinessDomain;
	}

	public void setBussinessDomain(BusinessDomain bussinessDomain) {
		this.bussinessDomain = bussinessDomain;
	}

	@javax.persistence.Transient transient BtSiteStatus old;
	@Override
	@PostLoad
	public void old() {
		old=(BtSiteStatus) SerializationUtils.clone(this);
		
	}

	@Override
	public BtSiteStatus gOld() {
		// TODO Auto-generated method stub
		return old;
	}

}