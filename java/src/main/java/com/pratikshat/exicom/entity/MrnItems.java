package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class MrnItems implements java.io.Serializable,OldPresist<MrnItems>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(fetch=FetchType.LAZY)
	MrnDetails mrnDetails;
	@ManyToOne(fetch=FetchType.LAZY)
	Products product;
	float qty;
	Double rateUnitQty;
	Double taxableAmount;
	Float discount;
	Double taxableAmountAfterDiscount;
	Float igst;
	Float cgst;
	Float sgst;
	Double totalAmount;
	
	Double returnQty;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MrnDetails getMrnDetails() {
		return mrnDetails;
	}
	public void setMrnDetails(MrnDetails mrnDetails) {
		this.mrnDetails = mrnDetails;
	}
	public Products getProduct() {
		return product;
	}
	public void setProduct(Products product) {
		this.product = product;
	}
	
	public Double getRateUnitQty() {
		return rateUnitQty;
	}
	public void setRateUnitQty(Double rateUnitQty) {
		this.rateUnitQty = rateUnitQty;
	}
	public Double getTaxableAmount() {
		return taxableAmount;
	}
	public void setTaxableAmount(Double taxableAmount) {
		this.taxableAmount = taxableAmount;
	}
	public Float getDiscount() {
		return discount;
	}
	public void setDiscount(Float discount) {
		this.discount = discount;
	}
	public Double getTaxableAmountAfterDiscount() {
		return taxableAmountAfterDiscount;
	}
	public void setTaxableAmountAfterDiscount(Double taxableAmountAfterDiscount) {
		this.taxableAmountAfterDiscount = taxableAmountAfterDiscount;
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
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public float getQty() {
		return qty;
	}
	public void setQty(float qty) {
		this.qty = qty;
	}
	public Double getReturnQty() {
		return returnQty;
	}
	public void setReturnQty(Double returnQty) {
		this.returnQty = returnQty;
	}
	
	

	@javax.persistence.Transient transient MrnItems old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(MrnItems)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public MrnItems gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
