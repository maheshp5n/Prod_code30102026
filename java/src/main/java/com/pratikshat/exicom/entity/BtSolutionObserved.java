package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

@Entity
public class BtSolutionObserved implements Serializable,OldPresist<BtSolutionObserved>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private boolean activation;
	
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	BtProblemMaster btProblemMaster;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isActivation() {
		return activation;
	}

	public void setActivation(boolean activation) {
		this.activation = activation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BtProblemMaster getBtProblemMaster() {
		return btProblemMaster;
	}

	public void setBtProblemMaster(BtProblemMaster btProblemMaster) {
		this.btProblemMaster = btProblemMaster;
	}

	@Transient transient BtSolutionObserved old;
	@Override
	public void old() {
		old= (BtSolutionObserved) SerializationUtils.clone(this);
		
	}

	@Override
	public BtSolutionObserved gOld() {
		
		return old;
	}
	
	
}
