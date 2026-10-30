package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.CaseRejectLog;
import com.pratikshat.exicom.entity.UploadedFiles;

public class CaseRejectLogResponse {

	long id;
	
	String caseManualId;
	String remark;
	
	String fileName;
	String fileUrl;
	String fileContentType;
	long fileSize;
	
	String createdByFirstName;
	String createdByLastName;
	String createdByMiddleName;
	String createdByEmailId;
	
	Date createdDate;
	
	
	public CaseRejectLogResponse() {
		
	}
	
	
	public CaseRejectLogResponse(CaseRejectLog caseRejectLog, UploadedFiles uploadedFiles) {
		
		if(caseRejectLog!=null) {
			this.id = caseRejectLog.getId();
			this.caseManualId = caseRejectLog.getCaseManualId();
			this.remark = caseRejectLog.getRemark();
			this.createdDate = caseRejectLog.getCreatedDate();
			if(caseRejectLog.getCreatedBy()!=null) {
				this.createdByFirstName=caseRejectLog.getCreatedBy().getFirstName();
				this.createdByLastName=caseRejectLog.getCreatedBy().getMiddleName();
				this.createdByMiddleName=caseRejectLog.getCreatedBy().getLastName();
				this.createdByEmailId=caseRejectLog.getCreatedBy().getEmailId();
			}
		}
		
		if(uploadedFiles!=null) {
			this.fileName=uploadedFiles.getFileName();
			this.fileUrl=uploadedFiles.getFileDownloadUri();
			this.fileContentType=uploadedFiles.getFileContentType();
			this.fileSize=uploadedFiles.getFileSize();
		}
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCaseManualId() {
		return caseManualId;
	}

	public void setCaseManualId(String caseManualId) {
		this.caseManualId = caseManualId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
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

	public String getCreatedByEmailId() {
		return createdByEmailId;
	}

	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
