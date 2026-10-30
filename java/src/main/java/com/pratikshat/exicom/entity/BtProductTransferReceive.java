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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({ObjectAuditListener.class,AuditingEntityListener.class})
public class BtProductTransferReceive extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtProductTransferReceive> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	Long id;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id")
	@MapsId
	BtProductTransferItem item;
	
	Date receiveDate;
	
	float receivedQuantity;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails ownerDetails;
	
	public BtProductTransferReceive() {}
	public BtProductTransferReceive(Long id) {
		this.id=id;
	}
	
	@Transient BtProductTransferReceive old;
	
	@PostLoad
	@Override
	public void old() {
		old=(BtProductTransferReceive)org.apache.commons.lang.SerializationUtils.clone(this);
	}

	@Override
	public BtProductTransferReceive gOld() {
		// TODO Auto-generated method stub
		return this.old;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	@Override
	public EmployeeDetails getOwner() {
		// TODO Auto-generated method stub
		return this.ownerDetails;
	}

	@Override
	public void setOwner(EmployeeDetails u) {
		this.ownerDetails=u;
	}

	@Override
	public String getManualId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setManualId(String manualId) {
		// TODO Auto-generated method stub
		
	}

	public BtProductTransferItem getItem() {
		return item;
	}

	public void setItem(BtProductTransferItem item) {
		this.item = item;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public float getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(float receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

	public EmployeeDetails getOwnerDetails() {
		return ownerDetails;
	}

	public void setOwnerDetails(EmployeeDetails ownerDetails) {
		this.ownerDetails = ownerDetails;
	}
	
	

}
