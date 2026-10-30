package com.pratikshat.exicom.model.response;

import com.pratikshat.exicom.entity.ProductLineItemProductState;

public class LocationFaultyLineItemResponse {
	long locationItemId;
	long locationLineItemId;
	String locationLineItemManualId;
	String serialNumber;
	String partCode;
	String pdoductDescription;
	long productStateId;
	String productStateName;
	
	public LocationFaultyLineItemResponse() {}
	public LocationFaultyLineItemResponse(long locationItemId,long locationLineItemId,String locationLineItemManualId,String serialNumber) {
		this.locationItemId=locationItemId;
		this.locationLineItemId=locationLineItemId;
		this.locationLineItemManualId=locationLineItemManualId;
		this.serialNumber=serialNumber;
	}
	
	public LocationFaultyLineItemResponse(long locationItemId,long locationLineItemId,String locationLineItemManualId,String serialNumber, String partCode, String pdoductDescription, long productstateId,String productStateName) {
		this.locationItemId=locationItemId;
		this.locationLineItemId=locationLineItemId;
		this.locationLineItemManualId=locationLineItemManualId;
		this.serialNumber=serialNumber;
		this.partCode = partCode;
		this.pdoductDescription = pdoductDescription;
		this.productStateId = productstateId;
		this.productStateName = productStateName;
	}
	
	/**
	 * @return the locationItemId
	 */
	public long getLocationItemId() {
		return locationItemId;
	}
	/**
	 * @return the locationLineItemId
	 */
	public long getLocationLineItemId() {
		return locationLineItemId;
	}
	/**
	 * @return the locationLineItemManualId
	 */
	public String getLocationLineItemManualId() {
		return locationLineItemManualId;
	}
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param locationItemId the locationItemId to set
	 */
	public void setLocationItemId(long locationItemId) {
		this.locationItemId = locationItemId;
	}
	/**
	 * @param locationLineItemId the locationLineItemId to set
	 */
	public void setLocationLineItemId(long locationLineItemId) {
		this.locationLineItemId = locationLineItemId;
	}
	/**
	 * @param locationLineItemManualId the locationLineItemManualId to set
	 */
	public void setLocationLineItemManualId(String locationLineItemManualId) {
		this.locationLineItemManualId = locationLineItemManualId;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public String getPdoductDescription() {
		return pdoductDescription;
	}
	public void setPdoductDescription(String pdoductDescription) {
		this.pdoductDescription = pdoductDescription;
	}
	public long getProductStateId() {
		return productStateId;
	}
	public void setProductStateId(long productStateId) {
		this.productStateId = productStateId;
	}
	public String getProductStateName() {
		return productStateName;
	}
	public void setProductStateName(String productStateName) {
		this.productStateName = productStateName;
	}
	
}	
