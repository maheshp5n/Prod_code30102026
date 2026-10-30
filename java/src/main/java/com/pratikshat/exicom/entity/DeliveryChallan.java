package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.pratikshat.exicom.model.request.LPRequest;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class DeliveryChallan implements java.io.Serializable,OldPresist<DeliveryChallan>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long deliveryChallanId;
	
	String manualId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	LProductRequest productRequest;
	
	int receivedQuantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Location fromLocation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Location toLocation;
	
	int quantityIssued;
	
	@ManyToOne(fetch = FetchType.LAZY)
	DeliveryChallanStatus deliveryChallanStatus;
	
	String receivedBy;
	
	boolean isSendShippingMail;
	
	@ManyToOne(fetch = FetchType.LAZY)
	DeliveryChallanShipmentMode deliveryChallanShipmentMode;
	
	
	Date shippingDate;
	
	String name;
	
	String email;
	
	String number;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails owner;
	
	public DeliveryChallan(long deliveryChallanId) {
		this.deliveryChallanId = deliveryChallanId;
	}

	public DeliveryChallan() {
		
	}

	public long getDeliveryChallanId() {
		return deliveryChallanId;
	}

	public void setDeliveryChallanId(long deliveryChallanId) {
		this.deliveryChallanId = deliveryChallanId;
	}

	public String getManualId() {
		return manualId;
	}

	public void setManualId(String manualId) {
		this.manualId = manualId;
	}

	

	public int getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(int receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

	public int getQuantityIssued() {
		return quantityIssued;
	}

	public void setQuantityIssued(int quantityIssued) {
		this.quantityIssued = quantityIssued;
	}

	public DeliveryChallanStatus getDeliveryChallanStatus() {
		return deliveryChallanStatus;
	}

	public void setDeliveryChallanStatus(DeliveryChallanStatus deliveryChallanStatus) {
		this.deliveryChallanStatus = deliveryChallanStatus;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public boolean isSendShippingMail() {
		return isSendShippingMail;
	}

	public void setSendShippingMail(boolean isSendShippingMail) {
		this.isSendShippingMail = isSendShippingMail;
	}

	public DeliveryChallanShipmentMode getDeliveryChallanShipmentMode() {
		return deliveryChallanShipmentMode;
	}

	public void setDeliveryChallanShipmentMode(DeliveryChallanShipmentMode deliveryChallanShipmentMode) {
		this.deliveryChallanShipmentMode = deliveryChallanShipmentMode;
	}

	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public LProductRequest getProductRequest() {
		return productRequest;
	}

	public void setProductRequest(LProductRequest productRequest) {
		this.productRequest = productRequest;
	}

	public Location getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(Location fromLocation) {
		this.fromLocation = fromLocation;
	}

	public Location getToLocation() {
		return toLocation;
	}

	public void setToLocation(Location toLocation) {
		this.toLocation = toLocation;
	}

	public EmployeeDetails getOwner() {
		return owner;
	}

	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}
	
	

	@javax.persistence.Transient transient DeliveryChallan old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(DeliveryChallan)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public DeliveryChallan gOld() {
		return old;
	}
	public String toString() {
			return ""+deliveryChallanId;
	}
}
