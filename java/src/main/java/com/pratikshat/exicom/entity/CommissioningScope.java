package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

@Entity
public class CommissioningScope implements java.io.Serializable,OldPresist<CommissioningScope>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private boolean active;
	
	public int getId() {
		return id;
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	@Transient transient CommissioningScope old;
	
	@Override
	public void old() {
		old=(CommissioningScope) SerializationUtils.clone(this);
		
	}
	@Override
	public CommissioningScope gOld() {
		// TODO Auto-generated method stub
		return old;
	}
	

}
