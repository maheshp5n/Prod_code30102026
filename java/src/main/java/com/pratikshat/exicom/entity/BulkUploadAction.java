package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BulkUploadAction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Bulkupload bulkUpload;
	
	@Column(columnDefinition = "TEXT")
	String ownerRoles;
	
	Long requestDocumentId;
	Long resultDocumentId;
	
	Date createdDate;
	
	Date lastMofidiedDate;
	String uploadStatus;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Bulkupload getBulkUpload() {
		return bulkUpload;
	}
	public void setBulkUpload(Bulkupload bulkUpload) {
		this.bulkUpload = bulkUpload;
	}
	public String getOwnerRoles() {
		return ownerRoles;
	}
	public void setOwnerRoles(String ownerRoles) {
		this.ownerRoles = ownerRoles;
	}
	public Long getRequestDocumentId() {
		return requestDocumentId;
	}
	public void setRequestDocumentId(Long requestDocumentId) {
		this.requestDocumentId = requestDocumentId;
	}
	public Long getResultDocumentId() {
		return resultDocumentId;
	}
	public void setResultDocumentId(Long resultDocumentId) {
		this.resultDocumentId = resultDocumentId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastMofidiedDate() {
		return lastMofidiedDate;
	}
	public void setLastMofidiedDate(Date lastMofidiedDate) {
		this.lastMofidiedDate = lastMofidiedDate;
	}
	public String getUploadStatus() {
		return uploadStatus;
	}
	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	
	
}
