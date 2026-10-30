package com.pratikshat.exicom.entity;

import java.io.Serializable;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BRCRepariInventoryProductConsumed implements java.io.Serializable,OldPresist<BRCRepariInventoryProductConsumed>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long consumedId;
	java.util.Date consumeddate;
	java.util.Date materialIssueDate;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	com.pratikshat.exicom.entity.Products product;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	com.pratikshat.exicom.entity.BRCRepairInventoryReceive receiveDetails;
	float quantity;
	java.util.Date faultySubmissionDate;
	String remarks;
	
	/**
	 * @return the consumedId
	 */
	public long getConsumedId() {
		return consumedId;
	}
	/**
	 * @return the consumeddate
	 */
	public java.util.Date getConsumeddate() {
		return consumeddate;
	}
	/**
	 * @return the materialIssueDate
	 */
	public java.util.Date getMaterialIssueDate() {
		return materialIssueDate;
	}
	/**
	 * @return the product
	 */
	public com.pratikshat.exicom.entity.Products getProduct() {
		return product;
	}
	/**
	 * @return the receiveDetails
	 */
	public com.pratikshat.exicom.entity.BRCRepairInventoryReceive getReceiveDetails() {
		return receiveDetails;
	}
	/**
	 * @return the quantity
	 */
	public float getQuantity() {
		return quantity;
	}
	/**
	 * @return the faultySubmissionDate
	 */
	public java.util.Date getFaultySubmissionDate() {
		return faultySubmissionDate;
	}
	/**
	 * @param consumedId the consumedId to set
	 */
	public void setConsumedId(long consumedId) {
		this.consumedId = consumedId;
	}
	/**
	 * @param consumeddate the consumeddate to set
	 */
	public void setConsumeddate(java.util.Date consumeddate) {
		this.consumeddate = consumeddate;
	}
	/**
	 * @param materialIssueDate the materialIssueDate to set
	 */
	public void setMaterialIssueDate(java.util.Date materialIssueDate) {
		this.materialIssueDate = materialIssueDate;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(com.pratikshat.exicom.entity.Products product) {
		this.product = product;
	}
	/**
	 * @param receiveDetails the receiveDetails to set
	 */
	public void setReceiveDetails(com.pratikshat.exicom.entity.BRCRepairInventoryReceive receiveDetails) {
		this.receiveDetails = receiveDetails;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	/**
	 * @param faultySubmissionDate the faultySubmissionDate to set
	 */
	public void setFaultySubmissionDate(java.util.Date faultySubmissionDate) {
		this.faultySubmissionDate = faultySubmissionDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	

	@javax.persistence.Transient transient BRCRepariInventoryProductConsumed old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BRCRepariInventoryProductConsumed)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BRCRepariInventoryProductConsumed gOld() {
		return old;
	}
	public String toString() {
			return ""+consumedId;
	}
}
