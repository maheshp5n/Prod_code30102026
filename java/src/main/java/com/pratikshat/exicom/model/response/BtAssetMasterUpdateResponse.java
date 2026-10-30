package com.pratikshat.exicom.model.response;

import java.util.Calendar;
import java.util.Date;

import com.pratikshat.exicom.entity.BtAssetMasterUpdate;
import com.pratikshat.exicom.entity.BtAssets;
import com.pratikshat.exicom.entity.BtAssetsWarrantyDetails;
import com.pratikshat.exicom.entity.BtCommissioningAssetMasterUpdate;

public class BtAssetMasterUpdateResponse {

	long srNo;

	long assetId;

	String equipmentType;

	String productPartCode;

	String productDescription;

	String equipmentSrNo;

	String softwareVersion;

	String warrantyStatus;

	private Date installationDate;

	long currentConditionId;

	String currentCondition;

	public BtAssetMasterUpdateResponse() {

	}

	public BtAssetMasterUpdateResponse(BtAssetMasterUpdate assetMasterUpdate) {

		if (assetMasterUpdate != null) {
			this.assetId = assetMasterUpdate.getBtAssets().getId();
			this.srNo = assetMasterUpdate.getSrNo();
			this.equipmentType = assetMasterUpdate.getEquipmentType();

			this.softwareVersion = assetMasterUpdate.getSoftwareVersion();
			this.equipmentSrNo = assetMasterUpdate.getEquipmentSrNo();
			if (assetMasterUpdate.getBtFsr() != null) {
				this.productDescription = assetMasterUpdate.getBtAssets().getProducts().getProductDescription();
				this.productPartCode = assetMasterUpdate.getBtAssets().getProducts().getManualId();
			}
			if (assetMasterUpdate.getCurrentCondition().toString().toLowerCase().equalsIgnoreCase("ok")) {
				this.currentConditionId = 0;
				this.currentCondition = assetMasterUpdate.getCurrentCondition();
			}
			if (assetMasterUpdate.getCurrentCondition().toString().toLowerCase().equalsIgnoreCase("faulty")) {
				this.currentConditionId = 1;
				this.currentCondition = assetMasterUpdate.getCurrentCondition();
			}
		}

	}

	public BtAssetMasterUpdateResponse(BtAssetMasterUpdate assetMasterUpdate,
			BtAssetsWarrantyDetails assetsWarrantyDetails) {
		if (assetMasterUpdate != null) {
			BtAssets btAssets = assetMasterUpdate.getBtAssets();
			if(btAssets != null) {
				this.assetId = btAssets.getId();
				this.productPartCode =  btAssets.getProducts().getManualId();
				this.productDescription =  btAssets.getProducts().getProductDescription();
				if (assetMasterUpdate.getCurrentCondition().toString().toLowerCase().equalsIgnoreCase("ok")) {
					this.currentConditionId = 0;
					this.currentCondition = assetMasterUpdate.getCurrentCondition();
				}
				if (assetMasterUpdate.getCurrentCondition().toString().toLowerCase().equalsIgnoreCase("faulty")) {

					this.currentConditionId = 1;
					this.currentCondition = assetMasterUpdate.getCurrentCondition();
				}
			}
//			this.assetId = assetMasterUpdate.getBtAssets().getId();
			this.srNo = assetMasterUpdate.getSrNo();
			this.equipmentType = assetMasterUpdate.getEquipmentType();
//			this.productPartCode = assetMasterUpdate.getBtAssets().getProducts().getManualId();
//			this.productDescription = assetMasterUpdate.getBtAssets().getProducts().getProductDescription();
			this.equipmentSrNo = assetMasterUpdate.getEquipmentSrNo();
			this.softwareVersion = assetMasterUpdate.getSoftwareVersion();
			if (assetsWarrantyDetails != null) {
				if (assetsWarrantyDetails.getWarrantyExpiryDate() != null) {
					Calendar calendar = Calendar.getInstance();
					Date currentDate = calendar.getTime();
					if (assetsWarrantyDetails.getWarrantyExpiryDate().getTime() > currentDate.getTime()) {
						this.warrantyStatus = "Under Warranty";
					} else {
						this.warrantyStatus = "Without Warranty";
					}
				}
			 else {
				this.warrantyStatus = "Without Warranty";
			}
			if(assetsWarrantyDetails.getInstallDate()!=null)
			this.installationDate = assetsWarrantyDetails.getInstallDate();
			}
//			if (assetMasterUpdate.getCurrentCondition().toString().toLowerCase().equalsIgnoreCase("ok")) {
//				this.currentConditionId = 0;
//				this.currentCondition = assetMasterUpdate.getCurrentCondition();
//			}
//			if (assetMasterUpdate.getCurrentCondition().toString().toLowerCase().equalsIgnoreCase("faulty")) {
//
//				this.currentConditionId = 1;
//				this.currentCondition = assetMasterUpdate.getCurrentCondition();
//			}

		}
	}

	public BtAssetMasterUpdateResponse(long assetId, long srNo, String equipmentType, String productPartCode,
			String productDescription, String equipmentSrNo, String softwareVersion, String warrantyStatus,
			long currentConditionId, String currentCondition) {
		this.assetId = assetId;
		this.srNo = srNo;
		this.equipmentType = equipmentType;
		this.productPartCode = productPartCode;
		this.productDescription = productDescription;
		this.equipmentSrNo = equipmentSrNo;
		this.softwareVersion = softwareVersion;
		this.warrantyStatus = warrantyStatus;
		this.currentConditionId = currentConditionId;
		this.currentCondition = currentCondition;
	}

	public long getAssetId() {
		return assetId;
	}

	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getProductPartCode() {
		return productPartCode;
	}

	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getEquipmentSrNo() {
		return equipmentSrNo;
	}

	public void setEquipmentSrNo(String equipmentSrNo) {
		this.equipmentSrNo = equipmentSrNo;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getWarrantyStatus() {
		return warrantyStatus;
	}

	public void setWarrantyStatus(String warrantyStatus) {
		this.warrantyStatus = warrantyStatus;
	}

	public String getCurrentCondition() {
		return currentCondition;
	}

	public void setCurrentCondition(String currentCondition) {
		this.currentCondition = currentCondition;
	}

	public Date getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}

	public long getCurrentConditionId() {
		return currentConditionId;
	}

	public void setCurrentConditionId(long currentConditionId) {
		this.currentConditionId = currentConditionId;
	}

	public long getSrNo() {
		return srNo;
	}

	public void setSrNo(long srNo) {
		this.srNo = srNo;
	}

}
