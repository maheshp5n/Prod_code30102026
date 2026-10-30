package com.pratikshat.exicom.model.response;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.pratikshat.exicom.entity.ActiveAlarms;
import com.pratikshat.exicom.entity.BtFaultyAssetParameters;
import com.pratikshat.exicom.entity.BtFsr;

public class BtFaultyAssetParametersResponse {

	private String equipmentName;
	private float voltage;
	private float soc;
	private float soh;
	long activeAlarmsId;
	String activeAlarms;
	private Date problemSince;
	private float minCellVoltage;
	private float maxCellVoltage;
	private float volGap;
	private float batteryTemp;
	private float lastCycleDel;
	private String logStatus;
	private String batteryRequiredAtBRC;

	public BtFaultyAssetParametersResponse(String equipmentName, float voltage, float soc, float soh,long activeAlarmsId,
			String activeAlarms, Date problemSince, float minCellVoltage, float maxCellVoltage, float volGap,
			float batteryTemp, float lastCycleDel, String logStatus, String batteryRequiredAtBRC) {
		this.equipmentName = equipmentName;
		this.voltage = voltage;
		this.soc = soc;
		this.soh = soh;
		this.activeAlarmsId=activeAlarmsId;
		this.activeAlarms = activeAlarms;
		this.problemSince = problemSince;
		this.minCellVoltage = minCellVoltage;
		this.maxCellVoltage = maxCellVoltage;
		this.volGap = volGap;
		this.batteryTemp = batteryTemp;
		this.lastCycleDel = lastCycleDel;
		this.logStatus = logStatus;
		this.batteryRequiredAtBRC = batteryRequiredAtBRC;
	}

	public BtFaultyAssetParametersResponse() {
		super();
	}

	public BtFaultyAssetParametersResponse(BtFaultyAssetParameters assetParameters) {
		if (assetParameters != null) {
			if (assetParameters.getActiveAlarms() != null) {
				this.activeAlarmsId=assetParameters.getActiveAlarms().getId();
				this.activeAlarms = assetParameters.getActiveAlarms().getTypeName();
			}
			this.batteryRequiredAtBRC=assetParameters.getBatteryRequiredAtBRC();
			this.equipmentName=assetParameters.getEquipmentName();
			this.batteryTemp=assetParameters.getBatteryTemp();
			this.lastCycleDel= assetParameters.getLastCycleDel();
			this.maxCellVoltage=assetParameters.getMaxCellVoltage();
			this.logStatus=assetParameters.getLogStatus();
			this.minCellVoltage= assetParameters.getMinCellVoltage();
			this.soc= assetParameters.getSoc();
			this.soh=assetParameters.getSoh();
			this.voltage=assetParameters.getVoltage();
			this.volGap=assetParameters.getVolGap();
			this.problemSince=assetParameters.getProblemSince();
		}

	}

	

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public float getVoltage() {
		return voltage;
	}

	public void setVoltage(float voltage) {
		this.voltage = voltage;
	}

	public float getSoc() {
		return soc;
	}

	public void setSoc(float soc) {
		this.soc = soc;
	}

	public float getSoh() {
		return soh;
	}

	public void setSoh(float soh) {
		this.soh = soh;
	}
	
	public long getActiveAlarmsId() {
		return activeAlarmsId;
	}

	public void setActiveAlarmsId(long activeAlarmsId) {
		this.activeAlarmsId = activeAlarmsId;
	}

	public String getActiveAlarms() {
		return activeAlarms;
	}

	public void setActiveAlarms(String activeAlarms) {
		this.activeAlarms = activeAlarms;
	}

	public Date getProblemSince() {
		return problemSince;
	}

	public void setProblemSince(Date problemSince) {
		this.problemSince = problemSince;
	}

	public float getMinCellVoltage() {
		return minCellVoltage;
	}

	public void setMinCellVoltage(float minCellVoltage) {
		this.minCellVoltage = minCellVoltage;
	}

	public float getMaxCellVoltage() {
		return maxCellVoltage;
	}

	public void setMaxCellVoltage(float maxCellVoltage) {
		this.maxCellVoltage = maxCellVoltage;
	}

	public float getVolGap() {
		return volGap;
	}

	public void setVolGap(float volGap) {
		this.volGap = volGap;
	}

	public float getBatteryTemp() {
		return batteryTemp;
	}

	public void setBatteryTemp(float batteryTemp) {
		this.batteryTemp = batteryTemp;
	}

	public float getLastCycleDel() {
		return lastCycleDel;
	}

	public void setLastCycleDel(float lastCycleDel) {
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

}
