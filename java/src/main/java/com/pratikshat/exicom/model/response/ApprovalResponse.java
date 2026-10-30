package com.pratikshat.exicom.model.response;

import java.util.Date;

public class ApprovalResponse {
	long id;
	String objectName;
	String objectClassName;
	String objectIdFiledName;
	int approvalStages;
	String description;
	boolean active;
	String entryCreteria;
	String initialSubmitters;
	String createByFirstName;
	String createdByMiddleName;
	String createByLastName;
	String createdByEmailId;
	Date createdDate;
	Date lastModifiedDate;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	String modifiedByEmailId;
	int businessDomainId;
	String businessDomainName;

	public ApprovalResponse(com.pratikshat.exicom.entity.Approval approval) {
		this.id = approval.getId();
		this.objectName = approval.getObjectName();
		this.objectClassName = approval.getObjectClassName();
		this.objectIdFiledName = approval.getObjectIdFiledName();
		this.approvalStages = approval.getApprovalStages();
		this.description = approval.getDescription();
		// this.active = approval.geta;
		this.entryCreteria = approval.getEntryCreteria();
		this.initialSubmitters = approval.getInitialSubmitters();
		if (approval.getCreatedBy() != null) {
			this.createByFirstName = approval.getCreatedBy().getFirstName();
			this.createdByMiddleName = approval.getCreatedBy().getMiddleName();
			this.createByLastName = approval.getCreatedBy().getLastName();
			this.createdByEmailId = approval.getCreatedBy().getEmailId();
		}
		if (approval.getModifiedBy() != null) {
			this.modifiedByFirstName = approval.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = approval.getModifiedBy().getMiddleName();
			this.modifiedByLastName = approval.getModifiedBy().getLastName();
			this.modifiedByEmailId = approval.getModifiedBy().getEmailId();
		}
		this.createdDate = approval.getCreatedDate();
		this.lastModifiedDate = approval.getModifiedDate();
		if (approval.getBusinessDomain() != null) {
			this.businessDomainId = approval.getBusinessDomain().getBusinessDomainId();
			this.businessDomainName = approval.getBusinessDomain().getBusinessDomainName();
		}

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectClassName() {
		return objectClassName;
	}

	public void setObjectClassName(String objectClassName) {
		this.objectClassName = objectClassName;
	}

	public String getObjectIdFiledName() {
		return objectIdFiledName;
	}

	public void setObjectIdFiledName(String objectIdFiledName) {
		this.objectIdFiledName = objectIdFiledName;
	}

	public int getApprovalStages() {
		return approvalStages;
	}

	public void setApprovalStages(int approvalStages) {
		this.approvalStages = approvalStages;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEntryCreteria() {
		return entryCreteria;
	}

	public void setEntryCreteria(String entryCreteria) {
		this.entryCreteria = entryCreteria;
	}

	public String getInitialSubmitters() {
		return initialSubmitters;
	}

	public void setInitialSubmitters(String initialSubmitters) {
		this.initialSubmitters = initialSubmitters;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreateByFirstName() {
		return createByFirstName;
	}

	public void setCreateByFirstName(String createByFirstName) {
		this.createByFirstName = createByFirstName;
	}

	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}

	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}

	public String getCreateByLastName() {
		return createByLastName;
	}

	public void setCreateByLastName(String createByLastName) {
		this.createByLastName = createByLastName;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
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

	public int getBusinessDomainId() {
		return businessDomainId;
	}

	public void setBusinessDomainId(int businessDomainId) {
		this.businessDomainId = businessDomainId;
	}

	public String getBusinessDomainName() {
		return businessDomainName;
	}

	public void setBusinessDomainName(String businessDomainName) {
		this.businessDomainName = businessDomainName;
	}

	public String getCreatedByEmailId() {
		return createdByEmailId;
	}

	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}

	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}

	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	
	

}
