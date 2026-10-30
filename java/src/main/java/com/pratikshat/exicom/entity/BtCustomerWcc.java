package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({ AuditingEntityListener.class, ObjectAuditListener.class })
public class BtCustomerWcc extends Auditable<EmployeeDetails> implements Serializable, OldPresist<BtCustomerWcc> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String manualId;

	@ManyToOne(fetch=FetchType.LAZY)
	BtWccCustomStatus status;

	String approvalStatus;
	java.util.Date approvalDate;
	
	@Column(columnDefinition = "bit default 0")
	Boolean isWccApprove;
	
	java.util.Date submittedBySchDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	CaseTypes casetype;
	@ManyToOne(fetch=FetchType.LAZY)
	CaseReasonDetails caseReason;
	@ManyToOne(fetch=FetchType.LAZY)
	LegalEntity serviceCenter;
	@ManyToOne(fetch=FetchType.LAZY)
	CircleDetails circle;
	String PODNumber;
	Date PODDate;
	String jmsNo;
	String customer;
	String podShippingMode;
	String podShippingToName;
	String podShippingTrackingNumber;
	String podShippingExpectedDate;
		
	float totalSiteVisitCharge;
	float totalCustomerSpareCharges;
	float totalCharges;
	String customerPoNumber;
	java.util.Date customerPoDate;
	String grnNumber;
	java.util.Date grnDate;
	java.util.Date servicePeriodFrom;
	java.util.Date servicePeriodTo;
	java.util.Date approvedByRsh;
	
	//For Commercial Use 
	
	String exicomInvoiceNumber;
	java.util.Date exicomInvoiceDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	BtCustomerWccReject rejectReason;
	String remarks;
	String SAPInvoiceReferenceNumber;


	public java.util.Date getApprovedByRsh() {
		return approvedByRsh;
	}
	public void setApprovedByRsh(java.util.Date approvedByRsh) {
		this.approvedByRsh = approvedByRsh;
	}
	public String getExicomInvoiceNumber() {
		return exicomInvoiceNumber;
	}
	public void setExicomInvoiceNumber(String exicomInvoiceNumber) {
		this.exicomInvoiceNumber = exicomInvoiceNumber;
	}
	public java.util.Date getExicomInvoiceDate() {
		return exicomInvoiceDate;
	}
	public void setExicomInvoiceDate(java.util.Date exicomInvoiceDate) {
		this.exicomInvoiceDate = exicomInvoiceDate;
	}
	public BtCustomerWccReject getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(BtCustomerWccReject rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSAPInvoiceReferenceNumber() {
		return SAPInvoiceReferenceNumber;
	}
	public void setSAPInvoiceReferenceNumber(String sAPInvoiceReferenceNumber) {
		SAPInvoiceReferenceNumber = sAPInvoiceReferenceNumber;
	}
	public String getCustomerPoNumber() {
		return customerPoNumber;
	}
	public void setCustomerPoNumber(String customerPoNumber) {
		this.customerPoNumber = customerPoNumber;
	}
	public java.util.Date getCustomerPoDate() {
		return customerPoDate;
	}
	public void setCustomerPoDate(java.util.Date customerPoDate) {
		this.customerPoDate = customerPoDate;
	}
	public String getGrnNumber() {
		return grnNumber;
	}
	public void setGrnNumber(String grnNumber) {
		this.grnNumber = grnNumber;
	}
	public java.util.Date getGrnDate() {
		return grnDate;
	}
	public void setGrnDate(java.util.Date grnDate) {
		this.grnDate = grnDate;
	}
	public java.util.Date getServicePeriodFrom() {
		return servicePeriodFrom;
	}
	public void setServicePeriodFrom(java.util.Date servicePeriodFrom) {
		this.servicePeriodFrom = servicePeriodFrom;
	}
	public java.util.Date getServicePeriodTo() {
		return servicePeriodTo;
	}
	public void setServicePeriodTo(java.util.Date servicePeriodTo) {
		this.servicePeriodTo = servicePeriodTo;
	}
	public java.util.Date getSubmittedBySchDate() {
		return submittedBySchDate;
	}
	public void setSubmittedBySchDate(java.util.Date submittedBySchDate) {
		this.submittedBySchDate = submittedBySchDate;
	}
	public Boolean getIsWccApprove() {
		return isWccApprove;
	}
	public void setIsWccApprove(Boolean isWccApprove) {
		this.isWccApprove = isWccApprove;
	}

	public BtCustomerWcc(Long id) {
		this.id = id;
	}
	public BtCustomerWcc() {}

	public CaseTypes getCasetype() {
		return casetype;
	}
	public void setCasetype(CaseTypes casetype) {
		this.casetype = casetype;
	}
	public CaseReasonDetails getCaseReason() {
		return caseReason;
	}
	public void setCaseReason(CaseReasonDetails caseReason) {
		this.caseReason = caseReason;
	}
	public LegalEntity getServiceCenter() {
		return serviceCenter;
	}
	public void setServiceCenter(LegalEntity serviceCenter) {
		this.serviceCenter = serviceCenter;
	}
	public CircleDetails getCircle() {
		return circle;
	}
	public void setCircle(CircleDetails circle) {
		this.circle = circle;
	}
	public String getPODNumber() {
		return PODNumber;
	}
	public void setPODNumber(String pODNumber) {
		PODNumber = pODNumber;
	}
	public Date getPODDate() {
		return PODDate;
	}
	public void setPODDate(Date pODDate) {
		PODDate = pODDate;
	}
	public String getJmsNo() {
		return jmsNo;
	}
	public void setJmsNo(String jmsNo) {
		this.jmsNo = jmsNo;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getPodShippingMode() {
		return podShippingMode;
	}
	public void setPodShippingMode(String podShippingMode) {
		this.podShippingMode = podShippingMode;
	}
	public String getPodShippingToName() {
		return podShippingToName;
	}
	public void setPodShippingToName(String podShippingToName) {
		this.podShippingToName = podShippingToName;
	}
	public String getPodShippingTrackingNumber() {
		return podShippingTrackingNumber;
	}
	public void setPodShippingTrackingNumber(String podShippingTrackingNumber) {
		this.podShippingTrackingNumber = podShippingTrackingNumber;
	}
	public String getPodShippingExpectedDate() {
		return podShippingExpectedDate;
	}
	public void setPodShippingExpectedDate(String podShippingExpectedDate) {
		this.podShippingExpectedDate = podShippingExpectedDate;
	}
	public float getTotalSiteVisitCharge() {
		return totalSiteVisitCharge;
	}
	public void setTotalSiteVisitCharge(float totalSiteVisitCharge) {
		this.totalSiteVisitCharge = totalSiteVisitCharge;
	}
	public float getTotalCustomerSpareCharges() {
		return totalCustomerSpareCharges;
	}
	public void setTotalCustomerSpareCharges(float totalCustomerSpareCharges) {
		this.totalCustomerSpareCharges = totalCustomerSpareCharges;
	}
	public float getTotalCharges() {
		return totalCharges;
	}
	public void setTotalCharges(float totalCharges) {
		this.totalCharges = totalCharges;
	}
	
	@Transient transient BtCustomerWcc old;
	@Override
	@PostLoad
	public void old() {
		this.old = (BtCustomerWcc) SerializationUtils.clone(this);

	}


	@Override
	public BtCustomerWcc gOld() {
		// TODO Auto-generated method stub
		return this.old;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;

	}

	public java.util.Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(java.util.Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	@Override
	public EmployeeDetails getOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwner(EmployeeDetails u) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getManualId() {
		// TODO Auto-generated method stub
		return manualId;
	}

	@Override
	public void setManualId(String manualId) {
		this.manualId = manualId;

	}

	public BtWccCustomStatus getStatus() {
		return status;
	}

	public void setStatus(BtWccCustomStatus status) {
		this.status = status;
	}


	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	@Override
	public String toString() {
		return String.valueOf(this.id);
	}

}
