package com.pratikshat.exicom.model.request;

public class LocationProductTransferReceiveRequest {
	long locationProductTransferItemId;
	float quantityReceive;
	java.util.List<String> receivedSerialNumber;
	
	/**
	 * @return the locationProductTransferItemId
	 */
	public long getLocationProductTransferItemId() {
		return locationProductTransferItemId;
	}
	/**
	 * @return the quantityReceive
	 */
	public float getQuantityReceive() {
		return quantityReceive;
	}
	/**
	 * @return the receivedSerialNumber
	 */
	public java.util.List<String> getReceivedSerialNumber() {
		return receivedSerialNumber;
	}
	/**
	 * @param locationProductTransferItemId the locationProductTransferItemId to set
	 */
	public void setLocationProductTransferItemId(long locationProductTransferItemId) {
		this.locationProductTransferItemId = locationProductTransferItemId;
	}
	/**
	 * @param quantityReceive the quantityReceive to set
	 */
	public void setQuantityReceive(float quantityReceive) {
		this.quantityReceive = quantityReceive;
	}
	/**
	 * @param receivedSerialNumber the receivedSerialNumber to set
	 */
	public void setReceivedSerialNumber(java.util.List<String> receivedSerialNumber) {
		this.receivedSerialNumber = receivedSerialNumber;
	}
	
	
}
