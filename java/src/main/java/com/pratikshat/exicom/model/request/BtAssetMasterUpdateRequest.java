package com.pratikshat.exicom.model.request;

import java.util.Date;
import java.util.Set;



public class BtAssetMasterUpdateRequest {
	
	private Long srNo;
	private String equipmentType;
	
	private Long btAsset;
	
	private String equipmentSrNo;
	
	private String softwareVersion;
	
	private String currentCondition;
	
//	private Set<BtFaultyAssteRequest> faulty;
	
	private int flag;
	//request of FaultyAssetParameter
	private String equipmentName;
	
	private Float voltage;
	
	private Float soc;
	private Float soh;
	private Long activeAlarms;
	private Date problemSince;
	private Float minCellVoltage;
	private Float maxCellVoltage;
	private Float volGap;
	private Float batteryTemp;
	private Float lastCycleDel;
	private String logStatus;
	private String batteryRequiredAtBRC;
	
	
	
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
	public String getCurrentCondition() {
		return currentCondition;
	}
	public void setCurrentCondition(String currentCondition) {
		this.currentCondition = currentCondition;
	}

	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public Float getVoltage() {
		return voltage;
	}
	public void setVoltage(Float voltage) {
		this.voltage = voltage;
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
	public Long getActiveAlarms() {
		return activeAlarms;
	}
	public void setActiveAlarms(Long activeAlarms) {
		this.activeAlarms = activeAlarms;
	}
	public Date getProblemSince() {
		return problemSince;
	}
	public void setProblemSince(Date problemSince) {
		this.problemSince = problemSince;
	}
	public Float getMinCellVoltage() {
		return minCellVoltage;
	}
	public void setMinCellVoltage(Float minCellVoltage) {
		this.minCellVoltage = minCellVoltage;
	}
	public Float getMaxCellVoltage() {
		return maxCellVoltage;
	}
	public void setMaxCellVoltage(Float maxCellVoltage) {
		this.maxCellVoltage = maxCellVoltage;
	}
	public Float getVolGap() {
		return volGap;
	}
	public void setVolGap(Float volGap) {
		this.volGap = volGap;
	}
	public Float getBatteryTemp() {
		return batteryTemp;
	}
	public void setBatteryTemp(Float batteryTemp) {
		this.batteryTemp = batteryTemp;
	}
	public Float getLastCycleDel() {
		return lastCycleDel;
	}
	public void setLastCycleDel(Float lastCycleDel) {
		this.lastCycleDel = lastCycleDel;
	}
	public String getLogStatus() {
		return logStatus;
	}
	public void setLogStatus(String logStatus) {
		this.logStatus = logStatus;
	}
	public String getBatteryRequiredAtBRC() {
		return batteryRequiredAtBRC;
	}
	public void setBatteryRequiredAtBRC(String batteryRequiredAtBRC) {
		this.batteryRequiredAtBRC = batteryRequiredAtBRC;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	

}
