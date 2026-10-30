package com.pratikshat.exicom.model.request;

public class PriceBookEntriesRequest {

	long id;
	long priceBookId;
	String partCode;
	boolean isActive;
	float listPrice;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPriceBookId() {
		return priceBookId;
	}
	public void setPriceBookId(long priceBookId) {
		this.priceBookId = priceBookId;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public float getListPrice() {
		return listPrice;
	}
	public void setListPrice(float listPrice) {
		this.listPrice = listPrice;
	}
	
	
}
