package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

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
public class BtServiceOrderItemReturnReceive extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtServiceOrderItemReturnReceive>{

    //LOGICAL ONE TO ONE WITH SERVICE ORDER ITEM RETURN
    @Id
    Long id;
    String manualId;
    @ManyToOne(fetch = FetchType.LAZY)
    BtServiceOrderItemReturn itemReturn;

    Date receiveDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    public BtServiceOrderItemReturnReceive(){}
    public BtServiceOrderItemReturnReceive(Long id){
        this.id=id;
    }


    


    public BtServiceOrderItemReturn getItemReturn() {
        return itemReturn;
    }
    public void setItemReturn(BtServiceOrderItemReturn itemReturn) {
        this.itemReturn = itemReturn;
    }
    public Date getReceiveDate() {
        return receiveDate;
    }
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }





    @Transient transient BtServiceOrderItemReturnReceive old;

    @PostLoad
    @Override
    public void old() {
        this.old=(BtServiceOrderItemReturnReceive)SerializationUtils.clone(this);
        
    }


    @Override
    public BtServiceOrderItemReturnReceive gOld() {
        
        return this.old;
    }

    @Override
    public Long getId() {
        // TODO Auto-generated method stub
        return this.id;
    }

    @Override
    public void setId(Long id) {
        // TODO Auto-generated method stub
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
        return this.manualId;
    }

    @Override
    public void setManualId(String manualId) {
        // TODO Auto-generated method stub
        this.manualId=manualId;
    }
    
}
