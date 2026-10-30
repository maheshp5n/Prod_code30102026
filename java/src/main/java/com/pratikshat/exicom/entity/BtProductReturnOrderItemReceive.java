package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtProductReturnOrderItemReceive extends Auditable<EmployeeDetails> implements OldPresist<BtProductReturnOrderItemReceive>,Serializable {

    @Id
    @Column(name = "id")
    Long id;

    String manualId;

    @OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id")
	@MapsId
    BtProductReturnOrderItem returnOrderItem;

    Float receivedQuantity;
    Float customerReceivedQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;


    

    public BtProductReturnOrderItemReceive(Long id) {
        this.id = id;
    }

    public BtProductReturnOrderItemReceive() {
    }


    public Float getCustomerReceivedQuantity() {
        return customerReceivedQuantity;
    }

    public void setCustomerReceivedQuantity(Float customerReceivedQuantity) {
        this.customerReceivedQuantity = customerReceivedQuantity;
    }

    public BtProductReturnOrderItem getReturnOrderItem() {
        return returnOrderItem;
    }

    public void setReturnOrderItem(BtProductReturnOrderItem returnOrderItem) {
        this.returnOrderItem = returnOrderItem;
    }

    public Float getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(Float receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }



    @Transient transient BtProductReturnOrderItemReceive old;

    @Override
    @PostLoad
    public void old() {
        this.old=(BtProductReturnOrderItemReceive)SerializationUtils.clone(this);
    }

    @Override
    public BtProductReturnOrderItemReceive gOld() {
        
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
