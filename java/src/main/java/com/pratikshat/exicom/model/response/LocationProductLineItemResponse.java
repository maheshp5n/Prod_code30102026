package com.pratikshat.exicom.model.response;

public class LocationProductLineItemResponse {
	long id;
	String locationProductLineItemManualId;
	long locationProductItemId;
	String locationProductItemManualId;
	String itemSerialNumber;
	long locationProductLineItemStatusId;
	String locationProductLineItemStatusName;
	long locationProeuctLineItemStateId;
	String locationProeuctLineItemStateName;
	boolean inStock;
	boolean replaced;
	boolean returned;
	String createdByEmailId;
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	java.util.Date createdDate;
	String modifiedByEmailId;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	java.util.Date modifiedDate;
	boolean customerInventory;
	public LocationProductLineItemResponse() {}
	public LocationProductLineItemResponse(com.pratikshat.exicom.entity.LocationProductLineItems items) {
		this.id=items.getId();
		this.locationProductLineItemManualId=items.getLocationProductLineItemId();
		if(items.getLocationProductItem()!=null) {
			this.locationProductItemId=items.getLocationProductItem().getId();
			this.locationProductItemManualId=items.getLocationProductItem().getLocationProductItemId();
		}
		this.itemSerialNumber=items.getItemSerialNumber();
		if(items.getLocationProductLineItemStatus()!=null) {
			this.locationProductLineItemStatusId=items.getLocationProductLineItemStatus().getStatusId();
			this.locationProductLineItemStatusName=items.getLocationProductLineItemStatus().getStatusName();
		}
		if(items.getLocationProeuctLineItemState()!=null) {
			this.locationProeuctLineItemStateId=items.getLocationProeuctLineItemState().getProductStateId();
			this.locationProeuctLineItemStateName=items.getLocationProeuctLineItemState().getProductStateName();
		}
		this.inStock=items.isInStock();
		this.replaced=items.isReplaced();
		this.returned=items.isReturned();
		if(items.getCreatedBy()!=null) {
			this.createdByEmailId=items.getCreatedBy().getEmailId();
			this.createdByFirstName=items.getCreatedBy().getFirstName();
			this.createdByMiddleName=items.getCreatedBy().getMiddleName();
			this.createdByLastName=items.getCreatedBy().getLastName();
		}
		this.createdDate=items.getCreatedDate();
		if(items.getModifiedBy()!=null) {
			this.modifiedByEmailId=items.getModifiedBy().getEmailId();
			this.modifiedByFirstName=items.getModifiedBy().getFirstName();
			this.modifiedByMiddleName=items.getModifiedBy().getMiddleName();
			this.modifiedByLastName=items.getModifiedBy().getLastName();
		}
		this.modifiedDate=items.getModifiedDate();
		this.customerInventory=items.isCustomerInventory();
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the locationProductLineItemManualId
	 */
	public String getLocationProductLineItemManualId() {
		return locationProductLineItemManualId;
	}
	/**
	 * @return the locationProductItemId
	 */
	public long getLocationProductItemId() {
		return locationProductItemId;
	}
	/**
	 * @return the locationProductItemManualId
	 */
	public String getLocationProductItemManualId() {
		return locationProductItemManualId;
	}
	/**
	 * @return the itemSerialNumber
	 */
	public String getItemSerialNumber() {
		return itemSerialNumber;
	}
	/**
	 * @return the locationProductLineItemStatusId
	 */
	public long getLocationProductLineItemStatusId() {
		return locationProductLineItemStatusId;
	}
	/**
	 * @return the locationProductLineItemStatusName
	 */
	public String getLocationProductLineItemStatusName() {
		return locationProductLineItemStatusName;
	}
	/**
	 * @return the locationProeuctLineItemStateId
	 */
	public long getLocationProeuctLineItemStateId() {
		return locationProeuctLineItemStateId;
	}
	/**
	 * @return the locationProeuctLineItemStateName
	 */
	public String getLocationProeuctLineItemStateName() {
		return locationProeuctLineItemStateName;
	}
	/**
	 * @return the inStock
	 */
	public boolean isInStock() {
		return inStock;
	}
	/**
	 * @return the replaced
	 */
	public boolean isReplaced() {
		return replaced;
	}
	/**
	 * @return the returned
	 */
	public boolean isReturned() {
		return returned;
	}
	/**
	 * @return the createdByEmailId
	 */
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	/**
	 * @return the createdByFirstName
	 */
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	/**
	 * @return the createdByLastName
	 */
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	/**
	 * @return the createdDate
	 */
	public java.util.Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the modifiedByEmailId
	 */
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}
	/**
	 * @return the modifiedByFirstName
	 */
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	/**
	 * @return the modifiedByLastName
	 */
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	/**
	 * @return the modifiedDate
	 */
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @return the customerInventory
	 */
	public boolean isCustomerInventory() {
		return customerInventory;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param locationProductLineItemManualId the locationProductLineItemManualId to set
	 */
	public void setLocationProductLineItemManualId(String locationProductLineItemManualId) {
		this.locationProductLineItemManualId = locationProductLineItemManualId;
	}
	/**
	 * @param locationProductItemId the locationProductItemId to set
	 */
	public void setLocationProductItemId(long locationProductItemId) {
		this.locationProductItemId = locationProductItemId;
	}
	/**
	 * @param locationProductItemManualId the locationProductItemManualId to set
	 */
	public void setLocationProductItemManualId(String locationProductItemManualId) {
		this.locationProductItemManualId = locationProductItemManualId;
	}
	/**
	 * @param itemSerialNumber the itemSerialNumber to set
	 */
	public void setItemSerialNumber(String itemSerialNumber) {
		this.itemSerialNumber = itemSerialNumber;
	}
	/**
	 * @param locationProductLineItemStatusId the locationProductLineItemStatusId to set
	 */
	public void setLocationProductLineItemStatusId(int locationProductLineItemStatusId) {
		this.locationProductLineItemStatusId = locationProductLineItemStatusId;
	}
	/**
	 * @param locationProductLineItemStatusName the locationProductLineItemStatusName to set
	 */
	public void setLocationProductLineItemStatusName(String locationProductLineItemStatusName) {
		this.locationProductLineItemStatusName = locationProductLineItemStatusName;
	}
	/**
	 * @param locationProeuctLineItemStateId the locationProeuctLineItemStateId to set
	 */
	public void setLocationProeuctLineItemStateId(int locationProeuctLineItemStateId) {
		this.locationProeuctLineItemStateId = locationProeuctLineItemStateId;
	}
	/**
	 * @param locationProeuctLineItemStateName the locationProeuctLineItemStateName to set
	 */
	public void setLocationProeuctLineItemStateName(String locationProeuctLineItemStateName) {
		this.locationProeuctLineItemStateName = locationProeuctLineItemStateName;
	}
	/**
	 * @param inStock the inStock to set
	 */
	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}
	/**
	 * @param replaced the replaced to set
	 */
	public void setReplaced(boolean replaced) {
		this.replaced = replaced;
	}
	/**
	 * @param returned the returned to set
	 */
	public void setReturned(boolean returned) {
		this.returned = returned;
	}
	/**
	 * @param createdByEmailId the createdByEmailId to set
	 */
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	/**
	 * @param createdByFirstName the createdByFirstName to set
	 */
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	/**
	 * @param createdByLastName the createdByLastName to set
	 */
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param modifiedByEmailId the modifiedByEmailId to set
	 */
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	/**
	 * @param modifiedByFirstName the modifiedByFirstName to set
	 */
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	/**
	 * @param modifiedByLastName the modifiedByLastName to set
	 */
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @param customerInventory the customerInventory to set
	 */
	public void setCustomerInventory(boolean customerInventory) {
		this.customerInventory = customerInventory;
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
	public void setLocationProductLineItemStatusId(long locationProductLineItemStatusId) {
		this.locationProductLineItemStatusId = locationProductLineItemStatusId;
	}
	public void setLocationProeuctLineItemStateId(long locationProeuctLineItemStateId) {
		this.locationProeuctLineItemStateId = locationProeuctLineItemStateId;
	}
	
	
}
