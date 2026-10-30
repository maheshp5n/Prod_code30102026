package com.pratikshat.exicom.entity;

import java.io.Serializable;

@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class AssetsId implements java.io.Serializable,OldPresist<AssetsId>{
	String serialNumber;
	Products product;
	public AssetsId() {}
	public AssetsId(String serialNumber,Products product) {
		this.serialNumber=serialNumber;
		this.product=product;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Products getProduct() {
		return product;
	}
	public void setProduct(Products product) {
		this.product = product;
	}
	
	

	@javax.persistence.Transient transient AssetsId old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(AssetsId)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public AssetsId gOld() {
		return old;
	}
	public String toString() {
			return "";
	}
}
