package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

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
public class BtAssetMasterUpdate extends  Auditable<EmployeeDetails> implements Serializable,OldPresist<BtAssetMasterUpdate>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long srNo;
	
	private String equipmentType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private BtAssets btAssets;
	
	private String equipmentSrNo;
	
	private String softwareVersion;
	
	private String currentCondition;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	BtFsr btFsr;

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

	public String getCurrentCondition() {
		return currentCondition;
	}

	public void setCurrentCondition(String currentCondition) {
		this.currentCondition = currentCondition;
	}

	public BtFsr getBtFsr() {
		return btFsr;
	}

	public void setBtFsr(BtFsr btFsr) {
		this.btFsr = btFsr;
	}

	@Transient transient BtAssetMasterUpdate old;
	@PostLoad
	@Override
	public void old() {
		old=(BtAssetMasterUpdate) SerializationUtils.clone(this);
		
	}

	@Override
	public BtAssetMasterUpdate gOld() {
		
		return this.old;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EmployeeDetails getOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwner(EmployeeDetails u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getManualId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setManualId(String manualId) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
}
