package com.pratikshat.exicom.model.response;

import java.util.Calendar;
import java.util.Date;

import com.pratikshat.exicom.entity.BtAssetsWarrantyDetails;
import com.pratikshat.exicom.entity.BtCommissioningAssetMasterUpdate;

public class BtCommissioningAssetMasterUpdateResponse {

	private long srNo;
	private long assetId;
	private String equipmentType;
	private String partCode;
	private String model;
	private String equipmentSrNo;
	private String softwareVersion;
	private float soc;
	private float soh;
	private String warrantyStatus;
	private Date installationDate;
	private long currentConditionId;
	private String currentCondition;

	public BtCommissioningAssetMasterUpdateResponse() {

	}

	public BtCommissioningAssetMasterUpdateResponse(long assetId, long srNo, String equipmentType, String partCode,
			String model, String equipmentSrNo, String softwareVersion, float soc, float soh, String warrantyStatus,
			Date installationDate, long currentConditionId, String currentCondition) {
		this.assetId = assetId;
		this.srNo = srNo;
		this.equipmentType = equipmentType;
		this.partCode = partCode;
		this.model = model;
		this.equipmentSrNo = equipmentSrNo;
		this.softwareVersion = softwareVersion;
		this.soc = soc;
		this.soh = soh;
		this.warrantyStatus = warrantyStatus;
		this.installationDate = installationDate;
		this.currentConditionId = currentConditionId;
		this.currentCondition = currentCondition;
	}

	public BtCommissioningAssetMasterUpdateResponse(BtCommissioningAssetMasterUpdate commissioningAssetMasterUpdate) {

	}

	public BtCommissioningAssetMasterUpdateResponse(BtCommissioningAssetMasterUpdate commissioningAssetMasterUpdate,
			BtAssetsWarrantyDetails assetsWarrantyDetails) {
		if (commissioningAssetMasterUpdate != null) {
			this.assetId = commissioningAssetMasterUpdate.getBtAssets().getId();
			this.srNo = commissioningAssetMasterUpdate.getSrNo();
			this.equipmentType = commissioningAssetMasterUpdate.getEquipmentType();

			this.partCode = commissioningAssetMasterUpdate.getBtAssets().getProducts().getManualId();
			this.model = commissioningAssetMasterUpdate.getBtAssets().getProducts().getProductDescription();
			this.equipmentSrNo = commissioningAssetMasterUpdate.getEquipmentSrNo();
			this.softwareVersion = commissioningAssetMasterUpdate.getSoftwareVersion();
			this.soc = commissioningAssetMasterUpdate.getSoc();
			this.soh = commissioningAssetMasterUpdate.getSoh();

			if (assetsWarrantyDetails != null) {
				if (assetsWarrantyDetails.getWarrantyExpiryDate() != null) {
					Calendar calendar = Calendar.getInstance();
					Date currentDate = calendar.getTime();
					if (assetsWarrantyDetails.getWarrantyExpiryDate().getTime() > currentDate.getTime()) {
						this.warrantyStatus = "Under Warranty";
					} else {
						this.warrantyStatus = "Without Warranty";
					}
				} else {
					this.warrantyStatus = "Without Warranty";
				}
				this.installationDate = assetsWarrantyDetails.getInstallDate();
			}
			if (commissioningAssetMasterUpdate.getCurrentCondition().toString().toLowerCase().equalsIgnoreCase("ok")) {
				this.currentConditionId = 0;
				this.currentCondition = commissioningAssetMasterUpdate.getCurrentCondition();
			}
			if (commissioningAssetMasterUpdate.getCurrentCondition().toString().toLowerCase()
					.equalsIgnoreCase("faulty")) {
				this.currentConditionId = 1;
				this.currentCondition = commissioningAssetMasterUpdate.getCurrentCondition();
			}

		}
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
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

	public float getSoc() {
		return soc;
	}

	public void setSoc(float soc) {
		this.soc = soc;
	}

	public float getSoh() {
		return soh;
	}

	public void setSoh(float soh) {
		this.soh = soh;
	}

	public String getWarrantyStatus() {
		return warrantyStatus;
	}

	public void setWarrantyStatus(String warrantyStatus) {
		this.warrantyStatus = warrantyStatus;
	}

	public Date getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}

	public String getCurrentCondition() {
		return currentCondition;
	}

	public void setCurrentCondition(String currentCondition) {
		this.currentCondition = currentCondition;
	}

	public long getAssetId() {
		return assetId;
	}

	public void setAssetId(long assetId) {
		this.assetId = assetId;
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
