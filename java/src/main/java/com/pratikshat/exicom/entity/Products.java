package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class Products implements java.io.Serializable,OldPresist<Products>{
	@Id
	String productPartCode;
	
	@Column(length=500)
	String pdoductDescription;
	@ManyToOne(fetch=FetchType.LAZY)
	ProductFamilySubProduct subProductFamily;
	String productType;
	float productPrice;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createddate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	boolean synced;
	boolean active;
	
	@ManyToOne(fetch=FetchType.LAZY)
	HsnDetails hsnDetails;
	/**
	 * @return the synced
	 */
	public boolean isSynced() {
		return synced;
	}

	/**
	 * @param synced the synced to set
	 */
	public void setSynced(boolean synced) {
		this.synced = synced;
	}

	public Products() {}
	
	public Products(String partCode) {
		this.productPartCode=partCode;
	}
	public void setSubProductFamilyId(long id) {
		this.subProductFamily=new ProductFamilySubProduct(id);
	}
	
	/**
	 * @return the productPartCode
	 */
	public String getProductPartCode() {
		return productPartCode;
	}
	/**
	 * @return the pdoductDescription
	 */
	public String getPdoductDescription() {
		return pdoductDescription;
	}
	/**
	 * @return the subProductFamily
	 */
	public ProductFamilySubProduct getSubProductFamily() {
		return subProductFamily;
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
	 * @param productPartCode the productPartCode to set
	 */
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	/**
	 * @param pdoductDescription the pdoductDescription to set
	 */
	public void setPdoductDescription(String pdoductDescription) {
		this.pdoductDescription = pdoductDescription;
	}
	/**
	 * @param subProductFamily the subProductFamily to set
	 */
	public void setSubProductFamily(ProductFamilySubProduct subProductFamily) {
		this.subProductFamily = subProductFamily;
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
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	/**
	 * @return the createddate
	 */
	public Date getCreateddate() {
		return createddate;
	}

	/**
	 * @return the modifiedBy
	 */
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @param createddate the createddate to set
	 */
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public HsnDetails getHsnDetails() {
		return hsnDetails;
	}

	public void setHsnDetails(HsnDetails hsnDetails) {
		this.hsnDetails = hsnDetails;
	}

	@Override
	public String toString() {
		return  productPartCode;
	}
	

	@javax.persistence.Transient transient Products old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(Products)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public Products gOld() {
		return old;
	}}
