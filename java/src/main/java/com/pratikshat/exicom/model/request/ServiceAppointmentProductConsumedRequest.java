package com.pratikshat.exicom.model.request;

public class ServiceAppointmentProductConsumedRequest {
	long serviceAppointmentNumber;
	long productConsumedId;
	String partCode;
	float consumedQty;
	String consumeType;
	String remark;
	
	java.util.List<ServiceAppointmentProductConsumedLineitemRequest> productLineItem;
	/**
	 * @return the serviceAppointmentNumber
	 */
	public long getServiceAppointmentNumber() {
		return serviceAppointmentNumber;
	}
	/**
	 * @return the productConsumedId
	 */
	public long getProductConsumedId() {
		return productConsumedId;
	}
	/**
	 * @return the partCode
	 */
	public String getPartCode() {
		return partCode;
	}
	/**
	 * @return the consumedQty
	 */
	public float getConsumedQty() {
		return consumedQty;
	}
	/**
	 * @return the consumeType
	 */
	public String getConsumeType() {
		return consumeType;
	}
	/**
	 * @return the productLineItem
	 */
	public java.util.List<ServiceAppointmentProductConsumedLineitemRequest> getProductLineItem() {
		return productLineItem;
	}
	/**
	 * @param serviceAppointmentNumber the serviceAppointmentNumber to set
	 */
	public void setServiceAppointmentNumber(long serviceAppointmentNumber) {
		this.serviceAppointmentNumber = serviceAppointmentNumber;
	}
	/**
	 * @param productConsumedId the productConsumedId to set
	 */
	public void setProductConsumedId(long productConsumedId) {
		this.productConsumedId = productConsumedId;
	}
	/**
	 * @param partCode the partCode to set
	 */
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	/**
	 * @param consumedQty the consumedQty to set
	 */
	public void setConsumedQty(float consumedQty) {
		this.consumedQty = consumedQty;
	}
	/**
	 * @param consumeType the consumeType to set
	 */
	public void setConsumeType(String consumeType) {
		this.consumeType = consumeType;
	}
	/**
	 * @param productLineItem the productLineItem to set
	 */
	public void setProductLineItem(java.util.List<ServiceAppointmentProductConsumedLineitemRequest> productLineItem) {
		this.productLineItem = productLineItem;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
