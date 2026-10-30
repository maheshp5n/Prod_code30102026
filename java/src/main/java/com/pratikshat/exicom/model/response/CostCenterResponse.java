package com.pratikshat.exicom.model.response;

public class CostCenterResponse {
	int costCenterId;
	String costCenterName;
	public CostCenterResponse(int costCenterId, String costCenterName) {
		this.costCenterId = costCenterId;
		this.costCenterName = costCenterName;
	}
	public int getCostCenterId() {
		return costCenterId;
	}
	public void setCostCenterId(int costCenterId) {
		this.costCenterId = costCenterId;
	}
	public String getCostCenterName() {
		return costCenterName;
	}
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
	
	
	
	
}
