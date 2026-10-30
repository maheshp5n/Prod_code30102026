package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
@Table(name = "btrmadetails")
public class BtRMADetails extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtRMADetails> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String manualId;
	
	
	String rmaStatus;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	BtCase caseDetails;
	
	@ManyToOne(fetch=FetchType.LAZY)
	BtServiceAppointment serviceAppointment;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	Long batteryGrnId;
	
	Date closeDate;
	
	
	@Transient
	transient BtRMADetails old;

	@Override
	@PostLoad
	public void old() {
		old=(BtRMADetails) SerializationUtils.clone(this);
	}

	@Override
	public BtRMADetails gOld() {
	
		return this.old;
	}

	@Override
	public Long getId() {
		
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}

	@Override
	public EmployeeDetails getOwner() {
		
		return this.owner;
	}

	@Override
	public void setOwner(EmployeeDetails u) {
		this.owner=u;
	}

	@Override
	public String getManualId() {
		
		return this.manualId;
	}

	@Override
	public void setManualId(String manualId) {
		this.manualId=manualId;
	}

	public String getRmaStatus() {
		return rmaStatus;
	}

	public void setRmaStatus(String rmaStatus) {
		this.rmaStatus = rmaStatus;
	}

	public BtCase getCaseDetails() {
		return caseDetails;
	}

	public void setCaseDetails(BtCase caseDetails) {
		this.caseDetails = caseDetails;
	}

	public BtServiceAppointment getServiceAppointment() {
		return serviceAppointment;
	}

	public void setServiceAppointment(BtServiceAppointment serviceAppointment) {
		this.serviceAppointment = serviceAppointment;
	}

	public Long getBatteryGrnId() {
		return batteryGrnId;
	}

	public void setBatteryGrnId(Long batteryGrnId) {
		this.batteryGrnId = batteryGrnId;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	
	public String toString() {
		return String.valueOf(this.id); 
	}

}
