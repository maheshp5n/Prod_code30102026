package com.pratikshat.exicom.model.request;

import java.util.Date;

public class CustomerInventoryOutWardRequest {
	String dispatchChallanNumber;
	Date dispatchDate;
	String dispatchDocketNumber;
	String productSerialNumber;
	String productPartcode;
	Long id;
	String lotNo;
	String remark;
	
	public String getDispatchChallanNumber() {
		return dispatchChallanNumber;
	}
	public void setDispatchChallanNumber(String dispatchChallanNumber) {
		this.dispatchChallanNumber = dispatchChallanNumber;
	}
	public Date getDispatchDate() {
		return dispatchDate;
	}
	public void setDispatchDate(Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	public String getDispatchDocketNumber() {
		return dispatchDocketNumber;
	}
	public void setDispatchDocketNumber(String dispatchDocketNumber) {
		this.dispatchDocketNumber = dispatchDocketNumber;
	}
	public String getProductSerialNumber() {
		return productSerialNumber;
	}
	public void setProductSerialNumber(String productSerialNumber) {
		this.productSerialNumber = productSerialNumber;
	}
	public String getProductPartcode() {
		return productPartcode;
	}
	public void setProductPartcode(String productPartcode) {
		this.productPartcode = productPartcode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
