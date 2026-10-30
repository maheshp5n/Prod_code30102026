package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class MailLogAttachements implements java.io.Serializable,OldPresist<MailLogAttachements>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(fetch=FetchType.LAZY)
	MailLog mailLog;
	String filepath;
	
	public MailLogAttachements() {}
	
	public MailLogAttachements(Long id) {
		this.id=id;
	}
	
	public MailLogAttachements(MailLog mailLog, String filepath) {
		this.mailLog = mailLog;
		this.filepath = filepath;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MailLog getMailLog() {
		return mailLog;
	}
	public void setMailLog(MailLog mailLog) {
		this.mailLog = mailLog;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	

	@javax.persistence.Transient transient MailLogAttachements old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(MailLogAttachements)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public MailLogAttachements gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
