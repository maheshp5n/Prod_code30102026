package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.annotation.Transient;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class BtPreRegisterReport implements Serializable, OldPresist<BtPreRegisterReport>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long probleReportedId;
	String problemName;
	@ManyToOne(fetch=FetchType.LAZY)
	BtPreRegistrationCases preRegistrationCases;

	public long getProbleReportedId() {
		return probleReportedId;
	}

	public void setProbleReportedId(long probleReportedId) {
		this.probleReportedId = probleReportedId;
	}

	public String getProblemName() {
		return problemName;
	}

	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

	public BtPreRegistrationCases getPreRegistrationCases() {
		return preRegistrationCases;
	}

	public void setPreRegistrationCases(BtPreRegistrationCases preRegistrationCases) {
		this.preRegistrationCases = preRegistrationCases;
	}

	@Transient transient BtPreRegisterReport old;
	
	@PostLoad
	@Override
	public void old() {
		old=(BtPreRegisterReport) SerializationUtils.clone(this);
		
	}

	@Override
	public BtPreRegisterReport gOld() {
		// TODO Auto-generated method stub
		return old;
	}

}
