package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class DeliveryChallanShipmentMode implements java.io.Serializable,OldPresist<DeliveryChallanShipmentMode>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long shipmentModeId;
	String shipmentModeName;
	boolean synced;
	
	public DeliveryChallanShipmentMode(long shipmentModeId) {
		this.shipmentModeId = shipmentModeId;
	}

	public DeliveryChallanShipmentMode() {
	}

	public long getShipmentModeId() {
		return shipmentModeId;
	}

	public void setShipmentModeId(long shipmentModeId) {
		this.shipmentModeId = shipmentModeId;
	}

	public String getShipmentModeName() {
		return shipmentModeName;
	}

	public void setShipmentModeName(String shipmentModeName) {
		this.shipmentModeName = shipmentModeName;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	

	@javax.persistence.Transient transient DeliveryChallanShipmentMode old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(DeliveryChallanShipmentMode)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public DeliveryChallanShipmentMode gOld() {
		return old;
	}
	public String toString() {
			return ""+shipmentModeId;
	}
}
