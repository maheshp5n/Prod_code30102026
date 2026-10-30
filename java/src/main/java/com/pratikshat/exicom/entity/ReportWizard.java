package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class ReportWizard implements Serializable,OldPresist<ReportWizard>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String reportName;
	@Column(columnDefinition = "TEXT")
	String reportQuery;
	String reportDescription;
	
	@Column(columnDefinition = "TEXT")
	String reportQueryCounter;
	
	String reportType;
	String presentationType;
	
	Date createdDate;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	
	Date lastModifiedDate;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails lastModifiedBy;
	
	Long reportLinkId;
	
	public ReportWizard() {}
	public ReportWizard(Long id) {
		this.id=id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportQuery() {
		return reportQuery;
	}
	public void setReportQuery(String reportQuery) {
		this.reportQuery = reportQuery;
	}
	public String getReportDescription() {
		return reportDescription;
	}
	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
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
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public EmployeeDetails getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(EmployeeDetails lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	@Transient transient ReportWizard old;
	
	@Override
	@PostLoad
	public void old() {
		old=(ReportWizard)SerializationUtils.clone(this);
		
	}
	@Override
	public ReportWizard gOld() {
		// TODO Auto-generated method stub
		return old;
	}
	public String toString() {
		return String.valueOf(id);
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getPresentationType() {
		return presentationType;
	}
	public void setPresentationType(String presentationType) {
		this.presentationType = presentationType;
	}
	public String getReportQueryCounter() {
		return reportQueryCounter;
	}
	public void setReportQueryCounter(String reportQueryCounter) {
		this.reportQueryCounter = reportQueryCounter;
	}
	public Long getReportLinkId() {
		return reportLinkId;
	}
	public void setReportLinkId(Long reportLinkId) {
		this.reportLinkId = reportLinkId;
	}
	
	
	
}
