package com.pratikshat.exicom.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtCommissioningAssetMasterUpdate extends  Auditable<EmployeeDetails> implements Serializable,OldPresist<BtCommissioningAssetMasterUpdate> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long srNo;

	private String equipmentType;

	@ManyToOne(fetch = FetchType.LAZY)
	private BtAssets btAssets;

	private String equipmentSrNo;

	private String softwareVersion;
	
	private float soc;
	
	private float soh;

	private String currentCondition;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	BtCommissioningFSR btCommissioningFSR;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails owner;

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSrNo() {
		return srNo;
	}

	public void setSrNo(long srNo) {
		this.srNo = srNo;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	
	public BtAssets getBtAssets() {
		return btAssets;
	}

	public void setBtAssets(BtAssets btAssets) {
		this.btAssets = btAssets;
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

	public String getCurrentCondition() {
		return currentCondition;
	}

	public void setCurrentCondition(String currentCondition) {
		this.currentCondition = currentCondition;
	}

	public BtCommissioningFSR getBtCommissioningFSR() {
		return btCommissioningFSR;
	}

	public void setBtCommissioningFSR(BtCommissioningFSR btCommissioningFSR) {
		this.btCommissioningFSR = btCommissioningFSR;
	}
	
	

	@Transient transient BtCommissioningAssetMasterUpdate old;
	@PostLoad
	@Override
	public void old() {
		old=(BtCommissioningAssetMasterUpdate) SerializationUtils.clone(this);
		
	}

	@Override
	public BtCommissioningAssetMasterUpdate gOld() {
		return this.old;
	}
	
	

	@Override
	public void setId(Long id) {	
		this.id=id;
	}

	@Override
	public EmployeeDetails getOwner() {
		return this.owner;
	}

	@Override
	public void setOwner(EmployeeDetails owner) {
		this.owner=owner;
	}

	@Override
	public String getManualId() {
		return null;
	}

	@Override
	public void setManualId(String manualId) {
		
	}
	
	

}
