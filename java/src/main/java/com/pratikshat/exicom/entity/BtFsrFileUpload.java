package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({AuditingEntityListener.class})
public class BtFsrFileUpload extends Auditable<EmployeeDetails> implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	String fileName;
	
	String physicalFileLocation;
	
	@ManyToOne
	BtServiceAppointment appointment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails details;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPhysicalFileLocation() {
		return physicalFileLocation;
	}

	public void setPhysicalFileLocation(String physicalFileLocation) {
		this.physicalFileLocation = physicalFileLocation;
	}


	public BtServiceAppointment getAppointment() {
		return appointment;
	}

	public void setAppointment(BtServiceAppointment appointment) {
		this.appointment = appointment;
	}

	public EmployeeDetails getDetails() {
		return details;
	}

	public void setDetails(EmployeeDetails details) {
		this.details = details;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
		
	}

	@Override
	public EmployeeDetails getOwner() {
		// TODO Auto-generated method stub
		return this.getOwner();
	}

	@Override
	public void setOwner(EmployeeDetails u) {
		this.details=u;
		
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
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
