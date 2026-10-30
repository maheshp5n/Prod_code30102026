package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class MrnLineItems implements java.io.Serializable,OldPresist<MrnLineItems>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	MrnItems item;
	String serialNumber;
	public MrnLineItems() {}
	public MrnLineItems(Long id) {
		this.id=id;
	}
	public MrnLineItems(String serialNumber) {
		this.serialNumber=serialNumber;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MrnItems getItem() {
		return item;
	}
	public void setItem(MrnItems item) {
		this.item = item;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	

	@javax.persistence.Transient transient MrnLineItems old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(MrnLineItems)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public MrnLineItems gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
