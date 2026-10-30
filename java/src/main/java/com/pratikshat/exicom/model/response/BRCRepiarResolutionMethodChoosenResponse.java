package com.pratikshat.exicom.model.response;

public class BRCRepiarResolutionMethodChoosenResponse {

	int resolutionMethodId;
	String resolutionMethodName;
	
	public BRCRepiarResolutionMethodChoosenResponse(int resolutionMethodId, String resolutionMethodName) {
		this.resolutionMethodId = resolutionMethodId;
		this.resolutionMethodName = resolutionMethodName;
	}
	public int getResolutionMethodId() {
		return resolutionMethodId;
	}
	public void setResolutionMethodId(int resolutionMethodId) {
		this.resolutionMethodId = resolutionMethodId;
	}
	public String getResolutionMethodName() {
		return resolutionMethodName;
	}
	public void setResolutionMethodName(String resolutionMethodName) {
		this.resolutionMethodName = resolutionMethodName;
	}
	
	
}
