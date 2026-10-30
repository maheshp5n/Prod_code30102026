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
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtServiceOrderReturn extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtServiceOrderReturn>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String manualId;

    @ManyToOne(fetch = FetchType.LAZY)
    Location sourceLocation;
    @ManyToOne(fetch = FetchType.LAZY)
    Location destinationLocation;
    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    Float totalNumberOfQuantity;
    Float totalNumberOfQuantityReceived;

    Date returnDate;

    String shippingTo;
    String shippintMode;
    String trackingNumber;

    public BtServiceOrderReturn(Long id) {
        this.id = id;
    }

    public BtServiceOrderReturn() {
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

    public Float getTotalNumberOfQuantity() {
        return totalNumberOfQuantity;
    }

    public void setTotalNumberOfQuantity(Float totalNumberOfQuantity) {
        this.totalNumberOfQuantity = totalNumberOfQuantity;
    }

    public Float getTotalNumberOfQuantityReceived() {
        return totalNumberOfQuantityReceived;
    }

    public void setTotalNumberOfQuantityReceived(Float totalNumberOfQuantityReceived) {
        this.totalNumberOfQuantityReceived = totalNumberOfQuantityReceived;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getShippingTo() {
        return shippingTo;
    }

    public void setShippingTo(String shippingTo) {
        this.shippingTo = shippingTo;
    }

    public String getShippintMode() {
        return shippintMode;
    }

    public void setShippintMode(String shippintMode) {
        this.shippintMode = shippintMode;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    @Transient transient BtServiceOrderReturn old;

    @Override
    @PostLoad
    public void old() {
        this.old=(BtServiceOrderReturn)SerializationUtils.clone(this);
    }

    @Override
    public BtServiceOrderReturn gOld() {
        
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
