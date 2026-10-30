package com.pratikshat.exicom.model.response;

public class BusinessAreaResponse {
	
	int businessAreaId;
	String businessAreaName;
	
	
	
	public BusinessAreaResponse(int businessAreaId, String businessAreaName) {
		this.businessAreaId = businessAreaId;
		this.businessAreaName = businessAreaName;
	}
	public int getBusinessAreaId() {
		return businessAreaId;
	}
	public void setBusinessAreaId(int businessAreaId) {
		this.businessAreaId = businessAreaId;
	}
	public String getBusinessAreaName() {
		return businessAreaName;
	}
	public void setBusinessAreaName(String businessAreaName) {
		this.businessAreaName = businessAreaName;
	}
	
	

}
