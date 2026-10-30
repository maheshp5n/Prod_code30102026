package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtJobCardProductConsume extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtJobCardProductConsume>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String manualId;
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    BtJobCard jobCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    BtProducts product;

    Float quantity;

    Date consumeDate;

    Boolean consumeFromStock;

    boolean synced;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    @Transient transient BtJobCardProductConsume old;

    public BtJobCardProductConsume(Long id){
        this.id=id;
    }

    

    public boolean isSynced() {
        return synced;
    }



    public void setSynced(boolean synced) {
        this.synced = synced;
    }



    public BtJobCardProductConsume() {
    }

    public BtJobCard getJobCard() {
        return jobCard;
    }

    public void setJobCard(BtJobCard jobCard) {
        this.jobCard = jobCard;
    }

    public BtProducts getProduct() {
        return product;
    }

    public void setProduct(BtProducts product) {
        this.product = product;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Date getConsumeDate() {
        return consumeDate;
    }

    public void setConsumeDate(Date consumeDate) {
        this.consumeDate = consumeDate;
    }

    public Boolean getConsumeFromStock() {
        return consumeFromStock;
    }

    public void setConsumeFromStock(Boolean consumeFromStock) {
        this.consumeFromStock = consumeFromStock;
    }

    @Override
    public void old() {
    
        this.old=(BtJobCardProductConsume)SerializationUtils.clone(this);
    }

    @Override
    public BtJobCardProductConsume gOld() {
        
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
