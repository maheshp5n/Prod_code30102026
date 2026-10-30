package com.pratikshat.exicom.model.request;

public class PriceBookRequest {

	long id;
	
	String name;
	String description;
	
	boolean isActive;
	boolean isStandardPriceBook;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isStandardPriceBook() {
		return isStandardPriceBook;
	}
	public void setStandardPriceBook(boolean isStandardPriceBook) {
		this.isStandardPriceBook = isStandardPriceBook;
	}
	
	
	
	
}
