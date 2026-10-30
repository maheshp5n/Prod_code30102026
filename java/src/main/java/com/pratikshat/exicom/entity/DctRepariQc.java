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
public class DctRepariQc implements java.io.Serializable,OldPresist<DctRepariQc>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(fetch=FetchType.LAZY)
	DctRepair repari;
	String heatRunStatus;
	String faultObservedInHeatRun;
	String reworkAfterFail;
	String finalStatus;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DctRepair getRepari() {
		return repari;
	}
	public void setRepari(DctRepair repari) {
		this.repari = repari;
	}
	public String getHeatRunStatus() {
		return heatRunStatus;
	}
	public void setHeatRunStatus(String heatRunStatus) {
		this.heatRunStatus = heatRunStatus;
	}
	public String getFaultObservedInHeatRun() {
		return faultObservedInHeatRun;
	}
	public void setFaultObservedInHeatRun(String faultObservedInHeatRun) {
		this.faultObservedInHeatRun = faultObservedInHeatRun;
	}
	public String getReworkAfterFail() {
		return reworkAfterFail;
	}
	public void setReworkAfterFail(String reworkAfterFail) {
		this.reworkAfterFail = reworkAfterFail;
	}
	public String getFinalStatus() {
		return finalStatus;
	}
	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}
	
	
	

	@javax.persistence.Transient transient DctRepariQc old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(DctRepariQc)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public DctRepariQc gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
