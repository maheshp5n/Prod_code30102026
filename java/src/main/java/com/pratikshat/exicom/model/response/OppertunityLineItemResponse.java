package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.OppertunityLineItem;

public class OppertunityLineItemResponse {

	long id;
	
	String productPartCode;
	String pdoductDescription;
	float productPrice;
	long priceBookEntryId;
	
	long oppertunityId;
	String oppertunityNumber;
	
	long priceBookId;
	String priceBookName;
	
	double listPrice;
	double salePrice;
	double quantity;
	double totalPrice;
	
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
	
	public OppertunityLineItemResponse(OppertunityLineItem oppertunityLineItem) {
		this.id = oppertunityLineItem.getId();
		this.salePrice = oppertunityLineItem.getPrice();
		this.quantity = oppertunityLineItem.getQuantity();
		this.totalPrice = oppertunityLineItem.getTotalPrice();
		
		if(oppertunityLineItem.getPriceBookEntries()!=null) {
			this.listPrice = oppertunityLineItem.getPriceBookEntries().getListPrice();
			if(oppertunityLineItem.getPriceBookEntries().getProduct()!=null) {
				this.productPartCode = oppertunityLineItem.getPriceBookEntries().getProduct().getProductPartCode();
				this.pdoductDescription = oppertunityLineItem.getPriceBookEntries().getProduct().getPdoductDescription();
				this.productPrice = oppertunityLineItem.getPriceBookEntries().getProduct().getProductPrice();
				this.priceBookEntryId = oppertunityLineItem.getPriceBookEntries().getId();
			}
			if(oppertunityLineItem.getPriceBookEntries().getPriceBook()!=null) {
				this.priceBookId = oppertunityLineItem.getPriceBookEntries().getPriceBook().getId();
				this.priceBookName = oppertunityLineItem.getPriceBookEntries().getPriceBook().getName();
			}
		}
		
		if(oppertunityLineItem.getOppertunity()!=null) {
			this.oppertunityId = oppertunityLineItem.getOppertunity().getOppertunityId();
			this.oppertunityNumber = oppertunityLineItem.getOppertunity().getOppertunityNumber();
		}
		
		if(oppertunityLineItem.getCreatedBy()!=null) {
			this.createdByFirstName = oppertunityLineItem.getCreatedBy().getFirstName();
			this.createdByMiddleName = oppertunityLineItem.getCreatedBy().getMiddleName();
			this.createdByLastName = oppertunityLineItem.getCreatedBy().getLastName();
			this.createdByEmailId = oppertunityLineItem.getCreatedBy().getEmailId();
			this.createdDate = oppertunityLineItem.getCreatedDate();
		}
		
		if(oppertunityLineItem.getModifiedBy()!=null) {
			this.modifiedByFirstName = oppertunityLineItem.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = oppertunityLineItem.getModifiedBy().getMiddleName();
			this.modifiedByLastName = oppertunityLineItem.getModifiedBy().getLastName();
			this.modifiedByEmailId = oppertunityLineItem.getModifiedBy().getEmailId();
			this.modifiedDate = oppertunityLineItem.getModifiedDate();
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
	public long getOppertunityId() {
		return oppertunityId;
	}
	public void setOppertunityId(long oppertunityId) {
		this.oppertunityId = oppertunityId;
	}
	public String getOppertunityNumber() {
		return oppertunityNumber;
	}
	public void setOppertunityNumber(String oppertunityNumber) {
		this.oppertunityNumber = oppertunityNumber;
	}
	public double getListPrice() {
		return listPrice;
	}
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	public double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
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
	public long getPriceBookEntryId() {
		return priceBookEntryId;
	}
	public void setPriceBookEntryId(long priceBookEntryId) {
		this.priceBookEntryId = priceBookEntryId;
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
