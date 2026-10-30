package com.pratikshat.exicom.entity;


@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class WarehouseProductStockLineItems implements java.io.Serializable,OldPresist<WarehouseProductStockLineItems>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long id;
	String lineItemSerialNumber;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	WarehouseProductStock product;
	String status;
	boolean synked;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	@javax.persistence.ManyToOne
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne
	EmployeeDetails modifiedBy;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the product
	 */
	public WarehouseProductStock getProduct() {
		return product;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @return the createdDate
	 */
	public java.util.Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the modifiedDate
	 */
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	/**
	 * @return the modifiedBy
	 */
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(WarehouseProductStock product) {
		this.product = product;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the synked
	 */
	public boolean isSynked() {
		return synked;
	}
	/**
	 * @param synked the synked to set
	 */
	public void setSynked(boolean synked) {
		this.synked = synked;
	}
	/**
	 * @return the lineItemSerialNumber
	 */
	public String getLineItemSerialNumber() {
		return lineItemSerialNumber;
	}
	/**
	 * @param lineItemSerialNumber the lineItemSerialNumber to set
	 */
	public void setLineItemSerialNumber(String lineItemSerialNumber) {
		this.lineItemSerialNumber = lineItemSerialNumber;
	}
	

	@javax.persistence.Transient transient WarehouseProductStockLineItems old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(WarehouseProductStockLineItems)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public WarehouseProductStockLineItems gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
