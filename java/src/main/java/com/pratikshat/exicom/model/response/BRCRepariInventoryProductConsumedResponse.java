package com.pratikshat.exicom.model.response;

import com.pratikshat.exicom.entity.BRCRepariInventoryProductConsumed;

public class BRCRepariInventoryProductConsumedResponse {

	long miCode;
	String miManualId;
	String productPartCode;
	float quantity;
	java.util.Date materialIssueDate;
	java.util.Date faultySubmissionDate;
	String remarks;
	long consumeid;
	java.util.Date consumedDate;	
	long caseId;
	String caseDetailsManualId;

	public BRCRepariInventoryProductConsumedResponse(BRCRepariInventoryProductConsumed pc) {
		if(pc.getReceiveDetails()!=null) {
			this.miCode = pc.getReceiveDetails().getReceivingId();	
			this.miManualId = pc.getReceiveDetails().getReceivingName();
			if(pc.getReceiveDetails().getCaseDetails()!=null) {
				this.caseId=pc.getReceiveDetails().getCaseDetails().getCaseId();
				this.caseDetailsManualId=pc.getReceiveDetails().getCaseDetails().getManualId();
			}
		}
		if(pc.getProduct()!=null) {
			this.productPartCode = pc.getProduct().getProductPartCode();
		}
		this.quantity = pc.getQuantity();
		this.materialIssueDate = pc.getMaterialIssueDate();
		this.faultySubmissionDate = pc.getFaultySubmissionDate();
		this.remarks = pc.getRemarks();
		this.consumeid = pc.getConsumedId();
		this.consumedDate = pc.getConsumeddate();
		
	}
	
	
	public long getMiCode() {
		return miCode;
	}
	public void setMiCode(long miCode) {
		this.miCode = miCode;
	}
	public String getMiManualId() {
		return miManualId;
	}
	public void setMiManualId(String miManualId) {
		this.miManualId = miManualId;
	}
	public String getProductPartCode() {
		return productPartCode;
	}
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public java.util.Date getMaterialIssueDate() {
		return materialIssueDate;
	}
	public void setMaterialIssueDate(java.util.Date materialIssueDate) {
		this.materialIssueDate = materialIssueDate;
	}
	public java.util.Date getFaultySubmissionDate() {
		return faultySubmissionDate;
	}
	public void setFaultySubmissionDate(java.util.Date faultySubmissionDate) {
		this.faultySubmissionDate = faultySubmissionDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public long getConsumeid() {
		return consumeid;
	}
	public void setConsumeid(long consumeid) {
		this.consumeid = consumeid;
	}	
	public java.util.Date getConsumedDate() {
		return consumedDate;
	}
	public void setConsumedDate(java.util.Date consumedDate) {
		this.consumedDate = consumedDate;
	}


	public long getCaseId() {
		return caseId;
	}


	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}


	public String getCaseDetailsManualId() {
		return caseDetailsManualId;
	}


	public void setCaseDetailsManualId(String caseDetailsManualId) {
		this.caseDetailsManualId = caseDetailsManualId;
	}	
	
	
}
