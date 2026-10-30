package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BtRmaDetailsStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String rmaStatusValue;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRmaStatusValue() {
		return rmaStatusValue;
	}

	public void setRmaStatusValue(String rmaStatusValue) {
		this.rmaStatusValue = rmaStatusValue;
	}
	
	
	
	

}
