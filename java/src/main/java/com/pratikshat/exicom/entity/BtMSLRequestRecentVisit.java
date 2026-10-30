package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@javax.persistence.EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtMSLRequestRecentVisit implements Serializable,OldPresist<BtMSLRequestRecentVisit>{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long btMslId;
	String visitorId;
	
	Date visitedDate;


	public BtMSLRequestRecentVisit(long btMslId, String visitorId) {
		this.btMslId = btMslId;
		this.visitorId = visitorId;
		this.visitedDate = new Date();
	}


	public BtMSLRequestRecentVisit() {
		
	}


	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getBtMslId() {
		return btMslId;
	}


	public void setBtMslId(long btMslId) {
		this.btMslId = btMslId;
	}


	public String getVisitorId() {
		return visitorId;
	}


	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}


	public Date getVisitedDate() {
		return visitedDate;
	}


	public void setVisitedDate(Date visitedDate) {
		this.visitedDate = visitedDate;
	}



	@javax.persistence.Transient transient BtMSLRequestRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BtMSLRequestRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BtMSLRequestRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
	
}
