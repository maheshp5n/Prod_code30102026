package com.pratikshat.exicom.model.request;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AssetsRequest {
	@NotNull(message="Assets serial number can not be null or empty")
	@NotEmpty(message="Assets serial number can not be null or empty")
	String serialNumber;
	@NotNull(message="Assets part number can not be null or empty")
	@NotEmpty(message="Assets part number can not be null or empty")	
	String productPardCode;
	@NotNull(message="Assets account sap number can not be null or empty")
	@NotEmpty(message="Assets account sap number can not be null or empty")
	String accountSapNumber;
	String plantCode;
	long siteId;
	String primaryAccountSapNumber;
	int assetsStatusId;
	String remarks;
	String purchaseOrderNumber;
	Date purchaseOrderDate;
	String sapDeliveryDocumentNumber;
	String sapMaterialDocumentNumber;
	Date purchaseDate;
	Date installDate;
	int warrantyFromDateOfDeliveryInMonth;
	int warrantyFromDateOfInstallationInMonth;
	String amcPurchaseOrderNumber;
	Date amcPurchaseOrderDate;
	Date amcStartDate;
	Date amcExpiryDate;
	int amcPeriod;
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @return the productPardCode
	 */
	public String getProductPardCode() {
		return productPardCode;
	}
	/**
	 * @return the accountSapNumber
	 */
	public String getAccountSapNumber() {
		return accountSapNumber;
	}
	/**
	 * @return the plantCode
	 */
	public String getPlantCode() {
		return plantCode;
	}
	/**
	 * @return the siteId
	 */
	public long getSiteId() {
		return siteId;
	}
	/**
	 * @return the primaryAccountSapNumber
	 */
	public String getPrimaryAccountSapNumber() {
		return primaryAccountSapNumber;
	}
	
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @return the purchaseOrderNumber
	 */
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}
	/**
	 * @return the purchaseOrderDate
	 */
	public Date getPurchaseOrderDate() {
		return purchaseOrderDate;
	}
	/**
	 * @return the sapDeliveryDocumentNumber
	 */
	public String getSapDeliveryDocumentNumber() {
		return sapDeliveryDocumentNumber;
	}
	/**
	 * @return the sapMaterialDocumentNumber
	 */
	public String getSapMaterialDocumentNumber() {
		return sapMaterialDocumentNumber;
	}
	/**
	 * @return the purchaseDate
	 */
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	/**
	 * @return the installDate
	 */
	public Date getInstallDate() {
		return installDate;
	}
	/**
	 * @return the warrantyFromDateOfDeliveryInMonth
	 */
	public int getWarrantyFromDateOfDeliveryInMonth() {
		return warrantyFromDateOfDeliveryInMonth;
	}
	/**
	 * @return the warrantyFromDateOfInstallationInMonth
	 */
	public int getWarrantyFromDateOfInstallationInMonth() {
		return warrantyFromDateOfInstallationInMonth;
	}
	/**
	 * @return the amcPurchaseOrderNumber
	 */
	public String getAmcPurchaseOrderNumber() {
		return amcPurchaseOrderNumber;
	}
	/**
	 * @return the amcPurchaseOrderDate
	 */
	public Date getAmcPurchaseOrderDate() {
		return amcPurchaseOrderDate;
	}
	/**
	 * @return the amcStartDate
	 */
	public Date getAmcStartDate() {
		return amcStartDate;
	}
	/**
	 * @return the amcExpiryDate
	 */
	public Date getAmcExpiryDate() {
		return amcExpiryDate;
	}
	/**
	 * @return the amcPeriod
	 */
	public int getAmcPeriod() {
		return amcPeriod;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @param productPardCode the productPardCode to set
	 */
	public void setProductPardCode(String productPardCode) {
		this.productPardCode = productPardCode;
	}
	/**
	 * @param accountSapNumber the accountSapNumber to set
	 */
	public void setAccountSapNumber(String accountSapNumber) {
		this.accountSapNumber = accountSapNumber;
	}
	/**
	 * @param plantCode the plantCode to set
	 */
	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	/**
	 * @param primaryAccountSapNumber the primaryAccountSapNumber to set
	 */
	public void setPrimaryAccountSapNumber(String primaryAccountSapNumber) {
		this.primaryAccountSapNumber = primaryAccountSapNumber;
	}
	
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @param purchaseOrderNumber the purchaseOrderNumber to set
	 */
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}
	/**
	 * @param purchaseOrderDate the purchaseOrderDate to set
	 */
	public void setPurchaseOrderDate(Date purchaseOrderDate) {
		this.purchaseOrderDate = purchaseOrderDate;
	}
	/**
	 * @param sapDeliveryDocumentNumber the sapDeliveryDocumentNumber to set
	 */
	public void setSapDeliveryDocumentNumber(String sapDeliveryDocumentNumber) {
		this.sapDeliveryDocumentNumber = sapDeliveryDocumentNumber;
	}
	/**
	 * @param sapMaterialDocumentNumber the sapMaterialDocumentNumber to set
	 */
	public void setSapMaterialDocumentNumber(String sapMaterialDocumentNumber) {
		this.sapMaterialDocumentNumber = sapMaterialDocumentNumber;
	}
	/**
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	/**
	 * @param installDate the installDate to set
	 */
	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}
	/**
	 * @param warrantyFromDateOfDeliveryInMonth the warrantyFromDateOfDeliveryInMonth to set
	 */
	public void setWarrantyFromDateOfDeliveryInMonth(int warrantyFromDateOfDeliveryInMonth) {
		this.warrantyFromDateOfDeliveryInMonth = warrantyFromDateOfDeliveryInMonth;
	}
	/**
	 * @param warrantyFromDateOfInstallationInMonth the warrantyFromDateOfInstallationInMonth to set
	 */
	public void setWarrantyFromDateOfInstallationInMonth(int warrantyFromDateOfInstallationInMonth) {
		this.warrantyFromDateOfInstallationInMonth = warrantyFromDateOfInstallationInMonth;
	}
	/**
	 * @param amcPurchaseOrderNumber the amcPurchaseOrderNumber to set
	 */
	public void setAmcPurchaseOrderNumber(String amcPurchaseOrderNumber) {
		this.amcPurchaseOrderNumber = amcPurchaseOrderNumber;
	}
	/**
	 * @param amcPurchaseOrderDate the amcPurchaseOrderDate to set
	 */
	public void setAmcPurchaseOrderDate(Date amcPurchaseOrderDate) {
		this.amcPurchaseOrderDate = amcPurchaseOrderDate;
	}
	/**
	 * @param amcStartDate the amcStartDate to set
	 */
	public void setAmcStartDate(Date amcStartDate) {
		this.amcStartDate = amcStartDate;
	}
	/**
	 * @param amcExpiryDate the amcExpiryDate to set
	 */
	public void setAmcExpiryDate(Date amcExpiryDate) {
		this.amcExpiryDate = amcExpiryDate;
	}
	/**
	 * @param amcPeriod the amcPeriod to set
	 */
	public void setAmcPeriod(int amcPeriod) {
		this.amcPeriod = amcPeriod;
	}
	public int getAssetsStatusId() {
		return assetsStatusId;
	}
	public void setAssetsStatusId(int assetsStatusId) {
		this.assetsStatusId = assetsStatusId;
	}
}
