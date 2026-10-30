package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class BtResolutionMethod implements Serializable,OldPresist<BtResolutionMethod>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private boolean actiation;
	
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
	public boolean isActiation() {
		return actiation;
	}
	public void setActiation(boolean actiation) {
		this.actiation = actiation;
	}
	@Transient transient BtResolutionMethod old;
	
	@Override
	public void old() {
		old=(BtResolutionMethod) SerializationUtils.clone(this);
		
	}
	@Override
	public BtResolutionMethod gOld() {
		// TODO Auto-generated method stub
		return old;
	}

}
