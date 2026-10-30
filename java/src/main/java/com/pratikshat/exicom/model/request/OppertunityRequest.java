package com.pratikshat.exicom.model.request;

import java.util.Date;

public class OppertunityRequest {
	long oppertunityId;
	long caseId;
	
	long oppertunityStageId;
	
	long oppertunityTypeId;
	
	long forcastCategoryId;
	
	String accountId;
	
	float probability;
	float ammount;
	Date closeDate;
	float expectedRevenue;
	
	
	public long getCaseId() {
		return caseId;
	}
	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}
	public long getOppertunityStageId() {
		return oppertunityStageId;
	}
	public void setOppertunityStageId(long oppertunityStageId) {
		this.oppertunityStageId = oppertunityStageId;
	}
	public long getOppertunityTypeId() {
		return oppertunityTypeId;
	}
	public void setOppertunityTypeId(long oppertunityTypeId) {
		this.oppertunityTypeId = oppertunityTypeId;
	}
	public long getForcastCategoryId() {
		return forcastCategoryId;
	}
	public void setForcastCategoryId(long forcastCategoryId) {
		this.forcastCategoryId = forcastCategoryId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public float getProbability() {
		return probability;
	}
	public void setProbability(float probability) {
		this.probability = probability;
	}
	public float getAmmount() {
		return ammount;
	}
	public void setAmmount(float ammount) {
		this.ammount = ammount;
	}
	public Date getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	public long getOppertunityId() {
		return oppertunityId;
	}
	public void setOppertunityId(long oppertunityId) {
		this.oppertunityId = oppertunityId;
	}
	public float getExpectedRevenue() {
		return expectedRevenue;
	}
	public void setExpectedRevenue(float expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}
	
	
}
