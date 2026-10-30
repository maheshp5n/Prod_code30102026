package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class Oppertunity implements java.io.Serializable,OldPresist<Oppertunity>{
	
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long oppertunityId;
	
	String oppertunityNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	CaseDetails caseDetails;
	
	@ManyToOne
	OppertunityStage oppertunityStage;
	
	@ManyToOne
	OppertunityType oppertunityType;
	
	@ManyToOne
	OppertunityForecastCategory forcastCategory;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Accounts accounts;
	
	double probability;
	double ammount;
	double expectedRevenue;
	
	
	java.util.Date closeDate;
	boolean syncFlag;

	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	java.util.Date createDate;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date modifiedDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails ownerDetails;
	
	boolean isDeleted;

	public Oppertunity(long oppertunityId) {
		this.oppertunityId = oppertunityId;
	}

	public Oppertunity() {
		
	}

	public long getOppertunityId() {
		return oppertunityId;
	}

	public void setOppertunityId(long oppertunityId) {
		this.oppertunityId = oppertunityId;
	}

	public String getOppertunityNumber() {
		return oppertunityNumber;
	}

	public void setOppertunityNumber(String oppertunityNumber) {
		this.oppertunityNumber = oppertunityNumber;
	}

	public CaseDetails getCaseDetails() {
		return caseDetails;
	}

	public void setCaseDetails(CaseDetails caseDetails) {
		this.caseDetails = caseDetails;
	}

	public OppertunityStage getOppertunityStage() {
		return oppertunityStage;
	}

	public void setOppertunityStage(OppertunityStage oppertunityStage) {
		this.oppertunityStage = oppertunityStage;
	}

	public OppertunityType getOppertunityType() {
		return oppertunityType;
	}

	public void setOppertunityType(OppertunityType oppertunityType) {
		this.oppertunityType = oppertunityType;
	}

	public OppertunityForecastCategory getForcastCategory() {
		return forcastCategory;
	}

	public void setForcastCategory(OppertunityForecastCategory forcastCategory) {
		this.forcastCategory = forcastCategory;
	}

	public Accounts getAccounts() {
		return accounts;
	}

	public void setAccounts(Accounts accounts) {
		this.accounts = accounts;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public double getAmmount() {
		return ammount;
	}

	public void setAmmount(double ammount) {
		this.ammount = ammount;
	}

	public double getExpectedRevenue() {
		return expectedRevenue;
	}

	public void setExpectedRevenue(double expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}

	public java.util.Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(java.util.Date closeDate) {
		this.closeDate = closeDate;
	}

	public boolean isSyncFlag() {
		return syncFlag;
	}

	public void setSyncFlag(boolean syncFlag) {
		this.syncFlag = syncFlag;
	}

	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public EmployeeDetails getOwnerDetails() {
		return ownerDetails;
	}

	public void setOwnerDetails(EmployeeDetails ownerDetails) {
		this.ownerDetails = ownerDetails;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	


	@javax.persistence.Transient transient Oppertunity old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(Oppertunity)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public Oppertunity gOld() {
		return old;
	}
	public String toString() {
			return ""+oppertunityId;
	}
}
