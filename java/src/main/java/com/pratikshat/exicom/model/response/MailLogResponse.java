package com.pratikshat.exicom.model.response;

import java.util.Date;
import java.util.List;

public class MailLogResponse {

	Long id;
	String logBy;
	Date logDate;
	String mailContent;
	String sendFrom;
	String sendTo;
	String sendCc;
	String subject;
	String objectName;
	String objectId;
	
	List<String> attachemt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogBy() {
		return logBy;
	}

	public void setLogBy(String logBy) {
		this.logBy = logBy;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getSendFrom() {
		return sendFrom;
	}

	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getSendCc() {
		return sendCc;
	}

	public void setSendCc(String sendCc) {
		this.sendCc = sendCc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public List<String> getAttachemt() {
		return attachemt;
	}

	public void setAttachemt(List<String> attachemt) {
		this.attachemt = attachemt;
	}
	
	
}
