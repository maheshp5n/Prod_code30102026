package com.pratikshat.exicom.model.request;

public class PrcActionRequest {

	long caseid;
	String action;
	String comment;
	
	
	
	public long getCaseid() {
		return caseid;
	}
	public void setCaseid(long caseid) {
		this.caseid = caseid;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
