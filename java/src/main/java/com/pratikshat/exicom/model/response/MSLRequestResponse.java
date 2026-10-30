package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.MSLRequest;

public class MSLRequestResponse {
	
	long mslId;
	String manualId;
	
	String mslRequestOwnerFirtName;
	String mslRequestOwnerMiddleName;
	String mslRequestOwnerlastName;
	String mslRequestOwnerEmailId;
	
	String locationName;
	long locationId;
	
	long statusId;
	String statusName;
	
	String createdByFirstName;
	String createdByMiddleName;
	String createByLastName;
	String createdByEmailId;
	Date createdDate;
	
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	String modifiedByEmailId;
	Date modifiedDate;
	
	public MSLRequestResponse() {}
	
	public MSLRequestResponse(MSLRequest mslRequest) {
		this.mslId = mslRequest.getId();
		this.manualId = mslRequest.getManualId();		
		if(mslRequest.getOwner()!=null) {
			this.mslRequestOwnerFirtName = mslRequest.getOwner().getFirstName();
			this.mslRequestOwnerMiddleName = mslRequest.getOwner().getMiddleName();
			this.mslRequestOwnerlastName = mslRequest.getOwner().getLastName();
			this.mslRequestOwnerEmailId = mslRequest.getOwner().getEmailId();
		}
		if(mslRequest.getWarehouse()!=null) {
			this.locationId = mslRequest.getWarehouse().getLocationId();
			this.locationName = mslRequest.getWarehouse().getLocationName();
		}
		
		if(mslRequest.getMslRequestStatus()!=null) {
			this.statusId = mslRequest.getMslRequestStatus().getStatusId();
			this.statusName = mslRequest.getMslRequestStatus().getStatusName();
		}
		if(mslRequest.getCreatedBy()!=null) {
			this.createdByFirstName = mslRequest.getCreatedBy().getFirstName();
			this.createdByMiddleName = mslRequest.getCreatedBy().getMiddleName();
			this.createByLastName = mslRequest.getCreatedBy().getLastName();
			this.createdByEmailId = mslRequest.getCreatedBy().getEmailId();
			this.createdDate = mslRequest.getCreatedDate();
		}
		if(mslRequest.getModifiedBy()!=null) {
			this.modifiedByFirstName = mslRequest.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = mslRequest.getModifiedBy().getMiddleName();
			this.modifiedByLastName = mslRequest.getModifiedBy().getLastName();
			this.modifiedByEmailId = mslRequest.getModifiedBy().getEmailId();
			this.modifiedDate = mslRequest.getModifiedDate();
		}
	}
	public long getMslId() {
		return mslId;
	}
	public void setMslId(long mslId) {
		this.mslId = mslId;
	}
	public String getManualId() {
		return manualId;
	}
	public void setManualId(String manualId) {
		this.manualId = manualId;
	}
	public String getMslRequestOwnerFirtName() {
		return mslRequestOwnerFirtName;
	}
	public void setMslRequestOwnerFirtName(String mslRequestOwnerFirtName) {
		this.mslRequestOwnerFirtName = mslRequestOwnerFirtName;
	}
	public String getMslRequestOwnerlastName() {
		return mslRequestOwnerlastName;
	}
	public void setMslRequestOwnerlastName(String mslRequestOwnerlastName) {
		this.mslRequestOwnerlastName = mslRequestOwnerlastName;
	}
	public String getMslRequestOwnerEmailId() {
		return mslRequestOwnerEmailId;
	}
	public void setMslRequestOwnerEmailId(String mslRequestOwnerEmailId) {
		this.mslRequestOwnerEmailId = mslRequestOwnerEmailId;
	}
	
	public long getStatusId() {
		return statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	public String getCreateByLastName() {
		return createByLastName;
	}
	public void setCreateByLastName(String createByLastName) {
		this.createByLastName = createByLastName;
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


	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}


	/**
	 * @return the locationId
	 */
	public long getLocationId() {
		return locationId;
	}


	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}


	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public String getMslRequestOwnerMiddleName() {
		return mslRequestOwnerMiddleName;
	}

	public void setMslRequestOwnerMiddleName(String mslRequestOwnerMiddleName) {
		this.mslRequestOwnerMiddleName = mslRequestOwnerMiddleName;
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
