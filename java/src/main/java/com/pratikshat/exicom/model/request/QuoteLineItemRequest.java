package com.pratikshat.exicom.model.request;

public class QuoteLineItemRequest {

	long id;
	
	long priceBookEntryId;
	long quoteId;
	
	float price;
	float quantity;
	String partCode;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	
	
}
