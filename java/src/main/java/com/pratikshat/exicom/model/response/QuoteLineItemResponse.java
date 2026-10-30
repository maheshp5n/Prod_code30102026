package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.QuoteLineItem;

public class QuoteLineItemResponse {

	
	long id;
	
	String productPartCode;
	String pdoductDescription;
	float productPrice;
	long priceBookEntryId;
	
	long quoteId;
	String quoteNumber;
	String quoteName;
	
	long priceBookId;
	String priceBookName;
	
	
	double price;
	double quantity;
	double totalPrice;
	double listPrice;
	double totalRepairPrice;
	double cgstAmount;
	double sgstAmount;
	double igstAmount;
	double totalPriceWithTax;
	
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	String createdByEmailId;
	Date createdDate;
	
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	String modifiedByEmailId;
	Date modifiedDate;
	
	
	public QuoteLineItemResponse(QuoteLineItem quoteLineItem) {
		
		this.id = quoteLineItem.getId();
		this.quantity = quoteLineItem.getQuantity();
		this.totalPrice = quoteLineItem.getTotalPrice();
		
		this.price = quoteLineItem.getPrice();
		this.totalRepairPrice = quoteLineItem.getTotalRepairPrice();
		this.cgstAmount = quoteLineItem.getCgstAmount();
		this.sgstAmount = quoteLineItem.getSgstAmount();
		this.igstAmount = quoteLineItem.getIgstAmount();
		this.totalPriceWithTax = quoteLineItem.getTotalPriceWithTax();
		
		if(quoteLineItem.getPriceBookEntries()!=null) {
			this.listPrice = quoteLineItem.getPriceBookEntries().getListPrice();
			if(quoteLineItem.getPriceBookEntries().getProduct()!=null) {
				this.productPartCode = quoteLineItem.getPriceBookEntries().getProduct().getProductPartCode();
				this.pdoductDescription = quoteLineItem.getPriceBookEntries().getProduct().getPdoductDescription();
				this.productPrice = quoteLineItem.getPriceBookEntries().getProduct().getProductPrice();
				this.priceBookEntryId = quoteLineItem.getPriceBookEntries().getId();
			}
			if(quoteLineItem.getPriceBookEntries().getPriceBook()!=null) {
				this.priceBookId = quoteLineItem.getPriceBookEntries().getPriceBook().getId();
				this.priceBookName = quoteLineItem.getPriceBookEntries().getPriceBook().getName();
			}
		}
		
		if(quoteLineItem.getQuote()!=null) {
			this.quoteId = quoteLineItem.getQuote().getId();
			this.quoteNumber = quoteLineItem.getQuote().getQuoteNumber();
			this.quoteName = quoteLineItem.getQuote().getQuoteName();
		}
		
		if(quoteLineItem.getCreatedBy()!=null) {
			this.createdByFirstName = quoteLineItem.getCreatedBy().getFirstName();
			this.createdByMiddleName = quoteLineItem.getCreatedBy().getMiddleName();
			this.createdByLastName = quoteLineItem.getCreatedBy().getLastName();
			this.createdByEmailId = quoteLineItem.getCreatedBy().getEmailId();
			this.createdDate = quoteLineItem.getCreatedDate();
		}
		
		if(quoteLineItem.getModifiedBy()!=null) {
			this.modifiedByFirstName = quoteLineItem.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = quoteLineItem.getModifiedBy().getMiddleName();
			this.modifiedByLastName = quoteLineItem.getModifiedBy().getLastName();
			this.modifiedByEmailId = quoteLineItem.getModifiedBy().getEmailId();
			this.modifiedDate = quoteLineItem.getModifiedDate();
		}
	}
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProductPartCode() {
		return productPartCode;
	}
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	public String getPdoductDescription() {
		return pdoductDescription;
	}
	public void setPdoductDescription(String pdoductDescription) {
		this.pdoductDescription = pdoductDescription;
	}
	public float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	public long getPriceBookEntryId() {
		return priceBookEntryId;
	}
	public void setPriceBookEntryId(long priceBookEntryId) {
		this.priceBookEntryId = priceBookEntryId;
	}
	
	
	public long getQuoteId() {
		return quoteId;
	}



	public void setQuoteId(long quoteId) {
		this.quoteId = quoteId;
	}



	public String getQuoteNumber() {
		return quoteNumber;
	}



	public void setQuoteNumber(String quoteNumber) {
		this.quoteNumber = quoteNumber;
	}



	public String getQuoteName() {
		return quoteName;
	}



	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}



	public long getPriceBookId() {
		return priceBookId;
	}
	public void setPriceBookId(long priceBookId) {
		this.priceBookId = priceBookId;
	}
	public String getPriceBookName() {
		return priceBookName;
	}
	public void setPriceBookName(String priceBookName) {
		this.priceBookName = priceBookName;
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
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public double getListPrice() {
		return listPrice;
	}
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	public double getTotalRepairPrice() {
		return totalRepairPrice;
	}
	public void setTotalRepairPrice(double totalRepairPrice) {
		this.totalRepairPrice = totalRepairPrice;
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
	public double getTotalPriceWithTax() {
		return totalPriceWithTax;
	}
	public void setTotalPriceWithTax(double totalPriceWithTax) {
		this.totalPriceWithTax = totalPriceWithTax;
	}
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}



	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}



	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}



	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}



	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}
	
	
	
}
