package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CsToFactoryMovementSerial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String serialNumber;
	@ManyToOne(fetch=FetchType.LAZY)
	CsToFactoryMovement csToFactoryMovement;
	
	
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
	public CsToFactoryMovement getCsToFactoryMovement() {
		return csToFactoryMovement;
	}
	public void setCsToFactoryMovement(CsToFactoryMovement csToFactoryMovement) {
		this.csToFactoryMovement = csToFactoryMovement;
	}
	
	
	
}
