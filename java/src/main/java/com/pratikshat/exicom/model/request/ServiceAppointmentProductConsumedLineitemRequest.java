package com.pratikshat.exicom.model.request;

public class ServiceAppointmentProductConsumedLineitemRequest {
	long id;
	String consumedItemSerialNumber;
	String replacedItemSerialNumber;
	String faultyPartcode;
	float qty;
	boolean refurbished;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the consumedItemSerialNumber
	 */
	public String getConsumedItemSerialNumber() {
		return consumedItemSerialNumber;
	}
	/**
	 * @return the replacedItemSerialNumber
	 */
	public String getReplacedItemSerialNumber() {
		return replacedItemSerialNumber;
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
	 * @param consumedItemSerialNumber the consumedItemSerialNumber to set
	 */
	public void setConsumedItemSerialNumber(String consumedItemSerialNumber) {
		this.consumedItemSerialNumber = consumedItemSerialNumber;
	}
	/**
	 * @param replacedItemSerialNumber the replacedItemSerialNumber to set
	 */
	public void setReplacedItemSerialNumber(String replacedItemSerialNumber) {
		this.replacedItemSerialNumber = replacedItemSerialNumber;
	}
	/**
	 * @param qty the qty to set
	 */
	public void setQty(float qty) {
		this.qty = qty;
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
	
	
}
