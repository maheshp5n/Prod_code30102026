package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class MSLRequest implements java.io.Serializable,OldPresist<MSLRequest>{

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long id;
	
	String manualId;
	
	@ManyToOne
	Location warehouse;
	
	
	@ManyToOne
	MSLRequestStatus mslRequestStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails owner;

	public MSLRequest(long id) {
		this.id = id;
	}
	public MSLRequest() {
		
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getManualId() {
		return manualId;
	}

	public void setManualId(String manualId) {
		this.manualId = manualId;
	}

	

	/**
	 * @return the warehouse
	 */
	public Location getWarehouse() {
		return warehouse;
	}



	/**
	 * @param warehouse the warehouse to set
	 */
	public void setWarehouse(Location warehouse) {
		this.warehouse = warehouse;
	}



	public MSLRequestStatus getMslRequestStatus() {
		return mslRequestStatus;
	}

	public void setMslRequestStatus(MSLRequestStatus mslRequestStatus) {
		this.mslRequestStatus = mslRequestStatus;
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

	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public EmployeeDetails getOwner() {
		return owner;
	}

	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}
	

	@javax.persistence.Transient transient MSLRequest old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(MSLRequest)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public MSLRequest gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
