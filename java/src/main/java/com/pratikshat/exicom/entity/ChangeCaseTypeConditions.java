package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ChangeCaseTypeConditions {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	
	String existingCaseType;
	String existingCaseReason;
	String newCaseType;
	String newCaseReason;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getExistingCaseType() {
		return existingCaseType;
	}
	public void setExistingCaseType(String existingCaseType) {
		this.existingCaseType = existingCaseType;
	}
	public String getExistingCaseReason() {
		return existingCaseReason;
	}
	public void setExistingCaseReason(String existingCaseReason) {
		this.existingCaseReason = existingCaseReason;
	}
	public String getNewCaseType() {
		return newCaseType;
	}
	public void setNewCaseType(String newCaseType) {
		this.newCaseType = newCaseType;
	}
	public String getNewCaseReason() {
		return newCaseReason;
	}
	public void setNewCaseReason(String newCaseReason) {
		this.newCaseReason = newCaseReason;
	}
}
