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

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({ObjectAuditListener.class,AuditingEntityListener.class})
public class BtProductItem extends Auditable<EmployeeDetails> implements OldPresist<BtProductItem>,Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String manualId;

    @ManyToOne(fetch = FetchType.LAZY)
    BtProducts product;

    @ManyToOne(fetch = FetchType.LAZY)
    Location warehouse;

    Float exicomTotalQuantity;
    Float exicomTotalOkQuantity;
    Float exicomTotalFaultyQuantity;
    Float exicomTotalBerQuantity;
    Float exicomTotalRejectedQuantity;
    Float exicomTotalScrapQuantity;

    Float customerTotalQuantity;
    Float customerTotalOkQuantity;
    Float customerTotalFaultyQuantity;
    Float customerTotalBerQuantity;
    Float customerTotalRejectedQuantity;
    Float customerTotalScrapQuantity;

    Float hslQuantity;
    Float mslQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    public BtProductItem(){}

    public BtProductItem(Long id){
        this.id=id;
    }

    @Transient transient BtProductItem old;

    @PostLoad
    @Override
    public void old() {
        
        this.old=(BtProductItem)SerializationUtils.clone(this);
    }

    @Override
    public BtProductItem gOld() {
        
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

    public BtProducts getProduct() {
        return product;
    }

    public void setProduct(BtProducts product) {
        this.product = product;
    }

    public Location getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Location warehouse) {
        this.warehouse = warehouse;
    }

    public Float getExicomTotalQuantity() {
        return exicomTotalQuantity;
    }

    public void setExicomTotalQuantity(Float exicomTotalQuantity) {
        this.exicomTotalQuantity = exicomTotalQuantity;
    }

    public Float getExicomTotalOkQuantity() {
        return exicomTotalOkQuantity;
    }

    public void setExicomTotalOkQuantity(Float exicomTotalOkQuantity) {
        this.exicomTotalOkQuantity = exicomTotalOkQuantity;
    }

    public Float getExicomTotalFaultyQuantity() {
        return exicomTotalFaultyQuantity;
    }

    public void setExicomTotalFaultyQuantity(Float exicomTotalFaultyQuantity) {
        this.exicomTotalFaultyQuantity = exicomTotalFaultyQuantity;
    }

    public Float getExicomTotalBerQuantity() {
        return exicomTotalBerQuantity;
    }

    public void setExicomTotalBerQuantity(Float exicomTotalBerQuantity) {
        this.exicomTotalBerQuantity = exicomTotalBerQuantity;
    }

    public Float getExicomTotalRejectedQuantity() {
        return exicomTotalRejectedQuantity;
    }

    public void setExicomTotalRejectedQuantity(Float exicomTotalRejectedQuantity) {
        this.exicomTotalRejectedQuantity = exicomTotalRejectedQuantity;
    }

    public Float getExicomTotalScrapQuantity() {
        return exicomTotalScrapQuantity;
    }

    public void setExicomTotalScrapQuantity(Float exicomTotalScrapQuantity) {
        this.exicomTotalScrapQuantity = exicomTotalScrapQuantity;
    }

    public Float getCustomerTotalQuantity() {
        return customerTotalQuantity;
    }

    public void setCustomerTotalQuantity(Float customerTotalQuantity) {
        this.customerTotalQuantity = customerTotalQuantity;
    }

    public Float getCustomerTotalOkQuantity() {
        return customerTotalOkQuantity;
    }

    public void setCustomerTotalOkQuantity(Float customerTotalOkQuantity) {
        this.customerTotalOkQuantity = customerTotalOkQuantity;
    }

    public Float getCustomerTotalFaultyQuantity() {
        return customerTotalFaultyQuantity;
    }

    public void setCustomerTotalFaultyQuantity(Float customerTotalFaultyQuantity) {
        this.customerTotalFaultyQuantity = customerTotalFaultyQuantity;
    }

    public Float getCustomerTotalBerQuantity() {
        return customerTotalBerQuantity;
    }

    public void setCustomerTotalBerQuantity(Float customerTotalBerQuantity) {
        this.customerTotalBerQuantity = customerTotalBerQuantity;
    }

    public Float getCustomerTotalRejectedQuantity() {
        return customerTotalRejectedQuantity;
    }

    public void setCustomerTotalRejectedQuantity(Float customerTotalRejectedQuantity) {
        this.customerTotalRejectedQuantity = customerTotalRejectedQuantity;
    }

    public Float getCustomerTotalScrapQuantity() {
        return customerTotalScrapQuantity;
    }

    public void setCustomerTotalScrapQuantity(Float customerTotalScrapQuantity) {
        this.customerTotalScrapQuantity = customerTotalScrapQuantity;
    }
    
    
}
