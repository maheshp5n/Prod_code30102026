package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.Warehouse;

public class WarehouseResponse {
	long warehouseId;
	String warehouseName;
	String warehouseEmail;
	long legalEntityId;
	String legalEntityName;
	String warehousePlant;
	Date createdDateTime;
	String createdByEmailId;
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	Date modifiedDate;
	String modifiedByEmailId;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	
	public WarehouseResponse() {}
	
	public WarehouseResponse(Warehouse warehouse) {
		this.warehouseId=warehouse.getWarehouseId();
		this.warehouseEmail=warehouse.getWarehouseEmail();
		this.warehouseName=warehouse.getWarehouseName();
		this.warehousePlant=warehouse.getWarehousePlant();
		this.createdDateTime=warehouse.getCreatedDateTime();
		this.modifiedDate=warehouse.getModifyiedDate();
		if(warehouse.getCreatedBy()!=null) {
			this.createdByEmailId=warehouse.getCreatedBy().getEmailId();
			this.createdByFirstName=warehouse.getCreatedBy().getFirstName();
			this.createdByMiddleName=warehouse.getCreatedBy().getMiddleName();
			this.createdByLastName=warehouse.getCreatedBy().getLastName();
		}
		if(warehouse.getModifiedBy()!=null) {
			this.modifiedByEmailId=warehouse.getModifiedBy().getEmailId();
			this.modifiedByFirstName=warehouse.getModifiedBy().getFirstName();
			this.modifiedByMiddleName=warehouse.getModifiedBy().getMiddleName();
			this.modifiedByLastName=warehouse.getModifiedBy().getLastName();
		}
		if(warehouse.getLegalEntity()!=null) {
			this.legalEntityId=warehouse.getLegalEntity().getLegalEntityId();
			this.legalEntityName=warehouse.getLegalEntity().getLegalEntityName();
		}
	}
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
	 * @return the legalEntityName
	 */
	public String getLegalEntityName() {
		return legalEntityName;
	}
	/**
	 * @return the warehousePlant
	 */
	public String getWarehousePlant() {
		return warehousePlant;
	}
	/**
	 * @return the createdDateTime
	 */
	public Date getCreatedDateTime() {
		return createdDateTime;
	}
	/**
	 * @return the createdByEmailId
	 */
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	/**
	 * @return the createdByFirstName
	 */
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	/**
	 * @return the createdByLastName
	 */
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @return the modifiedByEmailId
	 */
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}
	/**
	 * @return the modifiedByFirstName
	 */
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	/**
	 * @return the modifiedByLastName
	 */
	public String getModifiedByLastName() {
		return modifiedByLastName;
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
	 * @param legalEntityName the legalEntityName to set
	 */
	public void setLegalEntityName(String legalEntityName) {
		this.legalEntityName = legalEntityName;
	}
	/**
	 * @param warehousePlant the warehousePlant to set
	 */
	public void setWarehousePlant(String warehousePlant) {
		this.warehousePlant = warehousePlant;
	}
	/**
	 * @param createdDateTime the createdDateTime to set
	 */
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	/**
	 * @param createdByEmailId the createdByEmailId to set
	 */
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	/**
	 * @param createdByFirstName the createdByFirstName to set
	 */
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	/**
	 * @param createdByLastName the createdByLastName to set
	 */
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @param modifiedByEmailId the modifiedByEmailId to set
	 */
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	/**
	 * @param modifiedByFirstName the modifiedByFirstName to set
	 */
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	/**
	 * @param modifiedByLastName the modifiedByLastName to set
	 */
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}

	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}

	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}

	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}

	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}
	
}
