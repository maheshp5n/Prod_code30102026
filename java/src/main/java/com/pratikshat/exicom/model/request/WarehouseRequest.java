package com.pratikshat.exicom.model.request;

public class WarehouseRequest {
	long warehouseId;
	String warehouseName;
	String warehouseEmail;
	long legalEntityId;
	String warehousePlant;
	
	
	/**
	 * @return the warehouseId
	 */
	public long getWarehouseId() {
		return warehouseId;
	}
	/**
	 * @param warehouseId the warehouseId to set
	 */
	public void setWarehouseId(long warehouseId) {
		this.warehouseId = warehouseId;
	}
	/**
	 * @return the warehouseName
	 */
	public String getWarehouseName() {
		return warehouseName;
	}
	/**
	 * @return the warehouseEmail
	 */
	public String getWarehouseEmail() {
		return warehouseEmail;
	}
	/**
	 * @return the legalEntityId
	 */
	public long getLegalEntityId() {
		return legalEntityId;
	}
	/**
	 * @return the warehousePlant
	 */
	public String getWarehousePlant() {
		return warehousePlant;
	}
	/**
	 * @param warehouseName the warehouseName to set
	 */
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	/**
	 * @param warehouseEmail the warehouseEmail to set
	 */
	public void setWarehouseEmail(String warehouseEmail) {
		this.warehouseEmail = warehouseEmail;
	}
	/**
	 * @param legalEntityId the legalEntityId to set
	 */
	public void setLegalEntityId(long legalEntityId) {
		this.legalEntityId = legalEntityId;
	}
	/**
	 * @param warehousePlant the warehousePlant to set
	 */
	public void setWarehousePlant(String warehousePlant) {
		this.warehousePlant = warehousePlant;
	}
	
}
