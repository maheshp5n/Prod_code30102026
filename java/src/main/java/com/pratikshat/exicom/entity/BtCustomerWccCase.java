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

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.annotation.Transient;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class BtCustomerWccCase implements Serializable, OldPresist<BtCustomerWccCase> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	BtCustomerWcc wcc;
	
	@ManyToOne(fetch=FetchType.LAZY)
	BtCase btcase;
	
	
	float siteVisitCharge;
	float customerSpareCharges;
	float totalCharges;
	
		
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BtCustomerWcc getWcc() {
		return wcc;
	}

	public void setWcc(BtCustomerWcc wcc) {
		this.wcc = wcc;
	}

	public BtCase getBtcase() {
		return btcase;
	}

	public void setBtcase(BtCase btcase) {
		this.btcase = btcase;
	}

	public float getSiteVisitCharge() {
		return siteVisitCharge;
	}

	public void setSiteVisitCharge(float siteVisitCharge) {
		this.siteVisitCharge = siteVisitCharge;
	}

	public float getCustomerSpareCharges() {
		return customerSpareCharges;
	}

	public void setCustomerSpareCharges(float customerSpareCharges) {
		this.customerSpareCharges = customerSpareCharges;
	}

	public float getTotalCharges() {
		return totalCharges;
	}

	public void setTotalCharges(float totalCharges) {
		this.totalCharges = totalCharges;
	}

	@Transient transient BtCustomerWccCase old;
	@PostLoad
	@Override
	public void old() {
		
		this.old= (BtCustomerWccCase) SerializationUtils.clone(old);
		// TODO Auto-generated method stub
		
	}

	@Override
	public BtCustomerWccCase gOld() {
		// TODO Auto-generated method stub
		return this.old;
	}

}
