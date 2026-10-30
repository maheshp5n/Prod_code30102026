package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class PreRegistrationCaseActionLog implements java.io.Serializable,OldPresist<PreRegistrationCaseActionLog> {

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
	PreRegistrationCases preRegistrationCases;

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

	public PreRegistrationCases getPreRegistrationCases() {
		return preRegistrationCases;
	}

	public void setPreRegistrationCases(PreRegistrationCases preRegistrationCases) {
		this.preRegistrationCases = preRegistrationCases;
	}
	
	@Transient transient PreRegistrationCaseActionLog old;
	@Override
	@PostLoad
	public void old() {
		old=(PreRegistrationCaseActionLog)SerializationUtils.clone(this);
	}
	@Override
	public PreRegistrationCaseActionLog gOld() {
		// TODO Auto-generated method stub
		return old;
	}
}
