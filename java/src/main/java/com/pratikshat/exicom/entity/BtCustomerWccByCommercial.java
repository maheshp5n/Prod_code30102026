//package com.pratikshat.exicom.entity;
//
//import javax.persistence.Entity;
//import javax.persistence.EntityListeners;
//import javax.persistence.FetchType;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToOne;
//import javax.persistence.PostLoad;
//import javax.persistence.Transient;
//
//import org.apache.commons.lang.SerializationUtils;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import com.pratikshat.exicom.interceptor.ObjectAuditListener;
//
//@Entity
//@EntityListeners({ObjectAuditListener.class, AuditingEntityListener.class})
//public class BtCustomerWccByCommercial extends Auditable<EmployeeDetails> implements java.io.Serializable, OldPresist<BtCustomerWccByCommercial>{
//
//	
//	@Id
//	@OneToOne(fetch=FetchType.LAZY)
//	BtCustomerWcc wccId;
//	String exicomInvoiceNumber;
//	java.util.Date exicomInvoiceDate;
//	
//	@ManyToOne(fetch=FetchType.LAZY)
//	BtCustomerWccReject rejectReason;
//	String remarks;
//	String SAPInvoiceReferenceNumber;
//	String otherRejectionReason;
//	
//	@ManyToOne(fetch=FetchType.LAZY)
//	EmployeeDetails owner;
//	
//	public BtCustomerWccByCommercial() {
//	}
//	
//	public BtCustomerWccByCommercial(BtCustomerWcc wccId) {
//		this.wccId = wccId;
//	}
//
//	public BtCustomerWcc getWccId() {
//		return wccId;
//	}
//
//	public void setWccId(BtCustomerWcc wccId) {
//		this.wccId = wccId;
//	}
//
//	public String getExicomInvoiceNumber() {
//		return exicomInvoiceNumber;
//	}
//
//	public void setExicomInvoiceNumber(String exicomInvoiceNumber) {
//		this.exicomInvoiceNumber = exicomInvoiceNumber;
//	}
//
//	public java.util.Date getExicomInvoiceDate() {
//		return exicomInvoiceDate;
//	}
//
//	public void setExicomInvoiceDate(java.util.Date exicomInvoiceDate) {
//		this.exicomInvoiceDate = exicomInvoiceDate;
//	}
//
//	public BtCustomerWccReject getRejectReason() {
//		return rejectReason;
//	}
//
//	public void setRejectReason(BtCustomerWccReject rejectReason) {
//		this.rejectReason = rejectReason;
//	}
//
//	public String getRemarks() {
//		return remarks;
//	}
//
//	public void setRemarks(String remarks) {
//		this.remarks = remarks;
//	}
//
//	public String getSAPInvoiceReferenceNumber() {
//		return SAPInvoiceReferenceNumber;
//	}
//
//	public void setSAPInvoiceReferenceNumber(String sAPInvoiceReferenceNumber) {
//		SAPInvoiceReferenceNumber = sAPInvoiceReferenceNumber;
//	}
//
//	public String getOtherRejectionReason() {
//		return otherRejectionReason;
//	}
//
//	public void setOtherRejectionReason(String otherRejectionReason) {
//		this.otherRejectionReason = otherRejectionReason;
//	}
//
//	@Transient transient BtCustomerWccByCommercial old;
//	@Override
//	@PostLoad
//	public void old() {
//		this.old=(BtCustomerWccByCommercial) SerializationUtils.clone(this);
//	}
//
//	@Override
//	public BtCustomerWccByCommercial gOld() {
//		// TODO Auto-generated method stub
//		return this.old;
//	}
//
//	@Override
//	public Long getId() {
//		// TODO Auto-generated method stub
//		return this.wccId.getId();
//	}
//
//	@Override
//	public void setId(Long id) {
//		this.wccId = new BtCustomerWcc(id);
//	}
//
//	@Override
//	public EmployeeDetails getOwner() {
//		// TODO Auto-generated method stub
//		return this.owner;
//	}
//
//	@Override
//	public void setOwner(EmployeeDetails u) {
//		this.owner=u;
//	}
//
//	@Override
//	public String getManualId() {
//		// TODO Auto-generated method stub
//		return "";
//	}
//
//	@Override
//	public void setManualId(String manualId) {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
