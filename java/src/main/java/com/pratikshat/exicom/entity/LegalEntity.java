package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;
//service center
@Entity
@EntityListeners(ObjectAuditListener.class)
public class LegalEntity implements java.io.Serializable,OldPresist<LegalEntity>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long legalEntityId;
	String legalEntityName;
	String legalEntityManualId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	LegalEntityType legalEntityType;
	double legalEntityFixedPayout;
	
	@ManyToOne
	@JoinColumn(name="account_owner_id")
	EmployeeDetails accountOwner;
	
	boolean synced;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	java.util.Date createdDate;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date modifiedDate;
	String manualId;
	
	@ManyToOne
	BusinessDomain businessDoamin;
	
	/**
	 * @return the synced
	 */
	public boolean isSynced() {
		return synced;
	}

	/**
	 * @param synced the synced to set
	 */
	public void setSynced(boolean synced) {
		this.synced = synced;
	}

	public LegalEntity(long legalEntityId) {
		this.legalEntityId=legalEntityId;
	}
	
	/**
	 * @param legalEntityId
	 * @param legalEntityName
	 * @param legalEntityType
	 * @param legalEntityFixedPayout
	 * @param accountOwner
	 */
	public LegalEntity(long legalEntityId, String legalEntityName, LegalEntityType legalEntityType,
			double legalEntityFixedPayout, EmployeeDetails accountOwner) {
		this.legalEntityId = legalEntityId;
		this.legalEntityName = legalEntityName;
		this.legalEntityType = legalEntityType;
		this.legalEntityFixedPayout = legalEntityFixedPayout;
		this.accountOwner = accountOwner;
	}
	public LegalEntity(long legalEntityId, String legalEntityName, LegalEntityType legalEntityType,
			double legalEntityFixedPayout, String accountOwnerId) {
		this.legalEntityId = legalEntityId;
		this.legalEntityName = legalEntityName;
		this.legalEntityType = legalEntityType;
		this.legalEntityFixedPayout = legalEntityFixedPayout;
		this.accountOwner =new EmployeeDetails(accountOwnerId);
	}
	
	
	/**
	 * 
	 */
	public LegalEntity() {
	}


	/**
	 * @return the legalEntityId
	 */
	public long getLegalEntityId() {
		return legalEntityId;
	}
	/**
	 * @return the legalEntityName
	 */
	public String getLegalEntityName() {
		return legalEntityName;
	}
	
	/**
	 * @return the legalEntityFixedPayout
	 */
	public double getLegalEntityFixedPayout() {
		return legalEntityFixedPayout;
	}
	/**
	 * @return the accountOwner
	 */
	public EmployeeDetails getAccountOwner() {
		return accountOwner;
	}
	/**
	 * @param legalEntityId the legalEntityId to set
	 */
	public void setLegalEntityId(long legalEntityId) {
		this.legalEntityId = legalEntityId;
	}
	/**
	 * @param legalEntityName the legalEntityName to set
	 */
	public void setLegalEntityName(String legalEntityName) {
		this.legalEntityName = legalEntityName;
	}
	
	/**
	 * @param legalEntityFixedPayout the legalEntityFixedPayout to set
	 */
	public void setLegalEntityFixedPayout(double legalEntityFixedPayout) {
		this.legalEntityFixedPayout = legalEntityFixedPayout;
	}
	/**
	 * @param accountOwner the accountOwner to set
	 */
	public void setAccountOwner(EmployeeDetails accountOwner) {
		this.accountOwner = accountOwner;
	}

	/**
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @return the modifiedBy
	 */
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @return the modifiedDate
	 */
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	/**
	 * @return the manualId
	 */
	public String getManualId() {
		return manualId;
	}

	/**
	 * @param manualId the manualId to set
	 */
	public void setManualId(String manualId) {
		this.manualId = manualId;
	}

	public LegalEntityType getLegalEntityType() {
		return legalEntityType;
	}

	public void setLegalEntityType(LegalEntityType legalEntityType) {
		this.legalEntityType = legalEntityType;
	}

	public String getLegalEntityManualId() {
		return legalEntityManualId;
	}

	public void setLegalEntityManualId(String legalEntityManualId) {
		this.legalEntityManualId = legalEntityManualId;
	}
	
	@Transient transient LegalEntity old;

	@PostLoad
	@Override
	public void old() {
		old=(LegalEntity)SerializationUtils.clone(this);
	}

	@Override
	public LegalEntity gOld() {
		// TODO Auto-generated method stub
		return old;
	}

	 public String toString(){
		return String.valueOf(legalEntityId);
	 }

	public BusinessDomain getBusinessDoamin() {
		return businessDoamin;
	}

	public void setBusinessDoamin(BusinessDomain businessDoamin) {
		this.businessDoamin = businessDoamin;
	}
	

}
