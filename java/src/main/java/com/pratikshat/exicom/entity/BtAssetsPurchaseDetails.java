package com.pratikshat.exicom.entity;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.SerializationUtils;

@Entity
@EntityListeners({AuditingEntityListener.class, ObjectAuditListener.class})
public class BtAssetsPurchaseDetails  extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtAssetsPurchaseDetails>{
    @Id
    @Column(name = "id")
    Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    BtAssets assets;
    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    String purchaseOrderNumber;
    Date purchaseOrderDate;
    String sapDeliveryDocumentNumber;
    String sapMaterialDocumentNumber;
    String salesDocNumber;

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

    @Transient transient BtAssetsPurchaseDetails old;

    @Override
    @PostLoad
    public void old() {
        old=(BtAssetsPurchaseDetails) SerializationUtils.clone(this);
    }

    @Override
    public BtAssetsPurchaseDetails gOld() {
        return this.old;
    }

    public BtAssets getAssets() {
        return assets;
    }

    public void setAssets(BtAssets assets) {
        this.assets = assets;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public Date getPurchaseOrderDate() {
        return purchaseOrderDate;
    }

    public void setPurchaseOrderDate(Date purchaseOrderDate) {
        this.purchaseOrderDate = purchaseOrderDate;
    }

    public String getSapDeliveryDocumentNumber() {
        return sapDeliveryDocumentNumber;
    }

    public void setSapDeliveryDocumentNumber(String sapDeliveryDocumentNumber) {
        this.sapDeliveryDocumentNumber = sapDeliveryDocumentNumber;
    }

    public String getSapMaterialDocumentNumber() {
        return sapMaterialDocumentNumber;
    }

    public void setSapMaterialDocumentNumber(String sapMaterialDocumentNumber) {
        this.sapMaterialDocumentNumber = sapMaterialDocumentNumber;
    }

    public String getSalesDocNumber() {
        return salesDocNumber;
    }

    public void setSalesDocNumber(String salesDocNumber) {
        this.salesDocNumber = salesDocNumber;
    }
    public String toString() {
		return ""+id;
	} 
}
