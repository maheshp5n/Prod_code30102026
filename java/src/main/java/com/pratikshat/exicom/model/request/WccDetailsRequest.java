package com.pratikshat.exicom.model.request;

import java.util.Date;
import java.util.List;

public class WccDetailsRequest {
	Long id;
	String circleName;
	int wccTypeId;
	Date startDate;
	Date toDate;
	String ponumber;
	
	List<WccServiceAppointmentRequest> saIds;
	
	
	
	public int getWccTypeId() {
		return wccTypeId;
	}
	public void setWccTypeId(int wccTypeId) {
		this.wccTypeId = wccTypeId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<WccServiceAppointmentRequest> getSaIds() {
		return saIds;
	}
	public void setSaIds(List<WccServiceAppointmentRequest> saIds) {
		this.saIds = saIds;
	}
	public String getPonumber() {
		return ponumber;
	}
	public void setPonumber(String ponumber) {
		this.ponumber = ponumber;
	}
	
	
}
