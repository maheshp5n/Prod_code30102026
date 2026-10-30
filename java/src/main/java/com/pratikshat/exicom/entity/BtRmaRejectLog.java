package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BtRmaRejectLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	String rmaManualId;

	String fileName;

	String physicalFileLocation;
	
	String method;

	String remarks;

	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;

	Date createdDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRmaManualId() {
		return rmaManualId;
	}

	public void setRmaManualId(String rmaManualId) {
		this.rmaManualId = rmaManualId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPhysicalFileLocation() {
		return physicalFileLocation;
	}

	public void setPhysicalFileLocation(String physicalFileLocation) {
		this.physicalFileLocation = physicalFileLocation;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	
}
