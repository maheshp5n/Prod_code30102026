package com.pratikshat.exicom.entity;
import com.pratikshat.exicom.interceptor.ObjectAuditListener;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.SerializationUtils;

import javax.persistence.*;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtAssetsAmcdetails extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtAssetsAmcdetails>{
    @Id
    @Column(name = "id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    String amcPurchaseOrderNumber;
    String amcPurchaseOrderDate;
    Date amcStartDate;
    Date amcExpiryDate;
    int amcPeriod;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    BtAssets btAssets;

    public BtAssetsAmcdetails(){}
    public BtAssetsAmcdetails(Long id){
        this.id=id;
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
    public void setOwner(EmployeeDetails employeeDetails) {
        this.owner=employeeDetails;
    }

    @Override
    public String getManualId() {
        return null;
    }

    @Override
    public void setManualId(String manualId) {

    }

    @Transient transient BtAssetsAmcdetails old;

    @Override
    @PostLoad
    public void old() {
        old=(BtAssetsAmcdetails) SerializationUtils.clone(this);
    }

    @Override
    public BtAssetsAmcdetails gOld() {
        return this.old;
    }

    public String getAmcPurchaseOrderNumber() {
        return amcPurchaseOrderNumber;
    }

    public void setAmcPurchaseOrderNumber(String amcPurchaseOrderNumber) {
        this.amcPurchaseOrderNumber = amcPurchaseOrderNumber;
    }

    public String getAmcPurchaseOrderDate() {
        return amcPurchaseOrderDate;
    }

    public void setAmcPurchaseOrderDate(String amcPurchaseOrderDate) {
        this.amcPurchaseOrderDate = amcPurchaseOrderDate;
    }

    public Date getAmcStartDate() {
        return amcStartDate;
    }

    public void setAmcStartDate(Date amcStartDate) {
        this.amcStartDate = amcStartDate;
    }

    public Date getAmcExpiryDate() {
        return amcExpiryDate;
    }

    public void setAmcExpiryDate(Date amcExpiryDate) {
        this.amcExpiryDate = amcExpiryDate;
    }

    public int getAmcPeriod() {
        return amcPeriod;
    }

    public void setAmcPeriod(int amcPeriod) {
        this.amcPeriod = amcPeriod;
    }

    public BtAssets getBtAssets() {
        return btAssets;
    }

    public void setBtAssets(BtAssets btAssets) {
        this.btAssets = btAssets;
    }
    
    public String toString() {
		return ""+id;
	} 
    
}
