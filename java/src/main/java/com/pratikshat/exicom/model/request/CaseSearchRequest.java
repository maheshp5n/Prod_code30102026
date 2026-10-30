package com.pratikshat.exicom.model.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CaseSearchRequest {
	String caseNumber;
	String siteName;
	String assetsSerialNumber;
	String typeName;
	String reasonName;
	String status;
	String stage;
	//@JsonFormat(pattern="dd-MM-yy")
	Date dateTimeOpenFrom;
	//@JsonFormat(pattern="dd-MM-yy")
	Date dateTimeOpenTo;
	//@JsonFormat(pattern="dd-MM-yy")
	Date dateTimeCloseTo;
	//@JsonFormat(pattern="dd-MM-yy")
	Date dateTimeCloseFrom;
	String engName;
	String account;
	String preRegistrationCaseNumber;
	String customer;
	
	public String getCaseNumber() {
		return caseNumber;
	}
	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getAssetsSerialNumber() {
		return assetsSerialNumber;
	}
	public void setAssetsSerialNumber(String assetsSerialNumber) {
		this.assetsSerialNumber = assetsSerialNumber;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getReasonName() {
		return reasonName;
	}
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public Date getDateTimeOpenFrom() {
		return dateTimeOpenFrom;
	}
	public void setDateTimeOpenFrom(Date dateTimeOpenFrom) {
		this.dateTimeOpenFrom = dateTimeOpenFrom;
	}
	public Date getDateTimeOpenTo() {
		return dateTimeOpenTo;
	}
	public void setDateTimeOpenTo(Date dateTimeOpenTo) {
		this.dateTimeOpenTo = dateTimeOpenTo;
	}
	public Date getDateTimeCloseTo() {
		return dateTimeCloseTo;
	}
	public void setDateTimeCloseTo(Date dateTimeCloseTo) {
		this.dateTimeCloseTo = dateTimeCloseTo;
	}
	
	public Date getDateTimeCloseFrom() {
		return dateTimeCloseFrom;
	}
	public void setDateTimeCloseFrom(Date dateTimeCloseFrom) {
		this.dateTimeCloseFrom = dateTimeCloseFrom;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPreRegistrationCaseNumber() {
		return preRegistrationCaseNumber;
	}
	public void setPreRegistrationCaseNumber(String preRegistrationCaseNumber) {
		this.preRegistrationCaseNumber = preRegistrationCaseNumber;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	
	
}
