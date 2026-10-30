package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.BoardingLoadingDetails;

public class BoardingLoadingDetailsResponse {

	String boardingName;
	double boardingClassACity;
	double boardingClassBCity;
	double boardingClassCCity;
	double boardingAmountCityA;
	double boardingAmountCityB;
	double boardingAmountCityC;
	double twoWheelerChargesParKilo;
	double fourWheelChargesParkilo;
	double twoWheelerMaxDistance;
	double fourWheelMaxDistance;
	
	String ownerFirstName;
	String ownerMiddleName;
	String ownerLastName;
	String ownerEmailId;
	
	
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	String createdByEmailId;
	Date createdDate;
	
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	String modifiedByEmailId;
	Date modifiedDate;
	
	
	double lodgingClassACitySelf;
	double lodgingClassBCitySelf;
	double lodgingClassCCitySelf;
	
	
	
	public BoardingLoadingDetailsResponse(BoardingLoadingDetails boardingLoadingDetails) {
		this.boardingName = boardingLoadingDetails.getBoardingName();
		this.boardingClassACity = boardingLoadingDetails.getLodgingClassACity();
		this.boardingClassBCity = boardingLoadingDetails.getLodgingClassBCity();
		this.boardingClassCCity = boardingLoadingDetails.getLodgingClassCCity();
		this.boardingAmountCityA = boardingLoadingDetails.getBoardingAmountCityA();
		this.boardingAmountCityB = boardingLoadingDetails.getBoardingAmountCityB();
		this.boardingAmountCityC = boardingLoadingDetails.getBoardingAmountCityC();
		this.twoWheelerChargesParKilo = boardingLoadingDetails.getTwoWheelerChargesParKilo();
		this.fourWheelChargesParkilo = boardingLoadingDetails.getFourWheelChargesParkilo();
		
		this.twoWheelerMaxDistance = boardingLoadingDetails.getTwoWheelerMaxDistance();
		this.fourWheelMaxDistance = boardingLoadingDetails.getFourWheelerMaxDistance();
		
		this.lodgingClassACitySelf = boardingLoadingDetails.getLodgingClassACitySelf();
		this.lodgingClassBCitySelf = boardingLoadingDetails.getLodgingClassBCitySelf();
		this.lodgingClassCCitySelf = boardingLoadingDetails.getLodgingClassCCitySelf();
		
		if(boardingLoadingDetails.getAccountOwner()!=null) {
			this.ownerFirstName = boardingLoadingDetails.getAccountOwner().getFirstName();
			this.ownerMiddleName = boardingLoadingDetails.getAccountOwner().getMiddleName();
			this.ownerLastName = boardingLoadingDetails.getAccountOwner().getLastName();
			this.ownerEmailId = boardingLoadingDetails.getAccountOwner().getEmailId();
		}
		
		if(boardingLoadingDetails.getCreatedBy()!=null) {
			this.createdByFirstName = boardingLoadingDetails.getCreatedBy().getFirstName();
			this.createdByMiddleName = boardingLoadingDetails.getCreatedBy().getMiddleName();
			this.createdByLastName = boardingLoadingDetails.getCreatedBy().getLastName();
			this.createdByEmailId  = boardingLoadingDetails.getCreatedBy().getEmailId();
			this.createdDate = boardingLoadingDetails.getCreatedDate();
		}
		
		if(boardingLoadingDetails.getModifiedBy()!=null) {
			this.modifiedByFirstName = boardingLoadingDetails.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = boardingLoadingDetails.getModifiedBy().getMiddleName();
			this.modifiedByLastName = boardingLoadingDetails.getModifiedBy().getLastName();
			this.modifiedByEmailId = boardingLoadingDetails.getModifiedBy().getEmailId();
			this.modifiedDate = boardingLoadingDetails.getModifiedDate();
		}
	}
	public BoardingLoadingDetailsResponse() {
		
	}
	public String getBoardingName() {
		return boardingName;
	}
	public void setBoardingName(String boardingName) {
		this.boardingName = boardingName;
	}
	
	
	
	public double getBoardingClassACity() {
		return boardingClassACity;
	}
	public void setBoardingClassACity(double boardingClassACity) {
		this.boardingClassACity = boardingClassACity;
	}
	public double getBoardingClassBCity() {
		return boardingClassBCity;
	}
	public void setBoardingClassBCity(double boardingClassBCity) {
		this.boardingClassBCity = boardingClassBCity;
	}
	public double getBoardingClassCCity() {
		return boardingClassCCity;
	}
	public void setBoardingClassCCity(double boardingClassCCity) {
		this.boardingClassCCity = boardingClassCCity;
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
	public String getOwnerFirstName() {
		return ownerFirstName;
	}
	public void setOwnerFirstName(String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}
	public String getOwnerLastName() {
		return ownerLastName;
	}
	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}
	public String getOwnerEmailId() {
		return ownerEmailId;
	}
	public void setOwnerEmailId(String ownerEmailId) {
		this.ownerEmailId = ownerEmailId;
	}
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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
	public double getTwoWheelerMaxDistance() {
		return twoWheelerMaxDistance;
	}
	public void setTwoWheelerMaxDistance(double twoWheelerMaxDistance) {
		this.twoWheelerMaxDistance = twoWheelerMaxDistance;
	}
	public double getFourWheelMaxDistance() {
		return fourWheelMaxDistance;
	}
	public void setFourWheelMaxDistance(double fourWheelMaxDistance) {
		this.fourWheelMaxDistance = fourWheelMaxDistance;
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
	
	
	
}
