package com.pratikshat.exicom.model.request;

import com.pratikshat.exicom.entity.LegalEntity;
import com.pratikshat.exicom.entity.LegalEntityType;

public class LegalEntityResponse {
	long legalentityId;
	String legalEntityManualId;
	String legalEntityName;
	String legalEntityTypeName;
	long legalEntityTypeId;
	double legalEntiytFixedLayou;
	String ownerAccountId;
	String ownerAccountFirstName;
	String ownerAccountMiddleName;
	String ownerAccountLastName;
	String createdByEmailId;
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	java.util.Date createdDate;
	String modifiedByEmailid;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	java.util.Date modifiedDate;
	
	
	/**
	 * 
	 */
	public LegalEntityResponse() {
	}
	public LegalEntityResponse(LegalEntity legalEntity) {
		this.legalentityId=legalEntity.getLegalEntityId();
		this.legalEntityManualId = legalEntity.getLegalEntityManualId();
		this.legalEntityName=legalEntity.getLegalEntityName();
		if(legalEntity.getLegalEntityType()!=null) {
			this.legalEntityTypeName=legalEntity.getLegalEntityType().getTypeName();
			this.legalEntityTypeId = legalEntity.getLegalEntityType().getTypeId();
		}
		
		this.legalEntiytFixedLayou=legalEntity.getLegalEntityFixedPayout();
		if(legalEntity.getAccountOwner()!=null) {
			this.ownerAccountId=legalEntity.getAccountOwner().getEmailId();
			this.ownerAccountFirstName=legalEntity.getAccountOwner().getFirstName();
			this.ownerAccountMiddleName=legalEntity.getAccountOwner().getMiddleName();
			this.ownerAccountLastName=legalEntity.getAccountOwner().getLastName();
		}			
		if(legalEntity.getCreatedBy()!=null) {
			this.createdByEmailId=legalEntity.getCreatedBy().getEmailId();
			this.createdByFirstName=legalEntity.getCreatedBy().getFirstName();
			this.createdByMiddleName=legalEntity.getCreatedBy().getMiddleName();
			this.createdByLastName=legalEntity.getCreatedBy().getLastName();
		}
		this.createdDate=legalEntity.getCreatedDate();
		if(legalEntity.getModifiedBy()!=null) {
			this.modifiedByEmailid=legalEntity.getModifiedBy().getEmailId();
			this.modifiedByFirstName=legalEntity.getModifiedBy().getFirstName();
			this.modifiedByMiddleName=legalEntity.getModifiedBy().getMiddleName();
			this.modifiedByLastName=legalEntity.getModifiedBy().getLastName();
		}
		this.modifiedDate=legalEntity.getModifiedDate();
	}
	
	/**
	 * @return the legalentityId
	 */
	public long getLegalentityId() {
		return legalentityId;
	}
	/**
	 * @return the legalEntityName
	 */
	public String getLegalEntityName() {
		return legalEntityName;
	}
	
	/**
	 * @return the legalEntiytFixedLayou
	 */
	public double getLegalEntiytFixedLayou() {
		return legalEntiytFixedLayou;
	}
	/**
	 * @return the ownerAccountId
	 */
	public String getOwnerAccountId() {
		return ownerAccountId;
	}
	/**
	 * @return the ownerAccountFirstName
	 */
	public String getOwnerAccountFirstName() {
		return ownerAccountFirstName;
	}
	/**
	 * @return the ownerAccountLastName
	 */
	public String getOwnerAccountLastName() {
		return ownerAccountLastName;
	}
	/**
	 * @param legalentityId the legalentityId to set
	 */
	public void setLegalentityId(long legalentityId) {
		this.legalentityId = legalentityId;
	}
	/**
	 * @param legalEntityName the legalEntityName to set
	 */
	public void setLegalEntityName(String legalEntityName) {
		this.legalEntityName = legalEntityName;
	}
	
	/**
	 * @param legalEntiytFixedLayou the legalEntiytFixedLayou to set
	 */
	public void setLegalEntiytFixedLayou(double legalEntiytFixedLayou) {
		this.legalEntiytFixedLayou = legalEntiytFixedLayou;
	}
	/**
	 * @param ownerAccountId the ownerAccountId to set
	 */
	public void setOwnerAccountId(String ownerAccountId) {
		this.ownerAccountId = ownerAccountId;
	}
	/**
	 * @param ownerAccountFirstName the ownerAccountFirstName to set
	 */
	public void setOwnerAccountFirstName(String ownerAccountFirstName) {
		this.ownerAccountFirstName = ownerAccountFirstName;
	}
	/**
	 * @param ownerAccountLastName the ownerAccountLastName to set
	 */
	public void setOwnerAccountLastName(String ownerAccountLastName) {
		this.ownerAccountLastName = ownerAccountLastName;
	}
	/**
	 * @return the createdByEmailId
	 */
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	/**
	 * @return the createdByFirstName
	 */
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	/**
	 * @return the createdByLastName
	 */
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	/**
	 * @return the createdDate
	 */
	public java.util.Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the modifiedByEmailid
	 */
	public String getModifiedByEmailid() {
		return modifiedByEmailid;
	}
	/**
	 * @return the modifiedByFirstName
	 */
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	/**
	 * @return the modifiedByLastName
	 */
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	/**
	 * @return the modifiedDate
	 */
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param createdByEmailId the createdByEmailId to set
	 */
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	/**
	 * @param createdByFirstName the createdByFirstName to set
	 */
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	/**
	 * @param createdByLastName the createdByLastName to set
	 */
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param modifiedByEmailid the modifiedByEmailid to set
	 */
	public void setModifiedByEmailid(String modifiedByEmailid) {
		this.modifiedByEmailid = modifiedByEmailid;
	}
	/**
	 * @param modifiedByFirstName the modifiedByFirstName to set
	 */
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	/**
	 * @param modifiedByLastName the modifiedByLastName to set
	 */
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getLegalEntityManualId() {
		return legalEntityManualId;
	}
	public void setLegalEntityManualId(String legalEntityManualId) {
		this.legalEntityManualId = legalEntityManualId;
	}
	public String getLegalEntityTypeName() {
		return legalEntityTypeName;
	}
	public void setLegalEntityTypeName(String legalEntityTypeName) {
		this.legalEntityTypeName = legalEntityTypeName;
	}
	public long getLegalEntityTypeId() {
		return legalEntityTypeId;
	}
	public void setLegalEntityTypeId(long legalEntityTypeId) {
		this.legalEntityTypeId = legalEntityTypeId;
	}
	public String getOwnerAccountMiddleName() {
		return ownerAccountMiddleName;
	}
	public void setOwnerAccountMiddleName(String ownerAccountMiddleName) {
		this.ownerAccountMiddleName = ownerAccountMiddleName;
	}
	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}
	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}
	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}
	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}
	
}
