package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ProductFamilySubProduct implements java.io.Serializable,OldPresist<ProductFamilySubProduct>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long productFamilySubProductId;
	String productFailySubProductName;
	@ManyToOne(fetch = FetchType.LAZY)
	ProductFamily productFamily;
	
	boolean synced;
	
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
	public ProductFamilySubProduct() {}
	public ProductFamilySubProduct(long id) {
		this.productFamilySubProductId=id;
	}
	public ProductFamilySubProduct(long id,String name,long subId) {
		this.productFamilySubProductId=id;
		this.productFailySubProductName=name;
		this.productFamily=new ProductFamily(subId);
	}
	public ProductFamilySubProduct(long subId,String name) {
		this.productFamily=new ProductFamily(subId);
		this.productFailySubProductName=name;
	}
	
	/**
	 * @return the productFamilySubProductId
	 */
	public long getProductFamilySubProductId() {
		return productFamilySubProductId;
	}
	/**
	 * @return the productFailySubProductName
	 */
	public String getProductFailySubProductName() {
		return productFailySubProductName;
	}
	/**
	 * @return the productFamily
	 */
	public ProductFamily getProductFamily() {
		return productFamily;
	}
	/**
	 * @param productFamilySubProductId the productFamilySubProductId to set
	 */
	public void setProductFamilySubProductId(long productFamilySubProductId) {
		this.productFamilySubProductId = productFamilySubProductId;
	}
	/**
	 * @param productFailySubProductName the productFailySubProductName to set
	 */
	public void setProductFailySubProductName(String productFailySubProductName) {
		this.productFailySubProductName = productFailySubProductName;
	}
	/**
	 * @param productFamily the productFamily to set
	 */
	public void setProductFamily(ProductFamily productFamily) {
		this.productFamily = productFamily;
	}
	
	

	@javax.persistence.Transient transient ProductFamilySubProduct old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ProductFamilySubProduct)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ProductFamilySubProduct gOld() {
		return old;
	}
	public String toString() {
			return ""+productFamilySubProductId;
	}
}
