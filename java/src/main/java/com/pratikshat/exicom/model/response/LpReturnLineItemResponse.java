package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.EmployeeDetails;

public class LpReturnLineItemResponse {
	long returnLineItemId;
	String returnLineItemIdManual;
	long returnItemId;
	String returnItemIdManual;
	String serialNumber;
	boolean received;
	
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	String createdByEmailId;
	Date createdDate;
	
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	String modifiedByEmailId;
	Date modifiedDate;
	
	public LpReturnLineItemResponse() {}
	
	/**
	 * @param returnLineItemId
	 * @param returnLineItemIdManual
	 * @param returnItemId
	 * @param returnItemIdManual
	 * @param serialNumber
	 * @param received
	 */
	public LpReturnLineItemResponse(long returnLineItemId, String returnLineItemIdManual, long returnItemId,
			String returnItemIdManual, String serialNumber, boolean received) {
		this.returnLineItemId = returnLineItemId;
		this.returnLineItemIdManual = returnLineItemIdManual;
		this.returnItemId = returnItemId;
		this.returnItemIdManual = returnItemIdManual;
		this.serialNumber = serialNumber;
		this.received = received;
	}
	
	
	public LpReturnLineItemResponse(
			long returnLineItemId, 
			String returnLineItemIdManual,
			long returnItemId,
			String returnItemIdManual,
			String serialNumber,
			boolean received,
			String createdByFirstName,
			String createdByMiddleName,
			String createdByLastName,
			String createdByEmailId,
			Date createdDate,
			String modifiedByFirstName,
			String modifiedByMiddleName,
			String modifiedByLastName,
			String modifiedByEmailId,
			Date modifiedDate
			) {
		this.returnLineItemId = returnLineItemId;
		this.returnLineItemIdManual = returnLineItemIdManual;
		this.returnItemId = returnItemId;
		this.returnItemIdManual = returnItemIdManual;
		this.serialNumber = serialNumber;
		this.received = received;
		
		
			this.createdByFirstName = createdByFirstName;//createdBy.getFirstName();
			this.createdByMiddleName =createdByMiddleName; //createdBy.getMiddleName();
			this.createdByLastName =createdByLastName; //createdBy.getLastName();
			this.createdByEmailId = createdByEmailId;//createdBy.getEmailId();
			this.createdDate = createdDate;
		
		
		
		
			this.modifiedByFirstName = modifiedByFirstName;//modifiedBy.getFirstName();
			this.modifiedByMiddleName = modifiedByMiddleName;//modifiedBy.getMiddleName();
			this.modifiedByLastName = modifiedByLastName;//modifiedBy.getLastName();
			this.modifiedByEmailId =modifiedByEmailId; //modifiedBy.getEmailId();
			this.modifiedDate = modifiedDate;
		
	}
	
	/**
	 * @return the returnLineItemId
	 */
	public long getReturnLineItemId() {
		return returnLineItemId;
	}
	/**
	 * @return the returnLineItemIdManual
	 */
	public String getReturnLineItemIdManual() {
		return returnLineItemIdManual;
	}
	/**
	 * @return the returnItemId
	 */
	public long getReturnItemId() {
		return returnItemId;
	}
	/**
	 * @return the returnItemIdManual
	 */
	public String getReturnItemIdManual() {
		return returnItemIdManual;
	}
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @return the received
	 */
	public boolean isReceived() {
		return received;
	}
	/**
	 * @param returnLineItemId the returnLineItemId to set
	 */
	public void setReturnLineItemId(long returnLineItemId) {
		this.returnLineItemId = returnLineItemId;
	}
	/**
	 * @param returnLineItemIdManual the returnLineItemIdManual to set
	 */
	public void setReturnLineItemIdManual(String returnLineItemIdManual) {
		this.returnLineItemIdManual = returnLineItemIdManual;
	}
	/**
	 * @param returnItemId the returnItemId to set
	 */
	public void setReturnItemId(long returnItemId) {
		this.returnItemId = returnItemId;
	}
	/**
	 * @param returnItemIdManual the returnItemIdManual to set
	 */
	public void setReturnItemIdManual(String returnItemIdManual) {
		this.returnItemIdManual = returnItemIdManual;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @param received the received to set
	 */
	public void setReceived(boolean received) {
		this.received = received;
	}

	public String getCreatedByFirstName() {
		return createdByFirstName;
	}

	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}

	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}

	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}

	public String getCreatedByLastName() {
		return createdByLastName;
	}

	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}

	public String getCreatedByEmailId() {
		return createdByEmailId;
	}

	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}

	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}

	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}

	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}

	public String getModifiedByLastName() {
		return modifiedByLastName;
	}

	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}

	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}

	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
}
