package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ESignatureOnSA implements java.io.Serializable,OldPresist<ESignatureOnSA>{

	@Id
	@OneToOne(fetch=FetchType.LAZY)
	ServiceAppointment id;
	
	@Column(columnDefinition="TEXT")
	String eSignature;
	
	Date createdDate;
	
	Date modifiedDate;
	
	
	public ServiceAppointment getId() {
		return id;
	}
	public void setId(ServiceAppointment id) {
		this.id = id;
	}
	public String geteSignature() {
		return eSignature;
	}
	public void seteSignature(String eSignature) {
		this.eSignature = eSignature;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	@javax.persistence.Transient transient ESignatureOnSA old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ESignatureOnSA)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	
	
	@Override
	public ESignatureOnSA gOld() {
		return old;
	}

}
