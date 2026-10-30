package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.PriceBook;

public class PriceBookResponse {

	long id;

	String name;
	String description;

	boolean isActive;
	boolean isStandardPriceBook;

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

	public PriceBookResponse() {}
	
	public PriceBookResponse(PriceBook priceBook) {
		this.id = priceBook.getId();
		this.name = priceBook.getName();
		this.description = priceBook.getDescription();
		this.isActive = priceBook.isActive();
		this.isStandardPriceBook = priceBook.isStandardPriceBook();

		if (priceBook.getCreatedBy() != null) {
			this.createdByFirstName = priceBook.getCreatedBy().getFirstName();
			this.createdByLastName = priceBook.getCreatedBy().getLastName();
			this.createdByMiddleName = priceBook.getCreatedBy().getMiddleName();
			this.createdByEmailId = priceBook.getCreatedBy().getEmailId();
			this.createdDate = priceBook.getCreatedDate();
		}

		if (priceBook.getModifiedBy() != null) {
			this.modifiedByFirstName = priceBook.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = priceBook.getModifiedBy().getMiddleName();
			this.modifiedByLastName = priceBook.getModifiedBy().getLastName();
			this.modifiedByEmailId = priceBook.getModifiedBy().getEmailId();
			this.modifiedDate = priceBook.getModifiedDate();
		}
	}

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
