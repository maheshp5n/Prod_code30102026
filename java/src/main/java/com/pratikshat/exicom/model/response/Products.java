package com.pratikshat.exicom.model.response;

import java.util.Date;

public class Products {
	String partCode;
	String productDescription;
	long productFamilyId;
	String productFamilyName;
	long productSubFamilyId;
	String productSubFamilyName;
	String productType;
	float productPrice;
	String createdByEmailId;
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	Date createdDate;
	String modifiedByEmailId;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	Date modifiedDate;
	boolean isActive;
	public Products() {}
	public Products(com.pratikshat.exicom.entity.Products product,com.pratikshat.exicom.entity.ProductFamily productFamily) {
		if(product!=null) {
			this.partCode=product.getProductPartCode();
			this.productDescription=product.getPdoductDescription();
			this.isActive = product.isActive();
			if(product.getSubProductFamily()!=null) {
				this.productSubFamilyId=product.getSubProductFamily().getProductFamilySubProductId();
				this.productSubFamilyName=product.getSubProductFamily().getProductFailySubProductName();
			}
			this.productType=product.getProductType();
			this.productPrice=product.getProductPrice();
			if(product.getCreatedBy()!=null) {
				this.createdByEmailId=product.getCreatedBy().getEmailId();
				this.createdByFirstName=product.getCreatedBy().getFirstName();
				this.createdByMiddleName=product.getCreatedBy().getMiddleName();
				this.createdByLastName=product.getCreatedBy().getLastName();
			}
			this.createdDate=product.getCreateddate();
			if(product.getModifiedBy()!=null) {
				this.modifiedByEmailId=product.getModifiedBy().getEmailId();
				this.modifiedByFirstName=product.getModifiedBy().getFirstName();
				this.modifiedByMiddleName=product.getModifiedBy().getMiddleName();
				this.modifiedByLastName=product.getModifiedBy().getLastName();
			}
			this.modifiedDate=product.getModifiedDate();
			if(productFamily!=null) {
				this.productFamilyId=productFamily.getProductFamilyId();
				this.productFamilyName=productFamily.getProductFamilyName();
			}
		}
	}
	/**
	 * @return the partCode
	 */
	public String getPartCode() {
		return partCode;
	}
	/**
	 * @return the productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}
	/**
	 * @return the productFamilyId
	 */
	public long getProductFamilyId() {
		return productFamilyId;
	}
	/**
	 * @return the productFamilyName
	 */
	public String getProductFamilyName() {
		return productFamilyName;
	}
	/**
	 * @return the productSubFamilyId
	 */
	public long getProductSubFamilyId() {
		return productSubFamilyId;
	}
	/**
	 * @return the productSubFamilyName
	 */
	public String getProductSubFamilyName() {
		return productSubFamilyName;
	}
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}
	/**
	 * @return the productPrice
	 */
	public float getProductPrice() {
		return productPrice;
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
	public Date getCreatedDate() {
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
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param partCode the partCode to set
	 */
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	/**
	 * @param productDescription the productDescription to set
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	/**
	 * @param productFamilyId the productFamilyId to set
	 */
	public void setProductFamilyId(long productFamilyId) {
		this.productFamilyId = productFamilyId;
	}
	/**
	 * @param productFamilyName the productFamilyName to set
	 */
	public void setProductFamilyName(String productFamilyName) {
		this.productFamilyName = productFamilyName;
	}
	/**
	 * @param productSubFamilyId the productSubFamilyId to set
	 */
	public void setProductSubFamilyId(long productSubFamilyId) {
		this.productSubFamilyId = productSubFamilyId;
	}
	/**
	 * @param productSubFamilyName the productSubFamilyName to set
	 */
	public void setProductSubFamilyName(String productSubFamilyName) {
		this.productSubFamilyName = productSubFamilyName;
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	/**
	 * @param productPrice the productPrice to set
	 */
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
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
	public void setCreatedDate(Date createdDate) {
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
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
