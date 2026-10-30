package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtServiceOrderItemReceive extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtServiceOrderItemReceive>{
    
    //ID WILL BE SAME OF SERVICE ORDER ITEM MAKING ONE TO ONE RELATION WITHOUT PROPER ANNOTATION
    
    @Id
    Long id;

    String manualId;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    @ManyToOne(fetch = FetchType.LAZY)
    BtServiceOrderItem serviceOrderItem;

    public BtServiceOrderItemReceive(Long id){
        this.id=id;
    }
    public BtServiceOrderItemReceive(){

    }

    @Transient transient BtServiceOrderItemReceive old;

    @Override
    @PostLoad
    public void old() {
        
        this.old=(BtServiceOrderItemReceive)SerializationUtils.clone(this);
    }

    @Override
    public BtServiceOrderItemReceive gOld() {
        
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
    public BtServiceOrderItem getServiceOrderItem() {
        return serviceOrderItem;
    }
    public void setServiceOrderItem(BtServiceOrderItem serviceOrderItem) {
        this.serviceOrderItem = serviceOrderItem;
    }
    
    
    
}
