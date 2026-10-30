package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.FetchType;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BRCRepairInventoryReceiveRepairItem implements java.io.Serializable,OldPresist<BRCRepairInventoryReceiveRepairItem>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long repairId;
	@javax.persistence.OneToOne
	BRCRepairInventoryReceive receiveDetails;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	ServiceRecource engineer;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	BRCRepairType type;
	java.util.Date engineerIssuedDate;
	java.util.Date repariDate;
	String battryVoltageAfterRepair;
	String battryAHAfterRepair;
	String battryEngCorrectiveAction;
	String battryEngRemarks;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	BRCRepiarBalancedAt battryBalanceAt;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	BRCRepairBERRemark brcBERRemarks;
	String materialSortage;
	String cellBulged;
	
	@javax.persistence.OneToOne(mappedBy = "repariItem",fetch=javax.persistence.FetchType.LAZY)
	BRCRepairInventoryDispatch dispatchDetails;
	
	public BRCRepairInventoryReceiveRepairItem() {}
	public BRCRepairInventoryReceiveRepairItem(long id) {
		this.repairId=id;
	}
	
	
	/**
	 * @return the repairId
	 */
	public long getRepairId() {
		return repairId;
	}
	/**
	 * @return the receiveDetails
	 */
	public BRCRepairInventoryReceive getReceiveDetails() {
		return receiveDetails;
	}
	/**
	 * @return the engineer
	 */
	public ServiceRecource getEngineer() {
		return engineer;
	}
	/**
	 * @return the type
	 */
	public BRCRepairType getType() {
		return type;
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
	 * @return the battryBalanceAt
	 */
	public BRCRepiarBalancedAt getBattryBalanceAt() {
		return battryBalanceAt;
	}
	/**
	 * @return the brcBERRemarks
	 */
	public BRCRepairBERRemark getBrcBERRemarks() {
		return brcBERRemarks;
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
	 * @param receiveDetails the receiveDetails to set
	 */
	public void setReceiveDetails(BRCRepairInventoryReceive receiveDetails) {
		this.receiveDetails = receiveDetails;
	}
	/**
	 * @param engineer the engineer to set
	 */
	public void setEngineer(ServiceRecource engineer) {
		this.engineer = engineer;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(BRCRepairType type) {
		this.type = type;
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
	 * @param battryBalanceAt the battryBalanceAt to set
	 */
	public void setBattryBalanceAt(BRCRepiarBalancedAt battryBalanceAt) {
		this.battryBalanceAt = battryBalanceAt;
	}
	/**
	 * @param brcBERRemarks the brcBERRemarks to set
	 */
	public void setBrcBERRemarks(BRCRepairBERRemark brcBERRemarks) {
		this.brcBERRemarks = brcBERRemarks;
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
	/**
	 * @return the dispatchDetails
	 */
	public BRCRepairInventoryDispatch getDispatchDetails() {
		return dispatchDetails;
	}
	/**
	 * @param dispatchDetails the dispatchDetails to set
	 */
	public void setDispatchDetails(BRCRepairInventoryDispatch dispatchDetails) {
		this.dispatchDetails = dispatchDetails;
	}
	
	
	

	@javax.persistence.Transient transient BRCRepairInventoryReceiveRepairItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BRCRepairInventoryReceiveRepairItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BRCRepairInventoryReceiveRepairItem gOld() {
		return old;
	}
	public String toString() {
			return ""+repairId;
	}
}
