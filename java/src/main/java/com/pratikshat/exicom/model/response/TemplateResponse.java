package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.TemplateType;

public class TemplateResponse {
	Long id;
	String templateCode;
	TemplateType templateType;
	String templateBody;
	
	String createdByEmailId;
	String createdByFirstName;
	String createdByLastName;
	String createdByMiddleName;
	
	String modifiedByEmailid;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	
	Date createdDate;
	Date modifiedDate;
	boolean active;
	
	public TemplateResponse() {}
	
	
	public TemplateResponse(Long id, String templateCode, TemplateType templateType, String templateBody,
			String createdByEmailId, String createdByFirstName, String createdByLastName, String createdByMiddleName,
			String modifiedByEmailid, String modifiedByFirstName, String modifiedByMiddleName,
			String modifiedByLastName, Date createdDate, Date modifiedDate,boolean active) {
		this.id = id;
		this.templateCode = templateCode;
		this.templateType = templateType;
		this.templateBody = templateBody;
		this.createdByEmailId = createdByEmailId;
		this.createdByFirstName = createdByFirstName;
		this.createdByLastName = createdByLastName;
		this.createdByMiddleName = createdByMiddleName;
		this.modifiedByEmailid = modifiedByEmailid;
		this.modifiedByFirstName = modifiedByFirstName;
		this.modifiedByMiddleName = modifiedByMiddleName;
		this.modifiedByLastName = modifiedByLastName;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.active=active;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTemplateCode() {
		return templateCode;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	
	public String getTemplateBody() {
		return templateBody;
	}
	public void setTemplateBody(String templateBody) {
		this.templateBody = templateBody;
	}
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}
	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}
	public String getModifiedByEmailid() {
		return modifiedByEmailid;
	}
	public void setModifiedByEmailid(String modifiedByEmailid) {
		this.modifiedByEmailid = modifiedByEmailid;
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
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public TemplateType getTemplateType() {
		return templateType;
	}


	public void setTemplateType(TemplateType templateType) {
		this.templateType = templateType;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}
	
}
