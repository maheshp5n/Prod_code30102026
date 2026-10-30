package com.pratikshat.exicom.model.request;

import java.util.List;

public class QuotePdfHeaderAndFooterRequest {

	
	long id;
	
	String templateName;
	
	String logo;
	String signature;
	
	String companyName;
	String addressLine1;
	String addressLine2;
	String city;
	String pincode;
	String stateName;
	
	String cin;
	String documentNumber;
	String telephone;
	String gstNumber;
	String website;
	
	String footerline1;
	String footerline2;
	String footerline3;
	String footerline4;
	String footerline5;
	
	boolean isActive;
	
	String countryId;
	List<Long> stateList;
	
	int businessDomainId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getGstNumber() {
		return gstNumber;
	}
	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getFooterline1() {
		return footerline1;
	}
	public void setFooterline1(String footerline1) {
		this.footerline1 = footerline1;
	}
	public String getFooterline2() {
		return footerline2;
	}
	public void setFooterline2(String footerline2) {
		this.footerline2 = footerline2;
	}
	public String getFooterline3() {
		return footerline3;
	}
	public void setFooterline3(String footerline3) {
		this.footerline3 = footerline3;
	}
	public String getFooterline4() {
		return footerline4;
	}
	public void setFooterline4(String footerline4) {
		this.footerline4 = footerline4;
	}
	public String getFooterline5() {
		return footerline5;
	}
	public void setFooterline5(String footerline5) {
		this.footerline5 = footerline5;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public List<Long> getStateList() {
		return stateList;
	}
	public void setStateList(List<Long> stateList) {
		this.stateList = stateList;
	}
	public int getBusinessDomainId() {
		return businessDomainId;
	}
	public void setBusinessDomainId(int businessDomainId) {
		this.businessDomainId = businessDomainId;
	}
	
	
	
}
