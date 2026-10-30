package com.pratikshat.exicom.model.request;

public class LPTransferItemRequest {
	long lpTransferItemId;
	long lpRequestLineItemId;
	float transferQuantity;
	java.util.Set<String> serialNumbers;
	/**
	 * @return the lpTransferItemId
	 */
	public long getLpTransferItemId() {
		return lpTransferItemId;
	}
	/**
	 * @return the lpRequestLineItemId
	 */
	public long getLpRequestLineItemId() {
		return lpRequestLineItemId;
	}
	/**
	 * @return the transferQuantity
	 */
	public float getTransferQuantity() {
		return transferQuantity;
	}
	/**
	 * @param lpTransferItemId the lpTransferItemId to set
	 */
	public void setLpTransferItemId(long lpTransferItemId) {
		this.lpTransferItemId = lpTransferItemId;
	}
	/**
	 * @param lpRequestLineItemId the lpRequestLineItemId to set
	 */
	public void setLpRequestLineItemId(long lpRequestLineItemId) {
		this.lpRequestLineItemId = lpRequestLineItemId;
	}
	/**
	 * @param transferQuantity the transferQuantity to set
	 */
	public void setTransferQuantity(float transferQuantity) {
		this.transferQuantity = transferQuantity;
	}
	/**
	 * @return the serialNumbers
	 */
	public java.util.Set<String> getSerialNumbers() {
		return serialNumbers;
	}
	/**
	 * @param serialNumbers the serialNumbers to set
	 */
	public void setSerialNumbers(java.util.Set<String> serialNumbers) {
		this.serialNumbers = serialNumbers;
	}
	
}
