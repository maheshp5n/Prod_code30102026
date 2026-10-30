package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SapSalesDataSerialnumber {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String serialNumber;
	
	@ManyToOne(fetch=FetchType.LAZY)
	SapSalesData sapSalesData;
	
	public SapSalesDataSerialnumber() {}
	public SapSalesDataSerialnumber(Long id) {
		this.id=id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public SapSalesData getSapSalesData() {
		return sapSalesData;
	}
	public void setSapSalesData(SapSalesData sapSalesData) {
		this.sapSalesData = sapSalesData;
	}
	
	
	
}
