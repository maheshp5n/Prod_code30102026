package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtFaultyAssetParameters extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtFaultyAssetParameters>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String equipmentName;
	
	private float voltage;
	
	private float soc;
	
	private  float soh;
	
	@ManyToOne(fetch =FetchType.LAZY)
	ActiveAlarms activeAlarms;
	
	private Date problemSince;
	
	private float minCellVoltage;
	
	private float maxCellVoltage;
	
	private float volGap;
	
	private float batteryTemp;
	
	private float lastCycleDel;
	
	private String logStatus;
	
	private String batteryRequiredAtBRC;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	BtFsr btFsr;

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public ActiveAlarms getActiveAlarms() {
		return activeAlarms;
	}

	public void setActiveAlarms(ActiveAlarms activeAlarms) {
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

	public BtFsr getBtFsr() {
		return btFsr;
	}

	public void setBtFsr(BtFsr btFsr) {
		this.btFsr = btFsr;
	}

	@Transient transient BtFaultyAssetParameters old;
	@PostLoad
	@Override
	public void old() {
		old=(BtFaultyAssetParameters) SerializationUtils.clone(this);
		
	}

	@Override
	public BtFaultyAssetParameters gOld() {
		// TODO Auto-generated method stub
		return this.old;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
		
	}

	@Override
	public EmployeeDetails getOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwner(EmployeeDetails u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getManualId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setManualId(String manualId) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	

}
