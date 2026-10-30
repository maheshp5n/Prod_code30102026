package com.pratikshat.exicom.model.response;

import java.util.Date;

public class SiteDetailsResponse {
	long siteId;
	String primaryAccountSapNumber;
	String primaryAccountName;
	String ownerEmailId;
	String ownerFirstName;
	String ownerMiddleName;
	String ownerLastName;
	String siteName;
	String accountSapNumber;
	String accountName;
	long serviceCenterId;
	String serviceCenterName;
	String infraId;
	long serviceResourceId;
	String serviceResourceName;
	String circleId;
	String circleName;
	long ssaId;
	String ssaName;
	String clusterDistrict;
	String siteDescription;
	String siteAddress;
	String createByEmailId;
	String createByFirstName;
	String createdByMiddleName;
	String createByLastName;
	Date createdDate;
	String modifiedByEmailId;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	Date modifiedDate;
	String siteManualId;
	String customer;
	String indusId;
	/**
	 * @return the infraId
	 */
	public String getInfraId() {
		return infraId;
	}
	/**
	 * @param infraId the infraId to set
	 */
	public void setInfraId(String infraId) {
		this.infraId = infraId;
	}
	public SiteDetailsResponse() {}
	public SiteDetailsResponse(com.pratikshat.exicom.entity.SiteDetails site) {
		this.siteId=site.getSiteId();
		if(site.getPrimaryAccount()!=null) {
			this.primaryAccountSapNumber=site.getPrimaryAccount().getSapCustomerNumber();
			this.primaryAccountName=site.getPrimaryAccount().getName();
			
		}
		if(site.getOwner()!=null) {
			this.ownerEmailId=site.getOwner().getEmailId();
			this.ownerFirstName=site.getOwner().getFirstName();
			this.ownerMiddleName=site.getOwner().getMiddleName();
			this.ownerLastName=site.getOwner().getLastName();
		}
		this.siteName=site.getSiteName();
		if(site.getAccount()!=null) {
			this.accountName=site.getAccount().getName();
			this.accountSapNumber=site.getAccount().getSapCustomerNumber();
		}
		if(site.getServiceCenter()!=null) {
			this.serviceCenterId=site.getServiceCenter().getLegalEntityId();
			this.serviceCenterName=site.getServiceCenter().getLegalEntityName();
		}
		this.infraId=site.getInfraId();
		if(site.getServiceResource()!=null) {
			this.serviceResourceId=site.getServiceResource().getServiceResourceId();
			this.serviceResourceName=site.getServiceResource().getName();
		}
		if(site.getCircle()!=null) {
			this.circleId=site.getCircle().getCircleName();
			this.circleName=site.getCircle().getCircleName();
		}
		if(site.getSsa()!=null) {
			this.ssaId=site.getSsa().getId();
			this.ssaName=site.getSsa().getName();
		}
		this.clusterDistrict=site.getClusterDistrict();
		this.siteDescription=site.getSiteDescription();
		this.siteAddress=site.getSiteAddress();
		if(site.getCreateBy()!=null) {
			this.createByEmailId=site.getCreateBy().getEmailId();
			this.createByFirstName=site.getCreateBy().getFirstName();
			this.createdByMiddleName=site.getCreateBy().getMiddleName();
			this.createByLastName=site.getCreateBy().getLastName();
			
		}
		this.createdDate=site.getCreatedDate();
		if(site.getModifiedBy()!=null) {
			this.modifiedByEmailId=site.getModifiedBy().getEmailId();
			this.modifiedByFirstName=site.getModifiedBy().getFirstName();
			this.modifiedByMiddleName=site.getModifiedBy().getMiddleName();
			this.modifiedByLastName=site.getModifiedBy().getLastName();
		}
		this.modifiedDate=site.getModifiedDate();
		this.siteManualId=site.getManualSiteId();
		this.customer = site.getCustomer();
		this.indusId = site.getIndusId();
	}
	
	/**
	 * @return the siteId
	 */
	public long getSiteId() {
		return siteId;
	}
	/**
	 * @return the primaryAccountSapNumber
	 */
	public String getPrimaryAccountSapNumber() {
		return primaryAccountSapNumber;
	}
	/**
	 * @return the primaryAccountName
	 */
	public String getPrimaryAccountName() {
		return primaryAccountName;
	}
	/**
	 * @return the ownerEmailId
	 */
	public String getOwnerEmailId() {
		return ownerEmailId;
	}
	/**
	 * @return the ownerFirstName
	 */
	public String getOwnerFirstName() {
		return ownerFirstName;
	}
	/**
	 * @return the ownerLastName
	 */
	public String getOwnerLastName() {
		return ownerLastName;
	}
	/**
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}
	/**
	 * @return the accountSapNumber
	 */
	public String getAccountSapNumber() {
		return accountSapNumber;
	}
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @return the serviceCenterId
	 */
	public long getServiceCenterId() {
		return serviceCenterId;
	}
	/**
	 * @return the serviceCenterName
	 */
	public String getServiceCenterName() {
		return serviceCenterName;
	}
	/**
	 * @return the serviceResourceId
	 */
	public long getServiceResourceId() {
		return serviceResourceId;
	}
	/**
	 * @return the serviceResourceName
	 */
	public String getServiceResourceName() {
		return serviceResourceName;
	}
	/**
	 * @return the circleId
	 */
	public String getCircleId() {
		return circleId;
	}
	/**
	 * @return the circleName
	 */
	public String getCircleName() {
		return circleName;
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
	 * @return the createByEmailId
	 */
	public String getCreateByEmailId() {
		return createByEmailId;
	}
	/**
	 * @return the createByFirstName
	 */
	public String getCreateByFirstName() {
		return createByFirstName;
	}
	/**
	 * @return the createByLastName
	 */
	public String getCreateByLastName() {
		return createByLastName;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the modifiedByEmailId
	 */
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}
	/**
	 * @return the modifiedByFirstName
	 */
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	/**
	 * @return the modifiedByLastName
	 */
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	/**
	 * @param primaryAccountSapNumber the primaryAccountSapNumber to set
	 */
	public void setPrimaryAccountSapNumber(String primaryAccountSapNumber) {
		this.primaryAccountSapNumber = primaryAccountSapNumber;
	}
	/**
	 * @param primaryAccountName the primaryAccountName to set
	 */
	public void setPrimaryAccountName(String primaryAccountName) {
		this.primaryAccountName = primaryAccountName;
	}
	/**
	 * @param ownerEmailId the ownerEmailId to set
	 */
	public void setOwnerEmailId(String ownerEmailId) {
		this.ownerEmailId = ownerEmailId;
	}
	/**
	 * @param ownerFirstName the ownerFirstName to set
	 */
	public void setOwnerFirstName(String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}
	/**
	 * @param ownerLastName the ownerLastName to set
	 */
	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}
	/**
	 * @param siteName the siteName to set
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	/**
	 * @param accountSapNumber the accountSapNumber to set
	 */
	public void setAccountSapNumber(String accountSapNumber) {
		this.accountSapNumber = accountSapNumber;
	}
	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * @param serviceCenterId the serviceCenterId to set
	 */
	public void setServiceCenterId(long serviceCenterId) {
		this.serviceCenterId = serviceCenterId;
	}
	/**
	 * @param serviceCenterName the serviceCenterName to set
	 */
	public void setServiceCenterName(String serviceCenterName) {
		this.serviceCenterName = serviceCenterName;
	}
	/**
	 * @param serviceResourceId the serviceResourceId to set
	 */
	public void setServiceResourceId(long serviceResourceId) {
		this.serviceResourceId = serviceResourceId;
	}
	/**
	 * @param serviceResourceName the serviceResourceName to set
	 */
	public void setServiceResourceName(String serviceResourceName) {
		this.serviceResourceName = serviceResourceName;
	}
	/**
	 * @param circleId the circleId to set
	 */
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	/**
	 * @param circleName the circleName to set
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
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
	 * @param createByEmailId the createByEmailId to set
	 */
	public void setCreateByEmailId(String createByEmailId) {
		this.createByEmailId = createByEmailId;
	}
	/**
	 * @param createByFirstName the createByFirstName to set
	 */
	public void setCreateByFirstName(String createByFirstName) {
		this.createByFirstName = createByFirstName;
	}
	/**
	 * @param createByLastName the createByLastName to set
	 */
	public void setCreateByLastName(String createByLastName) {
		this.createByLastName = createByLastName;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param modifiedByEmailId the modifiedByEmailId to set
	 */
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	/**
	 * @param modifiedByFirstName the modifiedByFirstName to set
	 */
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	/**
	 * @param modifiedByLastName the modifiedByLastName to set
	 */
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the siteManualId
	 */
	public String getSiteManualId() {
		return siteManualId;
	}
	/**
	 * @param siteManualId the siteManualId to set
	 */
	public void setSiteManualId(String siteManualId) {
		this.siteManualId = siteManualId;
	}
	public long getSsaId() {
		return ssaId;
	}
	public void setSsaId(long ssaId) {
		this.ssaId = ssaId;
	}
	public String getSsaName() {
		return ssaName;
	}
	public void setSsaName(String ssaName) {
		this.ssaName = ssaName;
	}
	public String getOwnerMiddleName() {
		return ownerMiddleName;
	}
	public void setOwnerMiddleName(String ownerMiddleName) {
		this.ownerMiddleName = ownerMiddleName;
	}
	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}
	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}
	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}
	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
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
	
	
}
