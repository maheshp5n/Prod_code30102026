package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AgingReportOpenComplaints {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String customerName;
	String accountSapNumber;
	String accountName;
	String primaryAccountSapNumber;
	String primaryAccountName;
	String circleName;
	String CaseType;
	String caseReason;
	
	Date logDate;
	
	Integer addedNew;
	Integer addedClosed;
	Integer rejected;
	Integer openCase;
	
	public AgingReportOpenComplaints() {}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAccountSapNumber() {
		return accountSapNumber;
	}
	public void setAccountSapNumber(String accountSapNumber) {
		this.accountSapNumber = accountSapNumber;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPrimaryAccountSapNumber() {
		return primaryAccountSapNumber;
	}
	public void setPrimaryAccountSapNumber(String primaryAccountSapNumber) {
		this.primaryAccountSapNumber = primaryAccountSapNumber;
	}
	public String getPrimaryAccountName() {
		return primaryAccountName;
	}
	public void setPrimaryAccountName(String primaryAccountName) {
		this.primaryAccountName = primaryAccountName;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getCaseType() {
		return CaseType;
	}
	public void setCaseType(String caseType) {
		CaseType = caseType;
	}
	public String getCaseReason() {
		return caseReason;
	}
	public void setCaseReason(String caseReason) {
		this.caseReason = caseReason;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public Integer getAddedNew() {
		return addedNew;
	}
	public void setAddedNew(Integer addedNew) {
		this.addedNew = addedNew;
	}
	public Integer getAddedClosed() {
		return addedClosed;
	}
	public void setAddedClosed(Integer addedClosed) {
		this.addedClosed = addedClosed;
	}
	public Integer getRejected() {
		return rejected;
	}
	public void setRejected(Integer rejected) {
		this.rejected = rejected;
	}
	public Integer getOpenCase() {
		return openCase;
	}
	public void setOpenCase(Integer openCase) {
		this.openCase = openCase;
	}
	
}
