package com.pratikshat.exicom.entity;

import javax.persistence.*;
import java.io.Serializable;
import com.pratikshat.exicom.interceptor.ObjectAuditListener;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.apache.commons.lang.SerializationUtils;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtProducts extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtProducts>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String manualId;
    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;
    @Column(length=500)
    String productDescription;
    @ManyToOne(fetch=FetchType.LAZY)
    ProductFamilySubProduct subProductFamily;
    String productType;
    float productPrice;

    boolean synced;
    boolean active;

    @ManyToOne(fetch=FetchType.LAZY)
    HsnDetails hsnDetails;

    public BtProducts(){}
    public BtProducts(Long id){
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
        return this.manualId;
    }

    @Override
    public void setManualId(String manualId) {
        this.manualId=manualId;
    }

    @Transient transient BtProducts old;

    @Override
    @PostLoad
    public void old() {
        old=(BtProducts) SerializationUtils.clone(this);
    }

    @Override
    public BtProducts gOld() {
        return old;
    }

   

    public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public ProductFamilySubProduct getSubProductFamily() {
        return subProductFamily;
    }

    public void setSubProductFamily(ProductFamilySubProduct subProductFamily) {
        this.subProductFamily = subProductFamily;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public HsnDetails getHsnDetails() {
        return hsnDetails;
    }

    public void setHsnDetails(HsnDetails hsnDetails) {
        this.hsnDetails = hsnDetails;
    }

    public BtProducts getOld() {
        return old;
    }

    public void setOld(BtProducts old) {
        this.old = old;
    }
	@Override
	public String toString() {
		return " "+id;
	}
    
    
}