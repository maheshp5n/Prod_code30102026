package com.pratikshat.exicom.model.response;

public class LocationProductItemForList {
	long id;
	String locationProductItemId;
	String productPartCode;
	String productDescription;
	String productTypeName;
	
	public LocationProductItemForList() {}
	public LocationProductItemForList(long id,String locationProductItemId,String productPartCode) {
		this.id=id;
		this.locationProductItemId=locationProductItemId;
		this.productPartCode=productPartCode;
	}
	public LocationProductItemForList(long id,String locationProductItemId,String productPartCode,String productTypeName) {
		this.id=id;
		this.locationProductItemId=locationProductItemId;
		this.productPartCode=productPartCode;
		
		this.productTypeName=productTypeName;
	}
	
	public LocationProductItemForList(long id,String locationProductItemId,String productPartCode,String productTypeName,String productDescription) {
		this.id=id;
		this.locationProductItemId=locationProductItemId;
		this.productPartCode=productPartCode;
		this.productDescription=productDescription;
		this.productTypeName=productTypeName;
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the locationProductItemId
	 */
	public String getLocationProductItemId() {
		return locationProductItemId;
	}
	/**
	 * @return the productPartCode
	 */
	public String getProductPartCode() {
		return productPartCode;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param locationProductItemId the locationProductItemId to set
	 */
	public void setLocationProductItemId(String locationProductItemId) {
		this.locationProductItemId = locationProductItemId;
	}
	/**
	 * @param productPartCode the productPartCode to set
	 */
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	/**
	 * @return the productTypeName
	 */
	public String getProductTypeName() {
		return productTypeName;
	}
	/**
	 * @param productTypeName the productTypeName to set
	 */
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	
	
}
