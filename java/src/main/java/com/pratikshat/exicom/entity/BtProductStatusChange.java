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
public class BtProductStatusChange extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtProductStatusChange> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String manualId;

    @ManyToOne(fetch = FetchType.LAZY)
    Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    BtProducts product;
    
    Integer fromState;
    Integer toState;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    String remarks;

    public BtProductStatusChange() {
    }

    public BtProductStatusChange(Long id) {
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

    public BtProducts getProduct() {
        return product;
    }

    public void setProduct(BtProducts product) {
        this.product = product;
    }

    public Integer getFromState() {
        return fromState;
    }

    public void setFromState(Integer fromState) {
        this.fromState = fromState;
    }

    public Integer getToState() {
        return toState;
    }

    public void setToState(Integer toState) {
        this.toState = toState;
    }

    @Transient transient BtProductStatusChange old;


    @PostLoad
    @Override
    public void old() {
        this.old=(BtProductStatusChange)SerializationUtils.clone(this);
    }

    @Override
    public BtProductStatusChange gOld() {
        
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
    
}
