package com.pratikshat.exicom.model.request;

public class LpReturnOrderReceiveRequest {
	long lpReturnOderReceiveId;
	long lpReturnOrderId;
	float quantityReceived;
	java.util.List<LpReturnOrderReceiveItem> items;
	/**
	 * @return the lpReturnOderReceiveId
	 */
	public long getLpReturnOderReceiveId() {
		return lpReturnOderReceiveId;
	}
	/**
	 * @return the lpReturnOrderId
	 */
	public long getLpReturnOrderId() {
		return lpReturnOrderId;
	}
	/**
	 * @return the quantityReceived
	 */
	public float getQuantityReceived() {
		return quantityReceived;
	}
	/**
	 * @return the items
	 */
	public java.util.List<LpReturnOrderReceiveItem> getItems() {
		return items;
	}
	/**
	 * @param lpReturnOderReceiveId the lpReturnOderReceiveId to set
	 */
	public void setLpReturnOderReceiveId(long lpReturnOderReceiveId) {
		this.lpReturnOderReceiveId = lpReturnOderReceiveId;
	}
	/**
	 * @param lpReturnOrderId the lpReturnOrderId to set
	 */
	public void setLpReturnOrderId(long lpReturnOrderId) {
		this.lpReturnOrderId = lpReturnOrderId;
	}
	/**
	 * @param quantityReceived the quantityReceived to set
	 */
	public void setQuantityReceived(float quantityReceived) {
		this.quantityReceived = quantityReceived;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(java.util.List<LpReturnOrderReceiveItem> items) {
		this.items = items;
	}
	
	
}
