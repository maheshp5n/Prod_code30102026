package com.pratikshat.exicom.entity;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.annotation.Transient;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({ObjectAuditListener.class})
public class BtPreRegistrationCaseActionLog implements java.io.Serializable,OldPresist<BtPreRegistrationCaseActionLog>{

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long id;
	
	String action;
	
	@Column(length=1000)
	String comment;
	
	Date timestamp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails actionTakenBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	BtPreRegistrationCases preRegistrationCases;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public EmployeeDetails getActionTakenBy() {
		return actionTakenBy;
	}

	public void setActionTakenBy(EmployeeDetails actionTakenBy) {
		this.actionTakenBy = actionTakenBy;
	}

	public BtPreRegistrationCases getPreRegistrationCases() {
		return preRegistrationCases;
	}

	public void setPreRegistrationCases(BtPreRegistrationCases preRegistrationCases) {
		this.preRegistrationCases = preRegistrationCases;
	}

	@Transient transient BtPreRegistrationCaseActionLog old;
	@Override
	@PostLoad
	public void old() {
		old=(BtPreRegistrationCaseActionLog) SerializationUtils.clone(this);
		
	}

	@Override
	public BtPreRegistrationCaseActionLog gOld() {
		return this.old;
	}

}
