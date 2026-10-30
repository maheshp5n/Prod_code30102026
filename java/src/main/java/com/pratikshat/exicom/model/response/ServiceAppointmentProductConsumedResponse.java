package com.pratikshat.exicom.model.response;

public class ServiceAppointmentProductConsumedResponse {
	long serviceAppointmentId;
	String serviceAppointmentManualId;
	String partCode;
	String partDescription;
	float consumedQty;
	String consumedType;
	String remark;
	
	public ServiceAppointmentProductConsumedResponse() {}
	public ServiceAppointmentProductConsumedResponse(
			long serviceAppointmentId,
			String serviceAppointmentManualId,
			String partCode,
			String partDescription,
			float consumedQty,
			String consumedType,
			String remark
			) {
		this.serviceAppointmentId=serviceAppointmentId;
		this.serviceAppointmentManualId=serviceAppointmentManualId;
		this.partCode=partCode;
		this.partDescription=partDescription;
		this.consumedQty=consumedQty;
		this.consumedType=consumedType;
		this.remark = remark;
	}
	
	/**
	 * @return the serviceAppointmentId
	 */
	public long getServiceAppointmentId() {
		return serviceAppointmentId;
	}
	/**
	 * @return the serviceAppointmentManualId
	 */
	public String getServiceAppointmentManualId() {
		return serviceAppointmentManualId;
	}
	/**
	 * @return the partCode
	 */
	public String getPartCode() {
		return partCode;
	}
	/**
	 * @return the partDescription
	 */
	public String getPartDescription() {
		return partDescription;
	}
	/**
	 * @return the consumedQty
	 */
	public float getConsumedQty() {
		return consumedQty;
	}
	/**
	 * @return the consumedType
	 */
	public String getConsumedType() {
		return consumedType;
	}
	/**
	 * @param serviceAppointmentId the serviceAppointmentId to set
	 */
	public void setServiceAppointmentId(long serviceAppointmentId) {
		this.serviceAppointmentId = serviceAppointmentId;
	}
	/**
	 * @param serviceAppointmentManualId the serviceAppointmentManualId to set
	 */
	public void setServiceAppointmentManualId(String serviceAppointmentManualId) {
		this.serviceAppointmentManualId = serviceAppointmentManualId;
	}
	/**
	 * @param partCode the partCode to set
	 */
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	/**
	 * @param partDescription the partDescription to set
	 */
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	/**
	 * @param consumedQty the consumedQty to set
	 */
	public void setConsumedQty(float consumedQty) {
		this.consumedQty = consumedQty;
	}
	/**
	 * @param consumedType the consumedType to set
	 */
	public void setConsumedType(String consumedType) {
		this.consumedType = consumedType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
