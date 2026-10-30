package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class Annexure implements java.io.Serializable,OldPresist<Annexure>{

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	
	String manualId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	ContactDetails vendor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	CircleDetails circle;
	
	String invoiceNumber;
	
	Date invoiceDate;
	Date submittedDate;
	Date approvedByCommercialDate;
	Date approvedByCircleDate;
	boolean isInvoiceReceived;
	long lineItemCount;
	double totalAmount;
	
	String poNumber;
	Date poDate;
	
	@ManyToOne
	AnnexureApprovalStatus approvalStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails owner;
	
	@ManyToOne
	BusinessDomain businessDomain;
	
	public Annexure() {
		
	}
	
	public Annexure(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getManualId() {
		return manualId;
	}

	public void setManualId(String manualId) {
		this.manualId = manualId;
	}

	public ContactDetails getVendor() {
		return vendor;
	}

	public void setVendor(ContactDetails vendor) {
		this.vendor = vendor;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Date getApprovedByCommercialDate() {
		return approvedByCommercialDate;
	}

	public void setApprovedByCommercialDate(Date approvedByCommercialDate) {
		this.approvedByCommercialDate = approvedByCommercialDate;
	}

	public Date getApprovedByCircleDate() {
		return approvedByCircleDate;
	}

	public void setApprovedByCircleDate(Date approvedByCircleDate) {
		this.approvedByCircleDate = approvedByCircleDate;
	}

	public boolean isInvoiceReceived() {
		return isInvoiceReceived;
	}

	public void setInvoiceReceived(boolean isInvoiceReceived) {
		this.isInvoiceReceived = isInvoiceReceived;
	}

	public AnnexureApprovalStatus getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(AnnexureApprovalStatus approvalStatus) {
		this.approvalStatus = approvalStatus;
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

	public EmployeeDetails getOwner() {
		return owner;
	}

	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}

	public long getLineItemCount() {
		return lineItemCount;
	}

	public void setLineItemCount(long lineItemCount) {
		this.lineItemCount = lineItemCount;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public CircleDetails getCircle() {
		return circle;
	}

	public void setCircle(CircleDetails circle) {
		this.circle = circle;
	}

	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Date getPoDate() {
		return poDate;
	}

	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}
	
	@Transient transient Annexure old;
	
	@PostLoad
	@Override
	public void old() {
		old=(Annexure)SerializationUtils.clone(this);
	}

	@Override
	public Annexure gOld() {
		// TODO Auto-generated method stub
		return old;
	}
	public String toString() {
		return String.valueOf(id);
	}
	

}
