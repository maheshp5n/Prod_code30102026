package com.pratikshat.exicom.entity;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class MSLRequestItem implements java.io.Serializable,OldPresist<MSLRequestItem>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long id;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	MSLRequest mslRequest;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	Products products;
	float oldHSL;
	float newHSL;
	float olfMSL;
	float newMSL;
	String remarks;

	
	public MSLRequestItem() {}
	public MSLRequestItem(long id) {
		this.id=id;
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the mslRequest
	 */
	public MSLRequest getMslRequest() {
		return mslRequest;
	}
	
	/**
	 * @return the oldHSL
	 */
	public float getOldHSL() {
		return oldHSL;
	}
	/**
	 * @return the newHSL
	 */
	public float getNewHSL() {
		return newHSL;
	}
	/**
	 * @return the olfMSL
	 */
	public float getOlfMSL() {
		return olfMSL;
	}
	/**
	 * @return the newMSL
	 */
	public float getNewMSL() {
		return newMSL;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param mslRequest the mslRequest to set
	 */
	public void setMslRequest(MSLRequest mslRequest) {
		this.mslRequest = mslRequest;
	}
	
	/**
	 * @param oldHSL the oldHSL to set
	 */
	public void setOldHSL(float oldHSL) {
		this.oldHSL = oldHSL;
	}
	/**
	 * @param newHSL the newHSL to set
	 */
	public void setNewHSL(float newHSL) {
		this.newHSL = newHSL;
	}
	/**
	 * @param olfMSL the olfMSL to set
	 */
	public void setOlfMSL(float olfMSL) {
		this.olfMSL = olfMSL;
	}
	/**
	 * @param newMSL the newMSL to set
	 */
	public void setNewMSL(float newMSL) {
		this.newMSL = newMSL;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the products
	 */
	public Products getProducts() {
		return products;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(Products products) {
		this.products = products;
	}
	

	@javax.persistence.Transient transient MSLRequestItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(MSLRequestItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public MSLRequestItem gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
