package com.pratikshat.exicom.model.response;

public class WarehouseForList {
	long warehouseId;
	String warehouseName;
	
	public WarehouseForList(long warehouseId,String warehouseName) {
		this.warehouseId=warehouseId;
		this.warehouseName=warehouseName;
	}
	public WarehouseForList() {}
	/**
	 * @return the warehouseId
	 */
	public long getWarehouseId() {
		return warehouseId;
	}
	/**
	 * @return the warehouseName
	 */
	public String getWarehouseName() {
		return warehouseName;
	}
	/**
	 * @param warehouseId the warehouseId to set
	 */
	public void setWarehouseId(long warehouseId) {
		this.warehouseId = warehouseId;
	}
	/**
	 * @param warehouseName the warehouseName to set
	 */
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	
	
}
