package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class GlobalSearchIndex implements java.io.Serializable,OldPresist<GlobalSearchIndex>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long searchId;
	String searchString;
	String keyName;
	String objectName;
	String objectDetailsUrl;
	String objectIdName;
	String objectIdValue;
	
	public GlobalSearchIndex() {}
	public GlobalSearchIndex(String searchString,String keyName,String objectName,String objectDetailsUrl,String objectIdName,String objectIdValue) {
		this.searchString=searchString;
		this.keyName=keyName;
		this.objectName=objectName;
		this.objectDetailsUrl=objectDetailsUrl;
		this.objectIdName=objectIdName;
		this.objectIdValue=objectIdValue;
	}
	
	/**
	 * @return the searchString
	 */
	public String getSearchString() {
		return searchString;
	}
	/**
	 * @return the keyName
	 */
	public String getKeyName() {
		return keyName;
	}
	/**
	 * @return the objectName
	 */
	public String getObjectName() {
		return objectName;
	}
	/**
	 * @return the objectDetailsUrl
	 */
	public String getObjectDetailsUrl() {
		return objectDetailsUrl;
	}
	/**
	 * @return the objectIdName
	 */
	public String getObjectIdName() {
		return objectIdName;
	}
	/**
	 * @return the objectIdValue
	 */
	public String getObjectIdValue() {
		return objectIdValue;
	}
	/**
	 * @param searchString the searchString to set
	 */
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	/**
	 * @param keyName the keyName to set
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	/**
	 * @param objectName the objectName to set
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	/**
	 * @param objectDetailsUrl the objectDetailsUrl to set
	 */
	public void setObjectDetailsUrl(String objectDetailsUrl) {
		this.objectDetailsUrl = objectDetailsUrl;
	}
	/**
	 * @param objectIdName the objectIdName to set
	 */
	public void setObjectIdName(String objectIdName) {
		this.objectIdName = objectIdName;
	}
	/**
	 * @param objectIdValue the objectIdValue to set
	 */
	public void setObjectIdValue(String objectIdValue) {
		this.objectIdValue = objectIdValue;
	}
	
	

	@javax.persistence.Transient transient GlobalSearchIndex old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(GlobalSearchIndex)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public GlobalSearchIndex gOld() {
		return old;
	}
	public String toString() {
			return ""+searchId;
	}
}
