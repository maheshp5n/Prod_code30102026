package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Cacheable
@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class Warehouse implements java.io.Serializable,OldPresist<Warehouse>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long warehouseId;
	String warehouseName;
	String warehouseEmail;
	@ManyToOne(fetch=FetchType.LAZY)
	LegalEntity legalEntity;
	String warehousePlant;
	Date createdDateTime;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	Date modifyiedDate;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	boolean synced;
	/**
	 * @return the synced
	 */
	public boolean isSynced() {
		return synced;
	}
	/**
	 * @param synced the synced to set
	 */
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	public Warehouse() {
		
	}
	public Warehouse(long warehouseId) {
		this.warehouseId=warehouseId;
	}
	
	
	/**
	 * @return the createdDateTime
	 */
	public Date getCreatedDateTime() {
		return createdDateTime;
	}
	/**
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	/**
	 * @return the modifyiedDate
	 */
	public Date getModifyiedDate() {
		return modifyiedDate;
	}
	/**
	 * @return the modifiedBy
	 */
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param createdDateTime the createdDateTime to set
	 */
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @param modifyiedDate the modifyiedDate to set
	 */
	public void setModifyiedDate(Date modifyiedDate) {
		this.modifyiedDate = modifyiedDate;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
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
	 * @return the legalEntity
	 */
	public LegalEntity getLegalEntity() {
		return legalEntity;
	}
	/**
	 * @return the warehousePlant
	 */
	public String getWarehousePlant() {
		return warehousePlant;
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
	 * @param legalEntity the legalEntity to set
	 */
	public void setLegalEntity(LegalEntity legalEntity) {
		this.legalEntity = legalEntity;
	}
	/**
	 * @param warehousePlant the warehousePlant to set
	 */
	public void setWarehousePlant(String warehousePlant) {
		this.warehousePlant = warehousePlant;
	}
	

	@javax.persistence.Transient transient Warehouse old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(Warehouse)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public Warehouse gOld() {
		return old;
	}
	public String toString() {
			return ""+warehouseId;
	}
}
