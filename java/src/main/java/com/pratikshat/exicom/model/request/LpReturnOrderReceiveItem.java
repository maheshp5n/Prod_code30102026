package com.pratikshat.exicom.model.request;

public class LpReturnOrderReceiveItem {
	long lpReturnOrderReceiveItemId;
	long lpReturnOrderReceiveId;
	long lpReturnOrderItemId;
	String productPartCode;
	java.util.List<String> serialNumber;
	/**
	 * @return the lpReturnOrderReceiveItemId
	 */
	public long getLpReturnOrderReceiveItemId() {
		return lpReturnOrderReceiveItemId;
	}
	/**
	 * @return the lpReturnOrderReceiveId
	 */
	public long getLpReturnOrderReceiveId() {
		return lpReturnOrderReceiveId;
	}
	/**
	 * @return the lpReturnOrderItemId
	 */
	public long getLpReturnOrderItemId() {
		return lpReturnOrderItemId;
	}
	/**
	 * @return the productPartCode
	 */
	public String getProductPartCode() {
		return productPartCode;
	}
	/**
	 * @return the serialNumber
	 */
	public java.util.List<String> getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param lpReturnOrderReceiveItemId the lpReturnOrderReceiveItemId to set
	 */
	public void setLpReturnOrderReceiveItemId(long lpReturnOrderReceiveItemId) {
		this.lpReturnOrderReceiveItemId = lpReturnOrderReceiveItemId;
	}
	/**
	 * @param lpReturnOrderReceiveId the lpReturnOrderReceiveId to set
	 */
	public void setLpReturnOrderReceiveId(long lpReturnOrderReceiveId) {
		this.lpReturnOrderReceiveId = lpReturnOrderReceiveId;
	}
	/**
	 * @param lpReturnOrderItemId the lpReturnOrderItemId to set
	 */
	public void setLpReturnOrderItemId(long lpReturnOrderItemId) {
		this.lpReturnOrderItemId = lpReturnOrderItemId;
	}
	/**
	 * @param productPartCode the productPartCode to set
	 */
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(java.util.List<String> serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	
}
