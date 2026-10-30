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
public class CaseProblemReported implements java.io.Serializable,OldPresist<CaseProblemReported>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long probleReportedId;
	String problemName;
	@ManyToOne(fetch=FetchType.LAZY)
	CaseDetails caseDetails;
	boolean synced;
	public CaseProblemReported() {}
	public CaseProblemReported(long problemReportedId) {
		this.probleReportedId=problemReportedId;
	}
	public CaseProblemReported(long problemReportedId,String problemName) {
		this.probleReportedId=problemReportedId;
		this.problemName=problemName;
	}
	/**
	 * @return the probleReportedId
	 */
	public long getProbleReportedId() {
		return probleReportedId;
	}
	/**
	 * @return the problemName
	 */
	public String getProblemName() {
		return problemName;
	}
	/**
	 * @return the caseDetails
	 */
	public CaseDetails getCaseDetails() {
		return caseDetails;
	}
	/**
	 * @param probleReportedId the probleReportedId to set
	 */
	public void setProbleReportedId(long probleReportedId) {
		this.probleReportedId = probleReportedId;
	}
	/**
	 * @param problemName the problemName to set
	 */
	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}
	/**
	 * @param caseDetails the caseDetails to set
	 */
	public void setCaseDetails(CaseDetails caseDetails) {
		this.caseDetails = caseDetails;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	

	@javax.persistence.Transient transient CaseProblemReported old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CaseProblemReported)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CaseProblemReported gOld() {
		return old;
	}
	public String toString() {
			return ""+probleReportedId;
	}
}
