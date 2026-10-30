package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class DctRepariResulation implements java.io.Serializable,OldPresist<DctRepariResulation>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	DctRepair dctRepair;
	
	@ManyToOne(fetch=FetchType.LAZY)
	DctRepairProblemMaster resulation;

	public DctRepariResulation() {}
	public DctRepariResulation(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DctRepair getDctRepair() {
		return dctRepair;
	}

	public void setDctRepair(DctRepair dctRepair) {
		this.dctRepair = dctRepair;
	}

	public DctRepairProblemMaster getResulation() {
		return resulation;
	}

	public void setResulation(DctRepairProblemMaster resulation) {
		this.resulation = resulation;
	}
	
	
	
	

	@javax.persistence.Transient transient DctRepariResulation old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(DctRepariResulation)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public DctRepariResulation gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
