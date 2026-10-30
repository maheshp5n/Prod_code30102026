package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class MailerEntity implements java.io.Serializable,OldPresist<MailerEntity>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails lastModifiedBy;
	Date createdDate;
	Date lastModifiedDate;
	String smtpServer;
	int smtpPort;
	boolean active;
	boolean requiredAuthentication;
	boolean requiredTlsStart;
	boolean requiredSsl;
	int sslPort;
	String defaultUserName;
	String defaultPassword;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public EmployeeDetails getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(EmployeeDetails lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getSmtpServer() {
		return smtpServer;
	}
	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}
	public int getSmtpPort() {
		return smtpPort;
	}
	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isRequiredAuthentication() {
		return requiredAuthentication;
	}
	public void setRequiredAuthentication(boolean requiredAuthentication) {
		this.requiredAuthentication = requiredAuthentication;
	}
	public boolean isRequiredTlsStart() {
		return requiredTlsStart;
	}
	public void setRequiredTlsStart(boolean requiredTlsStart) {
		this.requiredTlsStart = requiredTlsStart;
	}
	public boolean isRequiredSsl() {
		return requiredSsl;
	}
	public void setRequiredSsl(boolean requiredSsl) {
		this.requiredSsl = requiredSsl;
	}
	public int getSslPort() {
		return sslPort;
	}
	public void setSslPort(int sslPort) {
		this.sslPort = sslPort;
	}
	public String getDefaultUserName() {
		return defaultUserName;
	}
	public void setDefaultUserName(String defaultUserName) {
		this.defaultUserName = defaultUserName;
	}
	public String getDefaultPassword() {
		return defaultPassword;
	}
	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}
	
	
	

	@javax.persistence.Transient transient MailerEntity old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(MailerEntity)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public MailerEntity gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
