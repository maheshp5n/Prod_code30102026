package com.pratikshat.exicom.entity;

import java.io.Serializable;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class LProductReturnOrder implements java.io.Serializable,OldPresist<LProductReturnOrder>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long lprOrderid;// locationProductReturnOrderId;
	String lprOrderManualId;//Initials will be RO
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	Location sourceLocation;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	Location destinationLocation;
	float totalQuantityReturned;
	float totalQuantityReceived;
	String locationReturnOrderStatus;
	
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails owner;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails modifiedBy;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	String shipToName;
	String shippingMode;
	String trackingNumber;
	java.util.Date expectedArrivalDate;
	
	//SAP FIELD
	String DELIV;
	String REMARK_D;
	String INVOICE;
	String REMARK_I;
	String MBLNR_G;
	String MJAHR_G;
	String REMARK_G;
	
	public LProductReturnOrder() {}
	public LProductReturnOrder(long lprOrderid) {
		this.lprOrderid=lprOrderid;
	}
	/**
	 * @return the lprOrderid
	 */
	public long getLprOrderid() {
		return lprOrderid;
	}
	/**
	 * @return the lprOrderManualId
	 */
	public String getLprOrderManualId() {
		return lprOrderManualId;
	}
	/**
	 * @return the sourceLocation
	 */
	public Location getSourceLocation() {
		return sourceLocation;
	}
	/**
	 * @return the destinationLocation
	 */
	public Location getDestinationLocation() {
		return destinationLocation;
	}
	/**
	 * @return the totalQuantityReturned
	 */
	public float getTotalQuantityReturned() {
		return totalQuantityReturned;
	}
	/**
	 * @return the totalQuantityReceived
	 */
	public float getTotalQuantityReceived() {
		return totalQuantityReceived;
	}
	/**
	 * @return the locationReturnOrderStatus
	 */
	public String getLocationReturnOrderStatus() {
		return locationReturnOrderStatus;
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
	 * @param lprOrderid the lprOrderid to set
	 */
	public void setLprOrderid(long lprOrderid) {
		this.lprOrderid = lprOrderid;
	}
	/**
	 * @param lprOrderManualId the lprOrderManualId to set
	 */
	public void setLprOrderManualId(String lprOrderManualId) {
		this.lprOrderManualId = lprOrderManualId;
	}
	/**
	 * @param sourceLocation the sourceLocation to set
	 */
	public void setSourceLocation(Location sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	/**
	 * @param destinationLocation the destinationLocation to set
	 */
	public void setDestinationLocation(Location destinationLocation) {
		this.destinationLocation = destinationLocation;
	}
	/**
	 * @param totalQuantityReturned the totalQuantityReturned to set
	 */
	public void setTotalQuantityReturned(float totalQuantityReturned) {
		this.totalQuantityReturned = totalQuantityReturned;
	}
	/**
	 * @param totalQuantityReceived the totalQuantityReceived to set
	 */
	public void setTotalQuantityReceived(float totalQuantityReceived) {
		this.totalQuantityReceived = totalQuantityReceived;
	}
	/**
	 * @param locationReturnOrderStatus the locationReturnOrderStatus to set
	 */
	public void setLocationReturnOrderStatus(String locationReturnOrderStatus) {
		this.locationReturnOrderStatus = locationReturnOrderStatus;
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
	/**
	 * @return the shipToName
	 */
	public String getShipToName() {
		return shipToName;
	}
	/**
	 * @return the shippingMode
	 */
	public String getShippingMode() {
		return shippingMode;
	}
	/**
	 * @return the trackingNumber
	 */
	public String getTrackingNumber() {
		return trackingNumber;
	}
	/**
	 * @return the expectedArrivalDate
	 */
	public java.util.Date getExpectedArrivalDate() {
		return expectedArrivalDate;
	}
	/**
	 * @param shipToName the shipToName to set
	 */
	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}
	/**
	 * @param shippingMode the shippingMode to set
	 */
	public void setShippingMode(String shippingMode) {
		this.shippingMode = shippingMode;
	}
	/**
	 * @param trackingNumber the trackingNumber to set
	 */
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	/**
	 * @param expectedArrivalDate the expectedArrivalDate to set
	 */
	public void setExpectedArrivalDate(java.util.Date expectedArrivalDate) {
		this.expectedArrivalDate = expectedArrivalDate;
	}
	
	

	@javax.persistence.Transient transient LProductReturnOrder old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(LProductReturnOrder)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public LProductReturnOrder gOld() {
		return old;
	}
	public String toString() {
			return ""+lprOrderid;
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
	
	
}
