package com.pratikshat.exicom.model.request;

public class BRCRepariInventoryProductConsumedRequest {
	long miCode;
	String productPartCode;
	float quantity;
	java.util.Date materialIssueDate;
	java.util.Date faultySubmissionDate;
	String remarks;
	long consumeid;
	
	/**
	 * @return the consumeid
	 */
	public long getConsumeid() {
		return consumeid;
	}
	/**
	 * @param consumeid the consumeid to set
	 */
	public void setConsumeid(long consumeid) {
		this.consumeid = consumeid;
	}
	/**
	 * @return the miCode
	 */
	public long getMiCode() {
		return miCode;
	}
	/**
	 * @return the productPartCode
	 */
	public String getProductPartCode() {
		return productPartCode;
	}
	/**
	 * @return the quantity
	 */
	public float getQuantity() {
		return quantity;
	}
	/**
	 * @return the materialIssueDate
	 */
	public java.util.Date getMaterialIssueDate() {
		return materialIssueDate;
	}
	/**
	 * @return the faultySubmissionDate
	 */
	public java.util.Date getFaultySubmissionDate() {
		return faultySubmissionDate;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param miCode the miCode to set
	 */
	public void setMiCode(long miCode) {
		this.miCode = miCode;
	}
	/**
	 * @param productPartCode the productPartCode to set
	 */
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	/**
	 * @param materialIssueDate the materialIssueDate to set
	 */
	public void setMaterialIssueDate(java.util.Date materialIssueDate) {
		this.materialIssueDate = materialIssueDate;
	}
	/**
	 * @param faultySubmissionDate the faultySubmissionDate to set
	 */
	public void setFaultySubmissionDate(java.util.Date faultySubmissionDate) {
		this.faultySubmissionDate = faultySubmissionDate;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
