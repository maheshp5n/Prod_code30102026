package com.pratikshat.exicom.model.response;

public class BrcfaultObservedResponse {
	long id;
	int domainId;
	String domainName;
	String faultObserved;
	public BrcfaultObservedResponse() {}
	public BrcfaultObservedResponse(com.pratikshat.exicom.entity.FaultObserved observed) {
		this.id=observed.getId();
		if(observed.getDomain()!=null) {
			this.domainId=observed.getDomain().getBusinessDomainId();
			this.domainName=observed.getDomain().getBusinessDomainName();
		}
		this.faultObserved=observed.getFaultObserved();
		
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the domainId
	 */
	public int getDomainId() {
		return domainId;
	}
	/**
	 * @return the domainName
	 */
	public String getDomainName() {
		return domainName;
	}
	/**
	 * @return the faultObserved
	 */
	public String getFaultObserved() {
		return faultObserved;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param domainId the domainId to set
	 */
	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}
	/**
	 * @param domainName the domainName to set
	 */
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	/**
	 * @param faultObserved the faultObserved to set
	 */
	public void setFaultObserved(String faultObserved) {
		this.faultObserved = faultObserved;
	}
	
}
