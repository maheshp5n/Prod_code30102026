package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ControllerInformation implements java.io.Serializable,OldPresist<ControllerInformation>{
	@Id
	String controllerObjectName;
	String requestUrl;
	String controllerType;
	public ControllerInformation() {}
	public ControllerInformation(String controllerInformationName) {
		this.controllerObjectName=controllerInformationName;
	}
	public ControllerInformation(String controllerObjectName,String requestUrl,String controllerType) {
		this.controllerObjectName=controllerObjectName;
		this.requestUrl=requestUrl;
		this.controllerType=controllerType;
	}
	/**
	 * @return the controllerObjectName
	 */
	public String getControllerObjectName() {
		return controllerObjectName;
	}
	/**
	 * @return the requestUrl
	 */
	public String getRequestUrl() {
		return requestUrl;
	}
	/**
	 * @return the controllerType
	 */
	public String getControllerType() {
		return controllerType;
	}
	/**
	 * @param controllerObjectName the controllerObjectName to set
	 */
	public void setControllerObjectName(String controllerObjectName) {
		this.controllerObjectName = controllerObjectName;
	}
	/**
	 * @param requestUrl the requestUrl to set
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	/**
	 * @param controllerType the controllerType to set
	 */
	public void setControllerType(String controllerType) {
		this.controllerType = controllerType;
	}
	

	@javax.persistence.Transient transient ControllerInformation old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ControllerInformation)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ControllerInformation gOld() {
		return old;
	}
	public String toString() {
			return ""+controllerObjectName;
	}
}
