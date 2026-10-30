package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class OppertunityLineItem implements java.io.Serializable,OldPresist<OppertunityLineItem>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	PriceBookEntries priceBookEntries;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Oppertunity oppertunity;
	
	double price;
	double quantity;
	double totalPrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	boolean isDeleted;

	public OppertunityLineItem() {
		
	}

	public OppertunityLineItem(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public PriceBookEntries getPriceBookEntries() {
		return priceBookEntries;
	}

	public void setPriceBookEntries(PriceBookEntries priceBookEntries) {
		this.priceBookEntries = priceBookEntries;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Oppertunity getOppertunity() {
		return oppertunity;
	}

	public void setOppertunity(Oppertunity oppertunity) {
		this.oppertunity = oppertunity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	

	@javax.persistence.Transient transient OppertunityLineItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(OppertunityLineItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public OppertunityLineItem gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
