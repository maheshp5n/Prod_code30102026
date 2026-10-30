package com.pratikshat.exicom.entity;

import java.io.Serializable;

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

@Entity
@EntityListeners(AuditingEntityListener.class)
public class BtPowerPlantMake implements  Serializable,OldPresist<BtPowerPlantMake>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String typeValue;
	
	private boolean activation;
	
	@ManyToOne(fetch= FetchType.LAZY)
	private BusinessDomain businessDomain;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}

	@Transient transient BtPowerPlantMake old;
	@PostLoad
	@Override
	public void old() {
		old=(BtPowerPlantMake) SerializationUtils.clone(this);
		
	}

	@Override
	public BtPowerPlantMake gOld() {
		// TODO Auto-generated method stub
		return this.old;
	}
	
	
	
}
