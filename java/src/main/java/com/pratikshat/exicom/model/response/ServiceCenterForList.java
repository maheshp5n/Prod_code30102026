package com.pratikshat.exicom.model.response;

public class ServiceCenterForList {
	long legalEntityId;
	String legalEntityName;
	
	public ServiceCenterForList() {}
	public ServiceCenterForList(long legalEntityId,String legalEnityName) {
		this.legalEntityId=legalEntityId;
		this.legalEntityName=legalEnityName;
	}
	
	/**
	 * @return the legalEntityId
	 */
	public long getLegalEntityId() {
		return legalEntityId;
	}
	/**
	 * @return the legalEntityName
	 */
	public String getLegalEntityName() {
		return legalEntityName;
	}
	/**
	 * @param legalEntityId the legalEntityId to set
	 */
	public void setLegalEntityId(long legalEntityId) {
		this.legalEntityId = legalEntityId;
	}
	/**
	 * @param legalEntityName the legalEntityName to set
	 */
	public void setLegalEntityName(String legalEntityName) {
		this.legalEntityName = legalEntityName;
	}
	
}
