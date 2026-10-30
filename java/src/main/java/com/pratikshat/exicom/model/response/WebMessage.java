package com.pratikshat.exicom.model.response;

public class WebMessage {
	String type;
	String message;
	
	public WebMessage(String type,String message) {
		this.type=type;
		this.message=message;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
