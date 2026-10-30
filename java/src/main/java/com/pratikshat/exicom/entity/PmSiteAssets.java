package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class PmSiteAssets extends Auditable<EmployeeDetails> implements Serializable,OldPresist<PmSiteAssets>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String manualId;

    @ManyToOne(fetch = FetchType.LAZY)
    SiteDetails siteDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    Assets assets;

    @Transient transient PmSiteAssets old;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;


    @Override
    public void old() {
        
        this.old=(PmSiteAssets)org.apache.commons.lang.SerializationUtils.clone(this);
    }

    @Override
    public PmSiteAssets gOld() {
        
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
        // TODO Auto-generated method stub
        return this.owner;
    }

    @Override
    public void setOwner(EmployeeDetails u) {
        // TODO Auto-generated method stub
        this.owner=u;
    }

    @Override
    public String getManualId() {
        // TODO Auto-generated method stub
        return this.manualId;
    }

    @Override
    public void setManualId(String manualId) {
        // TODO Auto-generated method stub
        this.manualId=manualId;
    }

    public SiteDetails getSiteDetails() {
        return siteDetails;
    }

    public void setSiteDetails(SiteDetails siteDetails) {
        this.siteDetails = siteDetails;
    }

    public Assets getAssets() {
        return assets;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }


    
}
