package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtProductTransfer extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtProductTransfer>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String manualId; //Initial will start with PT
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	Location sourceLocation;
	@ManyToOne(fetch=FetchType.LAZY)
	Location destinationLocation;
	
	String shippingToName;
	String trackingNumber;
	String shippingMode;
	
	java.util.Date actualDeliveryDate;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	java.util.Date receivedDate;

	Float totalQuantityIssued;
	Float totalQuantityReceived;
	boolean synced;
	//SAP FIELD
	String DELIV;
	String REMARK_D;
	String INVOICE;
	String REMARK_I;
	String MBLNR_G;
	String MJAHR_G;
	String REMARK_G;
	
	String transferStatus;

	public BtProductTransfer() {}
	
	public BtProductTransfer(Long id) {
		this.id=id;
	}

	
	
	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
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
	public Date getActualDeliveryDate() {
		return actualDeliveryDate;
	}
	/**
	 * @return the owner
	 */
	public EmployeeDetails getOwner() {
		return owner;
	}
	/**
	 * @param btProductRequest the btProductRequest to set
	 */
	
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
	public void setActualDeliveryDate(Date actualDeliveryDate) {
		this.actualDeliveryDate = actualDeliveryDate;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}
	
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Location getSourceLocation(){
		return this.sourceLocation;
	}

	public void setSourceLocation(Location sLocation){
		this.sourceLocation=sLocation;
	}
	public void setDestinationLocation(Location destinationLocation){
		this.destinationLocation=destinationLocation;
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



	@Transient transient BtProductTransfer old;
	@Override
	@PostLoad
	public void old() {
		old=(BtProductTransfer)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BtProductTransfer gOld() {
		return old;
	}
	public String toString() {
			return ""+this.id;
	}
	@Override
	public Long getId() {
		
		return this.id;
	}
	@Override
	public void setId(Long id) {
		
		this.id = id;
	}
	@Override
	public String getManualId() {
		
		return manualId;
	}
	@Override
	public void setManualId(String manualId) {
		
		this.manualId =manualId;		
	}

	public Location getDestinationLocation() {
		return destinationLocation;
	}

	public Float getTotalQuantityIssued() {
		return totalQuantityIssued;
	}

	public void setTotalQuantityIssued(Float totalQuantityIssued) {
		this.totalQuantityIssued = totalQuantityIssued;
	}

	public Float getTotalQuantityReceived() {
		return totalQuantityReceived;
	}

	public void setTotalQuantityReceived(Float totalQuantityReceived) {
		this.totalQuantityReceived = totalQuantityReceived;
	}

	public String getTransferStatus() {
		return transferStatus;
	}

	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}

	

}
