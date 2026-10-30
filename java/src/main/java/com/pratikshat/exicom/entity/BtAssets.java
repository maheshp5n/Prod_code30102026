package com.pratikshat.exicom.entity;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtAssets extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtAssets>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;
    String manualId;

    @ManyToOne(fetch = FetchType.LAZY,
    		cascade = CascadeType.ALL)
    BtProducts products;
    @ManyToOne(fetch=FetchType.LAZY)
    Accounts account;
    String plant;
    @ManyToOne(fetch = FetchType.LAZY)
    BtSiteDetails siteDetails;

    @ManyToOne(fetch=FetchType.LAZY)
    Accounts primaryAccount;

    @ManyToOne
    AssetsStatus status;
    @Column(length=500)
    String remarks;

    String customer;

    String source="CRM";


    public BtAssets(){}
    public BtAssets(Long id){
        this.id=id;
    }
    
    public BtAssets(String id){
        this.manualId=id;
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

    @Transient transient BtAssets old;

    @Override
    @PostLoad
    public void old() {
        this.old= (BtAssets) SerializationUtils.clone(this);
    }

    @Override
    public BtAssets gOld() {
        return this.old;
    }

    public BtProducts getProducts() {
        return products;
    }

    public void setProducts(BtProducts products) {
        this.products = products;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public BtSiteDetails getSiteDetails() {
        return siteDetails;
    }

    public void setSiteDetails(BtSiteDetails siteDetails) {
        this.siteDetails = siteDetails;
    }

    public Accounts getPrimaryAccount() {
        return primaryAccount;
    }

    public void setPrimaryAccount(Accounts primaryAccount) {
        this.primaryAccount = primaryAccount;
    }

    public AssetsStatus getStatus() {
        return status;
    }

    public void setStatus(AssetsStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    
    public String toString() {
		return ""+id;
	} 
}
