package com.pratikshat.exicom.model.response;

public class OkItemResponse {
	float okQuantityOnHand;
	java.util.List<String>serialNumbers;
	public OkItemResponse(float okQuantityOnHand,java.util.List<String>serialNumbers) {
		this.okQuantityOnHand=okQuantityOnHand;
		this.serialNumbers=serialNumbers;
	}
	public OkItemResponse() {}
	/**
	 * @return the okQuantityOnHand
	 */
	public float getOkQuantityOnHand() {
		return okQuantityOnHand;
	}
	/**
	 * @return the serialNumbers
	 */
	public java.util.List<String> getSerialNumbers() {
		return serialNumbers;
	}
	/**
	 * @param okQuantityOnHand the okQuantityOnHand to set
	 */
	public void setOkQuantityOnHand(float okQuantityOnHand) {
		this.okQuantityOnHand = okQuantityOnHand;
	}
	/**
	 * @param serialNumbers the serialNumbers to set
	 */
	public void setSerialNumbers(java.util.List<String> serialNumbers) {
		this.serialNumbers = serialNumbers;
	}
	
}
