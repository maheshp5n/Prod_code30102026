package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class PrcProblemReported implements java.io.Serializable,OldPresist<PrcProblemReported> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long probleReportedId;
	String problemName;
	@ManyToOne(fetch=FetchType.LAZY)
	PreRegistrationCases preRegistrationCases;
	
	public PrcProblemReported() {}
	
	public PrcProblemReported(long problemReportedId) {
		this.probleReportedId=problemReportedId;
	}
	
	public PrcProblemReported(long problemReportedId,String problemName) {
		this.probleReportedId=problemReportedId;
		this.problemName=problemName;
	}

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

	public PreRegistrationCases getPreRegistrationCases() {
		return preRegistrationCases;
	}

	public void setPreRegistrationCases(PreRegistrationCases preRegistrationCases) {
		this.preRegistrationCases = preRegistrationCases;
	}
	
	@Transient transient PrcProblemReported old;
	@Override
	@PostLoad
	public void old() {
		old=(PrcProblemReported)SerializationUtils.clone(this);
	}
	@Override
	public PrcProblemReported gOld() {
		// TODO Auto-generated method stub
		return old;
	}
	
}
