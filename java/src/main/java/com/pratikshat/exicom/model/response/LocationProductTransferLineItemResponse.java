package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LPTransferLineItem;

public class LocationProductTransferLineItemResponse {
	long productTransferItemId;
	long productTransferLineItemId;
	String productTransferLineItemManualId;
	String productPartCode;
	String productPartDescription;
	String productType;
	String productLineItemSerialNumber;
	boolean received;
	
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	Date createdDate;
	
	
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	Date modifiedDate;
	
	
	
	/**
	 * 
	 */
	public LocationProductTransferLineItemResponse() {
	
	}
	/**
	 * @param productTransferItemId
	 * @param productTransferLineItemId
	 * @param productTransferLineItemManualId
	 * @param productPartCode
	 * @param productPartDescription
	 * @param productType
	 * @param productLineItemSerialNumber
	 * @param received
	 */
	public LocationProductTransferLineItemResponse(long productTransferItemId, long productTransferLineItemId,
			String productTransferLineItemManualId, String productPartCode, String productPartDescription,
			String productType, String productLineItemSerialNumber,boolean received) {
		this.productTransferItemId = productTransferItemId;
		this.productTransferLineItemId = productTransferLineItemId;
		this.productTransferLineItemManualId = productTransferLineItemManualId;
		this.productPartCode = productPartCode;
		this.productPartDescription = productPartDescription;
		this.productType = productType;
		this.productLineItemSerialNumber = productLineItemSerialNumber;
		this.received=received;
	}
	
	
	public LocationProductTransferLineItemResponse(long productTransferItemId, long productTransferLineItemId,
			String productTransferLineItemManualId, String productPartCode, String productPartDescription,
			String productType, String productLineItemSerialNumber,boolean received, EmployeeDetails createdBy, Date createdDate, EmployeeDetails modifiedBy, Date modifiedDate) {
		this.productTransferItemId = productTransferItemId;
		this.productTransferLineItemId = productTransferLineItemId;
		this.productTransferLineItemManualId = productTransferLineItemManualId;
		this.productPartCode = productPartCode;
		this.productPartDescription = productPartDescription;
		this.productType = productType;
		this.productLineItemSerialNumber = productLineItemSerialNumber;
		this.received=received;
		
		if(createdBy!=null){
			this.createdByFirstName = createdBy.getFirstName();
			this.createdByMiddleName = createdBy.getMiddleName();
			this.createdByLastName = createdBy.getLastName();
			this.createdDate = createdDate;
		}
		
		if(modifiedBy!=null){
			this.modifiedByFirstName = modifiedBy.getFirstName();
			this.modifiedByMiddleName = modifiedBy.getMiddleName();
			this.modifiedByLastName = modifiedBy.getLastName();
			this.modifiedDate = modifiedDate;
		}
	}
	
	
	
	/**
	 * @return the productTransferItemId
	 */
	public long getProductTransferItemId() {
		return productTransferItemId;
	}
	/**
	 * @return the productTransferLineItemId
	 */
	public long getProductTransferLineItemId() {
		return productTransferLineItemId;
	}
	/**
	 * @return the productTransferLineItemManualId
	 */
	public String getProductTransferLineItemManualId() {
		return productTransferLineItemManualId;
	}
	/**
	 * @return the productPartCode
	 */
	public String getProductPartCode() {
		return productPartCode;
	}
	/**
	 * @return the productPartDescription
	 */
	public String getProductPartDescription() {
		return productPartDescription;
	}
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}
	/**
	 * @return the productLineItemSerialNumber
	 */
	public String getProductLineItemSerialNumber() {
		return productLineItemSerialNumber;
	}
	/**
	 * @param productTransferItemId the productTransferItemId to set
	 */
	public void setProductTransferItemId(long productTransferItemId) {
		this.productTransferItemId = productTransferItemId;
	}
	/**
	 * @param productTransferLineItemId the productTransferLineItemId to set
	 */
	public void setProductTransferLineItemId(long productTransferLineItemId) {
		this.productTransferLineItemId = productTransferLineItemId;
	}
	/**
	 * @param productTransferLineItemManualId the productTransferLineItemManualId to set
	 */
	public void setProductTransferLineItemManualId(String productTransferLineItemManualId) {
		this.productTransferLineItemManualId = productTransferLineItemManualId;
	}
	/**
	 * @param productPartCode the productPartCode to set
	 */
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	/**
	 * @param productPartDescription the productPartDescription to set
	 */
	public void setProductPartDescription(String productPartDescription) {
		this.productPartDescription = productPartDescription;
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	/**
	 * @param productLineItemSerialNumber the productLineItemSerialNumber to set
	 */
	public void setProductLineItemSerialNumber(String productLineItemSerialNumber) {
		this.productLineItemSerialNumber = productLineItemSerialNumber;
	}
	/**
	 * @return the received
	 */
	public boolean isReceived() {
		return received;
	}
	/**
	 * @param received the received to set
	 */
	public void setReceived(boolean received) {
		this.received = received;
	}
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}
	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}
	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
}
