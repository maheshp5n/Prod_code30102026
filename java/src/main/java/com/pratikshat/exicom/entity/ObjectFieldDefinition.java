package com.pratikshat.exicom.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ObjectFieldDefinition implements java.io.Serializable,OldPresist<ObjectFieldDefinition>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long objectFieldId;
	String objectFieldType;
	String objectFieldName;
	String objectFieldLabelName;
	String objectFieldDbName;
	String keyName;
	String keyReferences;
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	ObjectDefinition objectDef;
	/**
	 * @return the objectFieldId
	 */
	public long getObjectFieldId() {
		return objectFieldId;
	}
	/**
	 * @return the objectFieldType
	 */
	public String getObjectFieldType() {
		return objectFieldType;
	}
	/**
	 * @return the objectFieldName
	 */
	public String getObjectFieldName() {
		return objectFieldName;
	}
	/**
	 * @return the objectFieldLabelName
	 */
	public String getObjectFieldLabelName() {
		return objectFieldLabelName;
	}
	/**
	 * @return the objectFieldDbName
	 */
	public String getObjectFieldDbName() {
		return objectFieldDbName;
	}
	/**
	 * @return the keyName
	 */
	public String getKeyName() {
		return keyName;
	}
	/**
	 * @return the keyReferences
	 */
	public String getKeyReferences() {
		return keyReferences;
	}
	/**
	 * @return the objectDef
	 */
	public ObjectDefinition getObjectDef() {
		return objectDef;
	}
	/**
	 * @param objectFieldId the objectFieldId to set
	 */
	public void setObjectFieldId(long objectFieldId) {
		this.objectFieldId = objectFieldId;
	}
	/**
	 * @param objectFieldType the objectFieldType to set
	 */
	public void setObjectFieldType(String objectFieldType) {
		this.objectFieldType = objectFieldType;
	}
	/**
	 * @param objectFieldName the objectFieldName to set
	 */
	public void setObjectFieldName(String objectFieldName) {
		this.objectFieldName = objectFieldName;
	}
	/**
	 * @param objectFieldLabelName the objectFieldLabelName to set
	 */
	public void setObjectFieldLabelName(String objectFieldLabelName) {
		this.objectFieldLabelName = objectFieldLabelName;
	}
	/**
	 * @param objectFieldDbName the objectFieldDbName to set
	 */
	public void setObjectFieldDbName(String objectFieldDbName) {
		this.objectFieldDbName = objectFieldDbName;
	}
	/**
	 * @param keyName the keyName to set
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	/**
	 * @param keyReferences the keyReferences to set
	 */
	public void setKeyReferences(String keyReferences) {
		this.keyReferences = keyReferences;
	}
	/**
	 * @param objectDef the objectDef to set
	 */
	public void setObjectDef(ObjectDefinition objectDef) {
		this.objectDef = objectDef;
	}

	@javax.persistence.Transient transient ObjectFieldDefinition old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ObjectFieldDefinition)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ObjectFieldDefinition gOld() {
		return old;
	}
	public String toString() {
			return ""+objectFieldId;
	}
}
