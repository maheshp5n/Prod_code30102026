package com.pratikshat.exicom.model.request;

public class BtProductTransferItemRequest {
	long btTransferItemId;
	float transferQuantity;
	/**
	 * @return the btTransferItemId
	 */
	public long getBtTransferItemId() {
		return btTransferItemId;
	}
	/**
	 * @return the transferQuantity
	 */
	public float getTransferQuantity() {
		return transferQuantity;
	}
	/**
	 * @param btTransferItemId the btTransferItemId to set
	 */
	public void setBtTransferItemId(long btTransferItemId) {
		this.btTransferItemId = btTransferItemId;
	}
	/**
	 * @param transferQuantity the transferQuantity to set
	 */
	public void setTransferQuantity(float transferQuantity) {
		this.transferQuantity = transferQuantity;
	}
	
}
