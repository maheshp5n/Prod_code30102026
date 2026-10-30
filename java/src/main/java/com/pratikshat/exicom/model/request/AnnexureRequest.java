package com.pratikshat.exicom.model.request;

import java.util.Date;

public class AnnexureRequest {

	long id;

	String invoiceNumber;
	
	Date invoiceDate;
	Date submittedDate;
	Date approvedByCommercialDate;
	Date approvedByCircleDate;
	boolean isInvoiceReceived;
	int approvalStatusId;
	
	int businessDomainId;
	
	String poNumber;
	Date poDate;
	
	String circle;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public Date getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}
	public Date getApprovedByCommercialDate() {
		return approvedByCommercialDate;
	}
	public void setApprovedByCommercialDate(Date approvedByCommercialDate) {
		this.approvedByCommercialDate = approvedByCommercialDate;
	}
	public Date getApprovedByCircleDate() {
		return approvedByCircleDate;
	}
	public void setApprovedByCircleDate(Date approvedByCircleDate) {
		this.approvedByCircleDate = approvedByCircleDate;
	}
	public boolean isInvoiceReceived() {
		return isInvoiceReceived;
	}
	public void setInvoiceReceived(boolean isInvoiceReceived) {
		this.isInvoiceReceived = isInvoiceReceived;
	}
	public int getApprovalStatusId() {
		return approvalStatusId;
	}
	public void setApprovalStatusId(int approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}
	public int getBusinessDomainId() {
		return businessDomainId;
	}
	public void setBusinessDomainId(int businessDomainId) {
		this.businessDomainId = businessDomainId;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public Date getPoDate() {
		return poDate;
	}
	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}
	
	
}
