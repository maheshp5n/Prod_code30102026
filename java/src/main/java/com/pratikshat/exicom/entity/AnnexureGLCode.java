package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class AnnexureGLCode implements java.io.Serializable,OldPresist<AnnexureGLCode>{

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	
	String name;
	
	double amount;
	double taxRate;
	
	public AnnexureGLCode() {
	}

	public AnnexureGLCode(int id) {
		this.id = id;
	}
	
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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	@javax.persistence.Transient transient AnnexureGLCode old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(AnnexureGLCode)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public AnnexureGLCode gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
