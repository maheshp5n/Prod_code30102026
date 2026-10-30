package com.pratikshat.exicom.model.response;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BRCRepiarResolutionMethod;
import com.pratikshat.exicom.entity.BRCRepiarResolutionMethodChoosen;

public class BRCInventoryReceiveResponse {
	long receivingId;
	String receivingName;
	long caseId;
	String caseDetailsManualId;
	String assetsSerialNumber;
	String invoiceNumber;
	String productPartCode;
	String productDescription;
	long legalEntityId;
	String legalEntityName;
	long siteId;
	String siteManualId;
	String siteName;
	long contactId;
	String contactFirstName;
	String contactLastName;
	String contactmiddleName;
	String contactEmailid;
	java.util.Date invoiceDate;
	String alterSerialNumber;
	String challanNumber;
	java.util.Date rjilChallanDate;
	String locationStateOrCircle;
	int packingConditionId;
	String packingConditionName;
	java.util.Date receivedDate;
	String physicalDamageIfAny;
	int faultObservedId;
	String faultObservedName;
	String batteryVoltageBefoureRepair;
	String remarks;
	String ownerDetailsEmailId;
	String ownerDetailsFirstName;
	String ownerDetailsLastName;
	String ownerDetailsMiddleName;
	String createdByEmailId;
	String createdByFirstName;
	String createdByLastName;
	String createdByMiddleName;
	String modifiedByEmailid;
	String modifiedByFirstName;
	String modifiedByLastName;
	String modifiedByMiddleName;
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	
	long repairId;
	long engineerId;
	long engineerContactId;
	String engineerName;
	int repairTypeId;
	String repairTypeName;
	java.util.Date engineerIssuedDate;
	java.util.Date repariDate;
	String battryVoltageAfterRepair;
	String battryAHAfterRepair;
	String battryEngCorrectiveAction;
	String battryEngRemarks;
	int battryBalanceAtId;
	String battryBalanceAtName;
	int brcBERRemarksId;
	String brcBERRemarksName;
	String materialSortage;
	String cellBulged;
	
	long dispatchId;
	String podNumber;
	String dispatchMode;
	String dispatcherName;
	String sapDeleveryChallanNumber;
	java.util.Date dispatchDate;
	String dispatchCreatedbyEmailId;
	String dispatchCreatedByFirstName;
	String dispatchCreatedByMiddleName;
	String dispatchCreatedByLastName;
	java.util.Date dispatchCreatedDate;
	String dispatchModifieByEmailId;
	String dispatchModifiedByFirstName;
	String dispatchModifiedByMiddleName;
	String dispatchModifiedByLastName;
	java.util.Date dispatchModifiedDate;
	
	String dispatchShipmentNo;
	String dispatchRemark;	
	
	long brcRepairStatusId;
	String brcRepairStatusName;
	
	String warehouse;
	String customerAccountId;
	String customerAccountName;
	boolean warrantyStatus;
	String complaintStatus;
	String sla;
	Set<BRCRepiarResolutionMethodChoosenResponse> brcRepairResolutionMethod;
	
	public BRCInventoryReceiveResponse() {}
	public BRCInventoryReceiveResponse(com.pratikshat.exicom.entity.BRCRepairInventoryReceive receive) {
		this.insertReceiveData(receive);
		if(receive.getRepariItem()!=null) {
			this.receiveItem(receive.getRepariItem());
			if(receive.getRepariItem().getDispatchDetails()!=null) {
				insertDispatchDetails(receive.getRepariItem().getDispatchDetails());
			}
		}
		
	}
	public BRCInventoryReceiveResponse(com.pratikshat.exicom.entity.BRCRepairInventoryReceiveRepairItem item) {
		this.receiveItem(item);
		if(item.getReceiveDetails()!=null) {
			this.insertReceiveData(item.getReceiveDetails());
		}
		if(item.getDispatchDetails()!=null) {
			insertDispatchDetails(item.getDispatchDetails());
		}
	}
	public BRCInventoryReceiveResponse(com.pratikshat.exicom.entity.BRCRepairInventoryDispatch dispatch) {
		insertDispatchDetails(dispatch);
		if(dispatch.getRepariItem()!=null) {
			receiveItem(dispatch.getRepariItem());
			if(dispatch.getRepariItem().getReceiveDetails()!=null) {
				insertReceiveData(dispatch.getRepariItem().getReceiveDetails());
			}
		}
		
	}
	private void insertDispatchDetails(com.pratikshat.exicom.entity.BRCRepairInventoryDispatch dispatch) {
		this.dispatchId=dispatch.getDispatchId();
		this.podNumber=dispatch.getPodNumber();
		this.dispatchMode=dispatch.getDispatchMode();
		this.dispatcherName=dispatch.getDispatcherName();
		this.sapDeleveryChallanNumber=dispatch.getSapDeleveryChallanNumber();
		this.dispatchDate=dispatch.getDispatchDate();
		this.dispatchShipmentNo=dispatch.getDispatchShippmentNo();
		this.dispatchRemark=dispatch.getDispatchRemark();
		if(dispatch.getCreatedBy()!=null) {
			this.dispatchCreatedbyEmailId=dispatch.getCreatedBy().getEmailId();
			this.dispatchCreatedByFirstName=dispatch.getCreatedBy().getFirstName();
			this.dispatchCreatedByMiddleName=dispatch.getCreatedBy().getMiddleName();
			this.dispatchCreatedByLastName=dispatch.getCreatedBy().getLastName();
		}
		if(dispatch.getModifiedBy()!=null) {
			this.dispatchModifieByEmailId=dispatch.getModifiedBy().getEmailId();
			this.dispatchModifiedByFirstName=dispatch.getModifiedBy().getFirstName();
			this.dispatchModifiedByMiddleName=dispatch.getModifiedBy().getMiddleName();
			this.dispatchModifiedByLastName=dispatch.getModifiedBy().getLastName();
		}
		this.dispatchCreatedDate=dispatch.getCreatedDate();
		this.dispatchModifiedDate=dispatch.getModifiedDate();
	}
	private void insertReceiveData(com.pratikshat.exicom.entity.BRCRepairInventoryReceive receive) {
		this.receivingId=receive.getReceivingId();
		this.receivingName=receive.getReceivingName();
		this.warehouse = receive.getLocationStateOrCircle();
		if(receive.getCaseDetails()!=null) {
			this.caseId=receive.getCaseDetails().getCaseId();
			this.caseDetailsManualId=receive.getCaseDetails().getManualId();
			if(receive.getCaseDetails().getStatus()!=null) {
				this.complaintStatus = receive.getCaseDetails().getStatus().getCaseStatusName();	
			}
		}
		if(receive.getAssets()!=null) {
			this.assetsSerialNumber=receive.getAssets().getSerialNumber();
			
			if(receive.getAssets().getWarrantyExpiryDate()!=null) {
				Calendar cl=Calendar.getInstance();
				Date currentDate = cl.getTime();
				if(receive.getAssets().getWarrantyExpiryDate().getTime()>currentDate.getTime()) {
					this.warrantyStatus = true;
				}
			}
			
			if(receive.getAssets().getAccount()!=null) {
				this.customerAccountId = receive.getAssets().getAccount().getSapCustomerNumber();
				this.customerAccountName = receive.getAssets().getAccount().getName();
			}
			
		}
		this.invoiceNumber=receive.getInvoiceNumber();
		if(receive.getProduct()!=null) {
			this.productPartCode=receive.getProduct().getProductPartCode();
			this.productDescription=receive.getProduct().getPdoductDescription();
		}
		if(receive.getLegalEntity()!=null) {
			this.legalEntityId=receive.getLegalEntity().getLegalEntityId();
			this.legalEntityName=receive.getLegalEntity().getLegalEntityName();
		}
		if(receive.getSite()!=null) {
			this.siteId=receive.getSite().getSiteId();
			this.siteManualId=receive.getSite().getManualSiteId();
			this.siteName=receive.getSite().getSiteName();
			
		}
		if(receive.getContact()!=null) {
			this.contactId=receive.getContact().getContactId();
			this.contactFirstName=receive.getContact().getFirstName();
			this.contactLastName=receive.getContact().getLastName();
			this.contactmiddleName=receive.getContact().getMiddleName();
			this.contactEmailid=receive.getContact().getEmailId();
		}
		this.invoiceDate=receive.getInvoiceDate();
		this.alterSerialNumber=receive.getAlterSerialNumber();
		this.challanNumber=receive.getChallanNumber();
		this.rjilChallanDate=receive.getRjilChallanDate();
		this.locationStateOrCircle=receive.getLocationStateOrCircle();
		if(receive.getPackingCondition()!=null) {
			this.packingConditionId=receive.getPackingCondition().getId();
			this.packingConditionName=receive.getPackingCondition().getName();
		}
		this.receivedDate=receive.getReceivedDate();
		this.physicalDamageIfAny=receive.getPhysicalDamageIfAny();
		if(receive.getFaultObserved()!=null) {
			this.faultObservedId=receive.getFaultObserved().getId();
			this.faultObservedName=receive.getFaultObserved().getFaultObserved();
		}
		this.batteryVoltageBefoureRepair=receive.getBatteryVoltageBefoureRepair();
		this.remarks=receive.getRemarks();
		if(receive.getOwnerDetails()!=null) {
			this.ownerDetailsEmailId=receive.getOwnerDetails().getEmailId();
			this.ownerDetailsFirstName=receive.getOwnerDetails().getFirstName();
			this.ownerDetailsMiddleName=receive.getOwnerDetails().getMiddleName();
			this.ownerDetailsLastName=receive.getOwnerDetails().getLastName();
			
		}
		if(receive.getCreatedBy()!=null) {
			this.createdByEmailId=receive.getCreatedBy().getEmailId();
			this.createdByFirstName=receive.getCreatedBy().getFirstName();
			this.createdByMiddleName=receive.getCreatedBy().getMiddleName();
			this.createdByLastName=receive.getCreatedBy().getLastName();
		}
		if(receive.getModifiedBy()!=null) {
			this.modifiedByEmailid=receive.getModifiedBy().getEmailId();
			this.modifiedByFirstName=receive.getModifiedBy().getFirstName();
			this.modifiedByMiddleName=receive.getModifiedBy().getMiddleName();
			this.modifiedByLastName=receive.getModifiedBy().getLastName();
		}
		if(receive.getBrcRepariStatus()!=null) {
			this.brcRepairStatusId = receive.getBrcRepariStatus().getStatusId();
			this.brcRepairStatusName = receive.getBrcRepariStatus().getStatusName();
		}
		this.createdDate=receive.getCreatedDate();
		this.modifiedDate=receive.getModifiedDate();
	}
	private void receiveItem(com.pratikshat.exicom.entity.BRCRepairInventoryReceiveRepairItem item) {
		
		this.repairId=item.getRepairId();
		if(item.getEngineer()!=null) {
			this.engineerId=item.getEngineer().getServiceResourceId();
			if(item.getEngineer().getContact()!=null) {
				this.engineerContactId=item.getEngineer().getContact().getContactId();
			}
			this.engineerName=item.getEngineer().getName();
			
		}
		if(item.getType()!=null) {
			this.repairTypeId=item.getType().getTypeId();
			this.repairTypeName=item.getType().getTypeName();
			if(item.getReceiveDetails()!=null) {
				if(!item.getType().getTypeName().equalsIgnoreCase("Under Repair")) {
					this.sla = MiscellaneousUtils.slaCalculation(item.getReceiveDetails().getCreatedDate(), item.getRepariDate());
				}else {
					this.sla = MiscellaneousUtils.slaCalculation(item.getReceiveDetails().getCreatedDate(), new Date());
				}	
			}	
		}
		this.engineerIssuedDate=item.getEngineerIssuedDate();
		this.repariDate=item.getRepariDate();
		this.battryVoltageAfterRepair=item.getBattryVoltageAfterRepair();
		this.battryAHAfterRepair=item.getBattryAHAfterRepair();
		this.battryEngCorrectiveAction=item.getBattryEngCorrectiveAction();
		this.battryEngRemarks=item.getBattryEngRemarks();
		if(item.getBattryBalanceAt()!=null) {
			this.battryBalanceAtId=item.getBattryBalanceAt().getBalancedAtId();
			this.battryBalanceAtName=item.getBattryBalanceAt().getBalancedAtName();
		}
		if(item.getBrcBERRemarks()!=null) {
			this.brcBERRemarksId=item.getBrcBERRemarks().getBerRemarkId();
			this.brcBERRemarksName=item.getBrcBERRemarks().getBerRemarkName();
		}
		this.materialSortage=item.getMaterialSortage();
		this.cellBulged=item.getCellBulged();
	}
	/**
	 * @return the receivingId
	 */
	public long getReceivingId() {
		return receivingId;
	}
	/**
	 * @return the receivingName
	 */
	public String getReceivingName() {
		return receivingName;
	}
	/**
	 * @return the caseId
	 */
	public long getCaseId() {
		return caseId;
	}
	/**
	 * @return the caseDetailsManualId
	 */
	public String getCaseDetailsManualId() {
		return caseDetailsManualId;
	}
	/**
	 * @return the assetsSerialNumber
	 */
	public String getAssetsSerialNumber() {
		return assetsSerialNumber;
	}
	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	/**
	 * @return the productPartCode
	 */
	public String getProductPartCode() {
		return productPartCode;
	}
	/**
	 * @return the productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}
	/**
	 * @return the legalEntityId
	 */
	public long getLegalEntityId() {
		return legalEntityId;
	}
	/**
	 * @return the legalEntityName
	 */
	public String getLegalEntityName() {
		return legalEntityName;
	}
	/**
	 * @return the siteId
	 */
	public long getSiteId() {
		return siteId;
	}
	/**
	 * @return the siteManualId
	 */
	public String getSiteManualId() {
		return siteManualId;
	}
	/**
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}
	/**
	 * @return the contactId
	 */
	public long getContactId() {
		return contactId;
	}
	/**
	 * @return the contactFirstName
	 */
	public String getContactFirstName() {
		return contactFirstName;
	}
	/**
	 * @return the contactLastName
	 */
	public String getContactLastName() {
		return contactLastName;
	}
	/**
	 * @return the contactmiddleName
	 */
	public String getContactmiddleName() {
		return contactmiddleName;
	}
	/**
	 * @return the contactEmailid
	 */
	public String getContactEmailid() {
		return contactEmailid;
	}
	/**
	 * @return the invoiceDate
	 */
	public java.util.Date getInvoiceDate() {
		return invoiceDate;
	}
	/**
	 * @return the alterSerialNumber
	 */
	public String getAlterSerialNumber() {
		return alterSerialNumber;
	}
	/**
	 * @return the challanNumber
	 */
	public String getChallanNumber() {
		return challanNumber;
	}
	/**
	 * @return the rjilChallanDate
	 */
	public java.util.Date getRjilChallanDate() {
		return rjilChallanDate;
	}
	/**
	 * @return the locationStateOrCircle
	 */
	public String getLocationStateOrCircle() {
		return locationStateOrCircle;
	}
	/**
	 * @return the packingConditionId
	 */
	public int getPackingConditionId() {
		return packingConditionId;
	}
	/**
	 * @return the packingConditionName
	 */
	public String getPackingConditionName() {
		return packingConditionName;
	}
	/**
	 * @return the receivedDate
	 */
	public java.util.Date getReceivedDate() {
		return receivedDate;
	}
	/**
	 * @return the physicalDamageIfAny
	 */
	public String getPhysicalDamageIfAny() {
		return physicalDamageIfAny;
	}
	/**
	 * @return the faultObservedId
	 */
	public int getFaultObservedId() {
		return faultObservedId;
	}
	/**
	 * @return the faultObservedName
	 */
	public String getFaultObservedName() {
		return faultObservedName;
	}
	/**
	 * @return the batteryVoltageBefoureRepair
	 */
	public String getBatteryVoltageBefoureRepair() {
		return batteryVoltageBefoureRepair;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @return the ownerDetailsEmailId
	 */
	public String getOwnerDetailsEmailId() {
		return ownerDetailsEmailId;
	}
	/**
	 * @return the ownerDetailsFirstName
	 */
	public String getOwnerDetailsFirstName() {
		return ownerDetailsFirstName;
	}
	/**
	 * @return the ownerDetailsLastName
	 */
	public String getOwnerDetailsLastName() {
		return ownerDetailsLastName;
	}
	/**
	 * @return the ownerDetailsMiddleName
	 */
	public String getOwnerDetailsMiddleName() {
		return ownerDetailsMiddleName;
	}
	/**
	 * @return the createdByEmailId
	 */
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	/**
	 * @return the createdByFirstName
	 */
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	/**
	 * @return the createdByLastName
	 */
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	/**
	 * @return the createdByMiddleName
	 */
	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}
	/**
	 * @return the modifiedByEmailid
	 */
	public String getModifiedByEmailid() {
		return modifiedByEmailid;
	}
	/**
	 * @return the modifiedByFirstName
	 */
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	/**
	 * @return the modifiedByLastName
	 */
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	/**
	 * @return the modifiedByMiddleName
	 */
	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}
	/**
	 * @return the createdDate
	 */
	public java.util.Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the modifiedDate
	 */
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param receivingId the receivingId to set
	 */
	public void setReceivingId(long receivingId) {
		this.receivingId = receivingId;
	}
	/**
	 * @param receivingName the receivingName to set
	 */
	public void setReceivingName(String receivingName) {
		this.receivingName = receivingName;
	}
	/**
	 * @param caseId the caseId to set
	 */
	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}
	/**
	 * @param caseDetailsManualId the caseDetailsManualId to set
	 */
	public void setCaseDetailsManualId(String caseDetailsManualId) {
		this.caseDetailsManualId = caseDetailsManualId;
	}
	/**
	 * @param assetsSerialNumber the assetsSerialNumber to set
	 */
	public void setAssetsSerialNumber(String assetsSerialNumber) {
		this.assetsSerialNumber = assetsSerialNumber;
	}
	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	/**
	 * @param productPartCode the productPartCode to set
	 */
	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}
	/**
	 * @param productDescription the productDescription to set
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	/**
	 * @param legalEntityId the legalEntityId to set
	 */
	public void setLegalEntityId(long legalEntityId) {
		this.legalEntityId = legalEntityId;
	}
	/**
	 * @param legalEntityName the legalEntityName to set
	 */
	public void setLegalEntityName(String legalEntityName) {
		this.legalEntityName = legalEntityName;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	/**
	 * @param siteManualId the siteManualId to set
	 */
	public void setSiteManualId(String siteManualId) {
		this.siteManualId = siteManualId;
	}
	/**
	 * @param siteName the siteName to set
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	/**
	 * @param contactFirstName the contactFirstName to set
	 */
	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}
	/**
	 * @param contactLastName the contactLastName to set
	 */
	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}
	/**
	 * @param contactmiddleName the contactmiddleName to set
	 */
	public void setContactmiddleName(String contactmiddleName) {
		this.contactmiddleName = contactmiddleName;
	}
	/**
	 * @param contactEmailid the contactEmailid to set
	 */
	public void setContactEmailid(String contactEmailid) {
		this.contactEmailid = contactEmailid;
	}
	/**
	 * @param invoiceDate the invoiceDate to set
	 */
	public void setInvoiceDate(java.util.Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	/**
	 * @param alterSerialNumber the alterSerialNumber to set
	 */
	public void setAlterSerialNumber(String alterSerialNumber) {
		this.alterSerialNumber = alterSerialNumber;
	}
	/**
	 * @param challanNumber the challanNumber to set
	 */
	public void setChallanNumber(String challanNumber) {
		this.challanNumber = challanNumber;
	}
	/**
	 * @param rjilChallanDate the rjilChallanDate to set
	 */
	public void setRjilChallanDate(java.util.Date rjilChallanDate) {
		this.rjilChallanDate = rjilChallanDate;
	}
	/**
	 * @param locationStateOrCircle the locationStateOrCircle to set
	 */
	public void setLocationStateOrCircle(String locationStateOrCircle) {
		this.locationStateOrCircle = locationStateOrCircle;
	}
	/**
	 * @param packingConditionId the packingConditionId to set
	 */
	public void setPackingConditionId(int packingConditionId) {
		this.packingConditionId = packingConditionId;
	}
	/**
	 * @param packingConditionName the packingConditionName to set
	 */
	public void setPackingConditionName(String packingConditionName) {
		this.packingConditionName = packingConditionName;
	}
	/**
	 * @param receivedDate the receivedDate to set
	 */
	public void setReceivedDate(java.util.Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	/**
	 * @param physicalDamageIfAny the physicalDamageIfAny to set
	 */
	public void setPhysicalDamageIfAny(String physicalDamageIfAny) {
		this.physicalDamageIfAny = physicalDamageIfAny;
	}
	/**
	 * @param faultObservedId the faultObservedId to set
	 */
	public void setFaultObservedId(int faultObservedId) {
		this.faultObservedId = faultObservedId;
	}
	/**
	 * @param faultObservedName the faultObservedName to set
	 */
	public void setFaultObservedName(String faultObservedName) {
		this.faultObservedName = faultObservedName;
	}
	/**
	 * @param batteryVoltageBefoureRepair the batteryVoltageBefoureRepair to set
	 */
	public void setBatteryVoltageBefoureRepair(String batteryVoltageBefoureRepair) {
		this.batteryVoltageBefoureRepair = batteryVoltageBefoureRepair;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @param ownerDetailsEmailId the ownerDetailsEmailId to set
	 */
	public void setOwnerDetailsEmailId(String ownerDetailsEmailId) {
		this.ownerDetailsEmailId = ownerDetailsEmailId;
	}
	/**
	 * @param ownerDetailsFirstName the ownerDetailsFirstName to set
	 */
	public void setOwnerDetailsFirstName(String ownerDetailsFirstName) {
		this.ownerDetailsFirstName = ownerDetailsFirstName;
	}
	/**
	 * @param ownerDetailsLastName the ownerDetailsLastName to set
	 */
	public void setOwnerDetailsLastName(String ownerDetailsLastName) {
		this.ownerDetailsLastName = ownerDetailsLastName;
	}
	/**
	 * @param ownerDetailsMiddleName the ownerDetailsMiddleName to set
	 */
	public void setOwnerDetailsMiddleName(String ownerDetailsMiddleName) {
		this.ownerDetailsMiddleName = ownerDetailsMiddleName;
	}
	/**
	 * @param createdByEmailId the createdByEmailId to set
	 */
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	/**
	 * @param createdByFirstName the createdByFirstName to set
	 */
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	/**
	 * @param createdByLastName the createdByLastName to set
	 */
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	/**
	 * @param createdByMiddleName the createdByMiddleName to set
	 */
	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}
	/**
	 * @param modifiedByEmailid the modifiedByEmailid to set
	 */
	public void setModifiedByEmailid(String modifiedByEmailid) {
		this.modifiedByEmailid = modifiedByEmailid;
	}
	/**
	 * @param modifiedByFirstName the modifiedByFirstName to set
	 */
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	/**
	 * @param modifiedByLastName the modifiedByLastName to set
	 */
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	/**
	 * @param modifiedByMiddleName the modifiedByMiddleName to set
	 */
	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the repairId
	 */
	public long getRepairId() {
		return repairId;
	}
	/**
	 * @return the engineerId
	 */
	public long getEngineerId() {
		return engineerId;
	}
	/**
	 * @return the engineerContactId
	 */
	public long getEngineerContactId() {
		return engineerContactId;
	}
	/**
	 * @return the engineerName
	 */
	public String getEngineerName() {
		return engineerName;
	}
	/**
	 * @return the repairTypeId
	 */
	public int getRepairTypeId() {
		return repairTypeId;
	}
	/**
	 * @return the repairTypeName
	 */
	public String getRepairTypeName() {
		return repairTypeName;
	}
	/**
	 * @return the engineerIssuedDate
	 */
	public java.util.Date getEngineerIssuedDate() {
		return engineerIssuedDate;
	}
	/**
	 * @return the repariDate
	 */
	public java.util.Date getRepariDate() {
		return repariDate;
	}
	/**
	 * @return the battryVoltageAfterRepair
	 */
	public String getBattryVoltageAfterRepair() {
		return battryVoltageAfterRepair;
	}
	/**
	 * @return the battryAHAfterRepair
	 */
	public String getBattryAHAfterRepair() {
		return battryAHAfterRepair;
	}
	/**
	 * @return the battryEngCorrectiveAction
	 */
	public String getBattryEngCorrectiveAction() {
		return battryEngCorrectiveAction;
	}
	/**
	 * @return the battryEngRemarks
	 */
	public String getBattryEngRemarks() {
		return battryEngRemarks;
	}
	/**
	 * @return the battryBalanceAtId
	 */
	public int getBattryBalanceAtId() {
		return battryBalanceAtId;
	}
	/**
	 * @return the battryBalanceAtName
	 */
	public String getBattryBalanceAtName() {
		return battryBalanceAtName;
	}
	/**
	 * @return the brcBERRemarksId
	 */
	public int getBrcBERRemarksId() {
		return brcBERRemarksId;
	}
	/**
	 * @return the brcBERRemarksName
	 */
	public String getBrcBERRemarksName() {
		return brcBERRemarksName;
	}
	/**
	 * @return the materialSortage
	 */
	public String getMaterialSortage() {
		return materialSortage;
	}
	/**
	 * @return the cellBulged
	 */
	public String getCellBulged() {
		return cellBulged;
	}
	/**
	 * @param repairId the repairId to set
	 */
	public void setRepairId(long repairId) {
		this.repairId = repairId;
	}
	/**
	 * @param engineerId the engineerId to set
	 */
	public void setEngineerId(long engineerId) {
		this.engineerId = engineerId;
	}
	/**
	 * @param engineerContactId the engineerContactId to set
	 */
	public void setEngineerContactId(long engineerContactId) {
		this.engineerContactId = engineerContactId;
	}
	/**
	 * @param engineerName the engineerName to set
	 */
	public void setEngineerName(String engineerName) {
		this.engineerName = engineerName;
	}
	/**
	 * @param repairTypeId the repairTypeId to set
	 */
	public void setRepairTypeId(int repairTypeId) {
		this.repairTypeId = repairTypeId;
	}
	/**
	 * @param repairTypeName the repairTypeName to set
	 */
	public void setRepairTypeName(String repairTypeName) {
		this.repairTypeName = repairTypeName;
	}
	/**
	 * @param engineerIssuedDate the engineerIssuedDate to set
	 */
	public void setEngineerIssuedDate(java.util.Date engineerIssuedDate) {
		this.engineerIssuedDate = engineerIssuedDate;
	}
	/**
	 * @param repariDate the repariDate to set
	 */
	public void setRepariDate(java.util.Date repariDate) {
		this.repariDate = repariDate;
	}
	/**
	 * @param battryVoltageAfterRepair the battryVoltageAfterRepair to set
	 */
	public void setBattryVoltageAfterRepair(String battryVoltageAfterRepair) {
		this.battryVoltageAfterRepair = battryVoltageAfterRepair;
	}
	/**
	 * @param battryAHAfterRepair the battryAHAfterRepair to set
	 */
	public void setBattryAHAfterRepair(String battryAHAfterRepair) {
		this.battryAHAfterRepair = battryAHAfterRepair;
	}
	/**
	 * @param battryEngCorrectiveAction the battryEngCorrectiveAction to set
	 */
	public void setBattryEngCorrectiveAction(String battryEngCorrectiveAction) {
		this.battryEngCorrectiveAction = battryEngCorrectiveAction;
	}
	/**
	 * @param battryEngRemarks the battryEngRemarks to set
	 */
	public void setBattryEngRemarks(String battryEngRemarks) {
		this.battryEngRemarks = battryEngRemarks;
	}
	/**
	 * @param battryBalanceAtId the battryBalanceAtId to set
	 */
	public void setBattryBalanceAtId(int battryBalanceAtId) {
		this.battryBalanceAtId = battryBalanceAtId;
	}
	/**
	 * @param battryBalanceAtName the battryBalanceAtName to set
	 */
	public void setBattryBalanceAtName(String battryBalanceAtName) {
		this.battryBalanceAtName = battryBalanceAtName;
	}
	/**
	 * @param brcBERRemarksId the brcBERRemarksId to set
	 */
	public void setBrcBERRemarksId(int brcBERRemarksId) {
		this.brcBERRemarksId = brcBERRemarksId;
	}
	/**
	 * @param brcBERRemarksName the brcBERRemarksName to set
	 */
	public void setBrcBERRemarksName(String brcBERRemarksName) {
		this.brcBERRemarksName = brcBERRemarksName;
	}
	/**
	 * @param materialSortage the materialSortage to set
	 */
	public void setMaterialSortage(String materialSortage) {
		this.materialSortage = materialSortage;
	}
	/**
	 * @param cellBulged the cellBulged to set
	 */
	public void setCellBulged(String cellBulged) {
		this.cellBulged = cellBulged;
	}
	public long getDispatchId() {
		return dispatchId;
	}
	public void setDispatchId(long dispatchId) {
		this.dispatchId = dispatchId;
	}
	public String getPodNumber() {
		return podNumber;
	}
	public void setPodNumber(String podNumber) {
		this.podNumber = podNumber;
	}
	public String getDispatchMode() {
		return dispatchMode;
	}
	public void setDispatchMode(String dispatchMode) {
		this.dispatchMode = dispatchMode;
	}
	public String getDispatcherName() {
		return dispatcherName;
	}
	public void setDispatcherName(String dispatcherName) {
		this.dispatcherName = dispatcherName;
	}
	public String getSapDeleveryChallanNumber() {
		return sapDeleveryChallanNumber;
	}
	public void setSapDeleveryChallanNumber(String sapDeleveryChallanNumber) {
		this.sapDeleveryChallanNumber = sapDeleveryChallanNumber;
	}
	public java.util.Date getDispatchDate() {
		return dispatchDate;
	}
	public void setDispatchDate(java.util.Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	public String getDispatchCreatedbyEmailId() {
		return dispatchCreatedbyEmailId;
	}
	public void setDispatchCreatedbyEmailId(String dispatchCreatedbyEmailId) {
		this.dispatchCreatedbyEmailId = dispatchCreatedbyEmailId;
	}
	public String getDispatchCreatedByFirstName() {
		return dispatchCreatedByFirstName;
	}
	public void setDispatchCreatedByFirstName(String dispatchCreatedByFirstName) {
		this.dispatchCreatedByFirstName = dispatchCreatedByFirstName;
	}
	public String getDispatchCreatedByLastName() {
		return dispatchCreatedByLastName;
	}
	public void setDispatchCreatedByLastName(String dispatchCreatedByLastName) {
		this.dispatchCreatedByLastName = dispatchCreatedByLastName;
	}
	public java.util.Date getDispatchCreatedDate() {
		return dispatchCreatedDate;
	}
	public void setDispatchCreatedDate(java.util.Date dispatchCreatedDate) {
		this.dispatchCreatedDate = dispatchCreatedDate;
	}
	public String getDispatchModifieByEmailId() {
		return dispatchModifieByEmailId;
	}
	public void setDispatchModifieByEmailId(String dispatchModifieByEmailId) {
		this.dispatchModifieByEmailId = dispatchModifieByEmailId;
	}
	public String getDispatchModifiedByFirstName() {
		return dispatchModifiedByFirstName;
	}
	public void setDispatchModifiedByFirstName(String dispatchModifiedByFirstName) {
		this.dispatchModifiedByFirstName = dispatchModifiedByFirstName;
	}
	public String getDispatchModifiedByLastName() {
		return dispatchModifiedByLastName;
	}
	public void setDispatchModifiedByLastName(String dispatchModifiedByLastName) {
		this.dispatchModifiedByLastName = dispatchModifiedByLastName;
	}
	public java.util.Date getDispatchModifiedDate() {
		return dispatchModifiedDate;
	}
	public void setDispatchModifiedDate(java.util.Date dispatchModifiedDate) {
		this.dispatchModifiedDate = dispatchModifiedDate;
	}
	public long getBrcRepairStatusId() {
		return brcRepairStatusId;
	}
	public void setBrcRepairStatusId(long brcRepairStatusId) {
		this.brcRepairStatusId = brcRepairStatusId;
	}
	public String getBrcRepairStatusName() {
		return brcRepairStatusName;
	}
	public void setBrcRepairStatusName(String brcRepairStatusName) {
		this.brcRepairStatusName = brcRepairStatusName;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getCustomerAccountId() {
		return customerAccountId;
	}
	public void setCustomerAccountId(String customerAccountId) {
		this.customerAccountId = customerAccountId;
	}
	public String getCustomerAccountName() {
		return customerAccountName;
	}
	public void setCustomerAccountName(String customerAccountName) {
		this.customerAccountName = customerAccountName;
	}
	public boolean isWarrantyStatus() {
		return warrantyStatus;
	}
	public void setWarrantyStatus(boolean warrantyStatus) {
		this.warrantyStatus = warrantyStatus;
	}
	public String getComplaintStatus() {
		return complaintStatus;
	}
	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}
	public String getSla() {
		return sla;
	}
	public void setSla(String sla) {
		this.sla = sla;
	}
	public Set<BRCRepiarResolutionMethodChoosenResponse> getBrcRepairResolutionMethod() {
		return brcRepairResolutionMethod;
	}
	public void setBrcRepairResolutionMethod(Set<BRCRepiarResolutionMethodChoosenResponse> brcRepairResolutionMethod) {
		this.brcRepairResolutionMethod = brcRepairResolutionMethod;
	}
	public String getDispatchCreatedByMiddleName() {
		return dispatchCreatedByMiddleName;
	}
	public void setDispatchCreatedByMiddleName(String dispatchCreatedByMiddleName) {
		this.dispatchCreatedByMiddleName = dispatchCreatedByMiddleName;
	}
	public String getDispatchModifiedByMiddleName() {
		return dispatchModifiedByMiddleName;
	}
	public void setDispatchModifiedByMiddleName(String dispatchModifiedByMiddleName) {
		this.dispatchModifiedByMiddleName = dispatchModifiedByMiddleName;
	}
	public String getDispatchShipmentNo() {
		return dispatchShipmentNo;
	}
	public void setDispatchShipmentNo(String dispatchShipmentNo) {
		this.dispatchShipmentNo = dispatchShipmentNo;
	}
	public String getDispatchRemark() {
		return dispatchRemark;
	}
	public void setDispatchRemark(String dispatchRemark) {
		this.dispatchRemark = dispatchRemark;
	}
	
	
}
