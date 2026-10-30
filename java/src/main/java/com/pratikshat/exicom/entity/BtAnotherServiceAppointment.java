package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtAnotherServiceAppointment extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtAnotherServiceAppointment> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	String manualId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	BtCase caseDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	BtServiceAppointment service;
	
	private String remark;
	
	private String status;

	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	public BtAnotherServiceAppointment() {}
	
	public BtAnotherServiceAppointment(long id) {
		
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BtAnotherServiceAppointment getOld() {
		return old;
	}

	public void setOld(BtAnotherServiceAppointment old) {
		this.old = old;
	}

	public BtCase getCaseDetails() {
		return caseDetails;
	}

	public void setCaseDetails(BtCase caseDetails) {
		this.caseDetails = caseDetails;
	}

	public BtServiceAppointment getService() {
		return service;
	}

	public void setService(BtServiceAppointment service) {
		this.service = service;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Transient transient BtAnotherServiceAppointment old;
	
	@Override
	public void old() {
		old=(BtAnotherServiceAppointment) SerializationUtils.clone(this);
		
	}

	@Override
	public BtAnotherServiceAppointment gOld() {
		return old;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(Long id) {
		
		this.id=id;
	}

	@Override
	public EmployeeDetails getOwner() {
		// TODO Auto-generated method stub
		return this.owner;
	}

	@Override
	public void setOwner(EmployeeDetails owner) {
		
		this.owner=owner;
	}

	@Override
	public String getManualId() {
		
		return this.manualId;
	}

	@Override
	public void setManualId(String manualId) {
		this.manualId=manualId;
	}
	
	public String toString() {
		return ""+id;
	}

}
