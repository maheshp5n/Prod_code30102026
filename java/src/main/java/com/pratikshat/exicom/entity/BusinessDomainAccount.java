package com.pratikshat.exicom.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BusinessDomainAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Accounts accounts;
    @ManyToOne(fetch = FetchType.LAZY)
    BusinessDomain businessDomain;

    Date createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails createdBy;

    public BusinessDomainAccount(){}

    public BusinessDomainAccount(Long id){
        this.id=id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public EmployeeDetails getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(EmployeeDetails createdBy) {
        this.createdBy = createdBy;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public BusinessDomain getBusinessDomain() {
        return businessDomain;
    }

    public void setBusinessDomain(BusinessDomain businessDomain) {
        this.businessDomain = businessDomain;
    }
}
