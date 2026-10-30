package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class DcrRepariCitemsSerial implements java.io.Serializable,OldPresist<DcrRepariCitemsSerial>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(fetch=FetchType.LAZY)
	DctRepairItemConsumed items;
	String serialNumber;
	
	public DcrRepariCitemsSerial() {}
	public DcrRepariCitemsSerial(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DctRepairItemConsumed getItems() {
		return items;
	}
	public void setItems(DctRepairItemConsumed items) {
		this.items = items;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	

	@javax.persistence.Transient transient DcrRepariCitemsSerial old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(DcrRepariCitemsSerial)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public DcrRepariCitemsSerial gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
