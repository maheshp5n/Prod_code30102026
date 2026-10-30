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

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtProductRequestItem extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtProductRequestItem>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    
    String manualId;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	BtProducts btProduct;
	
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	BtProductRequest btProductRequest;
	
	float quantity;
	float quantityReceived;
	float quantityIssued;
	String description;
	
	boolean synced=false;
	
	String itemStatus;
	
	boolean disabledItem=false;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
	
	public BtProductRequestItem() {}
	public BtProductRequestItem(long id) {
		this.id=id;
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
		return this.owner;
	}
	@Override
	public void setOwner(EmployeeDetails u) {
		// TODO Auto-generated method stub
		this.owner=u;
	}
	@Override
	public String getManualId() {
		// TODO Auto-generated method stub
		return manualId;
	}
	@Override
	public void setManualId(String manualId) {
		this.manualId = manualId;
		
	}


	public BtProducts getBtProduct() {
		return btProduct;
	}
	public void setBtProduct(BtProducts btProduct) {
		this.btProduct = btProduct;
	}
	public BtProductRequest getBtProductRequest() {
		return btProductRequest;
	}
	public void setBtProductRequest(BtProductRequest btProductRequest) {
		this.btProductRequest = btProductRequest;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public float getQuantityReceived() {
		return quantityReceived;
	}
	public void setQuantityReceived(float quantityReceived) {
		this.quantityReceived = quantityReceived;
	}
	public float getQuantityIssued() {
		return quantityIssued;
	}
	public void setQuantityIssued(float quantityIssued) {
		this.quantityIssued = quantityIssued;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	public String getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
	public BtProductRequestItem getOld() {
		return old;
	}
	public void setOld(BtProductRequestItem old) {
		this.old = old;
	}


	@Transient transient BtProductRequestItem old;

	@Override
    @PostLoad
    public void old() {
        old=(BtProductRequestItem) SerializationUtils.clone(this);
    }

	@Override
	public BtProductRequestItem gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
	public boolean isDisabledItem() {
		return disabledItem;
	}
	public void setDisabledItem(boolean disabledItem) {
		this.disabledItem = disabledItem;
	}
	
	
	
}
