package com.pratikshat.exicom.model.request;

public class BtCommissioningAssetMasterUpdateRequest {

	private Long srNo;
	
	private String equipmentType;
	
	private Long btAsset;
	
	private String equipmentSrNo;
	
	private String softwareVersion;
	
	private Float soc;
	
	private Float soh;
	
	private Long currentCondition;


	public Long getSrNo() {
		return srNo;
	}

	public void setSrNo(Long srNo) {
		this.srNo = srNo;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public Long getBtAsset() {
		return btAsset;
	}

	public void setBtAsset(Long btAsset) {
		this.btAsset = btAsset;
	}

	public String getEquipmentSrNo() {
		return equipmentSrNo;
	}

	public void setEquipmentSrNo(String equipmentSrNo) {
		this.equipmentSrNo = equipmentSrNo;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public Float getSoc() {
		return soc;
	}

	public void setSoc(Float soc) {
		this.soc = soc;
	}

	public Float getSoh() {
		return soh;
	}

	public void setSoh(Float soh) {
		this.soh = soh;
	}

	public Long getCurrentCondition() {
		return currentCondition;
	}

	public void setCurrentCondition(Long currentCondition) {
		this.currentCondition = currentCondition;
	}

	
	
}
