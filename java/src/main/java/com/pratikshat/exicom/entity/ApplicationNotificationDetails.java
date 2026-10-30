package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.pratikshat.exicom.interceptor.ApplicationNotificationListener;


@Entity
@javax.persistence.EntityListeners(ApplicationNotificationListener.class)
public class ApplicationNotificationDetails implements java.io.Serializable,OldPresist<ApplicationNotificationDetails>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long notificationId;
	Date nofificationDate;
	String userEmailId;
	String notificationMessage;
	String notificationActionUrl;
	boolean notificationRead;
	/**
	 * @return the notificationId
	 */
	public long getNotificationId() {
		return notificationId;
	}
	/**
	 * @return the nofificationDate
	 */
	public Date getNofificationDate() {
		return nofificationDate;
	}
	/**
	 * @return the userEmailId
	 */
	public String getUserEmailId() {
		return userEmailId;
	}
	/**
	 * @return the notificationMessage
	 */
	public String getNotificationMessage() {
		return notificationMessage;
	}
	/**
	 * @return the notificationActionUrl
	 */
	public String getNotificationActionUrl() {
		return notificationActionUrl;
	}
	/**
	 * @return the notificationRead
	 */
	public boolean isNotificationRead() {
		return notificationRead;
	}
	/**
	 * @param notificationId the notificationId to set
	 */
	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}
	/**
	 * @param nofificationDate the nofificationDate to set
	 */
	public void setNofificationDate(Date nofificationDate) {
		this.nofificationDate = nofificationDate;
	}
	/**
	 * @param userEmailId the userEmailId to set
	 */
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	/**
	 * @param notificationMessage the notificationMessage to set
	 */
	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}
	/**
	 * @param notificationActionUrl the notificationActionUrl to set
	 */
	public void setNotificationActionUrl(String notificationActionUrl) {
		this.notificationActionUrl = notificationActionUrl;
	}
	/**
	 * @param notificationRead the notificationRead to set
	 */
	public void setNotificationRead(boolean notificationRead) {
		this.notificationRead = notificationRead;
	}
	

	@javax.persistence.Transient transient ApplicationNotificationDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ApplicationNotificationDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ApplicationNotificationDetails gOld() {
		return old;
	}
	public String toString() {
			return ""+notificationId;
	}
}
