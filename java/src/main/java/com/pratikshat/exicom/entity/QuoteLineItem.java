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
public class QuoteLineItem implements java.io.Serializable,OldPresist<QuoteLineItem>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	PriceBookEntries priceBookEntries;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Quote quote;
	
	double price;
	double quantity;
	double totalPrice;
	double totalRepairPrice;
	double cgstAmount;
	double sgstAmount;
	double igstAmount;
	double totalPriceWithTax;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	boolean isDeleted;
	
	
	public QuoteLineItem(long id) {
		this.id = id;
	}

	public QuoteLineItem() {
		
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

	public double getTotalRepairPrice() {
		return totalRepairPrice;
	}

	public void setTotalRepairPrice(double totalRepairPrice) {
		this.totalRepairPrice = totalRepairPrice;
	}

	public double getTotalPriceWithTax() {
		return totalPriceWithTax;
	}

	public void setTotalPriceWithTax(double totalPriceWithTax) {
		this.totalPriceWithTax = totalPriceWithTax;
	}

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
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

	public double getCgstAmount() {
		return cgstAmount;
	}

	public void setCgstAmount(double cgstAmount) {
		this.cgstAmount = cgstAmount;
	}

	public double getSgstAmount() {
		return sgstAmount;
	}

	public void setSgstAmount(double sgstAmount) {
		this.sgstAmount = sgstAmount;
	}

	public double getIgstAmount() {
		return igstAmount;
	}

	public void setIgstAmount(double igstAmount) {
		this.igstAmount = igstAmount;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	

	@javax.persistence.Transient transient QuoteLineItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(QuoteLineItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public QuoteLineItem gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
