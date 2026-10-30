package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtVendorInvoiceSap extends Auditable<EmployeeDetails> implements java.io.Serializable,OldPresist<BtVendorInvoiceSap>{

	@Id
	@JoinColumn(name="id")
	Long id;
	
	String sapNumber;;
	Date sapDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id")
	@MapsId
	BtVendorInvoiceDetails vendorIvoice;
	
	public BtVendorInvoiceSap() {
	}
		
	public BtVendorInvoiceSap(Long id) {
		this.id = id;
	}



	public String getSapNumber() {
		return sapNumber;
	}

	public void setSapNumber(String sapNumber) {
		this.sapNumber = sapNumber;
	}

	public Date getSapDate() {
		return sapDate;
	}

	public void setSapDate(Date sapDate) {
		this.sapDate = sapDate;
	}

	public BtVendorInvoiceDetails getVendorIvoice() {
		return vendorIvoice;
	}

	public void setVendorIvoice(BtVendorInvoiceDetails vendorIvoice) {
		this.vendorIvoice = vendorIvoice;
	}

	@Transient transient BtVendorInvoiceSap old;
	@Override
	@PostLoad
	public void old() {
		this.old=(BtVendorInvoiceSap)org.apache.commons.lang.SerializationUtils.clone(this);
	}

	@Override
	public BtVendorInvoiceSap gOld() {
		// TODO Auto-generated method stub
		return this.old;
	}
	
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
//		this.vendorIvoice=new BtVendorInvoiceDetails(id);
	}

	@Override
	public EmployeeDetails getOwner() {
		// TODO Auto-generated method stub
		return this.owner;
	}

	@Override
	public void setOwner(EmployeeDetails u) {
		this.owner=u;
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
	
	



	@Override
	public String toString() {
		return String.valueOf(this.id);
	}

}
