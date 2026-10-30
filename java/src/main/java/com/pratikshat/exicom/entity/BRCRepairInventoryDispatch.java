package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Column;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BRCRepairInventoryDispatch implements java.io.Serializable,OldPresist<BRCRepairInventoryDispatch>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long dispatchId;
	String podNumber;
	String dispatchMode;
	String dispatcherName;
	String sapDeleveryChallanNumber;
	java.util.Date dispatchDate;
	
	@javax.persistence.OneToOne(fetch=javax.persistence.FetchType.LAZY)
	BRCRepairInventoryReceiveRepairItem repariItem;
	
	@javax.persistence.ManyToOne
	EmployeeDetails ownerDetails;
	@javax.persistence.ManyToOne
	EmployeeDetails createdBy;
	@javax.persistence.ManyToOne
	EmployeeDetails modifiedBy;
	
	java.util.Date createdDate;
	java.util.Date modifiedDate;
	
	String dispatchShippmentNo;
	@Column(length = 500)
	String dispatchRemark;	
	
	public BRCRepairInventoryDispatch() {}
	public BRCRepairInventoryDispatch(long dispatchId) {
		this.dispatchId=dispatchId;
	}
	/**
	 * @return the dispatchId
	 */
	public long getDispatchId() {
		return dispatchId;
	}
	/**
	 * @return the podNumber
	 */
	public String getPodNumber() {
		return podNumber;
	}
	/**
	 * @return the dispatchMode
	 */
	public String getDispatchMode() {
		return dispatchMode;
	}
	/**
	 * @return the dispatcherName
	 */
	public String getDispatcherName() {
		return dispatcherName;
	}
	/**
	 * @return the sapDeleveryChallanNumber
	 */
	public String getSapDeleveryChallanNumber() {
		return sapDeleveryChallanNumber;
	}
	/**
	 * @return the dispatchDate
	 */
	public java.util.Date getDispatchDate() {
		return dispatchDate;
	}
	/**
	 * @return the ownerDetails
	 */
	public EmployeeDetails getOwnerDetails() {
		return ownerDetails;
	}
	/**
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	/**
	 * @return the modifiedBy
	 */
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
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
	 * @param dispatchId the dispatchId to set
	 */
	public void setDispatchId(long dispatchId) {
		this.dispatchId = dispatchId;
	}
	/**
	 * @param podNumber the podNumber to set
	 */
	public void setPodNumber(String podNumber) {
		this.podNumber = podNumber;
	}
	/**
	 * @param dispatchMode the dispatchMode to set
	 */
	public void setDispatchMode(String dispatchMode) {
		this.dispatchMode = dispatchMode;
	}
	/**
	 * @param dispatcherName the dispatcherName to set
	 */
	public void setDispatcherName(String dispatcherName) {
		this.dispatcherName = dispatcherName;
	}
	/**
	 * @param sapDeleveryChallanNumber the sapDeleveryChallanNumber to set
	 */
	public void setSapDeleveryChallanNumber(String sapDeleveryChallanNumber) {
		this.sapDeleveryChallanNumber = sapDeleveryChallanNumber;
	}
	/**
	 * @param dispatchDate the dispatchDate to set
	 */
	public void setDispatchDate(java.util.Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	/**
	 * @param ownerDetails the ownerDetails to set
	 */
	public void setOwnerDetails(EmployeeDetails ownerDetails) {
		this.ownerDetails = ownerDetails;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
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
	 * @return the repariItem
	 */
	public BRCRepairInventoryReceiveRepairItem getRepariItem() {
		return repariItem;
	}
	/**
	 * @param repariItem the repariItem to set
	 */
	public void setRepariItem(BRCRepairInventoryReceiveRepairItem repariItem) {
		this.repariItem = repariItem;
	}
	public String getDispatchRemark() {
		return dispatchRemark;
	}
	public void setDispatchRemark(String dispatchRemark) {
		this.dispatchRemark = dispatchRemark;
	}
	public String getDispatchShippmentNo() {
		return dispatchShippmentNo;
	}
	public void setDispatchShippmentNo(String dispatchShippmentNo) {
		this.dispatchShippmentNo = dispatchShippmentNo;
	}
	

	@javax.persistence.Transient transient BRCRepairInventoryDispatch old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BRCRepairInventoryDispatch)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BRCRepairInventoryDispatch gOld() {
		return old;
	}
	public String toString() {
			return ""+dispatchId;
	}
}
