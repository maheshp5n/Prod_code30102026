package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ControllerInformationMethods implements java.io.Serializable,OldPresist<ControllerInformationMethods>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long methodId;
	String methodName;
	String requestUrl;
	String requestMethod;
	String returnTypes;
	@ManyToOne
	ControllerInformation controllerInformation;
	
	public ControllerInformationMethods() {}
	public ControllerInformationMethods(long methodId) {
		this.methodId=methodId;
	}
	public ControllerInformationMethods(String methodName,String requestUrl,String requestMethod,String returnTypes,String controllerInformationName) {
		this.methodName=methodName;
		this.requestUrl=requestUrl;
		this.requestMethod=requestMethod;
		this.returnTypes=returnTypes;
		this.controllerInformation=this.controllerInformation==null?new ControllerInformation(controllerInformationName):this.controllerInformation;
	}
	public ControllerInformationMethods(long methodId,String methodName,String requestUrl,String requestMethod,String returnTypes) {
		this.methodId=methodId;
		this.methodName=methodName;
		this.requestUrl=requestUrl;
		this.requestMethod=requestMethod;
		this.returnTypes=returnTypes;
	}
	/**
	 * @return the methodId
	 */
	public long getMethodId() {
		return methodId;
	}
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @return the requestUrl
	 */
	public String getRequestUrl() {
		return requestUrl;
	}
	/**
	 * @return the requestMethod
	 */
	public String getRequestMethod() {
		return requestMethod;
	}
	/**
	 * @param methodId the methodId to set
	 */
	public void setMethodId(long methodId) {
		this.methodId = methodId;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @param requestUrl the requestUrl to set
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	/**
	 * @param requestMethod the requestMethod to set
	 */
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	

	@javax.persistence.Transient transient ControllerInformationMethods old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ControllerInformationMethods)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ControllerInformationMethods gOld() {
		return old;
	}
	public String toString() {
			return ""+methodId;
	}
}
