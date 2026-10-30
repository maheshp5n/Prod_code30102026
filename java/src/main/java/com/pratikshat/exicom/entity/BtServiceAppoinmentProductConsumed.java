package com.pratikshat.exicom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtServiceAppoinmentProductConsumed extends Auditable<EmployeeDetails> implements java.io.Serializable,OldPresist<BtServiceAppoinmentProductConsumed>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long id;
	String manualId;
	boolean synked;
	@javax.persistence.ManyToOne(fetch=FetchType.LAZY)
	BtServiceAppointment serviceAppointment;
	@ManyToOne(fetch=FetchType.LAZY)
	BtProducts replaced;
	@ManyToOne(fetch=FetchType.LAZY)
	BtProducts consumed;
	@javax.persistence.ManyToOne(fetch=FetchType.EAGER)
	ProductConsumptionType consumptionType;
	float quantity;
	@Column(length =1000)
	String remark;
	Boolean consume;
	@ManyToOne
	BtLocationProductItem items;
	
	
	
	public BtLocationProductItem getItems() {
		return items;
	}
	public void setItems(BtLocationProductItem items) {
		this.items = items;
	}
	public Boolean getConsume() {
		return consume;
	}
	public void setConsume(Boolean consume) {
		this.consume = consume;
	}
	public boolean isSynked() {
		return synked;
	}
	public void setSynked(boolean synked) {
		this.synked = synked;
	}
	public BtServiceAppointment getServiceAppointment() {
		return serviceAppointment;
	}
	public void setServiceAppointment(BtServiceAppointment serviceAppointment) {
		this.serviceAppointment = serviceAppointment;
	}
	public BtProducts getReplaced() {
		return replaced;
	}
	public void setReplaced(BtProducts replaced) {
		this.replaced = replaced;
	}
	public BtProducts getConsumed() {
		return consumed;
	}
	public void setConsumed(BtProducts consumed) {
		this.consumed = consumed;
	}
	public ProductConsumptionType getConsumptionType() {
		return consumptionType;
	}
	public void setConsumptionType(ProductConsumptionType consumptionType) {
		this.consumptionType = consumptionType;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Transient transient BtServiceAppoinmentProductConsumed old;
	@Override
	@PostLoad
	public void old() {
		old=(BtServiceAppoinmentProductConsumed) SerializationUtils.clone(this);
		
	}
	@Override
	public BtServiceAppoinmentProductConsumed gOld() {
		// TODO Auto-generated method stub
		return this.old;
	}
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	@Override
	public void setId(Long id) {
	this.id=id;
		
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
		return this.manualId;
	}
	@Override
	public void setManualId(String manualId) {
		this.manualId=manualId;
		
	}

}
