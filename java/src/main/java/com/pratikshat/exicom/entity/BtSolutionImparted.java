package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

@Entity
public class BtSolutionImparted implements Serializable,OldPresist<BtSolutionImparted>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private boolean activation;
	
	
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

	public boolean isActivation() {
		return activation;
	}

	public void setActivation(boolean activation) {
		this.activation = activation;
	}

	@Transient transient BtSolutionImparted old;
	@Override
	public void old() {
		old= (BtSolutionImparted) SerializationUtils.clone(this);
		
	}

	@Override
	public BtSolutionImparted gOld() {
		return old;
	}
	
	

}
