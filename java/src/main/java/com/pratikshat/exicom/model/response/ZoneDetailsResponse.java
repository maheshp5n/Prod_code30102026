package com.pratikshat.exicom.model.response;

import java.util.Date;
import com.pratikshat.exicom.entity.ZoneDetails;

public class ZoneDetailsResponse {
	String zoneName;
	String createById;
	String createByFirstName;
	String createByMiddleName;
	String createByLastName;
	Date createdDate;
	String modifiedById;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	Date modifyedDate;

	public ZoneDetailsResponse() {
	}

	public ZoneDetailsResponse(ZoneDetails zoneDetails) {
		this.zoneName = zoneDetails.getZoneName();
		if (zoneDetails.getCreatedBy() != null) {
			this.createById = zoneDetails.getCreatedBy().getEmailId();
			this.createByFirstName = zoneDetails.getCreatedBy().getFirstName();
			this.createByMiddleName= zoneDetails.getCreatedBy().getMiddleName();
			this.createByLastName = zoneDetails.getCreatedBy().getLastName();
		}
		if (zoneDetails.getModifyedBy() != null) {
			this.modifiedById = zoneDetails.getModifyedBy().getEmailId();
			this.modifiedByFirstName = zoneDetails.getModifyedBy().getFirstName();
			this.modifiedByMiddleName = zoneDetails.getModifyedBy().getMiddleName();
			this.modifiedByLastName = zoneDetails.getModifyedBy().getLastName();
		}
		this.createdDate = zoneDetails.getCreatedDate();
		this.modifyedDate = zoneDetails.getModifyedDate();
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}

	public String getCreateByFirstName() {
		return createByFirstName;
	}

	public void setCreateByFirstName(String createByFirstName) {
		this.createByFirstName = createByFirstName;
	}

	public String getCreateByLastName() {
		return createByLastName;
	}

	public void setCreateByLastName(String createByLastName) {
		this.createByLastName = createByLastName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
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

	public Date getModifyedDate() {
		return modifyedDate;
	}

	public void setModifyedDate(Date modifyedDate) {
		this.modifyedDate = modifyedDate;
	}

	public String getCreateByMiddleName() {
		return createByMiddleName;
	}

	public void setCreateByMiddleName(String createByMiddleName) {
		this.createByMiddleName = createByMiddleName;
	}

	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}

	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}
	
	
}
