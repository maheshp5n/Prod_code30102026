package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.QuoteTermsAndConditions;

public class TermsAndConditionsResponse {

	long id;
	
	String rule;
	int serialNumber;
	String objectName;
	boolean isActive;
	
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	Date createdDate;
	
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	Date modifiedDate;
	
	
	public TermsAndConditionsResponse(QuoteTermsAndConditions termsAndConditions) {
		
		this.id = termsAndConditions.getId();
		this.rule = termsAndConditions.getRule();
		this.serialNumber = termsAndConditions.getSerialNumber();
		this.objectName = termsAndConditions.getObjectName();
		this.isActive = termsAndConditions.isActive();
		
		if(termsAndConditions.getCreatedBy()!=null) {
			this.createdByFirstName = termsAndConditions.getCreatedBy().getFirstName();
			this.createdByMiddleName = termsAndConditions.getCreatedBy().getMiddleName();
			this.createdByLastName = termsAndConditions.getCreatedBy().getLastName();
			this.createdDate = termsAndConditions.getCreatedDate();
		}
		
		if(termsAndConditions.getModifiedBy()!=null) {
			this.modifiedByFirstName = termsAndConditions.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = termsAndConditions.getModifiedBy().getMiddleName();
			this.modifiedByLastName = termsAndConditions.getModifiedBy().getLastName();
			this.modifiedDate = termsAndConditions.getModifiedDate();
		}
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}
	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}
	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
