package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class SmsLog implements java.io.Serializable,OldPresist<SmsLog>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String toMobileNumber;
	String message;
	String sender;
	int responseCode;
	String response;
	Date logDate;
	public SmsLog() {}
	
	
	
	public SmsLog( String toMobileNumber, String message, String sender, int responseCode, String response,Date logDate) {
		
		this.toMobileNumber = toMobileNumber;
		this.message = message;
		this.sender = sender;
		this.responseCode = responseCode;
		this.response = response;
		this.logDate=logDate;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToMobileNumber() {
		return toMobileNumber;
	}
	public void setToMobileNumber(String toMobileNumber) {
		this.toMobileNumber = toMobileNumber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	

	@javax.persistence.Transient transient SmsLog old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(SmsLog)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public SmsLog gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
