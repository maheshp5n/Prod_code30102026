package com.pratikshat.exicom.model.request;

public class BRCRepairInventoryReceiveRequest {
	long receivingId;
	long caseDetailsId;
	String assetsSerialNumber;
	String invoiceNumber;
	String productPartCode;
	long legalEntityId;
	long siteId;
	long contactId;
	java.util.Date invoiceDate;
	String alterSerialNumber;
	String challanNumber;
	java.util.Date rjilChallanDate;
	String locationStateOrCircle;
	int packingConditionId;
	java.util.Date receivedDate;
	String physicalDamageIfAny;
	int faultObservedId;
	String batteryVoltageBefoureRepair;
	String remarks;
	int brcRepariStatus;
	
	
	/**
	 * @return the brcRepariStatus
	 */
	public int getBrcRepariStatus() {
		return brcRepariStatus;
	}
	/**
	 * @param brcRepariStatus the brcRepariStatus to set
	 */
	public void setBrcRepariStatus(int brcRepariStatus) {
		this.brcRepariStatus = brcRepariStatus;
	}
	/**
	 * @return the receivingId
	 */
	public long getReceivingId() {
		return receivingId;
	}
	/**
	 * @return the caseDetailsId
	 */
	public long getCaseDetailsId() {
		return caseDetailsId;
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
	 * @return the legalEntityId
	 */
	public long getLegalEntityId() {
		return legalEntityId;
	}
	
	/**
	 * @return the contactId
	 */
	public long getContactId() {
		return contactId;
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
	 * @param receivingId the receivingId to set
	 */
	public void setReceivingId(long receivingId) {
		this.receivingId = receivingId;
	}
	/**
	 * @param caseDetailsId the caseDetailsId to set
	 */
	public void setCaseDetailsId(long caseDetailsId) {
		this.caseDetailsId = caseDetailsId;
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
	 * @param legalEntityId the legalEntityId to set
	 */
	public void setLegalEntityId(long legalEntityId) {
		this.legalEntityId = legalEntityId;
	}
	
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(long contactId) {
		this.contactId = contactId;
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
	 * @return the siteId
	 */
	public long getSiteId() {
		return siteId;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	
	
}
