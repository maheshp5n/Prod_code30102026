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
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtServiceOrder extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtServiceOrder> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String manualId;

    @ManyToOne(fetch = FetchType.LAZY)
    Location sourceLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    Location destinationLocation;

    Float totalNumberOfItem;

    Float totalNumberOfItemReceived;

    Float totalNumberOfItemReturned;

    Float totalNumberOfItemAssigned;

    Float totalNumberOfItemAssignedReturned;

    String shippingMode;
    String shippingTo;
    String trackingNumber;

    

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    public BtServiceOrder(){}
    public BtServiceOrder(Long id){
        this.id=id;
    }

    

    public Location getSourceLocation() {
        return sourceLocation;
    }
    public void setSourceLocation(Location sourceLocation) {
        this.sourceLocation = sourceLocation;
    }
    public Location getDestinationLocation() {
        return destinationLocation;
    }
    public void setDestinationLocation(Location destinationLocation) {
        this.destinationLocation = destinationLocation;
    }
    public Float getTotalNumberOfItem() {
        return totalNumberOfItem;
    }
    public void setTotalNumberOfItem(Float totalNumberOfItem) {
        this.totalNumberOfItem = totalNumberOfItem;
    }
    public Float getTotalNumberOfItemReceived() {
        return totalNumberOfItemReceived;
    }
    public void setTotalNumberOfItemReceived(Float totalNumberOfItemReceived) {
        this.totalNumberOfItemReceived = totalNumberOfItemReceived;
    }
    public Float getTotalNumberOfItemReturned() {
        return totalNumberOfItemReturned;
    }
    public void setTotalNumberOfItemReturned(Float totalNumberOfItemReturned) {
        this.totalNumberOfItemReturned = totalNumberOfItemReturned;
    }
    public Float getTotalNumberOfItemAssigned() {
        return totalNumberOfItemAssigned;
    }
    public void setTotalNumberOfItemAssigned(Float totalNumberOfItemAssigned) {
        this.totalNumberOfItemAssigned = totalNumberOfItemAssigned;
    }
    public Float getTotalNumberOfItemAssignedReturned() {
        return totalNumberOfItemAssignedReturned;
    }
    public void setTotalNumberOfItemAssignedReturned(Float totalNumberOfItemAssignedReturned) {
        this.totalNumberOfItemAssignedReturned = totalNumberOfItemAssignedReturned;
    }
    public String getShippingMode() {
        return shippingMode;
    }
    public void setShippingMode(String shippingMode) {
        this.shippingMode = shippingMode;
    }
    public String getShippingTo() {
        return shippingTo;
    }
    public void setShippingTo(String shippingTo) {
        this.shippingTo = shippingTo;
    }
    public String getTrackingNumber() {
        return trackingNumber;
    }
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }



    @Transient transient BtServiceOrder old;
    @Override
    @PostLoad
    public void old() {
        this.old=(BtServiceOrder)SerializationUtils.clone(this);
    }

    @Override
    public BtServiceOrder gOld() {
        
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
    public String toString(){
        return ""+id;
    }
    
}
