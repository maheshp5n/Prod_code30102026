package com.pratikshat.exicom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Bulkupload {
	@Id
	Long id;
	String uploadName;
	String uploadDescription;
	String templateApi;
	String templateApiName;
	String uploadApi;
	String uploadButtonName;
	
	@Column(columnDefinition = "TEXT")
	String assignedTo;
	public Bulkupload() {};
	public Bulkupload(Long id) {
		this.id=id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUploadName() {
		return uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public String getUploadDescription() {
		return uploadDescription;
	}

	public void setUploadDescription(String uploadDescription) {
		this.uploadDescription = uploadDescription;
	}

	public String getTemplateApi() {
		return templateApi;
	}

	public void setTemplateApi(String templateApi) {
		this.templateApi = templateApi;
	}

	public String getTemplateApiName() {
		return templateApiName;
	}

	public void setTemplateApiName(String templateApiName) {
		this.templateApiName = templateApiName;
	}

	public String getUploadApi() {
		return uploadApi;
	}

	public void setUploadApi(String uploadApi) {
		this.uploadApi = uploadApi;
	}

	public String getUploadButtonName() {
		return uploadButtonName;
	}

	public void setUploadButtonName(String uploadButtonName) {
		this.uploadButtonName = uploadButtonName;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	
	
	
}
