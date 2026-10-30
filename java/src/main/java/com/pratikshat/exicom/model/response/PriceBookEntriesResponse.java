package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.PriceBookEntries;

public class PriceBookEntriesResponse {

	long id;
	boolean isActive;
	float listPrice;
	
	String partCode;
	String description;
	float partCodePrice;
	String productFamily;
	String productType;
	
	String priceBookName;
	long priceBookId;
	
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
	
	public PriceBookEntriesResponse() {}
	
	public PriceBookEntriesResponse(PriceBookEntries priceBookEntries) {
		this.id = priceBookEntries.getId();
		this.isActive = priceBookEntries.isActive();
		this.listPrice = priceBookEntries.getListPrice();
		
		if(priceBookEntries.getProduct()!=null) {
			this.partCode = priceBookEntries.getProduct().getProductPartCode();
			this.description = priceBookEntries.getProduct().getPdoductDescription();
			this.partCodePrice = priceBookEntries.getProduct().getProductPrice();
			this.productFamily = priceBookEntries.getProduct().getSubProductFamily().getProductFamily().getProductFamilyName();
			this.productType = priceBookEntries.getProduct().getProductType();
		}
		
		if(priceBookEntries.getPriceBook()!=null) {
			this.priceBookName = priceBookEntries.getPriceBook().getName();
			this.priceBookId = priceBookEntries.getPriceBook().getId();
		}
		
		if(priceBookEntries.getCreatedBy()!=null) {
			this.createdByFirstName = priceBookEntries.getCreatedBy().getFirstName();
			this.createdByMiddleName = priceBookEntries.getCreatedBy().getFirstName();
			this.createdByLastName = priceBookEntries.getCreatedBy().getLastName();
			this.createdByEmailId = priceBookEntries.getCreatedBy().getEmailId();
			this.createdDate = priceBookEntries.getCreatedDate();
		}
		
		if(priceBookEntries.getModifiedBy()!=null) {
			this.modifiedByFirstName = priceBookEntries.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = priceBookEntries.getModifiedBy().getMiddleName();
			this.modifiedByLastName = priceBookEntries.getModifiedBy().getLastName();
			this.modifiedByEmailId = priceBookEntries.getModifiedBy().getEmailId();
			this.modifiedDate = priceBookEntries.getModifiedDate();
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPartCodePrice() {
		return partCodePrice;
	}

	public void setPartCodePrice(float partCodePrice) {
		this.partCodePrice = partCodePrice;
	}

	public String getPriceBookName() {
		return priceBookName;
	}

	public void setPriceBookName(String priceBookName) {
		this.priceBookName = priceBookName;
	}

	public long getPriceBookId() {
		return priceBookId;
	}

	public void setPriceBookId(long priceBookId) {
		this.priceBookId = priceBookId;
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

	public String getProductFamily() {
		return productFamily;
	}

	public void setProductFamily(String productFamily) {
		this.productFamily = productFamily;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
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
