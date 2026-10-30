package com.pratikshat.exicom.model.request;

public class BRCRepairInventoryDispatchRequest {
	long dispatchId;
	String podNumber;
	String dispatchMode;
	String dispatcherName;
	String sapDeleveryChallanNumber;
	java.util.Date dispatchDate;
	long repariItemId;
	String dispatchShipmentNo;
	String dispatchRemark;	
	/**
	 * @return the dispatchId
	 */
	public long getDispatchId() {
		return dispatchId;
	}
	/**
	 * @return the podNumber
	 */
	public String getPodNumber() {
		return podNumber;
	}
	/**
	 * @return the dispatchMode
	 */
	public String getDispatchMode() {
		return dispatchMode;
	}
	/**
	 * @return the dispatcherName
	 */
	public String getDispatcherName() {
		return dispatcherName;
	}
	/**
	 * @return the sapDeleveryChallanNumber
	 */
	public String getSapDeleveryChallanNumber() {
		return sapDeleveryChallanNumber;
	}
	/**
	 * @return the dispatchDate
	 */
	public java.util.Date getDispatchDate() {
		return dispatchDate;
	}
	
	/**
	 * @param dispatchId the dispatchId to set
	 */
	public void setDispatchId(long dispatchId) {
		this.dispatchId = dispatchId;
	}
	/**
	 * @param podNumber the podNumber to set
	 */
	public void setPodNumber(String podNumber) {
		this.podNumber = podNumber;
	}
	/**
	 * @param dispatchMode the dispatchMode to set
	 */
	public void setDispatchMode(String dispatchMode) {
		this.dispatchMode = dispatchMode;
	}
	/**
	 * @param dispatcherName the dispatcherName to set
	 */
	public void setDispatcherName(String dispatcherName) {
		this.dispatcherName = dispatcherName;
	}
	/**
	 * @param sapDeleveryChallanNumber the sapDeleveryChallanNumber to set
	 */
	public void setSapDeleveryChallanNumber(String sapDeleveryChallanNumber) {
		this.sapDeleveryChallanNumber = sapDeleveryChallanNumber;
	}
	/**
	 * @param dispatchDate the dispatchDate to set
	 */
	public void setDispatchDate(java.util.Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	/**
	 * @param receiveItemId the receiveItemId to set
	 */
	/**
	 * @return the repariItemId
	 */
	public long getRepariItemId() {
		return repariItemId;
	}
	/**
	 * @param repariItemId the repariItemId to set
	 */
	public void setRepariItemId(long repariItemId) {
		this.repariItemId = repariItemId;
	}
	public String getDispatchShipmentNo() {
		return dispatchShipmentNo;
	}
	public void setDispatchShipmentNo(String dispatchShipmentNo) {
		this.dispatchShipmentNo = dispatchShipmentNo;
	}
	public String getDispatchRemark() {
		return dispatchRemark;
	}
	public void setDispatchRemark(String dispatchRemark) {
		this.dispatchRemark = dispatchRemark;
	}
	
	
	
}
