package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostUpdate;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;



@Entity
@EntityListeners(ObjectAuditListener.class)
public class SiteDetails implements java.io.Serializable,OldPresist<SiteDetails>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long siteId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	Accounts primaryAccount;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails owner;
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createBy;
	Date createdDate;
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	String manualSiteId;
	@Column(length = 20)
	String zoneZoneName;
	@Column(length = 20)
	String customer;
	
	String indusId;
	
	/**
	 * @return the manualSiteId
	 */
	public String getManualSiteId() {
		return manualSiteId;
	}
	/**
	 * @param manualSiteId the manualSiteId to set
	 */
	public void setManualSiteId(String manualSiteId) {
		this.manualSiteId = manualSiteId;
	}
	public SiteDetails() {}
	public SiteDetails(long siteId){
		this.siteId=siteId;
	}
	
	/**
	 * @return the createBy
	 */
	public EmployeeDetails getCreateBy() {
		return createBy;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the modifiedBy
	 */
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(EmployeeDetails createBy) {
		this.createBy = createBy;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the primaryAccount
	 */
	public Accounts getPrimaryAccount() {
		return primaryAccount;
	}
	/**
	 * @return the owner
	 */
	public EmployeeDetails getOwner() {
		return owner;
	}
	/**
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}
	/**
	 * @return the account
	 */
	public Accounts getAccount() {
		return account;
	}
	/**
	 * @return the serviceCenter
	 */
	public LegalEntity getServiceCenter() {
		return serviceCenter;
	}
	/**
	 * @return the infraId
	 */
	public String getInfraId() {
		return infraId;
	}
	/**
	 * @return the serviceResource
	 */
	public ServiceRecource getServiceResource() {
		return serviceResource;
	}
	/**
	 * @return the circle
	 */
	public CircleDetails getCircle() {
		return circle;
	}
	
	/**
	 * @return the clusterDistrict
	 */
	public String getClusterDistrict() {
		return clusterDistrict;
	}
	/**
	 * @return the siteDescription
	 */
	public String getSiteDescription() {
		return siteDescription;
	}
	/**
	 * @return the siteAddress
	 */
	public String getSiteAddress() {
		return siteAddress;
	}
	/**
	 * @param primaryAccount the primaryAccount to set
	 */
	public void setPrimaryAccount(Accounts primaryAccount) {
		this.primaryAccount = primaryAccount;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}
	/**
	 * @param siteName the siteName to set
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(Accounts account) {
		this.account = account;
	}
	/**
	 * @param serviceCenter the serviceCenter to set
	 */
	public void setServiceCenter(LegalEntity serviceCenter) {
		this.serviceCenter = serviceCenter;
	}
	/**
	 * @param infraId the infraId to set
	 */
	public void setInfraId(String infraId) {
		this.infraId = infraId;
	}
	/**
	 * @param serviceResource the serviceResource to set
	 */
	public void setServiceResource(ServiceRecource serviceResource) {
		this.serviceResource = serviceResource;
	}
	/**
	 * @param circle the circle to set
	 */
	public void setCircle(CircleDetails circle) {
		this.circle = circle;
	}
	/**
	 * @param clusterDistrict the clusterDistrict to set
	 */
	public void setClusterDistrict(String clusterDistrict) {
		this.clusterDistrict = clusterDistrict;
	}
	/**
	 * @param siteDescription the siteDescription to set
	 */
	public void setSiteDescription(String siteDescription) {
		this.siteDescription = siteDescription;
	}
	/**
	 * @param siteAddress the siteAddress to set
	 */
	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}
	/**
	 * @return the siteId
	 */
	public long getSiteId() {
		return siteId;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	
	public SSA getSsa() {
		return ssa;
	}
	
	public void setSsa(SSA ssa) {
		this.ssa = ssa;
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



	@Transient transient SiteDetails old;
	
	@PostLoad
	public void old() {
		this.old=(SiteDetails)SerializationUtils.clone(this);
	}
	@Override
	public SiteDetails gOld() {
		// TODO Auto-generated method stub
		return old;
	}
	
	public String toString() {
		return ""+siteId;
	}
	public String getIndusId() {
		return indusId;
	}
	public void setIndusId(String indusId) {
		this.indusId = indusId;
	}

}
