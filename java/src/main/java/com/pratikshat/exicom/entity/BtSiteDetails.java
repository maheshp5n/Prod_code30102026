package com.pratikshat.exicom.entity;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.io.Serializable;

import org.apache.commons.lang.SerializationUtils;

import javax.persistence.*;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtSiteDetails extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtSiteDetails>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String manualId;
    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    @ManyToOne(fetch=FetchType.LAZY)
    Accounts primaryAccount;

    String siteName;
    @ManyToOne(fetch=FetchType.LAZY)
    Accounts account;

    @ManyToOne(fetch=FetchType.LAZY)
    LegalEntity serviceCenter;
    String infraId;

    @ManyToOne(fetch=FetchType.LAZY)
    ServiceRecource serviceResource;

    @ManyToOne(fetch=FetchType.LAZY)
    CircleDetails circle;
    @ManyToOne(fetch=FetchType.LAZY)
    SSA ssa;

    String clusterDistrict;
    @Column(length = 500)
    String siteDescription;
    @Column(length = 500)
    String siteAddress;
    @Column(length = 20)
    String zoneZoneName;
    @Column(length = 20)
    String customer;
    String indusId;
    String cmp;
    String rjilJcName;
    

	public BtSiteDetails(){}
    public BtSiteDetails(Long id){
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
    
    @Transient transient BtSiteDetails old;
    @Override
    @PostLoad
    public void old() {
        old=(BtSiteDetails) SerializationUtils.clone(this);
    }

    @Override
    public BtSiteDetails gOld() {
        return this.old;
    }

    public Accounts getPrimaryAccount() {
        return primaryAccount;
    }

    public void setPrimaryAccount(Accounts primaryAccount) {
        this.primaryAccount = primaryAccount;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    public LegalEntity getServiceCenter() {
        return serviceCenter;
    }

    public void setServiceCenter(LegalEntity serviceCenter) {
        this.serviceCenter = serviceCenter;
    }

    public String getInfraId() {
        return infraId;
    }

    public String getRjilJcName() {
		return rjilJcName;
	}
	public void setRjilJcName(String rjilJcName) {
		this.rjilJcName = rjilJcName;
	}
	public void setInfraId(String infraId) {
        this.infraId = infraId;
    }

    public ServiceRecource getServiceResource() {
        return serviceResource;
    }

    public void setServiceResource(ServiceRecource serviceResource) {
        this.serviceResource = serviceResource;
    }

    public CircleDetails getCircle() {
        return circle;
    }

    public void setCircle(CircleDetails circle) {
        this.circle = circle;
    }

    public SSA getSsa() {
        return ssa;
    }

    public void setSsa(SSA ssa) {
        this.ssa = ssa;
    }

    public String getClusterDistrict() {
        return clusterDistrict;
    }

    public void setClusterDistrict(String clusterDistrict) {
        this.clusterDistrict = clusterDistrict;
    }

    public String getSiteDescription() {
        return siteDescription;
    }

    public void setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public String getZoneZoneName() {
        return zoneZoneName;
    }

    public void setZoneZoneName(String zoneZoneName) {
        this.zoneZoneName = zoneZoneName;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
	public String getIndusId() {
		return indusId;
	}
	public void setIndusId(String indusId) {
		this.indusId = indusId;
	}
    public String getCmp() {
		return cmp;
	}
	public void setCmp(String cmp) {
		this.cmp = cmp;
	}
}
