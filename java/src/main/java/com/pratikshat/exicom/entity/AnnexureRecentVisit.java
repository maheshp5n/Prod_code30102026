package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class AnnexureRecentVisit implements java.io.Serializable,OldPresist<AnnexureRecentVisit>{

	@Id
	long annexureId;
	String visitorEmailId;
	Date visistdate;
	public AnnexureRecentVisit() {}
	
	public AnnexureRecentVisit(long annexureId, String visitorEmailId) {
		this.annexureId = annexureId;
		this.visitorEmailId = visitorEmailId;
		this.visistdate = new Date();
	}

	public long getAnnexureId() {
		return annexureId;
	}

	public void setAnnexureId(long annexureId) {
		this.annexureId = annexureId;
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
	
	

	@javax.persistence.Transient transient AnnexureRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(AnnexureRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public AnnexureRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+annexureId;
	}
}
