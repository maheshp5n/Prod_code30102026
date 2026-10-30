package com.pratikshat.exicom.model.response;

import com.pratikshat.exicom.entity.ZoneDetails;

public class CircleZoneForList {
	String circleName;
	String zoneName;
	public CircleZoneForList() {}
	public CircleZoneForList(String circleName,ZoneDetails zone) {
		this.circleName=circleName;
		if(zone!=null) {
			this.zoneName = zone.getZoneName();
		}
		
	}
	/**
	 * @return the circleName
	 */
	public String getCircleName() {
		return circleName;
	}

	/**
	 * @param circleName the circleName to set
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	
	
}
