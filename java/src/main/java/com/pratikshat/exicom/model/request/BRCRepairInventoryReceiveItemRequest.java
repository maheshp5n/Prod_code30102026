package com.pratikshat.exicom.model.request;

public class BRCRepairInventoryReceiveItemRequest {
	long repairId;
	long receiveDetailsId;
	long serviceResourceId;
	int brcRepariType;
	java.util.Date engineerIssuedDate;
	java.util.Date repariDate;
	String battryVoltageAfterRepair;
	String battryAHAfterRepair;
	String battryEngCorrectiveAction;
	String battryEngRemarks;
	int battryBalanceAtId;
	int brcBERRemarksId;
	String materialSortage;
	String cellBulged;
	java.util.Set<BRCRepiarResolutionMethodChoosenRequest> methodChoosen;
	
	/**
	 * @return the repairId
	 */
	public long getRepairId() {
		return repairId;
	}
	/**
	 * @return the receiveDetailsId
	 */
	public long getReceiveDetailsId() {
		return receiveDetailsId;
	}
	/**
	 * @return the serviceResourceId
	 */
	public long getServiceResourceId() {
		return serviceResourceId;
	}
	/**
	 * @return the brcRepariType
	 */
	public int getBrcRepariType() {
		return brcRepariType;
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
	 * @return the brcBERRemarksId
	 */
	public int getBrcBERRemarksId() {
		return brcBERRemarksId;
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
	 * @return the methodChoosen
	 */
	public java.util.Set<BRCRepiarResolutionMethodChoosenRequest> getMethodChoosen() {
		return methodChoosen;
	}
	/**
	 * @param repairId the repairId to set
	 */
	public void setRepairId(long repairId) {
		this.repairId = repairId;
	}
	/**
	 * @param receiveDetailsId the receiveDetailsId to set
	 */
	public void setReceiveDetailsId(long receiveDetailsId) {
		this.receiveDetailsId = receiveDetailsId;
	}
	/**
	 * @param serviceResourceId the serviceResourceId to set
	 */
	public void setServiceResourceId(long serviceResourceId) {
		this.serviceResourceId = serviceResourceId;
	}
	/**
	 * @param brcRepariType the brcRepariType to set
	 */
	public void setBrcRepariType(int brcRepariType) {
		this.brcRepariType = brcRepariType;
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
	 * @param brcBERRemarksId the brcBERRemarksId to set
	 */
	public void setBrcBERRemarksId(int brcBERRemarksId) {
		this.brcBERRemarksId = brcBERRemarksId;
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
	 * @param methodChoosen the methodChoosen to set
	 */
	public void setMethodChoosen(java.util.Set<BRCRepiarResolutionMethodChoosenRequest> methodChoosen) {
		this.methodChoosen = methodChoosen;
	}
	
}
