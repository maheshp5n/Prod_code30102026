package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BRCRepairInventoryReceiveRecentVisit implements java.io.Serializable,OldPresist<BRCRepairInventoryReceiveRecentVisit>{
	@Id
	long receivingId;
	String visitorEmailId;
	Date visistdate;

	public BRCRepairInventoryReceiveRecentVisit() {
	}

	public BRCRepairInventoryReceiveRecentVisit(long receivingId, String visitorEmailId, Date visistdate) {
		this.receivingId = receivingId;
		this.visitorEmailId = visitorEmailId;
		this.visistdate = visistdate;
	}

	public long getReceivingId() {
		return receivingId;
	}

	public void setReceivingId(long receivingId) {
		this.receivingId = receivingId;
	}

	public String getVisitorEmailId() {
		return visitorEmailId;
	}

	public void setVisitorEmailId(String visitorEmailId) {
		this.visitorEmailId = visitorEmailId;
	}

	public Date getVisistdate() {
		return visistdate;
	}

	public void setVisistdate(Date visistdate) {
		this.visistdate = visistdate;
	}


	@javax.persistence.Transient transient BRCRepairInventoryReceiveRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BRCRepairInventoryReceiveRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BRCRepairInventoryReceiveRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+receivingId;
	}
}
