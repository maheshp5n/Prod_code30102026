package com.pratikshat.exicom.model.request;

import java.util.Date;

public class AnnexureLineItemRequest {

	long id;
	
	long annexureId;
	
	long serviceAppointmentId;
	
	int glCodeId;
	
	Date workCompletionDate;
	
	String activity;
	
	String workStatus;
	
	Date exicomInvoiceDate;
	String exicomInvoiceNumber;
	
	String remark;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAnnexureId() {
		return annexureId;
	}

	public void setAnnexureId(long annexureId) {
		this.annexureId = annexureId;
	}

	public long getServiceAppointmentId() {
		return serviceAppointmentId;
	}

	public void setServiceAppointmentId(long serviceAppointmentId) {
		this.serviceAppointmentId = serviceAppointmentId;
	}

	public int getGlCodeId() {
		return glCodeId;
	}

	public void setGlCodeId(int glCodeId) {
		this.glCodeId = glCodeId;
	}

	public Date getWorkCompletionDate() {
		return workCompletionDate;
	}

	public void setWorkCompletionDate(Date workCompletionDate) {
		this.workCompletionDate = workCompletionDate;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public Date getExicomInvoiceDate() {
		return exicomInvoiceDate;
	}

	public void setExicomInvoiceDate(Date exicomInvoiceDate) {
		this.exicomInvoiceDate = exicomInvoiceDate;
	}

	public String getExicomInvoiceNumber() {
		return exicomInvoiceNumber;
	}

	public void setExicomInvoiceNumber(String exicomInvoiceNumber) {
		this.exicomInvoiceNumber = exicomInvoiceNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
