package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ProductFamily implements java.io.Serializable,OldPresist<ProductFamily>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long productFamilyId;
	String productFamilyName;
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
	public ProductFamily() {}
	public ProductFamily(long id) {
		this.productFamilyId=id;
	}
	public ProductFamily(long id,String name) {
		this.productFamilyId=id;
		this.productFamilyName=name;
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
	
	
	

	@javax.persistence.Transient transient ProductFamily old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ProductFamily)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ProductFamily gOld() {
		return old;
	}
	public String toString() {
			return ""+productFamilyId;
	}
}
