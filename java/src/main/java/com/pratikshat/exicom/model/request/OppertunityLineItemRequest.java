package com.pratikshat.exicom.model.request;

public class OppertunityLineItemRequest {

	long id;
	double price;
	double quantity;
	String partCode;
	long priceBookEntryId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public long getPriceBookEntryId() {
		return priceBookEntryId;
	}
	public void setPriceBookEntryId(long priceBookEntryId) {
		this.priceBookEntryId = priceBookEntryId;
	}
	
	
	
}
