package com.pratikshat.exicom.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BusinessDomainProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    BusinessDomain businessDomain;
    @ManyToOne(fetch = FetchType.LAZY)
    Products products;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails createdBy;

    Date createdDate;
public BusinessDomainProduct(){}
    public BusinessDomainProduct(Long id){
        this.id=id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessDomain getBusinessDomain() {
        return businessDomain;
    }

    public void setBusinessDomain(BusinessDomain businessDomain) {
        this.businessDomain = businessDomain;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public EmployeeDetails getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(EmployeeDetails createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
