package com.pratikshat.exicom.entity;

import javax.persistence.FetchType;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ServiceAppointmentProductConsumedLineItems implements java.io.Serializable,OldPresist<ServiceAppointmentProductConsumedLineItems>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long id;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	ServiceAppoinmentProductConsumed productConsumed;
	String consumedLineItemSerialNumber;
	String replacedLineItemSerialNumber;
	float qty;
	boolean synked;
	boolean refurbished=false;
	
	String faultyPartcode;
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the productConsumed
	 */
	public ServiceAppoinmentProductConsumed getProductConsumed() {
		return productConsumed;
	}
	/**
	 * @return the consumedLineItemSerialNumber
	 */
	public String getConsumedLineItemSerialNumber() {
		return consumedLineItemSerialNumber;
	}
	/**
	 * @return the replacedLineItemSerialNumber
	 */
	public String getReplacedLineItemSerialNumber() {
		return replacedLineItemSerialNumber;
	}
	/**
	 * @return the qty
	 */
	public float getQty() {
		return qty;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param productConsumed the productConsumed to set
	 */
	public void setProductConsumed(ServiceAppoinmentProductConsumed productConsumed) {
		this.productConsumed = productConsumed;
	}
	/**
	 * @param consumedLineItemSerialNumber the consumedLineItemSerialNumber to set
	 */
	public void setConsumedLineItemSerialNumber(String consumedLineItemSerialNumber) {
		this.consumedLineItemSerialNumber = consumedLineItemSerialNumber;
	}
	/**
	 * @param replacedLineItemSerialNumber the replacedLineItemSerialNumber to set
	 */
	public void setReplacedLineItemSerialNumber(String replacedLineItemSerialNumber) {
		this.replacedLineItemSerialNumber = replacedLineItemSerialNumber;
	}
	/**
	 * @param qty the qty to set
	 */
	public void setQty(float qty) {
		this.qty = qty;
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
	public boolean isRefurbished() {
		return refurbished;
	}
	public void setRefurbished(boolean refurbished) {
		this.refurbished = refurbished;
	}
	
	

	public String getFaultyPartcode() {
		return faultyPartcode;
	}
	public void setFaultyPartcode(String faultyPartcode) {
		this.faultyPartcode = faultyPartcode;
	}



	@javax.persistence.Transient transient ServiceAppointmentProductConsumedLineItems old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ServiceAppointmentProductConsumedLineItems)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ServiceAppointmentProductConsumedLineItems gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
