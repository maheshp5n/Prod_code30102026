package com.pratikshat.exicom.model.response;

public class LpReturnOrderItemResponse {
	long returnOrderItemId;
	String returnOrderItemManualId;
	float returnOrderQuantity;
	float returnOrderReceivedQuantity;
	long locationItemId;
	String locationItemIdManual;
	long returnOrderId;
	String partCode;
	String partDescription;
	java.util.List<LpReturnLineItemResponse> lineItems;
	
	
	/**
	 * @param returnOrderItemId
	 * @param returnOrderItemManualId
	 * @param returnOrderQuantity
	 * @param returnOrderReceivedQuantity
	 * @param locationItemId
	 * @param locationItemIdManual
	 * @param returnOrderId
	 * @param partCode
	 * @param partDescription
	 */
	public LpReturnOrderItemResponse(long returnOrderItemId, String returnOrderItemManualId, float returnOrderQuantity,
			float returnOrderReceivedQuantity, long locationItemId, String locationItemIdManual, long returnOrderId,
			String partCode, String partDescription) {
		this.returnOrderItemId = returnOrderItemId;
		this.returnOrderItemManualId = returnOrderItemManualId;
		this.returnOrderQuantity = returnOrderQuantity;
		this.returnOrderReceivedQuantity = returnOrderReceivedQuantity;
		this.locationItemId = locationItemId;
		this.locationItemIdManual = locationItemIdManual;
		this.returnOrderId = returnOrderId;
		this.partCode = partCode;
		this.partDescription = partDescription;
	}
	/**
	 * @return the returnOrderItemId
	 */
	public long getReturnOrderItemId() {
		return returnOrderItemId;
	}
	/**
	 * @return the returnOrderItemManualId
	 */
	public String getReturnOrderItemManualId() {
		return returnOrderItemManualId;
	}
	/**
	 * @return the returnOrderQuantity
	 */
	public float getReturnOrderQuantity() {
		return returnOrderQuantity;
	}
	/**
	 * @return the returnOrderReceivedQuantity
	 */
	public float getReturnOrderReceivedQuantity() {
		return returnOrderReceivedQuantity;
	}
	/**
	 * @return the locationItemId
	 */
	public long getLocationItemId() {
		return locationItemId;
	}
	/**
	 * @return the locationItemIdManual
	 */
	public String getLocationItemIdManual() {
		return locationItemIdManual;
	}
	/**
	 * @return the returnOrderId
	 */
	public long getReturnOrderId() {
		return returnOrderId;
	}
	/**
	 * @return the partCode
	 */
	public String getPartCode() {
		return partCode;
	}
	/**
	 * @return the partDescription
	 */
	public String getPartDescription() {
		return partDescription;
	}
	/**
	 * @return the lineItems
	 */
	public java.util.List<LpReturnLineItemResponse> getLineItems() {
		return lineItems;
	}
	/**
	 * @param returnOrderItemId the returnOrderItemId to set
	 */
	public void setReturnOrderItemId(long returnOrderItemId) {
		this.returnOrderItemId = returnOrderItemId;
	}
	/**
	 * @param returnOrderItemManualId the returnOrderItemManualId to set
	 */
	public void setReturnOrderItemManualId(String returnOrderItemManualId) {
		this.returnOrderItemManualId = returnOrderItemManualId;
	}
	/**
	 * @param returnOrderQuantity the returnOrderQuantity to set
	 */
	public void setReturnOrderQuantity(float returnOrderQuantity) {
		this.returnOrderQuantity = returnOrderQuantity;
	}
	/**
	 * @param returnOrderReceivedQuantity the returnOrderReceivedQuantity to set
	 */
	public void setReturnOrderReceivedQuantity(float returnOrderReceivedQuantity) {
		this.returnOrderReceivedQuantity = returnOrderReceivedQuantity;
	}
	/**
	 * @param locationItemId the locationItemId to set
	 */
	public void setLocationItemId(long locationItemId) {
		this.locationItemId = locationItemId;
	}
	/**
	 * @param locationItemIdManual the locationItemIdManual to set
	 */
	public void setLocationItemIdManual(String locationItemIdManual) {
		this.locationItemIdManual = locationItemIdManual;
	}
	/**
	 * @param returnOrderId the returnOrderId to set
	 */
	public void setReturnOrderId(long returnOrderId) {
		this.returnOrderId = returnOrderId;
	}
	/**
	 * @param partCode the partCode to set
	 */
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	/**
	 * @param partDescription the partDescription to set
	 */
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	/**
	 * @param lineItems the lineItems to set
	 */
	public void setLineItems(java.util.List<LpReturnLineItemResponse> lineItems) {
		this.lineItems = lineItems;
	}
	
	
	
}
