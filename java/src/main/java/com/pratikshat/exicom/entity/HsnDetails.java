package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class HsnDetails implements java.io.Serializable,OldPresist<HsnDetails>{
	@Id
	String hsnCode;
	Float igst;
	Float cgst;
	Float sgst;
	
	
	public HsnDetails() {
	}

	public HsnDetails(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public HsnDetails(String hsnCode, Float igst, Float cgst, Float sgst) {
		this.hsnCode = hsnCode;
		this.igst = igst;
		this.cgst = cgst;
		this.sgst = sgst;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public Float getIgst() {
		return igst;
	}

	public void setIgst(Float igst) {
		this.igst = igst;
	}

	public Float getCgst() {
		return cgst;
	}

	public void setCgst(Float cgst) {
		this.cgst = cgst;
	}

	public Float getSgst() {
		return sgst;
	}

	public void setSgst(Float sgst) {
		this.sgst = sgst;
	}
	
	

	@javax.persistence.Transient transient HsnDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(HsnDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public HsnDetails gOld() {
		return old;
	}
	public String toString() {
			return ""+hsnCode;
	}
}
