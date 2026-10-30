package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BoardingLoadingDetails implements java.io.Serializable,OldPresist<BoardingLoadingDetails>{
	@Id
	String boardingName;
	double LodgingClassACity;
	double LodgingClassBCity;
	double LodgingClassCCity;
	double boardingAmountCityA;
	double boardingAmountCityB;
	double boardingAmountCityC;
	double twoWheelerChargesParKilo;
	double fourWheelChargesParkilo;
	double twoWheelerMaxDistance;
	double fourWheelerMaxDistance;
	
	double lodgingClassACitySelf;
	double lodgingClassBCitySelf;
	double lodgingClassCCitySelf;
	
	@ManyToOne
	@JoinColumn(name="owner_id")
	EmployeeDetails accountOwner;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	boolean synced;
	
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
	 * @return the accountOwner
	 */
	public EmployeeDetails getAccountOwner() {
		return accountOwner;
	}

	/**
	 * @param accountOwner the accountOwner to set
	 */
	public void setAccountOwner(EmployeeDetails accountOwner) {
		this.accountOwner = accountOwner;
	}

	

	/**
	 * @param boardingName
	 */
	public BoardingLoadingDetails(String boardingName) {
		this.boardingName = boardingName;
	}
	
	

	/**
	 * 
	 */
	public BoardingLoadingDetails() {
	}
	

	public String getBoardingName() {
		return boardingName;
	}

	public void setBoardingName(String boardingName) {
		this.boardingName = boardingName;
	}

	public double getLodgingClassACity() {
		return LodgingClassACity;
	}

	public void setLodgingClassACity(double lodgingClassACity) {
		LodgingClassACity = lodgingClassACity;
	}

	public double getLodgingClassBCity() {
		return LodgingClassBCity;
	}

	public void setLodgingClassBCity(double lodgingClassBCity) {
		LodgingClassBCity = lodgingClassBCity;
	}

	public double getLodgingClassCCity() {
		return LodgingClassCCity;
	}

	public void setLodgingClassCCity(double lodgingClassCCity) {
		LodgingClassCCity = lodgingClassCCity;
	}

	public double getBoardingAmountCityA() {
		return boardingAmountCityA;
	}

	public void setBoardingAmountCityA(double boardingAmountCityA) {
		this.boardingAmountCityA = boardingAmountCityA;
	}

	public double getBoardingAmountCityB() {
		return boardingAmountCityB;
	}

	public void setBoardingAmountCityB(double boardingAmountCityB) {
		this.boardingAmountCityB = boardingAmountCityB;
	}

	public double getBoardingAmountCityC() {
		return boardingAmountCityC;
	}

	public void setBoardingAmountCityC(double boardingAmountCityC) {
		this.boardingAmountCityC = boardingAmountCityC;
	}

	public double getTwoWheelerChargesParKilo() {
		return twoWheelerChargesParKilo;
	}

	public void setTwoWheelerChargesParKilo(double twoWheelerChargesParKilo) {
		this.twoWheelerChargesParKilo = twoWheelerChargesParKilo;
	}

	public double getFourWheelChargesParkilo() {
		return fourWheelChargesParkilo;
	}

	public void setFourWheelChargesParkilo(double fourWheelChargesParkilo) {
		this.fourWheelChargesParkilo = fourWheelChargesParkilo;
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

	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public double getTwoWheelerMaxDistance() {
		return twoWheelerMaxDistance;
	}

	public void setTwoWheelerMaxDistance(double twoWheelerMaxDistance) {
		this.twoWheelerMaxDistance = twoWheelerMaxDistance;
	}

	public double getFourWheelerMaxDistance() {
		return fourWheelerMaxDistance;
	}

	public void setFourWheelerMaxDistance(double fourWheelerMaxDistance) {
		this.fourWheelerMaxDistance = fourWheelerMaxDistance;
	}
	
	public double getLodgingClassACitySelf() {
		return lodgingClassACitySelf;
	}

	public void setLodgingClassACitySelf(double lodgingClassACitySelf) {
		this.lodgingClassACitySelf = lodgingClassACitySelf;
	}

	public double getLodgingClassBCitySelf() {
		return lodgingClassBCitySelf;
	}

	public void setLodgingClassBCitySelf(double lodgingClassBCitySelf) {
		this.lodgingClassBCitySelf = lodgingClassBCitySelf;
	}

	public double getLodgingClassCCitySelf() {
		return lodgingClassCCitySelf;
	}

	public void setLodgingClassCCitySelf(double lodgingClassCCitySelf) {
		this.lodgingClassCCitySelf = lodgingClassCCitySelf;
	}



	@javax.persistence.Transient transient BoardingLoadingDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BoardingLoadingDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BoardingLoadingDetails gOld() {
		return old;
	}
	public String toString() {
			return ""+boardingName;
	}
}
