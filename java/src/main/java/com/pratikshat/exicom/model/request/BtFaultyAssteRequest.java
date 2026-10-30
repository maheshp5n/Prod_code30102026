package com.pratikshat.exicom.model.request;

public class BtFaultyAssteRequest {
	
	private String serialno;
	private double voltage;
	private String activeAlarm;
	
	public String getSerialno() {
		return serialno;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	public double getVoltage() {
		return voltage;
	}
	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}
	public String getActiveAlarm() {
		return activeAlarm;
	}
	public void setActiveAlarm(String activeAlarm) {
		this.activeAlarm = activeAlarm;
	}
	
	
	

}
