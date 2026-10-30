package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class RequisitionProductDetails implements java.io.Serializable,OldPresist<RequisitionProductDetails>{
	
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Requisition requisition;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Products products;
	
	float quantitty;
	
	

	public RequisitionProductDetails(long id) {
		this.id = id;
	}
	
	

	public RequisitionProductDetails() {
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Requisition getRequisition() {
		return requisition;
	}

	public void setRequisition(Requisition requisition) {
		this.requisition = requisition;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public float getQuantitty() {
		return quantitty;
	}

	public void setQuantitty(float quantitty) {
		this.quantitty = quantitty;
	}
	
	
	


	@javax.persistence.Transient transient RequisitionProductDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(RequisitionProductDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public RequisitionProductDetails gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
