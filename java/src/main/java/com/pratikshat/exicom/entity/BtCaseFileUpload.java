package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({AuditingEntityListener.class})
public class BtCaseFileUpload extends Auditable<EmployeeDetails> implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	String manualId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	BtCase btCase;
	
	String fileName;
	String physicalFileLocation;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	@Override
	public EmployeeDetails getOwner() {
		// TODO Auto-generated method stub
		return this.getOwner();
	}

	@Override
	public void setOwner(EmployeeDetails u) {
		// TODO Auto-generated method stub
		this.owner=u;
	}

	@Override
	public String getManualId() {
		// TODO Auto-generated method stub
		return this.getManualId();
	}

	@Override
	public void setManualId(String manualId) {
		// TODO Auto-generated method stub
		this.manualId=manualId;
	}
	
	
	
	public BtCase getBtCase() {
		return btCase;
	}

	public void setBtCase(BtCase btCase) {
		this.btCase = btCase;
	}

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

	@Override
	public String toString() {
		
		return String.valueOf(this.id);
	}

}
