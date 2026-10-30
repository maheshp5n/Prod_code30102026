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

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtPartcodeChange extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtPartcodeChange> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String manualId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    BtProducts fromProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    BtProducts toProduct;

    Float quantity;
    
    Integer productState;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    String remarks;
    


    public BtPartcodeChange() {
    }

    public BtPartcodeChange(Long id) {
        this.id = id;
    }

    

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public BtProducts getFromProduct() {
        return fromProduct;
    }

    public void setFromProduct(BtProducts fromProduct) {
        this.fromProduct = fromProduct;
    }

    public BtProducts getToProduct() {
        return toProduct;
    }

    public void setToProduct(BtProducts toProduct) {
        this.toProduct = toProduct;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Integer getProductState() {
        return productState;
    }

    public void setProductState(Integer productState) {
        this.productState = productState;
    }

    @Transient transient BtPartcodeChange old;

    @PostLoad
    @Override
    public void old() {
        this.old=(BtPartcodeChange)SerializationUtils.clone(this);
        
    }

    @Override
    public BtPartcodeChange gOld() {
        
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
        
        return this.getManualId();
    }

    @Override
    public void setManualId(String manualId) {
        
        this.manualId=manualId;
    }
    
}
