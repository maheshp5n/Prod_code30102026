package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity 
@EntityListeners({ObjectAuditListener.class,AuditingEntityListener.class})
public class BtPreRegistrationCases extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtPreRegistrationCases>{
	
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long id;
	@ManyToOne
	BusinessDomain businessDomain;
	@ManyToOne
	CaseTypes caseType;
	@ManyToOne
	CaseReasonDetails caseReason;
	@ManyToOne(fetch=FetchType.LAZY)
	BtAssets assets;
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
	//Record Created and updated details
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails closedBy;
	java.util.Date closeDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails ownerDetails;
	
	String manualId;
	@ManyToOne(fetch=FetchType.LAZY)
	LegalEntity serviceCenter;
	
	@ManyToOne
	ServiceRecource engineer;
	String missingAssets;
	String RJILTT01No;
	@Column(length=1000)
	String otherProblemReported;
	boolean isAssignByAssignmentRule;
	
	String lastAction;
	String lastActionComment;
	String PONumber;
	Date PODate;
	String poamount;
	String lineitem;
	String openDate;
	String ticketNo;
	
	@ManyToOne
	BtAssetsTypeChoice assetsTypeChoice;
	
	@ManyToOne
	BtProblemMaster btProblemMaster;

	public BtProblemMaster getBtProblemMaster() {
		return btProblemMaster;
	}
	public void setBtProblemMaster(BtProblemMaster btProblemMaster) {
		this.btProblemMaster = btProblemMaster;
	}
	public BtAssetsTypeChoice getAssetsTypeChoice() {
		return assetsTypeChoice;
	}
	public void setAssetsTypeChoice(BtAssetsTypeChoice assetsTypeChoice) {
		this.assetsTypeChoice = assetsTypeChoice;
	}
	public void setId(long id) {
		this.id = id;
	}
	public BtPreRegistrationCases() {
		super();
	}
	public BtPreRegistrationCases(long id) {
		super();
		this.id = id;
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
	public BtAssets getAssets() {
		return assets;
	}
	public void setAssets(BtAssets assets) {
		this.assets = assets;
	}
	public BtSiteDetails getSite() {
		return site;
	}
	public void setSite(BtSiteDetails site) {
		this.site = site;
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
	public String getLastAction() {
		return lastAction;
	}
	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}
	public String getLastActionComment() {
		return lastActionComment;
	}
	public void setLastActionComment(String lastActionComment) {
		this.lastActionComment = lastActionComment;
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
	
	@Transient transient BtPreRegistrationCases old;
	@Override
	@PostLoad
	public void old() {
		old=(BtPreRegistrationCases) SerializationUtils.clone(this);
		
	}
	@Override
	public BtPreRegistrationCases gOld() {
		// TODO Auto-generated method stub
		return old;
	}
	@Override
	public Long getId() {
		
		return this.id;
	}
	@Override
	public void setId(Long id) {
		this.id=id;
		
	}
	@Override
	public EmployeeDetails getOwner() {
		// TODO Auto-generated method stub
		return this.ownerDetails;
	}
	@Override
	public void setOwner(EmployeeDetails u) {
		this.ownerDetails=u;
		
	}
	@Override
	public String getManualId() {
		// TODO Auto-generated method stub
		return this.manualId;
	}
	@Override
	public void setManualId(String manualId) {
		this.manualId=manualId;
		
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
			return ""+id;
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

}
