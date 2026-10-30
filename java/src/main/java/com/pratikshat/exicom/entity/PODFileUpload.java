package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class PODFileUpload  implements java.io.Serializable,OldPresist<PODFileUpload>{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String fileUrl;
	
//	@ManyToOne
//	BtCase btCase;
	
	@ManyToOne
	BtCustomerWcc wcc;
	
	
	//Add for Commercial
	
	String uploadFileName;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	int addBy; //1 For SCH and 2 For Commercial

	
	public void setId(Long id) {
		this.id = id;
	}

	public EmployeeDetails getOwner() {
		return owner;
	}

	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}


	public int getAddBy() {
		return addBy;
	}

	public void setAddBy(int addBy) {
		this.addBy = addBy;
	}

	public Long getId() {
		return id;
	}


	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	
//	public BtCase getBtCase() {
//		return btCase;
//	}
//
//	public void setBtCase(BtCase btCase) {
//		this.btCase = btCase;
//	}

	
	public BtCustomerWcc getWcc() {
		return wcc;
	}

	public void setWcc(BtCustomerWcc wcc) {
		this.wcc = wcc;
	}


	@Transient transient PODFileUpload old;
	@Override
	public void old() {
		this.old=(PODFileUpload) SerializationUtils.clone(this);
		
	}

	@Override
	public PODFileUpload gOld() {
		// TODO Auto-generated method stub
		return this.old;
	}


}
