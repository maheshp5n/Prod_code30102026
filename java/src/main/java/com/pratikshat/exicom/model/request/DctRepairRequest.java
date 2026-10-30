package com.pratikshat.exicom.model.request;

import java.util.List;

public class DctRepairRequest {
	String partCode;
	String serialNumber;
	long productStatus;
	Long jobCardId;
	List<DctRepairConsumedItemsRequest> items;
	List<Long> problemmaster;
	String remarks;
	//QC
	String heatRunStatus;
	String faultObservedInHeatRun;
	String reworkAfterFail;
	String finalStatus;
	
	String repariEng;
	
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public List<DctRepairConsumedItemsRequest> getItems() {
		return items;
	}
	public void setItems(List<DctRepairConsumedItemsRequest> items) {
		this.items = items;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public long getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(long productStatus) {
		this.productStatus = productStatus;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getJobCardId() {
		return jobCardId;
	}
	public void setJobCardId(Long jobCardId) {
		this.jobCardId = jobCardId;
	}
	public List<Long> getProblemmaster() {
		return problemmaster;
	}
	public void setProblemmaster(List<Long> problemmaster) {
		this.problemmaster = problemmaster;
	}
	public String getHeatRunStatus() {
		return heatRunStatus;
	}
	public void setHeatRunStatus(String heatRunStatus) {
		this.heatRunStatus = heatRunStatus;
	}
	public String getFaultObservedInHeatRun() {
		return faultObservedInHeatRun;
	}
	public void setFaultObservedInHeatRun(String faultObservedInHeatRun) {
		this.faultObservedInHeatRun = faultObservedInHeatRun;
	}
	public String getReworkAfterFail() {
		return reworkAfterFail;
	}
	public void setReworkAfterFail(String reworkAfterFail) {
		this.reworkAfterFail = reworkAfterFail;
	}
	public String getFinalStatus() {
		return finalStatus;
	}
	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}
	public String getRepariEng() {
		return repariEng;
	}
	public void setRepariEng(String repariEng) {
		this.repariEng = repariEng;
	}
	
	
}
