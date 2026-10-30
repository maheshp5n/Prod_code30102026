package com.pratikshat.exicom.model.request;

public class MSLItemRequestRequest {
	long mslItmeRequestId;
	long mslRequestId;
	String productPartCode;
	float mslQty;
	float hslQty;
	String remarks;
	/**
	 * @return the mslRequestId
	 */
	public long getMslRequestId() {
		return mslRequestId;
	}
	
	/**
	 * @return the mslQty
	 */
	public float getMslQty() {
		return mslQty;
	}
	/**
	 * @return the hslQty
	 */
	public float getHslQty() {
		return hslQty;
	}
	/**
	 * @param mslRequestId the mslRequestId to set
	 */
	public void setMslRequestId(long mslRequestId) {
		this.mslRequestId = mslRequestId;
	}
	
	/**
	 * @param mslQty the mslQty to set
	 */
	public void setMslQty(float mslQty) {
		this.mslQty = mslQty;
	}
	/**
	 * @param hslQty the hslQty to set
	 */
	public void setHslQty(float hslQty) {
		this.hslQty = hslQty;
	}
	/**
	 * @return the mslItmeRequestId
	 */
	public long getMslItmeRequestId() {
		return mslItmeRequestId;
	}
	/**
	 * @param mslItmeRequestId the mslItmeRequestId to set
	 */
	public void setMslItmeRequestId(long mslItmeRequestId) {
		this.mslItmeRequestId = mslItmeRequestId;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the productPartCode
	 */
	public String getProductPartCode() {
		return productPartCode;
	}

	/**
	 * @param productPartCode the productPartCode to set
	 */
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	
}
