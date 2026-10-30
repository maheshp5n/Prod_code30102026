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

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtServiceOrderItemReturn extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtServiceOrderItemReturn>{
    
    //ID WILL BE SAME AS SERVICE ORDER ITEM/SERVICE ORDER ITEM RECEIVE, LOGICAL ONE TO ONE RELATION
    @Id
    Long id;

    String manualId;

    @ManyToOne(fetch = FetchType.LAZY)
    BtServiceOrderItemReceive itemReceive;
    
    @ManyToOne(fetch = FetchType.LAZY)
    BtServiceOrderReturn soReturn;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    Boolean received;

    boolean synced;

    public BtServiceOrderItemReturn(){

    }
    
    public BtServiceOrderItemReturn(Long id){
        this.id=id;
    }

    
    

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }

    public BtServiceOrderItemReceive getItemReceive() {
        return itemReceive;
    }
    public void setItemReceive(BtServiceOrderItemReceive itemReceive) {
        this.itemReceive = itemReceive;
    }
    public BtServiceOrderReturn getSoReturn() {
        return soReturn;
    }
    public void setSoReturn(BtServiceOrderReturn soReturn) {
        this.soReturn = soReturn;
    }
    public Boolean getReceived() {
        return received;
    }
    public void setReceived(Boolean received) {
        this.received = received;
    }



    @Transient transient BtServiceOrderItemReturn old;

    @Override
    @PostLoad
    public void old() {
        this.old=(SerializationUtils.clone(this));
    }

    @Override
    public BtServiceOrderItemReturn gOld() {
        
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
