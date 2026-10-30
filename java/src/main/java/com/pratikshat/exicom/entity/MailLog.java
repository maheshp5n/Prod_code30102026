package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class MailLog implements java.io.Serializable,OldPresist<MailLog>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String logBy;
	Date logDate;
	@Column(columnDefinition = "TEXT")
	String mailContent;
	String sendFrom;
	@Column(columnDefinition = "TEXT")
	String sendTo;
	@Column(columnDefinition = "TEXT")
	String sendCc;
	@Column(columnDefinition = "TEXT")
	String subject;
	String objectName;
	String objectId;
	public MailLog() {}
	
	public MailLog(Long id) {
		this.id=id;
	}
	
	public MailLog(String logBy, Date logDate, String mailContent, String sendFrom, String sendTo) {
		this.logBy = logBy;
		this.logDate = logDate;
		this.mailContent = mailContent;
		this.sendFrom = sendFrom;
		this.sendTo = sendTo;
	}
	
	public MailLog(String logBy, Date logDate, String mailContent, String sendFrom, String sendTo, String sendCc, String subject, String objectName, String objectId) {
		this.logBy = logBy;
		this.logDate = logDate;
		this.mailContent = mailContent;
		this.sendFrom = sendFrom;
		this.sendTo = sendTo;
		this.sendCc = sendCc;
		this.subject = subject;
		this.objectName = objectName;
		this.objectId = objectId;
	}
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	

	@javax.persistence.Transient transient MailLog old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(MailLog)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public MailLog gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
