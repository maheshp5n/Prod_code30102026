package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class DctRepairBulkConsumption extends Auditable<EmployeeDetails> implements Serializable,OldPresist<DctRepairBulkConsumption> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String manualId;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    Products product;

    @ManyToOne(fetch = FetchType.LAZY)
    Products consumedProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    Location location;

    Float consumedQuantity;

    @Column(length = 500)
    String remarks;

    boolean scynced;

    public DctRepairBulkConsumption(){}

    public DctRepairBulkConsumption(Long id){
        this.id=id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Products getConsumedProduct() {
        return consumedProduct;
    }

    public void setConsumedProduct(Products consumedProduct) {
        this.consumedProduct = consumedProduct;
    }

    public Float getConsumedQuantity() {
        return consumedQuantity;
    }

    public void setConsumedQuantity(Float consumedQuantity) {
        this.consumedQuantity = consumedQuantity;
    }



    @Transient transient DctRepairBulkConsumption old;

    @Override
    public void old() {
       
        this.old=(DctRepairBulkConsumption)SerializationUtils.clone(this);
    }

    @Override
    public DctRepairBulkConsumption gOld() {
        
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isScynced() {
        return scynced;
    }

    public void setScynced(boolean scynced) {
        this.scynced = scynced;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    
    
    
}
