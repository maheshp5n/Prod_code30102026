package com.pratikshat.exicom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ServiceAppoinmentProductConsumed implements java.io.Serializable,OldPresist<ServiceAppoinmentProductConsumed>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long id;
	String manualId;
	boolean synked;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	ServiceAppointment serviceAppointment;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	Products product;
	@javax.persistence.ManyToOne(fetch=FetchType.EAGER)
	ProductConsumptionType consumptionType;
	float quantity;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	java.util.Date createdDate;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date modifiedDate;
	@Column(length =1000)
	String remark;
	public ServiceAppoinmentProductConsumed() {}
	public ServiceAppoinmentProductConsumed(long id) {
		this.id=id;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the manualId
	 */
	public String getManualId() {
		return manualId;
	}
	/**
	 * @return the synked
	 */
	public boolean isSynked() {
		return synked;
	}
	/**
	 * @return the serviceAppointment
	 */
	public ServiceAppointment getServiceAppointment() {
		return serviceAppointment;
	}
	/**
	 * @return the product
	 */
	public Products getProduct() {
		return product;
	}
	/**
	 * @return the consumptionType
	 */
	public ProductConsumptionType getConsumptionType() {
		return consumptionType;
	}
	/**
	 * @return the quantity
	 */
	public float getQuantity() {
		return quantity;
	}
	/**
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	/**
	 * @return the createdDate
	 */
	public java.util.Date getCreatedDate() {
		return createdDate;
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
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param manualId the manualId to set
	 */
	public void setManualId(String manualId) {
		this.manualId = manualId;
	}
	/**
	 * @param synked the synked to set
	 */
	public void setSynked(boolean synked) {
		this.synked = synked;
	}
	/**
	 * @param serviceAppointment the serviceAppointment to set
	 */
	public void setServiceAppointment(ServiceAppointment serviceAppointment) {
		this.serviceAppointment = serviceAppointment;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Products product) {
		this.product = product;
	}
	/**
	 * @param consumptionType the consumptionType to set
	 */
	public void setConsumptionType(ProductConsumptionType consumptionType) {
		this.consumptionType = consumptionType;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
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
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

	@javax.persistence.Transient transient ServiceAppoinmentProductConsumed old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ServiceAppoinmentProductConsumed)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ServiceAppoinmentProductConsumed gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
