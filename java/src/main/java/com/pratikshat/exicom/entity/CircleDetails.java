package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class CircleDetails implements java.io.Serializable,OldPresist<CircleDetails>{
	@Id
	String circleName;
	@ManyToOne
	ZoneDetails zone;

	@ManyToOne
	ZoneDetails zoneBattery;

	@ManyToOne
	@JoinColumn(name="account_owner_id")
	EmployeeDetails accountOwner;
	@ManyToOne
	@JoinColumn(name="country_code")
	CountryDetails country;
	
	@ManyToOne
	LegalEntity serviceCenterBattery;
	@ManyToOne
	LegalEntity serviceCenterPower;
	@ManyToOne
	LegalEntity serviceCenterEv;
	
	@ManyToOne
	@JoinColumn(name="circle_manager_battry_id")
	ContactDetails circleManagerBattry;
	@ManyToOne
	@JoinColumn(name="circle_manager_power_id")
	ContactDetails circleManagerPower;
	@ManyToOne
	ContactDetails circleManagerEv;
	@ManyToOne
	@JoinColumn(name="created_by_id")
	EmployeeDetails createBy;
	Date createdDate;
	@ManyToOne
	@JoinColumn(name="last_modified_by_id")
	EmployeeDetails modifiedBy;
	Date lastModifyedDate;
	boolean synced;
	
	String sapCode;
	
	/**
	 * @return the circleManagerBattry
	 */
	public ContactDetails getCircleManagerBattry() {
		return circleManagerBattry;
	}
	/**
	 * @return the circleManagerPower
	 */
	public ContactDetails getCircleManagerPower() {
		return circleManagerPower;
	}
	/**
	 * @return the circleManagerEv
	 */
	public ContactDetails getCircleManagerEv() {
		return circleManagerEv;
	}
	/**
	 * @param circleManagerBattry the circleManagerBattry to set
	 */
	public void setCircleManagerBattry(ContactDetails circleManagerBattry) {
		this.circleManagerBattry = circleManagerBattry;
	}
	/**
	 * @param circleManagerPower the circleManagerPower to set
	 */
	public void setCircleManagerPower(ContactDetails circleManagerPower) {
		this.circleManagerPower = circleManagerPower;
	}
	/**
	 * @param circleManagerEv the circleManagerEv to set
	 */
	public void setCircleManagerEv(ContactDetails circleManagerEv) {
		this.circleManagerEv = circleManagerEv;
	}
	/**
	 * @return the synced
	 */
	public boolean isSynced() {
		return synced;
	}
	/**
	 * @param synced the synced to set
	 */
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	/**
	 * @param circleName
	 */
	public CircleDetails(String circleName) {
		this.circleName = circleName;
	}
	/**
	 * 
	 */
	public CircleDetails() {
	}
	
	
	/**
	 * @return the circleName
	 */
	public String getCircleName() {
		return circleName;
	}
	/**
	 * @return the accountOwner
	 */
	public EmployeeDetails getAccountOwner() {
		return accountOwner;
	}
	/**
	 * @return the country
	 */
	public CountryDetails getCountry() {
		return country;
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
	 * @return the lastModifyedDate
	 */
	public Date getLastModifyedDate() {
		return lastModifyedDate;
	}
	/**
	 * @param circleName the circleName to set
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	/**
	 * @param accountOwner the accountOwner to set
	 */
	public void setAccountOwner(EmployeeDetails accountOwner) {
		this.accountOwner = accountOwner;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(CountryDetails country) {
		this.country = country;
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
	 * @param lastModifyedDate the lastModifyedDate to set
	 */
	public void setLastModifyedDate(Date lastModifyedDate) {
		this.lastModifyedDate = lastModifyedDate;
	}
	/**
	 * @return the zone
	 */
	public ZoneDetails getZone() {
		return zone;
	}
	/**
	 * @param zone the zone to set
	 */
	public void setZone(ZoneDetails zone) {
		this.zone = zone;
	}
	public LegalEntity getServiceCenterBattery() {
		return serviceCenterBattery;
	}
	public void setServiceCenterBattery(LegalEntity serviceCenterBattery) {
		this.serviceCenterBattery = serviceCenterBattery;
	}
	public LegalEntity getServiceCenterPower() {
		return serviceCenterPower;
	}
	public void setServiceCenterPower(LegalEntity serviceCenterPower) {
		this.serviceCenterPower = serviceCenterPower;
	}
	public LegalEntity getServiceCenterEv() {
		return serviceCenterEv;
	}
	public void setServiceCenterEv(LegalEntity serviceCenterEv) {
		this.serviceCenterEv = serviceCenterEv;
	}
	
	
	

	public String getSapCode() {
		return sapCode;
	}
	public void setSapCode(String sapCode) {
		this.sapCode = sapCode;
	}




	@javax.persistence.Transient transient CircleDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(CircleDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public CircleDetails gOld() {
		return old;
	}
	public String toString() {
			return ""+circleName;
	}
	public ZoneDetails getZoneBattery() {
		return zoneBattery;
	}
	public void setZoneBattery(ZoneDetails zoneBattery) {
		this.zoneBattery = zoneBattery;
	}

	
}
