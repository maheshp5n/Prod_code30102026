package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtProductRequest extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtProductRequest>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;	
    String manualId;
	@Column(length = 500)
	String description;
	java.util.Date needByDateTime;
	@ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	Location sourceLocation;
	@ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	Location destinationLocation;	
	String btProductRequestStatus;
	
	float totalQuantityRequired;
	float totalQuantityReceived;
	float totalQuantityIssued;
	
	String dummyStatus;
	
	@ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails ownerDetails;
	
	boolean synced;
	
	java.util.Date submittedDate;
	
	public BtProductRequest() {}
	public BtProductRequest(long btprId) {
		this.id=btprId;
	}
	
	@Override
	public Long getId() {
		
		return this.id;
	}
	@Override
	public void setId(Long id) {
		this.id= id;
		
	}
	@Override
	public EmployeeDetails getOwner() {
		
		return this.ownerDetails;
	}
	@Override
	public void setOwner(EmployeeDetails u) {
		
		this.ownerDetails=u;
	}
	@Override
	public String getManualId() {
		
		return manualId;
	}
	@Override
	public void setManualId(String manualId) {
		this.manualId = manualId;
		
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public java.util.Date getNeedByDateTime() {
		return needByDateTime;
	}
	public void setNeedByDateTime(java.util.Date needByDateTime) {
		this.needByDateTime = needByDateTime;
	}
	public Location getSourceLocation() {
		return sourceLocation;
	}
	public void setSourceLocation(Location sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	public Location getDestinationLocation() {
		return destinationLocation;
	}
	public void setDestinationLocation(Location destinationLocation) {
		this.destinationLocation = destinationLocation;
	}
	public String getBtProductRequestStatus() {
		return btProductRequestStatus;
	}
	public void setBtProductRequestStatus(String btProductRequestStatus) {
		this.btProductRequestStatus = btProductRequestStatus;
	}
	public float getTotalQuantityRequired() {
		return totalQuantityRequired;
	}
	public void setTotalQuantityRequired(float totalQuantityRequired) {
		this.totalQuantityRequired = totalQuantityRequired;
	}
	public float getTotalQuantityReceived() {
		return totalQuantityReceived;
	}
	public void setTotalQuantityReceived(float totalQuantityReceived) {
		this.totalQuantityReceived = totalQuantityReceived;
	}
	public float getTotalQuantityIssued() {
		return totalQuantityIssued;
	}
	public void setTotalQuantityIssued(float totalQuantityIssued) {
		this.totalQuantityIssued = totalQuantityIssued;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	public EmployeeDetails getOwnerDetails() {
		return ownerDetails;
	}
	public void setOwnerDetails(EmployeeDetails ownerDetails) {
		this.ownerDetails = ownerDetails;
	}



	@Transient transient BtProductRequest old;

	@Override
    @PostLoad
    public void old() {
        old=(BtProductRequest) SerializationUtils.clone(this);
    }

    @Override
    public BtProductRequest gOld() {
        return old;
    }
	public String toString() {
			return ""+id;
	}
	public String getDummyStatus() {
		return dummyStatus;
	}
	public void setDummyStatus(String dummyStatus) {
		this.dummyStatus = dummyStatus;
	}
	public BtProductRequest getOld() {
        return old;
    }

    public void setOld(BtProductRequest old) {
        this.old = old;
    }
	public java.util.Date getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(java.util.Date submittedDate) {
		this.submittedDate = submittedDate;
	}
    
    
	
}
