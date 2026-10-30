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
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtServiceAppointment  extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtServiceAppointment>{

	
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long serviceAppointmentId;
	
	String manualId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	BtCase caseDetails;
	
	@Column(length=1000)
	String faultObserved;
	@Column(length=1000)
	String correctiveActionss;
	@Column(length=1000)
	String punchPointExiom;
	@Column(length=1000)
	String punchPointCustomer;
	java.util.Date visitDate;
	String customerRepresentativeName;
	String customerRepresentativeContact;
	@Column(length=1000)
	String remark;
	
	@javax.persistence.ManyToOne
	ServiceRecource serviceResource;
	
	@ManyToOne
	ServiceAppointmentStatus status;
	
	@ManyToOne
	PendingResponsibility pendingResponsibility;
	
	@ManyToOne
	BtPendingReasonCustomerBucket pendingReasonCustomerBucket;
	
	private  String pendingResource;
	
	String earliestStartPermittedDate;
	String earliestStartPermittedTime;
	
	String dueDate;
	String dueTime;
	String tFlag;
	boolean syncFlag;

	
	java.util.Date createDate;
	java.util.Date closeDate;

	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails ownerDetails;
	@ManyToOne(fetch=FetchType.LAZY)
	CircleDetails circle;
	@ManyToOne(fetch=FetchType.LAZY)
	ContactDetails circleManager;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ClaimStatus claimStatus;
	
	@ManyToOne(fetch=FetchType.LAZY)
	BtAssets assets;
	@ManyToOne(fetch=FetchType.LAZY)
	BtProducts product;
	
	@Column(columnDefinition="integer default 0")
	int approvalStage;
	
	boolean isApprovedByFinance;
	Date approvedByFinance;
	String costCenter;
	
	@ManyToOne
	BusinessDomain businessDomain;
	@Column(columnDefinition = "bit default 0")
	boolean wcc=false;
	
	@Column(columnDefinition = "bit default 0")
	boolean reassign = false;
	
	String wccId;
	String wccManualId;
	
	String acknowledgedByName;
	String acknowledgedByPhone;
	
	double fixedPayout;
	Double latitude;
	Double longitude;
	@Column(columnDefinition = "TEXT")
	String address;
	boolean rma;
	@ManyToOne
	BtServicePayout btServicePayout;
	
	String closedFromPlatform;
	
	@Column(columnDefinition = "bit default 0")
	boolean serviceAppointmentExpense = false;
	
	@Column(columnDefinition = "bit default 0")
	boolean fsrCreated=false;
	
	@Column(columnDefinition = "bit default 0")
	boolean isSchFill=false;
	
	@Column(columnDefinition="TEXT")
	String esignature;
	
	String vfCode;
	
	@Column(columnDefinition = "bit default 0")
	boolean billingRequired=true;
	
	
	public boolean isBillingRequired() {
		return billingRequired;
	}

	public void setBillingRequired(boolean billingRequired) {
		this.billingRequired = billingRequired;
	}

	public BtServiceAppointment(long id) {
		// TODO Auto-generated constructor stub
	}
	
	public BtServiceAppointment() {
		super();
		// TODO Auto-generated constructor stub
	}



	public boolean isServiceAppointmentExpense() {
		return serviceAppointmentExpense;
	}

	
	public String getClosedFromPlatform() {
		return closedFromPlatform;
	}


	public void setClosedFromPlatform(String closedFromPlatform) {
		this.closedFromPlatform = closedFromPlatform;
	}


	public void setServiceAppointmentExpense(boolean serviceAppointmentExpense) {
		this.serviceAppointmentExpense = serviceAppointmentExpense;
	}

	public boolean isRma() {
		return rma;
	}

	public void setRma(boolean rma) {
		this.rma = rma;
	}

	public BtServicePayout getBtServicePayout() {
		return btServicePayout;
	}

	public void setBtServicePayout(BtServicePayout btServicePayout) {
		this.btServicePayout = btServicePayout;
	}

	public long getServiceAppointmentId() {
		return serviceAppointmentId;
	}

	public double getFixedPayout() {
		return fixedPayout;
	}

	public void setFixedPayout(double fixedPayout) {
		this.fixedPayout = fixedPayout;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setServiceAppointmentId(long serviceAppointmentId) {
		this.serviceAppointmentId = serviceAppointmentId;
	}

	public String getmanualId() {
		return manualId;
	}

	public void setmanualId(String manualId) {
		this.manualId = manualId;
	}

	public BtCase getCaseDetails() {
		return caseDetails;
	}

	public void setCaseDetails(BtCase caseDetails) {
		this.caseDetails = caseDetails;
	}

	public BtPendingReasonCustomerBucket getPendingReasonCustomerBucket() {
		return pendingReasonCustomerBucket;
	}


	public void setPendingReasonCustomerBucket(BtPendingReasonCustomerBucket pendingReasonCustomerBucket) {
		this.pendingReasonCustomerBucket = pendingReasonCustomerBucket;
	}


	public String getFaultObserved() {
		return faultObserved;
	}

	public void setFaultObserved(String faultObserved) {
		this.faultObserved = faultObserved;
	}

	public String getCorrectiveActionss() {
		return correctiveActionss;
	}


	public void setCorrectiveActionss(String correctiveActionss) {
		this.correctiveActionss = correctiveActionss;
	}


	public String getPunchPointExiom() {
		return punchPointExiom;
	}

	public void setPunchPointExiom(String punchPointExiom) {
		this.punchPointExiom = punchPointExiom;
	}

	public String getPunchPointCustomer() {
		return punchPointCustomer;
	}

	public void setPunchPointCustomer(String punchPointCustomer) {
		this.punchPointCustomer = punchPointCustomer;
	}

	public java.util.Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(java.util.Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getCustomerRepresentativeName() {
		return customerRepresentativeName;
	}

	public void setCustomerRepresentativeName(String customerRepresentativeName) {
		this.customerRepresentativeName = customerRepresentativeName;
	}

	public String getCustomerRepresentativeContact() {
		return customerRepresentativeContact;
	}

	public void setCustomerRepresentativeContact(String customerRepresentativeContact) {
		this.customerRepresentativeContact = customerRepresentativeContact;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ServiceRecource getServiceResource() {
		return serviceResource;
	}

	public void setServiceResource(ServiceRecource serviceResource) {
		this.serviceResource = serviceResource;
	}

	public ServiceAppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(ServiceAppointmentStatus status) {
		this.status = status;
	}

	public PendingResponsibility getPendingResponsibility() {
		return pendingResponsibility;
	}

	public void setPendingResponsibility(PendingResponsibility pendingResponsibility) {
		this.pendingResponsibility = pendingResponsibility;
	}

	public String getEarliestStartPermittedDate() {
		return earliestStartPermittedDate;
	}

	public void setEarliestStartPermittedDate(String earliestStartPermittedDate) {
		this.earliestStartPermittedDate = earliestStartPermittedDate;
	}

	public String getEarliestStartPermittedTime() {
		return earliestStartPermittedTime;
	}

	public void setEarliestStartPermittedTime(String earliestStartPermittedTime) {
		this.earliestStartPermittedTime = earliestStartPermittedTime;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getDueTime() {
		return dueTime;
	}

	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}

	public String gettFlag() {
		return tFlag;
	}

	public void settFlag(String tFlag) {
		this.tFlag = tFlag;
	}

	public boolean isSyncFlag() {
		return syncFlag;
	}

	public void setSyncFlag(boolean syncFlag) {
		this.syncFlag = syncFlag;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
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

	public CircleDetails getCircle() {
		return circle;
	}

	public void setCircle(CircleDetails circle) {
		this.circle = circle;
	}

	public ContactDetails getCircleManager() {
		return circleManager;
	}

	public void setCircleManager(ContactDetails circleManager) {
		this.circleManager = circleManager;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public BtAssets getAssets() {
		return assets;
	}

	public void setAssets(BtAssets assets) {
		this.assets = assets;
	}

	public BtProducts getProduct() {
		return product;
	}

	public void setProduct(BtProducts product) {
		this.product = product;
	}

	public int getApprovalStage() {
		return approvalStage;
	}

	public void setApprovalStage(int approvalStage) {
		this.approvalStage = approvalStage;
	}

	public boolean isApprovedByFinance() {
		return isApprovedByFinance;
	}

	public void setApprovedByFinance(boolean isApprovedByFinance) {
		this.isApprovedByFinance = isApprovedByFinance;
	}

	public Date getApprovedByFinance() {
		return approvedByFinance;
	}

	public void setApprovedByFinance(Date approvedByFinance) {
		this.approvedByFinance = approvedByFinance;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}

	public boolean isWcc() {
		return wcc;
	}

	public void setWcc(boolean wcc) {
		this.wcc = wcc;
	}

	public boolean isReassign() {
		return reassign;
	}

	public void setReassign(boolean reassign) {
		this.reassign = reassign;
	}

	public String getWccId() {
		return wccId;
	}

	public void setWccId(String wccId) {
		this.wccId = wccId;
	}

	public String getWccManualId() {
		return wccManualId;
	}

	public void setWccManualId(String wccManualId) {
		this.wccManualId = wccManualId;
	}

	public String getAcknowledgedByName() {
		return acknowledgedByName;
	}

	public void setAcknowledgedByName(String acknowledgedByName) {
		this.acknowledgedByName = acknowledgedByName;
	}

	public String getAcknowledgedByPhone() {
		return acknowledgedByPhone;
	}

	public void setAcknowledgedByPhone(String acknowledgedByPhone) {
		this.acknowledgedByPhone = acknowledgedByPhone;
	}

	public boolean isFsrCreated() {
		return fsrCreated;
	}


	public void setFsrCreated(boolean fsrCreated) {
		this.fsrCreated = fsrCreated;
	}
	
	public String getPendingResource() {
		return pendingResource;
	}


	public void setPendingResource(String pendingResource) {
		this.pendingResource = pendingResource;
	}



	public boolean isSchFill() {
		return isSchFill;
	}


	public void setSchFill(boolean isSchFill) {
		this.isSchFill = isSchFill;
	}



	@Transient transient BtServiceAppointment old;

   
    @Override
    @PostLoad
	public void old() {
		old=(BtServiceAppointment) SerializationUtils.clone(this);
		
	}

	@Override
	public BtServiceAppointment gOld() {
		return old;
	}

	@Override
	public Long getId() {
		
		return this.serviceAppointmentId;
	}

	@Override
	public void setId(Long id) {
		this.serviceAppointmentId=id;
		
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
		return this.manualId;
	}

	@Override
	public void setManualId(String manualId) {
		this.manualId=manualId;
		
	}
	
	public String toString() {
		return ""+serviceAppointmentId;
	}


	public String getEsignature() {
		return esignature;
	}


	public void setEsignature(String esignature) {
		this.esignature = esignature;
	}


	public String getVfCode() {
		return vfCode;
	}


	public void setVfCode(String vfCode) {
		this.vfCode = vfCode;
	} 

	
}

