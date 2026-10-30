package com.pratikshat.exicom.model.response;

public class LocationProductItemResponse {
	long locationProductItemId;
	String locationProductItemManualId;
	String productPartCode;
	String productDescription;
	float quantityInOnHand;
	String quantityUnitOfMasur;
	long locationId;
	String locationName;
	long parentLocationProductItemId;
	String parentLocationProductItemManualId;
	float hslQuantity;
	String hslStatus;
	float issuedQunatity;
	float mslQuantity;
	String mslQuantityStatus;
	float okQuantity;
	float faultyQuantity;
	float customerInventory;
	float scrapQty=0;
	float rejectedQty=0;
	float berQty=0;
	float refurbished=0;
	
	String productType;
	
	public LocationProductItemResponse() {
			
	}
	public LocationProductItemResponse(com.pratikshat.exicom.entity.LocationProductItem item) {
		this.locationProductItemId=item.getId();
		this.locationProductItemManualId=item.getLocationProductItemId();
		if(item.getProduct()!=null) {
			this.productPartCode=item.getProduct().getProductPartCode();
			this.productDescription=item.getProduct().getPdoductDescription();
			this.productType=item.getProduct().getProductType();
		}
		this.quantityInOnHand=item.getQuantityInOnHand();
		this.quantityUnitOfMasur=item.getQuantityUnitOfMasur();
		if(item.getLocation()!=null) {
			this.locationId=item.getLocation().getLocationId();
			this.locationName=item.getLocation().getLocationName();
		}
		if(item.getParentLocationProductItem()!=null) {
			this.parentLocationProductItemId=item.getParentLocationProductItem().getId();
			this.parentLocationProductItemManualId=item.getParentLocationProductItem().getLocationProductItemId();
		}
		this.hslQuantity=item.getHslQuantity();
		this.hslStatus=item.getHslStatus();
		this.issuedQunatity=item.getIssuedQunatity();
		this.mslQuantity=item.getMslQuantity();
		this.mslQuantityStatus=item.getMslQuantityStatus();
		this.okQuantity=item.getOkQuantity();
		this.faultyQuantity=item.getFaultyQuantity();
		this.scrapQty=item.getScrapQty();
		this.rejectedQty=item.getRejectedQty();
		this.berQty=item.getBerQty();
		this.refurbished=item.getRefurbished();
		
		if(okQuantity<mslQuantity) {
			mslQuantityStatus="L";
		}
		else {
			mslQuantityStatus="O";
		}
		if(okQuantity<hslQuantity) {
			hslStatus="L";
		}
		else {
			hslStatus="O";
		}
		this.customerInventory=item.getCustomerInventory();
	}
	
	/**
	 * @return the locationProductItemId
	 */
	public long getLocationProductItemId() {
		return locationProductItemId;
	}
	/**
	 * @return the locationProductItemManualId
	 */
	public String getLocationProductItemManualId() {
		return locationProductItemManualId;
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
	 * @return the quantityInOnHand
	 */
	public float getQuantityInOnHand() {
		return quantityInOnHand;
	}
	/**
	 * @return the quantityUnitOfMasur
	 */
	public String getQuantityUnitOfMasur() {
		return quantityUnitOfMasur;
	}
	/**
	 * @return the locationId
	 */
	public long getLocationId() {
		return locationId;
	}
	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}
	/**
	 * @return the parentLocationProductItemId
	 */
	public long getParentLocationProductItemId() {
		return parentLocationProductItemId;
	}
	/**
	 * @return the parentLocationProductItemManualId
	 */
	public String getParentLocationProductItemManualId() {
		return parentLocationProductItemManualId;
	}
	/**
	 * @return the hslQuantity
	 */
	public float getHslQuantity() {
		return hslQuantity;
	}
	/**
	 * @return the hslStatus
	 */
	public String getHslStatus() {
		return hslStatus;
	}
	/**
	 * @return the issuedQunatity
	 */
	public float getIssuedQunatity() {
		return issuedQunatity;
	}
	/**
	 * @return the mslQuantity
	 */
	public float getMslQuantity() {
		return mslQuantity;
	}
	/**
	 * @return the mslQuantityStatus
	 */
	public String getMslQuantityStatus() {
		return mslQuantityStatus;
	}
	/**
	 * @return the okQuantity
	 */
	public float getOkQuantity() {
		return okQuantity;
	}
	/**
	 * @return the faultyQuantity
	 */
	public float getFaultyQuantity() {
		return faultyQuantity;
	}
	/**
	 * @param locationProductItemId the locationProductItemId to set
	 */
	public void setLocationProductItemId(long locationProductItemId) {
		this.locationProductItemId = locationProductItemId;
	}
	/**
	 * @param locationProductItemManualId the locationProductItemManualId to set
	 */
	public void setLocationProductItemManualId(String locationProductItemManualId) {
		this.locationProductItemManualId = locationProductItemManualId;
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
	 * @param quantityInOnHand the quantityInOnHand to set
	 */
	public void setQuantityInOnHand(float quantityInOnHand) {
		this.quantityInOnHand = quantityInOnHand;
	}
	/**
	 * @param quantityUnitOfMasur the quantityUnitOfMasur to set
	 */
	public void setQuantityUnitOfMasur(String quantityUnitOfMasur) {
		this.quantityUnitOfMasur = quantityUnitOfMasur;
	}
	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	/**
	 * @param parentLocationProductItemId the parentLocationProductItemId to set
	 */
	public void setParentLocationProductItemId(long parentLocationProductItemId) {
		this.parentLocationProductItemId = parentLocationProductItemId;
	}
	/**
	 * @param parentLocationProductItemManualId the parentLocationProductItemManualId to set
	 */
	public void setParentLocationProductItemManualId(String parentLocationProductItemManualId) {
		this.parentLocationProductItemManualId = parentLocationProductItemManualId;
	}
	/**
	 * @param hslQuantity the hslQuantity to set
	 */
	public void setHslQuantity(float hslQuantity) {
		this.hslQuantity = hslQuantity;
	}
	/**
	 * @param hslStatus the hslStatus to set
	 */
	public void setHslStatus(String hslStatus) {
		this.hslStatus = hslStatus;
	}
	/**
	 * @param issuedQunatity the issuedQunatity to set
	 */
	public void setIssuedQunatity(float issuedQunatity) {
		this.issuedQunatity = issuedQunatity;
	}
	/**
	 * @param mslQuantity the mslQuantity to set
	 */
	public void setMslQuantity(float mslQuantity) {
		this.mslQuantity = mslQuantity;
	}
	/**
	 * @param mslQuantityStatus the mslQuantityStatus to set
	 */
	public void setMslQuantityStatus(String mslQuantityStatus) {
		this.mslQuantityStatus = mslQuantityStatus;
	}
	/**
	 * @param okQuantity the okQuantity to set
	 */
	public void setOkQuantity(float okQuantity) {
		this.okQuantity = okQuantity;
	}
	/**
	 * @param faultyQuantity the faultyQuantity to set
	 */
	public void setFaultyQuantity(float faultyQuantity) {
		this.faultyQuantity = faultyQuantity;
	}
	/**
	 * @return the customerInventory
	 */
	public float getCustomerInventory() {
		return customerInventory;
	}
	/**
	 * @param customerInventory the customerInventory to set
	 */
	public void setCustomerInventory(float customerInventory) {
		this.customerInventory = customerInventory;
	}
	public float getScrapQty() {
		return scrapQty;
	}
	public void setScrapQty(float scrapQty) {
		this.scrapQty = scrapQty;
	}
	public float getRejectedQty() {
		return rejectedQty;
	}
	public void setRejectedQty(float rejectedQty) {
		this.rejectedQty = rejectedQty;
	}
	public float getBerQty() {
		return berQty;
	}
	public void setBerQty(float berQty) {
		this.berQty = berQty;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public float getRefurbished() {
		return refurbished;
	}
	public void setRefurbished(float refurbished) {
		this.refurbished = refurbished;
	}
	
	
}
