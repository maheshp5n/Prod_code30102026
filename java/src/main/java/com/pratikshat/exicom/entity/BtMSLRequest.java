package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@javax.persistence.EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtMSLRequest extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtMSLRequest>{

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;
    private String manualId; 
    
    @ManyToOne
	Location warehouse;
	
	@ManyToOne
	MSLRequestStatus mslRequestStatus;

	public Long getId() {
		return id;
	}

	
	public BtMSLRequest(long id) {
		super();
		this.id = id;
	}


	public BtMSLRequest() {
		super();
		
	}


	public void setId(Long id) {
		this.id = id;
	}

	public EmployeeDetails getOwner() {
		return owner;
	}

	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}

	public String getManualId() {
		return manualId;
	}

	public void setManualId(String manualId) {
		this.manualId = manualId;
	}

	public Location getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Location warehouse) {
		this.warehouse = warehouse;
	}

	public MSLRequestStatus getMslRequestStatus() {
		return mslRequestStatus;
	}

	public void setMslRequestStatus(MSLRequestStatus mslRequestStatus) {
		this.mslRequestStatus = mslRequestStatus;
	}
	
	@javax.persistence.Transient transient BtMSLRequest old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BtMSLRequest)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BtMSLRequest gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
