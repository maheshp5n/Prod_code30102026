package com.pratikshat.exicom.entity;

import java.io.Serializable;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LPTrasfer implements java.io.Serializable,OldPresist<LPTrasfer>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long locationProductTransferId;
	String locationProductTransferManualId; //Initial will start with PT
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	LProductRequest locationProductRequest;
	String shippingToName;
	String trackingNumber;
	String shippingMode;
	java.util.Date actualDeliveryDate;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails owner;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	
	java.util.Date receivedDate;
	
	//SAP FIELD
	String DELIV;
	String REMARK_D;
	String INVOICE;
	String REMARK_I;
	String MBLNR_G;
	String MJAHR_G;
	String REMARK_G;
	
	public LPTrasfer() {}
	public LPTrasfer(long locationProductTransferId) {
		this.locationProductTransferId=locationProductTransferId;
	}
	
	/**
	 * @return the locationProductTransferId
	 */
	public long getLocationProductTransferId() {
		return locationProductTransferId;
	}
	/**
	 * @return the locationProductTransferManualId
	 */
	public String getLocationProductTransferManualId() {
		return locationProductTransferManualId;
	}
	/**
	 * @return the locationProductRequest
	 */
	public LProductRequest getLocationProductRequest() {
		return locationProductRequest;
	}
	/**
	 * @return the shippingToName
	 */
	public String getShippingToName() {
		return shippingToName;
	}
	/**
	 * @return the trackingNumber
	 */
	public String getTrackingNumber() {
		return trackingNumber;
	}
	/**
	 * @return the shippingMode
	 */
	public String getShippingMode() {
		return shippingMode;
	}
	/**
	 * @return the actualDeliveryDate
	 */
	public java.util.Date getActualDeliveryDate() {
		return actualDeliveryDate;
	}
	/**
	 * @return the owner
	 */
	public EmployeeDetails getOwner() {
		return owner;
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
	 * @param locationProductTransferId the locationProductTransferId to set
	 */
	public void setLocationProductTransferId(long locationProductTransferId) {
		this.locationProductTransferId = locationProductTransferId;
	}
	/**
	 * @param locationProductTransferManualId the locationProductTransferManualId to set
	 */
	public void setLocationProductTransferManualId(String locationProductTransferManualId) {
		this.locationProductTransferManualId = locationProductTransferManualId;
	}
	/**
	 * @param locationProductRequest the locationProductRequest to set
	 */
	public void setLocationProductRequest(LProductRequest locationProductRequest) {
		this.locationProductRequest = locationProductRequest;
	}
	/**
	 * @param shippingToName the shippingToName to set
	 */
	public void setShippingToName(String shippingToName) {
		this.shippingToName = shippingToName;
	}
	/**
	 * @param trackingNumber the trackingNumber to set
	 */
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	/**
	 * @param shippingMode the shippingMode to set
	 */
	public void setShippingMode(String shippingMode) {
		this.shippingMode = shippingMode;
	}
	/**
	 * @param actualDeliveryDate the actualDeliveryDate to set
	 */
	public void setActualDeliveryDate(java.util.Date actualDeliveryDate) {
		this.actualDeliveryDate = actualDeliveryDate;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
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
	
	
	public java.util.Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(java.util.Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	

	public String getDELIV() {
		return DELIV;
	}
	public void setDELIV(String dELIV) {
		DELIV = dELIV;
	}
	public String getREMARK_D() {
		return REMARK_D;
	}
	public void setREMARK_D(String rEMARK_D) {
		REMARK_D = rEMARK_D;
	}
	public String getINVOICE() {
		return INVOICE;
	}
	public void setINVOICE(String iNVOICE) {
		INVOICE = iNVOICE;
	}
	public String getREMARK_I() {
		return REMARK_I;
	}
	public void setREMARK_I(String rEMARK_I) {
		REMARK_I = rEMARK_I;
	}
	public String getMBLNR_G() {
		return MBLNR_G;
	}
	public void setMBLNR_G(String mBLNR_G) {
		MBLNR_G = mBLNR_G;
	}
	public String getMJAHR_G() {
		return MJAHR_G;
	}
	public void setMJAHR_G(String mJAHR_G) {
		MJAHR_G = mJAHR_G;
	}
	public String getREMARK_G() {
		return REMARK_G;
	}
	public void setREMARK_G(String rEMARK_G) {
		REMARK_G = rEMARK_G;
	}



	@javax.persistence.Transient transient LPTrasfer old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LPTrasfer)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LPTrasfer gOld() {
		return old;
	}
	public String toString() {
			return ""+locationProductTransferId;
	}
}
