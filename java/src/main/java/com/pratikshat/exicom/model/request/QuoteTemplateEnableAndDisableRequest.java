package com.pratikshat.exicom.model.request;

public class QuoteTemplateEnableAndDisableRequest {

	long id;
	boolean isActive;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
