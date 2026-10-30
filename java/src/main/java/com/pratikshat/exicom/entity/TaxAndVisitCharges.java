package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class TaxAndVisitCharges implements java.io.Serializable,OldPresist<TaxAndVisitCharges>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	float cgst;
	float igst;
	float sgst;
	float visitCharges;
	
	@ManyToOne
	BusinessDomain businessDomain;

	public TaxAndVisitCharges() {
		
	}

	public TaxAndVisitCharges(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getCgst() {
		return cgst;
	}

	public void setCgst(float cgst) {
		this.cgst = cgst;
	}

	public float getIgst() {
		return igst;
	}

	public void setIgst(float igst) {
		this.igst = igst;
	}

	public float getSgst() {
		return sgst;
	}

	public void setSgst(float sgst) {
		this.sgst = sgst;
	}

	public float getVisitCharges() {
		return visitCharges;
	}

	public void setVisitCharges(float visitCharges) {
		this.visitCharges = visitCharges;
	}

	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}
	
	
	

	@javax.persistence.Transient transient TaxAndVisitCharges old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(TaxAndVisitCharges)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public TaxAndVisitCharges gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
