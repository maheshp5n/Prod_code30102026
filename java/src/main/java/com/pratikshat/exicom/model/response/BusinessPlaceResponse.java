package com.pratikshat.exicom.model.response;

public class BusinessPlaceResponse {

	int businessPlaceId;
	String businessPlaceName;
	
	
	
	public BusinessPlaceResponse(int businessPlaceId, String businessPlaceName) {
		this.businessPlaceId = businessPlaceId;
		this.businessPlaceName = businessPlaceName;
	}
	public int getBusinessPlaceId() {
		return businessPlaceId;
	}
	public void setBusinessPlaceId(int businessPlaceId) {
		this.businessPlaceId = businessPlaceId;
	}
	public String getBusinessPlaceName() {
		return businessPlaceName;
	}
	public void setBusinessPlaceName(String businessPlaceName) {
		this.businessPlaceName = businessPlaceName;
	}
	
	
}
