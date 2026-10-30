package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AssetAmcUpdateLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	String assetSerialNo;
	String assetPartCode;
	String amcPoNo;
	String amcPoDate; 
	String amcStartDate;
	String amcPeriod;
	
	Date createdDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	EmployeeDetails createdBy;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAssetSerialNo() {
		return assetSerialNo;
	}
	public void setAssetSerialNo(String assetSerialNo) {
		this.assetSerialNo = assetSerialNo;
	}
	public String getAssetPartCode() {
		return assetPartCode;
	}
	public void setAssetPartCode(String assetPartCode) {
		this.assetPartCode = assetPartCode;
	}
	public String getAmcPoNo() {
		return amcPoNo;
	}
	public void setAmcPoNo(String amcPoNo) {
		this.amcPoNo = amcPoNo;
	}
	public String getAmcPoDate() {
		return amcPoDate;
	}
	public void setAmcPoDate(String amcPoDate) {
		this.amcPoDate = amcPoDate;
	}
	public String getAmcStartDate() {
		return amcStartDate;
	}
	public void setAmcStartDate(String amcStartDate) {
		this.amcStartDate = amcStartDate;
	}
	public String getAmcPeriod() {
		return amcPeriod;
	}
	public void setAmcPeriod(String amcPeriod) {
		this.amcPeriod = amcPeriod;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
