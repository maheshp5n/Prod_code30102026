package com.pratikshat.exicom.entity;

import javax.persistence.*;
import java.util.Date;
import com.pratikshat.exicom.interceptor.ObjectAuditListener;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.io.Serializable;

import org.apache.commons.lang.SerializationUtils;
@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtAssetsWarrantyDetails extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtAssetsWarrantyDetails>{

    @Id
    @Column(name ="id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    BtAssets btAssets;

    Date purchaseDate;
    Date installDate;
    int warrantyFromDateOfDeliveryInMonth;
    int warrantyFromDateOfInstallationInMonth;
    Date warrantyExpiryDate;
    Date installExpiryDate;
    Date dateOfDeliveryExpiryDate;

    public BtAssetsWarrantyDetails(){}
    public BtAssetsWarrantyDetails(Long id){
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

    @Transient transient BtAssetsWarrantyDetails old;

    @Override
    @PostLoad
    public void old() {
        old=(BtAssetsWarrantyDetails) SerializationUtils.clone(this);
    }

    @Override
    public BtAssetsWarrantyDetails gOld() {
        return old;
    }

    public BtAssets getBtAssets() {
        return btAssets;
    }

    public void setBtAssets(BtAssets btAssets) {
        this.btAssets = btAssets;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    public int getWarrantyFromDateOfDeliveryInMonth() {
        return warrantyFromDateOfDeliveryInMonth;
    }

    public void setWarrantyFromDateOfDeliveryInMonth(int warrantyFromDateOfDeliveryInMonth) {
        this.warrantyFromDateOfDeliveryInMonth = warrantyFromDateOfDeliveryInMonth;
    }

    public int getWarrantyFromDateOfInstallationInMonth() {
        return warrantyFromDateOfInstallationInMonth;
    }

    public void setWarrantyFromDateOfInstallationInMonth(int warrantyFromDateOfInstallationInMonth) {
        this.warrantyFromDateOfInstallationInMonth = warrantyFromDateOfInstallationInMonth;
    }

    public Date getWarrantyExpiryDate() {
        return warrantyExpiryDate;
    }

    public void setWarrantyExpiryDate(Date warrantyExpiryDate) {
        this.warrantyExpiryDate = warrantyExpiryDate;
    }

    public Date getInstallExpiryDate() {
        return installExpiryDate;
    }

    public void setInstallExpiryDate(Date installExpiryDate) {
        this.installExpiryDate = installExpiryDate;
    }

    public Date getDateOfDeliveryExpiryDate() {
        return dateOfDeliveryExpiryDate;
    }

    public void setDateOfDeliveryExpiryDate(Date dateOfDeliveryExpiryDate) {
        this.dateOfDeliveryExpiryDate = dateOfDeliveryExpiryDate;
    }
    
    public String toString() {
		return ""+id;
	} 
}
