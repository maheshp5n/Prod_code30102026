package com.pratikshat.exicom.model.request;

import java.util.Date;

public class WorkOrderRequest {

	private String workOrderNumber;
	private Date workOrderDate;
	private long quoteId;
	
	public String getWorkOrderNumber() {
		return workOrderNumber;
	}
	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}
	public Date getWorkOrderDate() {
		return workOrderDate;
	}
	public void setWorkOrderDate(Date workOrderDate) {
		this.workOrderDate = workOrderDate;
	}
	public long getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(long quoteId) {
		this.quoteId = quoteId;
	}
	
	
}
