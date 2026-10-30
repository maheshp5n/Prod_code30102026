package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ObjectDefinition implements java.io.Serializable,OldPresist<ObjectDefinition>{
	@Id
	String objectName;
	String packageName;
	String objectLableName;
	String bojectLabelDbName;
	String objectUrl;
	
	public ObjectDefinition() {}
	public ObjectDefinition(String objectName) {
		this.objectName=objectName;
	}
	/**
	 * @return the objectLableName
	 */
	public String getObjectLableName() {
		return objectLableName;
	}
	/**
	 * @return the bojectLabelDbName
	 */
	public String getBojectLabelDbName() {
		return bojectLabelDbName;
	}
	/**
	 * @param objectLableName the objectLableName to set
	 */
	public void setObjectLableName(String objectLableName) {
		this.objectLableName = objectLableName;
	}
	/**
	 * @param bojectLabelDbName the bojectLabelDbName to set
	 */
	public void setBojectLabelDbName(String bojectLabelDbName) {
		this.bojectLabelDbName = bojectLabelDbName;
	}
	/**
	 * @return the objectName
	 */
	public String getObjectName() {
		return objectName;
	}
	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}
	/**
	 * @param objectName the objectName to set
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getObjectUrl() {
		return objectUrl;
	}
	public void setObjectUrl(String objectUrl) {
		this.objectUrl = objectUrl;
	}
	

	@javax.persistence.Transient transient ObjectDefinition old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ObjectDefinition)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ObjectDefinition gOld() {
		return old;
	}
	public String toString() {
			return ""+objectName;
	}
}
