package com.pratikshat.exicom.entity;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners({AuditingEntityListener.class , ObjectAuditListener.class})
public class BtCase extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtCase>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
	@ManyToOne
	BusinessDomain businessDomain;
	@ManyToOne
	CaseTypes caseType;
	@ManyToOne
	CaseReasonDetails caseReason;
	@ManyToOne(fetch=FetchType.LAZY,
			cascade = CascadeType.MERGE)
	BtAssets btAssets;
	@ManyToOne(fetch=FetchType.LAZY)
	BtSiteDetails site;
	@ManyToOne
	CaseStatusDetails status;
	@ManyToOne
	PendingResponsibility pendingResponsibility;
	@ManyToOne
	CaseStageDetails stage;
	@ManyToOne
	CaseOriginDetails origin;
	@ManyToOne
	CasePriorityDetails priority;
	@ManyToOne
	ContactDetails contactPerson;
	
	String noOfsmr;
	@Column(length=1000)
	String remarks;
	@Column(length=1000)
	String inputFromEng;
	@Column(length=1000)
	String internalRemark;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails closedBy;
	java.util.Date closeDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails ownerDetails;
	
	String manualId;
	boolean synked;
	@ManyToOne(fetch=FetchType.LAZY)
	LegalEntity serviceCenter;
	
	@ManyToOne
	ServiceRecource engineer;
	String missingAssets;
	String RJILTT01No;
	
	@Column(length=1000)
	String otherProblemReported;
	boolean isAssignByAssignmentRule;
	String PODNumber;
	Date PODDate;
	String jmsNo;
	public String getJmsNo() {
		return jmsNo;
	}
	public void setJmsNo(String jmsNo) {
		this.jmsNo = jmsNo;
	}
	String PONumber;
	Date PODate;
	String invoiceNumber;
	Date invoiceDate;
	String vendorInvoiceNumber;
	Date vendorInvoiceDate;
	
	String otherSARequest;
	
	String podShippingMode;
	String podShippingToName;
	String podFile;
	String podShippingTrackingNumber;
	String podShippingExpectedDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	BtPreRegistrationCases prc;
	
	@ManyToOne
	BtAssetsTypeChoice assetsTypeChoice;
	String poamount;
	String lineitem;
	@ManyToOne
	BtProblemMaster btProblemMaster;
	
	@Column(columnDefinition = "bit default 0")
	boolean rma;
	@Column(columnDefinition = "bit default 0")
	boolean rol;
	
	String rolRemark;
	
	
	String customer;
	
	@Column(columnDefinition = "bit default 0")
	boolean isPODUploaded;
	
	String CustomerWccId;
	String CustomerWccManualId;
	
	String openDate;
	String ticketNo;
	
	String casesubReason;
	

	// @ManyToOne(fetch=FetchType.LAZY)
	// BtCustomerWcc customerWcc;
	
		
	public String getCustomerWccId() {
		return CustomerWccId;
	}
	public void setCustomerWccId(String customerWccId) {
		CustomerWccId = customerWccId;
	}
	public String getCustomerWccManualId() {
		return CustomerWccManualId;
	}
	public void setCustomerWccManualId(String customerWccManualId) {
		CustomerWccManualId = customerWccManualId;
	}
	// public BtCustomerWcc getCustomerWcc() {
	// 	return customerWcc;
	// }
	// public void setCustomerWcc(BtCustomerWcc customerWcc) {
	// 	this.customerWcc = customerWcc;
	// }
	public BtCase(long id) {
		this.id = id;
	}
	public BtCase() {
		
	}

	public boolean isPODUploaded() {
		return isPODUploaded;
	}
	public void setPODUploaded(boolean isPODUploaded) {
		this.isPODUploaded = isPODUploaded;
	}
	public String getPodFile() {
		return podFile;
	}
	public void setPodFile(String podFile) {
		this.podFile = podFile;
	}
	public BtAssetsTypeChoice getAssetsTypeChoice() {
		return assetsTypeChoice;
	}
	public void setAssetsTypeChoice(BtAssetsTypeChoice assetsTypeChoice) {
		this.assetsTypeChoice = assetsTypeChoice;
	}
	@Override
	public BtCase gOld() {
		// TODO Auto-generated method stub
		return old;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
		
	}

	@Override
	public EmployeeDetails getOwner() {
		// TODO Auto-generated method stub
		return ownerDetails;
	}

	@Override
	public void setOwner(EmployeeDetails employeeDetails) {
		this.ownerDetails=employeeDetails;
	}

	@Override
	public String getManualId() {
		// TODO Auto-generated method stub
		return manualId;
	}

	@Override
	public void setManualId(String manualId) {
		this.manualId=manualId;
		
	}
    @Transient transient BtCase old;
	
	@PostLoad
	@Override
	public void old() {
	this.old=(BtCase) SerializationUtils.clone(this);
	}

	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}

	public CaseTypes getCaseType() {
		return caseType;
	}

	public void setCaseType(CaseTypes caseType) {
		this.caseType = caseType;
	}

	public CaseReasonDetails getCaseReason() {
		return caseReason;
	}

	public void setCaseReason(CaseReasonDetails caseReason) {
		this.caseReason = caseReason;
	}

	public BtProblemMaster getBtProblemMaster() {
		return btProblemMaster;
	}
	public void setBtProblemMaster(BtProblemMaster btProblemMaster) {
		this.btProblemMaster = btProblemMaster;
	}
	public BtAssets getBtAssets() {
		return btAssets;
	}

	public void setBtAssets(BtAssets btAssets) {
		this.btAssets = btAssets;
	}

	public BtSiteDetails getSite() {
		return site;
	}

	public void setSite(BtSiteDetails site) {
		this.site = site;
	}

	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public CaseStatusDetails getStatus() {
		return status;
	}

	public void setStatus(CaseStatusDetails status) {
		this.status = status;
	}

	public PendingResponsibility getPendingResponsibility() {
		return pendingResponsibility;
	}

	public void setPendingResponsibility(PendingResponsibility pendingResponsibility) {
		this.pendingResponsibility = pendingResponsibility;
	}

	public CaseStageDetails getStage() {
		return stage;
	}

	public void setStage(CaseStageDetails stage) {
		this.stage = stage;
	}

	public CaseOriginDetails getOrigin() {
		return origin;
	}

	public void setOrigin(CaseOriginDetails origin) {
		this.origin = origin;
	}

	public CasePriorityDetails getPriority() {
		return priority;
	}

	public void setPriority(CasePriorityDetails priority) {
		this.priority = priority;
	}

	public ContactDetails getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(ContactDetails contactPerson) {
		this.contactPerson = contactPerson;
	}
	
	//getter & setter for casesubreason

	
	public String getNoOfsmr() {
		return noOfsmr;
	}

	
	public void setNoOfsmr(String noOfsmr) {
		this.noOfsmr = noOfsmr;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getInputFromEng() {
		return inputFromEng;
	}

	public void setInputFromEng(String inputFromEng) {
		this.inputFromEng = inputFromEng;
	}

	public String getInternalRemark() {
		return internalRemark;
	}

	public void setInternalRemark(String internalRemark) {
		this.internalRemark = internalRemark;
	}

	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	public EmployeeDetails getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(EmployeeDetails closedBy) {
		this.closedBy = closedBy;
	}

	public java.util.Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(java.util.Date closeDate) {
		this.closeDate = closeDate;
	}

	public EmployeeDetails getOwnerDetails() {
		return ownerDetails;
	}

	public void setOwnerDetails(EmployeeDetails ownerDetails) {
		this.ownerDetails = ownerDetails;
	}

	public boolean isSynked() {
		return synked;
	}

	public void setSynked(boolean synked) {
		this.synked = synked;
	}

	public LegalEntity getServiceCenter() {
		return serviceCenter;
	}

	public void setServiceCenter(LegalEntity serviceCenter) {
		this.serviceCenter = serviceCenter;
	}

	public ServiceRecource getEngineer() {
		return engineer;
	}

	public void setEngineer(ServiceRecource engineer) {
		this.engineer = engineer;
	}

	public String getMissingAssets() {
		return missingAssets;
	}

	public void setMissingAssets(String missingAssets) {
		this.missingAssets = missingAssets;
	}

	public String getRJILTT01No() {
		return RJILTT01No;
	}

	public void setRJILTT01No(String rJILTT01No) {
		RJILTT01No = rJILTT01No;
	}

	public String getOtherProblemReported() {
		return otherProblemReported;
	}

	public void setOtherProblemReported(String otherProblemReported) {
		this.otherProblemReported = otherProblemReported;
	}

	public boolean isAssignByAssignmentRule() {
		return isAssignByAssignmentRule;
	}

	public void setAssignByAssignmentRule(boolean isAssignByAssignmentRule) {
		this.isAssignByAssignmentRule = isAssignByAssignmentRule;
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

	public String getPONumber() {
		return PONumber;
	}

	public void setPONumber(String pONumber) {
		PONumber = pONumber;
	}

	public Date getPODate() {
		return PODate;
	}

	public void setPODate(Date pODate) {
		PODate = pODate;
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

	public String getVendorInvoiceNumber() {
		return vendorInvoiceNumber;
	}

	public void setVendorInvoiceNumber(String vendorInvoiceNumber) {
		this.vendorInvoiceNumber = vendorInvoiceNumber;
	}

	public Date getVendorInvoiceDate() {
		return vendorInvoiceDate;
	}

	public void setVendorInvoiceDate(Date vendorInvoiceDate) {
		this.vendorInvoiceDate = vendorInvoiceDate;
	}

	public String getOtherSARequest() {
		return otherSARequest;
	}

	public void setOtherSARequest(String otherSARequest) {
		this.otherSARequest = otherSARequest;
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

	public boolean isRma() {
		return rma;
	}
	public void setRma(boolean rma) {
		this.rma = rma;
	}
	
	public boolean isRol() {
		return rol;
	}
	public void setRol(boolean rol) {
		this.rol = rol;
	}
	public String getRolRemark() {
		return rolRemark;
	}
	public void setRolRemark(String rolRemark) {
		this.rolRemark = rolRemark;
	}
	public BtPreRegistrationCases getPrc() {
		return prc;
	}

	public void setPrc(BtPreRegistrationCases prc) {
		this.prc = prc;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String getPoamount() {
		return poamount;
	}
	public void setPoamount(String poamount) {
		this.poamount = poamount;
	}
	public String getLineitem() {
		return lineitem;
	}
	public void setLineitem(String lineitem) {
		this.lineitem = lineitem;
	}
	
	public String toString() {
		return ""+String.valueOf(id);
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getCasesubReason() {
		return casesubReason;
	}
	public void setCasesubReason(String casesubReason) {
		this.casesubReason = casesubReason;
	}
	

}
