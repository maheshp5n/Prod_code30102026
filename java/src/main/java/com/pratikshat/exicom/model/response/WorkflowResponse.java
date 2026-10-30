package com.pratikshat.exicom.model.response;

public class WorkflowResponse {
	Long id;
	String description;
	String objectName;
	String object;
	String dmlCommmand;
	String onevent;
	String createdBy;
	String modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	String className;
	boolean active;
	String createdByMidName;
	String createdByLastName;
	String createdByEmailId;
	String modifiedByMidName;
	String modifiedByLastName;
	String modifiedByEmailId;
	String codeSnip;
	public WorkflowResponse() {}
	
	public WorkflowResponse(Long id, String description, String objectName, String object, String dmlCommmand,
			String onevent, String createdBy, String modifiedBy, java.util.Date createdDate, java.util.Date modifiedDate,
			String className,boolean active,String createdByMidName,String createdbyLastName,String createdByEmailId,String modifiedByMidName,String modifiedByLastName,String modifiedByEmailId,String codeSnip) {
		this.id = id;
		this.description = description;
		this.objectName = objectName;
		this.object = object;
		this.dmlCommmand = dmlCommmand;
		this.onevent = onevent;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.className = className;
		this.active=active;
		this.createdByMidName=createdByMidName;
		this.createdByLastName=createdbyLastName;
		this.createdByEmailId=createdByEmailId;
		this.modifiedByMidName=modifiedByMidName;
		this.modifiedByLastName=modifiedByLastName;
		this.modifiedByEmailId=modifiedByEmailId;
		this.codeSnip=codeSnip;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getDmlCommmand() {
		return dmlCommmand;
	}
	public void setDmlCommmand(String dmlCommmand) {
		this.dmlCommmand = dmlCommmand;
	}
	public String getOnevent() {
		return onevent;
	}
	public void setOnevent(String onevent) {
		this.onevent = onevent;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}

	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCreatedByMidName() {
		return createdByMidName;
	}

	public void setCreatedByMidName(String createdByMidName) {
		this.createdByMidName = createdByMidName;
	}

	public String getCreatedByLastName() {
		return createdByLastName;
	}

	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}

	public String getCreatedByEmailId() {
		return createdByEmailId;
	}

	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}

	public String getModifiedByMidName() {
		return modifiedByMidName;
	}

	public void setModifiedByMidName(String modifiedByMidName) {
		this.modifiedByMidName = modifiedByMidName;
	}

	public String getModifiedByLastName() {
		return modifiedByLastName;
	}

	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}

	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}

	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}

	public String getCodeSnip() {
		return codeSnip;
	}

	public void setCodeSnip(String codeSnip) {
		this.codeSnip = codeSnip;
	}
	
}
