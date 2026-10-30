package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class Assets implements java.io.Serializable,OldPresist<Assets>{
	@Id
	String serialNumber;
	@ManyToOne(fetch=FetchType.LAZY)
	Products product;
	@ManyToOne(fetch=FetchType.LAZY)
	Accounts account;
	@ManyToOne(fetch=FetchType.LAZY)
	PlantDetails plant;
	@ManyToOne(fetch=FetchType.LAZY)
	SiteDetails site;
	@ManyToOne(fetch=FetchType.LAZY)
	Accounts primaryAccount;
	
	@ManyToOne
	AssetsStatus status;
	@Column(length=500)
	String remarks;
	
	//**************Purchase Details*********//
	String purchaseOrderNumber;
	Date purchaseOrderDate;
	String sapDeliveryDocumentNumber;
	String sapMaterialDocumentNumber;
	
	//*************Warranty Details**********//
	Date purchaseDate;
	Date installDate;
	int warrantyFromDateOfDeliveryInMonth;
	int warrantyFromDateOfInstallationInMonth;
	Date warrantyExpiryDate;
	Date installExpiryDate;
	Date dateOfDeliveryExpiryDate;
	
	//************AMC Details***************//
	String amcPurchaseOrderNumber;
	String amcPurchaseOrderDate;
	Date amcStartDate;
	Date amcExpiryDate;
	int amcPeriod;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifyedBy;
	Date modifyedDate;
	boolean synced;
	//SAP ADDISNATIONAL PROPERTYES
	String salesDocNumber;
	
	//CREATED ON 11-12-2020
	String source="CRM";
	String actlSerialNumber;
	
	@Transient
	private transient Assets old;
	
	@PostLoad
	public void old() {
		old=(Assets) SerializationUtils.clone(this);
		
	}
	public Assets gOld() {
		return this.old;
	}
	
	public Assets() {}
	
	public Assets(String serialNumber) {
		this.serialNumber=serialNumber;
	}
	
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @return the product
	 */
	public Products getProduct() {
		return product;
	}
	/**
	 * @return the account
	 */
	public Accounts getAccount() {
		return account;
	}
	/**
	 * @return the plant
	 */
	public PlantDetails getPlant() {
		return plant;
	}
	/**
	 * @return the site
	 */
	public SiteDetails getSite() {
		return site;
	}
	/**
	 * @return the primaryAccount
	 */
	public Accounts getPrimaryAccount() {
		return primaryAccount;
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
	 * @return the warrantyExpiryDate
	 */
	public Date getWarrantyExpiryDate() {
		return warrantyExpiryDate;
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
	public String getAmcPurchaseOrderDate() {
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
	 * @param product the product to set
	 */
	public void setProduct(Products product) {
		this.product = product;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(Accounts account) {
		this.account = account;
	}
	/**
	 * @param plant the plant to set
	 */
	public void setPlant(PlantDetails plant) {
		this.plant = plant;
	}
	/**
	 * @param site the site to set
	 */
	public void setSite(SiteDetails site) {
		this.site = site;
	}
	/**
	 * @param primaryAccount the primaryAccount to set
	 */
	public void setPrimaryAccount(Accounts primaryAccount) {
		this.primaryAccount = primaryAccount;
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
	 * @param warrantyExpiryDate the warrantyExpiryDate to set
	 */
	public void setWarrantyExpiryDate(Date warrantyExpiryDate) {
		this.warrantyExpiryDate = warrantyExpiryDate;
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
	public void setAmcPurchaseOrderDate(String amcPurchaseOrderDate) {
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

	/**
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @return the modifyedBy
	 */
	public EmployeeDetails getModifyedBy() {
		return modifyedBy;
	}

	/**
	 * @return the modifyedDate
	 */
	public Date getModifyedDate() {
		return modifyedDate;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @param modifyedBy the modifyedBy to set
	 */
	public void setModifyedBy(EmployeeDetails modifyedBy) {
		this.modifyedBy = modifyedBy;
	}

	/**
	 * @param modifyedDate the modifyedDate to set
	 */
	public void setModifyedDate(Date modifyedDate) {
		this.modifyedDate = modifyedDate;
	}

	/**
	 * @return the synced
	 */
	public boolean isSynced() {
		return synced;
	}

	/**
	 * @param synced the synced to set
	 */
	public void setSynced(boolean synced) {
		this.synced = synced;
	}

	public AssetsStatus getStatus() {
		return status;
	}

	public void setStatus(AssetsStatus status) {
		this.status = status;
	}

	public Date getInstallExpiryDate() {
		return installExpiryDate;
	}

	public void setInstallExpiryDate(Date installExpiryDate) {
		this.installExpiryDate = installExpiryDate;
	}

	public Date getDateOfDeliveryExpiryDate() {
		return dateOfDeliveryExpiryDate;
	}

	public void setDateOfDeliveryExpiryDate(Date dateOfDeliveryExpiryDate) {
		this.dateOfDeliveryExpiryDate = dateOfDeliveryExpiryDate;
	}

	public String getSalesDocNumber() {
		return salesDocNumber;
	}

	public void setSalesDocNumber(String salesDocNumber) {
		this.salesDocNumber = salesDocNumber;
	}

	@Override
	public String toString() {
		return  serialNumber;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getActlSerialNumber() {
		return actlSerialNumber;
	}
	public void setActlSerialNumber(String actlSerialNumber) {
		this.actlSerialNumber = actlSerialNumber;
	}
	
	

}
