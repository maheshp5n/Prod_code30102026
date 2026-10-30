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
public class CaseFilesUpload extends Auditable<EmployeeDetails> implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	String manualId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	CaseDetails caseDetails;
	
	String uploadedFileName;
	
	String uploadPhysicalLocation;
	
	
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
		return this.owner;
	}

	@Override
	public void setOwner(EmployeeDetails u) {
		// TODO Auto-generated method stub
		this.owner=u;
	}

	@Override
	public String getManualId() {
		// TODO Auto-generated method stub
		return this.manualId;
	}

	@Override
	public void setManualId(String manualId) {
		// TODO Auto-generated method stub
		this.manualId=manualId;
	}
	
	
	
	public CaseDetails getCaseDetails() {
		return caseDetails;
	}

	public void setCaseDetails(CaseDetails caseDetails) {
		this.caseDetails = caseDetails;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}

	public String getUploadPhysicalLocation() {
		return uploadPhysicalLocation;
	}

	public void setUploadPhysicalLocation(String uploadPhysicalLocation) {
		this.uploadPhysicalLocation = uploadPhysicalLocation;
	}

	@Override
	public String toString() {
		return String.valueOf(this.id);
	}
	
	

}
