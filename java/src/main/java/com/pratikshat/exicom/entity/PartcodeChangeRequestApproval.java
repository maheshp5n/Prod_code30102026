package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class PartcodeChangeRequestApproval {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	Long id;
	
	@MapsId
	@OneToOne
	@JoinColumn(name="id")
	PartcodeChangeRequest changeRequest;
	
	Date approvalDate;
	String oldPartcode;
	String oldSerialNumber;
	String newPartCode;
	String newSerialnumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	
	Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails modifiedBy;
	
	Date modifiedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Location location;
	
	Boolean synced=false;
	@Column(length = 100)
	String remarks;
	
	//SAP UPDATE FIEDLDS
	@Column(name="MBLNR",length = 20)
	String MBLNR;
	@Column(name="MJAHR",length = 10)
	String MJAHR;
	@Column(name="MBLNR_C",length = 20)
	String MBLNR_C;
	@Column(name="MJAHR_C",length = 10)
	String MJAHR_C;
	@Column(name="REMARK_C",length = 200)
	String REMARK_C;
	@Column(name="MBLNR_U",length = 20)
	String MBLNR_U;
	@Column(name="MJAHR_U",length = 10)
	String MJAHR_U;
	@Column(name="REMARK_U",length = 200)
	String REMARK_U;
	
	public PartcodeChangeRequestApproval() {}
	
	public PartcodeChangeRequestApproval(Long id) {
		this.id=id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PartcodeChangeRequest getChangeRequest() {
		return changeRequest;
	}

	public void setChangeRequest(PartcodeChangeRequest changeRequest) {
		this.changeRequest = changeRequest;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getOldPartcode() {
		return oldPartcode;
	}

	public void setOldPartcode(String oldPartcode) {
		this.oldPartcode = oldPartcode;
	}

	public String getOldSerialNumber() {
		return oldSerialNumber;
	}

	public void setOldSerialNumber(String oldSerialNumber) {
		this.oldSerialNumber = oldSerialNumber;
	}

	public String getNewPartCode() {
		return newPartCode;
	}

	public void setNewPartCode(String newPartCode) {
		this.newPartCode = newPartCode;
	}

	public String getNewSerialnumber() {
		return newSerialnumber;
	}

	public void setNewSerialnumber(String newSerialnumber) {
		this.newSerialnumber = newSerialnumber;
	}

	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public Boolean getSynced() {
		return synced;
	}
	
	public void setSynced(Boolean synced) {
		this.synced = synced;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMBLNR() {
		return MBLNR;
	}

	public void setMBLNR(String mBLNR) {
		MBLNR = mBLNR;
	}

	public String getMJAHR() {
		return MJAHR;
	}

	public void setMJAHR(String mJAHR) {
		MJAHR = mJAHR;
	}

	public String getMBLNR_C() {
		return MBLNR_C;
	}

	public void setMBLNR_C(String mBLNR_C) {
		MBLNR_C = mBLNR_C;
	}

	public String getMJAHR_C() {
		return MJAHR_C;
	}

	public void setMJAHR_C(String mJAHR_C) {
		MJAHR_C = mJAHR_C;
	}

	public String getREMARK_C() {
		return REMARK_C;
	}

	public void setREMARK_C(String rEMARK_C) {
		REMARK_C = rEMARK_C;
	}

	public String getMBLNR_U() {
		return MBLNR_U;
	}

	public void setMBLNR_U(String mBLNR_U) {
		MBLNR_U = mBLNR_U;
	}

	public String getMJAHR_U() {
		return MJAHR_U;
	}

	public void setMJAHR_U(String mJAHR_U) {
		MJAHR_U = mJAHR_U;
	}

	public String getREMARK_U() {
		return REMARK_U;
	}

	public void setREMARK_U(String rEMARK_U) {
		REMARK_U = rEMARK_U;
	}
	
	
	
	
}
