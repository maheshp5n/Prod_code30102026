package com.pratikshat.exicom.model.response;

public class CaseReasonForList {
	long caseReasonId;
	String caseReasonName;
	public CaseReasonForList() {}
	public CaseReasonForList(long caseReasonId,String caseReasonName) {
		this.caseReasonId=caseReasonId;
		this.caseReasonName=caseReasonName;
	}
	/**
	 * @return the caseReasonId
	 */
	public long getCaseReasonId() {
		return caseReasonId;
	}
	/**
	 * @return the caseReasonName
	 */
	public String getCaseReasonName() {
		return caseReasonName;
	}
	/**
	 * @param caseReasonId the caseReasonId to set
	 */
	public void setCaseReasonId(long caseReasonId) {
		this.caseReasonId = caseReasonId;
	}
	/**
	 * @param caseReasonName the caseReasonName to set
	 */
	public void setCaseReasonName(String caseReasonName) {
		this.caseReasonName = caseReasonName;
	}
	
}
