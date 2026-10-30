package com.pratikshat.exicom.model.response;

public class CaseTypesForList {
	long caseTypeId;
	String caseTypeName;
	
	public CaseTypesForList() {}
	public CaseTypesForList(long caseTypeId,String caseTypeName) {
		this.caseTypeId=caseTypeId;
		this.caseTypeName=caseTypeName;
	}
	/**
	 * @return the caseTypeId
	 */
	public final long getCaseTypeId() {
		return caseTypeId;
	}
	/**
	 * @return the caseTypeName
	 */
	public final String getCaseTypeName() {
		return caseTypeName;
	}
	/**
	 * @param caseTypeId the caseTypeId to set
	 */
	public final void setCaseTypeId(long caseTypeId) {
		this.caseTypeId = caseTypeId;
	}
	/**
	 * @param caseTypeName the caseTypeName to set
	 */
	public final void setCaseTypeName(String caseTypeName) {
		this.caseTypeName = caseTypeName;
	}
	
}
