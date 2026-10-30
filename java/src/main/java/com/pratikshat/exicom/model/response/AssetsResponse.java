package com.pratikshat.exicom.model.response;

import java.util.Calendar;
import java.util.Date;

import com.pratikshat.exicom.entity.Assets;

public class AssetsResponse {
	String serialNumber;
	String productPartCode;
	String productDescription;
	String productSubProductId;
	String productSubProductName;
	String productFamailyId;
	String productFamailyName;
	String accountSapNumber;
	String accountName;
	String plantCode;
	String plantName;
	long siteId;
	String siteManualId;
	String siteName;
	String primaryAccountSapNumber;
	String primaryAccountName;
	String assetsStatusName;
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
	Date warrantyExpiryDate;
	Date installExpiryDate;
	Date dateOfDeliveryExpiryDate;
	String amcPurchaseOrderNumber;
	String amcPurchaseOrderDate;
	Date amcStartDate;
	Date amcExpiryDate;
	int amcPeriod;
	String createdByEmailId;
	Date createdDate;
	String modifyedByEmailId;
	Date modifyedDate;
	String createdByFirstName;
	String createdByLastName;
	String createdByMiddleName;
	String modifyedByFirstName;
	String modifyedByLastName;
	String modifyedByMiddleName;
	String circleName;
	boolean isWarrantyExist;
	boolean isAMCWarrantyExist;
	String warranty;
	String source;
	String actlSerialNumber;
	
	public AssetsResponse() {}
	public AssetsResponse(Assets assets) {
		if(assets!=null) {
			this.serialNumber=assets.getSerialNumber();
			if(assets.getProduct()!=null) {
				this.productPartCode=assets.getProduct().getProductPartCode();
				this.productDescription=assets.getProduct().getPdoductDescription();
				if(assets.getProduct().getSubProductFamily()!=null) {
					this.productSubProductId=String.valueOf(assets.getProduct().getSubProductFamily().getProductFamilySubProductId());
					this.productSubProductName=assets.getProduct().getSubProductFamily().getProductFailySubProductName();
					if(assets.getProduct().getSubProductFamily().getProductFamily()!=null) {
						this.productFamailyId=String.valueOf(assets.getProduct().getSubProductFamily().getProductFamily().getProductFamilyId());
						this.productFamailyName=assets.getProduct().getSubProductFamily().getProductFamily().getProductFamilyName();
					}
				}
			}
			if(assets.getAccount()!=null) {
				this.accountName=assets.getAccount().getName();
				this.accountSapNumber=assets.getAccount().getSapCustomerNumber();
			}
			if(assets.getPlant()!=null) {
				this.plantCode=assets.getPlant().getPlantCode();
				this.plantName=assets.getPlant().getPlantName();
			}
			if(assets.getSite()!=null) {
				this.siteId=(assets.getSite().getSiteId());
				this.siteManualId=assets.getSite().getManualSiteId();
				this.siteName=assets.getSite().getSiteName();
				this.siteManualId = assets.getSite().getManualSiteId();
				if(assets.getSite().getCircle()!=null) {
					this.circleName=assets.getSite().getCircle().getCircleName();
				}
			}
			if(assets.getPrimaryAccount()!=null) {
				this.primaryAccountName=assets.getPrimaryAccount().getName();
				this.primaryAccountSapNumber=assets.getPrimaryAccount().getSapCustomerNumber();
			}
			if(assets.getStatus()!=null) {
				this.assetsStatusId = assets.getStatus().getAssetsStatusId();
				this.assetsStatusName = assets.getStatus().getAssetsStatusName();
			}
			
			this.remarks=assets.getRemarks();
			this.purchaseOrderNumber=assets.getPurchaseOrderNumber();
			this.purchaseOrderDate=assets.getPurchaseOrderDate();
			this.sapDeliveryDocumentNumber=assets.getSapDeliveryDocumentNumber();
			this.sapMaterialDocumentNumber=assets.getSapMaterialDocumentNumber();
			
			this.purchaseDate=assets.getPurchaseDate();
			this.warrantyFromDateOfDeliveryInMonth=assets.getWarrantyFromDateOfDeliveryInMonth();
			this.dateOfDeliveryExpiryDate = assets.getDateOfDeliveryExpiryDate();
			
			if(assets.getWarrantyExpiryDate()!=null) {
				this.warrantyExpiryDate=assets.getWarrantyExpiryDate();
				Calendar cl=Calendar.getInstance();
				Date currentDate = cl.getTime();
				if(assets.getWarrantyExpiryDate().getTime()>currentDate.getTime()) {
					this.isWarrantyExist = true;
					this.warranty = "Under Warranty";
				}else {
					this.warranty = "Without Warranty/AMC";
				}
			}
			
			this.installDate=assets.getInstallDate();
			this.warrantyFromDateOfInstallationInMonth=assets.getWarrantyFromDateOfInstallationInMonth();
			this.installExpiryDate=assets.getInstallExpiryDate();
			
			
			this.amcPurchaseOrderNumber=assets.getAmcPurchaseOrderNumber();
			this.amcPurchaseOrderDate=assets.getAmcPurchaseOrderDate();
			this.amcStartDate=assets.getAmcStartDate();
			
			if(assets.getAmcExpiryDate()!=null) {
				this.amcExpiryDate=assets.getAmcExpiryDate();
				Calendar cl=Calendar.getInstance();
				Date currentDate = cl.getTime();
				if(assets.getAmcExpiryDate().getTime()>currentDate.getTime()) {
					this.isAMCWarrantyExist = true;
					this.warranty = "Under AMC";
				}else {
					this.warranty = "Without Warranty/AMC";
				}
			}
			this.amcPeriod=assets.getAmcPeriod();
			
			if(assets.getAmcExpiryDate()!=null && assets.getWarrantyExpiryDate()!=null) {
				Calendar cl=Calendar.getInstance();
				Date currentDate = cl.getTime();
				
				if(assets.getAmcExpiryDate().getTime()>currentDate.getTime() && assets.getWarrantyExpiryDate().getTime()>currentDate.getTime()) {
					this.warranty = "Under Warranty";
					
				}else if(assets.getAmcExpiryDate().getTime()<currentDate.getTime() && assets.getWarrantyExpiryDate().getTime()>currentDate.getTime()) {
					this.warranty = "Under Warranty";
					
				}else if(assets.getAmcExpiryDate().getTime()>currentDate.getTime() && assets.getWarrantyExpiryDate().getTime()<currentDate.getTime()) {
					this.warranty = "Under AMC";
				}else {
					this.warranty = "Without Warranty/AMC";
				}
			}
			
			
			
			if(assets.getCreatedBy()!=null) {
				this.createdByEmailId=assets.getCreatedBy().getEmailId();
				this.createdByFirstName=assets.getCreatedBy().getFirstName();
				this.createdByMiddleName = assets.getCreatedBy().getMiddleName();
				this.createdByLastName=assets.getCreatedBy().getLastName();
			}
			this.createdDate=assets.getCreatedDate();
			if(assets.getModifyedBy()!=null) {
				this.modifyedByEmailId=assets.getModifyedBy().getEmailId();
				this.modifyedByFirstName=assets.getModifyedBy().getFirstName();
				this.modifyedByMiddleName = assets.getModifyedBy().getMiddleName();
				this.modifyedByLastName=assets.getModifyedBy().getLastName();
			}
			this.modifyedDate=assets.getModifyedDate();
			this.source=assets.getSource();
			this.actlSerialNumber=assets.getActlSerialNumber();
		}
	}
	
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
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
	 * @return the productSubProductId
	 */
	public String getProductSubProductId() {
		return productSubProductId;
	}
	/**
	 * @return the productSubProductName
	 */
	public String getProductSubProductName() {
		return productSubProductName;
	}
	/**
	 * @return the productFamailyId
	 */
	public String getProductFamailyId() {
		return productFamailyId;
	}
	/**
	 * @return the productFamailyName
	 */
	public String getProductFamailyName() {
		return productFamailyName;
	}
	/**
	 * @return the accountSapNumber
	 */
	public String getAccountSapNumber() {
		return accountSapNumber;
	}
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @return the plantCode
	 */
	public String getPlantCode() {
		return plantCode;
	}
	/**
	 * @return the plantName
	 */
	public String getPlantName() {
		return plantName;
	}
	
	/**
	 * @return the primaryAccountSapNumber
	 */
	public String getPrimaryAccountSapNumber() {
		return primaryAccountSapNumber;
	}
	/**
	 * @return the primaryAccountName
	 */
	public String getPrimaryAccountName() {
		return primaryAccountName;
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
	 * @return the createdByEmailId
	 */
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the modifyedByEmailId
	 */
	public String getModifyedByEmailId() {
		return modifyedByEmailId;
	}
	/**
	 * @return the modifyedDate
	 */
	public Date getModifyedDate() {
		return modifyedDate;
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
	 * @return the modifyedByFirstName
	 */
	public String getModifyedByFirstName() {
		return modifyedByFirstName;
	}
	/**
	 * @return the modifyedByLastName
	 */
	public String getModifyedByLastName() {
		return modifyedByLastName;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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
	 * @param productSubProductId the productSubProductId to set
	 */
	public void setProductSubProductId(String productSubProductId) {
		this.productSubProductId = productSubProductId;
	}
	/**
	 * @param productSubProductName the productSubProductName to set
	 */
	public void setProductSubProductName(String productSubProductName) {
		this.productSubProductName = productSubProductName;
	}
	/**
	 * @param productFamailyId the productFamailyId to set
	 */
	public void setProductFamailyId(String productFamailyId) {
		this.productFamailyId = productFamailyId;
	}
	/**
	 * @param productFamailyName the productFamailyName to set
	 */
	public void setProductFamailyName(String productFamailyName) {
		this.productFamailyName = productFamailyName;
	}
	/**
	 * @param accountSapNumber the accountSapNumber to set
	 */
	public void setAccountSapNumber(String accountSapNumber) {
		this.accountSapNumber = accountSapNumber;
	}
	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * @param plantCode the plantCode to set
	 */
	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}
	/**
	 * @param plantName the plantName to set
	 */
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	
	/**
	 * @param primaryAccountSapNumber the primaryAccountSapNumber to set
	 */
	public void setPrimaryAccountSapNumber(String primaryAccountSapNumber) {
		this.primaryAccountSapNumber = primaryAccountSapNumber;
	}
	/**
	 * @param primaryAccountName the primaryAccountName to set
	 */
	public void setPrimaryAccountName(String primaryAccountName) {
		this.primaryAccountName = primaryAccountName;
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
	 * @param createdByEmailId the createdByEmailId to set
	 */
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param modifyedByEmailId the modifyedByEmailId to set
	 */
	public void setModifyedByEmailId(String modifyedByEmailId) {
		this.modifyedByEmailId = modifyedByEmailId;
	}
	/**
	 * @param modifyedDate the modifyedDate to set
	 */
	public void setModifyedDate(Date modifyedDate) {
		this.modifyedDate = modifyedDate;
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
	 * @param modifyedByFirstName the modifyedByFirstName to set
	 */
	public void setModifyedByFirstName(String modifyedByFirstName) {
		this.modifyedByFirstName = modifyedByFirstName;
	}
	/**
	 * @param modifyedByLastName the modifyedByLastName to set
	 */
	public void setModifyedByLastName(String modifyedByLastName) {
		this.modifyedByLastName = modifyedByLastName;
	}
	/**
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}
	/**
	 * @return the sapDeliveryDocumentNumber
	 */
	public String getSapDeliveryDocumentNumber() {
		return sapDeliveryDocumentNumber;
	}
	/**
	 * @return the circleName
	 */
	public String getCircleName() {
		return circleName;
	}
	/**
	 * @param siteName the siteName to set
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	/**
	 * @param sapDeliveryDocumentNumber the sapDeliveryDocumentNumber to set
	 */
	public void setSapDeliveryDocumentNumber(String sapDeliveryDocumentNumber) {
		this.sapDeliveryDocumentNumber = sapDeliveryDocumentNumber;
	}
	/**
	 * @param circleName the circleName to set
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getAssetsStatusName() {
		return assetsStatusName;
	}
	public void setAssetsStatusName(String assetsStatusName) {
		this.assetsStatusName = assetsStatusName;
	}
	public int getAssetsStatusId() {
		return assetsStatusId;
	}
	public void setAssetsStatusId(int assetsStatusId) {
		this.assetsStatusId = assetsStatusId;
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
	 * @param siteId the siteId to set
	 */
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	/**
	 * @param siteManualId the siteManualId to set
	 */
	
	public boolean isWarrantyExist() {
		return isWarrantyExist;
	}
	public void setWarrantyExist(boolean isWarrantyExist) {
		this.isWarrantyExist = isWarrantyExist;
	}
	public boolean isAMCWarrantyExist() {
		return isAMCWarrantyExist;
	}
	public void setAMCWarrantyExist(boolean isAMCWarrantyExist) {
		this.isAMCWarrantyExist = isAMCWarrantyExist;
	}
	
	public void setSiteManualId(String siteManualId) {
		this.siteManualId = siteManualId;
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
	public String getWarranty() {
		return warranty;
	}
	public void setWarranty(String warranty) {
		this.warranty = warranty;

	}
	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}
	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}
	public String getModifyedByMiddleName() {
		return modifyedByMiddleName;
	}
	public void setModifyedByMiddleName(String modifyedByMiddleName) {
		this.modifyedByMiddleName = modifyedByMiddleName;
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
