package com.pratikshat.exicom.model.request;

public class ServiceOrderReturnRequestItem {
	Long itemReceiveId;
	long finalStatusId;
	
	public Long getItemReceiveId() {
		return itemReceiveId;
	}
	public void setItemReceiveId(Long itemReceiveId) {
		this.itemReceiveId = itemReceiveId;
	}
	public long getFinalStatusId() {
		return finalStatusId;
	}
	public void setFinalStatusId(long finalStatusId) {
		this.finalStatusId = finalStatusId;
	}
	
	
}
