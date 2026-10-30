package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class BtVendorInvoiceGrn extends Auditable<EmployeeDetails> implements java.io.Serializable,OldPresist<BtVendorInvoiceGrn>{

	@Id
	@OneToOne(fetch=FetchType.LAZY)
	BtVendorInvoiceDetails invoice;
	
	String grnNumber;
	String grnDate;
	String entrySheetNumber;
	
	@ManyToOne(fetch=FetchType.LAZY)
	InternalServiceCode internalServiceCode;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	
	
	public BtVendorInvoiceGrn() {
	}
	public BtVendorInvoiceGrn(BtVendorInvoiceDetails invoice) {
		this.invoice = invoice;
	}
	public BtVendorInvoiceDetails getInvoice() {
		return invoice;
	}
	public void setInvoice(BtVendorInvoiceDetails invoice) {
		this.invoice = invoice;
	}
	public String getGrnNumber() {
		return grnNumber;
	}
	public void setGrnNumber(String grnNumber) {
		this.grnNumber = grnNumber;
	}
	public String getGrnDate() {
		return grnDate;
	}
	public void setGrnDate(String grnDate) {
		this.grnDate = grnDate;
	}
	public String getEntrySheetNumber() {
		return entrySheetNumber;
	}
	public void setEntrySheetNumber(String entrySheetNumber) {
		this.entrySheetNumber = entrySheetNumber;
	}
	public InternalServiceCode getInternalServiceCode() {
		return internalServiceCode;
	}
	public void setInternalServiceCode(InternalServiceCode internalServiceCode) {
		this.internalServiceCode = internalServiceCode;
	}

	@Transient transient BtVendorInvoiceGrn old;	
	@Override
	@PostLoad
	public void old() {
		old=(BtVendorInvoiceGrn) SerializationUtils.clone(this);
		
	}
	@Override
	public BtVendorInvoiceGrn gOld() {
		return this.old;
	}
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.invoice.getId();
	}

	@Override
	public void setId(Long id) {
		
		this.invoice=new BtVendorInvoiceDetails(id);
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
		return "";
	}

	@Override
	public void setManualId(String manualId) {
		// TODO Auto-generated method stub
		
	}

}
