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
public class ReportRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(columnDefinition = "TEXT")
	String query;
	@Column(columnDefinition = "TEXT")
	String counterQuery;
	Date requestDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails requestBy;
	@ManyToOne(fetch=FetchType.LAZY)
	ReportWizard wizard;
	
	@Column(length = 15)
	String reportStatus;
	
	Long fileSize;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public EmployeeDetails getRequestBy() {
		return requestBy;
	}

	public void setRequestBy(EmployeeDetails requestBy) {
		this.requestBy = requestBy;
	}

	public ReportWizard getWizard() {
		return wizard;
	}

	public void setWizard(ReportWizard wizard) {
		this.wizard = wizard;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public String getCounterQuery() {
		return counterQuery;
	}

	public void setCounterQuery(String counterQuery) {
		this.counterQuery = counterQuery;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	
}
