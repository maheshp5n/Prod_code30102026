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

import com.pratikshat.exicom.interceptor.ObjectAuditListener;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtProductReturnOrderItem extends Auditable<EmployeeDetails> implements OldPresist<BtProductReturnOrderItem>,Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String manualId;

    @ManyToOne(fetch = FetchType.LAZY)
    BtProducts products;

    Float quantityReturned;
    Float quantityReceived;

    Float customerQuantityReturned;
    Float customerQuantityReceived;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    @ManyToOne(fetch = FetchType.LAZY)
    BtProductReturnOrder returnOrder;

    boolean synced=false;

    public BtProductReturnOrderItem(Long id) {
        this.id = id;
    }

    public BtProductReturnOrderItem() {
    }

    public BtProducts getProducts() {
        return products;
    }

    public void setProducts(BtProducts products) {
        this.products = products;
    }

    public Float getQuantityReturned() {
        return quantityReturned;
    }

    public void setQuantityReturned(Float quantityReturned) {
        this.quantityReturned = quantityReturned;
    }

    public Float getQuantityReceived() {
        return quantityReceived;
    }

    public void setQuantityReceived(Float quantityReceived) {
        this.quantityReceived = quantityReceived;
    }

    public BtProductReturnOrder getReturnOrder() {
        return returnOrder;
    }

    public void setReturnOrder(BtProductReturnOrder returnOrder) {
        this.returnOrder = returnOrder;
    }

    
    public Float getCustomerQuantityReturned() {
        return customerQuantityReturned;
    }

    public void setCustomerQuantityReturned(Float customerQuantityReturned) {
        this.customerQuantityReturned = customerQuantityReturned;
    }

    public Float getCustomerQuantityReceived() {
        return customerQuantityReceived;
    }

    public void setCustomerQuantityReceived(Float customerQuantityReceived) {
        this.customerQuantityReceived = customerQuantityReceived;
    }




    @Transient transient BtProductReturnOrderItem old;

    @Override
    @PostLoad
    public void old() {
        this.old=(BtProductReturnOrderItem)org.apache.commons.lang.SerializationUtils.clone(this);
    }

    @Override
    public BtProductReturnOrderItem gOld() {
        
        return this.old;
    }

    @Override
    public Long getId() {
        
        return this.id;
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
    public void setOwner(EmployeeDetails u) {
        
        this.owner=u;
    }

    @Override
    public String getManualId() {
        
        return this.manualId;
    }

    @Override
    public void setManualId(String manualId) {
        this.manualId=manualId;
        
    }

    @Override
    public String toString() {
        return ""+this.id;
    }
    
    
}
