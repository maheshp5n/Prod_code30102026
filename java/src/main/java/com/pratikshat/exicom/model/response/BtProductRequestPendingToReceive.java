package com.pratikshat.exicom.model.response;

public class BtProductRequestPendingToReceive {
	long productRequestId;
	String productRequestManualId;
	long productRequestItemId;
	String productRequestItemManualId;
	long productTransferId;
	String productTransferManualId;
	long productTransferItemId;
	String productTransferItemManualId;
	String productPartCode;
	String productDescription;
	String productType;
	long sourceLocationId;
	String sourceLocationName;
	long destinationLocationId;
	String destinationLocationName;
	float totalQuantityTransfred;
	float totalQuantityReceived;
	java.util.Date transferDate;
	java.util.List<String> serialNumbers;
	public BtProductRequestPendingToReceive() {}
//	public BtProductRequestPendingToReceive(com.pratikshat.exicom.entity.LPTransferItem items) {
//		this.productTransferItemId=items.getLpTransferItemId();
//		this.productTransferItemManualId=items.getLpTransferItemIdManualId();
//		this.totalQuantityTransfred=items.getTransferQuantity();
//		this.totalQuantityReceived=items.getReceivedQuantity();
//		if(items.getLpTranfer()!=null) {
//			this.productTransferId=items.getLpTranfer().getLocationProductTransferId();
//			this.productTransferManualId=items.getLpTranfer().getLocationProductTransferManualId();
//			this.transferDate=items.getCreatedDate();
//			
//			if(items.getLpTranfer().getLocationProductRequest()!=null) {
//				this.productRequestId=items.getLpTranfer().getLocationProductRequest().getLocationProductRequestId();
//				this.productRequestManualId=items.getLpTranfer().getLocationProductRequest().getLocationProductRequestManualId();
//				if(items.getLpTranfer().getLocationProductRequest().getSourceLocation()!=null) {
//					this.sourceLocationId=items.getLpTranfer().getLocationProductRequest().getSourceLocation().getLocationId();
//					this.sourceLocationName=items.getLpTranfer().getLocationProductRequest().getSourceLocation().getLocationName();
//				}
//				if(items.getLpTranfer().getLocationProductRequest().getDestinationLocation()!=null) {
//					this.destinationLocationId=items.getLpTranfer().getLocationProductRequest().getDestinationLocation().getLocationId();
//					this.destinationLocationName=items.getLpTranfer().getLocationProductRequest().getDestinationLocation().getLocationName();
//				}
//			}
//		}
//		if(items.getLpRequestLineItem()!=null) {
//			this.productRequestItemId=items.getLpRequestLineItem().getLocationProductRequestLineItemId();
//			this.productRequestItemManualId=items.getLpRequestLineItem().getLocationProductRequestLineItemManualId();
//		}
//		if(items.getProduct()!=null) {
//			this.productPartCode=items.getProduct().getProductPartCode();
//			this.productDescription=items.getProduct().getPdoductDescription();
//			this.productType=items.getProduct().getProductType();
//		}
//		
//	}
	/**
	 * @return the productRequestId
	 */
	public long getProductRequestId() {
		return productRequestId;
	}
	/**
	 * @return the productRequestManualId
	 */
	public String getProductRequestManualId() {
		return productRequestManualId;
	}
	/**
	 * @return the productRequestItemId
	 */
	public long getProductRequestItemId() {
		return productRequestItemId;
	}
	/**
	 * @return the productRequestItemManualId
	 */
	public String getProductRequestItemManualId() {
		return productRequestItemManualId;
	}
	/**
	 * @return the productTransferId
	 */
	public long getProductTransferId() {
		return productTransferId;
	}
	/**
	 * @return the productTransferManualId
	 */
	public String getProductTransferManualId() {
		return productTransferManualId;
	}
	/**
	 * @return the productTransferItemId
	 */
	public long getProductTransferItemId() {
		return productTransferItemId;
	}
	/**
	 * @return the productTransferItemManualId
	 */
	public String getProductTransferItemManualId() {
		return productTransferItemManualId;
	}
	/**
	 * @return the productPartCode
	 */
	public String getProductPartCode() {
		return productPartCode;
	}
	/**
	 * @return the productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}
	/**
	 * @return the sourceLocationId
	 */
	public long getSourceLocationId() {
		return sourceLocationId;
	}
	/**
	 * @return the sourceLocationName
	 */
	public String getSourceLocationName() {
		return sourceLocationName;
	}
	/**
	 * @return the destinationLocationId
	 */
	public long getDestinationLocationId() {
		return destinationLocationId;
	}
	/**
	 * @return the destinationLocationName
	 */
	public String getDestinationLocationName() {
		return destinationLocationName;
	}
	/**
	 * @return the totalQuantityTransfred
	 */
	public float getTotalQuantityTransfred() {
		return totalQuantityTransfred;
	}
	/**
	 * @return the totalQuantityReceived
	 */
	public float getTotalQuantityReceived() {
		return totalQuantityReceived;
	}
	/**
	 * @return the transferDate
	 */
	public java.util.Date getTransferDate() {
		return transferDate;
	}
	/**
	 * @param productRequestId the productRequestId to set
	 */
	public void setProductRequestId(long productRequestId) {
		this.productRequestId = productRequestId;
	}
	/**
	 * @param productRequestManualId the productRequestManualId to set
	 */
	public void setProductRequestManualId(String productRequestManualId) {
		this.productRequestManualId = productRequestManualId;
	}
	/**
	 * @param productRequestItemId the productRequestItemId to set
	 */
	public void setProductRequestItemId(long productRequestItemId) {
		this.productRequestItemId = productRequestItemId;
	}
	/**
	 * @param productRequestItemManualId the productRequestItemManualId to set
	 */
	public void setProductRequestItemManualId(String productRequestItemManualId) {
		this.productRequestItemManualId = productRequestItemManualId;
	}
	/**
	 * @param productTransferId the productTransferId to set
	 */
	public void setProductTransferId(long productTransferId) {
		this.productTransferId = productTransferId;
	}
	/**
	 * @param productTransferManualId the productTransferManualId to set
	 */
	public void setProductTransferManualId(String productTransferManualId) {
		this.productTransferManualId = productTransferManualId;
	}
	/**
	 * @param productTransferItemId the productTransferItemId to set
	 */
	public void setProductTransferItemId(long productTransferItemId) {
		this.productTransferItemId = productTransferItemId;
	}
	/**
	 * @param productTransferItemManualId the productTransferItemManualId to set
	 */
	public void setProductTransferItemManualId(String productTransferItemManualId) {
		this.productTransferItemManualId = productTransferItemManualId;
	}
	/**
	 * @param productPartCode the productPartCode to set
	 */
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	/**
	 * @param productDescription the productDescription to set
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	/**
	 * @param sourceLocationId the sourceLocationId to set
	 */
	public void setSourceLocationId(long sourceLocationId) {
		this.sourceLocationId = sourceLocationId;
	}
	/**
	 * @param sourceLocationName the sourceLocationName to set
	 */
	public void setSourceLocationName(String sourceLocationName) {
		this.sourceLocationName = sourceLocationName;
	}
	/**
	 * @param destinationLocationId the destinationLocationId to set
	 */
	public void setDestinationLocationId(long destinationLocationId) {
		this.destinationLocationId = destinationLocationId;
	}
	/**
	 * @param destinationLocationName the destinationLocationName to set
	 */
	public void setDestinationLocationName(String destinationLocationName) {
		this.destinationLocationName = destinationLocationName;
	}
	/**
	 * @param totalQuantityTransfred the totalQuantityTransfred to set
	 */
	public void setTotalQuantityTransfred(float totalQuantityTransfred) {
		this.totalQuantityTransfred = totalQuantityTransfred;
	}
	/**
	 * @param totalQuantityReceived the totalQuantityReceived to set
	 */
	public void setTotalQuantityReceived(float totalQuantityReceived) {
		this.totalQuantityReceived = totalQuantityReceived;
	}
	/**
	 * @param transferDate the transferDate to set
	 */
	public void setTransferDate(java.util.Date transferDate) {
		this.transferDate = transferDate;
	}
	/**
	 * @return the serialNumbers
	 */
	public java.util.List<String> getSerialNumbers() {
		return serialNumbers;
	}
	/**
	 * @param serialNumbers the serialNumbers to set
	 */
	public void setSerialNumbers(java.util.List<String> serialNumbers) {
		this.serialNumbers = serialNumbers;
	}
	
	
}
