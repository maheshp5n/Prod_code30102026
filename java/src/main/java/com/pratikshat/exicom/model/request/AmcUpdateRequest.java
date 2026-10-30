package com.pratikshat.exicom.model.request;

import java.util.Date;

public class AmcUpdateRequest {
	String assetSerialNo;
	String assetPartCode;
	String amcPoNo;
	Date amcPoDate; 
	Date amcStartDate;
	String amcPeriod;
	
	
	public String getAssetSerialNo() {
		return assetSerialNo;
	}
	public void setAssetSerialNo(String assetSerialNo) {
		this.assetSerialNo = assetSerialNo;
	}
	public String getAssetPartCode() {
		return assetPartCode;
	}
	public void setAssetPartCode(String assetPartCode) {
		this.assetPartCode = assetPartCode;
	}
	public String getAmcPoNo() {
		return amcPoNo;
	}
	public void setAmcPoNo(String amcPoNo) {
		this.amcPoNo = amcPoNo;
	}
	
	public Date getAmcPoDate() {
		return amcPoDate;
	}
	public void setAmcPoDate(Date amcPoDate) {
		this.amcPoDate = amcPoDate;
	}
	public Date getAmcStartDate() {
		return amcStartDate;
	}
	public void setAmcStartDate(Date amcStartDate) {
		this.amcStartDate = amcStartDate;
	}
	public String getAmcPeriod() {
		return amcPeriod;
	}
	public void setAmcPeriod(String amcPeriod) {
		this.amcPeriod = amcPeriod;
	}
	
	
	
	
	
	
}
