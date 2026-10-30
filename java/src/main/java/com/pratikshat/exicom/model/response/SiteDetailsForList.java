package com.pratikshat.exicom.model.response;

public class SiteDetailsForList {
	long siteId;
	String siteName;
	String siteManualId;
	public SiteDetailsForList() {}
	public SiteDetailsForList(long siteId,String siteName,String siteManualId) {
		this.siteId=siteId;
		this.siteName=siteName;
		this.siteManualId = siteManualId;
	}
	/**
	 * @return the siteId
	 */
	public long getSiteId() {
		return siteId;
	}
	/**
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	/**
	 * @param siteName the siteName to set
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteManualId() {
		return siteManualId;
	}
	public void setSiteManualId(String siteManualId) {
		this.siteManualId = siteManualId;
	}
	
}
