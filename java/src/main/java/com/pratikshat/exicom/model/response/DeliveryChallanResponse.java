package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.DeliveryChallan;

public class DeliveryChallanResponse {

	
	long deliveryChallanId;
	String manualId;
	
	
	String productRequestManualId;
	long productRequestId;
	
	int receivedQuantity;
	int quantityIssued;
	
	String fromLocationName;
	long fromLocationId;
	
	String toLocationName;
	long toLocationId;
	
	long deliveryChallanStatusId;
	String deliveryChallanStatusName;
	
	String receivedBy;
	
	boolean isSendShippingMail;
	
	
	long deliveryChallanShipmentModeId;
	String deliveryChallanShipmentModeName;
	
	Date shippingDate;
	
	String name;
	
	String email;
	
	String number;
	
	
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	String createdByEmailId;
	Date createdDate;
	
	String modifiedByFirstName;
	String modifiedByLastName;
	String modifiedByMiddleName;
	String modifiedByEmailId;
	Date modifiedDate;
	
	String ownerFirstName;
	String ownerMiddleName;
	String ownerLastName;
	String ownerEmailId;
	
	
	
	public DeliveryChallanResponse(DeliveryChallan deliveryChallan) {
		this.deliveryChallanId = deliveryChallan.getDeliveryChallanId();
		this.manualId = deliveryChallan.getManualId();
		
		if(deliveryChallan.getProductRequest()!=null) {
			this.productRequestManualId = deliveryChallan.getProductRequest().getLocationProductRequestManualId();
			this.productRequestId = deliveryChallan.getProductRequest().getLocationProductRequestId();
		}
		
		
		this.receivedQuantity = deliveryChallan.getReceivedQuantity();
		this.quantityIssued = deliveryChallan.getQuantityIssued();
		
		if(deliveryChallan.getFromLocation()!=null) {
			this.fromLocationName = deliveryChallan.getFromLocation().getLocationName();
			this.fromLocationId = deliveryChallan.getFromLocation().getLocationId();
		}
		
		if(deliveryChallan.getToLocation()!=null) {
			this.toLocationName = deliveryChallan.getToLocation().getLocationName();
			this.toLocationId = deliveryChallan.getToLocation().getLocationId();
		}
		
		if(deliveryChallan.getDeliveryChallanStatus()!=null) {
			this.deliveryChallanStatusId = deliveryChallan.getDeliveryChallanStatus().getStatusId();
			this.deliveryChallanStatusName = deliveryChallan.getDeliveryChallanStatus().getStatusName();
		}
		
		
		this.receivedBy = deliveryChallan.getReceivedBy();
		
		this.isSendShippingMail = deliveryChallan.isSendShippingMail();
		
		if(deliveryChallan.getDeliveryChallanShipmentMode()!=null) {
			this.deliveryChallanShipmentModeId = deliveryChallan.getDeliveryChallanShipmentMode().getShipmentModeId();
			this.deliveryChallanShipmentModeName = deliveryChallan.getDeliveryChallanShipmentMode().getShipmentModeName();
		}
		
		
		this.shippingDate = deliveryChallan.getShippingDate();
		
		this.name = deliveryChallan.getReceivedBy();
		
		this.email = deliveryChallan.getEmail();
		
		this.number = deliveryChallan.getNumber();
		
		if(deliveryChallan.getCreatedBy()!=null) {
			this.createdByFirstName = deliveryChallan.getCreatedBy().getFirstName();
			this.createdByMiddleName = deliveryChallan.getCreatedBy().getMiddleName();
			this.createdByLastName = deliveryChallan.getCreatedBy().getLastName();
			this.createdByEmailId = deliveryChallan.getCreatedBy().getEmailId();
			this.createdDate = deliveryChallan.getCreatedDate();
		}
		
		if(deliveryChallan.getModifiedBy()!=null) {
			this.modifiedByFirstName = deliveryChallan.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = deliveryChallan.getModifiedBy().getMiddleName();
			this.modifiedByLastName = deliveryChallan.getModifiedBy().getLastName();
			this.modifiedByEmailId = deliveryChallan.getModifiedBy().getEmailId();
			this.modifiedDate = deliveryChallan.getModifiedDate();
		}
		
		
		if(deliveryChallan.getOwner()!=null) {
			this.ownerFirstName = deliveryChallan.getOwner().getFirstName();
			this.ownerMiddleName = deliveryChallan.getOwner().getMiddleName();
			this.ownerLastName = deliveryChallan.getOwner().getLastName();
			this.ownerEmailId = deliveryChallan.getOwner().getEmailId();
		}
		
	}
	
	
	
	
	public DeliveryChallanResponse() {
		
		
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
	
	public long getDeliveryChallanStatusId() {
		return deliveryChallanStatusId;
	}
	public void setDeliveryChallanStatusId(long deliveryChallanStatusId) {
		this.deliveryChallanStatusId = deliveryChallanStatusId;
	}
	public String getDeliveryChallanStatusName() {
		return deliveryChallanStatusName;
	}
	public void setDeliveryChallanStatusName(String deliveryChallanStatusName) {
		this.deliveryChallanStatusName = deliveryChallanStatusName;
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
	public long getDeliveryChallanShipmentModeId() {
		return deliveryChallanShipmentModeId;
	}
	public void setDeliveryChallanShipmentModeId(long deliveryChallanShipmentModeId) {
		this.deliveryChallanShipmentModeId = deliveryChallanShipmentModeId;
	}
	public String getDeliveryChallanShipmentModeName() {
		return deliveryChallanShipmentModeName;
	}
	public void setDeliveryChallanShipmentModeName(String deliveryChallanShipmentModeName) {
		this.deliveryChallanShipmentModeName = deliveryChallanShipmentModeName;
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
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}




	public String getProductRequestManualId() {
		return productRequestManualId;
	}




	public void setProductRequestManualId(String productRequestManualId) {
		this.productRequestManualId = productRequestManualId;
	}




	public long getProductRequestId() {
		return productRequestId;
	}




	public void setProductRequestId(long productRequestId) {
		this.productRequestId = productRequestId;
	}




	public String getFromLocationName() {
		return fromLocationName;
	}




	public void setFromLocationName(String fromLocationName) {
		this.fromLocationName = fromLocationName;
	}




	public long getFromLocationId() {
		return fromLocationId;
	}




	public void setFromLocationId(long fromLocationId) {
		this.fromLocationId = fromLocationId;
	}




	public String getToLocationName() {
		return toLocationName;
	}




	public void setToLocationName(String toLocationName) {
		this.toLocationName = toLocationName;
	}




	public long getToLocationId() {
		return toLocationId;
	}




	public void setToLocationId(long toLocationId) {
		this.toLocationId = toLocationId;
	}




	public String getOwnerFirstName() {
		return ownerFirstName;
	}




	public void setOwnerFirstName(String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}




	public String getOwnerLastName() {
		return ownerLastName;
	}




	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}




	public String getOwnerEmailId() {
		return ownerEmailId;
	}




	public void setOwnerEmailId(String ownerEmailId) {
		this.ownerEmailId = ownerEmailId;
	}




	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}




	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}




	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}




	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}




	public String getOwnerMiddleName() {
		return ownerMiddleName;
	}




	public void setOwnerMiddleName(String ownerMiddleName) {
		this.ownerMiddleName = ownerMiddleName;
	}
	
	
}
