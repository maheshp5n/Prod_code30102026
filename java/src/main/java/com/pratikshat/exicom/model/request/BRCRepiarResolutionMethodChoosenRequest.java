package com.pratikshat.exicom.model.request;

public class BRCRepiarResolutionMethodChoosenRequest {
	long id;
	long repariItemId;
	int resolutionMethodsId;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the repariItemId
	 */
	public long getRepariItemId() {
		return repariItemId;
	}
	/**
	 * @return the resolutionMethodsId
	 */
	public int getResolutionMethodsId() {
		return resolutionMethodsId;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param repariItemId the repariItemId to set
	 */
	public void setRepariItemId(long repariItemId) {
		this.repariItemId = repariItemId;
	}
	/**
	 * @param resolutionMethodsId the resolutionMethodsId to set
	 */
	public void setResolutionMethodsId(int resolutionMethodsId) {
		this.resolutionMethodsId = resolutionMethodsId;
	}
	
	
}
