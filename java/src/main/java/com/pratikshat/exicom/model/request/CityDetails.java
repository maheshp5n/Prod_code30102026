package com.pratikshat.exicom.model.request;

public class CityDetails {
	String cityName;
	int cityCategoryId;
	
	
	public com.pratikshat.exicom.entity.CityDetails getCityDetails(){
		return new com.pratikshat.exicom.entity.CityDetails(cityName,cityCategoryId);
	}
	/**
	 * 
	 */
	public CityDetails(com.pratikshat.exicom.entity.CityDetails city) {
		this.cityName=city.getCityName();
		this.cityCategoryId=city.getCityCatagory()!=null? city.getCityCatagory().getCityCategoryId():0;
	}
	public CityDetails() {
	}
	/**
	 * @param cityName
	 * @param catagoryName
	 */
	public CityDetails(String cityName, int cityCatagoryId) {
		this.cityName = cityName;
		this.cityCategoryId = cityCatagoryId;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getCityCategoryId() {
		return cityCategoryId;
	}
	public void setCityCategoryId(int cityCategoryId) {
		this.cityCategoryId = cityCategoryId;
	}
	
	
}
