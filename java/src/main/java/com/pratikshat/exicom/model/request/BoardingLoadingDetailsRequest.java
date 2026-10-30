package com.pratikshat.exicom.model.request;

public class BoardingLoadingDetailsRequest {

	
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
	double fourWheelerMaxDistance;
	
	double lodgingClassACitySelf;
	double lodgingClassBCitySelf;
	double lodgingClassCCitySelf;
	
	public BoardingLoadingDetailsRequest() {
		
	}
	public BoardingLoadingDetailsRequest(String boardingName) {
		this.boardingName = boardingName;
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
	
	
}
