package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class UploadedFiles implements java.io.Serializable,OldPresist<UploadedFiles>{

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	
	String objectName;
	String objectId;
	
	String fileName;
	String fileDownloadUri;
	String fileContentType;
	long fileSize;
	
	Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	
	Date modifiedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails modifiedBy;
	
	boolean isDeleted;

	
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

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}





	@javax.persistence.Transient transient UploadedFiles old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(UploadedFiles)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public UploadedFiles gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
